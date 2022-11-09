package week2.utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/*
 * Copyright (c) 2022.  - All Rights Reserved
 *  * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 *  * is strictly prohibited-
 *  * @Author -aparajita.
 */

public class HttpClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClient.class);

    public static String sendGET(String url) {
        StringBuilder response = null;
        try {
            LOGGER.info("Calling URL : " + url);
            URL urlForGetRequest = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlForGetRequest.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
            LOGGER.info("Response code : " + responseCode);
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                response = new StringBuilder();
                String readLine;
                while (null != (readLine = in.readLine()) ) {
                    response.append(readLine);
                }
                in.close();
                LOGGER.info("JSON String Result " + response);
            } else {
                LOGGER.error("GET NOT WORKED");
            }
        } catch (IOException e) {
            LOGGER.error("Exception occurred while calling " + url + "and exception is :" + e);
            throw new RuntimeException(e);
        }
        return null != response ? response.toString() : null;
    }

    public static class ZipFolder {

        public static void zipFolder(String outputZipFile, File files, String sourceFolder) {
            ZipFolder appZip = new ZipFolder();
            appZip.zip(outputZipFile, files, sourceFolder);
        }

        public void zip(String zipFile, File files, String sourceFolder) {
            byte[] buffer = new byte[1024];
            try {
                FileOutputStream fos = new FileOutputStream(zipFile);
                ZipOutputStream zos = new ZipOutputStream(fos);
                System.out.println("Output to Zip : " + zipFile);
                for (String file : files.list()) {
                    System.out.println("File Added : " + file);
                    ZipEntry ze = new ZipEntry(file);
                    zos.putNextEntry(ze);
                    FileInputStream in = new FileInputStream(sourceFolder + file);
                    int len;
                    while ((len = in.read(buffer)) > 0) {
                        zos.write(buffer, 0, len);
                    }
                    in.close();
                }
                zos.closeEntry();
                // remember close it
                zos.close();
                System.out.println("Done");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }
}
