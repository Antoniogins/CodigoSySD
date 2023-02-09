package p4;

import java.io.Serializable;
import java.nio.ByteBuffer;

public class Header implements Serializable{
    public int type;
    public int length;

    public Header(){}

    public Header(int type){
        this.type = type;
    }

    public byte[] pack() {
        ByteBuffer bb = ByteBuffer.allocate(8);
        bb.putInt(type);
        bb.putInt(length);

        return bb.array();
    }











}


