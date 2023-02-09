package p4;

import java.nio.ByteBuffer;

public class CloseMessage extends Header {
    

    public CloseMessage() { 
        type = TYPES.TYPE_CLOSE;  
        length = 12;
    }


    public byte[] pack() {
        ByteBuffer bb = ByteBuffer.allocate(9);
        bb.put(super.pack());
        bb.put(TYPES.lastByteSimbol);
        return bb.array();
    }

    public static CloseMessage unpack(byte[] raw) {
        ByteBuffer bb = ByteBuffer.wrap(raw);
        return unpack(bb);
    }

    public static CloseMessage unpack(ByteBuffer bb) {
        CloseMessage cm = new CloseMessage();
        cm.type = bb.getInt(0);
        cm.length = 12;
        return cm;
    }

}
