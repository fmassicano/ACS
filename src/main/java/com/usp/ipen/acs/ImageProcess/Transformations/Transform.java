package com.usp.ipen.acs.ImageProcess.Transformations;

// TODO Métodos em inglês

import com.usp.ipen.acs.ImageProcess.Mat.Complex;


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Esta classe implenta métodos que efetuam transformações
 * em imagens. Métodos:  Transformada de RADON e Transformada 
 * inversa de RADON efetuadas pela classe RadonTrasnform.
 * 
 * @author fmassicano
 */
public final class Transform {
    
    private double[][] image;
    private double[][] imageTransform;
    private Complex[][] image_complex;
    private Complex[][] imageTransform_complex;
    
    /**
     * 
     * Aplica a Transformada de RADON na image 'I'
     * 
     * @param I Imagem
     * @param theta vetor com os ângulos das projeções
     * @param Nb número de linhas da matriz final
     */
    public void radon(double[][] I, double[] theta, int Nb){
        this.image = I;
        this.imageTransform = RADON.radon(I, theta, Nb);
    }
    
    /**
     * 
     * Aplica a Transformada Inversa de RADON na image 'I'
     * 
     * @param R Projection data
     * @param theta the angles at which the projections were taken
     * @param interp the type of interpolation to use
     * @param filter specifying filter or the actual filter
     * @param scaling a scalar specifying normalized freq. at which to crop the frequency response of the filter
     * @param output_size The size of the reconstructed image
     */
    public void iradon(double[][] I, double[] theta, int interp, int filter, double scaling, int output_size){
        this.imageTransform = I;
        this.image = RADON.iradon(I,theta,interp,filter,scaling,output_size);
    }
    
    /**
     * Compute the FFT of x[][], assuming its length is a power of 2
     * 
     * @param x Matriz Complexa
     */
    public void fft(Complex[][] x){
        this.image_complex = x;
        imageTransform_complex = FFT.fft(image_complex);
    }
    
    /**
     * Compute the inverse FFT of x[][], assuming its length is a power of 2
     * @param x Matriz Complexa
     */
    public void ifft(Complex[][] x){
      this.imageTransform_complex = x;
      image_complex = FFT.ifft(imageTransform_complex);
    }
    
    
    /**
     * @return the image
     */
    public double[][] getImage() {
        return image;
    }

    /**
     * @return the imageTransform
     */
    public double[][] getImageTransform() {
        // TODO quando for ifft tem que pegar a parte real
        return imageTransform;
    }

    /**
     * @return the image_i (parte imaginária)
     */
    public Complex[][] getImage_Complex() {
        return image_complex;
    }
    
    /**
     * @return the image_i (parte imaginária)
     */
    public Complex[][] getImageTranform_Complex() {
        return imageTransform_complex;
    }
    
    
}
