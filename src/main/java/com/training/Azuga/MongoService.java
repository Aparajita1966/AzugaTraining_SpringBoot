package com.training.Azuga;

import com.github.openjson.JSONObject;
import com.google.gson.Gson;
import database.Museum;
import database.MuseumMongo;
import database.jdbc.MongoDatabaseConnection;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MongoService implements IMongoService {

    @Override
    public void addMuseum(JSONObject jsonObject) throws Exception {
        MongoDatabaseConnection.insertDocument("museumDb","museum",jsonObject);
    }

    @Override
    public void updateMuseumObject(JSONObject jsonObject) throws Exception {
        MongoDatabaseConnection.updateDocument("museumDb","museum",jsonObject);
    }

    @Override
    public void deleteMuseumObject(Integer objectId) throws Exception{
        MongoDatabaseConnection.deleteDocument("museumDb","museum",objectId);
    }

    @Override
    public MuseumMongo getMuseum(Integer objectId) throws Exception {
        String result = MongoDatabaseConnection.getDocumentById("museumDb","museum",objectId);
        if(null != result){
            Gson gson = new Gson();
            return gson.fromJson(result, MuseumMongo.class);
        }
        return null;
    }

    @Override
    public List<MuseumMongo> getMuseumAll() throws Exception {
        return MongoDatabaseConnection.getDocuments("museumDb","museum");
    }
}
