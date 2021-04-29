package service;

import java.io.*;
import java.nio.file.*;

public class ReaderService {

    private static ReaderService INSTANCE;
    private ReaderService() {}

    public static ReaderService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ReaderService();
        }
        return INSTANCE;
    }


    public String readFile(String path){
        StringBuilder content = new StringBuilder();

        try {
            BufferedReader reader = Files.newBufferedReader(Paths.get(path));
            String line = reader.readLine();
            line = reader.readLine();
            while(line != null) {
                content.append(line).append("\n");
                line = reader.readLine();
            }

        } catch (FileNotFoundException e) {
            System.out.print("The file at '" + path + "' can't be found.");
        } catch (IOException e) {
            System.out.println(e.getClass() + " " + e.getMessage());
        }

        return content.toString();
    }
}
