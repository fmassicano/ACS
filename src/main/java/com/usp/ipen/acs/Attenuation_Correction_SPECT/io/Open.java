package com.usp.ipen.acs.Attenuation_Correction_SPECT.io;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import ij.ImagePlus;
import ij.io.OpenDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author fmassicano
 */
public class Open {

    // Atributos
    private JFileChooser jFileChooser1;
    private String pathDirectory, nameDiretory;
    private String title, button;
    private boolean lock_Open_Image,lock_Open_File,lock_Open;
    private ImagePlus image;

    // Construtor
    public Open() {
    
    }

    public Open(String title){
        this.title = title;
        selectImage();
    }

    // Métodos
    public void OpenDirectory() {

        int approve;
        lock_Open = false;
        
        if (jFileChooser1 == null) {

            jFileChooser1 = new javax.swing.JFileChooser();

            jFileChooser1.setDialogTitle(title);
            
            jFileChooser1.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        }

        approve = jFileChooser1.showDialog(new JFrame(),button);

        if (approve == JFileChooser.APPROVE_OPTION) {

            pathDirectory = jFileChooser1.getSelectedFile().getPath();

            nameDiretory  = jFileChooser1.getSelectedFile().getName();
            
            lock_Open = true;

        }

        jFileChooser1.setSelectedFile(null);

    }

    public void selectImage(){
        OpenDialog dialogImage = new OpenDialog(title, "");
        String selectedImage = dialogImage.getDirectory() + dialogImage.getFileName();
        image = new ImagePlus(selectedImage);
        pathDirectory = dialogImage.getDirectory();
        nameDiretory = dialogImage.getFileName();
        
        if(image.getProcessor()==null){
            return;
        }else{
            lock_Open_Image = true;
        }
        
    }
    
    public void selectOutFile(){
        OpenDialog dialogFile = new OpenDialog(title, "");
        pathDirectory = dialogFile.getDirectory() + dialogFile.getFileName();
        nameDiretory = dialogFile.getFileName();
        lock_Open_File = true;
    }
    
    public void setLock_Open_Image(boolean lock_Open_Image){
        this.lock_Open_Image = lock_Open_Image;
    } 
    
    public void setLock_Open_File(boolean lock_Open_File){
        this.lock_Open_File = lock_Open_File;
    }
    
    public void setTitle(String title){
         this.title = title;
    }
    
    public void setButton(String button){
         this.button = button;
    }
    
    public ImagePlus getImagePlus(){
        return this.image;
    }
    
    public boolean getLock_Open_Image(){
        return this.lock_Open_Image;
    } 
    
    public boolean getLock_Open_File(){
        return this.lock_Open_File;
    }
    
    public String getPathDirectory(){
        return this.pathDirectory ;
    }
    
    public String getNameDirectory(){
        return this.nameDiretory;
    }
    
    
}
