/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.usp.ipen.acs.ImageProcess.Filters;

import com.usp.ipen.acs.Code.HelpMethods;

/**
 *
 * @author Felipe
 */
public final class FilterMethods {

    public static final int NONE = -1;
    public static final int RAM_LAK = 0;
    public static final int SHEPP_LOGAN = 1;
    public static final int COSINE = 2;
    public static final int HAMMING = 3;
    public static final int HANN = 4;

    /**
     * Não permitir instanciar esta Classe
     */
    private FilterMethods() {
    }

    /**
     * Filtro SHEPP_LOGAN
     * 
     * @param H filtro
     * @param w frequency axis up to Nyquist
     * @param d the fraction of frequencies below the nyquist which we want to pass
     * @return filtro de Sheep_Logan
     */
    public static double[][] shepp_logan(double[][] H, double[][] w, double d) {

        for (int i = 0; i < H.length; i++) {
            for (int j = 1; j < H[0].length; j++) {
                H[i][j] = H[i][j] * (Math.sin(w[i][j] / (2.0 * d) ) * 2.0 * d / w[i][j]);
            }
        }
        return H;
    }

    /**
     * Filtro COSINE
     * 
     * @param H filtro
     * @param w frequency axis up to Nyquist
     * @param d the fraction of frequencies below the nyquist which we want to pass
     * @return filtro de cosine
     */
    public static double[][] cosine(double[][] H, double[][] w, double d) {

        for (int i = 0; i < H.length; i++) {
            for (int j = 1; j < H[0].length; j++) {
                H[i][j] = H[i][j] * Math.cos(w[i][j] / 2 * d);
            }
        }
        return H;
    }

    /**
     * Filtro HAMMING
     * 
     * @param H filtro
     * @param w frequency axis up to Nyquist
     * @param d the fraction of frequencies below the nyquist which we want to pass
     * @return filtro de hamming
     */
    public static double[][] hamming(double[][] H, double[][] w, double d) {

        for (int i = 0; i < H.length; i++) {
            for (int j = 1; j < H[0].length; j++) {
                H[i][j] = H[i][j] * (.54 + .46 * Math.cos(w[i][j] / d));
            }
        }
        return H;
    }

    /**
     * Filtro HANN
     * 
     * @param H filtro
     * @param w frequency axis up to Nyquist
     * @param d the fraction of frequencies below the nyquist which we want to pass
     * @return filtro de Hann
     */
    public static double[][] hann(double[][] H, double[][] w, double d) {
        
        for (int i = 0; i < H.length; i++) {
            for (int j = 1; j < H[0].length; j++) {
                H[i][j] = H[i][j] * ((1 + Math.cos(w[i][j] / d)) / 2);
            }
        }
        return H;
    }
}
