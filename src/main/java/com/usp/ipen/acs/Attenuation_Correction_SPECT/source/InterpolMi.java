/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.usp.ipen.acs.Attenuation_Correction_SPECT.source;

/**
 *
 * @author fmassicano
 */
public class InterpolMi {

    
    private float mi;
    private float[][] e_mi = new float[36][2];



    public InterpolMi(){

        e_mi[0][0] = 1.00E-03f;
        e_mi[1][0] = 1.50E-03f;
        e_mi[2][0] = 2.00E-03f;
        e_mi[3][0] = 3.00E-03f;
        e_mi[4][0] = 4.00E-03f;
        e_mi[5][0] = 5.00E-03f;
        e_mi[6][0] = 6.00E-03f;
        e_mi[7][0] = 8.00E-03f;
        e_mi[8][0] = 1.00E-02f;
        e_mi[9][0] = 1.50E-02f;
        e_mi[10][0] = 2.00E-02f;
        e_mi[11][0] = 3.00E-02f;
        e_mi[12][0] = 4.00E-02f;
        e_mi[13][0] = 5.00E-02f;
        e_mi[14][0] = 6.00E-02f;
        e_mi[15][0] = 8.00E-02f;
        e_mi[16][0] = 1.00E-01f;
        e_mi[17][0] = 1.50E-01f;
        e_mi[18][0] = 2.00E-01f;
        e_mi[19][0] = 3.00E-01f;
        e_mi[20][0] = 4.00E-01f;
        e_mi[21][0] = 5.00E-01f;
        e_mi[22][0] = 6.00E-01f;
        e_mi[23][0] = 8.00E-01f;
        e_mi[24][0] = 1.00E+00f;
        e_mi[25][0] = 1.25E+00f;
        e_mi[26][0] = 1.50E+00f;
        e_mi[27][0] = 2.00E+00f;
        e_mi[28][0] = 3.00E+00f;
        e_mi[29][0] = 4.00E+00f;
        e_mi[30][0] = 5.00E+00f;
        e_mi[31][0] = 6.00E+00f;
        e_mi[32][0] = 8.00E+00f;
        e_mi[33][0] = 1.00E+01f;
        e_mi[34][0] = 1.50E+01f;
        e_mi[35][0] = 2.00E+01f;

        e_mi[0][1] = 4.08E+03f;
        e_mi[1][1] = 1.38E+03f;
        e_mi[2][1] = 6.17E+02f;
        e_mi[3][1] = 1.93E+02f;
        e_mi[4][1] = 8.28E+01f;
        e_mi[5][1] = 4.26E+01f;
        e_mi[6][1] = 2.46E+01f;
        e_mi[7][1] = 1.04E+01f;
        e_mi[8][1] = 5.33E+00f;
        e_mi[9][1] = 1.67E+00f;
        e_mi[10][1] = 8.10E-01f;
        e_mi[11][1] = 3.76E-01f;
        e_mi[12][1] = 2.68E-01f;
        e_mi[13][1] = 2.27E-01f;
        e_mi[14][1] = 2.06E-01f;
        e_mi[15][1] = 1.84E-01f;
        e_mi[16][1] = 1.71E-01f;
        e_mi[17][1] = 1.51E-01f;
        e_mi[18][1] = 1.37E-01f;
        e_mi[19][1] = 1.19E-01f;
        e_mi[20][1] = 1.06E-01f;
        e_mi[21][1] = 9.69E-02f;
        e_mi[22][1] = 8.96E-02f;
        e_mi[23][1] = 7.87E-02f;
        e_mi[24][1] = 7.07E-02f;
        e_mi[25][1] = 6.32E-02f;
        e_mi[26][1] = 5.75E-02f;
        e_mi[27][1] = 4.94E-02f;
        e_mi[28][1] = 3.97E-02f;
        e_mi[29][1] = 3.40E-02f;
        e_mi[30][1] = 3.03E-02f;
        e_mi[31][1] = 2.77E-02f;
        e_mi[32][1] = 2.43E-02f;
        e_mi[33][1] = 2.22E-02f;
        e_mi[34][1] = 1.94E-02f;
        e_mi[35][1] = 1.81E-02f;
        
    }


    public void interpolMi(double e){

        double a = 0.0,
               b = 0.0 ;

        int ant=0,
            dep=0;

        for(int i = 0; i < e_mi.length ; i++) {
            if (e == e_mi[i][0]) {
               this.mi = e_mi[i][1];
               return;
            }
        }

        for(int i = 0; i < e_mi.length - 1 ; i++) {

            if (e > e_mi[i][0] && e < e_mi[i + 1][0]) {

                ant = i;
                dep = i+1;

                double delta_y = Math.log10(e_mi[dep][1]) - Math.log10(e_mi[ant][1]);
                double delta_x = Math.log10(e_mi[dep][0]) - Math.log10(e_mi[ant][0]);

                a = delta_y/delta_x;

                b = Math.log10(e_mi[ant][1]) - ( a * Math.log10(e_mi[ant][0]) );

                this.mi =  (float) ( a * Math.log10(e) + b );

                this.mi = (float) Math.pow(10,this.mi);
                
                return;
            }

        }


    }

    public float getMi(){
        return this.mi;
    }


    public static void main(String args[]){

        InterpolMi mi = new InterpolMi();
        mi.interpolMi(1.4E-1);
        float w_s = mi.getMi();
        System.out.println(mi.getMi());
        mi.interpolMi(7.5E-2);
        float w_c = mi.getMi();
        System.out.println(mi.getMi());
     
        double f = w_s/w_c;

        System.out.println(f);

    }

}



