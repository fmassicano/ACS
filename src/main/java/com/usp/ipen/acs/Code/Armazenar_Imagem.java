package com.usp.ipen.acs.Code;

/*
 * ContarLinhas.java
 *
 * Created on 26 de Novembro de 2007, 15:44
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;

/**
 *
 * @author Felipe
 */
public class Armazenar_Imagem {
    
    double[][] imagem;
    
    /** Creates a new instance of ContarLinhas */
    public Armazenar_Imagem(int l, int c) {
        imagem = new double[l][c];
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public double[][] armazenar(String name){
        
        try {
            
            FileReader ler = new FileReader(new File(name));
            BufferedReader  origem = new BufferedReader(ler);
            
            String linha = null;
            int i = 0; // linhas
            int j = 0; // colunas
            
            while ((linha = origem.readLine()) != null) {
                
                StringTokenizer st = new StringTokenizer(linha, " ");
                
                while(st.hasMoreTokens()){
                    imagem[i][j] = Double.parseDouble(st.nextToken());
                    j++;
                }
                j=0;
                i++;
            }
            
            origem.close();
            ler.close();
            
        }catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null,"Arquivo não encontrado.","ERRO",JOptionPane.ERROR_MESSAGE);
        }catch(IOException ex2) {
            JOptionPane.showMessageDialog(null,ex2,"ERRO",JOptionPane.ERROR_MESSAGE);
        }
        
        return imagem;
    }
}
