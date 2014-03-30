package com.usp.ipen.acs.Code;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import Jama.Matrix;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Felipe
 */
public class RadonTransform_old {

    private Matrix image;
    private double[] theta;
    private FileWriter record;
    private PrintWriter save;
    private int m = 0;
    private int n = 0;
    
    public RadonTransform_old(Matrix image, double[] theta) {
        this.image = image;
        this.theta = theta;
        function();
//        imprimir(image, "imageTXT.txt");
    }

    private void function() {

        m = image.getRowDimension();
        n = image.getColumnDimension();
        
        // centro da imagem
        int xc = (int) Math.floor((m + 1) / 2);
        int yc = (int) Math.floor((n + 1) / 2);

        // divide cada pixels numa matriz 2X2 de subpixels
        Matrix d = subpixel2X2();

        int b = (int) Math.ceil((Math.sqrt((m * m) + (n * n)) / 2) + 1);

        Matrix xp = obtainXP(b);

        Matrix sz = obtainSZ(xp);

        Matrix X = obtainX(xc);
        
        Matrix Y = obtainY(yc);
        
        Matrix D = obtainDcolunaTransposto(d);
        
        double[] th = obtainThetaRad(theta);

        Matrix Xp = new Matrix(1, 4*m*n);
        Matrix ip = new Matrix(1, 4*m*n);
        Matrix sum = new Matrix(1, 4*m*n, b+1);
        Matrix k = new Matrix(1, 4*m*n);
        Matrix um = new Matrix(1, 4*m*n,1);
        
        Matrix R = new Matrix((int)sz.get(0, 0), theta.length);
        
        for(int i = 0; i < theta.length; i ++){//theta.length
           Xp = X.times(-Math.sin(th[i])).plus(Y.times(Math.cos(th[i])));
           ip = Xp.plus(sum);
           k = obtainK(ip);
           preencherMatrix(R, accumarray(obtainK(ip), multPoint(D, um.minus(ip.minus(k))), sz).plus(accumarray(k.plus(um),multPoint(D, ip.minus(k)), sz)), i);        
        }
        
        imprimir(R,"RT.txt");
    }
    
    private void imprimir(Matrix matrix, String texto){
        try {
            record = new FileWriter(new File("C:\\Documents and Settings\\fmassicano\\Desktop\\" + texto));
//            record = new FileWriter(new File("F:\\FELIPE\\Congressos\\INAC\\ACS interation\\Teste\\" + texto));
            save = new PrintWriter(record);
            matrix.print(save,1, 7);
            save.close();
            record.close();
        } catch (IOException ex) {
            Logger.getLogger(RadonTransform_old.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Exemplo: 
     * vals = 3 9 0 10 5
     * sub  = 2 2 1 2  4
     * accumarray = 0  22 0 5
     * index acc  = 1  2  3 4
     * 
     * accumarray: cria um array de tamanho sz onde sz é um vetor linha de inteiros de valores não negativos.
     * 
     * @param sub  
     * @param vals 
     * @param sz
     * @return 
     */
    private Matrix accumarray(Matrix sub, Matrix vals, Matrix sz){
        double[][] sub_array = sub.getArrayCopy();
        double[][] vals_array = vals.getArrayCopy();
        double[][] sz_array = sz.getArrayCopy();
        double[][] fator = new double[(int)sz_array[0][0]][(int)sz_array[0][1]];
        
        double[][] ident = preencherColuna(vals_array, sub_array);
        
        preencheZeros(fator);
        
        for (int i = 0; i < fator.length; i++) {
            for (int j = 0; j < ident.length; j++) {
                if (i == ident[j][1] - 1) {
                    fator[i][0] = fator[i][0] + ident[j][0];
                }
            }
        }
        
        Matrix result = new Matrix(fator);
        return result;
    }
    
    private double[][] preencherColuna(double[][] a,double[][] b){
        
        double[][] result = new double[a[0].length][2];
        
        for (int i = 0; i < a[0].length; i++) {
            result[i][0] = a[0][i];
            result[i][1] = b[0][i];
        }
        
        return result;
    }
    
    private void preencherMatrix(Matrix R, Matrix ac, int l){
        
        for(int i = 0; i < R.getRowDimension();i++){
            R.set(i, l, ac.get(i,0));
        }
        
    }
    
    private void preencheZeros(double[][] a){
        for(int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                a[i][j] = 0.0;
            }
        }
    }
    
    private Matrix multPoint(Matrix m, Matrix m1){
 
        double[][] m_array = m.getArrayCopy();
        double[][] m1_array = m1.getArrayCopy();
        double[][] fator = new double[m_array.length][m_array[0].length];
        
        
        for(int i = 0; i < m_array.length; i++) {
            for (int j = 0; j < m_array[0].length; j++) {
                fator[i][j] = m_array[i][j]*m1_array[i][j];
            }
        }
        
        Matrix result = new Matrix(fator);
        
        return result;
    }
    
    private Matrix obtainK(Matrix ip){
        
        Matrix k = new Matrix(1, 4*m*n);
        
        for(int i = 0; i < k.getColumnDimension(); i++){
            k.set(0, i, Math.floor(ip.get(0, i)));
        }
        
        return k;
    }
    
    private Matrix obtainX(int xc){
        
        double[][] x = new double[2*m][2*n];
        
        int k = 0;
        int cont = 0;
        for (int i = 0; i < x.length; i++) {
            for (int j = 0; j < x[0].length; j++) {
                x[i][j] = 0.75 - xc + (k / 2.0);
                if (cont == (2 * n - 1)) {
                    k++;
                    cont = 0;
                } else {
                    cont++;
                }
            }
        }
        Matrix mx = new Matrix(x);
        Matrix X = new Matrix(mx.getColumnPackedCopy(),1);
        return X;
    }

    private Matrix obtainY(int yc){
        
        double[][] y = new double[2*m][2*n];
        
        
        int k = 0;
        int n = 0;
        for (int j = 0; j < y[0].length; j++) {
            for (int i = 0; i < y.length; i++) {
                y[i][j] = 0.75 - yc + (k / 2.0);
                if (n == (2 * image.getRowDimension() - 1)) {
                    k++;
                    n = 0;
                } else {
                    n++;
                }
            }
        }
        
        Matrix my = new Matrix(y);
        Matrix Y = new Matrix(my.getColumnPackedCopy(),1);
        return Y;
    }
    
    private Matrix obtainDcolunaTransposto(Matrix d){
       Matrix dcT = new Matrix(d.getColumnPackedCopy(),1);
       return dcT;
    }

    private double[] obtainThetaRad(double[] theta){
        double[] th = theta;
        
        for (int i = 0; i < th.length; i++) {
            th[i] = th[i]*Math.PI/180;
        }
        
        return th;
    }
    
    private Matrix obtainSZ(Matrix xp) {
        double[] size = {xp.getRowDimension(), xp.getColumnDimension()};

        Matrix sz = new Matrix(size, 1);  
        return sz;  
    }
    
    private Matrix obtainXP(int b) {
        double[] mxp = new double[2 * b + 1];

        for (int i = 0; i < mxp.length; i++) {
            mxp[i] = -b + i;
        }
        
        Matrix xp = new Matrix(mxp, 1);  
        return xp.transpose();  
    }

    private Matrix subpixel2X2() {

        Matrix d = new Matrix(2 * m, 2 * n, 0);

        int ii = 0;
        int jj = 0;

        for (int i = 0; i < d.getRowDimension(); i = i + 2) {
            for (int j = 0; j < d.getColumnDimension(); j = j + 2) {
                d.set(i, j, image.get(ii, jj) / 4);
                d.set(i, j + 1, image.get(ii, jj) / 4);
                d.set(i + 1, j, image.get(ii, jj) / 4);
                d.set(i + 1, j + 1, image.get(ii, jj) / 4);
                jj++;
            }
            jj = 0;
            ii++;
        }

        return d;

    }

   
    public static Matrix normalise(Matrix A){
        
        double max = 0.0;
        
        for (int i = 0; i < A.getRowDimension(); i++) {
            for (int j = 0; j < A.getColumnDimension(); j++) {
                if(A.get(i, j) > max) max = A.get(i, j);
            }
        }
        
        return A.times(1/max);
    }
    
    public static double getMin(Matrix A){
        
        double min = 0.0;
        
        for (int i = 0; i < A.getRowDimension(); i++) {
            for (int j = 0; j < A.getColumnDimension(); j++) {
                if(A.get(i, j) < min) min = A.get(i, j);
            }
        }
        
        return min;
    }
    
    public static double[] getMaxColumn(Matrix A){
        
        double[] max = new double[A.getColumnDimension()];

        for (int j = 0; j < A.getColumnDimension(); j++) {
            max[j] = 0;
            for (int i = 0; i < A.getRowDimension(); i++) {
                if (A.get(i, j) > max[j]) {
                    max[j] = A.get(i, j);
                }
            }
        }
        
        return max;
    }
    
    public static Matrix normMaxColumn(Matrix A, double[] fac){
        
        for (int j = 0; j < A.getColumnDimension(); j++) {
            for (int i = 0; i < A.getRowDimension(); i++) {
                A.set(i, j, A.get(i, j)*fac[j]);
            }
        }
        
        return A;
    }
    
    public static double[] getFac(double[] fs, double[] fc){
        
        double[] fac = new double[fs.length];
        
        for (int i = 0; i < fac.length; i++) {
            fac[i] = fc[i]/fs[i];
        }
        
        return fac;
    }
    
    public static void main(String[] args) {
        
        double[][] R = new double[5][5];
        
        R[0][0] = 1.0;
        R[0][1] = 2.0;
        R[0][2] = 3.0;
        R[0][3] = 4.0;
        R[0][4] = 5.0;
        
        R[1][0] = 1.0;
        R[1][1] = 2.0;
        R[1][2] = 3.0;
        R[1][3] = 4.0;
        R[1][4] = 5.0;
        
        R[2][0] = 1.0;
        R[2][1] = 2.0;
        R[2][2] = 3.0;
        R[2][3] = 4.0;
        R[2][4] = 5.0;
        
        R[3][0] = 1.0;
        R[3][1] = 2.0;
        R[3][2] = 3.0;
        R[3][3] = 4.0;
        R[3][4] = 5.0;
        
        R[4][0] = 1.0;
        R[4][1] = 2.0;
        R[4][2] = 3.0;
        R[4][3] = 4.0;
        R[4][4] = 5.0;
        
        double[] theta = new double[128];
        double k = 0;
        for(int i = 0; i < theta.length ; i++){
            theta[i] = theta[i] + k;
            k++;
        }
        Matrix imageM = new Matrix(R);
        RadonTransform_old RT = new RadonTransform_old(imageM,theta);
        
        
//        Armazenar_Imagem ai1 = new Armazenar_Imagem(64, 64);
//        Armazenar_Imagem ai2 = new Armazenar_Imagem(64, 64);
//        Armazenar_Imagem ai_1 = new Armazenar_Imagem(95, 2*360);
//        Armazenar_Imagem ai_2 = new Armazenar_Imagem(95, 2*360);
//        
////        double[][] image = ai1.armazenar("F:\\FELIPE\\Congressos\\INAC\\ACS interation\\Teste\\chang.txt");
////        double[][] image = ai1.armazenar("F:\\FELIPE\\Congressos\\INAC\\ACS interation\\chang.txt");
//        
////        Matrix imageM = new Matrix(image);
//
////        double[] theta = new double[2*360];
////        double k = 0;
////        for(int i = 0; i < theta.length ; i++){
////            theta[i] = theta[i] + k;
////            k = k + 0.5;
////        }
//        
////        RadonTransform rt = new RadonTransform(imageM, theta);
//        
//        double[][] image1 = ai_1.armazenar("F:\\FELIPE\\Congressos\\INAC\\ACS interation\\Teste\\RT_sino.txt");
//        
//        Matrix s = new Matrix(image1);
//        
//        double[][] image2 = ai_2.armazenar("F:\\FELIPE\\Congressos\\INAC\\ACS interation\\Teste\\RT_chang.txt");
//        
//        Matrix c = new Matrix(image2);
//        
//        Matrix image_ = new Matrix(95, 2*360);
//        Matrix image_1 = new Matrix(95, 2*360);
//        Matrix one = new Matrix(95, 2*360,1.0);
//        
//        double[] fs = getMaxColumn(s);
//        double[] fc = getMaxColumn(c);
//        
//        double[] fac = getFac(fs, fc);
//        
//        image_1 = normMaxColumn(s,fac);
//        
//        image_ = image_1.minus(c);
//        
//        image_.plusEquals(one);
//        
////        image_ = i1.plus(i2);
//        
////        image_ = normalise(image_);
////        
//        try {
//            FileWriter record = new FileWriter(new File("F:\\FELIPE\\Congressos\\INAC\\ACS interation\\Teste\\" + "RT_erroNEW.txt"));
//            PrintWriter save = new PrintWriter(record);
//            image_.print(save,3, 20);
//            save.close();
//            record.close();
//        } catch (IOException ex) {
//            Logger.getLogger(RadonTransform.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
    }
}
