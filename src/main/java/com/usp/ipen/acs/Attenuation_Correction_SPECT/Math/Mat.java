/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.usp.ipen.acs.Attenuation_Correction_SPECT.Math;

/**
 *
 * @author fmassicano
 */
public class Mat {

    public Mat(){
        
    }
    
    public double getMax(double[][] vector, int heigth, int width){
        
        double max = 0;
        
        for(int j = 0; j < heigth; j++){ // coluna - Height - x
            for(int i = 0; i < width; i++){ // linha - Width - y
                if(vector[i][j] > max){
                    max = vector[i][j];
                }
            }
        }
        
        return max;
        
    }
    
    public int aredondaParaInteiro(double num){
        double p = num - Math.floor(num);
        int resul = 0; 
        if(p < 0.5){
            resul = (int) Math.floor(num);
            
        }else{
            resul = (int) Math.ceil(num);
        }
        
        return resul;
    }
    
    public void fillZeroMatrix(double[][] matrix, int width, int heigth){
        
        for(int j = 0; j < width; j++){ // coluna - Height - x
            for(int i = 0; i < heigth; i++){ // linha - Width - y
                matrix[i][j] = 0.0;
            }
        }
        
    }

    public void setArrayZero(double[] array, int size){

        for (int j = 0; j < size; j++) { array[j] = 0; }

    }

    public void setArrayZero(int[] array, int size){

        for (int j = 0; j < size; j++) { array[j] = 0; }

    }
    
}
