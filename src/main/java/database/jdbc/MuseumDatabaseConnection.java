package database.jdbc;

import com.github.openjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MuseumDatabaseConnection {

    private static final Logger LOGGER = LoggerFactory.getLogger(MuseumDatabaseConnection.class);
    static final String DB_URL = "jdbc:mysql://localhost/MUSEUMDB";
    static final String USER = "root";
    static final String PASS = "Am@29032";

    public static List<JSONObject> getObjectDetailsWhereTagsAreMoreThanOne() throws SQLException {
        List<JSONObject> list = new ArrayList<JSONObject>();
        // Open a connection
        String QUERY = "select * from objects where objectID in (SELECT objectID FROM tags group by objectID HAVING COUNT(objectID) > 1)";
        Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
        PreparedStatement p = conn.prepareStatement(QUERY);
        LOGGER.info("Query Running: " + QUERY);
        ResultSet result = p.executeQuery();
        ResultSetMetaData resultSetMetaData = result.getMetaData();
        int count = resultSetMetaData.getColumnCount();
        while (result.next()) {
            JSONObject json = new JSONObject();
            fetchJson(json, result, resultSetMetaData, count);
            list.add(json);
        }
        conn.close();
        return list;
    }

    private static void fetchJson(JSONObject json, ResultSet result, ResultSetMetaData resultSetMetaData, int count)
            throws SQLException {
        LOGGER.info("Got results.....");
        for (int i = 1; i <= count; i++) {
            json.put(resultSetMetaData.getColumnLabel(i), result.getString(i));
        }
    }

    public static List<JSONObject> getObjectDetailsForConstituents(int value) throws SQLException {
        List<JSONObject> list = new ArrayList<JSONObject>();
        // Open a connection
        String QUERY = "select * from objects where objectID in (SELECT objectID FROM constituents group by objectID HAVING COUNT(objectID) > ?)";
        Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
        PreparedStatement p = conn.prepareStatement(QUERY);
        p.setInt(1,value);
        LOGGER.info("Query Running: " + QUERY);
        ResultSet result = p.executeQuery();
        ResultSetMetaData resultSetMetaData = result.getMetaData();
        int count = resultSetMetaData.getColumnCount();
        while (result.next()) {
            JSONObject json = new JSONObject();
            fetchJson(json, result, resultSetMetaData, count);
            list.add(json);
        }
        conn.close();
        return list;
    }

    public static List<JSONObject> getObjectsDetailsByJoin(int value) throws SQLException {
        List<JSONObject> list = new ArrayList<JSONObject>();
        // Open a connection
        String QUERY = "SELECT * FROM objects o LEFT JOIN tags t ON o.objectID = t.objectID LEFT JOIN measurements m ON o.objectID = m.objectID LEFT JOIN constituents c ON o.objectID = c.objectID where o.objectID= ?;";
        Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
        PreparedStatement p = conn.prepareStatement(QUERY);
        p.setInt(1,value);
        LOGGER.info("Query Running: " + QUERY);
        ResultSet result = p.executeQuery();
        ResultSetMetaData resultSetMetaData = result.getMetaData();
        int count = resultSetMetaData.getColumnCount();
        while (result.next()) {
            JSONObject json = new JSONObject();
            fetchJson(json, result, resultSetMetaData, count);
            list.add(json);
        }
        conn.close();
        return list;
    }

    public static List<JSONObject> getObjectsDetailsWithoutUsingJoin() throws SQLException {
        List<JSONObject> list = new ArrayList<JSONObject>();
        // Open a connection
        String QUERY = "SELECT objectID, objectName, constituentID, constituentWikidata_URL FROM objects, constituents WHERE objects.objectID = constituents.objectID;";
        Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
        PreparedStatement p = conn.prepareStatement(QUERY);
        LOGGER.info("Query Running: " + QUERY);
        ResultSet result = p.executeQuery();
        ResultSetMetaData resultSetMetaData = result.getMetaData();
        int count = resultSetMetaData.getColumnCount();
        while (result.next()) {
            JSONObject json = new JSONObject();
            fetchJson(json, result, resultSetMetaData, count);
            list.add(json);
        }
        conn.close();
        return list;
    }

    public static List<JSONObject> getObjectsDetailsUsingAND() throws SQLException {
        List<JSONObject> list = new ArrayList<JSONObject>();
        // Open a connection
        String QUERY = "SELECT * FROM objects WHERE department='The American Wing' AND culture='Mexican' AND (accessionYear= 1967 OR accessionYear= 1979);";
        Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
        PreparedStatement p = conn.prepareStatement(QUERY);
        LOGGER.info("Query Running: " + QUERY);
        ResultSet result = p.executeQuery();
        ResultSetMetaData resultSetMetaData = result.getMetaData();
        int count = resultSetMetaData.getColumnCount();
        while (result.next()) {
            JSONObject json = new JSONObject();
            fetchJson(json, result, resultSetMetaData, count);
            list.add(json);
        }
        conn.close();
        return list;
    }

    public static List<JSONObject> getObjectsDetailsUsingAscDesc() throws SQLException {
        List<JSONObject> list = new ArrayList<JSONObject>();
        // Open a connection
        String QUERY = "SELECT * FROM objects ORDER BY accessionYear ASC, objectName DESC;";
        Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
        PreparedStatement p = conn.prepareStatement(QUERY);
        LOGGER.info("Query Running: " + QUERY);
        ResultSet result = p.executeQuery();
        ResultSetMetaData resultSetMetaData = result.getMetaData();
        int count = resultSetMetaData.getColumnCount();
        while (result.next()) {
            JSONObject json = new JSONObject();
            fetchJson(json, result, resultSetMetaData, count);
            list.add(json);
        }
        conn.close();
        return list;
    }

    public static List<JSONObject> getObjectsDetailsUsingMinMax() throws SQLException {
        List<JSONObject> list = new ArrayList<JSONObject>();
        // Open a connection
        String QUERY = "SELECT MIN(Diameter) AS SmallestDiameter, MAX(Diameter) AS LargestDiameter FROM measurements;";
        Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
        PreparedStatement p = conn.prepareStatement(QUERY);
        LOGGER.info("Query Running: " + QUERY);
        ResultSet result = p.executeQuery();
        ResultSetMetaData resultSetMetaData = result.getMetaData();
        int count = resultSetMetaData.getColumnCount();
        while (result.next()) {
            JSONObject json = new JSONObject();
            fetchJson(json, result, resultSetMetaData, count);
            list.add(json);
        }
        conn.close();
        return list;
    }

    public static List<JSONObject> getObjectsDetailsUsingNotBetween() throws SQLException {
        List<JSONObject> list = new ArrayList<JSONObject>();
        // Open a connection
        String QUERY = "SELECT * FROM objects WHERE accessionYear NOT BETWEEN 1960 AND 1980;";
        Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
        PreparedStatement p = conn.prepareStatement(QUERY);
        LOGGER.info("Query Running: " + QUERY);
        ResultSet result = p.executeQuery();
        ResultSetMetaData resultSetMetaData = result.getMetaData();
        int count = resultSetMetaData.getColumnCount();
        while (result.next()) {
            JSONObject json = new JSONObject();
            fetchJson(json, result, resultSetMetaData, count);
            list.add(json);
        }
        conn.close();
        return list;
    }

    public static List<JSONObject> getObjectsDetailsUsingCount() throws SQLException {
        List<JSONObject> list = new ArrayList<JSONObject>();
        // Open a connection
        String QUERY = "SELECT COUNT(objectID), objectName FROM objects GROUP BY objectName ORDER BY COUNT(objectID) DESC;";
        Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
        PreparedStatement p = conn.prepareStatement(QUERY);
        LOGGER.info("Query Running: " + QUERY);
        ResultSet result = p.executeQuery();
        ResultSetMetaData resultSetMetaData = result.getMetaData();
        int count = resultSetMetaData.getColumnCount();
        while (result.next()) {
            JSONObject json = new JSONObject();
            fetchJson(json, result, resultSetMetaData, count);
            list.add(json);
        }
        conn.close();
        return list;
    }

    public static List<JSONObject> getObjectsDetailsUsingLeftJoin() throws SQLException {
        List<JSONObject> list = new ArrayList<JSONObject>();
        // Open a connection
        String QUERY = "SELECT objects.objectName, COUNT(objects.objectID) AS NumberOfObjects FROM objects LEFT JOIN constituents ON objects.objectID = constituents.constituentID GROUP BY objectName;";
        Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
        PreparedStatement p = conn.prepareStatement(QUERY);
        LOGGER.info("Query Running: " + QUERY);
        ResultSet result = p.executeQuery();
        ResultSetMetaData resultSetMetaData = result.getMetaData();
        int count = resultSetMetaData.getColumnCount();
        while (result.next()) {
            JSONObject json = new JSONObject();
            fetchJson(json, result, resultSetMetaData, count);
            list.add(json);
        }
        conn.close();
        return list;
    }

    public static List<JSONObject> getObjectsDetailsUsingExists() throws SQLException {
        List<JSONObject> list = new ArrayList<JSONObject>();
        // Open a connection
        String QUERY = "SELECT objectID, objectName FROM objects WHERE EXISTS (SELECT constituentID FROM constituents WHERE objects.objectID = constituents.constituentID);";
        Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
        PreparedStatement p = conn.prepareStatement(QUERY);
        LOGGER.info("Query Running: " + QUERY);
        ResultSet result = p.executeQuery();
        ResultSetMetaData resultSetMetaData = result.getMetaData();
        int count = resultSetMetaData.getColumnCount();
        while (result.next()) {
            JSONObject json = new JSONObject();
            fetchJson(json, result, resultSetMetaData, count);
            list.add(json);
        }
        conn.close();
        return list;
    }

}
