package util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;

public class FileOutput {

    public static void outputStringToFile(final String folderName, final String fileName, final String data) throws IOException {
        try {
            Files.write(Paths.get(folderName + fileName), data.getBytes());
        } catch (NoSuchFileException nsfe) {
            System.err.println("Write error: did not find output folder " + folderName);
            throw nsfe;
        }
    }
}
