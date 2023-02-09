package p1.v1;

import java.io.IOException;

public class RunCopyFile {

	public static void main(String[] args) throws IOException {
		CopyFile cf = new CopyFile();
		// Ruta por defecto es la raiz del projecto (antes del src)
		// String
		// ruta="C:\\Users\\alumno\\Desktop\\planificacion\\practica1\\src\\pictures\\";
		String ruta = "src\\main\\resources\\pictures\\";
		// cf.copiar(ruta+"texto.txt", "textoCopiadoRutaAbsoluta.txt");
		cf.copiar("src\\main\\resources\\pictures\\texto.txt", "textoCopiadoRutaRelativa.txt");

		cf.copiarBuffer(ruta + "texto.txt", "textoCopiadoConBuffer.txt");
	}
}
