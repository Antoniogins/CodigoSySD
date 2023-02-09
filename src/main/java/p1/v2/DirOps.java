package p1.v2;

import java.io.IOException;
import java.nio.file.*;

public class DirOps {
    public DirectoryStream<Path> listar(Path dir) throws IOException {
        // Path path = FileSystems.getDefault().getPath(directorio);
        DirectoryStream<Path> ls = Files.newDirectoryStream(dir);
        System.out.println("ls under " + dir.toAbsolutePath().toString() + ":");
        for (Path p : ls)
            System.out.println(p.getFileName());
        return ls;
    }

    public Path crear(String directorio) throws IOException {
        Path dir = FileSystems.getDefault().getPath(directorio);
        // System.out.println(dir.toAbsolutePath());
        return Files.exists(dir) ? dir : Files.createDirectory(dir.toAbsolutePath());
    }

    public void copiar() {

    }

}
