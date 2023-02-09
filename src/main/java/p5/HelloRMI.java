package p5;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface HelloRMI extends Remote {
    String sayHello(String name) throws RemoteException;
    long getTime() throws RemoteException;
    
}
