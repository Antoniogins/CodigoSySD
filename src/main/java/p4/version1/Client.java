package p4.version1;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import p4.*;

public class Client {
    public void runClient(int port) throws IOException {
        Socket sk = null;
        ExecutorService ex = Executors.newFixedThreadPool(1);

        try {
            // Socket del cliente
            sk = new Socket("localhost", port);

            // Streams de la comunicacion TCP
            ObjectOutputStream oos = new ObjectOutputStream(sk.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(sk.getInputStream());

            ClientInput ct = new ClientInput(this, oos);
            ex.execute(ct);

            Header reciv;
            while ((reciv = (Header) ois.readObject()) != null) {
                if( reciv.type == TYPES.TYPE_CLOSE) reciv = null;
                else {
                    TextMessage tm = (TextMessage) reciv;
                    System.out.println("<" + tm.dni + "> " + tm.text);
                }
            }


            


        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            sk.close();
            ex.shutdownNow();
        }
    }

    public static void main(String[] args) throws IOException {
        Client c = new Client();
        c.runClient(22222);
    }
}
