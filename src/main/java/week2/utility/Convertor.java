package week2.utility;

import com.github.openjson.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import week2.constant.GeneralConstant;
import week2.service.ConverterService;
import week2.service.ConverterServiceImpl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/*
 * Copyright (c) 2022.  - All Rights Reserved
 *  * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 *  * is strictly prohibited-
 *  * @Author -aparajita.
 */

public class Convertor {

    private static final Logger LOGGER = LoggerFactory.getLogger(Convertor.class);
    public static List<String> strArr = new ArrayList<>();

    public static void convertFile(boolean sendIndividualZipFile, String path, JSONArray docs, boolean generateZipFile, boolean sendEmail) throws IOException {
        String filePath = "." + File.separator + "." + File.separator + "." + File.separator + "OutputFiles" + File.separator;
        File file = new File(path);
        if (file.mkdir()) {
            LOGGER.info("Path created " + file.getAbsolutePath());
        }
        ConverterService converterService = new ConverterServiceImpl();
        try {
			converterService.convertJsonToCSV(docs, filePath + path + GeneralConstant.CSV);
		} catch (IOException e) {
			LOGGER.info("Exception occured while creating CSV file " + filePath + path + GeneralConstant.CSV);
			throw new IOException("Json not found");
		}
    }
}

