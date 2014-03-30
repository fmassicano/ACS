package com.usp.ipen.acs.ImageProcess.Transformations;

// TODO Métodos em inglês

import com.usp.ipen.acs.Code.HelpMethods;
import com.usp.ipen.acs.ImageProcess.Filters.Filter;
import com.usp.ipen.acs.ImageProcess.Interpolation.Interpolation;
import com.usp.ipen.acs.ImageProcess.Interpolation.InterpolationMethods;
import com.usp.ipen.acs.ImageProcess.Mat.MathFunc;
import com.usp.ipen.acs.ImageProcess.Mat.MatrixOperation;


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author fmassicano
 */
final class RADON {

    /**
     * Não permitir instanciar esta Classe
     */
    private RADON() {
    }

    /**
     * RADON Compute RADON transform.
     * The RADON function computes the RADON transform, which is the
     * projection of the image intensity along a radial line
     * oriented at a specific angle.
     * 
     * R = RADON(I,THETA,N) returns the RADON transform of the
     * intensity image I for the angle THETA degrees. If THETA is a
     * scalar, the result R is a column vector containing the RADON
     * transform for THETA degrees. If THETA is a vector, then R is
     * a matrix in which each column is the RADON transform for one
     * of the angles in THETA.
     * 
     * R = RADON(I,THETA,N) returns a RADON transform with the
     * projection computed at N points. R has N rows. 
     * 
     * [R,Xp] = RADON(...) returns a vector Xp containing the radial
     * coordinates corresponding to each row of R.
     */
    public static double[][] radon(double[][] I, double[] theta, int Nb) {

        int m = I.length;    // Linhas
        int n = I[0].length; // Colunas

        // centro da imagem
        int xc = (int) Math.floor((m + 1) / 2);
        int yc = (int) Math.floor((n + 1) / 2);

        // divide cada pixels numa matriz 2X2 de subpixels
        double[][] d = subpixel2X2(I, m, n);

        // metade da metade da diagonal. 
        int b = (int) Math.ceil((Math.sqrt(Math.pow(Math.floor(m / 2), 2) + Math.pow(Math.floor(n / 2), 2)))) + 1;

        double[][] xp = obtainXP(b);

        int[] sz = MathFunc.size(xp);

        double[][] X = obtainX(m, n, xc);

        double[][] Y = obtainY(m, n, yc);

        double[][] D = MatrixOperation.vectorize_Bi(d);

        double[] th = obtainThetaRad(theta);

        double[][] R = new double[sz[0]][theta.length];

        double[][] Xp ;//= new double[1][4 * m * n]; TODO verificar funcionamento
        double[][] ip ;//= new double[1][4 * m * n];
        double[][] sum = MathFunc.matrix_n(1, 4 * m * n, b + 1);
        double[][] k ;//= new double[1][4 * m * n];
        double[][] one = MathFunc.matrix_n(1, 4 * m * n, 1);

        for (int i = 0; i < theta.length; i++) {

            Xp = MatrixOperation.plus(MatrixOperation.times(X, -Math.sin(th[i])),
                    MatrixOperation.times(Y, Math.cos(th[i])));

            ip = MatrixOperation.plus(Xp, sum);

            k = obtainK(ip);
            fillMatrixR(R,
                    MatrixOperation.plus(
                    MathFunc.accumarray(k, MathFunc.multPoint(D, MatrixOperation.minus(one, MatrixOperation.minus(ip, k))), sz),
                    MathFunc.accumarray(MatrixOperation.plus(k, one), MathFunc.multPoint(D, MatrixOperation.minus(ip, k)), sz)),
                    i);

        }

        if (MathFunc.size(R)[0] != Nb) {

            double[] new_xp = MathFunc.linspace(MathFunc.min(xp), MathFunc.max(xp), Nb);

            Interpolation interp = new Interpolation(MatrixOperation.vectorize_Uni(xp), R, new_xp, InterpolationMethods.LINEAR);

            R = interp.getInterpMatrix();

            R = MatrixOperation.times(R, xp.length * 1.0 / new_xp.length);

        }

        return R;
    }

    /**
     * <p align="justify" >  
     *   IRADON Inverse RADON transform. <br/>
     *
     *   IRADON uses the filtered backprojection algorithm to perform the inverse
     *   RADON transform.  The filter is designed directly in the frequency
     *   domain and then multiplied by the FFT of the projections.  The
     *   projections are zero-padded to a power of 2 before filtering to prevent
     *   spatial domain aliasing and to speed up the FFT.<br/><br/>
     *
     *   <p>I = IRADON(R,THETA,INTERPOLATION,FILTER,FREQUENCY_SCALING,OUTPUT_SIZE)</p>
     *   specifies parameters to use in the inverse RADON transform.  You can
     *   specify any combination of the last four arguments.  IRADON uses default
     *   values for any of these arguments that you omit.<br/><br/>
     *
     *   INTERPOLATION specifies the type of interpolation to use in the
     *   backprojection. The default is linear interpolation. Available methods
     *   are:<br/><br/>
     *
     *      'nearest' - nearest neighbor interpolation<br/>
     *      'linear'  - linear interpolation (default)<br/>
     *      'spline'  - spline interpolation<br/>
     *      'pchip'   - shape-preserving piecewise cubic interpolation<br/>
     *      'cubic'   - same as 'pchip'<br/>
     *      'v5cubic' - the cubic interpolation from MATLAB 5, which does not 
     *                  extrapolate and uses 'spline' if X is not equally spaced <br/>
     *  <br/>
     *   FILTER specifies the filter to use for frequency domain filtering.<br/>
     *   FILTER is a string that specifies any of the following standard filters:<br/><br/>
     *
     *   'Ram-Lak'     The cropped Ram-Lak or ramp filter (default).  The
     *                 frequency response of this filter is |f|.  Because this
     *                 filter is sensitive to noise in the projections, one of
     *                 the filters listed below may be preferable.<br/>
     *   'Shepp-Logan' The Shepp-Logan filter multiplies the Ram-Lak filter by
     *                 a sinc function.<br/>
     *   'Cosine'      The cosine filter multiplies the Ram-Lak filter by a
     *                 cosine function.<br/>
     *   'Hamming'     The Hamming filter multiplies the Ram-Lak filter by a
     *                 Hamming window.<br/>
     *   'Hann'        The Hann filter multiplies the Ram-Lak filter by a
     *                 Hann window.<br/>
     *   'none'        No filtering is performed.<br/><br/>
     *
     *   FREQUENCY_SCALING is a scalar in the range (0,1] that modifies the
     *   filter by rescaling its frequency axis.  The default is 1.  If
     *   FREQUENCY_SCALING is less than 1, the filter is compressed to fit into
     *   the frequency range [0,FREQUENCY_SCALING], in normalized frequencies;
     *   all frequencies above FREQUENCY_SCALING are set to 0.<br/>
     *
     *   OUTPUT_SIZE is a scalar that specifies the number of rows and columns in
     *   the reconstructed image.  If OUTPUT_SIZE is not specified, the size is
     *   determined from the length of the projections:<br/><br/>
     *
     *       OUTPUT_SIZE = 2*floor(size(R,1)/(2*sqrt(2)))<br/><br/>
     *
     *   If you specify OUTPUT_SIZE, IRADON reconstructs a smaller or larger
     *   portion of the image, but does not change the scaling of the data.<br/>
     *
     *   If the projections were calculated with the RADON function, the
     *   reconstructed image may not be the same size as the original image.<br/>
     *
     *   [I,H] = iradon(...) returns the frequency response of the filter in the
     *   vector H.<br/><br/>
     *
     *   Copyright 1993-2009 The MathWorks, Inc.<br/>
     *   $Revision: 1.13.4.9 $  $Date: 2009/08/11 15:40:57 $<br/>
     *
     *   References:<br/>
     *     A. C. Kak, Malcolm Slaney, "Principles of Computerized Tomographic
     *     Imaging", IEEE Press 1988.<br/>
     * </p>
     * @param R Projection data
     * @param theta the angles at which the projections were taken
     * @param interp the type of interpolation to use
     * @param filter specifying filter or the actual filter
     * @param scaling a scalar specifying normalized freq. at which to crop the frequency response of the filter
     * @param output_size The size of the reconstructed image
     */
    public static double[][] iradon(double[][] R, double[] theta, int interp, int filter, double scaling, int output_size) {

        if (theta.length != R[0].length) {
            throw new RuntimeException("THETA does not match the number of projections.");
//            return MathFunc.matrix_n(output_size, output_size, 0.0);
        }

        double[] th = obtainThetaRad(theta);

        if (output_size == 0) {
            output_size = (int) (2 * Math.floor(R.length / (2 * Math.sqrt(2))));  // This doesn't always jive with RADON
        }

        double[][] I = new double[output_size][output_size];

        Filter f = new Filter(R, filter, scaling);
        I = f.getFilterMatrix();
        
        double[][] x = MathFunc.mirror(output_size);
        x = MathFunc.repmat(x, output_size, 1);

        double[][] y = MathFunc.mirror(output_size);
        MathFunc.changeSignal(y);
        y = MathFunc.repmat(MatrixOperation.transpose(y), 1, output_size);
        
        int len = MathFunc.size(I)[0];
        int ctrIdx = (int) Math.ceil(len / 2); //index of the center of the projections
        
        double imgDiag = 2 * Math.ceil(output_size * 1.0 / Math.sqrt(2)) + 1.0;
        
        if (len < imgDiag) {
            double rz = imgDiag - len; // how many rows of the zeros
            I = MathFunc.addMatriz_Below(MathFunc.addMatriz_Below(new double[(int) (Math.ceil(rz / 2))][I[0].length], I),
                    new double[(int) (Math.floor(rz / 2))][I[0].length]);
            ctrIdx = ctrIdx + (int) Math.ceil(rz / 2);
        }
        
        Interpolation it = new Interpolation(I, x, y, th, interp, output_size, ctrIdx);
        I = it.getInterpMatrix();
        
        return I;
    }

    /* 
     * Métodos específicos para esta classe.
     * Estes métodos deverão ser todos privados e estáticos.
     */
    /**
     * Métodos específicos para o método
     * estático RADON
     */
    /**
     * 
     * Subdivide um pixel em quatro.
     * 
     * @param I imagem original
     * @param m número de linhas
     * @param n número de colunas
     * @return vetor bidimensional com os pixels quaduplicados.
     */
    private static double[][] subpixel2X2(double[][] I, int m, int n) {

        double[][] d = new double[2 * m][2 * n];

        int ii = 0;
        int jj = 0;

        for (int i = 0; i < d.length; i = i + 2) {
            for (int j = 0; j < d[0].length; j = j + 2) {
                d[i][j] = I[ii][jj] / 4;
                d[i][j + 1] = I[ii][jj] / 4;
                d[i + 1][j] = I[ii][jj] / 4;
                d[i + 1][j + 1] = I[ii][jj] / 4;
                jj++;
            }
            jj = 0;
            ii++;
        }

        return d;

    }

    /**
     * 
     * Obtêm a mtriz XP
     * 
     * a = -n -n+1 -n+2 ... -n + 2n 
     * 
     * @param b limite
     * @return a
     */
    private static double[][] obtainXP(int b) {
        double[] mxp = new double[2 * b + 1];

        for (int i = 0; i < mxp.length; i++) {
            mxp[i] = -b + i;
        }

        return MatrixOperation.transpose(mxp);
    }

    /**
     * 
     * Obtêm a matrix X
     * 
     * @param m linhas
     * @param n colunas
     * @param xc centro da imagem
     * @return matriz X
     */
    private static double[][] obtainX(int m, int n, int xc) {

        double[][] x = new double[2 * m][2 * n];

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

        return MatrixOperation.vectorize_Bi(x);
    }

    /**
     * 
     * Obtêm a matrix Y
     * 
     * @param m linhas
     * @param n colunas
     * @param yc centro da imagem
     * @return matriz Y
     */
    private static double[][] obtainY(int m, int n, int yc) {

        double[][] y = new double[2 * m][2 * n];

        int k = 0;
        int cont = 0;
        for (int j = 0; j < y[0].length; j++) {
            for (int i = 0; i < y.length; i++) {
                y[i][j] = 0.75 - yc + (k / 2.0);
                if (cont == (2 * m - 1)) {
                    k++;
                    cont = 0;
                } else {
                    cont++;
                }
            }
        }

        return MatrixOperation.vectorize_Bi(y);
    }

    /**
     * 
     * Dado um vetor contendo angulos em graus
     * transforma-o em radianos.
     * 
     * @param theta ângulos em graus
     * @return ângulos em radianos
     */
    private static double[] obtainThetaRad(double[] theta) {
        double[] th = theta;

        for (int i = 0; i < th.length; i++) {
            th[i] = th[i] * Math.PI / 180;
        }

        return th;
    }

    /**
     * Obtêm a Matriz 'k'
     * @param ip
     * @return 
     */
    private static double[][] obtainK(double[][] ip) {

        double[][] k = new double[ip.length][ip[0].length];

        for (int i = 0; i < k[0].length; i++) {
            k[0][i] = Math.floor(ip[0][i]);
        }

        return k;
    }

    /**
     * Preenche a Matriz da transforma de RADON.
     * 
     * @param R Matriz da transformada
     * @param ac vetor que será incorporado a Matriz
     * @param l localização da coluna na matriz
     */
    private static void fillMatrixR(double[][] R, double[][] ac, int l) {
        for (int i = 0; i < R.length; i++) {
            R[i][l] = ac[i][0];
        }
    }
    
}
