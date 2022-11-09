package week2.service;

import com.github.openjson.JSONArray;
import com.github.openjson.JSONObject;
import com.github.wnameless.json.flattener.JsonFlattener;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class ConverterServiceImpl implements ConverterService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConverterServiceImpl.class);

    @Override
    public void convertJsonToCSV(JSONArray response, String path) throws IOException {
        JSONArray array = flatData(response);
        LOGGER.info("Csv Path : " + path);
        File file = new File(path);
        //String csvString = CDL.toString(array);
        FileUtils.writeStringToFile(file, "");
        LOGGER.info("CSV file generated");
    }

    private static JSONArray flatData(JSONArray docs) {
        for (int i = 0; i < docs.length(); i++) {
            JSONObject jsonObj = new JSONObject(docs.get(i).toString());
            String flattenedJson = JsonFlattener.flatten(jsonObj.toString());
            JSONObject formattedJsonObj = new JSONObject(flattenedJson);
            docs.put(i, formattedJsonObj);
        }
        return docs;
    }

}
