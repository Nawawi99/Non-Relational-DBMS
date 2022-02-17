package login;

import file.FilesNavigator;
import json.Json;
import java.io.*;
import java.util.*;

public class LoginValidator {

    public static boolean isValid(String username, String password) throws FileNotFoundException {
        boolean loginSuccess = false;
        while (!loginSuccess){
            File[] admins = FilesNavigator.getFiles("./users/admins");
            for(int i = 0; i < admins.length - 1; i++){
                Map jsonObject = Json.convertToObject(admins[i].getPath());
                if(jsonObject.get("username").equals(username) && jsonObject.get("password").equals(password)){
                    System.out.println("Login Successful, Welcome " + username);
                    loginSuccess = true;
                    break;
                }
            }
            if(!loginSuccess){
                System.out.println("Login failed, please try again!");
                return false;
            }
        }
        return true;
    }

}
