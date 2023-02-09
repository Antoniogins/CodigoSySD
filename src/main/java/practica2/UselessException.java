package practica2;

public class UselessException extends Exception {
    
    public final static int REASON_ERROR = 3210;
    public final static int REASON_STREAM_SHUTDOWN = 99929;
    public final static int REASON_MASTER_CLOSE = 3021111;

    public String mensaje;
    public int reason;
    public UselessException(String mensaje){ this.mensaje = mensaje; }
    public UselessException(String mensaje, int reason){ this.mensaje = mensaje; this.reason = reason; }
    public UselessException(){ mensaje="No Message"; }
    public String toString(){ return mensaje; }
}
