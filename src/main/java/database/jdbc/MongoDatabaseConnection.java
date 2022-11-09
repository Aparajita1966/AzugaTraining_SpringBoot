package database.jdbc;

import com.github.openjson.JSONObject;
import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import database.MuseumMongo;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MongoDatabaseConnection {

    private static final Logger LOGGER = LoggerFactory.getLogger(MongoDatabaseConnection.class);

    public static MongoDatabase getMongoDatabase(String databaseName) throws Exception {
        // Creating a Mongo Client
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        LOGGER.info("Created Mongo Connection successfully");
        MongoCursor<String> dbsCursor = mongoClient.listDatabaseNames().iterator();
        while(dbsCursor.hasNext()) {
            if(dbsCursor.next().equals(databaseName))
                LOGGER.info("Database already exists");
        }
        MongoDatabase db = mongoClient.getDatabase(databaseName);
        LOGGER.info("Database connected successful");
        return db;
    }

    public static MongoCollection<Document> getDocumentMongoCollection(MongoDatabase db, String collectionName) throws Exception {
        //creating collection or get collection if exists.
        MongoCollection<Document> collection = db.getCollection(collectionName);
        LOGGER.info("Collection created ");
        return collection;
    }

    public static MongoCollection<Document> getMongoConnection(String databaseName, String collectionName) throws Exception {
        MongoDatabase md = getMongoDatabase(databaseName);
        return getDocumentMongoCollection(md, collectionName);
    }

    public static void insertDocument(String databaseName, String collectionName, JSONObject obj) throws Exception {
        //Inserting documents.
        MongoCollection<Document> collection = getMongoConnection(databaseName, collectionName);
        LOGGER.info("Document Inserted " + obj);
        obj.put("_id",obj.get("objectID"));
        Document jsnObject = Document.parse(obj.toString());
        collection.insertOne(jsnObject);
    }

    public static void updateDocument(String databaseName, String collectionName, JSONObject obj) throws Exception {
        MongoCollection<Document> collection = getMongoConnection(databaseName, collectionName);
        BasicDBObject updateFields = new BasicDBObject();
        Set<String> keys = obj.keySet();
        List<String> list = new ArrayList<String>(keys);
        for (int i = 1; i < list.size(); i++) {
            String key = list.get(i);
            updateFields.append(key,obj.get(key));
        }
        BasicDBObject setQuery = new BasicDBObject();
        setQuery.append("$set", updateFields);
        collection.updateMany(Filters.eq("_id", obj.get("objectID")), setQuery);
        LOGGER.info("Document updated successfully...");
    }

    public static void deleteDocument(String databaseName, String collectionName, Integer objectId) throws Exception {
        MongoCollection<Document> collection = getMongoConnection(databaseName, collectionName);
        collection.deleteMany(Filters.eq("_id", objectId));
        System.out.println("Document deleted successfully...");
    }

    public static String getDocumentById(String databaseName, String collectionName, int objectId) throws Exception {
        String result = null;
        MongoCollection<Document> collection = getMongoConnection(databaseName, collectionName);
        //getting specific document in a collection
        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put("_id", objectId);
        LOGGER.info("Getting Document" + searchQuery);
        MongoCursor<Document> cursor = collection.find(searchQuery).iterator();
        while (cursor.hasNext()) {
            Document document = cursor.next();
            result =  document.toJson();
        }
        return result;
    }

    public static List<MuseumMongo> getDocuments(String databaseName, String collectionName) throws Exception{
        List<MuseumMongo> list = new ArrayList<>();
        MongoCollection<Document> collection = getMongoConnection(databaseName, collectionName);
        MongoCursor<Document> iterDoc = collection.find().iterator();
        while (iterDoc.hasNext()) {
            Document document = iterDoc.next();
            Gson gson = new Gson();
            MuseumMongo museum = gson.fromJson(document.toJson(), MuseumMongo.class);
            list.add(museum);
        }
        return list;
    }
}
