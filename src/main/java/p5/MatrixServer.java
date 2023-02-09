package p5;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class MatrixServer extends UnicastRemoteObject implements MatrixAlgebra {
    
    private static final long serialVersionUID = 1L;
    public MatrixServer() throws RemoteException {
        super();
    }

    @Override
    public Matrix suma(Matrix a, Matrix b) throws RemoteException {
        if( a.filas != b.filas || a.columnas != b.columnas) return null;
        System.out.println("realizando suma");

        Matrix resultado = new Matrix(a.filas, a.columnas);

        for(int i=0; i<a.filas ; i++) {
            for(int j=0; j<a.columnas ; j++) {
                int res = a.get(i, j) + b.get(i, j);
                resultado.set(i, j, res);
            }
        }


        return resultado;
    }

    @Override
    public Matrix resta(Matrix a, Matrix b) throws RemoteException {
        if( a.filas != b.filas || a.columnas != b.columnas) return null;
        System.out.println("realizando resta");

        Matrix resultado = new Matrix(a.filas, a.columnas);

        for(int i=0; i<a.filas ; i++) {
            for(int j=0; j<a.columnas ; j++) {
                int res = a.get(i, j) - b.get(i, j);
                resultado.set(i, j, res);
            }
        }


        return resultado;
    }

    @Override
    public Matrix multiplicar(Matrix a, Matrix b) throws RemoteException {
        if(a.columnas != b.filas) return null;


        Matrix resultado = new Matrix(a.filas, b.columnas);

        for(int i=0 ; i<a.filas ; i++) {
            for(int j=0 ; j<b.columnas ; j++) {

                int res = 0;
                for(int h=0 ; h<a.columnas ; h++) {
                    for(int k=0 ; k<b.filas ; k++) {
                        res += a.get(i, h)*b.get(j, k); //TODO cambiar que se itere la segunda parte de b (columnas primero) LOL
                    }
                }


                resultado.set(i, j, res);


            }
        }





        return resultado;
    }

    @Override
    public int determinante(Matrix a) throws RemoteException {

        int[][] shit = new int[a.filas][a.columnas];
        for(int i=0 ; i<a.filas ; i++) {
            for(int j=0 ; j<a.columnas ; j++) {
                shit[i][j] = a.get(i,j);
            }
        }

        return determin(shit);
    }

    private static int determin(int matriz[][]) {
        int deter = 0;
        int i, mult = 1;
        if (matriz.length > 2) {
            for (i = 0; i < matriz.length; i++) {
                deter += mult * matriz[i][0] * determin(submatrize(matriz, i, 0));
                mult *= -1;
            }
            return deter;
        } else
            return matriz[0][0] * matriz[1][1] - matriz[0][1] * matriz[1][0];
    }

    private static int[][] submatrize(int matriz[][], int x, int y) {
        int submatriz[][] = new int[matriz.length - 1][matriz.length - 1];
        int i, j, cur_x = 0, cur_y = 0;
        for (i = 0; i < matriz.length; i++) {
            if (i != x) {
                cur_y = 0;
                for (j = 0; j < matriz.length; j++) {
                    if (j != y) {
                        submatriz[cur_x][cur_y] = matriz[i][j];
                        cur_y++;
                    }
                }
                cur_x++;
            }
        }
        return submatriz;
    }

    public Matrix adjunto(Matrix a, int f, int c) {
        Matrix adj = new Matrix(a.filas-1, a.columnas-1);
        
        for(int i=0; i<adj.filas ; i++) {
            for(int j=0; j<adj.columnas ; j++) {
                if(i<f) {
                    if(j<c){
                        adj.set(i,j, a.get(i,j));
                    } else {
                        adj.set(i,j, a.get(i,j+1));
                    }
                } else {
                    if(j<c){
                        adj.set(i,j, a.get(i+1,j));
                    } else {
                        adj.set(i,j, a.get(i+1,j+1));
                    }
                }
            }
        }


        return adj;
    }

    @Override
    public Matrix convolucion(Matrix a, Matrix b) throws RemoteException {
        // TODO Auto-generated method stub
        return null;
    }

    public String sayHello() throws RemoteException {
        return "Hello, im working";
    }

    


    public static void main(String[] args) throws RemoteException, MalformedURLException {
        MatrixServer ms = new MatrixServer();
        // Registry reg = LocateRegistry.createRegistry(5670);
        Naming.rebind("rmi://localhost:5670/MatrixAlgebra", ms);
        System.out.println("Ready to say Hello and calculate matrix");
    }
}
