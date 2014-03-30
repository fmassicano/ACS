/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.usp.ipen.acs.Code;

import com.usp.ipen.acs.ImageProcess.Filters.FilterMethods;
import com.usp.ipen.acs.ImageProcess.Interpolation.InterpolationMethods;
import com.usp.ipen.acs.ImageProcess.Mat.MathFunc;
import com.usp.ipen.acs.ImageProcess.Mat.MatrixOperation;
import com.usp.ipen.acs.ImageProcess.Transformations.Transform;

/**
 *
 * @author fmassicano
 */
public class Run {

    public static void main(String[] args) {

        //Todo barra de diretório
        String slash = System.getProperty("file.separator");
        
        boolean windowsNotUnix = System.getProperty("os.name").contains("Win") ? true: false;
        
        // TODO Implementar o código que está no arquivo DropBox/metodologia.txt dentro deste método main
        
        Armazenar_Imagem arm = new Armazenar_Imagem(64, 128);
        double[][] p_radon = new double[64][128];
        
        // Sinograma da imagem original
        if(windowsNotUnix){
            p_radon = arm.armazenar("C:"+slash+"Users"+slash+"Felipe"+slash+"Documents"+slash+"My Dropbox"+slash+"ACS"+slash+"ACPS-v.INAC"+slash+"sinograma");
        }else{
            p_radon = arm.armazenar(slash+"home"+slash+"users"+slash+"fmassicano"+slash+"Dropbox"+slash+"ACS"+slash+"ACPS-v.INAC"+slash+"sinograma");
        }
        // Soma dos elementos do sinograma
        double sum = MathFunc.sum(p_radon);
//        
//        // Normalização do sinograma
        p_radon = MatrixOperation.divide(p_radon, sum);
//        
        Transform it = new Transform();
        
        double[] theta = MathFunc.linspace(0, 360, 128);
//        
        // TODO testar todas as interpolações: fazer futuramente
        it.iradon(p_radon, theta, InterpolationMethods.LINEAR_IRADON, FilterMethods.HANN, 1, 64);
        double[][] img = it.getImage();
        HelpMethods.show(img);
        
        // efetuar o metodo de chang da imagem original
        System.exit(0);
        // Imagem de Chang
        double[][] chang = new double[64][64];
        if(windowsNotUnix){
            chang = arm.armazenar("F:"+slash+"FELIPE"+slash+"Congressos"+slash+"INAC"+slash+"ACS interation"+slash+"Teste2"+slash+"n iterações"+slash+"A"+slash+"sinograma");
        }else{
            chang = arm.armazenar(slash+"home"+slash+"users"+slash+"fmassicano"+slash+"Dropbox"+slash+"ACS"+slash+"ACPS-v.INAC"+slash+"chang"); // linux
        }
        
        it.radon(chang, MathFunc.colon(0, 127), 64);
        double[][] s_chang = MatrixOperation.divide(it.getImageTransform(), sum);
        
        // sub é a diferença entre o sinograma da imagem normalizada e o sinograma de chang normalizado
        // TODO adquirir o p_radon novamente
        double[][] sub = MatrixOperation.minus(p_radon, s_chang);
        
        // erro é a imagem ERRO
        it.iradon(sub, MathFunc.linspace(0, 360, 128), InterpolationMethods.LINEAR_IRADON, FilterMethods.HANN, 1, 64);
        double[][] erro = it.getImage();
        
        
        double[][] im = new double[64][64];
        if(windowsNotUnix){
            chang = arm.armazenar("F:"+slash+"FELIPE"+slash+"Congressos"+slash+"INAC"+slash+"ACS interation"+slash+"Teste2"+slash+"n iterações"+slash+"A"+slash+"sinograma");
        }else{
            chang = arm.armazenar(slash+"home"+slash+"users"+slash+"fmassicano"+slash+"Dropbox"+slash+"ACS"+slash+"ACPS-v.INAC"+slash+"im"); // linux
        }
        
        //image_nova é a imagem corrigida
        double[][] image_nova = MatrixOperation.plus(im, erro);
        HelpMethods.show(image_nova);
//        
//        
//        // TODO retirar após teste ou comentar código -> Visualização da IMG
//        System.out.println("Visualização da IMG");
//        HelpMethods.show(img);
        
//        it.radon(MathFunc.matrix_n(5, 5, 1.0), MathFunc.linspace(0, 127, 128),5);
//        
//        System.out.println(" Matriz R: [" + MathFunc.matrix_n(5, 5, 1.0).length + "," + MathFunc.matrix_n(5, 5, 1.0)[0].length + "]");
//        System.out.println("");
//        HelpMethods.show(MathFunc.matrix_n(5, 5, 1.0));
//        System.out.println("");
//        System.out.println(" Transformada de Radon da Matriz R [" + it.getImageTransform().length + "," + it.getImageTransform()[0].length+"]");
//        System.out.println("");
//        HelpMethods.show(it.getImageTransform());
//        System.out.println("");

    }
}
