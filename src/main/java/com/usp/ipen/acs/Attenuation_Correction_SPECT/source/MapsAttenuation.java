/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.usp.ipen.acs.Attenuation_Correction_SPECT.source;

import com.usp.ipen.acs.Attenuation_Correction_SPECT.ACS_;
import com.usp.ipen.acs.Attenuation_Correction_SPECT.progress.Progress;
import ij.process.ImageProcessor;

/**
 *
 * @author fmassicano
 */
public class MapsAttenuation {

    private static float[][] coef;
    private int factor;
    private  int w_ct , w_spect , h_spect;
    private  float mi_h2o;//cm^{-1}
    private int numb;
    private ImageProcessor ip;
    private float fac_corr;

    public MapsAttenuation(ImageProcessor ip, int numb) {

        this.ip = ip;
        this.numb = numb;

        Processor();

    }

    public MapsAttenuation(ImageProcessor ip, int numb, float fac_corr, float mi_h2o) {

        this.ip = ip;
        this.numb = numb;
        this.fac_corr = fac_corr;
        this.mi_h2o = mi_h2o;

        Processor();

    }

    public void running(){
       construction(ip);
    }

    private void construction(ImageProcessor ip){

        int[] t = ACS_.get_X_Y();

        this.w_spect = t[1];//y - linhas
        this.h_spect = t[0];//x - colunas

        coef = new float[w_spect][h_spect];

        Progress p2 = new Progress(null, true); // construir o jframe da barra de progresso
        
        p2.setText("Waiting...");

        try {
            if(numb == 0){

                w_ct = ip.getWidth();

                p2.getjProgressBar().setValue(30);
                p2.setText("Construction Maps of Attenuation...");
                Thread.sleep(100);

                DimensionarImagem(ip);

                p2.getjProgressBar().setValue(60);
                p2.setText("Construction Maps of Attenuation...");
                Thread.sleep(100);

                PutCoef();

                p2.getjProgressBar().setValue(100);
                p2.setText("Finished.");
                Thread.sleep(100);

                p2.setVisible(false);

            }else{

                if(numb == 1){

                    p2.getjProgressBar().setValue(50);
                    Thread.sleep(100);
                    p2.setText("Construction Maps of Attenuation...");

                    for (int i = 0; i < w_spect; i++) {
                        for (int j = 0; j < h_spect; j++) {
                            coef[i][j] = ip.getPixelValue(j, i);
                        }
                    }

                    p2.getjProgressBar().setValue(100);
                    p2.setText("Finished...");
                    Thread.sleep(100);

                    p2.setVisible(false);

                }
            }

        } catch (Exception ex) {
        }

    }

    private void DimensionarImagem(ImageProcessor ct){

        factor = w_ct/w_spect;

        double  num = 0;
        int x = 0, y = 0, c = 0, l = 0;

        // fazer primeiro percorrer a linha depois as colunas
        for (int i = 0; i < w_spect; i++) { // esse tem que ser a linha
            for (int j = 0; j < h_spect; j++) { // esse tem que ser a coluna

                for (x = c; x < c + factor; x++) { // esse tem que ser a linha
                    for (y = l; y < l + factor; y++) { // esse tem que ser a coluna
                        num = num + ct.getPixelValue(y, x);
                    }
                }
                l = l + factor;
                coef[i][j] = (float) num / (factor * factor);
                num = 0;
            }
            c = c + factor;
            l = 0;
        }

    }

    private void PutCoef(){

        for(int i = 0; i < w_spect;i++){
            for(int j = 0; j < h_spect; j++){
                coef[i][j]=(((coef[i][j]/1000)+1)*mi_h2o)*fac_corr;
            }
        }

    }

    public static float[][] getCoef(){
        return MapsAttenuation.coef;
    }

    private void Processor() {
        new Thread() {
            @Override
            public void run() {
                running();
            }
        }.start();
    }


//                    System.out.println(coef[0][0] + " " + coef[0][1] + " " + coef[0][2] + " " + coef[0][3] + " " + coef[0][4]);
//                    System.out.println(coef[1][0] + " " + coef[1][1] + " " + coef[1][2] + " " + coef[1][3] + " " + coef[1][4]);
//                    System.out.println(coef[2][0] + " " + coef[2][1] + " " + coef[2][2] + " " + coef[2][3] + " " + coef[2][4]);
//                    System.out.println(coef[3][0] + " " + coef[3][1] + " " + coef[3][2] + " " + coef[3][3] + " " + coef[3][4]);
//                    System.out.println(coef[4][0] + " " + coef[4][1] + " " + coef[4][2] + " " + coef[4][3] + " " + coef[4][4]);
    
    //método Teste
//    public static void main(String[] args){
//
//        double[][] ct = new double[8][8];
//
//        ct[0][0]=1; ct[0][1]=1; ct[0][2]=1; ct[0][3]=1; ct[0][4]=1; ct[0][5]=1; ct[0][6]=1; ct[0][7]=1;
//        ct[1][0]=1; ct[1][1]=1; ct[1][2]=1; ct[1][3]=1; ct[1][4]=1; ct[1][5]=1; ct[1][6]=1; ct[1][7]=1;
//        ct[2][0]=1; ct[2][1]=1; ct[2][2]=1; ct[2][3]=1; ct[2][4]=1; ct[2][5]=1; ct[2][6]=1; ct[2][7]=1;
//        ct[3][0]=1; ct[3][1]=1; ct[3][2]=1; ct[3][3]=1; ct[3][4]=1; ct[3][5]=1; ct[3][6]=1; ct[3][7]=1;
//        ct[4][0]=1; ct[4][1]=1; ct[4][2]=1; ct[4][3]=1; ct[4][4]=1; ct[4][5]=1; ct[4][6]=1; ct[4][7]=1;
//        ct[5][0]=1; ct[5][1]=1; ct[5][2]=1; ct[5][3]=1; ct[5][4]=1; ct[5][5]=1; ct[5][6]=1; ct[5][7]=1;
//        ct[6][0]=1; ct[6][1]=1; ct[6][2]=1; ct[6][3]=1; ct[6][4]=1; ct[6][5]=1; ct[6][6]=1; ct[6][7]=1;
//        ct[7][0]=1; ct[7][1]=1; ct[7][2]=1; ct[7][3]=1; ct[7][4]=1; ct[7][5]=1; ct[7][6]=1; ct[7][7]=1;
//
//        double[][] coef1 = new double[4][4];
//
//        double  num = 0;
//        int x = 0, y = 0, c = 0, l = 0;
//
//        int  factor1 = 8/4;
//        System.out.println("factor = " + factor1);
//
//        // fazer primeiro percorrer a linha depois as colunas
//        for (int i = 0; i < 4; i++) { // esse tem que ser a linha
//            for (int j = 0; j < 4; j++) { // esse tem que ser a coluna
//
//                for (x = c; x < c + factor1; x++) {// esse tem que ser a linha
//
//                    for (y = l; y < l + factor1; y++) { // esse tem que ser a coluna
//                        System.out.println("["+x+"]"+"["+y+"]");
//                        num = num + ct[x][y];
//
//                    }
//
//                }
//                l = l + factor1;
//                coef1[i][j] = num / (factor1*factor1);
//                num = 0;
//            }
//            c = c + factor1;
//            l = 0;
//        }
//
//        System.out.println(coef1[0][0] + " " + coef1[0][1] + " " + coef1[0][2] + " " + coef1[0][3] + "\n" +
//                           coef1[1][0] + " " + coef1[1][1] + " " + coef1[1][2] + " " + coef1[1][3]+ "\n" +
//                           coef1[2][0] + " " + coef1[2][1] + " " + coef1[2][2] + " " + coef1[2][3]+ "\n" +
//                           coef1[3][0] + " " + coef1[3][1] + " " + coef1[3][2] + " " + coef1[3][3]);
//
//
//    }
}
