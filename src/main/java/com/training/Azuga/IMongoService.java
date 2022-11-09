package com.training.Azuga;

import com.github.openjson.JSONObject;
import database.Museum;
import database.MuseumMongo;

import java.util.List;

public interface IMongoService {
    void addMuseum(JSONObject jsonObject) throws Exception;

    void updateMuseumObject(JSONObject json) throws Exception;

    void deleteMuseumObject(Integer objectId) throws Exception;

    MuseumMongo getMuseum(Integer objectId) throws Exception;

    List<MuseumMongo> getMuseumAll() throws Exception;
}
