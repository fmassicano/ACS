/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.usp.ipen.acs.Attenuation_Correction_SPECT.folder;

import java.io.File;
import javax.swing.JOptionPane;

/**
 *
 * @author fmassicano
 */
public class NewFolder {

    // Atributos
    private String title, path;
    
    // Construtor
    public NewFolder(){
       
    }
    
    // Método
    public void CreatFolder(){
        
        if(title != null && path != null){
            File file = new File(path + title);
            file.mkdir();
        }else {
            JOptionPane.showMessageDialog(null, "Need set title and path.", "ERRO", JOptionPane.ERROR_MESSAGE);
        }
        
    }
    
    public void setPath(String path){
        this.path = path;
    }
    
    public void setTitle(String title){
        this.title = title;
    }
    
}
