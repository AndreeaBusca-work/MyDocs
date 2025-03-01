package org.example.mydocs_4_0;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testConstructorInitialization() {
        String firstName = "Petra";
        String lastName = "Nea";
        String password = "password123";
        UserType type = UserType.admin;
        int id = 1;

        User user = new User(firstName, lastName, password, type, id);

        assertEquals(firstName, user.getFirstName(), "First name should be initialized correctly.");
        assertEquals(lastName, user.getLastName(), "Last name should be initialized correctly.");
        assertNotNull(user.getHashedPassword(), "Password should be hashed.");
        assertEquals(type, user.getType(), "User type should be initialized correctly.");
        assertEquals(id, user.getId(), "ID should be initialized correctly.");
    }


    @Test
    void hashPassword() {
        String password = "verySecurePassword";
        String hashedPassword = User.hashPassword(password);

        assertNotEquals(password,hashedPassword,"Hashed password should be different from the actual plain text password. ");
        assertTrue(hashedPassword.startsWith("$2a$12$"));
    }

    @Test
    void checkPassword() {
        String password = "verySecurePassword";
        User user = new User("Petra","Nea",password,UserType.citizen,2);

        assertTrue(user.checkPassword(password),"checkPassword should return true for the correct password");
        assertFalse(user.checkPassword("entirelyWrongPassword"),"checkPassword should return false if the given password does not match the correct password");
    }
}