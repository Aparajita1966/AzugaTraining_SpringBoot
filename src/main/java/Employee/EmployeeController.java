package Employee;

import com.github.openjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;

public class EmployeeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);

    public List<JSONObject> getEmployeeGender() {
        try {
            EmployeeService employeeService = new EmployeeService();
            List<JSONObject> list = employeeService.getEmployeeGender();
            if (list.size() != 0) {
                for (JSONObject json : list) {
                    LOGGER.info("Query Results :" + json);
                }
            } else {
                LOGGER.info("No records found.");
            }
        } catch (SQLException e) {
            LOGGER.error("Error in query " + e);
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<JSONObject> getEmployeeAvgSalary() {
        try {
            EmployeeService employeeService = new EmployeeService();
            List<JSONObject> list = employeeService.getEmployeeAvgSalary();
            if (list.size() != 0) {
                for (JSONObject json : list) {
                    LOGGER.info("Query Results :" + json);
                }
            } else {
                LOGGER.info("No records found.");
            }
        } catch (SQLException e) {
            LOGGER.error("Error in query " + e);
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<JSONObject> getEmployeeDetailsWhoWorkedInTwoDepartments() {
        try {
            EmployeeService employeeService = new EmployeeService();
            List<JSONObject> list = employeeService.getEmployeeDetailsWhoWorkedInTwoDepartments();
            if (list.size() != 0) {
                for (JSONObject json : list) {
                    LOGGER.info("Query Results :" + json);
                }
            } else {
                LOGGER.info("No records found.");
            }
        } catch (SQLException e) {
            LOGGER.error("Error in query " + e);
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<JSONObject> getHighestPaidEmployeeDetails() {
        try {
            EmployeeService employeeService = new EmployeeService();
            List<JSONObject> list = employeeService.getHighestPaidEmployeeDetails();
            if (list.size() != 0) {
                for (JSONObject json : list) {
                    LOGGER.info("Query Results :" + json);
                }
            } else {
                LOGGER.info("No records found.");
            }
        } catch (SQLException e) {
            LOGGER.error("Error in query " + e);
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<JSONObject> getSecondHighestPaidEmployeeDetails() {
        try {
            EmployeeService employeeService = new EmployeeService();
            List<JSONObject> list = employeeService.getSecondHighestPaidEmployeeDetails();
            if (list.size() != 0) {
                for (JSONObject json : list) {
                    LOGGER.info("Query Results :" + json);
                }
            } else {
                LOGGER.info("No records found.");
            }
        } catch (SQLException e) {
            LOGGER.error("Error in query " + e);
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<JSONObject> getMonthWithMostHires() {
        try {
            EmployeeService employeeService = new EmployeeService();
            List<JSONObject> list = employeeService.getMonthWithMostHires();
            if (list.size() != 0) {
                for (JSONObject json : list) {
                    LOGGER.info("Query Results :" + json);
                }
            } else {
                LOGGER.info("No records found.");
            }
        } catch (SQLException e) {
            LOGGER.error("Error in query " + e);
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<JSONObject> getYoungestEmployeeDetails() {
        try {
            EmployeeService employeeService = new EmployeeService();
            List<JSONObject> list = employeeService.getYoungestEmployeeDetails();
            if (list.size() != 0) {
                for (JSONObject json : list) {
                    LOGGER.info("Query Results :" + json);
                }
            } else {
                LOGGER.info("No records found.");
            }
        } catch (SQLException e) {
            LOGGER.error("Error in query " + e);
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<JSONObject> getEmployeeDetailsWithoutVowelInName() {
        try {
            EmployeeService employeeService = new EmployeeService();
            List<JSONObject> list = employeeService.getEmployeeDetailsWithoutVowelInName();
            if (list.size() != 0) {
                for (JSONObject json : list) {
                    LOGGER.info("Query Results :" + json);
                }
            } else {
                LOGGER.info("No records found.");
            }
        } catch (SQLException e) {
            LOGGER.error("Error in query " + e);
            throw new RuntimeException(e);
        }
        return null;
    }
}
