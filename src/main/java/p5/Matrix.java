package p5;

import java.io.Serializable;

public class Matrix implements Serializable {
    int[][] enteros;
    public int filas;
    public int columnas;

    public Matrix(int fila, int columna) {
        if(fila == 0 || columna == 0) return;
        this.filas = fila;
        this.columnas = columna;
        enteros = new int[fila][columna];
    }

    public Matrix(int[][] a) {
        if(a == null || a.length == 0 || a[0].length == 0) return;
        enteros = new int[a.length][a[0].length];
        filas = a.length;
        columnas = a[0].length;

        for(int i=0; i<a.length ; i++) {
            for(int j=0; j<a[0].length ; j++) {
                enteros[i][j] = a[i][j];
            }
        }
        //Mejor copiar valor a valor que si copiamos la matriz por referencia, si no estariamos permitiendo
        //que se pueda modificar la matriz sin querer, cambiando "a"
    }



    public void set(int fila, int columna, int valor) {
        enteros[fila][columna] = valor;
    }
    public int get(int fila, int columna) {
        return enteros[fila][columna];
    }
    public int getFilas() {
        return enteros.length;
    }
    public int getColumnas() {
        return enteros[0].length;
    }

    @Override
    public String toString() {
        StringBuilder constructor = new StringBuilder();
        for(int i=0 ; i<enteros.length ; i++){
            for(int j=0 ; j<enteros[i].length ; j++) {
                constructor.append(this.enteros[i][j]+"\t");
            }
            constructor.append("\n");
        }
        return constructor.toString();
    }

}
