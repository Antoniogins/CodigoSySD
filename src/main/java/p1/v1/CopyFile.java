package p1.v1;

import java.io.*;

public class CopyFile {

	public void copiar(String fileSourceName, String fileDestineName) throws IOException {
		FileReader fr = new FileReader(new File(fileSourceName));
		FileWriter fw = new FileWriter(new File(fileDestineName));

		int car;

		while ((car = fr.read()) != -1) {
			fw.write(car);
			System.out.println(((char) car) + " " + car);
		}
		fr.close();
		fw.close();
	}

	public void copiarBuffer(String fileSourceName, String fileDestineName) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader(new File(fileSourceName)));
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File(fileDestineName)));

		String line = null;

		while ((line = br.readLine()) != null) {
			bw.write(line + "\n\r");
		}

		bw.flush();
		br.close();
		br.close();

	}

}
