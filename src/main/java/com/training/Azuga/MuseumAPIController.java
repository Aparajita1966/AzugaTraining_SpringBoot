package com.training.Azuga;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.github.openjson.JSONObject;
import com.github.wnameless.json.unflattener.JsonUnflattener;
import com.google.gson.Gson;
import database.Museum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import week2.constant.GeneralConstant;
import week2.controller.MuseumApi;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*
 * Copyright (c) 2022.  - All Rights Reserved
 *  * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 *  * is strictly prohibited-
 *  * @Author -aparajita.
 */
@RestController
public class MuseumAPIController {

    @Autowired
    private IMuseumService museumService;

    private static final Logger LOGGER = LoggerFactory.getLogger(MuseumAPIController.class);

    @GetMapping(value = "/museum/add/{objectId}")
    public String addMuseum(@PathVariable Integer objectId) {
        Response response = new Response();
        try {
            JSONObject jsonObject = MuseumApi.getMuseumObjectByIdData(objectId);
            if (null != jsonObject) {
                Museum museum = museumService.getMuseum(objectId);
                //when there is no data in db it will return 0
                if (0 != museum.getObjectID()) {
                    return "Object Id already exists" + objectId;
                } else {
                    museumService.addMuseum(jsonObject);
                    return "Success";
                }
            } else {
                LOGGER.info("JSON Object is empty for ObjectID: " + objectId);
                return "JSON Object is empty for ObjectID: " + objectId;
            }
        } catch (SQLException e) {
            LOGGER.error("Exception occurred while connecting to the database", e);
            return e.getMessage();
        }
    }

    @GetMapping(value = "/museum/{objectId}")
    public Museum getMuseumObjectById(@PathVariable Integer objectId) {
        try {
            Museum museum = museumService.getMuseum(objectId);
            LOGGER.info("JSON Object for: " + objectId + " and Json Data: " + museum);
            return museum;
        } catch (SQLException e) {
            LOGGER.error("Exception occurred while connecting to the database", e);
        }
        return null;
    }

    @GetMapping(value = "/museum")
    public List<Museum> getMuseum() {
        try {
            List<Museum> list = museumService.getMuseumAll(null);
            return list;
        } catch (SQLException e) {
        }
        return null;
    }

    @DeleteMapping(value = "/museum/{objectId}")
    public String deleteMuseumObject(@PathVariable Integer objectId) {
        try {
            museumService.deleteMuseumObject(objectId);
            LOGGER.info("Delete Data for objectId : " + objectId);
            return "Deleted";
        } catch (SQLException e) {
            LOGGER.error("Exception occurred while connecting to the database", e);
        }
        return null;
    }

    @PutMapping(value = "/museum/{id}")
    public String updateMuseumObject(@RequestBody Museum museum, @PathVariable Integer id) {
        try {
            String strJson = new Gson().toJson(museum);
            JSONObject json = new JSONObject(strJson);
            LOGGER.info("Going to update json: " + json + " for object Id : " + museum.getObjectID());
            museumService.updateMuseumObject(json);
            LOGGER.info("Update object: " + museum.getObjectID());
            return "Success";
        } catch (SQLException e) {
            LOGGER.error("Exception occurred while connecting to the database", e);
            return e.getMessage() + id;
        }
    }

    @PostMapping(value = "/museum/add")
    public String addMuseumUsingCSVFile(@RequestBody Museum museum) {
        try {
            List<JSONObject> jsonList = csvToJson();
            if (!jsonList.isEmpty()) {
                for (JSONObject object : jsonList) {
                    museumService.addMuseum(object);
                }
                return "Success";
            } else {
                return "CSV file is empty";
            }
        } catch (SQLException e) {
            LOGGER.error("Exception occurred while connecting to the database", e);
            return e.getMessage();
        }
    }

    @PostMapping(value = "/museum")
    public String addMuseum(@RequestBody Museum museum) {
        try {
            Museum museumDb = museumService.getMuseum(museum.getObjectID());
            if (0 != museumDb.getObjectID()) {
                return "Object Id already exists" + museum.getObjectID();
            } else {
                if (null == museum.getAccessionYear()) {
                    museum.setAccessionYear(0);
                }
                if (null == museum.getObjectBeginDate()) {
                    museum.setObjectBeginDate(0);
                }
                if (null == museum.getObjectEndDate()) {
                    museum.setObjectEndDate(0);
                }
                JSONObject object = new JSONObject(museum);
                museumService.addMuseum(object);
                return "Success";
            }
        } catch (SQLException e) {
            LOGGER.error("Exception occurred while connecting to the database", e);
            return e.getMessage();
        }
    }

    public List<JSONObject> csvToJson() {
        List<JSONObject> jsonList = new ArrayList<>();
        String filePath = "." + File.separator + "." + File.separator + "." + File.separator + "OutputFiles" + File.separator;
        File input = new File(filePath + "/objects/objects" + GeneralConstant.CSV);
        try {
            CsvSchema csv = CsvSchema.emptySchema().withHeader();
            CsvMapper csvMapper = new CsvMapper();
            MappingIterator<Map<?, ?>> mappingIterator = csvMapper.reader().forType(Map.class).with(csv).readValues(input);
            List<Map<?, ?>> list = mappingIterator.readAll();
            for (Map<?, ?> map : list) {
                JSONObject jsonObject = new JSONObject();
                for (Map.Entry<?, ?> pair : map.entrySet()) {
                    jsonObject.put(pair.getKey().toString(), pair.getValue());
                }
                String nestedJson = JsonUnflattener.unflatten(String.valueOf(jsonObject));
                JSONObject new1 = new JSONObject(nestedJson);
                jsonList.add(new1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonList;
    }
}

