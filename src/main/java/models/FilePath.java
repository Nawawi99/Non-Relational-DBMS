package models;

public class FilePath {
    private String root;
    private String database;
    private String schema;

    public FilePath(String database, String schema){
        this.root = "dbs";
        this.database = database;
        this.schema = schema;
    }

    public FilePath() {
        this.root = "dbs";
    }

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        if(database == null)
            return;
        this.database = database;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    @Override
    public String toString(){
        return "./" + root + "/" + database + "/" + schema + "/";
    }
}
