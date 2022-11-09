package week2.utility;

import com.github.openjson.JSONArray;
import com.github.openjson.JSONException;
import com.github.openjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Utility {
    private static final Logger LOGGER = LoggerFactory.getLogger(Utility.class);

    public static JSONArray getUniqueData(String response) {
        JSONObject jsonObject = new JSONObject(response);
        JSONArray docs = new JSONArray();
        docs.put(jsonObject);
        LOGGER.info("Converted JSON : " + docs);
        return docs;
    }

    public static JSONArray getAllData(String response, String key) throws JSONException {
        JSONObject jsonObject = new JSONObject(response);
        JSONArray docs = jsonObject.getJSONArray(key);
        LOGGER.info("Converted JSON : " + docs);
        return docs;
    }

}
