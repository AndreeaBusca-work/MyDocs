package org.example.mydocs_4_0;


import org.mindrot.jbcrypt.BCrypt;

import java.io.File;
import java.io.Serializable;

public class Document implements Comparable<Document>, Serializable, MemorySizeAcceptable {

    private String name;
    private DocumentType type;
    //private LocalDate additionDate;
    private String hashedOwnerName;
    private double documentSize;
    private String content;

    public Document(){

    }

    public Document(String name, DocumentType type, String ownerName, String filePath, String content){
        this.name = name;
        this.type = type;
        //this.additionDate = additionDate;
        this.hashedOwnerName = hashOwnerName(ownerName);
        this.documentSize = new File(filePath).length() / 1024.0;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DocumentType getType() {
        return type;
    }

    public void setType(DocumentType type) {
        this.type = type;
    }

//    public LocalDate getAdditionDate() {
//        return additionDate;
//    }
//
//    public void setAdditionDate(LocalDate additionDate) {
//        this.additionDate = additionDate;
//    }

    public String getHashedOwnerName() {
        return hashedOwnerName;
    }

    public void setHashedOwnerName(String hashedOwnerName) {
        this.hashedOwnerName = hashedOwnerName;
    }

    public double getDocumentSize() {
        return documentSize;
    }

    public void setDocumentSize(double documentSize) {
        this.documentSize = documentSize;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public static String hashOwnerName(String ownerName){
        return BCrypt.hashpw(ownerName, BCrypt.gensalt(12));
    }

    public  boolean checkOwner(String ownerName){
        return BCrypt.checkpw(ownerName,this.hashedOwnerName);
    }

    @Override
    public int compareTo(Document other) {
        return this.hashedOwnerName.compareTo(other.hashedOwnerName);
    }

    public boolean memoryRequirementsMet(){
        return this.documentSize <= 50;
    }
}

