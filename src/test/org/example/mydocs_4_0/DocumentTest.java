package org.example.mydocs_4_0;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DocumentTest {

    @Test
    void testConstructor(){
        String name = "Document";
        DocumentType type = DocumentType.pdf;
        String ownerName = "Petra Nea";
        String filePath = "C:\\Users\\ADINA\\Downloads\\Homework_Lecture_9.pdf";
        String content = "This is the document content";

        Document document = new Document(name, type, ownerName, filePath, content);

        // Assert that the name is set correctly
        assertEquals(name, document.getName());

        // Assert that the type is set correctly
        assertEquals(type, document.getType());

        // Assert that the owner name is hashed
        assertNotNull(document.getHashedOwnerName());

        // Assert that the document size is correctly calculated (in KB)
        assertTrue(document.getDocumentSize() > 0);

        // Assert that the content is set correctly
        assertEquals(content, document.getContent());

    }


    @Test
    void hashOwnerName() {
        String ownerName = "Petra Nea";
        String hashedOwnerName = Document.hashOwnerName(ownerName);

        assertNotEquals(ownerName,hashedOwnerName,"Hashed name should be different from the actual plain text name. ");
        assertTrue(hashedOwnerName.startsWith("$2a$12$"));
    }

    @Test
    void checkOwner() {
        String ownerName = "Petra Nea";
        String hashedOwnerName = Document.hashOwnerName(ownerName);
        Document document = new Document();
        document.setHashedOwnerName(hashedOwnerName);

        assertTrue(document.checkOwner(ownerName),"checkOwner should return true only when the name of the owner is correct.");
        assertFalse(document.checkOwner("WrongName"));

    }
}