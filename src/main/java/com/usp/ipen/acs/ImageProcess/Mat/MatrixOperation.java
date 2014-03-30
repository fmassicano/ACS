package com.usp.ipen.acs.ImageProcess.Mat;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import com.usp.ipen.acs.Code.HelpMethods;
import Jama.Matrix;

/**
 * Nesta classe contêm funções que
 * utilizam a biblioteca da matriz
 * Jama
 * 
 * @author fmassicano
 */
public final class MatrixOperation {

    
    /**
     * Soma de Matriz
     * 
     * @param a
     * @param b
     * @return a + b
     */
    public static double[][] plus(double[][] a, double[][] b){
        
        if(a.length != b.length || a[0].length != b[0].length){
            throw new RuntimeException(" As matrizes tem que possuir a mesma dimessão.");
        }
        
        Matrix A = new Matrix(a);
        Matrix B = new Matrix(b);
        return A.plus(B).getArray();
    }
    
    /**
     * Soma de Matriz
     * 
     * @param a
     * @param b
     * @return a + b
     */
    public static double[][] plus(double[][] a, double b){
        Matrix A = new Matrix(a);
        Matrix B = new Matrix(a.length,a[0].length,b);
        return A.plus(B).getArray();
    }
    
    /**
     * Subtração de Matriz
     * @param a
     * @param b
     * @return a - b
     */
    public static double[][] minus(double[][] a, double b){
        Matrix A = new Matrix(a);
        Matrix B = new Matrix(a.length,a[0].length,b);
        return A.minus(B).getArray();
    }
    
    /**
     * Subtração de Matriz
     * @param a
     * @param b
     * @return a - b
     */
    public static double[][] minus(double[] a, double[] b){
        Matrix A = new Matrix(a,1);
        Matrix B = new Matrix(b,1);
        return A.minus(B).getArray();
    }
    
    /**
     * Subtração de Matriz
     * @param a
     * @param b
     * @return a - b
     */
    public static double[][] minus(double[] a, double b){
        Matrix A = new Matrix(a,1);
        Matrix B = new Matrix(1,a.length, b);
        return A.minus(B).getArray();
    }
    
    /**
     * Subtração de Matriz
     * @param a
     * @param b
     * @return a - b
     */
    public static double[][] minus(double[][] a, double[][] b){
        Matrix A = new Matrix(a);
        Matrix B = new Matrix(b);
        return A.minus(B).getArray();
    }
    
    
    
    /**
     * Subtração de Matriz
     * @param a
     * @param b
     * @return a - b
     */
    public static double[][] minus(double[] a, double[] b, int n){
        Matrix A = new Matrix(a,n);
        Matrix B = new Matrix(b,n);
        return A.minus(B).getArray();
    }
    
    /**
     * Multiplicação de Matriz
     * @param a
     * @param b
     * @return a * b
     */
    public static double[][] times(double[][] a, double[][] b){
        Matrix A = new Matrix(a);
        Matrix B = new Matrix(b);
        return A.times(B).getArray();
    }
    
    /**
     * Multiplicação de Matriz por um valor double
     * @param a matriz double
     * @param b valor double
     * @return nova matriz
     */
    public static double[][] times(double[][] a, double b){
        Matrix A = new Matrix(a);
        return A.times(b).getArray();
    }
    
    /**
     * Multiplicação de Matriz por um valor int
     * @param a matriz double
     * @param b valor int
     * @return nova matriz
     */
    public static double[][] times(double[][] a, int b){
        Matrix A = new Matrix(a);
        return A.times(b).getArray();
    }
    
    /**
     * Element-by-element left division
     * 
     * @param a Matriz que será dividida
     * @param b valor a ser dividido
     * @return a./b
     */
    public static double[][] divide(double[][] a , int b){
        Matrix A = new Matrix(a);
        double[][] b_array = MathFunc.matrix_n(A.getRowDimension(), A.getColumnDimension(), b);
        Matrix B = new Matrix(b_array);
        return A.arrayRightDivide(B).getArray();
    }
    
    /**
     * Element-by-element left division
     * 
     * @param a Matriz que será dividida
     * @param b valor a ser dividido
     * @return a./b
     */
    public static double[][] divide(double[][] a , double b){
        Matrix A = new Matrix(a);
        double[][] b_array = MathFunc.matrix_n(A.getRowDimension(), A.getColumnDimension(), b);
        Matrix B = new Matrix(b_array);
        return A.arrayRightDivide(B).getArray();
    }
    
    /**
     * 
     * Faz a tranposta da matriz dada.
     * 
     * @param a matriz
     * @return transposta
     */
    public static double[][] transpose(double[][] a){
        Matrix m = new Matrix(a); 
        return m.transpose().getArray();
    }
    
    /**
     * 
     * Faz a tranposta do vetor dada.
     * 
     * @param a matriz
     * @return transposta
     */
    public static double[][] transpose(double[] a){
        Matrix m = new Matrix(a,1); 
        return m.transpose().getArray();
    }
    
    /**
     * 
     * Vetoriza uma matriz em linha (por coluna).
     * 
     * @param a
     * @return Matriz linha vetorizada.
     */
    public static double[][] vectorize_Bi(double[][] a) {
        Matrix ma = new Matrix(a);
        Matrix r = new Matrix(ma.getColumnPackedCopy(),1);
        return r.getArray();
    }
    
    /**
     * 
     * Vetoriza uma matriz em linha (por coluna).
     * 
     * @param a
     * @return Matriz linha vetorizada.
     */
    public static double[] vectorize_Uni(double[][] a) {
        Matrix ma = new Matrix(a);
        Matrix r = new Matrix(ma.getColumnPackedCopy(),1);
        double[][] array = r.getArray();
        
        double[] v = new double[array[0].length];
        
        System.arraycopy(array[0], 0, v, 0, v.length);
        
        return v;
    }
    
    /**
     * 
     * Transforma um vetor em uma matriz linha.
     * 
     * @param a vetor
     * @return matriz
     */
    public static double[][] vectorTomatrix(double[] a){
        Matrix m = new Matrix(a,1); 
        return m.getArray();
    }
    
    /**
     * Frobenius norm
     * @param a
     * @return sqrt of sum of squares of all elements.
     */
    public static double norm(double[][] a){
        Matrix ma = new Matrix(a);
        return ma.normF();
    }
    
    /**
     * Frobenius norm
     * @param a
     * @return sqrt of sum of squares of all elements.
     */
    public static double norm(double[] a){
        Matrix ma = new Matrix(a,1);
        return ma.normF();
    }
    
    public static void main(String[] args) {
    
        double[][] a = new double[3][4];
        
        a[0][0] = 1;
        a[0][1] = 2;
        a[0][2] = 3;
        a[0][3] = 4;
        a[1][0] = 5;
        a[1][1] = 6;
        a[1][2] = 7;
        a[1][3] = 8;
        a[2][0] = 9;
        a[2][1] = 10;
        a[2][2] = 10;
        a[2][3] = 10;
        
        HelpMethods.show(a);
        HelpMethods.show(minus(a,1.0));
        
//        double[] e = new double[4];
//        e[0] = 2;
//        e[1] = 4;
//        e[2] = 6;
//        e[3] = 8;
//        
//        System.out.println(" Vector ");
//        
//        HelpMethods.show(e);
//        
//        System.out.println(" Matriz ");
//        
//        System.out.println(e.length );
//        
//        HelpMethods.show(vectorTomatrix(e));
//        
//        System.out.println(vectorTomatrix(e).length + " " + vectorTomatrix(e)[0].length);
//        
//        System.exit(0);
//        
//        double[][] a = new double[1][10];
//        
//        a[0][0] = 1;
//        a[0][1] = 2;
//        a[0][2] = 3;
//        a[0][3] = 4;
//        a[0][4] = 5;
//        a[0][5] = 6;
//        a[0][6] = 7;
//        a[0][7] = 8;
//        a[0][8] = 9;
//        a[0][9] = 10;
//        
//        HelpMethods.show(a);
//        System.out.println("");
//        
//        a = transpose(a);
//        
//        
//        HelpMethods.show(a);
//        System.out.println("");
        
        
        
    }
    
}
