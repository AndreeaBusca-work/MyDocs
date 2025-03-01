package org.example.mydocs_4_0;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
public class Server {
    String url = "jdbc:sqlite:mydocs.db";

    String createTableUsersSQL = "CREATE TABLE IF NOT EXISTS Users (" +
            "firstName TEXT NOT NULL, " +
            "lastName TEXT NOT NULL, " +
            "hashedPassword TEXT NOT NULL, " +
            "userType TEXT NOT NULL, " +
            "id INTEGER PRIMARY KEY, " +
            "UNIQUE(firstName, lastName, hashedPassword))";

    String createTableDocumentsSQL = "CREATE TABLE IF NOT EXISTS Documents (" +
            "name TEXT NOT NULL, " +
            "documentType TEXT NOT NULL, " +
            "hashedOwnerName TEXT NOT NULL, " +
            "content TEXT NOT NULL, " +
            "documentSize INTEGER PRIMARY KEY)";

    User admin = new User("Admin_Name", "Admin_Name", "password", UserType.admin, 1);

    public void initializeDatabase() {
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                // Create Users table
                try (PreparedStatement stmt = conn.prepareStatement(createTableUsersSQL)) {
                    stmt.executeUpdate();
                }

                // Create Documents table
                try (PreparedStatement stmt = conn.prepareStatement(createTableDocumentsSQL)) {
                    stmt.executeUpdate();
                }

                // Insert admin user
                String insertSQL = "INSERT INTO Users (firstName, lastName, hashedPassword, userType, id) " +
                        "VALUES(?, ?, ?, ?, ?)";
                try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
                    pstmt.setString(1, admin.getFirstName());
                    pstmt.setString(2, admin.getLastName());
                    pstmt.setString(3, admin.getHashedPassword());
                    pstmt.setString(4, admin.getType().toString());
                    pstmt.setInt(5, admin.getId());
                    pstmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
        }
    }

    public boolean searchUser(String firstName, String lastName, String password, String userType) {
        String query = "SELECT * FROM Users WHERE firstName = ? AND lastName = ? AND userType = ?";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3,userType);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String storedHashedPassword = rs.getString("hashedPassword");

                // If a user is found, return the true
                return BCrypt.checkpw(password,storedHashedPassword);
            } else {
                System.out.println("User not found.");
                return false; // Return false if no user is found
            }

        } catch (SQLException e) {
            System.err.println("Error while searching for user: " + e.getMessage());
            return false;
        }
    }

    public boolean exists(String firstName, String lastName, String userType){
        String query = "SELECT * FROM Users WHERE firstName = ? AND lastName = ? AND userType = ?";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3,userType);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {

                return true;
            } else {
                return false; // Return false if no user is found
            }

        } catch (SQLException e) {
            System.err.println("Error while searching for user: " + e.getMessage());
            return false;
        }
    }

    public void printAllUsers() {
        String query = "SELECT * FROM Users";
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            // Loop through all users and print their details
            while (rs.next()) {
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                String hashedPassword = rs.getString("hashedPassword");
                String userType = rs.getString("userType");
                int id = rs.getInt("id");

                // Print user information
                System.out.println("User ID: " + id + ", First Name: " + firstName + ", Last Name: " + lastName +
                        ", UserType: " + userType + ", Hashed Password: " + hashedPassword);
            }

        } catch (SQLException e) {
            System.err.println("Error while retrieving users: " + e.getMessage());
        }
    }

    public boolean createUser(User user){
        String query = "INSERT INTO Users (firstName, lastName, hashedPassword, userType) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, user.getFirstName());
            pstmt.setString(2, user.getLastName());
            pstmt.setString(3, user.getHashedPassword());  // Store the hashed password
            pstmt.setString(4, user.getType().toString());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User created successfully.");
                return true;
            } else {
                System.out.println("Failed to create user.");
                return false;
            }

        } catch (SQLException e) {
            System.err.println("Error while creating user: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteUser(String firstName, String lastName, String userType, int id){
        String deleteSQL = "DELETE FROM Users WHERE firstName = ? AND lastName = ? AND userType = ? AND id = ?";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(deleteSQL)) {

            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, userType);
            pstmt.setInt(4, id);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User " + firstName + " " + lastName + " deleted successfully.");
                return true;
            } else {
                System.out.println("No user found with the name " + firstName + " " + lastName + ".");
                return false;
            }

        } catch (SQLException e) {
            System.err.println("Error while deleting user: " + e.getMessage());
            return false;
        }


    }

    public boolean addDocument(Document document){
        String query = "INSERT INTO Documents (name, documentType, hashedOwnerName, content, documentSize) VALUES (?, ?, ?, ?,?)";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, document.getName());
            pstmt.setString(2, document.getType().toString());
            pstmt.setString(3, document.getHashedOwnerName());  // Store the hashed owner
            pstmt.setString(4, document.getContent());
            pstmt.setInt(5, (int) document.getDocumentSize());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Document added successfully.");
                return true;
            } else {
                System.out.println("Failed to add document.");
                return false;
            }

        } catch (SQLException e) {
            System.err.println("Error while adding document: " + e.getMessage());
            return false;
        }
    }

    public String[] searchDocument(String name, String ownerName){
        String query = "SELECT * FROM Documents WHERE name = ?";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, name);

            ResultSet rs = pstmt.executeQuery();

            while(rs.next()) {
                String hashedOwnerName = rs.getString("hashedOwnerName");

                if(BCrypt.checkpw(ownerName,hashedOwnerName)){
                    String content = rs.getString("content");
                    String documentType = rs.getString("documentType");

                    String[] info = new String[2];
                    info[0] = content;
                    info[1] = documentType;

                    // If a document is found, return its type and content
                    return info;
                }

                if(!rs.next()){
                    System.out.println("Document not found.");
                    String[] info = new String[2];
                    info[0] = "";
                    info[1] = "";
                    return info; // Return false if no user is found
                }

            }

        } catch (SQLException e) {
            System.err.println("Error while searching for document: " + e.getMessage());
            String[] info = new String[2];
            info[0] = "";
            info[1] = "";
            return info;
        }

        String[] info = new String[2];
        info[0] = "";
        info[1] = "";
        return info;

    }

    public boolean deleteDocument(String documentName, String ownerName) {
        String query = "SELECT * FROM Documents WHERE name = ?";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, documentName);

            ResultSet rs = pstmt.executeQuery();

            // If no document is found with the given name
            if (!rs.next()) {
                System.out.println("Document not found.");
                return false;
            }

            // Iterate through all matching documents with the same name
            do {
                String hashedOwnerName = rs.getString("hashedOwnerName");

                // Check if the provided owner name matches the stored hashed owner name
                if (BCrypt.checkpw(ownerName, hashedOwnerName)) {
                    String deleteSQL = "DELETE FROM Documents WHERE name = ? AND hashedOwnerName = ?";
                    try (PreparedStatement pstmt2 = conn.prepareStatement(deleteSQL)) {
                        pstmt2.setString(1, documentName);
                        pstmt2.setString(2, hashedOwnerName);
                        int rowsAffected = pstmt2.executeUpdate();
                        if (rowsAffected > 0) {
                            System.out.println("Document " + documentName + " deleted successfully.");
                            return true;
                        } else {
                            System.out.println("Failed to delete document " + documentName + ".");
                            return false;
                        }
                    }
                }

            } while (rs.next());  // Continue checking all matching documents

            // If no match is found for the owner name
            System.out.println("No document found with the specified owner.");
            return false;

        } catch (SQLException e) {
            System.err.println("Error while searching for document: " + e.getMessage());
            return false;
        }
    }

}
