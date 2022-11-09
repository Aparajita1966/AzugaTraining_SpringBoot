package com.training.Azuga;

import com.github.openjson.JSONObject;
import database.Museum;

import java.sql.SQLException;
import java.util.List;

public interface IMuseumService {
    void addMuseum(JSONObject jsonObject) throws SQLException;

    Museum getMuseum(int objectId) throws SQLException;

    List<Museum> getMuseumAll(String tableName) throws SQLException;

    void deleteMuseumObject(int objectId) throws SQLException;

    void updateMuseumObject(JSONObject jsonObject) throws SQLException;

    List<JSONObject> getObjectDetailsWhereTagsAreMoreThanOne() throws SQLException;

    List<JSONObject> getObjectDetailsForConstituents(int value) throws SQLException;

    List<JSONObject> getObjectsDetailsByJoin(int value) throws SQLException;

    List<JSONObject> getObjectsDetailsWithoutUsingJoin() throws SQLException;

    List<JSONObject> getObjectsDetailsUsingAND() throws SQLException;

    List<JSONObject> getObjectsDetailsUsingAscDesc() throws SQLException;

    List<JSONObject> getObjectsDetailsUsingMinMax() throws SQLException;

    List<JSONObject> getObjectsDetailsUsingNotBetween() throws SQLException;

    List<JSONObject> getObjectsDetailsUsingCount() throws SQLException;

    List<JSONObject> getObjectsDetailsUsingLeftJoin() throws SQLException;

    List<JSONObject> getObjectsDetailsUsingExists() throws SQLException;
}
