package p4;

import java.nio.ByteBuffer;

public class TextMessage extends Header{
    
    public String text;
    public int dni;


    public TextMessage(String text, int dni) {
        super(TYPES.TYPE_TEXT);
        this.text = text;
        this.dni = dni;
        this.length = text.getBytes().length; //Cada Char son 2byte
    }

    public TextMessage(){}

    
    



    public byte[] pack() {
        ByteBuffer bb = ByteBuffer.allocate( text.getBytes().length + 13);
        bb.put(super.pack());
        bb.putInt(dni);
        bb.put(text.getBytes());
        bb.put(TYPES.lastByteSimbol);

        return bb.array();
    }

    public static TextMessage unpack(ByteBuffer bb) {
        TextMessage tm = new TextMessage();
        tm.type = bb.getInt(0);
        tm.length = bb.getInt(4);
        tm.dni = bb.getInt(8);

        byte[] constructor = new byte[tm.length]; //Length inherited from super class Header
        for( int i = 0 ; i < tm.length ; i++) {
            constructor[i] = bb.get(i + 12);
        }
        tm.text = new String(constructor);

        return tm;
    }

    public static TextMessage unpack(byte[] raw) {
        ByteBuffer bb = ByteBuffer.wrap(raw);
        return unpack(bb);
    }


}
