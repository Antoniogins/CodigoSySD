package practica2;

import java.io.IOException;

public class StartSimpleServer {

    public static void main(String[] args) {
        SimpleServer ss = new SimpleServer();
        try {

            ss.runServer(22222);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
