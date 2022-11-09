package mongodb;

import com.github.openjson.JSONObject;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class CrudOperations {

    private static final Logger LOGGER = LoggerFactory.getLogger(CrudOperations.class);

    public static MongoDatabase getMongoDatabase(String databaseName) {
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

    public static MongoCollection<Document> getDocumentMongoCollection(MongoDatabase db, String collectionName) {
        //creating collection or get collection if exists.
        MongoCollection<Document> collection = db.getCollection(collectionName);
        LOGGER.info("Collection created successfully.. ");
        return collection;
    }

    public static void insertDocument(MongoCollection<Document> collection) {
        //Inserting documents

        List<Document> documentList = new ArrayList<>();
        JSONObject firstDoc = new JSONObject();
        firstDoc.put("_id", 14);
        firstDoc.put("first_name", "Ike");
        firstDoc.put("last_name", "Anigbogu");
        firstDoc.put("position", "C");
        JSONObject json = new JSONObject();
        json.put("team_name","Indiana Pacers");
        json.put("division","Central");
        firstDoc.append("team", json);
        Document jsonObject = Document.parse(firstDoc.toString());
        documentList.add(jsonObject);

        JSONObject secondDoc = new JSONObject();
        secondDoc.put("_id", 47);
        secondDoc.put("first_name", "Jabari");
        secondDoc.put("last_name", "Bird");
        secondDoc.put("position", "G");
        secondDoc.put("team", "Boston Celtics");
        Document jsonSecondObject = Document.parse(secondDoc.toString());
        documentList.add(jsonSecondObject);
        collection.insertMany(documentList);
        LOGGER.info("Document Inserted " + documentList);
    }

    public static void getDocument(MongoCollection<Document> collection) {

        //getting specific document in a collection
        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put("first_name", "Ike");
        LOGGER.info("Getting Document" + searchQuery);
        MongoCursor<Document> cursor = collection.find(searchQuery).iterator();
        while (cursor.hasNext()) {
            LOGGER.info("Results: " + cursor.next());
        }
    }

    //Updating Document

    public static void updateDocument(MongoCollection<Document> collection){
        collection.updateMany(Filters.eq("first_name", "Ike"), Updates.set("position", " "));
        LOGGER.info("Document updated successfully...");
        getDocuments(collection);
    }

    public static void getDocuments(MongoCollection<Document> collection) {
        FindIterable<Document> iterDoc = collection.find();
        Iterator<Document> iterator = iterDoc.iterator();
        LOGGER.info("Get all documents in the collection: ");
        while (iterator.hasNext()) {
            LOGGER.info("Results: " + iterator.next());
        }
    }

    //Deleting Document

    public static void deleteDocument(MongoCollection<Document> collection){
        collection.deleteMany(Filters.eq("first_name", "Jabari"));
        LOGGER.info("Document deleted successfully...");
        getDocuments(collection);
    }

    //Deleting Collection

    public static void deleteCollection(MongoCollection<Document> collection){
        collection.drop();
        LOGGER.info("Collection dropped successfully...");
    }
}
