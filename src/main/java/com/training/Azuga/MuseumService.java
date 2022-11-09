package com.training.Azuga;

import com.github.openjson.JSONArray;
import com.github.openjson.JSONObject;
import com.google.gson.Gson;
import database.Museum;
import database.jdbc.DatabaseConnection;
import database.jdbc.MuseumDatabaseConnection;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class MuseumService implements IMuseumService {


    private static final Logger LOGGER = LoggerFactory.getLogger(MuseumService.class);

    @Override
    public void addMuseum(JSONObject jsonObject) throws SQLException {
        LOGGER.info("Adding objects to database");
        JSONArray constituentsArray = null;
        JSONArray measurementsArray = null;
        JSONArray tagsArray = null;
        if (jsonObject.has("constituents")) {
            if (!jsonObject.get("constituents").equals(null)) {
                if(jsonObject.get("constituents").toString() != "null") {
                    constituentsArray = jsonObject.getJSONArray("constituents");
                }
            }
            jsonObject.remove("constituents");
        }
        if (jsonObject.has("measurements")) {
            if (!jsonObject.get("measurements").equals(null)) {
                if(jsonObject.get("measurements").toString() != "null") {
                    measurementsArray = jsonObject.getJSONArray("measurements");
                }
            }
            jsonObject.remove("measurements");
        }
        if (jsonObject.has("tags")) {
            if (!jsonObject.get("tags").equals(null)) {
                if(!jsonObject.get("tags").toString().equals("null")) {
                    tagsArray = jsonObject.getJSONArray("tags");
                }
            }
            jsonObject.remove("tags");
        }
        DatabaseConnection.insertObject(jsonObject, "objects");
        if (null != constituentsArray) {
            for (int i = 0; i < constituentsArray.length(); i++) {
                JSONObject jsonObj = constituentsArray.getJSONObject(i);
                jsonObj.put("objectID", jsonObject.getInt("objectID"));
                DatabaseConnection.insertObject(jsonObj, "constituents");
            }
            jsonObject.remove("constituents");
        }
        if (null != measurementsArray) {
            for (int i = 0; i < measurementsArray.length(); i++) {
                JSONObject jsonObj = measurementsArray.getJSONObject(i);
                if (!jsonObj.get("elementMeasurements").equals(null)) {
                    JSONObject elemJson = new JSONObject(String.valueOf(jsonObj.get("elementMeasurements")));
                    if (elemJson.has("Depth")) {
                        jsonObj.put("Depth", elemJson.getDouble("Depth"));
                    }
                    if (elemJson.has("Height")) {
                        jsonObj.put("Height", elemJson.getDouble("Height"));
                    }
                    if (elemJson.has("Width")) {
                        jsonObj.put("Width", elemJson.getDouble("Width"));
                    }
                    if (elemJson.has("Diameter")) {
                        jsonObj.put("Diameter", elemJson.getDouble("Diameter"));
                    }
                    jsonObj.remove("elementMeasurements");
                }
                jsonObj.put("objectID", jsonObject.getInt("objectID"));
                DatabaseConnection.insertObject(jsonObj, "measurements");
            }
            jsonObject.remove("measurements");
        }
        if (null != tagsArray) {
            for (int i = 0; i < tagsArray.length(); i++) {
                JSONObject jsonObj = tagsArray.getJSONObject(i);
                jsonObj.put("objectID", jsonObject.getInt("objectID"));
                DatabaseConnection.insertObject(jsonObj, "tags");
            }
            jsonObject.remove("tags");
        }
    }

    @Override
    public Museum getMuseum(int objectId) throws SQLException {
        JSONObject json = DatabaseConnection.getObjectById(objectId);
        //getOtherParameter(objectId, json);
        Gson gson = new Gson();
        Museum data = gson.fromJson(json.toString(), Museum.class);
        return data;
    }

    private void getOtherParameter(int objectId, JSONObject json) throws SQLException {
        JSONArray constituentsArray = new JSONArray();
        List<JSONObject> constituentsList = DatabaseConnection.getData(objectId, "constituents");
        if (!CollectionUtils.isEmpty(constituentsList)) {
            constituentsArray.put(constituentsList);
            json.put("constituents", constituentsArray);
        }
        JSONArray measurementsArray = new JSONArray();
        List<JSONObject> measurementsList = DatabaseConnection.getData(objectId, "measurements");
        if (!CollectionUtils.isEmpty(measurementsList)) {
            measurementsArray.put(measurementsList);
            json.put("measurements", measurementsArray);
        }
        JSONArray tagsArray = new JSONArray();
        List<JSONObject> tagsList = DatabaseConnection.getData(objectId, "tags");
        if (!CollectionUtils.isEmpty(tagsList)) {
            tagsArray.put(tagsList);
            json.put("tags", tagsArray);
        }
    }

    @Override
    public List<Museum> getMuseumAll(String tableName) throws SQLException {
        List<Museum> list = new ArrayList<Museum>();
        List<JSONObject> jsonObjects = DatabaseConnection.getObjects(tableName);
        // Table Name check to fetch other field data
        if (false) {
            for (JSONObject jsonObject : jsonObjects) {
                getOtherParameter(jsonObject.getInt("objectID"), jsonObject);
            }
        }
        Gson gson = new Gson();
        for (JSONObject json:jsonObjects){
            list.add(gson.fromJson(json.toString(), Museum.class));
        }
        return list;
    }

    @Override
    public void deleteMuseumObject(int objectId) throws SQLException {
        DatabaseConnection.deleteMuseumObject(objectId);
    }

    @Override
    public void updateMuseumObject(JSONObject jsonObject) throws SQLException {
        if (jsonObject.has("objectID") && jsonObject.keySet().size() > 1) {
            int objectId = jsonObject.getInt("objectID");
            jsonObject.remove("objectID");
            if (jsonObject.has("constituents") && !jsonObject.get("constituents").equals(null)) {
                JSONArray jsonArray = jsonObject.getJSONArray("constituents");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObj = jsonArray.getJSONObject(i);
                    DatabaseConnection.updateMuseumObject(jsonObj, objectId, "constituents");
                }
                jsonObject.remove("constituents");
            }
            if (jsonObject.has("measurements") && !jsonObject.get("measurements").equals(null)) {
                JSONArray jsonArray = jsonObject.getJSONArray("measurements");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObj = jsonArray.getJSONObject(i);
                    if (!jsonObj.get("elementMeasurements").equals(null)) {
                        JSONObject elemJson = new JSONObject(String.valueOf(jsonObj.get("elementMeasurements")));
                        if (elemJson.has("Depth")) {
                            jsonObj.put("Depth", elemJson.getDouble("Depth"));
                        }
                        if (elemJson.has("Height")) {
                            jsonObj.put("Height", elemJson.getDouble("Height"));
                        }
                        if (elemJson.has("Width")) {
                            jsonObj.put("Width", elemJson.getDouble("Width"));
                        }
                        if (elemJson.has("Diameter")) {
                            jsonObj.put("Diameter", elemJson.getDouble("Diameter"));
                        }
                        jsonObj.remove("elementMeasurements");
                    }
                    DatabaseConnection.updateMuseumObject(jsonObj, objectId, "measurements");
                }
                jsonObject.remove("measurements");
            }
            if (jsonObject.has("tags") && !jsonObject.get("tags").equals(null)) {
                JSONArray jsonArray = jsonObject.getJSONArray("tags");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObj = jsonArray.getJSONObject(i);
                    DatabaseConnection.updateMuseumObject(jsonObj, objectId, "tags");
                }
                jsonObject.remove("tags");
            }
            DatabaseConnection.updateMuseumObject(jsonObject, objectId, "objects");
        }
    }

    @Override
    public List<JSONObject> getObjectDetailsWhereTagsAreMoreThanOne() throws SQLException {
        return MuseumDatabaseConnection.getObjectDetailsWhereTagsAreMoreThanOne();
    }

    @Override
    public List<JSONObject> getObjectDetailsForConstituents(int value) throws SQLException {
        return MuseumDatabaseConnection.getObjectDetailsForConstituents(value);
    }

    @Override
    public List<JSONObject> getObjectsDetailsByJoin(int value) throws SQLException {
        return MuseumDatabaseConnection.getObjectsDetailsByJoin(value);
    }

    @Override
    public List<JSONObject> getObjectsDetailsWithoutUsingJoin() throws SQLException {
        return MuseumDatabaseConnection.getObjectsDetailsWithoutUsingJoin();
    }

    @Override
    public List<JSONObject> getObjectsDetailsUsingAND() throws SQLException {
        return MuseumDatabaseConnection.getObjectsDetailsUsingAND();
    }

    @Override
    public List<JSONObject> getObjectsDetailsUsingAscDesc() throws SQLException {
        return MuseumDatabaseConnection.getObjectsDetailsUsingAscDesc();
    }

    @Override
    public List<JSONObject> getObjectsDetailsUsingMinMax() throws SQLException {
        return MuseumDatabaseConnection.getObjectsDetailsUsingMinMax();
    }

    @Override
    public List<JSONObject> getObjectsDetailsUsingNotBetween() throws SQLException {
        return MuseumDatabaseConnection.getObjectsDetailsUsingNotBetween();
    }

    @Override
    public List<JSONObject> getObjectsDetailsUsingCount() throws SQLException {
        return MuseumDatabaseConnection.getObjectsDetailsUsingCount();
    }

    @Override
    public List<JSONObject> getObjectsDetailsUsingLeftJoin() throws SQLException {
        return MuseumDatabaseConnection.getObjectsDetailsUsingLeftJoin();
    }

    @Override
    public List<JSONObject> getObjectsDetailsUsingExists() throws SQLException {
        return MuseumDatabaseConnection.getObjectsDetailsUsingExists();
    }
}
