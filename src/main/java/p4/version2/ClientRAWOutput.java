package p4.version2;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import p4.SerialReader;
import p4.TYPES;
import p4.TextMessage;

public class ClientRAWOutput implements Runnable {
    ClientRAW cr;
    InputStream is;
    boolean receiveClose = false;

    public ClientRAWOutput(ClientRAW cr) {
        this.cr = cr;
    }
    public void setInputStream(InputStream is){
        this.is = is;
    }

    public void run() {
        byte[] raw;
        ByteBuffer bb;
        boolean controlRods = true;
        do {
            try {
                raw = SerialReader.read(is);

                if (raw == null || raw.length == 0) {
                    controlRods = false;
                    continue;
                }

                bb = ByteBuffer.wrap(raw);

                switch (bb.getInt(0)) {
                    case TYPES.TYPE_CLOSE: {
                        controlRods = false;
                        cr.receiveClose = true;
                        break;
                    }
                    case TYPES.TYPE_TEXT: {
                        TextMessage tm = TextMessage.unpack(raw);
                        System.out.println("<" + tm.dni + "> " + tm.text);

                        break;
                    }
                    case TYPES.TYPE_USER: {
                        System.out.println("Me envias usuarios?");

                        break;
                    }
                    default: {
                        System.out.println("Mensaje recibido con formato incorrecto");
                    }

                }
            } catch (IOException e) {
                System.out.println("\nError 03\n");
                controlRods = false;
                e.printStackTrace();
            }

        } while (controlRods && !cr.receiveClose && !receiveClose);


    }
}
