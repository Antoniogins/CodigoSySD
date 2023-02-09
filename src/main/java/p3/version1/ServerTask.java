package p3.version1;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

import p2.UselessException;

public class ServerTask implements Runnable {

    private Socket s;

    public ServerTask(Socket s) {
        this.s = s;
    }

    public void run() {

        System.out.println("Executing socket: local_port:"+s.getLocalPort()+" cliente_port:"+s.getPort());

        try {

            BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintWriter bw = new PrintWriter(new OutputStreamWriter(s.getOutputStream()));

            try // ejecucion del socket
            {
                // mostrar por pantalla la linea recibida y reenviarla
                String cad;

                while (true) {
                    cad = br.readLine();
                    bw.println(cad);
                    bw.flush();

                    if (cad == null)
                        throw new UselessException("Socket con puerto_cliente:"+s.getPort()+" se cerro de forma inesperada");

                    System.out.println("Port " + s.getPort() + ": " + cad);
                    switch (cad) {
                        /*
                         * case "masterClose": {
                         * throw new UselessException("Cerrando el Server de forma remota",
                         * UselessException.REASON_MASTER_CLOSE);
                         * }
                         */
                        case "quit": {
                            throw new UselessException("Socket con puerto_cliente:"+s.getPort()+" se cerro a peticion del cliente");
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
                System.out.println(ue);
            } catch (SocketException se) {
                se.printStackTrace();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            } finally {
                br.close();
                bw.close();
                s.close();
            }

        } catch (IOException ie) {
            ie.printStackTrace();
        }

    }
}
