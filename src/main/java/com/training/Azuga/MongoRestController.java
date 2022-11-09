package com.training.Azuga;

import com.github.openjson.JSONObject;
import com.google.gson.Gson;

import database.MuseumMongo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import week2.controller.MuseumApi;

import java.util.List;


@RestController
public class MongoRestController {

    @Autowired
    private IMongoService mongoService;

    private static final Logger LOGGER = LoggerFactory.getLogger(MongoRestController.class);

    @GetMapping(value = "/mongo/add/{objectId}")
    public String addMuseum(@PathVariable Integer objectId) {
        Response response = new Response();
        try {
            JSONObject jsonObject = MuseumApi.getMuseumObjectByIdData(objectId);
            if (null != jsonObject) {
                MuseumMongo museum = mongoService.getMuseum(objectId);
                //when there is no data in db it will return 0
                if(null != museum && 0 != museum.getObjectID()){
                    return "Object Id already exists" + objectId;
                }else{
                    mongoService.addMuseum(jsonObject);
                    return "Success";
                }
            } else {
                LOGGER.info("JSON Object is empty for ObjectID: " + objectId);
                return "JSON Object is empty for ObjectID: " + objectId;
            }
        } catch (Exception e) {
            LOGGER.error("Exception occurred while connecting to the database", e);
            return e.getMessage();
        }
    }

    @PutMapping(value = "/mongo/{id}")
    public String updateMuseumObject(@RequestBody MuseumMongo museum, @PathVariable Integer id) {
        try {
            String strJson = new Gson().toJson(museum);
            JSONObject json = new JSONObject(strJson);
            LOGGER.info("Going to update json: " + json + " for object Id : " + museum.getObjectID());
            mongoService.updateMuseumObject(json);
            LOGGER.info("Update object: " + museum.getObjectID());
            return "Success";
        } catch (Exception e) {
            LOGGER.error("Exception occurred while connecting to the database", e);
            return e.getMessage() + id;
        }
    }

    @DeleteMapping(value = "/mongo/{objectId}")
    public String deleteMuseumObject(@PathVariable Integer objectId) {
        try {
            mongoService.deleteMuseumObject(objectId);
            LOGGER.info("Delete Data for objectId : " + objectId);
            return "Deleted";
        } catch (Exception e) {
            LOGGER.error("Exception occurred while connecting to the database", e);
        }
        return null;
    }

    @GetMapping(value = "/mongo/{objectId}")
    public MuseumMongo getMuseumObjectById(@PathVariable Integer objectId) {
        try {
            MuseumMongo museum = mongoService.getMuseum(objectId);
            LOGGER.info("JSON Object for: " + objectId + " and Json Data: " + museum);
            return museum;
        } catch (Exception e) {
            LOGGER.error("Exception occurred while connecting to the database", e);
        }
        return null;
    }

    @GetMapping(value = "/mongo")
    public List<MuseumMongo> getMuseum() {
        try {
            List<MuseumMongo> list = mongoService.getMuseumAll();
            return list;
        } catch (Exception e) {
            LOGGER.error("Exception occurred while connecting to the database", e);
        }
        return null;
    }

    @PostMapping(value = "/mongo")
    public String addMuseum(@RequestBody MuseumMongo museum) {
        try {
            MuseumMongo museumDb = mongoService.getMuseum(museum.getObjectID());
            if(null != museumDb && 0 != museumDb.getObjectID()){
                return "Object Id already exists" + museum.getObjectID();
            }else{
                if(null == museum.getAccessionYear()){
                    museum.setAccessionYear(0);
                }
                if(null == museum.getObjectBeginDate()){
                    museum.setObjectBeginDate(0);
                }
                if(null == museum.getObjectEndDate()){
                    museum.setObjectEndDate(0);
                }
                JSONObject object = new JSONObject(museum);
                mongoService.addMuseum(object);
                return "Success";
            }
        } catch (Exception e) {
            LOGGER.error("Exception occurred while connecting to the database", e);
            return e.getMessage();
        }
    }
}
