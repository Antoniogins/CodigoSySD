package practica1_2;

import java.io.IOException;
import java.nio.file.*;

public class FilesAndMore {
    public static void main(String[] args) throws IOException {
        String rootDir = "src\\main\\resources\\pictures";
        Path rootpath = FileSystems.getDefault().getPath(rootDir);
        Path abs = rootpath.toAbsolutePath();
        Path img = FileSystems.getDefault().getPath(abs.toString(), "cat.jpg");
        System.out.println(rootDir);
        System.out.println(rootpath);
        System.out.println(abs);
        System.out.println(img);
        System.out.println("File: " + img + " exists: " + Files.exists(img));
        System.out.println("File: " + img + " is writeable: " + Files.isWritable(img));

        System.out.println("\n------------------------------------\n------------------------------------\n");
        DirectoryStream<Path> directorio = Files.newDirectoryStream(rootpath);
        System.out.println("ls under " + rootpath);
        for (Path p : directorio)
            System.out.println(p.getFileName());

    }
}
