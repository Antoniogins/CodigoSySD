package p4.version2;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

import p4.*;

public class ServerRAWTask implements Runnable {

    Socket sc;
    ServerRAW ccs;
    UserMessage user;

    InputStream is;
    BufferedOutputStream bos;

    public ServerRAWTask(Socket s, ServerRAW ccs) {
        this.sc = s;
        this.ccs = ccs;

        try {

            is = s.getInputStream();
            bos = new BufferedOutputStream(s.getOutputStream());

        } catch (IOException ioe) {
            System.out.println("\nError 05\n");
            ioe.printStackTrace();
            endTask();
        }

    }

    public void run() {
        try {
            boolean isCapable = true;
            do {
                byte[] raw = SerialReader.read(is);
                if (raw == null || raw.length == 0)
                    throw new IOException("error al obtener los bytes");
                ByteBuffer bb = ByteBuffer.wrap(raw);



                switch (bb.getInt(0)) {
                    case TYPES.TYPE_CLOSE: {
                        CloseMessage cm = CloseMessage.unpack(bb);

                        bos.write(cm.pack());
                        bos.flush();
                        isCapable = false;
                        break;
                    }
                    case TYPES.TYPE_TEXT: {
                        TextMessage tm = TextMessage.unpack(bb);
                        evaluateMessage(tm);

                        break;
                    }
                    case TYPES.TYPE_USER: {
                        user = UserMessage.unpack(bb);
                        break;
                    }
                    default: {
                        // TODO enviar mensaje erroneo o error

                        endTask();
                    }
                }

            } while (isCapable);

        } catch (IOException ioe) {
            System.out.println("\nError 04\n");
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
                for (ServerRAWTask concha : ccs.listaClientes) {
                    clientes.concat(concha.sc.getPort() + " -> " + concha.user.name + "\n");
                }
                bos.write((new TextMessage(clientes, user.dni)).pack());
                bos.flush();
                ;

                break;
            }
            default: {
                ccs.deliverMessage(tm, this);
            }
        }
    }

    public void sendMessageToClient(TextMessage w) {
        if (w == null)
            return;
        try {
            bos.write(w.pack());
            bos.flush();
        } catch (IOException e) {
            System.out.println("\nError 03\n");
            e.printStackTrace();
        }

    }

    public void endTask() {
        try {

            if (bos != null)
                bos.close();
            if (is != null)
                is.close();

            if (user != null) {
                String goodbye = "Cliente " + sc.getPort() + ": \"" + user.dni + "\" cerro conexion";
                System.out.println(goodbye);
                ccs.deliverMessage(new TextMessage(goodbye, 9999), this);
                ccs.listaClientes.remove(this);

            }
            sc.close();

        } catch (IOException e) {
            System.out.println("\nError 02\n");
            e.printStackTrace();
        }
    }

}
