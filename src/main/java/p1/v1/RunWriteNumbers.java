package p1.v1;

import java.io.IOException;

public class RunWriteNumbers {
    public static void main(String[] args) throws IOException {
        WriteNumbers wn = new WriteNumbers();
        // wn.escribir("cascada");
        // System.out.println("Salida?");

        for(int i=12345 ; i<14000001 ; ){
            wn.escribirNBytes("sold/pruebas_syncapp", i);
            i *= 2;
        }
    }
}
