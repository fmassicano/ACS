package com.usp.ipen.acs.ImageProcess.Mat;

// TODO Métodos em inglês

import com.usp.ipen.acs.Code.HelpMethods;


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *  Esta Classe será utilizada para conter
 *  funções que ajudem no código como um todo.
 *  
 *  Todos os métodos dele devem ser estáticos. 
 * 
 * @author fmassicano
 */
public final class MathFunc {

    public static final int IGUAL = 1;
    public static final int MENOR = 2;
    public static final int MAIOR = 3;
    public static final int MENOR_IGUAL = 4;
    public static final int MAIOR_IGUAL = 5;

    /**
     * Adquiri a largura e altura da matriz de double
     * @param a Matriz de double
     * @return um vetor de inteiros o qual a primeira posição é a 
     * larura e a segunda posição é a altura
     */
    public static double[] dsize(double[][] a) {
        double[] s = new double[2];
        s[0] = a.length;
        s[1] = a[0].length;

        return s;
    }

    /**
     * Adquiri a largura e altura da matriz de double
     * @param a Matriz de double
     * @return um vetor de inteiros o qual a primeira posição é a 
     * larura e a segunda posição é a altura
     */
    public static int[] size(double[][] a) {
        int[] s = new int[2];
        s[0] = a.length;
        s[1] = a[0].length;

        return s;
    }

    /**
     * Adquiri a largura e altura da matriz de inteiros
     * @param a Matriz de int
     * @return um vetor de inteiros o qual a primeira posição é a 
     * larura e a segunda posição é a altura
     */
    public static int[] size(int[][] a) {
        int[] s = new int[2];
        s[0] = a.length;
        s[1] = a[0].length;

        return s;
    }

    /**
     * Adquiri a largura e altura da matriz de inteiros
     * @param a Matriz de int
     * @return um vetor de inteiros o qual a primeira posição é a 
     * larura e a segunda posição é a altura
     */
    public static int[] size(double[] a) {
        int[] s = new int[2];
        s[0] = 1;
        s[1] = a.length;

        return s;
    }

    /**
     * Preenche um vetor double de Zeros (0)
     * @param a
     * @return vetor preenchido de zeros
     */
    public static double[] zeros(double[] a) {
        for (int i = 0; i < a.length; i++) {
            a[i] = 0;
        }
        return a;
    }

    /**
     * Preenche uma Matriz double de Zeros (0)
     * @param a
     * @return Matriz preenchido de zeros
     */
    public static double[][] zeros(double[][] a) {
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                a[i][j] = 1;
            }
        }
        return a;
    }

    /**
     * Preenche um Vetor double com o valor de 'n'.
     * @param a vetor
     * @param n valor a ser preenchido
     * @return Vetor preenchido de 'n'
     */
    public static double[] matrix_n(int m, double n) {
        double[] a = new double[m];
        for (int i = 0; i < a.length; i++) {
            a[i] = n;
        }
        return a;
    }

    /**
     * Preenche um Vetor double com o valor de 'n'.
     * @param a vetor
     * @param n valor a ser preenchido
     * @return Vetor preenchido de 'n'
     */
    public static double[] matrix_n(double[] a, double n) {
        for (int i = 0; i < a.length; i++) {
            a[i] = n;
        }
        return a;
    }

    /**
     * Preenche uma Matriz double com o valor de 'n'.
     * @param a matriz
     * @param n valor a ser preenchido
     * @return Matriz preenchida de 'n'
     */
    public static double[][] matrix_n(double[][] a, double n) {
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                a[i][j] = n;
            }
        }
        return a;
    }

    /**
     * Preenche uma Matriz double com o valor de 'n'.
     * @param a Matriz
     * @param b valor a ser preenchido
     * @return Matriz preenchida de 'n'
     */
    public static double[][] matrix_n(int m, int n, double b) {
        double[][] a = new double[m][n];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                a[i][j] = b;
            }
        }
        return a;
    }

    /**
     * Localiza o intervalo onde o valor b 
     * se encontra dentro do array a
     * 
     * 0 - 0 - 1; 1 - 1 - 2; 2 - 2 - 3; ... ;
     * 
     * @param a
     * @param b
     * @return 0 à (a.length - 1) (intervalos)
     */
    public static int checkRange(double[] a, double b) {
        int interval = 0;
        for (int n = 0; n < a.length; n++) {
            if (a[n] < b && b <= a[n + 1]) {
                interval = n;
                break;
            }
        }
        return interval;
    }

    /**
     * 
     * Subdivide um vetor em 'n' espaçamentos iguais. Coloca
     * este vetor em ordem crescente.
     * 
     * @param max menor elemento do vetor
     * @param max maior elemento do vetor
     * @param n número de intervalos
     * @return vetor subdivido
     */
    public static double[] linspace(double min, double max, int n) {

        double[] a = new double[n];

        if (n == 0) {
            a[0] = min;
            a[1] = max;
        } else {
            double r = (max - min) / ((n) - 1);
            a[0] = min;
            for (int i = 1; i < n; i++) {
                a[i] = a[i - 1] + r;
            }
        }

        return a;
    }

    /**
     * Concatena duas matriz
     * 
     * @param a 
     * @param b
     * @return matriz [a b]
     */
    public static double[][] addMatriz_Left(double[][] a, double[][] b) {

        if (a.length != b.length) {
            System.out.println("As Matrizes devem ter o mesmo número de linhas.");
            return null;
        }

        double[][] r = new double[a.length][a[0].length + b[0].length];

        for (int i = 0; i < r.length; i++) {
            for (int j = 0; j < r[0].length; j++) {
                if (j < a[0].length) {
                    r[i][j] = a[i][j];
                } else {
                    r[i][j] = b[i][j - a[0].length];
                }
            }
        }

        return r;
    }

    /**
     * Concatena duas matriz
     * 
     * @param a 
     * @param b
     * @return matriz [a;b]
     */
    public static double[][] addMatriz_Below(double[][] a, double[][] b) {

        if (a[0].length != b[0].length) {
            System.out.println("As Matrizes devem ter o mesmo número de colunas.");
            return null;
        }

        double[][] r = new double[a.length + b.length][a[0].length];

        for (int i = 0; i < r.length; i++) {
            for (int j = 0; j < r[0].length; j++) {
                if (i < a.length) {
                    r[i][j] = a[i][j];
                } else {
                    r[i][j] = b[i - a.length][j];
                }
            }
        }

        return r;
    }

    /**
     * 
     * Adquiri uma subclasse da Matriz 'a'. A matriz gerada começa no
     * 'start' e termina no índice 'end'.
     * <br></br>
     * <br><b>Obs: O vetor começa em 0 e termina em length - 1 </b></br>
     * 
     * @param start índice inicial
     * @param end índice final
     * @param a Matriz
     * @return Matriz linha contendo os valores da Matriz 'a' do ponto inicial ao final.
     */
    public static double[][] subMatriz(int start, int end, double[][] a) {

        if (start > end) {
            System.out.println("Start deve ser menor do que End.");
            return null;
        } else if ((end > MatrixOperation.vectorize_Bi(a)[0].length) || (start < 0)) {
            System.out.println("Start e End devem ser indíces contidos na matriz de entrada.");
            return null;
        }

        double[][] c = new double[1][end - start + 1];

        for (int j = 0; j < c[0].length; j++) {
            c[0][j] = MatrixOperation.vectorize_Bi(a)[0][end - j];
        }

        return c;

    }

    /**
     * Adquiri uma sub matriz da matriz dada
     * @param xi ponto inicial (linha)
     * @param xf ponto final (linha)
     * @param yi ponto inicial (coluna)
     * @param yf ponto final (coluna)
     * @param a Matriz dada
     * @return 
     */
    public static double[][] getSubMatriz(int xi, int xf, int yi, int yf, double[][] a) {

        int y = yi;
        
        if(xi>xf || yi > yf)
        throw new RuntimeException("Problema as coordenadas: xi < xf e yi < yf.");
        
        if(xi<0 || yi<0)
        throw new RuntimeException("Problema as coordenadas: xi >= 0 e yi >= 0.");
        
        if(xf > a.length || yf > a[0].length)
        throw new RuntimeException("Problema as coordenadas: xf < a.length e yf < a[0].length.");
        
        double[][] b = new double[xf - xi + 1][yf - yi + 1];

        for(int i = 0 ; i < b.length; i++, xi++, y = yi) {
            for (int j = 0; j < b[0].length; j++ , y++) {
                b[i][j] = a[xi][y];
            }
        }
        
        return b;
    }

    /**
     *  
     * Recebe dois vetores linhas (n colunas) e constroi 
     * um vetor com 2 colunas sendo a primeira coluna
     * o primeiro vetor recebido (a) e a segunda coluna o 
     * segundo vetor recebido (b)
     * 
     * @param a primeiro vetor
     * @param b segundo vetor
     * @return vetor contendo 'a' e 'b'
     */
    public static double[][] fillColumn(double[][] a, double[][] b) {

        double[][] result = new double[a[0].length][2];

        for (int i = 0; i < a[0].length; i++) {
            result[i][0] = a[0][i];
            result[i][1] = b[0][i];
        }

        return result;
    }

    /**
     * Exemplo: 
     * vals = 3 9 0 10 5
     * sub  = 2 2 1 2  4
     * accumarray = 0  22 0 5
     * index acc  = 1  2  3 4
     * 
     * accumarray: cria um array de tamanho sz onde sz é um vetor linha de inteiros de valores não negativos.
     * 
     * @param sub  
     * @param vals 
     * @param sz
     * @return 
     */
    public static double[][] accumarray(double[][] sub, double[][] vals, int[] sz) {

        double[][] accumarray = new double[sz[0]][sz[1]];

        double[][] ident = fillColumn(vals, sub);

        for (int i = 0; i < accumarray.length; i++) {
            for (int j = 0; j < ident.length; j++) {
                if (i == ident[j][1] - 1) {
                    accumarray[i][0] = accumarray[i][0] + ident[j][0];
                }
            }
        }

        return accumarray;
    }

    /**
     * Efetua a multiplicação ponto a ponto entre as duas matrizes.
     * 
     * @param m Matriz 'm'
     * @param m1 Matriz 'm1'
     * @return Matriz que representa a mulpicação ponto a ponto entre as duas.
     */
    public static double[][] multPoint(double[][] m, double[][] m1) {

        double[][] multPoint = new double[m.length][m[0].length];

        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[0].length; j++) {
                multPoint[i][j] = m[i][j] * m1[i][j];
            }
        }

        return multPoint;
    }

    /**
     * Dado um Vetor retorna o menor valor do Vetor
     * 
     * @param r vetor
     * @return menor valor de 'r' 
     */
    public static double min(double[] r) {
        double min = r[0];
        for (int i = 0; i < r.length; i++) {
            if (min > r[i]) {
                min = r[i];
            }
        }
        return min;
    }

    /**
     * Dada uma Matriz retorna o menor valor da Matriz
     * 
     * @param r Matriz
     * @return menor valor de 'r'
     */
    public static double min(double[][] r) {
        double min = r[0][0];
        for (int i = 0; i < r.length; i++) {
            for (int j = 0; j < r[0].length; j++) {
                if (min > r[i][j]) {
                    min = r[i][j];
                }
            }
        }
        return min;
    }

    /**
     * Dado dois valores retorna o maior
     * 
     * @param a valor a
     * @param b valor b
     * @return maior valor entre a e b 
     */
    public static double max(double a, double b) {
        double max = a;
        if (b > a) {
            max = b;
        }
        return max;
    }

    /**
     * Dado um Vetor retorna o maior valor do Vetor
     * 
     * @param r vetor
     * @return maior valor de 'r' 
     */
    public static double max(double[] r) {
        double max = r[0];
        for (int i = 0; i < r.length; i++) {
            if (max < r[i]) {
                max = r[i];
            }
        }
        return max;
    }

    /**
     * Dada uma Matriz retorna o maior valor da Matriz
     * 
     * @param r Matriz
     * @return maior valor de 'r'
     */
    public static double max(double[][] r) {
        double max = r[0][0];
        for (int i = 0; i < r.length; i++) {
            for (int j = 0; j < r[0].length; j++) {
                if (max < r[i][j]) {
                    max = r[i][j];
                }
            }
        }
        return max;
    }

    /**
     * 
     * @param n
     * @return 
     */
    public static double[] log2_FE(double n) {

        if (n <= 0) {
            System.out.println("O logaritmando não pode ser menor ou igual a ZERO");
            return null;
        }

        double f = 0.5;

        double e = log2(n) - log2(f);

        if (!((e - Math.floor(e)) == 0.0)) {
            f = n / (Math.pow(2, Math.floor(e)));
            e = Math.floor(e);
        }

        double[] r = new double[2];

        r[0] = f;
        r[1] = e;

        return r;
    }

    /**
     * Calcula o Logaritmo de n na base 2
     * @param n
     * @return log2(n)
     */
    public static double log2(double n) {
        if (n <= 0) {
            System.out.println("O logaritmando não pode ser menor ou igual a ZERO");
            return n;
        }
        return Math.log10(n) / Math.log10(2);
    }

    /**
     * Next higher power of 2.
     * @param n
     * @return 
     */
    public static double nextpow2(double n) {

        double[] a = log2_FE(n);

        //  Check if n is an exact power of 2.
        if (!isEmpty(a) && a[0] == 0.5) {
            a[1] = a[1] - 1;
        }

        return a[1];
    }

    /**
     * Verifica se o vetor é null
     * @param a
     * @return true se vazio e false se preenchido
     */
    public static boolean isEmpty(double[] a) {
        if (a == null) {
            return true;
        }
        return false;
    }

    /**
     * 
     * Subdivide um vetor em 'max+1' espaçamentos iguais.<br/>
     * 
     * @param min menor elemento do vetor
     * @param max maior elemento do vetor
     * @return 
     */
    public static double[][] linspace(double min, double max) {

        int n = (int) max + 1;

        double[][] a = new double[1][n];

        if (n == 0) {
            a[0][0] = min;
            a[0][1] = max;
        } else {
            double r = (max - min) / ((n) - 1);
            a[0][0] = min;
            for (int i = 1; i < n; i++) {
                a[0][i] = a[0][i - 1] + r;
            }
        }

        return a;
    }
    
    /**
     * 
     * Subdivide um vetor conforme a expressão <b>':'</b> do Matlab.<br/>
     * 
     * Exemplo: Matlab -> (1:2:10) = 1 3 5 7 9
     * 
     * @param min menor elemento do vetor
     * @param max maior elemento do vetor
     * @return 
     */
    public static double[] colon(double min, double medio, double max){
        // TODO Implentar essa função (Somente se preocupar quando precisar)
        return new double[3];
    }
    
    /**
     * 
     * Subdivide um vetor conforme a expressão <b>':'</b> do Matlab.<br/>
     * 
     * Exemplo: Matlab -> (1:10) = 1 2 3 4 5 6 7 8 9 10
     * 
     * @param min menor elemento do vetor
     * @param max maior elemento do vetor
     * @return 
     */
    public static double[] colon(double min, double max){
       int quant_points = (int) (max - min + 1) ;
        
        double[] a = new double[quant_points];
        
        a[0] = min;
        
        for(int i = 1; i < max; i++){
            a[i] = a[i-1] + 1;
        }
        
        return a;
        
    }

    /**
     * 
     * A e B devem ter mesma dimessão
     * <br> </br>
     * <br><b>A(B>val)=numb;</b></br>
     * 
     * @param a matriz que será mudada 
     * @param b matriz que indicará a posição onde irá ocorrer a mudança na matriz 'a'
     * @param sinal simbolo de comparação 
     * @param vals valor que será comparado
     * @param numb valor que será substituido
     */
    public static void subsFor(double[][] a, double[][] b, int sinal, double vals, double numb) {

        for (int i = 0; i < b.length; i++) {
            for (int j = 0; j < b[0].length; j++) {

                if (sinal == IGUAL) {
                    if (b[i][j] == vals) {
                        a[i][j] = numb;
                    }
                } else if (sinal == MENOR) {
                    if (b[i][j] < vals) {
                        a[i][j] = numb;
                    }
                } else if (sinal == MENOR_IGUAL) {
                    if (b[i][j] <= vals) {
                        a[i][j] = numb;
                    }
                } else if (sinal == MAIOR) {
                    if (b[i][j] > vals) {
                        a[i][j] = numb;
                    }
                } else if (sinal == MAIOR_IGUAL) {
                    if (b[i][j] >= vals) {
                        a[i][j] = numb;
                    }
                }

            }
        }

    }

    /**
     * Obtem a coluna 'j' da Matriz 'a'
     * @param a Matriz
     * @param j índice da coluna
     * @return vetor contendo a coluna 'j'
     */
    public static double[] getColumn(double[][] a, int j) {

        if (j > a[0].length - 1) {
            throw new RuntimeException("A matriz não possui o índice j = " + j);
        }

        double[] column = new double[a.length];

        for (int i = 0; i < column.length; i++) {
            column[i] = a[i][j];
        }

        return column;
    }

    /**
     * Obtem a coluna 'j' da Matriz 'a'
     * @param a Matriz
     * @param j índice da coluna
     * @return vetor contendo a coluna 'j'
     */
    public static Complex[] getColumn(Complex[][] a, int j) {

        if (j > a[0].length - 1) {
            throw new RuntimeException("A matriz complexa não possui o índice j = " + j);
        }

        Complex[] column = new Complex[a.length];

        for (int i = 0; i < column.length; i++) {
            column[i] = a[i][j];
        }

        return column;
    }

    /**
     * Modifica a coluna 'j' da Matriz 'a' pelo vetor 'b'
     * @param a Matriz a 
     * @param b Vetor b
     * @param j índice da coluna da matriz 'a'
     */
    public static void setColumn(double[][] a, double[] b, int j) {

        if (j > a[0].length) {
            throw new RuntimeException("A matriz não possui o índice j = " + j);
        }

        if (b.length > a.length) {
            throw new RuntimeException("O vetor 'b' necessita ter a mesma ordem de linhas da matriz 'a'");
        }

        for (int i = 0; i < a.length; i++) {
            a[i][j] = b[i];
        }

    }

    /**
     * Modifica a coluna 'j' da Matriz 'a' pelo vetor 'b'
     * @param a Matriz a 
     * @param b Vetor b
     * @param j índice da coluna da matriz 'a'
     */
    public static void setColumn(Complex[][] a, Complex[] b, int j) {

        if (j > a[0].length) {
            throw new RuntimeException("A matriz complexa 'a' não possui o índice j = " + j);
        }

        if (b.length > a.length) {
            throw new RuntimeException("O vetor complexo 'b' necessita ter a mesma ordem de linhas da matriz complexa 'a'");
        }

        for (int i = 0; i < a.length; i++) {
            a[i][j] = b[i];
        }

    }

    /**
     * Multiplica a coluna 'j' da Matriz 'a' pelo vetor 'b'
     * @param a Matriz a 
     * @param b Vetor b
     * @param j índice da coluna da matriz 'a'
     */
    public static void timesColumnforVetor(double[][] a, double[] b, int j) {

        if (j > a[0].length) {
            throw new RuntimeException("A matriz não possui o índice j = " + j);
        }

        if (b.length > a.length) {
            throw new RuntimeException("O vetor 'b' necessita ter a mesma ordem de linhas da matriz 'a'");
        }

        for (int i = 0; i < a.length; i++) {
            a[i][j] = a[i][j] * b[i];
        }

    }

    /**
     * Multiplica a coluna 'j' da Matriz 'a' pelo vetor 'b'
     * @param a Matriz a 
     * @param b Vetor b
     * @param j índice da coluna da matriz 'a'
     */
    public static void timesColumnforVetor(double[][] a, double[][] b, int j) {


        if ((b.length * b[0].length) > a[0].length) {
            throw new RuntimeException("O tamanho da matriz 'b' necessita coincidir com as linhas da matriz 'a'");
        }

        if (j > a[0].length) {
            throw new RuntimeException("A matriz não possui o índice j = " + j);
        }

        double[] c = MatrixOperation.vectorize_Uni(b);

        for (int i = 0; i < a.length; i++) {
            a[i][j] = a[i][j] * c[i];
        }

    }

    /**
     * Multiplica a coluna 'j' da Matriz 'a' pelo vetor 'b'
     * @param a Matriz a 
     * @param b Vetor b
     * @param j índice da coluna da matriz 'a'
     */
    public static void timesColumnforVetor(Complex[][] a, double[][] b, int j) {

        if ((b.length * b[0].length) > a[0].length) {
            throw new RuntimeException("O tamanho da matriz 'b' necessita coincidir com as linhas da matriz complexa 'a'");
        }

        if (j > a[0].length) {
            throw new RuntimeException("A matriz não possui o índice j = " + j);
        }

        double[] c = MatrixOperation.vectorize_Uni(b);

        for (int i = 0; i < a.length; i++) {
            a[i][j] = a[i][j].times(c[i]); 
        }
    }
    
    /**
     * Transforma a matriz double em complexa 
     * @param x matriz double
     * @return matriz complexa
     */
    public static Complex[][] parseComplex(double[][] x) {
        Complex[][] complex = new Complex[x.length][x[0].length];

        for (int i = 0; i < x.length; i++) {
            for (int j = 0; j < x[0].length; j++) {
                complex[i][j] = new Complex(x[i][j], 0);
            }
        }

        return complex;
    }

    /**
     * Forma um vetor que começa em start e termina em end em ordem
     * crescente com acrescimo de 1.
     * @param start valor inicial
     * @param end valor final
     * @return 
     */
    public static double[][] vetorUp(int start, int end){
        
        if (start > end) {
            throw new RuntimeException("O valor 'start' tem que ser menor que o 'end'.");
        }
        
        double[][] a = new double[1][end - start + 1];
        a[0][0] = (double) start;
        
        for(int i = 1; i < a[0].length; i++){
            a[0][i] = a[0][i-1] +1;
        }
        
        return a;
    }

    /**
     * Replica a matriz em m linhas e n colunas
     * @param x matriz a ser replicada
     * @param m número de linhas
     * @param n número de colunas
     * @return Matriz replicada
     */
    public static double[][] repmat(double[][] x, int m, int n){
        
        double[][] a = x;
        
        for(int i = 0; i < m - 1; i++) {
            a = MathFunc.addMatriz_Below(a, x);
        }
        double[][] a2 = a;
        
        for (int j = 0; j < n - 1; j++) {
            a = MathFunc.addMatriz_Left(a, a2);
        }
        return a;
    }
    
    /**
     * Calcula o fatorial de um número
     * @param n
     * @return 
     */
    public static double fatorial(double n) {

        if (n == 0) {
            return 1;
        }

        return fatorial(n - 1) * n;
    }
    
    /**
     * Inverte as linhas da Matriz
     * @param x
     * @return 
     */
    public static double[][] inverterArray(double[][] array) {
        double arrayInvertido[][] = new double[array.length][array[0].length];

        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                arrayInvertido[i][j] = array[i][array[0].length - j - 1];
            }
        }
        return arrayInvertido;
    }
    
    /**
     * changeSignal<br/>
     * 
     * Troca o sinal dos elementos do vetor
     * 
     * @param Vetor Vetor que o sinal será trocado
     * 
     * @author fmassicano
     * @since 2012-01-23
     */
    public static void changeSignal(double[][] array){
        
        for(int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                array[i][j] = -array[i][j];
            }
        }
        
    }
    
    /**
     * Cria um vetor espelhado
     * 
     * <br><b>Exemplo</b></br>
     * 
     * <br>n = 4; -> -1 0 1 2</br>
     * 
     * <br>n = 10; -> -4 -3 -2 -1 0 1 2 3 4 5</br>
     * 
     * @param n número de colunas da matriz
     * @return 
     */
    public static double[][] mirror(int n){
        double[][] a = new double[1][n];
        
        double x = (n*1.0/2.0) - 1;
        
        for(int j = 0; j < a[0].length; j++){
            a[0][j] = -x + j; 
        }
        
        return a;
    }
    
    /**
     * Retorna um vetor contendo os cosenos.
     * 
     * @param theta angulos em graus
     * @return 
     */
    public static double[] costheta(double[] theta) {

        double[] costheta = new double[theta.length];

        for (int i = 0; i < theta.length; i++) {
            costheta[i] = Math.cos(theta[i]);
        }

        return costheta;
    }
    /**
     * Retorna um vetor contendo os senos.
     * 
     * @param theta angulos em graus
     * @return 
     */
    public static double[] sintheta(double[] theta) {

        double[] sintheta = new double[theta.length];

        for (int i = 0; i < theta.length; i++) {
            sintheta[i] = Math.sin(theta[i]);
        }

        return sintheta;
    }
    
    /**
     * floor
     * 
     * Apply Math.floor in matrix
     * 
     * @param matrix 
     */
    public static double[][] floor(double[][] matrix){
        double[][] a = new double[matrix.length][matrix[0].length];
        for(int i = 0; i < matrix.length; i++){
            for(int j =0; j < matrix[0].length; j++){
                a[i][j] = Math.floor(matrix[i][j]);
            }
        }
        return a;
    }
    
    /**
     * Apply Math.round in matrix
     * 
     * @param matrix 
     */
    public static void round(double[][] matrix){
        
        for(int i = 0; i < matrix.length; i++){
            for(int j =0; j < matrix[0].length; j++){
                matrix[i][j] = Math.round(matrix[i][j]);
            }
        }
        
    }
    
    /**
     * getMatrixIdx <br/>return<br/>
     * 
     * Recebe dois parâmetros, um Vetor real e uma matriz de índices.<br/>
     * Retorna uma matriz com mesma dimensão da matriz fornecida, porém as informações
     * contidas nessa matriz serão referentes aos valores contidos no vetor.<br/><br/>
     * 
     * @param vector Vetor contendo valores reais 
     * @param matrixIdx Matriz contendo indices referente a esse vetor
     * 
     * @return Uma matriz com as informações do vetor construida mediante os indices da matriz dada.
     * 
     * @since 2012-01-22
     */
    public static double[][] getMatrixIdx(double[] vector, double[][] matrixIdx){
        double[][] matrix = new double[matrixIdx.length][matrixIdx[0].length];
        
	for(int i = 0; i < matrix.length; i ++){
	    for(int j = 0; j< matrix[0].length; j++){
                if(matrixIdx[i][j] < 0){
		    throw new RuntimeException("A matriz de índices somente contêm valores positivos.");
		}
		if(matrixIdx[i][j] >= vector.length){
                        throw new RuntimeException("A matriz de índices não pode conter valores maiores ou iguais ao tamanho do vetor dado. Valor da matriz de índices = " + matrixIdx[i][j]);
                }
		matrix[i][j]=vector[(int)matrixIdx[i][j]];
	    }
	}
        return matrix;
    }
    
    /**
     * Efetua a soma de todos elementos da matriz.
     * 
     * @param x Matriz double
     * @return Soma dos elementos da Matriz x
     */
    public static double sum(double[][] x){
        double sum = 0;
        
        for(int i = 0; i < x.length; i++){
            for(int j = 0; j < x[0].length; j++){
                sum += x[i][j];
            }
        }
        
        return sum;
    }
    
    /* Testa funções da classe MathFunc*/
    public static void main(String[] args) {
        
        System.out.println(Math.floor(-9.8));
        
//        double[][] a = MathFunc.matrix_n(5, 4, 9);
//        
//        double d = 4.2;
//        
//        int e = 3;
//        
//        
//        HelpMethods.show(MatrixOperation.divide(a, d));
//        System.out.println("");
//        System.out.println("");
//        HelpMethods.show(MatrixOperation.divide(a, e));
//        
        
        
        
//        double[][] b = MathFunc.matrix_n(5, 4, 1);
//         
//        double[][] z = MathFunc.addMatriz_Below(a, b);
//        
//        double sum = sum(z);
//        
//        HelpMethods.show(z);
//        
//        System.out.println("");
//        System.out.println("sum = " + sum);
        
//        HelpMethods.show(colon(1,11));
//        HelpMethods.show(colon(1,2,11));
        
//	double[] vector = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
//
//	double[][] matrixIdx = new double[5][5];
//
//	matrixIdx[0][0] = 0;
//	matrixIdx[0][1] = 0;
//	matrixIdx[0][2] = 0;
//	matrixIdx[0][3] = 0;
//	matrixIdx[0][4] = 0;
//
//	matrixIdx[1][0] = 2;
//	matrixIdx[1][1] = 2;
//	matrixIdx[1][2] = 2;
//	matrixIdx[1][3] = 2;
//	matrixIdx[1][4] = 2;
//
//        matrixIdx[2][0] = 9;
//        matrixIdx[2][1] = 9;
//        matrixIdx[2][2] = 9;
//        matrixIdx[2][3] = 9;
//        matrixIdx[2][4] = 9;
//
//        matrixIdx[3][0] = 3;
//        matrixIdx[3][1] = 3;
//        matrixIdx[3][2] = 3;
//        matrixIdx[3][3] = 3;
//        matrixIdx[3][4] = 3;
//
//	matrixIdx[4][0] = 5;
//        matrixIdx[4][1] = 5;
//        matrixIdx[4][2] = 5;
//        matrixIdx[4][3] = 5;
//        matrixIdx[4][4] = 5;
//
//	HelpMethods.show(matrixIdx);
//        System.out.println("");
//	HelpMethods.show(getMatrixIdx(vector,matrixIdx));

        
//        double[][] x = MatrixOperation.plus(MatrixOperation.minus(MathFunc.vetorUp(1, 64), MathFunc.matrix_n(1, 64, 1)),MathFunc.matrix_n(1, 64, -31) );
//        HelpFunction.show(MathFunc.vetorUp(1, 64));
//        HelpFunction.show(MathFunc.matrix_n(1, 64, 1));
//        System.out.println("");
//        HelpFunction.show(x);
//        System.out.println("");
//        HelpFunction.show(MatrixOperation.transpose(inverterArray(x)));
//        
//        HelpFunction.show();
        
        //        
//        Complex[][] a = new Complex[2][2];
//        a[0][0] = new Complex(1,1); 
//        a[0][1] = new Complex(1,1); 
//        
//        a[1][0] = new Complex(2,3); 
//        a[1][1] = new Complex(2,4); 
//        
//        Complex[] b = new Complex[2];
//        b[0] = new Complex(10,4); 
//        b[1] = new Complex(10,4); 
//        
//        HelpFunction.show(a);
//        System.out.println("");
//        HelpFunction.show(b);
//        System.out.println("");
//        setColumn(a, b, 1);
//        HelpFunction.show(a);
//        System.out.println("");
//        HelpFunction.show(getColumn(a, 0));
//        double order = 128.0;
//        
//        double[][] H = MatrixOperation.times(MatrixOperation.times(MathFunc.linspace(0, (order/2.0)),2), (1.0/order));
//        double[][] w = MatrixOperation.times(MatrixOperation.times(MathFunc.linspace(0, size(H)[1] - 1), 2*Math.PI), 1.0/order);
//
//        double[][] a = FilterMethods.hann(H, w, 1);
//        
//        subsFor(a, w, MAIOR, Math.PI*0.5, 0);
//        
////      HelpFunction.show(subMatriz(1, a[0].length - 2, a));
//        
//        HelpFunction.show(addMatriz_Below(MatrixOperation.transpose(a), MatrixOperation.transpose( subMatriz(1, a[0].length - 2, a)  )));
//        
//        double[][] c = addMatriz_Below(a, b);
//        double[][] c = new double[1][10];
//        
//        c[0][0] = 1;
//        c[0][1] = 2;
//        c[0][2] = 3;
//        c[0][3] = 4;
//        c[0][4] = 5;
//        c[0][5] = 6;
//        c[0][6] = 7;
//        c[0][7] = 8;
//        c[0][8] = 9;
//        c[0][9] = 10;
//        
//        HelpFunction.show(c);
//        
//        System.out.println("");
//        
//        HelpFunction.show(addMatriz_Below(c, matrix_n(c[0].length - c.length, c[0].length, 0)));
    }
}
