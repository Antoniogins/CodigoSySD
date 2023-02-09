package practica1_1;

import java.io.IOException;

public class RunWriteNumbers {
    public static void main(String[] args) throws IOException {
        WriteNumbers wn = new WriteNumbers();
        wn.escribir("cascada");
        System.out.println("Salida?");
    }
}
