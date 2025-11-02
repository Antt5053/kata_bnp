package utils;

import java.nio.file.Files;
import java.nio.file.Paths;

public class ReadJSON {
    public static String readJsonFromFile(String file) {
        try {
            return new String(Files.readAllBytes(Paths.get("src/test/resources/payloads/" + file)));
        } catch (Exception e) {
            throw new RuntimeException("Failed to read JSON file: " + file, e);
        }
    }
}
