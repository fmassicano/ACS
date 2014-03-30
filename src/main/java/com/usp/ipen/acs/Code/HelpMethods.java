/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.usp.ipen.acs.Code;

import com.usp.ipen.acs.ImageProcess.Mat.Complex;

/**
 *
 * @author fmassicano
 */
public class HelpMethods {
    
    /**
     * Imprimi o Array
     * 
     * @param c 
     */
    public static void show(Complex[][] c){
        for(int i = 0; i < c.length; i++){
            for(int j = 0; j < c[0].length; j++){
                System.out.print(" " + c[i][j]);
            }
            System.out.println(" ");
        }
    }
    
    /**
     * Imprimi o Array
     * 
     * @param c 
     */
    public static void show(double[][] c){      
        for(int i = 0; i < c.length; i++){
            for(int j = 0; j < c[0].length; j++){
                System.out.print(" " + c[i][j]);
            }
            System.out.println(" ");
        }
    }
    
    /**
     * Imprimi o Array
     * 
     * @param c 
     */
    public static void show(double[] c){      
        for(int i = 0; i < c.length; i++){
            System.out.print(" " + c[i]);
        }
        System.out.println(" ");
    }
    
    /**
     * Imprimi o Array
     * 
     * @param c 
     */
    public static void show(Complex[] c){      
        for(int i = 0; i < c.length; i++){
            System.out.print(" " + c[i]);
        }
        System.out.println(" ");
    }
    
}
