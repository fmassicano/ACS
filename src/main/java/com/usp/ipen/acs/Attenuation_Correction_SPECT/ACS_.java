package com.usp.ipen.acs.Attenuation_Correction_SPECT;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

//import Attenuation_Correction_SPECT_PET.source.*;
import com.usp.ipen.acs.Attenuation_Correction_SPECT.source.ParameterToAttenuationCorrection;
import javax.swing.JOptionPane;
import ij.process.*;
import java.awt.*;

/**
 *
 * @author fmassicano
 */
public class ACS_ {

    private static ImageProcessor copy;
    private static ImageProcessor image;
    public static boolean[][] contour;
    
    private static int x , y;

    public static void run(ImageProcessor ip) {

        image = ip;
            
        x = ip.getHeight(); // coluna
        y = ip.getWidth();  // linha

        Rectangle roi = ip.getRoi();
        ImageProcessor mask = ip.getMask();

        /* verifica se mask é diferente de null (se tem mask).
         caso o roi seja um retângulo ou linha ele será igual a null e assim hasMask = false */
        boolean hasMask = (mask != null);

        copy = ip.duplicate();
        contour =  PutFalse(copy.getWidth(),copy.getHeight()); 
        
        // coordenadas do retângulo que contem o ROI
        int rLeft = roi.x;
        int rTop = roi.y;
        int rRight = rLeft + roi.width;
        int rBottom = rTop + roi.height;

        if(rLeft==0 && rTop==0 && rRight==ip.getWidth() && rBottom == ip.getHeight() && !hasMask){
            JOptionPane.showMessageDialog(null, "ROI definido incorretamente.","ERRO", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (hasMask) {// Caso Tenha MASK

            // colocar zeros em todos os pixels fora da MASK
            for (int v = rTop; v < rBottom; v++) { // y -> linha (da direita para esquerda)
                for (int u = rLeft; u < rRight; u++) { // x -> coluna (de cima para baixo)
                    if (!hasMask || mask.getPixel(u - rLeft, v - rTop) == 0) { // ==
                        copy.putPixelValue(u, v, 0.0);
                        contour[u][v] = true;
                    }
                }
            }

            // colocar zeros fora do retângulo
            for (int y_ = 0; y_ < y; y_++) {
                for (int x_ = 0; x_ < x; x_++) {
                    if ((x_ < rLeft || x_ >= rRight) || (y_ < rTop || y_ >= rBottom)) {
                        copy.putPixelValue(x_, y_, 0.0);
                        contour[x_][y_] = true;
                    }
                }
            }

        }else{

            // colocar zeros fora do retângulo
            for (int y_ = 0; y_ < y; y_++) {
                for (int x_ = 0; x_ < x; x_++) {
                    if ((x_ < rLeft || x_ >= rRight) || (y_ < rTop || y_ >= rBottom)) {
                        copy.putPixelValue(x_, y_, 0.0);
                        contour[x_][y_] = true;
                    }
                }
            }

        }

        ParameterToAttenuationCorrection ca = new ParameterToAttenuationCorrection();
        ca.setVisible(true);

    }

    public static ImageProcessor getCopy(){
        return copy;
    }

    public static ImageProcessor getImage(){
        return image;
    }

    public static int[] get_X_Y(){
        int[] x_y = new int[2];

        x_y[0] = x;
        x_y[1] = y;

        return x_y;
    }

    
    public static boolean[][] PutFalse(int w, int h){
        boolean[][] r = new boolean[w][h];
        
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < w; j++) {
                r[i][j] = false;
            }
        }
        
        return r;
    }
    
}

