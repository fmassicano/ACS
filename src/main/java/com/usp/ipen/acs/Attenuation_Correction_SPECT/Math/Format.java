/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.usp.ipen.acs.Attenuation_Correction_SPECT.Math;

import java.util.Formatter;

/**
 *
 * @author fmassicano
 */
public class Format {

    private String result;
    
    public Format(){
        
    }
    
    public String format_int(int valor){
        
        Formatter formatter = new Formatter();
        formatter.format("%d", valor);
        result = formatter.toString();
        
        return this.result;
    }
    
    public String format_scientific(double valor, int fix){
        
        Formatter formatter = new Formatter();
        formatter.format("%."+fix+"e", valor);
        result = formatter.toString();
        result = result.replaceAll(",", ".");
    
        return this.result;
    }
    
    public String format_double(double valor, int fix){
        
        Formatter formatter = new Formatter();
        formatter.format("%."+fix+"f", valor);
        result = formatter.toString();
        result = result.replaceAll(",", ".");
        
        return this.result;
    }
    
    
}
