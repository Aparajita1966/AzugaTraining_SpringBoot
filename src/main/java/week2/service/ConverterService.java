package week2.service;

import com.github.openjson.JSONArray;

import java.io.IOException;

public interface ConverterService {

    void convertJsonToCSV(JSONArray response, String path) throws IOException;

}
