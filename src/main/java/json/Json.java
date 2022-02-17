package json;

import file.FilesNavigator;
import com.google.gson.*;
import java.io.*;
import java.util.*;

import static json.Schema.gson;

public class Json {

    public static String convertToString(String filePath) throws FileNotFoundException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
        JsonObject jsonObject = gson.fromJson(bufferedReader, JsonObject.class);
        String json = gson.toJson(jsonObject);
        return json;
    }

    public static HashMap<String, Object> convertToObject(String filePath) throws FileNotFoundException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        HashMap<String, Object> json = gson.fromJson(bufferedReader, HashMap.class);
        return json;
    }

    public static void printJson(String filePath) throws FileNotFoundException {
        String json = convertToString(filePath);
        System.out.println(json);
    }

    public static boolean addJson(String json, String filePath) throws IOException {
        FilesNavigator.createFile(filePath);
        BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));
        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
        json = gson.toJson(jsonObject);
        bw.write(json);
        bw.flush();
        bw.close();
        return JsonValidator.isValid(filePath);
    }

}
