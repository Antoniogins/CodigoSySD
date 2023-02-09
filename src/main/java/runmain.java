import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import p2.SimpleServer;
import p3.version1.ConcurrentServer;
import p3.version2.ConcurrentChatServer;
import p3.version3.NIOServer;
import p4.version1.Server;

public class runmain {
    public static void main(String[] args) {
        BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));

        try{
            System.out.println(
                "Indica el servidor que quieres iniciar: \n"+
                " (1) SimpleServer\n"+
                " (2) ConcurrentServer\n"+
                " (3) ConcurrentChatServer\n"+
                " (4) NIOServer\n"+
                " (5) SerializerServer\n"+
                "\n"+
                " (other) quit"

            );
            int sel = Integer.parseInt(keyboard.readLine());
            switch(sel) {
                case 1 : {
                    SimpleServer ss = new SimpleServer();
                    ss.runServer(22222);
                    break;
                }
                case 2 : {
                    ConcurrentServer cs = new ConcurrentServer();
                    cs.runServer(22222);
                    break;
                }
                case 3 : {
                    ConcurrentChatServer ccs = new ConcurrentChatServer();
                    ccs.run(22222);
                    break;
                }
                case 4 : {
                    NIOServer nio = new NIOServer();
                    nio.runServer(22222);
                    break;
                }
                case 5 : {
                    Server s = new Server();
                    s.run(22222);
                    break;
                }
                default : {

                }
            }
        } catch(IOException ioe){
            ioe.printStackTrace();
        }
    }
}
