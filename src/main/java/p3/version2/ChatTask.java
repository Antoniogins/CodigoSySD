package p3.version2;

import java.io.*;
import java.net.Socket;

import p2.UselessException;

public class ChatTask implements Runnable {

    Socket sc;
    ConcurrentChatServer ccs;
    String userName;
    BufferedReader br;
    PrintWriter pw;

    public ChatTask(Socket s, ConcurrentChatServer ccs) {
        this.sc = s;
        this.ccs = ccs;
        userName = "" + s.getPort();

        try {
            br = new BufferedReader(new InputStreamReader(sc.getInputStream()));
            pw = new PrintWriter(new OutputStreamWriter(sc.getOutputStream()));
        } catch (IOException ioe) {
            endTask();
            ioe.printStackTrace();
        }

    }

    public void run() {

        try {
            String r;
            do {
                r = br.readLine();
                if(r == null) continue;

                String[] command = r.split(":");

                switch(command[0]){
                    case "username" : {
                        userName = command[1].trim();
                        ccs.deliverMessage("<" + sc.getPort() + "> changed username to <" + userName + ">");
                        break;
                    }
                    case "help" : {
                        String comandos = "username:x   -> set x as ur username\n"+
                                          "showclients  -> show clients and its alias\n"+
                                          "quit         -> closes the connection of this client in the server\n"+ 
                                          "help         -> show avaliable commands\n"
                        ;
                        pw.print(comandos);
                        pw.flush();
                        break;
                    }
                    case "quit" : {
                        throw new UselessException();
                    }
                    case "showclients" : {
                        String clientes = "";
                        for(ChatTask concha : ccs.listaClientes){
                            clientes = clientes + concha.sc.getPort()+" -> "+concha.userName+"\n";
                        }
                        pw.print(clientes);
                        pw.flush();
                        break;
                    }
                    default : {
                        ccs.deliverMessage("<" + userName + "> " + r);
                    }
                }
                                

            } while (r != null);

        } catch(UselessException ue) {
            //Literally, useless
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            endTask();
        }

    }

    public void sendMessageToClient(String w) {
        if (w != null) {
            pw.println(w);
            pw.flush();
        }
    }

    public void endTask() {
        try {

            String goodbye = "Cliente " + sc.getPort() + ":\"" +userName + "\" cerro conexion";
            System.out.println(goodbye);
            if(sc != null) sc.close(); 
            if(br != null) br.close(); 
            if(pw != null) pw.close(); 

            ccs.listaClientes.remove(this);
            ccs.deliverMessage(goodbye);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
