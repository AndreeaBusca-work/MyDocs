package org.example.mydocs_4_0;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

public class FileEncodeDecode {
    public static String encodeFileToBase64(File file) {
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            byte[] fileContent = new byte[(int) file.length()];
            fileInputStream.read(fileContent);
            return Base64.getEncoder().encodeToString(fileContent);
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            e.printStackTrace(); // This will provide more information about the error
            return null;
        }
    }

    public static boolean decodeFileFromBase64(String base64String, File outputFile) {
        try {
            // Decode the Base64 string into a byte array
            byte[] decodedBytes = Base64.getDecoder().decode(base64String);

            // Write the decoded bytes to the output file
            try (FileOutputStream fileOutputStream = new FileOutputStream(outputFile)) {
                fileOutputStream.write(decodedBytes);
            }

            System.out.println("File saved successfully: " + outputFile.getAbsolutePath());
            return true;
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}

