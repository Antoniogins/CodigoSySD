package p4.version1;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import p4.*;

public class ServerTask implements Runnable {

    Socket sc;
    Server ccs;
    UserMessage user;

    ObjectInputStream ois;
    ObjectOutputStream oos;

    public ServerTask(Socket s, Server ccs) {
        this.sc = s;
        this.ccs = ccs;

        try {

            // MUY IMPORTANTE: en el servidor el orden de creacion de os ObjectStream tiene
            // que ser el inverso
            // al orden en que se crean en el cliente -> cuando se crea un
            // ObjectOutputStream se envia un mensaje
            // de cabecera inicial, y el ObjectInputStream bloquea hasta que la otra parte
            // manda esa cabecera inicial
            ois = new ObjectInputStream(sc.getInputStream());
            oos = new ObjectOutputStream(sc.getOutputStream());

        } catch (IOException ioe) {
            ioe.printStackTrace();
            endTask();
        }

    }

    public void run() {
        try {
            Header h;
            do {
                h = (Header) ois.readObject();
                switch (h.type) {
                    case TYPES.TYPE_CLOSE: {
                        CloseMessage cm = (CloseMessage) h;
                        oos.writeObject(cm);
                        oos.flush();
                        endTask();
                        break;
                    }
                    case TYPES.TYPE_TEXT: {
                        TextMessage tm = (TextMessage) h;
                        evaluateMessage(tm);

                        break;
                    }
                    case TYPES.TYPE_USER: {
                        user = (UserMessage) h;
                        break;
                    }
                    default: {
                        // TODO enviar mensaje erroneo o error

                        endTask();
                    }
                }

            } while (h != null);

        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        } catch (IOException ioe) {
            System.out.println("EXCEPCION #22");
            ioe.printStackTrace();
        } finally {
            endTask();
        }

    }

    public void evaluateMessage(TextMessage tm) throws IOException {

        String[] command = tm.text.split(":");
        switch (command[0]) {
            case "username": {
                user.name = command[1].trim();
                tm.text = "<" + sc.getPort() + "> changed username to <" + user.name + ">";
                ccs.deliverMessage(tm, this);
                break;
            }
            case "help": {
                String comandos = "username:x   -> set x as ur username\n" +
                        "showclients  -> show clients and its alias\n" +
                        "quit         -> closes the connection of this client in the server\n" +
                        "help         -> show avaliable commands\n";
                this.sendMessageToClient(new TextMessage(comandos, user.dni));
                break;
            }
            case "quit": {
                endTask();
            }
            case "showclients": {
                String clientes = "";
                for (ServerTask concha : ccs.listaClientes) {
                    clientes.concat(concha.sc.getPort() + " -> " + concha.user.name + "\n");
                }
                oos.writeObject(new TextMessage(clientes, user.dni));
                oos.flush();

                break;
            }
            default: {
                ccs.deliverMessage(tm, this);
            }
        }
    }

    public void sendMessageToClient(TextMessage w) {
        if (w == null) return;
        try {
            oos.writeObject(w);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void endTask() {
        try {

            if (ois != null) ois.close();
            if (oos != null) oos.close();

            if (user != null) {
                String goodbye = "Cliente " + sc.getPort() + ": \"" + user.dni + "\" cerro conexion";
                System.out.println(goodbye);
                ccs.listaClientes.remove(this);
                ccs.deliverMessage(new TextMessage(goodbye, user.dni), this);

                sc.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
