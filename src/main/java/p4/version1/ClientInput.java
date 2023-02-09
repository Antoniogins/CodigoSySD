package p4.version1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;

import p4.*;

public class ClientInput implements Runnable {
    Client c;
    ObjectOutputStream oos;

    public ClientInput(Client c, ObjectOutputStream oos) {
        this.c = c;
        this.oos = oos;
    }

    public void run() {

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
                ioe.printStackTrace();
            }
        }

        // Introducimos el nombre
        String usuario = null;
        try {
            System.out.println("Introduce nombre: ");
            usuario = teclado.readLine();

            UserMessage userMessage = new UserMessage(usuario, dni);
            oos.writeObject(userMessage);



            //Comenzamos a leer del teclado, hasta que se cierre
            String mensaje;
            while ( (mensaje = teclado.readLine()) != null) {

                switch( mensaje ){
                    // case "help": {

                    //     break;
                    // }
                    case "quit": {
                        oos.writeObject(new CloseMessage());
                        mensaje = null;
                        break;
                    }
                    default : {
                        oos.writeObject(new TextMessage(mensaje, userMessage.dni));
                    }
                }

            }

            oos.close();




        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
