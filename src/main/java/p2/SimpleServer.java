package p2;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class SimpleServer {

    public void runServer(int port) throws IOException {

        ServerSocket listener = new ServerSocket(port);
        System.out.println("---------------------------------------");
        System.out.println("-----------Socket Server---------------" +
                "\nServer Address: " + listener.getInetAddress() +
                (listener.getInetAddress().toString().equals("0.0.0.0/0.0.0.0") ? " -> localhost" : "") +
                "\nServer Port   : " + listener.getLocalPort());

        try {
            //CUERPO


            while (listener != null) {
                Socket s = listener.accept();
                // imprimimos datos de sockets
                System.out.println("\n-----------Socket Cliente--------------" +
                        "\nClient Address: " + s.getInetAddress() +
                        "\nClient Port   : " + s.getPort() +
                        "\nData input:\n"
                );
                
                BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
                PrintWriter bw = new PrintWriter(new OutputStreamWriter(s.getOutputStream()));

                try // ejecucion del socket
                {

                    // mostrar por pantalla la linea recibida y reenviarla
                    String cad;

                    while (true) {
                        System.out.println("---------------------------------------");
                        cad = br.readLine();
                        bw.println(cad);
                        bw.flush();

                        if (cad == null)
                            throw new UselessException("Se cerro el Stream de datos de forma inesperada", UselessException.REASON_ERROR);

                        System.out.println(cad);
                        switch (cad) {
                            case "masterClose": {
                                throw new UselessException("Cerrando el Server de forma remota", UselessException.REASON_MASTER_CLOSE);
                            }
                            case "quit": {
                                throw new UselessException("Cerrando conexion a peticion del cliente");
                            }
                            case "showInfo": {
                                System.out.println(s.getInetAddress());
                                break;
                            }
                            case "help": {
                                System.out.println("Avaliable Commands:");
                                break;
                            }
                        }

                    }

                } catch (UselessException ue) {
                    System.out.println("\n" + ue.mensaje);  
                    if(ue.reason == UselessException.REASON_MASTER_CLOSE) listener.close();
                    else{
                        System.out.println("---------------------------------------");
                        System.out.println("\nEsperando nuevas peticiones\n");
                    }

                } catch (SocketException se) {
                    //System.out.println("\nCerrando el server de forma remota");
                    //listener.close();
                    se.printStackTrace();

                } catch (IOException ioe) {
                    ioe.printStackTrace();
                } finally {
                    br.close();
                    bw.close();
                    s.close();
                }
            }


            //FIN DEL CUERPO

        } catch (SocketException se) {
            System.out.println("-----------Cerrando server-------------");
        } finally {
            listener.close();
        }

    }
}
