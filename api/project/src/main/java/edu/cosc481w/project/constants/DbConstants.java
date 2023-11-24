package edu.cosc481w.project.constants;

public class DbConstants {
    public static final String kApiVersion = "v1";

    // DO NOT COMMIT THIS
    public static final String kUsername = "mongo_db_admin";
    public static final String kPassword = "Emucosc481";

    public static final String kConnectionString = "mongodb://" + kUsername + ":" + kPassword + "@groot.daltzsmith.com:27017/?directConnection=true&serverSelectionTimeoutMS=2000&authSource=admin&appName=mongosh+2.1.0";
}
