package org.example.mydocs_4_0;

import java.util.HashMap;

public interface LoginAcceptable {

    boolean login(String firstName, String lastName,String password, String userType) throws InvalidLoginException;
}

