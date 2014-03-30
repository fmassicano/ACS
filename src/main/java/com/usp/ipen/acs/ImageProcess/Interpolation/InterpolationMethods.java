/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.usp.ipen.acs.ImageProcess.Interpolation;

// TODO Métodos em inglês
import com.usp.ipen.acs.Code.HelpMethods;
import com.usp.ipen.acs.ImageProcess.Mat.MathFunc;
import com.usp.ipen.acs.ImageProcess.Mat.MatrixOperation;

/**
 *
 * @author fmassicano
 */
public final class InterpolationMethods {

    public static final int LINEAR = 0;
    public static final int NEAREST_NEIGHBOR = 1;
    public static final int SPLINE = 2;
    public static final int PCHIP = 3;
    public static final int CUBIC = 4;
    public static final int V5CUBIC = 5;
    public static final int LINEAR_IRADON = 6;
    public static final int SPLINE_IRADON = 7;
    public static final int PCHIP_IRADON = 8;
    public static final int CUBIC_IRADON = 9;
    public static final int V5CUBIC_IRADON = 10;

    /**
     * Não permitir instanciar esta Classe
     */
    private InterpolationMethods() {
    }

    /**
     * Efetua a interpolação Linear
     * TODO descrever parâmetros
     */
    public static double[][] linear(double[] x, double[][] Y, double[] xi) {

        double[][] InterpMatrix = new double[xi.length][Y[0].length];

        // TODO modifiquei f = 0
        double f; // fator de proporcionalidade

        // TODO modifiquei interval = 0
        int interval;

        for (int i = 0; i < xi.length; i++) {

            if (xi[i] < x[0] || xi[i] > x[x.length - 1]) {
                continue;
            }

            if (xi[i] == x[0]) {
                System.arraycopy(Y[0], 0, InterpMatrix[0], 0, Y[0].length);
            } else {
                interval = MathFunc.checkRange(x, xi[i]);

                if (xi[i] == x[interval + 1]) {
                    System.arraycopy(Y[interval + 1], 0, InterpMatrix[i], 0, Y[0].length);
                } else {

                    f = (xi[i] - x[interval]) / (x[interval + 1] - x[interval]);

                    for (int c = 0; c < Y[0].length; c++) {
                        InterpMatrix[i][c] = (Y[interval][c] + Y[interval + 1][c]) * f;
                    }
                }

            }
        }

        return InterpMatrix;
    }

    public static double[][] spline(double[] x, double[][] Y, double[] xi) {
        // TODO implementar spline
        
        if(!(Y.length==1)){
            // vector
            // Transformar em matriz ao finalizar
        }else{
            // matrix (Provavelmente terá que manipular a metodologia anterior)
        }
        
        return Y;
    }

    public static double[][] pchip(double[] x, double[][] Y, double[] xi) {
        // TODO implementar pchip
        
        if(!(Y.length==1)){
            // vector
            // Transformar em matriz ao finalizar
        }else{
            // matrix (Provavelmente terá que manipular a metodologia anterior)
        }
        
        return Y;
    }

    public static double[][] cubic(double[] x, double[][] Y, double[] xi) {
        // TODO implementar cubic
        
        if(!(Y.length==1)){
            // vector
            // Transformar em matriz ao finalizar
        }else{
            // matrix (Provavelmente terá que manipular a metodologia anterior)
        }
        
        return Y;
    }

    public static double[][] v5cubic(double[] x, double[][] Y, double[] xi) {
        // TODO implementar v5cubic
        
        if(!(Y.length==1)){
            // vector
            // Transformar em matriz ao finalizar
        }else{
            // matrix (Provavelmente terá que manipular a metodologia anterior)
        }
        
        return Y;
    }
    

    /*
     * Métodos específicos do IRADON
     */
    /**
     * nearest_neighbor <br/>
     *
     * Aplica o método de interpolação: Nearest-Neighbor<br/>
     *
     * @param p_radon Matriz que será interpolada
     * @param x_radon Matriz referente ao eixo x
     * @param y_radon Matriz referente ao eixo y
     * @param theta_radon Vetor contendo os ângulos
     * @param N Tamanho desejado da imagem final
     * @param ctrIdx Índice central da imagem
     *
     * @return Matriz Interpolada pelo método de Nearest-Neighbor
     * @author fmassicano
     * @since 2012-01-23
     */
    public static double[][] nearest_neighbor(double[][] p_radon, double[][] x_radon, double[][] y_radon, double[] theta_radon, int N, int ctrIdx) {

        double[] costheta = MathFunc.costheta(theta_radon);
        double[] sintheta = MathFunc.sintheta(theta_radon);

        double[][] image = new double[N][N];

        // TODO modifiquei = new double[N][N]
        double[][] t;

        for (int i = 0; i < theta_radon.length; i++) {
            t = MatrixOperation.plus(MatrixOperation.times(x_radon, costheta[i]), MatrixOperation.times(y_radon, sintheta[i]));
            MathFunc.round(t);
            image = MatrixOperation.plus(image, MathFunc.getMatrixIdx(MathFunc.getColumn(p_radon, i), MatrixOperation.plus(t, ctrIdx)));
        }

        image = MatrixOperation.divide(MatrixOperation.times(image, Math.PI), 2 * theta_radon.length);
        
        return image;
    }

    /**
     * linear_iradon <br/>
     *
     * Aplica o método de interpolação: Linear<br/>
     *
     * @param p_radon Matriz que será interpolada
     * @param x_radon Matriz referente ao eixo x
     * @param y_radon Matriz referente ao eixo y
     * @param theta_radon Vetor contendo os ângulos
     * @param N Tamanho desejado da imagem final
     * @param ctrIdx Índice central da imagem
     *
     * @return Matriz Interpolada pelo método Linear
     * @author fmassicano
     * @since 2012-01-23
     */
    public static double[][] linear_iradon(double[][] p_radon, double[][] x_radon, double[][] y_radon, double[] theta_radon, int N, int ctrIdx) {
        double[] costheta = MathFunc.costheta(theta_radon);
        double[] sintheta = MathFunc.sintheta(theta_radon);

        double[][] image = new double[N][N];

        double[][] t;
        double[][] a;
        
        for (int i = 0; i < theta_radon.length; i++) {
            
            t = MatrixOperation.plus(MatrixOperation.times(x_radon, costheta[i]), MatrixOperation.times(y_radon, sintheta[i]));
            a = MathFunc.floor(t);
            
            image = MatrixOperation.plus(
                    MatrixOperation.plus(
                    image,
                    MathFunc.multPoint(
                    MatrixOperation.minus(t, a),
                    MathFunc.getMatrixIdx(MathFunc.getColumn(p_radon, i), MatrixOperation.plus(a, ctrIdx)))),
                    MathFunc.multPoint(
                    MatrixOperation.minus(MatrixOperation.plus(a, 1), t),
                    MathFunc.getMatrixIdx(MathFunc.getColumn(p_radon, i), MatrixOperation.plus(MatrixOperation.minus(a,1.0), ctrIdx))));
        }
        
        image = MatrixOperation.divide(MatrixOperation.times(image, Math.PI), 2 * theta_radon.length);
        
        return image;

    }

    /**
     * spcv_iradon <br/>
     *
     * Aplica os métodos de interpolação: Spline,Pchip,Cubic, V5cubic<br/>
     * 
     * Estes metodos foram implentados nessa mesma Classe porém não especificamente
     * para transformada iversa de Radon (IRADON)<br/>
     *
     * @param p_radon Matriz que será interpolada
     * @param x_radon Matriz referente ao eixo x
     * @param y_radon Matriz referente ao eixo y
     * @param theta_radon Vetor contendo os ângulos
     * @param N Tamanho desejado da imagem final
     * @param ctrIdx Índice central da imagem
     * @param interp_method Método de interpolação requisitado
     * 
     * @return Matriz Interpolada
     * @author fmassicano
     * @since 2012-01-23
     */
    public static double[][] spcv_iradon(double[][] p_radon, double[][] x_radon, double[][] y_radon, double[] theta_radon, int N, int ctrIdx, int interp_method) {

        /*
         * TODO Fazer interpolação: 'spline','pchip','cubic','v5cubic' Montar
         * primeiramente o esqueleto. Depois implementar em interp1 para
         * 'spline','pchip','cubic','v5cubic'
         * fazer função reshape do matlab e colcocar em MatrixOperation
         */

        double[] costheta = MathFunc.costheta(theta_radon);
        double[] sintheta = MathFunc.sintheta(theta_radon);

        double[] taxis;
        double[][] t;
        double[] projContrib;

        
        // TODO verificar se isto funciona - SPCV_IRADON
        Interpolation it = new Interpolation();
        
        for (int i = 0; i < theta_radon.length; i++) {

            taxis = MatrixOperation.vectorize_Uni(MatrixOperation.minus(MathFunc.colon(1, MathFunc.size(p_radon)[0]), ctrIdx));
                    
            t = MatrixOperation.plus(MatrixOperation.times(x_radon, costheta[i]), MatrixOperation.times(y_radon, sintheta[i]));

            switch (interp_method) {
                
                case InterpolationMethods.SPLINE_IRADON:
                    it = new Interpolation(taxis, 
                            MatrixOperation.vectorTomatrix(MathFunc.getColumn(p_radon, i)), 
                            MatrixOperation.vectorize_Uni(t), InterpolationMethods.SPLINE);
                    break;
                case InterpolationMethods.PCHIP_IRADON:
                    it = new Interpolation(taxis, 
                            MatrixOperation.vectorTomatrix(MathFunc.getColumn(p_radon, i)), 
                            MatrixOperation.vectorize_Uni(t), InterpolationMethods.PCHIP);
                    break;
                case InterpolationMethods.CUBIC_IRADON:
                    it = new Interpolation(taxis, 
                            MatrixOperation.vectorTomatrix(MathFunc.getColumn(p_radon, i)), 
                            MatrixOperation.vectorize_Uni(t), InterpolationMethods.CUBIC);
                    break;
                case InterpolationMethods.V5CUBIC_IRADON:
                    it = new Interpolation(taxis, 
                            MatrixOperation.vectorTomatrix(MathFunc.getColumn(p_radon, i)), 
                            MatrixOperation.vectorize_Uni(t), InterpolationMethods.V5CUBIC);
                    break;
                default:
                    System.out.println("Método não esta válido");
                    break;

            }
            
            projContrib  = MatrixOperation.vectorize_Uni(it.getInterpMatrix());
            

        }

//
//        interp_method = sprintf('*%s',interp); % Add asterisk to assert
//                                           % even-spacing of taxis
//    
//        for i=1:length(theta)
//        proj = p(:,i);
//        taxis = (1:size(p,1)) - ctrIdx;
//        t = x.*costheta(i) + y.*sintheta(i);
//        projContrib = interp1(taxis,proj,t(:),interp_method);
//        img = img + reshape(projContrib,N,N);

        return p_radon;
    }
    
    public static void main(String[] args) {
        
        System.out.println(" colon ");
        
        HelpMethods.show(MathFunc.colon(1.0, 11.0));
        
        System.out.println(" minus 2 ");
        
        HelpMethods.show(MatrixOperation.minus(MathFunc.colon(1, 10), 2));
        
        System.out.println(" vector ");
        
        HelpMethods.show(MatrixOperation.vectorize_Uni(MatrixOperation.minus(MathFunc.colon(1, 93), 32)));
        
    }

}
