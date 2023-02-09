package p4;

import java.nio.ByteBuffer;

public class UserMessage extends Header {
    
    public String name;
    public int dni;

    public UserMessage(String name, int dni) {
        super(TYPES.TYPE_USER);
        this.name = name;
        this.dni = dni;
        this.length = name.getBytes().length;
    }

    public UserMessage() {}

    



    public byte[] pack() {
        ByteBuffer bb = ByteBuffer.allocate( name.getBytes().length + 13);
        bb.put(super.pack());
        bb.putInt(dni);
        bb.put(name.getBytes());
        bb.put(TYPES.lastByteSimbol);


        return bb.array();
    }

    public static UserMessage unpack(ByteBuffer bb) {
        UserMessage um = new UserMessage();
        um.type = bb.getInt(0);
        um.length = bb.getInt(4);
        um.dni = bb.getInt(8);
        byte[] constructor = new byte[um.length]; //Length inherited from super class Header
        for( int i = 0 ; i < um.length ; i++) {
            constructor[i] = bb.get(i + 12);
        }
        um.name = new String(constructor);
        
        return um;
    }

    public static UserMessage unpack(byte[] raw) {
        ByteBuffer bb = ByteBuffer.wrap(raw);
        return unpack(bb);
    }

}
