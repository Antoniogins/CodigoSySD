package p5;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MatrixAlgebra extends Remote {
    Matrix suma(Matrix a, Matrix b) throws RemoteException;
    Matrix resta(Matrix a, Matrix b) throws RemoteException;
    Matrix multiplicar(Matrix a, Matrix b) throws RemoteException;
    int determinante(Matrix a) throws RemoteException;
    Matrix convolucion(Matrix a, Matrix b) throws RemoteException;
    String sayHello() throws RemoteException;
    Matrix adjunto(Matrix a, int f, int c) throws RemoteException;
}
