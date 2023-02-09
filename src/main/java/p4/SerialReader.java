package p4;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class SerialReader {
    

    public static byte[] read(InputStream is) throws IOException {

        if(is == null) return null;

        ByteBuffer bb = ByteBuffer.allocate(TYPES.MAX_BUFFER_SIZE);
        byte received;

        //TODO comprobar que tras leer los primeros 12 bytes setrata de un stream de bytes que contienen Header
        while( (received = (byte) is.read() ) != TYPES.lastByteSimbol ) {
            bb.put(received);
        }

        int length  = bb.getInt(4);
        byte[] bytes = new byte[length + 12];

        for(int i = 0 ; i < length + 12 ; i++){
            bytes[i] = bb.get(i);
        }

        bb = null;


        return bytes;

    }
}
