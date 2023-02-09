package practica1_2;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.*;

public class RunDirOps {
    public static void main(String[] args) throws IOException, URISyntaxException {
        DirOps dos = new DirOps();
        Path root = FileSystems.getDefault().getPath("src\\main\\resources\\pictures");
        dos.listar(root);
        Path subdir = dos.crear("directorioConCadenas");
        dos.listar(root);
        Path img = Paths.get(new URI("src/main/resources/pictures/cat.jpg"));
        System.out.println(img.toAbsolutePath().toString());

    }
}
