package p3.version2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConcurrentChatServer {

    ArrayList<ChatTask> listaClientes = new ArrayList<>();
    ArrayList<String> messages = new ArrayList<>();
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

                ChatTask ct = new ChatTask(s, this);
                listaClientes.add(ct);

                exec.execute(ct);
            }

        } catch (SocketException se) {
            se.printStackTrace();
        } finally {
            listener.close();
        }

    }

    public void deliverMessage(String m) {
        for (ChatTask ct : listaClientes) {
            if (ct != null) ct.sendMessageToClient(m);
        }
    }


    public static void main(String[] args) throws IOException {
        ConcurrentChatServer ccs = new ConcurrentChatServer();
        ccs.run(22222);
    }
}