package com.usp.ipen.acs.ImageProcess.Transformations;

import com.usp.ipen.acs.Code.HelpMethods;
import com.usp.ipen.acs.ImageProcess.Mat.Complex;
import com.usp.ipen.acs.ImageProcess.Mat.MathFunc;

/**
 * 
 * @author Felipe
 */
final class FFT {

    private FFT() { 
    }

    /**
     * fft: in-place radix-2 DIT DFT of a complex input 
     * @param x double array of length n with real part of data
     * @param y double array of length n with imag part of data
     */
    private static void fft(double[] x, double[] y) {

        int n = x.length;

        int m = (int) (Math.log(n) / Math.log(2)); // m: n = 2**m , portanto m = (int) log2(n);

        // Make sure n is a power of 2
        if (n != (1 << m)) {
            throw new RuntimeException("FFT length must be power of 2");
        }

        // Lookup tables.  Only need to recompute when size of FFT changes.
        // precompute tables
        double[] cos = new double[n / 2];
        double[] sin = new double[n / 2];

//      for(int i=0; i<n/4; i++) {
//        cos[i] = Math.cos(-2*Math.PI*i/n);
//        sin[n/4-i] = cos[i];
//        cos[n/2-i] = -cos[i];
//        sin[n/4+i] = cos[i];
//        cos[n/2+i] = -cos[i];
//        sin[n*3/4-i] = -cos[i];
//        cos[n-i]   = cos[i];
//        sin[n*3/4+i] = -cos[i];        
//      }

        for (int i = 0; i < n / 2; i++) {
            cos[i] = Math.cos(-2 * Math.PI * i / n);
            sin[i] = Math.sin(-2 * Math.PI * i / n);
        }

        int i, j, k, n1, n2, a;
        double c, s, e, t1, t2;


        // Bit-reverse
        j = 0;
        n2 = n / 2;
        for (i = 1; i < n - 1; i++) {
            n1 = n2;
            while (j >= n1) {
                j = j - n1;
                n1 = n1 / 2;
            }
            j = j + n1;

            if (i < j) {
                t1 = x[i];
                x[i] = x[j];
                x[j] = t1;
                t1 = y[i];
                y[i] = y[j];
                y[j] = t1;
            }
        }

        // FFT
        n1 = 0;
        n2 = 1;

        for (i = 0; i < m; i++) {
            n1 = n2;
            n2 = n2 + n2;
            a = 0;

            for (j = 0; j < n1; j++) {
                c = cos[a];
                s = sin[a];
                a += 1 << (m - i - 1);

                for (k = j; k < n; k = k + n2) {
                    t1 = c * x[k + n1] - s * y[k + n1];
                    t2 = s * x[k + n1] + c * y[k + n1];
                    x[k + n1] = x[k] - t1;
                    y[k + n1] = y[k] - t2;
                    x[k] = x[k] + t1;
                    y[k] = y[k] + t2;
                }
            }
        }
    }

    /**
     * 
     * @param x Matriz real
     * @param y Matriz imaginária
     */
    public static void fft(double[][] x, double[][] y) {

        double[] xi = new double[x.length];
        double[] yi = new double[y.length];

        for (int j = 0; j < x[0].length; j++) {
            xi = MathFunc.getColumn(x, j);
            yi = MathFunc.getColumn(y, j);
            fft(xi, yi);
            MathFunc.setColumn(x, xi, j);
            MathFunc.setColumn(y, yi, j);
        }

    }
    
    /**
     * fft<br/>
     * 
     * Code from site: http://introcs.cs.princeton.edu/java/97data/FFT.java.html<a/><br/>
     * 
     * compute the FFT of x[], assuming its length is a power of 2<br/>
     * 
     * @param x vector Complex
     * @return FFT
     */
    public static Complex[] fft(Complex[] x) {
        int N = x.length;

        // base case
        if (N == 1) return new Complex[] { x[0] };

        // radix 2 Cooley-Tukey FFT
        if (N % 2 != 0) { throw new RuntimeException("N is not a power of 2"); }

        // fft of even terms
        Complex[] even = new Complex[N/2];
        for (int k = 0; k < N/2; k++) {
            even[k] = x[2*k];
        }
        Complex[] q = fft(even);

        // fft of odd terms
        Complex[] odd  = even;  // reuse the array
        for (int k = 0; k < N/2; k++) {
            odd[k] = x[2*k + 1];
        }
        Complex[] r = fft(odd);

        // combine
        Complex[] y = new Complex[N];
        for (int k = 0; k < N/2; k++) {
            double kth = -2 * k * Math.PI / N;
            Complex wk = new Complex(Math.cos(kth), Math.sin(kth));
            y[k]       = q[k].plus(wk.times(r[k]));
            y[k + N/2] = q[k].minus(wk.times(r[k]));
        }
        return y;
    }
    
    /**
     * fft<br/>
     * 
     * Code from site: http://introcs.cs.princeton.edu/java/97data/FFT.java.html<a/><br/>
     * 
     * compute the inverse FFT of x[], assuming its length is a power of 2<br/>
     * 
     * @param x vector Complex
     * @return inverse FFT
     */
    public static Complex[] ifft(Complex[] x) {
        int N = x.length;
        Complex[] y = new Complex[N];

        // take conjugate
        for (int i = 0; i < N; i++) {
            y[i] = x[i].conjugate();
        }

        // compute forward FFT
        y = fft(y);

        // take conjugate again
        for (int i = 0; i < N; i++) {
            y[i] = y[i].conjugate();
        }

        // divide by N
        for (int i = 0; i < N; i++) {
            y[i] = y[i].times(1.0 / N);
        }

        return y;

    }

    
    /**
     * Compute the FFT of x[][], assuming its length is a power of 2
     * 
     * @param x
     * @return 
     */
    public static Complex[][] fft(Complex[][] x){
        
        Complex[][] y = new Complex[x.length][x[0].length];
        
        for(int j = 0; j < x[0].length; j++){
            MathFunc.setColumn(y, fft(MathFunc.getColumn(x, j)), j);
        }
        
        return y;
    }
    
    /**
     * Compute the inverse FFT of x[][], assuming its length is a power of 2
     * 
     * @param x
     * @return 
     */
    public static Complex[][] ifft(Complex[][] x){
        Complex[][] y = new Complex[x.length][x[0].length];
        
        for(int j = 0; j < x[0].length; j++){
            MathFunc.setColumn(y, ifft(MathFunc.getColumn(x, j)), j);
        }
        
        return y;
    }
    
    // Test the FFT to make sure it's working
    public static void main(String[] args) {
        
        Complex[][] x = new Complex[4][4];

        x[0][0] = new Complex(0, 0); 
        x[0][1] = new Complex(0, 0); 
        x[0][2] = new Complex(0, 0); 
        x[0][3] = new Complex(0, 0); 

        x[1][0] = new Complex(1, 0); 
        x[1][1] = new Complex(2, 0); 
        x[1][2] = new Complex(3, 0); 
        x[1][3] = new Complex(4, 0);

        x[2][0] = new Complex(0, 0); 
        x[2][1] = new Complex(0, 0); 
        x[2][2] = new Complex(0, 0); 
        x[2][3] = new Complex(0, 0);
        
        x[3][0] = new Complex(0, 0); 
        x[3][1] = new Complex(0, 0); 
        x[3][2] = new Complex(0, 0); 
        x[3][3] = new Complex(0, 0);
        
        
        Complex[][] c = fft(x);
       
        HelpMethods.show(c);
        
        System.out.println("");
        
        HelpMethods.show(ifft(c));
        
    }
}
