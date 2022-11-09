package week2.controller;

import com.github.openjson.JSONArray;
import com.github.openjson.JSONException;
import com.github.openjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import week2.utility.Convertor;
import week2.utility.HttpClient;
import java.io.IOException;


/*
 * Copyright (c) 2022.  - All Rights Reserved
 *  * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 *  * is strictly prohibited-
 *  * @Author -aparajita.
 */

public class MuseumApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(MuseumApi.class);

    private static final String BASE_URL = "https://collectionapi.metmuseum.org/public/collection/v1";
    private static final String DEPARTMENTS_URL = "/departments";
    private static final String OBJECT_URL = "/objects";
    
    public static void main(String[] args) throws JSONException, IOException {
    	JSONArray docs = new JSONArray();
    	for (int i = 10; i < 16; i++) {
    		JSONObject jsonObject= getMuseumObjectData(i);
    		docs.put(jsonObject);
		}
    	Convertor.convertFile(false, "/objects/objects", docs, false, false);
    	
	}


    public static JSONObject getMuseumObjectByIdData(Integer id) throws JSONException {
        JSONObject jsonObject = null;
        String response = HttpClient.sendGET(BASE_URL + OBJECT_URL + "/" + id);
        if (null != response) {
            response = response.replace(", ", "; ");
            jsonObject = new JSONObject(response);
        }
        return jsonObject;
    }
    
    public static JSONObject getMuseumObjectData(Integer id) throws JSONException, IOException {
    	JSONObject jsonObject = null;
        String response = HttpClient.sendGET(BASE_URL + OBJECT_URL + "/" + id);
        if (null != response) {
            response = response.replace(", ", "; ");
            jsonObject = new JSONObject(response);
        }
        return jsonObject;
    }
}
