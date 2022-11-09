package com.training.Azuga;

import com.github.openjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;

public class MuseumController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MuseumController.class);

    public List<JSONObject> getObjectDetailsWhereTagsAreMoreThanOne() {
        try {
            MuseumService museumService = new MuseumService();
            List<JSONObject> list = museumService.getObjectDetailsWhereTagsAreMoreThanOne();
            if (list.size() != 0) {
                for (JSONObject json : list) {
                    LOGGER.info("For objectId " + json.get("objectID") + " where tags are more than one :" + json);
                }
            } else {
                LOGGER.info("No objects found.");
            }
        } catch (SQLException e) {
            LOGGER.error("Error in query " + e);
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<JSONObject> getObjectDetailsForConstituents(int value) {
        try {
            MuseumService museumService = new MuseumService();
            List<JSONObject> list = museumService.getObjectDetailsForConstituents(value);
            if (list.size() != 0) {
                for (JSONObject json : list) {
                    LOGGER.info("For objectId " + json.get("objectID") + " where constituents are more than " + value + ":" + json);
                }
            } else {
                LOGGER.info("No objects found.");
            }
        } catch (SQLException e) {
            LOGGER.error("Error in query " + e);
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<JSONObject> getObjectsDetailsByJoin(int value) {
        try {
            MuseumService museumService = new MuseumService();
            List<JSONObject> list = museumService.getObjectsDetailsByJoin(value);
            if (list.size() != 0) {
                for (JSONObject json : list) {
                    LOGGER.info("For objectId " + json.get("objectID") + " the full object details are :" + json);
                }
            } else {
                LOGGER.info("No objects found.");
            }
        } catch (SQLException e) {
            LOGGER.error("Error in query " + e);
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<JSONObject> getObjectsDetailsWithoutUsingJoin() {
        try {
            MuseumService museumService = new MuseumService();
            List<JSONObject> list = museumService.getObjectsDetailsWithoutUsingJoin();
            if (list.size() != 0) {
                for (JSONObject json : list) {
                    LOGGER.info("For objectId " + json.get("objectID") + " the object details are :" + json);
                }
            } else {
                LOGGER.info("No objects found.");
            }
        } catch (SQLException e) {
            LOGGER.error("Error in query " + e);
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<JSONObject> getObjectsDetailsUsingAND() {
        try {
            MuseumService museumService = new MuseumService();
            List<JSONObject> list = museumService.getObjectsDetailsUsingAND();
            if (list.size() != 0) {
                for (JSONObject json : list) {
                    LOGGER.info("For objectId " + json.get("objectID") + " the object details are :" + json);
                }
            } else {
                LOGGER.info("No objects found.");
            }
        } catch (SQLException e) {
            LOGGER.error("Error in query " + e);
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<JSONObject> getObjectsDetailsUsingAscDesc() {
        try {
            MuseumService museumService = new MuseumService();
            List<JSONObject> list = museumService.getObjectsDetailsUsingAscDesc();
            if (list.size() != 0) {
                for (JSONObject json : list) {
                    LOGGER.info("For objectId " + json.get("objectID") + " the object details are :" + json);
                }
            } else {
                LOGGER.info("No objects found.");
            }
        } catch (SQLException e) {
            LOGGER.error("Error in query " + e);
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<JSONObject> getObjectsDetailsUsingMinMax() {
        try {
            MuseumService museumService = new MuseumService();
            List<JSONObject> list = museumService.getObjectsDetailsUsingMinMax();
            if (list.size() != 0) {
                for (JSONObject json : list) {
                    LOGGER.info("The smallest and largest diameter are :" + json);
                }
            } else {
                LOGGER.info("No objects found.");
            }
        } catch (SQLException e) {
            LOGGER.error("Error in query " + e);
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<JSONObject> getObjectsDetailsUsingNotBetween() {
        try {
            MuseumService museumService = new MuseumService();
            List<JSONObject> list = museumService.getObjectsDetailsUsingNotBetween();
            if (list.size() != 0) {
                for (JSONObject json : list) {
                    LOGGER.info("For objectId " + json.get("objectID") + " the object details are :" + json);
                }
            } else {
                LOGGER.info("No objects found.");
            }
        } catch (SQLException e) {
            LOGGER.error("Error in query " + e);
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<JSONObject> getObjectsDetailsUsingCount() {
        try {
            MuseumService museumService = new MuseumService();
            List<JSONObject> list = museumService.getObjectsDetailsUsingCount();
            if (list.size() != 0) {
                for (JSONObject json : list) {
                    LOGGER.info("Query Results of the object details are :" + json);
                }
            } else {
                LOGGER.info("No objects found.");
            }
        } catch (SQLException e) {
            LOGGER.error("Error in query " + e);
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<JSONObject> getObjectsDetailsUsingLeftJoin() {
        try {
            MuseumService museumService = new MuseumService();
            List<JSONObject> list = museumService.getObjectsDetailsUsingLeftJoin();
            if (list.size() != 0) {
                for (JSONObject json : list) {
                    LOGGER.info("Query Results of the object details are :" + json);
                }
            } else {
                LOGGER.info("No objects found.");
            }
        } catch (SQLException e) {
            LOGGER.error("Error in query " + e);
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<JSONObject> getObjectsDetailsUsingExists() {
        try {
            MuseumService museumService = new MuseumService();
            List<JSONObject> list = museumService.getObjectsDetailsUsingExists();
            if (list.size() != 0) {
                for (JSONObject json : list) {
                    LOGGER.info("Query Results of the object details are :" + json);
                }
            } else {
                LOGGER.info("No objects found.");
            }
        } catch (SQLException e) {
            LOGGER.error("Error in query " + e);
            throw new RuntimeException(e);
        }
        return null;
    }
}
