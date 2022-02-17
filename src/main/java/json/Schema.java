package json;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import file.FilesNavigator;
import java.io.*;
import java.util.*;

public class Schema {
    static Gson gson = new Gson();

    public static void writeSchema(String schema, String filePath) throws IOException {
        FilesNavigator.createFile(filePath + "schema.json");
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath + "schema.json"));
        bufferedWriter.write(schema);
        bufferedWriter.flush();
        bufferedWriter.close();
    }

    public static boolean createSchema(String databaseName,String schemaName)
            throws IOException {
        if(FilesNavigator.createDirectory("./dbs/" + databaseName + "/" + schemaName)) {
            return true;
        }
        return false;
    }

    public static List<String> getSchemas(String database) throws FileNotFoundException {
        List<String> schemaNames = new ArrayList<>();
        File[] schemas = FilesNavigator.getFiles("./dbs/" + database);
        for(File schema : schemas){
            if(! schema.getName().contains("_ind_")){
                schemaNames.add(schema.getName());
            }
        }
        return schemaNames;
    }

    public static String isValidSchema(List<String> schemas){
        boolean validInput = false;
        Scanner scan = new Scanner(System.in);
        while(!validInput) {
            System.out.println("\nEnter the schema name: ");
            String schema = scan.next();
            for(String s : schemas){
                if(s.equalsIgnoreCase(schema)){
                    return schema;
                }
            }
        }
        return null;
    }

    public static Map getSchemaAsObject(String filePath) throws FileNotFoundException {
        Map<String, Object> jsonObject = Json.convertToObject(filePath + "schema.json");
        return jsonObject;
    }

    public static void printSchemas(List<String> schemas){
        for(String schema : schemas){
            System.out.println(schema + "   ");
        }
    }

    public static boolean validateProperty(String filePath, String property) throws FileNotFoundException {
        Map json = Schema.getSchemaAsObject(filePath);
        JsonObject jsonObject = gson.toJsonTree(json.get("properties")).getAsJsonObject(); //convert LinkedTreeMap to JsonObject
        return jsonObject.has(property); // check if Prop exists
    }
}
