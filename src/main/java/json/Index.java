package json;

import file.FilesNavigator;
import models.FilePath;

import java.io.*;
import java.nio.file.*;
import java.util.Map;

public class Index {

    public static boolean indexProperty(String schemaPath, String property) throws IOException {
        if (Schema.validateProperty(schemaPath + "schema.json", property)) { //Validate if the prop exists in schema
            String indexDir = schemaPath.substring(0, schemaPath.lastIndexOf("/")) + "_ind_" + property; //Create dir of index
            FilesNavigator.createDirectory(indexDir);
            FilesNavigator.createFile(indexDir + "/ind_" + property + ".txt");
            FileWriter fw = new FileWriter(indexDir + "/ind_" + property + ".txt");
            BufferedWriter bw = new BufferedWriter(fw);

            for (int i = 1; i < FilesNavigator.getFiles(schemaPath).length; i++) {
                if(Files.exists(Paths.get(schemaPath + "/" + i + ".json"))) { //Check if the file exists
                    Map jsonObject = Json.convertToObject(schemaPath + i + ".json");
                    bw.write(jsonObject.get(property) + ":" + schemaPath  + i + ".json");
                    bw.newLine();
                }
            }
            bw.close();
        } else {
            return false;
        }
        return true;
    }

    public static boolean isSchemaIndexed(FilePath filePath) throws FileNotFoundException {
        File[] files = FilesNavigator.getFiles("./dbs/" + filePath.getDatabase());
        for(File file : files){
            if(file.getName().contains((filePath.getSchema() + "_ind_")))
                return true;
        }
        return false;
    }

    public static boolean isPropIndexed(String schemaPath, String property) throws FileNotFoundException {
        return Files.exists(Paths.get(schemaPath.substring(0, schemaPath.lastIndexOf("/")) + "_ind_" + property));
    }
    }

