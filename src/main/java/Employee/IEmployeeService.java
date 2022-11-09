package Employee;

import com.github.openjson.JSONObject;

import java.sql.SQLException;
import java.util.List;


public interface IEmployeeService {

    List<JSONObject> getEmployeeGender() throws SQLException;

    List<JSONObject> getEmployeeAvgSalary() throws SQLException;

    List<JSONObject> getEmployeeDetailsWhoWorkedInTwoDepartments() throws SQLException;

    List<JSONObject> getHighestPaidEmployeeDetails() throws SQLException;

    List<JSONObject> getSecondHighestPaidEmployeeDetails() throws SQLException;

    List<JSONObject> getMonthWithMostHires() throws SQLException;

    List<JSONObject> getYoungestEmployeeDetails() throws SQLException;

    List<JSONObject> getEmployeeDetailsWithoutVowelInName() throws SQLException;
}
