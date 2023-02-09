package p3.version1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConcurrentServer {
    public void runServer(int port) throws IOException{

        ServerSocket listener = new ServerSocket(port);
        System.out.println("-----------Socket Server---------------" +
                "\nServer Address: " + listener.getInetAddress() +
                (listener.getInetAddress().toString().equals("0.0.0.0/0.0.0.0") ? " -> localhost" : "") +
                "\nServer Port   : " + listener.getLocalPort()
        );
        System.out.println("---------------------------------------");
        ExecutorService exec = Executors.newFixedThreadPool(3);

        try{
            while(true){
                ServerTask st = new ServerTask(listener.accept());
                exec.execute(st);
            }
        }
        catch(SocketException se){
            se.printStackTrace();
        }
        finally{
            listener.close();
        }







    }

    
    public static void main(String[] args) throws IOException {
        ConcurrentServer cs = new ConcurrentServer();
        cs.runServer(22222);
    }
}
