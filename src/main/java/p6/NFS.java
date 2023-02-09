package p6;

import java.io.File;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface NFS extends Remote{
    int open(File file) throws RemoteException; //retorna id_file
    byte[] read(int id_file) throws RemoteException;
    void write(int id_file, byte[] raw_data) throws RemoteException;
    void close(int id_file) throws RemoteException;
}
