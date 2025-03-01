package org.example.mydocs_4_0;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

public class UserManager implements LoginAcceptable {
//    private static final String FILE_PATH = "user.json";
//    private static final ObjectMapper objectMapper = new ObjectMapper();
//
//    public static void saveUsers(HashMap<String, User> users){
//        try{
//            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(FILE_PATH), users);
//            System.out.println("User data saved successfully.");
//        }catch (IOException e){
//            System.err.println("Error writing to JSON file: " + e.getMessage());
//
//        }
//    }
//
//    //load users from a JSON file (deserialization, reads JSON data from the file and converts it into HashMap<String,User>)
//    public static HashMap<String,User> loadUsers(){
//        try{
//            File file = new File(FILE_PATH);
//            if (file.exists()){
//                return objectMapper.readValue(file,objectMapper.getTypeFactory().constructMapType(HashMap.class,String.class, User.class));
//            }else{
//                return new HashMap<>();
//            }
//
//        }catch (FileNotFoundException e) {
//            System.err.println("File not found: " + e.getMessage());
//            return new HashMap<>();
//        }catch (JsonProcessingException e) {
//            System.err.println("Error processing JSON data: " + e.getMessage());
//            return new HashMap<>();
//        }catch(IOException e){
//            System.err.println("Error reading from JSON file: " + e.getMessage());
//            return new HashMap<>();
//        }
//    }


    @Override
    public  boolean login( String firstName, String lastName,String password, String userType) throws InvalidLoginException{
        Server server = new Server();
        if (server.searchUser(firstName, lastName, password, userType)) {
            System.out.println("Login successful for " + firstName + " " + lastName);
            return true;
        } else {
            throw new InvalidLoginException("Invalid login credentials for " + firstName + " " + lastName);
        }

    }

    public static boolean alreadyExists(String firstName, String lastName, String userType){
        Server server = new Server();
        if(server.exists(firstName,lastName,userType)){
            return true;
        }else{
            return false;
        }
    }

    public static void createUser(User user){
        Server server = new Server();
        server.createUser(user);
        server.printAllUsers();
        System.out.println("User added");

    }

    public static void deleteUser(String firstName, String lastName, String userType, int id) {
        Server server = new Server();
        server.deleteUser(firstName,lastName,userType,id);
    }

}

