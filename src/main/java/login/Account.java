package login;

import file.FilesNavigator;
import json.Json;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Account {
    public static List<Map> getAccounts(String filePath) throws FileNotFoundException {
        File[] files = FilesNavigator.getFiles(filePath);
        List<Map> accounts = new ArrayList<>();
        for (int i = 0; i < files.length - 1; i++) {
            Map jsonObject = Json.convertToObject(filePath + "/" + files[i].getName());
            accounts.add(jsonObject);
        }
        return accounts;
    }

    public static void printAccounts(List<Map> accounts) {
        System.out.println("*******************");
        System.out.println("Username\tPassword");
        for (Map account : accounts) {
            System.out.println(account.get("username") + "\t\t" + account.get("password"));
        }
        System.out.println("*******************");
    }
}
