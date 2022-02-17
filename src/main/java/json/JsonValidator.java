package json;

import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.*;
import java.io.*;

public class JsonValidator {
    static JSONObject jsonObject = null;

    public static boolean isValid(String filePath) throws IOException {
        File schemaFile = new File(filePath.substring(0, filePath.lastIndexOf('/')) + "/schema.json");
        FileInputStream schemaInputStream = new FileInputStream(schemaFile);
        JSONTokener schemaData = new JSONTokener(schemaInputStream);
        JSONObject jsonSchema = new JSONObject(schemaData);

        File jsonFile = new File(filePath);
        FileInputStream jsonInputStream = new FileInputStream(jsonFile);
        JSONTokener jsonData = new JSONTokener(jsonInputStream);
        try{
            jsonObject = new JSONObject(jsonData);
        } catch (Exception ignored) { }
        Schema schemaValidator = SchemaLoader.load(jsonSchema);
        jsonInputStream.close();
        schemaInputStream.close();
        try {
            schemaValidator.validate(jsonObject);
        } catch (Exception e) {
            System.out.println("Json Addition Failed: " + e.getMessage());
            if(jsonFile.delete()){
                return false;
            }
        }
        return true;
    }
}