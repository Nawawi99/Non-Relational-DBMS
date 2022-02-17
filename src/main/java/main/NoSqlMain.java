package main;

import file.FilesNavigator;
import json.Database;
import json.Index;
import json.Json;
import json.Schema;
import login.Account;
import login.AccountUtils;
import models.FilePath;

import java.io.*;
import java.util.*;

public class NoSqlMain {
    static Scanner scan = new Scanner(System.in);
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {
        while (true) {
            System.out.println("Choose the operation number to:\n1.READ\n2.WRITE\n3.MANAGE USERS\n4.CREATE DATABASE\n5.EXIT");
            String answer = scan.next().trim();
            switch (answer) {
                case "1":
                    read();
                    break;
                case "2":
                    write();
                    break;
                case "3":
                    AccountUtils.printAccountsDetails();
                    break;
                case "4":
                    createDB();
                case "5":
                    return;
                default:
                    System.out.println("Please input a valid option");
            }
        }
    }

    public static void read() throws IOException {
        FilePath filePath = readPathFromUser();
        if (Index.isSchemaIndexed(filePath)) {
            readFromIndex(filePath);
        } else {
            File[] documents = FilesNavigator.getFiles(filePath.toString());
            for (int i = 0; i < documents.length - 1; i++) {
                printJson(documents[i]);
            }
        }
    }

    public static void write() throws IOException {
        FilePath filePath = readPathFromUser();
        int fileId = FilesNavigator.getFiles(filePath.toString()).length;
        System.out.println("Enter the json to be added");
        String json = br.readLine();
        boolean validFile = Json.addJson(json, filePath.toString() + fileId + ".json");
        if (validFile) {
            System.out.println("File is valid and successfully added");
        } else {
            System.out.println("File isn't valid and will be deleted");
        }
    }

    private static FilePath readPathFromUser() throws FileNotFoundException {
        FilePath filePath = new FilePath();

        List<String> databases = Database.getDatabases();
        Database.printDatabases(databases);
        String database = Database.isValidDatabase(databases);
        filePath.setDatabase(database);

        List<String> schemas = Schema.getSchemas(filePath.getDatabase());
        Schema.printSchemas(schemas);
        String schema = Schema.isValidSchema(schemas);
        filePath.setSchema(schema);

        return filePath;
    }

    private static void printJson(File document) throws FileNotFoundException {
        String fileName = document.getName();
        String filePath = document.getPath();
        int id = FilesNavigator.getId(fileName);
        System.out.println("Document no. " + id);
        Json.printJson(filePath);
        System.out.println("********************");
    }

    private static void readFromIndex(FilePath filePath) throws IOException {
        String line;
        File[] files = FilesNavigator.getFiles("./dbs/" + filePath.getDatabase() + "/");
        String indexedFile = "";
        for (File file : files) {
            if (file.getName().contains((filePath.getSchema() + "_ind_")))
                indexedFile = file.getName();
        }
        String[] fileStruct = indexedFile.split("_");
        br = new BufferedReader(new FileReader("./dbs/" + filePath.getDatabase() + "/"
                + indexedFile + "/" + fileStruct[1] + "_" + fileStruct[2] + ".txt"));
        while ((line = br.readLine()) != null) {
            String value = line.substring(0, line.lastIndexOf(":"));
            String file = line.substring(line.lastIndexOf(":")).replace(":", "");
            Json.printJson(file);
        }
    }

    private static void createDB() throws IOException {
        System.out.println("Enter database name: ");
        String database = scan.next().trim();
        if(Database.createDatabase(database)){
            System.out.println("Sucess");
        } else {
            System.out.println("Name already in use");
            createDB();
        }
        System.out.println("Enter schema name:");
        String schema = scan.next().trim();
        if(Schema.createSchema(database, schema)){
            System.out.println("scues");
        } else {
            System.out.println("Name already in use");
            createDB();
        }
        FilePath filePath = new FilePath();
        filePath.setDatabase(database);
        filePath.setSchema(schema);
        System.out.println("Enter schema:");
        Schema.writeSchema("{\n" +
                "  \"type\": \"object\",\n" +
                "  \"properties\": {\n" +
                "    \"fName\": {\n" +
                "      \"type\": \"string\"\n" +
                "    },\n" +
                "    \"lName\": {\n" +
                "      \"type\": \"string\"\n" +
                "    }\n" +
                "  },\n" +
                "  \"required\": [\n" +
                "    \"fName\",\n" +
                "\"lName\"\n" +
                "  ]\n" +
                "}", filePath.toString() + "/");

    }
}

