package service;

import java.io.*;
import java.nio.file.*;

public class WriterService {

    private static WriterService INSTANCE;
    private WriterService() {}

    public static WriterService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new WriterService();
        }
        return INSTANCE;
    }

    public void writeInFile(String path, String text){
        try {
            BufferedWriter writer = Files.newBufferedWriter(Paths.get(path), StandardOpenOption.APPEND);
            writer.write(text + "\n");
            writer.flush();
        } catch (FileNotFoundException e) {
            System.out.print("The file path '" + path + "' can't be found.");
        } catch (IOException e) {
            System.out.println(e.getClass() + " " + e.getMessage());
        }

    }
}
