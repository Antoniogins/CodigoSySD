package practica1_2;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.*;

public class ChannelsAndBuffers {
    public static void main(String[] args) throws IOException {
        Path ruta = FileSystems.getDefault().getPath("src\\main\\resources\\pictures");

        // TODO pues to do everything XD
        FileChannel fcin = (FileChannel) Files.newByteChannel(ruta);
        FileChannel fcout = (FileChannel) Files.newByteChannel(ruta, StandardOpenOption.CREATE,
                StandardOpenOption.APPEND);

    }
}
