package p1.v2;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.*;

public class RunDirOps {
    public static void main(String[] args) throws IOException, URISyntaxException {
        // DirOps dos = new DirOps();
        // Path root = FileSystems.getDefault().getPath("src\\main\\resources\\pictures");
        // dos.listar(root);
        // Path subdir = dos.crear("directorioConCadenas");
        // dos.listar(root);
        // Path img = Paths.get(new URI("src/main/resources/pictures/cat.jpg"));
        // System.out.println(img.toAbsolutePath().toString());


        //Above is wrong, and wrong is not acceptable :)

        Path projectPath = Paths.get("../ProyectoFinal", args);

        System.out.println(projectPath.toAbsolutePath());
        System.out.println(projectPath.toAbsolutePath().normalize());
        projectPath=projectPath.toAbsolutePath().normalize();
        
        for(int i=0 ; i<projectPath.getNameCount() ; i++ ) {
            System.out.println(projectPath.getName(i));
        }

        //so getName is like taking the string of path and split() the strings (kind of)

    }
}
