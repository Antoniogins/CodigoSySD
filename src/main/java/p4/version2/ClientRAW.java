package p4.version2;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import p4.*;

public class ClientRAW {
    public boolean receiveClose = false;

    public void runClient(int port) throws IOException {
        Socket sk = null;
        ExecutorService ex = Executors.newFixedThreadPool(1);
        ClientRAWOutput crw = new ClientRAWOutput(this);;

        try {
            // Socket del cliente
            sk = new Socket("localhost", port);

            // Streams de la comunicacion TCP
            InputStream is = sk.getInputStream();
            BufferedOutputStream bos = new BufferedOutputStream(sk.getOutputStream());

            BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));

            int dni = 0;
            boolean keep = true;

            // Introducir dni hasta que tenga un formato correcto
            while (keep) {
                try {
                    System.out.println("Introduce DNI sin letra: ");
                    dni = Integer.parseInt(teclado.readLine());
                    keep = false;
                } catch (NumberFormatException nfe) {
                    System.out.println("formato de DNI incorrecto");
                } catch (IOException ioe) {
                    System.out.println("\nError 01\n");
                    ioe.printStackTrace();
                }
            }

            // Introducimos el nombre
            String usuario = null;
            System.out.println("Introduce nombre: ");
            usuario = teclado.readLine();

            UserMessage userMessage = new UserMessage(usuario, dni);
            bos.write(userMessage.pack());
            bos.flush();

            // Iniciamos la lectura de datos del socket
            crw.setInputStream(is);
            ex.execute(crw);



            // Comenzamos a leer del teclado, hasta que se cierre
            String mensaje;
            while ((mensaje = teclado.readLine()) != null && !receiveClose) {

                bos.write((new TextMessage(mensaje, userMessage.dni)).pack());
                bos.flush();

            }

            bos.close();

        } catch (UnknownHostException e) {
            System.out.println("\nError 02\n");
            e.printStackTrace();
        } finally {
            sk.close();
            receiveClose = true;
            crw.receiveClose = true;
        }
    }

    public static void main(String[] args) throws IOException {
        ClientRAW c = new ClientRAW();
        c.runClient(22222);
    }
}
