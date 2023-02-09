package p4.version1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import p4.*;

public class Server {
    
    ArrayList<ServerTask> listaClientes = new ArrayList<>();
    ExecutorService exec = Executors.newFixedThreadPool(3);

    public void run(int port) throws IOException {

        ServerSocket listener = new ServerSocket(port);
        System.out.println("-----------Socket Server---------------" +
                "\n    Server Address: " + listener.getInetAddress() +
                (listener.getInetAddress().toString().equals("0.0.0.0/0.0.0.0") ? " -> localhost" : "") +
                "\n    Server Port   : " + listener.getLocalPort());
        System.out.println("---------------------------------------");

        try {

            while (true) {
                Socket s = listener.accept();
                System.out.println("Nuevo cliente <" + s.getPort()+">");

                ServerTask ct = new ServerTask(s, this);
                listaClientes.add(ct);

                exec.execute(ct);
            }

        } catch (SocketException se) {
            se.printStackTrace();
        } finally {
            listener.close();
        }

    }

    public void deliverMessage(TextMessage tm, ServerTask st) {
        for (ServerTask ct : listaClientes) {
            if (ct != null && tm != null && st !=null && ct != st) ct.sendMessageToClient(tm);
        }
    }

    public static void main(String[] args) throws IOException {
        Server s = new Server();
        s.run(22222);
    }
}
