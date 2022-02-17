package json;

import file.FilesNavigator;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Database {
    public static boolean createDatabase(String databaseName)
            throws IOException {
        if(FilesNavigator.createDirectory("./dbs/" + databaseName)) {
            return true;
        }
        return false;
    }

    public static List<String> getDatabases() throws FileNotFoundException {
        List<String> databaseNames = new ArrayList<>();
        File[] databases = FilesNavigator.getFiles("./dbs/");
        for(File database : databases){
            databaseNames.add(String.valueOf(database)
                    .substring(String.valueOf(database).lastIndexOf("\\"))
                    .replace("\\", ""));
        }
        return databaseNames;
    }

    public static String isValidDatabase(List<String> databases){
        boolean validInput = false;
        Scanner scan = new Scanner(System.in);
        while(!validInput) {
            System.out.println("\nEnter the database name: ");
            String database = scan.next();
            for(String d : databases){
                if(d.equalsIgnoreCase(database)){
                    return database;
                }
            }
        }
        return null;
    }

    public static void printDatabases(List<String> databases){
        for (String database : databases) {
            System.out.print(database + "   ");
        }
    }

    public static void dropDatabase(String database) throws IOException {
        FilesNavigator.deleteDirectory("./dbs/" + database);
    }
}
