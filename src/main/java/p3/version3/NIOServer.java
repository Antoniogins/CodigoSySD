package p3.version3;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

public class NIOServer {

    Selector slt;
    ServerSocketChannel ssc;
    ByteBuffer buffer;
    ArrayList<SocketChannel> listaClientes;

    public void runServer(int port) throws IOException {

        // https://www.baeldung.com/java-nio-selector

        // En primer lugar obtenemos un selector desde el sistema operativo, usando la
        // funcion estatica Selector.open()
        slt = Selector.open();

        // En segundo lugar obtenemos un ServerSocketChannel a partir del metodo
        // estatico ServerSocketChannel.open()
        ssc = ServerSocketChannel.open();
        ssc.socket().bind(new InetSocketAddress("localhost", port));

        // En tercer lugar registramos el ServerSocketCHannel al Selector que tenemos,
        // indicando la operacion OP_ACCEPT
        // Cuando registramos ssc con la operacion OP_ACCEPT, le estamos indicando a
        // Selector que preste atencion a ssc
        // cuando ocurra ese tipo de operacion (cuando ssc reciba una peticion de acceso
        // -> .accept())
        ssc.configureBlocking(false);
        ssc.register(slt, SelectionKey.OP_ACCEPT);

        buffer = ByteBuffer.allocate(1024);
        listaClientes = new ArrayList<>();

        try {

            while (true) {

                // En cuarto lugar, necesitamos indicar a selector que comienze a funcionar, y
                // escuche a los eventos.
                // Para ello le indicamos select(), que bloqueara hasta que haya un evento al
                // que atender
                slt.select();

                // En quinto lugar, necesitamos obtener una lista de todos los Channels que esta
                // atendiendo listerner
                // para poder iterarlos y trabajar con ellos

                Set<SelectionKey> list = slt.selectedKeys();
                Iterator<SelectionKey> it = list.iterator();

                while (it.hasNext()) {

                    SelectionKey next = it.next();

                    if (next.isAcceptable())
                        accept(next);
                    // if (sk.isConnectable())
                    // connect(sk);
                    if (next.isReadable())
                        read(next);
                    // if (sk.isWritable())
                    // write(sk);
                    // if (sk.isValid())
                    // validate(sk);

                    list.remove(next);

                }

            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            slt.selectedKeys().clear();
        }
    }

    void accept(SelectionKey sk) throws IOException {
        SocketChannel scs = ssc.accept();
        listaClientes.add(scs);

        scs.configureBlocking(false);
        scs.register(slt, SelectionKey.OP_READ);

        System.out.println("Nuevo cliente, con puerto " + scs.getRemoteAddress());
    }

    void connect(SelectionKey sk) {

    }

    void read(SelectionKey sk) throws IOException {
        SocketChannel scs = (SocketChannel) sk.channel();

        if(scs.read(buffer) == -1) {
            sk.channel().close();
            sk.cancel();
            System.out.println("anda cerrando wey");
        } else {
            System.out.println(buffer);
            buffer.flip();
            scs.write(buffer);
            buffer.clear();
            System.out.println(buffer);
        }

        
        // if (buffer.position() == 0) { //TODO se comprueba asi se ha cerrado el cliente?
            
        // } else {
            
        // }
    }

    void validate(SelectionKey sk) {

    }

    void write(SelectionKey sk) {

    }

    public static void main(String[] args) {
        try {
            NIOServer nio = new NIOServer();
            nio.runServer(22222);
        } catch (IOException elele) {
            elele.printStackTrace();
        }
    }

}
