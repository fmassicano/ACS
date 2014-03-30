/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.usp.ipen.acs.Attenuation_Correction_SPECT.Print;

import com.usp.ipen.acs.Attenuation_Correction_SPECT.source.ParameterToAttenuationCorrection;
import com.usp.ipen.acs.Attenuation_Correction_SPECT.source.AcHomHet;
import com.usp.ipen.acs.Attenuation_Correction_SPECT.ACS_;
import com.usp.ipen.acs.Attenuation_Correction_SPECT.Math.Format;
import com.usp.ipen.acs.Attenuation_Correction_SPECT.Math.Mat;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author Felipe Massicano
 */
public class PrintImage {

    private Mat mat = new Mat();
    private Format f = new Format();

    /** Creates a new instance of ImprimirSation_CurveCabecalho */
    public PrintImage() {
    }

    public void PrintTest() {

        int[] t = ACS_.get_X_Y();

        String saida = "";

        double p = 0.0;

        try {

            FileWriter gravar = new FileWriter(new File(ParameterToAttenuationCorrection.getPathDirectory()), true);
            PrintWriter out = new PrintWriter(gravar, true);

            for (int j = 0; j < t[0]; j++) { // coluna - heigth - x

                for (int i = 0; i < t[1]; i++) { // linha - width - y

                    p = ACS_.getCopy().getPixelValue(i, j);
                    saida = f.format_double(p, 2);

                    if (i != t[0] - 1) {
                        out.print(saida + " ");
                    } else {
                        out.println(saida);
                    }

                }
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public void Print() {

        //float [][] coef = AcHomHet.getCoef();
        double [][] imageLoad = AcHomHet.getSource();
        //double[][] imageLoad = AcHomHet.getMatrix();
        int[] t = ACS_.get_X_Y();

        // Armazema o pixel de maior valor
        double pmax = mat.getMax(AcHomHet.getSource(), t[0], t[1]);

        String saida = "";

        double p = 0.0;

        try {

            FileWriter gravar = new FileWriter(new File(ParameterToAttenuationCorrection.getPathDirectory()), true);
            PrintWriter out = new PrintWriter(gravar, true);

            for (int i = 0; i < t[0]; i++) { // coluna - width - x

                for (int j = 0; j < t[1]; j++) { // linha - height - y

//                    p = imageLoad[i][j]/pmax; // SCMS
                    //p = coef[i][j];
                    p = imageLoad[i][j]; // INAC
                    saida = f.format_double(p, 10);

                    if (j != t[0] - 1) {
                        out.print(saida + " ");
                    } else {
                        out.println(saida);
                    }

                }
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    
    
}
