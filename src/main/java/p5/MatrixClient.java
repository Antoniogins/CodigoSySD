package p5;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class MatrixClient {
    public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {
        MatrixAlgebra ma = (MatrixAlgebra) Naming.lookup("rmi://localhost:5670/MatrixAlgebra");
        System.out.println("start response from server: "+ma.sayHello()+"\n");


        Matrix a = new Matrix(5,5);
        Matrix b = new Matrix(5, 5);
        for(int i=0; i<5 ; i++){
            for(int o=0; o<5 ; o++) {
                a.set(i, o, (i+1)*(o+1));
                b.set(i, o, (i+8)*(o-2));
            }
        }

        System.out.println("\nMatrix A: ");
        System.out.println(a.toString());

        System.out.println("\nMatrix B: ");
        System.out.println(b.toString()+"\n\n");

        Matrix res = ma.suma(a, b);
        System.out.println("\nA+B");
        System.out.println(res+"\n");

        res = ma.resta(a, b);
        System.out.println("A-B");
        System.out.println(res+"\n");

        res = ma.adjunto(a, 0, 1);
        System.out.println("Adjunto(A) en f1,c1 \n"
                + res + "\n");

        int det = ma.determinante(a);
        System.out.println("|A|="+det+"\n");

        Matrix c = new Matrix(2, 2);
        c.set(0, 0, 55);
        c.set(1, 1, 5);

        System.out.println("\nMatriz C");
        System.out.println(c.toString());
        det = ma.determinante(c);
        System.out.println("|C|="+det);
        System.out.println("|B|="+ma.determinante(b));


        System.out.println("\n\n\n\n");

        Matrix e = new Matrix(new int[][] {{ 2, 0, 1},{ 3, 0, 0},{ 5, 0, 1}});
        Matrix d = new Matrix(new int[][] {{ 1, 0, 1},{ 1, 2, 1},{ 1, 1, 0},});
        

        System.out.println("\nMatrix C: ");
        System.out.println(e.toString());

        System.out.println("\nMatrix D: ");
        System.out.println(d.toString()+"\n\n");

        Matrix f = ma.multiplicar(e, d);
        System.out.println("E*D\n"+f.toString()+"\n");



    }
}
