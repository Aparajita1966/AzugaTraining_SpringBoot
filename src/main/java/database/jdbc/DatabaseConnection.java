package database.jdbc;

import com.github.openjson.JSONObject;
import com.google.gson.Gson;
import database.Museum;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DatabaseConnection {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseConnection.class);
    static final String DB_URL = "jdbc:mysql://localhost/MUSEUMDB";
    static final String USER = "root";
    static final String PASS = "Am@29032";

    public static void insertObject(JSONObject jsonObject, String tableName) throws SQLException {
        LOGGER.info("Establishing connection with database..");
        // Open a connection
        Set<String> keys = jsonObject.keySet();
        List<String> list = new ArrayList<String>(keys);
        StringBuilder INSERT_SQL = new StringBuilder("INSERT INTO ").append(tableName).append("( ");
        // insert into objects ( a, b, c ) values ('abc', 'aaa', 'dff');
        String str1 = list.get(0);
        if (str1.equals("isHighlight")) {
            str1 = "highlight";
        }
        if (str1.equals("isPublicDomain")) {
            str1 = "publicDomain";
        }
        INSERT_SQL.append(str1);
        if (keys.size() > 1) {
            for (int i = 1; i < list.size(); i++) {
                String key = list.get(i);
                if (key.equals("isHighlight")) {
                    key = "Highlight";
                }
                if (key.equals("isPublicDomain")) {
                    key = "PublicDomain";
                }
                INSERT_SQL.append(" , ").append(key);
            }
        }
        INSERT_SQL.append(") VALUES (");
        Object objectValue = jsonObject.get(list.get(0));
        if ("null" != objectValue.toString()) {
            INSERT_SQL.append("'").append(String.valueOf(objectValue)).append("'");
        }else{
            INSERT_SQL.append("'").append("'");
        }
        if (list.size() > 1) {
            for (int i = 1; i < list.size(); i++) {
                String key = list.get(i);
                INSERT_SQL.append(" , ");
                if ("null" != jsonObject.get(key).toString()) {
                    INSERT_SQL.append("'").append(String.valueOf(jsonObject.get(key))).append("'");
                }else{
                    INSERT_SQL.append("'").append("'");
                }
            }
        }
        INSERT_SQL.append(");");
        Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
        PreparedStatement p = conn.prepareStatement(INSERT_SQL.toString());
        LOGGER.info("INSERT_SQL query : " + INSERT_SQL);
        p.execute();
        LOGGER.info("Data inserted in the objects table");
        conn.close();
    }

    public static JSONObject getObjectById(int objectId) throws SQLException {
        JSONObject json = new JSONObject();
        // Open a connection
        String GET_MUSEUM_OBJECT_BY_ID = "select * from objects where objectID = " + objectId;
        Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
        PreparedStatement p = conn.prepareStatement(GET_MUSEUM_OBJECT_BY_ID);
        LOGGER.info("Query Running: " + GET_MUSEUM_OBJECT_BY_ID);
        ResultSet result = p.executeQuery();
        ResultSetMetaData resultSetMetaData = result.getMetaData();
        int count = resultSetMetaData.getColumnCount();
        while (result.next()) {
            fetchJson(json, result, resultSetMetaData, count);
        }
        conn.close();
        return json;
    }

    private static void fetchJson(JSONObject json, ResultSet result, ResultSetMetaData resultSetMetaData, int count)
            throws SQLException {
        LOGGER.info("Got results.....");
        for (int i = 1; i <= count; i++) {
            json.put(resultSetMetaData.getColumnLabel(i), result.getString(i));
        }
    }

    public static List<JSONObject> getData(int objectId, String tableName) throws SQLException {
        List<JSONObject> list = new ArrayList<JSONObject>();
        // Open a connection
        String GET_ALL_OBJECTS_BY_ID = "select * from " + (StringUtils.isNoneBlank(tableName) ? tableName : "objects") + " where objectID = " + objectId;
        Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
        PreparedStatement p = conn.prepareStatement(GET_ALL_OBJECTS_BY_ID);
        LOGGER.info("Query Running: " + GET_ALL_OBJECTS_BY_ID);
        ResultSet result = p.executeQuery();
        ResultSetMetaData resultSetMetaData = result.getMetaData();
        System.out.println(resultSetMetaData.getColumnLabel(1));
        int count = resultSetMetaData.getColumnCount();
        while (result.next()) {
            JSONObject json = new JSONObject();
            LOGGER.info("Got results.....");
            for (int i = 3; i <= count; i++) {
                json.put(resultSetMetaData.getColumnLabel(i), result.getString(i));
            }
            list.add(json);
        }
        conn.close();
        return list;
    }

    public static List<JSONObject> getObjects(String tableName) throws SQLException {
        List<JSONObject> list = new ArrayList<JSONObject>();
        // Open a connection
        String GET_ALL_MUSEUM_OBJECTIDS = "select * from " + (StringUtils.isNotBlank(tableName) ? tableName : "objects");
        Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
        PreparedStatement p = conn.prepareStatement(GET_ALL_MUSEUM_OBJECTIDS);
        LOGGER.info("Query Running: " + GET_ALL_MUSEUM_OBJECTIDS);
        ResultSet result = p.executeQuery();
        ResultSetMetaData resultSetMetaData = result.getMetaData();
        int count = resultSetMetaData.getColumnCount();
        Gson gson = new Gson();
        while (result.next()) {
            JSONObject json = new JSONObject();
            fetchJson(json, result, resultSetMetaData, count);
            list.add(json);
        }
        conn.close();
        return list;
    }

    public static void deleteMuseumObject(int objectId) throws SQLException {
        String DELETE_SQL = "DELETE FROM objects WHERE objectId = " + objectId;
        Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
        PreparedStatement p = conn.prepareStatement(DELETE_SQL);
        p.execute();
        conn.close();
    }

    public static void updateMuseumObject(JSONObject jsonObject, int objectId, String tableName) throws SQLException {
        //update object set a='asd',b ='fdff' where objectid = 1;
        StringBuilder UPDATE_SQL = new StringBuilder("update ").append(tableName).append(" set ");
        Set<String> keys = jsonObject.keySet();
        List<String> list = new ArrayList<String>(keys);
        Object value = jsonObject.get(list.get(0));
        String str1 = list.get(0);
        if (str1.equals("isHighlight")) {
            str1 = "highlight";
        }
        if (str1.equals("isPublicDomain")) {
            str1 = "publicDomain";
        }
        if ("null" != value.toString()) {
            UPDATE_SQL.append(str1).append(" = ").append("'").append(String.valueOf(value)).append("'");
        }
        if (list.size() > 1) {
            for (int i = 1; i < list.size(); i++) {
                String key = list.get(i);
                if (key.equals("isHighlight")) {
                    key = "highlight";
                }
                if (key.equals("isPublicDomain")) {
                    key = "publicDomain";
                }
                if ("null" != jsonObject.get(key).toString()) {
                    UPDATE_SQL.append(" , ").append(key).append(" = ").append("'")
                            .append(String.valueOf(jsonObject.get(key))).append("'");
                }
            }
        }
        UPDATE_SQL.append(" where objectId = ").append(objectId);
        Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
        PreparedStatement p = conn.prepareStatement(UPDATE_SQL.toString());
        p.executeUpdate();
        conn.close();
    }
}
