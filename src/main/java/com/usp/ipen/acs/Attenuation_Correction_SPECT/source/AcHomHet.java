/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.usp.ipen.acs.Attenuation_Correction_SPECT.source;

import com.usp.ipen.acs.Attenuation_Correction_SPECT.ACS_;
import com.usp.ipen.acs.Attenuation_Correction_SPECT.Math.Mat;
import com.usp.ipen.acs.Attenuation_Correction_SPECT.Print.PrintImage;
import com.usp.ipen.acs.Attenuation_Correction_SPECT.progress.Progress;
import ij.process.ImageProcessor;
import javax.swing.JOptionPane;


/**
 *
 * @author fmassicano
 */
public class AcHomHet {
    
    private PrintImage pImage = new PrintImage();
    private static float[][] coef;
    private static double[][] source;
    private static double[][] matrix;
    private float pixel, nproj;
    private String hom_het;
    private ImageProcessor ip;
    private Mat mat = new Mat(); // construir o objeto contendo funções matemáticas
    private ImageProcessor image;

    public AcHomHet(ImageProcessor ip, String hom_het) {

        this.ip = ip;
        this.hom_het = hom_het;

        this.image = ACS_.getImage();

        /* -- Tamanho do Pixel -- */
        pixel = ParameterToAttenuationCorrection.getSizePixels(); 

        /* -- Número de Projeções -- */
        nproj = ParameterToAttenuationCorrection.getProj();

        source = new double[ip.getWidth()][ip.getHeight()]; // matriz da fonte
        mat.fillZeroMatrix(source, ip.getWidth(), ip.getHeight()); // preenchendo de zeros

        matrix = new double[ip.getWidth()][ip.getHeight()]; // matriz dos coeficientes de correção
        mat.fillZeroMatrix(matrix, ip.getWidth(), ip.getHeight()); // preenchendo de zeros

        Processor();
    }
   
     public void running(){
         
         if(hom_het.equals("homogeneous")) {
             correctionAttenuationHomogeno(ip);
         } else {
             if (hom_het.equals("heterogeneous")) {
                 correctionAttenuationHeterogeneo(ip);
             }
         }
         
     }
   
   /**
     * 
     * C(x,y) = (1/(1/M)*SOMA(i=0,M)[exp(-uL(x,y,teta))]);
     * 
     * @param phantom homogêneo ou heterogêneo.
     */  
   private void correctionAttenuationHomogeno(ImageProcessor ip){
       
       Progress p = new Progress(null, true);

       int quant = ip.getHeight();

       int quant_max = (quant * 10) + 25;

       p.getjProgressBar().setMaximum(quant_max);
       p.getjProgressBar().setMinimum(0);
       
       int valor = 10;

       p.setText("Starting...");

       try {
           
           p.getjProgressBar().setValue(valor);
           Thread.sleep(100);

           int a, i, j, ic, jc, ia, ja, l;
           float sum = 0.0f, mii = 0.0f, e = 0.0f, delta, teta, rad;
           float c, d = 0.0f;
           int m = 0, n = 0;

           /* -- Coeficiente de atenuacao -- */
           mii = ParameterToAttenuationCorrection.getCoef(); //coefA;

           /* -- coeficiente de atenuação linear vezes o tamanho do pixel -- */
           e = mii * pixel;

           delta = (float) (360.0 / nproj);
           teta = 0.0f;
           rad = 0.0f;
           a = 0;
           
           p.setText("Correction...");
           Thread.sleep(100);
           
           for (j = 0; j < ip.getHeight(); j++) { // coluna - heigth - x

                valor = valor + 10;
                p.getjProgressBar().setValue(valor); 
                Thread.sleep(100);

               for (i = 0; i < ip.getWidth(); i++) { // linha - width - y

//                   if (ip.getPixelValue(i, j) == 0.0) {
                   if (ACS_.contour[i][j]) { // só entra se for "false"
                       continue;
                   }

                   sum = 0.0f;

                   for (a = 0; a < nproj; a++) {

                       /* transforma o "teta" de cada "L(x,y,teta)" em radianos */
                       teta = a * delta;
                       rad = (float) ((teta * Math.PI) / 180.0);

                       ic = i;
                       jc = j;

                       for (l = 0; l < 127; l++) { // 127 -> size 128 X 128

                           /* coordenada do ponto inicial p(i,j) */
                           ia = ic;
                           ja = jc;

                           /* coordenada do ponto que irá flutuar para calcular a distância */
                           m = mat.aredondaParaInteiro(i + (l * Math.cos(rad)));
                           n = mat.aredondaParaInteiro(j + (l * Math.sin(rad)));

                           ic = m;
                           jc = n;

                           /* calcula a Distância do ponto p(ic,jc) ao ponto p(ia,ja) */
//                           if (ip.getPixelValue(ic, jc) == 0.0) {
                           if (ACS_.contour[ic][jc]) {
                               d = (float) Math.sqrt(Math.pow((i - ia), 2) + Math.pow((j - ja), 2));
                               break;
                           }

                       }
                       /* calcula a Somatória */
                       sum = (float) (sum + (Math.exp(-e * d)));
                   }
                   /* atribui o fator de correção ao ponto p(i,j) */
                   c = (float) ((1 / (sum / nproj)) * 100.0);
                   matrix[i][j] = c;
               }
               
           }

           valor = valor + 5;
           p.getjProgressBar().setValue(valor);
           p.setText("Building the matrix of source...");
           Thread.sleep(100);
           
           for (j = 0; j < ip.getHeight(); j++) { // coluna - heigth - x
               for (i = 0; i < ip.getWidth(); i++) { // linha - width - y
                   c = (float) (matrix[i][j] / 100.0);
                   source[i][j] = image.getPixelValue(i, j) * c;
               }
           }

           valor = valor + 5;
           p.getjProgressBar().setValue(valor);
           Thread.sleep(100);
           p.setText("Printing the source...");

           pImage.Print();

           valor = valor + 5;
           p.getjProgressBar().setValue(valor);
           Thread.sleep(100);
           p.setText("Finished.");
           Thread.sleep(100);
           
           p.setVisible(false);
           
       } catch (Exception ex) {
           
       }
       
    }

    /**
     *
     * C(x,y) = (1/(1/M)*SOMA(i=0,M)[exp(-[SOMA(j=0,N)u]L(x,y,teta))]);
     *
     * @param phantom homogêneo ou heterogêneo.
     */
   private void correctionAttenuationHeterogeneo(ImageProcessor ip){

//       JOptionPane.showMessageDialog(null, "...", "...", JOptionPane.ERROR_MESSAGE);
       
       Progress p = new Progress(null, true);

       int quant = ip.getHeight();

       int quant_max = (quant * 10) + 25;

       p.getjProgressBar().setMaximum(quant_max);
       p.getjProgressBar().setMinimum(0);

       int valor = 10;

       p.setText("Starting...");

       coef = MapsAttenuation.getCoef();

       try {

           int a, i, j, ic, jc, l;
           float sum = 0.0f, sum_coef = 0.0f, delta, teta, rad;
           float c, d = 0.0f;
           int m = 0, n = 0;


           /* -- Criando matriz de correcão -- */ // fazer um novo método
           for (int linha = 0; linha < ip.getWidth(); linha++) {
               for (int coluna = 0; coluna < ip.getHeight(); coluna++) {
                   coef[linha][coluna] = coef[linha][coluna]*pixel;
               }
           }
           
           delta = (float) (360.0 / nproj);
           teta = 0.0f;
           rad = 0.0f;
           a = 0;

           p.getjProgressBar().setValue(valor);
           p.setText("Correction...");
           Thread.sleep(100);

           for (j = 0; j < ip.getWidth(); j++) { // coluna - width - x

                valor = valor + 10;
                p.getjProgressBar().setValue(valor);
                Thread.sleep(100);

               for (i = 0; i < ip.getHeight(); i++) { // linha - height - y

                   if (ip.getPixelValue(i, j) == 0.0) {
                       continue;
                   }

                   sum = 0.0f;
                   
                   for (a = 0; a < nproj; a++) {

                       /* transforma o "teta" de cada "L(x,y,teta)" em radianos */
                       teta = a * delta;
                       rad = (float) ((teta * Math.PI) / 180.0);

                       ic = i;
                       jc = j;

                       sum_coef = 0.0f;

                       for (l = 1; l < 128; l++) { // 127 -> size 128 X 128

                           /* coordenada do ponto que irá flutuar para calcular a distância */
                           m = mat.aredondaParaInteiro(i + (l * Math.cos(rad)));
                           n = mat.aredondaParaInteiro(j + (l * Math.sin(rad)));

                           ic = m;
                           jc = n;

                           /* verifica se chegou na borda */
                           if (ip.getPixelValue(ic, jc) == 0.0) {
                               break;
                           }
                           sum_coef = sum_coef + coef[ic][jc];
                       }
                       
                       /* calcula a Somatória */
                       sum = (float) (sum + (Math.exp(-sum_coef)));
                       
                   }
                   /* atribui o fator de correção ao ponto p(i,j) */
                   c = (float) ((1 / (sum / nproj)) * 100.0);
                   matrix[i][j] = c;
               }

           }

           valor = valor + 5;
           p.getjProgressBar().setValue(valor);
           p.setText("Building the matrix of source...");
           Thread.sleep(100);


           for (j = 0; j < ip.getHeight(); j++) { // coluna - heigth - x
               for (i = 0; i < ip.getWidth(); i++) { // linha - width - y
                   c = (float) (matrix[i][j] / 100.0);
                   source[i][j] = image.getPixelValue(i, j) * c;
               }
           }

           valor = valor + 5;
           p.getjProgressBar().setValue(valor);
           Thread.sleep(100);
           p.setText("Printing the source...");

           pImage.Print(); // Imprimi a imagem que vai para o SCMS

           valor = valor + 5;
           p.getjProgressBar().setValue(valor);
           p.setText("Finished.");
           Thread.sleep(100);

           p.setVisible(false);

       } catch (Exception ex) {
       }

    }
    
   public static double[][] getMatrix(){
        return matrix;
   }

   public static float[][] getCoef(){
        return coef;
   }
    
   public static double[][] getSource(){
        return source;
    }
    
   private void Processor() {
        new Thread() {
            @Override
            public void run() {
                running();
            }
        }.start();
    }
    
    
}

