package p2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class SimpleClient {

    public void runClient(int port) {
        try {
            // Socket del cliente
            Socket sk = new Socket("localhost", port);

            // Buffer de entrada del teclado
            BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));

            // Streams de la comunicacion TCP
            BufferedReader br = new BufferedReader(new InputStreamReader(sk.getInputStream()));
            PrintWriter bw = new PrintWriter(new OutputStreamWriter(sk.getOutputStream()));

            try {
                String read;
                System.out.println("Comienza introducir texto: \n");
                String cadena = teclado.readLine();
                do {
                    bw.println(cadena);
                    bw.flush();

                    read = br.readLine();

                    if (read == null)
                        throw new UselessException("Extreme of the Stream disconnected");
                    System.out.println(read);

                    cadena = teclado.readLine();

                } while (true);

            } catch (UselessException ue) {
                System.out.println("\n" + ue.mensaje);
            } finally {
                sk.close();
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
