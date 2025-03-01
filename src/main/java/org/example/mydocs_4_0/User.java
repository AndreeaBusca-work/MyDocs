package org.example.mydocs_4_0;

import org.mindrot.jbcrypt.BCrypt;

import java.io.Serializable;

public class User implements Comparable<User>, Serializable {

    private String firstName, lastName, hashedPassword;
    private UserType type;
    private int id;


    public User() {
    }

    public User(String firstName, String lastName, String password, UserType type, int id){
        this.firstName = firstName;
        this.lastName = lastName;
        this.hashedPassword = hashPassword(password);
        this.type = type;
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String FullName(){
        return firstName + " " + lastName;
    }

    @Override
    public int compareTo(User other) {
        return this.FullName().compareTo(other.FullName());
    }

    public static String hashPassword(String password){
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    public boolean checkPassword(String password){
        return BCrypt.checkpw(password,this.hashedPassword);
    }
}

