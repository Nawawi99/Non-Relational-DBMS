package file;
import json.Json;
import java.io.*;
import java.util.*;

public class FilesNavigator {

    public static File[] getFiles(String path) throws FileNotFoundException {
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        return listOfFiles;
    }

    public static int getId(String fileName){
        int id = Integer.parseInt(fileName.substring(0, fileName.lastIndexOf('.')));
        return id;
    }

    public static Map<String, Object> getObjectFromJson(String filePath) throws FileNotFoundException {
        Json jsonUtils = new Json();
        Map<String, Object> jsonObject = jsonUtils.convertToObject(filePath);
        return jsonObject;
    }

    public static void createFile(String filePath) throws IOException {
        File newFile = new File(filePath);
        if(!newFile.exists())
            newFile.createNewFile();
    }

    public static boolean createDirectory(String filePath) throws IOException {
        File newDir = new File(filePath);
        if(!newDir.exists()){
            newDir.mkdir();
            return true;
        } else {
            return false;
        }
    }

    public static void deleteDirectory(String filePath) throws IOException {
        File newDir = new File(filePath);
        if(newDir.exists())
            newDir.delete();
    }
}
