/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.usp.ipen.acs.ImageProcess.Interpolation;

/**
 *
 * Efetua diferentes métodos de interpolação
 * 
 * @author fmassicano
 */
public class Interpolation {

    private double[][] InterpMatrix;
    private double[] x;
    private double[][] Y;
    private double[] xi;

    public Interpolation(){
        
    }
    
    /**
     * 
     * Efetua a interpolação da matriz Y com 
     * o vetor x (original) e o vetor xi (novo)
     * por intermédio do método escolhido.
     * <br></br>
     * <br><b>Obs: o vetor x representa o indice 
     * da linha da matriz Y.</b></br>
     * 
     * @param x vetor original
     * @param Y matriz a ser interpolada
     * @param xi novo vetor
     * @param interp o método aplicado na interpolação
     */
    public Interpolation(double[] x, double[][] Y, double[] xi, int interp) {
        this.InterpMatrix = new double[xi.length][Y[0].length];
        this.x = x;
        this.Y = Y;
        this.xi = xi;

        switch (interp) {
            case InterpolationMethods.LINEAR:
                InterpMatrix = InterpolationMethods.linear(x, Y, xi);
                break;
            case InterpolationMethods.SPLINE:
                InterpMatrix = InterpolationMethods.spline(x, Y, xi);
                break;
            case InterpolationMethods.PCHIP:
                InterpMatrix = InterpolationMethods.pchip(x, Y, xi);
                break;
            case InterpolationMethods.CUBIC:
                InterpMatrix = InterpolationMethods.cubic(x, Y, xi);
                break;
            case InterpolationMethods.V5CUBIC:
                InterpMatrix = InterpolationMethods.v5cubic(x, Y, xi);
                break;
            default:
                System.out.println("Método não esta válido");
                break;
        }

    }
    

    /**
     * Construtor para a método IRADON da Classe RadonTransform
     * @param p_radon
     * @param x_radon
     * @param y_radon
     * @param theta_radon
     * @param interp
     * @param N
     * @param ctrIdx  
     */
    public Interpolation(double[][] p_radon, double[][] x_radon, double[][] y_radon, double[] theta_radon, int interp, int N,int ctrIdx) {

        switch (interp) {
            case InterpolationMethods.LINEAR_IRADON:
                InterpMatrix = InterpolationMethods.linear_iradon(p_radon, x_radon, y_radon, theta_radon,N,ctrIdx);
                break;
            case InterpolationMethods.NEAREST_NEIGHBOR:
                InterpMatrix = InterpolationMethods.nearest_neighbor(p_radon, x_radon, y_radon, theta_radon,N,ctrIdx);
                break;
            case InterpolationMethods.SPLINE_IRADON:
                InterpMatrix = InterpolationMethods.spcv_iradon(p_radon, x_radon, y_radon, theta_radon, N, ctrIdx,InterpolationMethods.SPLINE_IRADON);
                break;
            case InterpolationMethods.PCHIP_IRADON:
                InterpMatrix = InterpolationMethods.spcv_iradon(p_radon, x_radon, y_radon, theta_radon, N, ctrIdx,InterpolationMethods.PCHIP_IRADON);
                break;
            case InterpolationMethods.CUBIC_IRADON:
                InterpMatrix = InterpolationMethods.spcv_iradon(p_radon, x_radon, y_radon, theta_radon, N, ctrIdx,InterpolationMethods.CUBIC_IRADON);
                break;
            case InterpolationMethods.V5CUBIC_IRADON:
                InterpMatrix = InterpolationMethods.spcv_iradon(p_radon, x_radon, y_radon, theta_radon, N, ctrIdx,InterpolationMethods.V5CUBIC_IRADON);
                break;
            default:
                System.out.println("Método não esta válido");
                break;
        }

    }
   

    /* Metodos Get and Setter*/
    /**
     * Matriz interpolada
     * @return the InterpMatrix
     */
    public double[][] getInterpMatrix() {
        return this.InterpMatrix;
    }
    

    /**
     * 
     * Vetor original corresponte a matriz Y
     * 
     * @return the x
     */
    public double[] getX() {
        return x;
    }

    /**
     * 
     * Modificar o vetor original corresponte a matriz Y
     * 
     * @param x the x to set
     */
    public void setX(double[] x) {
        this.x = x;
    }

    /**
     * 
     * Matriz que será realiza a interpolação
     * 
     * @return the Y
     */
    public double[][] getY() {
        return Y;
    }

    /**
     * 
     * Modificar a matriz que será realiza a interpolação
     * 
     * @param Y the Y to set
     */
    public void setY(double[][] Y) {
        this.Y = Y;
    }

    /**
     * 
     * Vetor que substituirá o original
     * 
     * @return the xi
     */
    public double[] getXi() {
        return xi;
    }

    /**
     * 
     * Modificar o vetor que substituirá o original
     * 
     * @param xi the xi to set
     */
    public void setXi(double[] xi) {
        this.xi = xi;
    }

    /* Método main para testar o algoritmo */
    public static void main(String[] args) {

        double[] x = {-4.0, -3.0, -2.0, -1.0, 0.0, 1.0, 2.0, 3.0, 4.0};
        double[] xi = {-4.0, -3.0, 1.5, 2.0, 3.5};
        double[] Y = {0.0, 4, 3, 5, 2, 6, 2, 6, 9, 0, 9};
        double[][] Y1 = new double[9][5];

        Y1[0][0] = 0.0;
        Y1[0][1] = 0.1;
        Y1[0][2] = 0.2;
        Y1[0][3] = 0.3;
        Y1[0][4] = 0.4;

        Y1[1][0] = 0.0;
        Y1[1][1] = 0.2;
        Y1[1][2] = 0.4;
        Y1[1][3] = 0.6;
        Y1[1][4] = 0.8;


        Y1[2][0] = 0.0;
        Y1[2][1] = 0.3;
        Y1[2][2] = 0.6;
        Y1[2][3] = 0.9;
        Y1[2][4] = 1.2;

        Y1[3][0] = 0.0;
        Y1[3][1] = 0.4;
        Y1[3][2] = 0.8;
        Y1[3][3] = 1.6;
        Y1[3][4] = 3.2;


        Y1[4][0] = 0.0;
        Y1[4][1] = 0.5;
        Y1[4][2] = 1.0;
        Y1[4][3] = 1.5;
        Y1[4][4] = 2.0;

        Y1[5][0] = 0.0;
        Y1[5][1] = 0.6;
        Y1[5][2] = 1.2;
        Y1[5][3] = 1.8;
        Y1[5][4] = 1.4;

        Y1[6][0] = 0.0;
        Y1[6][1] = 0.7;
        Y1[6][2] = 0.7;
        Y1[6][3] = 0.7;
        Y1[6][4] = 0.7;


        Y1[7][0] = 0.0;
        Y1[7][1] = 0.0;
        Y1[7][2] = 0.8;
        Y1[7][3] = 0.8;
        Y1[7][4] = 0.8;

        Y1[8][0] = 0.0;
        Y1[8][1] = 0.0;
        Y1[8][2] = 0.0;
        Y1[8][3] = 0.0;
        Y1[8][4] = 0.0;

//        Y1[9][0] = 0.0;
//        Y1[9][1] = 0.1;
//        Y1[9][2] = 0.2;
//        Y1[9][3] = 0.3;
//        Y1[9][4] = 0.4;
//
//        Y1[10][0] = 0.0;
//        Y1[10][1] = 0.1;
//        Y1[10][2] = 0.2;
//        Y1[10][3] = 0.3;
//        Y1[10][4] = 0.4;

        Interpolation interp = new Interpolation(x, Y1, xi, InterpolationMethods.LINEAR);
        double[][] R = interp.getInterpMatrix();

        for (int i = 0; i < R.length; i++) {
            for (int j = 0; j < R[0].length; j++) {
                System.out.print(R[i][j] + " ");
            }
            System.out.println("");
        }

        System.out.println();
    }
}
