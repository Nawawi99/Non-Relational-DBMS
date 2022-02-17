package login;

import json.Json;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class AccountUtils {
    public static void changeDefaultAdminPassword() throws IOException {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter a new password: ");
        String newPassword = scan.next();
        Map<String, Object> defaultAdmin = Json.convertToObject("./accounts/admins/default.json");
        BufferedWriter bw = new BufferedWriter(new FileWriter("./accounts/admins/default.json"));
        defaultAdmin.put("password", newPassword);
        defaultAdmin.put("passwordChanged", true);
        Json.addJson(defaultAdmin.toString(), "./accounts/admins/default.json");
        bw.close();
    }

    public static void printAccountsDetails() throws IOException {
        Scanner scan = new Scanner(System.in);
        Map defaultAdmin = Json.convertToObject("./accounts/admins/default.json");
        System.out.println("Enter your username: ");
        String username = scan.next().trim().toLowerCase();
        System.out.println("Enter your password: ");
        String password = scan.next().trim();
        if (defaultAdmin.get("username").equals(username) && defaultAdmin.get("password").equals(password)) {
            if (defaultAdmin.get("passwordChanged").equals(false)) {
                System.out.println("Password should be changed");
                AccountUtils.changeDefaultAdminPassword();
            } else {
                String path = "./accounts/";
                List<Map> admins = Account.getAccounts(path + "admins");
                List<Map> users = Account.getAccounts(path + "users");
                System.out.println("Admins");
                Account.printAccounts(admins);
                System.out.println("Users");
                Account.printAccounts(users);
            }
        } else {
            System.out.println("Permission not allowed or wrong password");
            printAccountsDetails();
        }
    }
}
