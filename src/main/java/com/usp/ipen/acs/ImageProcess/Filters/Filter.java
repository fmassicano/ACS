/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.usp.ipen.acs.ImageProcess.Filters;

import com.usp.ipen.acs.Code.HelpMethods;
import com.usp.ipen.acs.ImageProcess.Transformations.Transform;
import com.usp.ipen.acs.ImageProcess.Mat.Complex;
import com.usp.ipen.acs.ImageProcess.Mat.MathFunc;
import com.usp.ipen.acs.ImageProcess.Mat.MatrixOperation;

/**
 *
 * @author Felipe
 */
public class Filter {

    private double[][] FilterMatrix;

    public Filter(double[][] R, int filter, double d) {

        double[][] p = R;

        // Design the filter 
        int len = MathFunc.size(R)[0];
        double[][] H = designFilter(filter, len, d);
        
        if (filter == FilterMethods.NONE) {
            return;
        }

        // Zero pad projections
        p = MathFunc.addMatriz_Below(p, MathFunc.matrix_n(Math.abs(p[0].length - p.length), p[0].length, 0));
        
        // In the code below, I continuously reuse the array p so as to
        // save memory.  This makes it harder to read, but the comments
        // explain what is going on.

        Transform it = new Transform();
        Complex[][] p_complex = MathFunc.parseComplex(p);
        
        // p holds fft of projections
        it.fft(p_complex);
        p_complex = it.getImageTranform_Complex();
        
        // frequency domain filtering
        for (int j = 0; j < p_complex[0].length; j++) {
            MathFunc.timesColumnforVetor(p_complex, H, j);
        }
        
        it.ifft(p_complex);
        p_complex = it.getImage_Complex();

        //p is the filtered projections
        p = Complex.re(p_complex);

        // Truncate the filtered projections
        p = MathFunc.getSubMatriz(0, len-1, 0, p[0].length-1, p);
        
        FilterMatrix = p;
    }

    /**
     * Returns the Fourier Transform of the filter which 
     * will be used to filter the projections
     * 
     * @param filter either the integer specifying the filter
     * @param len the length of the projections
     * @param d he fraction of frequencies below the nyquist which we want to pass
     * @return 
     */
    private double[][] designFilter(int filter, int len, double d) {

        double order = MathFunc.max(64, Math.pow(2, MathFunc.nextpow2(2 * len)));

        if (filter == FilterMethods.NONE) {
            return MathFunc.matrix_n(1, (int) order, 1);
        }

        // First create a ramp filter - go up to the next highest power of 2.
        double[][] H = MatrixOperation.times(MatrixOperation.times(MathFunc.linspace(0, (order / 2.0)), 2), (1.0 / order));
        double[][] w = MatrixOperation.times(MatrixOperation.times(MathFunc.linspace(0, MathFunc.size(H)[1] - 1), 2 * Math.PI), 1.0 / order);

        switch (filter) {
            case FilterMethods.RAM_LAK:
                // TODO implemtar filtro rampa
                break;
            case FilterMethods.SHEPP_LOGAN:
                H = FilterMethods.shepp_logan(H, w, d);
                break;
            case FilterMethods.COSINE:
                H = FilterMethods.cosine(H, w, d);
                break;
            case FilterMethods.HAMMING:
                H = FilterMethods.hamming(H, w, d);
                break;
            case FilterMethods.HANN:
                H = FilterMethods.hann(H, w, d);
                break;
            default:
                System.out.println("Filtro inválido");
        }

        // Crop the frequency response
        MathFunc.subsFor(H, w, MathFunc.MAIOR, Math.PI * d, 0);

        // Symmetry of the filter
        H = MathFunc.addMatriz_Below(MatrixOperation.transpose(H), MatrixOperation.transpose(MathFunc.subMatriz(1, H[0].length - 2, H)));
        
        return H;
    }

    public double[][] getFilterMatrix(){
        return this.FilterMatrix;
    }
}
