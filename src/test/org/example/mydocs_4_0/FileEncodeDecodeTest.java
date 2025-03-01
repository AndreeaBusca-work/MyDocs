package org.example.mydocs_4_0;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class FileEncodeDecodeTest {

    private File testFile;

    @BeforeEach
    void setUp() throws IOException {
        // Create a temporary test file with some content
        testFile = File.createTempFile("test", ".txt");
        testFile.deleteOnExit();
    }

    @Test
    void testEncodeFileToBase64_validFile() throws IOException {
        // Prepare content to write to the file
        String fileContent = "Hello, world!";
        try (FileOutputStream fileOutputStream = new FileOutputStream(testFile)) {
            fileOutputStream.write(fileContent.getBytes());
        }

        // Call the method to encode the file to Base64
        String encodedString = FileEncodeDecode.encodeFileToBase64(testFile);

        // Assert that the encoded string is not null or empty
        assertNotNull(encodedString, "Encoded string should not be null.");
        assertFalse(encodedString.isEmpty(), "Encoded string should not be empty.");

        // Optionally, verify that the content is Base64-encoded (this can be checked by decoding back)
        assertTrue(encodedString.matches("^[A-Za-z0-9+/=]+$"), "Encoded string should match Base64 pattern.");
    }

    @Test
    void testEncodeFileToBase64_invalidFile() {
        // Test with a non-existent file
        File nonExistentFile = new File("non_existent_file.txt");

        String result = FileEncodeDecode.encodeFileToBase64(nonExistentFile);

        // Assert that the result is null since the file does not exist
        assertNull(result, "Encoded string should be null for a non-existent file.");
    }

    @Test
    void testDecodeFileFromBase64_validBase64() throws IOException {
        // Prepare a Base64 string (from the file content "Hello, world!")
        String base64String = "SGVsbG8sIHdvcmxkIQ==";  // Base64 encoded "Hello, world!"

        // Create a temporary output file
        File outputFile = File.createTempFile("output", ".txt");
        outputFile.deleteOnExit();

        // Call the method to decode the Base64 string to a file
        boolean result = FileEncodeDecode.decodeFileFromBase64(base64String, outputFile);

        // Assert that the file was written successfully
        assertTrue(result, "File should be decoded and saved successfully.");

        // Optionally, check the content of the output file
        String decodedContent = new String(java.nio.file.Files.readAllBytes(outputFile.toPath()));
        assertEquals("Hello, world!", decodedContent, "Decoded file content should match the original content.");
    }

    @Test
    void testDecodeFileFromBase64_invalidFilePath() throws IOException {
        // Prepare a Base64 string (valid content)
        String base64String = "SGVsbG8sIHdvcmxkIQ==";  // Base64 encoded "Hello, world!"

        // Create a file that cannot be written to (e.g., by using a read-only file or a non-existent path)
        File invalidFilePath = new File("/non/existent/path/output.txt");

        // Call the method to decode the Base64 string to the invalid path
        boolean result = FileEncodeDecode.decodeFileFromBase64(base64String, invalidFilePath);

        // Assert that the decoding failed
        assertFalse(result, "Decoding should fail for an invalid file path.");
    }
}