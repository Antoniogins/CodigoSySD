package practica1_1;

import java.io.*;

public class WriteNumbers {
    public void escribir(String file) throws IOException {
        FileWriter fwc = new FileWriter(new File(file + "_codification.txt"));
        DataOutput dos = new DataOutputStream(new FileOutputStream(new File(file + "_binary.txt")));

        for (int i = 1; i <= 100000; i++) {

            fwc.write(i);
            dos.writeInt(i);

        }
        fwc.close();

    }

}
