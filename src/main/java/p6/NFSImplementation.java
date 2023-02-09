package p6;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.rmi.RemoteException;
import java.util.HashMap;

public class NFSImplementation implements NFS {

    HashMap< Integer, File> openFiles = new HashMap<>(); 
    HashMap< Integer, RandomAccessFile> filesHandless = new HashMap<>();
    static int id_global;

    @Override
    public int open(File file) throws RemoteException {
        int id_final = id_global + 1;
        RandomAccessFile raf = null;

        try {
            raf = new RandomAccessFile(file, "read/write"); 
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return -1;
        }

        filesHandless.put(id_final, raf);
        openFiles.put(id_final, file);
        id_global++;

        return id_final;
    }

    @Override
    public byte[] read(int id_file) throws RemoteException {
        if(id_file < 0 ) return null;
        byte[] raw;
        RandomAccessFile raf = filesHandless.get(id_file);
        try {
            raw = new byte[(int) raf.length()];
            raf.read(raw);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        
        return raw;
    }

    @Override
    public void write(int id_file, byte[] raw_data) throws RemoteException {
        if(raw_data == null || raw_data.length == 0 || id_file<0) return;
        RandomAccessFile raf = filesHandless.get(id_file);
        try {
            raf.write(raw_data);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void close(int id_file) throws RemoteException {
        if(id_file < 0) return;
        try {
            (filesHandless.get(id_file)).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
