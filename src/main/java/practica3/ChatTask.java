package practica3;

import java.io.*;
import java.net.Socket;
public class ChatTask implements Runnable {

    Socket s;
    ConcurrentChatServer ccs;
    int locator;



    public ChatTask(Socket s, ConcurrentChatServer ccs) { this.s = s; this.ccs = ccs; locator=s.getPort(); }

    public void run(){
        if(s!=null){
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
                
                try{
                    String r;
                    do{
                        r = br.readLine();
                        ccs.deliverMessage(s.getPort()+": "+r);
                        
                    } while( r != null);
                    endTask();
    
                }
                catch(IOException ioe){ ioe.printStackTrace(); endTask(); }
    
            }
            catch(IOException ioe){ ioe.printStackTrace(); }
        }

        
    }

    public void sendMessageToClient(String w){
        try{

            if(w!=null){
                PrintWriter bw = new PrintWriter(new OutputStreamWriter(s.getOutputStream()));
                bw.println(w);
                bw.flush();
            }

        }
        catch(IOException ioe){ ioe.printStackTrace(); }
    }
    public void endTask(){
        try {

            System.out.println("Cliente "+locator+" cerro conexion");
            if(s!=null){
                s.close();
            }
            ccs.removeClient(this);
                

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
