package Employee;

import com.github.openjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class EmployeeService implements IEmployeeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeService.class);

    @Override
    public List<JSONObject> getEmployeeGender() throws SQLException {
        return EmployeeDatabaseController.getEmployeeGender();
    }

    @Override
    public List<JSONObject> getEmployeeAvgSalary() throws SQLException {
        return EmployeeDatabaseController.getEmployeeAvgSalary();
    }

    @Override
    public List<JSONObject> getEmployeeDetailsWhoWorkedInTwoDepartments() throws SQLException {
        return EmployeeDatabaseController.getEmployeeDetailsWhoWorkedInTwoDepartments();
    }

    @Override
    public List<JSONObject> getHighestPaidEmployeeDetails() throws SQLException {
        return EmployeeDatabaseController.getHighestPaidEmployeeDetails();
    }

    @Override
    public List<JSONObject> getSecondHighestPaidEmployeeDetails() throws SQLException {
        return EmployeeDatabaseController.getSecondHighestPaidEmployeeDetails();
    }

    @Override
    public List<JSONObject> getMonthWithMostHires() throws SQLException {
        return EmployeeDatabaseController.getMonthWithMostHires();
    }

    @Override
    public List<JSONObject> getYoungestEmployeeDetails() throws SQLException {
        return EmployeeDatabaseController.getYoungestEmployeeDetails();
    }

    @Override
    public List<JSONObject> getEmployeeDetailsWithoutVowelInName() throws SQLException {
        return EmployeeDatabaseController.getEmployeeDetailsWithoutVowelInName();
    }
}
