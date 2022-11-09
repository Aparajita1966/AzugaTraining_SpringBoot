package mongodb;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class Main {

    public static void main(String[] args) {
        CrudOperations main = new CrudOperations();
        MongoDatabase db = main.getMongoDatabase("basketballdb");
        MongoCollection<Document> collection = main.getDocumentMongoCollection(db, "players");
        main.getDocuments(collection);
        main.getDocument(collection);
        main.insertDocument(collection);
        //main.updateDocument(collection);
        //main.deleteDocument(collection);
        //main.deleteCollection(collection);
    }

}
