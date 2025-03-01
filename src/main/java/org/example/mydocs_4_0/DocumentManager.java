package org.example.mydocs_4_0;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

public class DocumentManager {

    // Create a new document and add it to the map
    public static void addDocument(Document document) {
        Server server = new Server();
        server.addDocument(document);
        System.out.println("Document added successfully.");
    }

    public static void deleteDocument(String documentName, String ownerName) {
       Server server = new Server();
       server.deleteDocument(documentName,ownerName);
    }

    public static String[] searchDocument(String documentName, String ownerName){
        Server server = new Server();
        return server.searchDocument(documentName,ownerName);
    }

    public static HashMap<String, Document> getDocumentsByOwner(HashMap<String, Document> documents, String ownerName){
        HashMap<String,Document> ownerDocuments = new HashMap<>();
        for (Document document : documents.values()) {
            // Check if the document name starts with the base name and matches the owner
            if (document.checkOwner(ownerName)) {
                ownerDocuments.put(document.getName(),document);
            }
        }
        return ownerDocuments;

    }
}

