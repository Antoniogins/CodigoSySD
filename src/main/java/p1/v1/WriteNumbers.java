package p1.v1;

import java.io.*;

public class WriteNumbers {



    void escribirNBytes(String file, int n) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(new File(file + "_codification"+n+".txt")));
        char[] raw = new char[n];
        bw.write(raw);
        bw.close();
    }


    public void escribir(String file) throws IOException {
        FileWriter fwc = new FileWriter(new File(file + "_codification.txt"));
        DataOutput dos = new DataOutputStream(new FileOutputStream(new File(file + "_binary.txt")));

        for (int i = 1; i <= 100000; i++) {

            fwc.write(i);
            fwc.flush();
            dos.writeInt(i);

        }
        fwc.close();
        

    }

}
