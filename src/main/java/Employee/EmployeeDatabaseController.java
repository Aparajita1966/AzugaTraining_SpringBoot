package Employee;

import com.github.openjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class EmployeeDatabaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeDatabaseController.class);

    static final String DB_URL = "jdbc:mysql://localhost/employeesDB";

    static final String USER = "root";

    static final String PASS = "Am@29032";

    /**
     * 1. Finds the number of Male (M) and Female (F) employees in the database and order the counts in descending order.
     */

    public static List<JSONObject> getEmployeeGender() throws SQLException {
        List<JSONObject> list = new ArrayList<JSONObject>();
        // Open a connection
        String QUERY = "SELECT gender, COUNT(*) AS total_count FROM employees GROUP BY gender ORDER BY total_count DESC;";
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


    /**
     * 2. Finds the average salary by employee title, round to 2 decimal places and order by descending order.
     */

    public static List<JSONObject> getEmployeeAvgSalary() throws SQLException {
        List<JSONObject> list = new ArrayList<JSONObject>();
        // Open a connection
        String QUERY = "SELECT title, ROUND(AVG(salary), 2) as avg_salary FROM titles t JOIN salaries s ON s.emp_no = t.emp_no GROUP BY title ORDER BY avg_salary DESC;";
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



    /***
     * 3. Find all the employees that have worked in at least 2 departments. Show their first name, last_name and
     * the number of departments they work in. Display all results in ascending order.
     */
    public static List<JSONObject> getEmployeeDetailsWhoWorkedInTwoDepartments() throws SQLException {
        List<JSONObject> list = new ArrayList<JSONObject>();
        // Open a connection
        String QUERY = "SELECT CONCAT(e.first_name, ' ' , e.last_name) AS name, COUNT(*) AS number_of_departments FROM employees e JOIN dept_emp d ON e.emp_no = d.emp_no GROUP BY d.emp_no HAVING COUNT(*) > 1 ORDER BY name ASC;";
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


    /**
     * 4. Display the first name, last name, and salary of the highest paid employee.
     */
    public static List<JSONObject> getHighestPaidEmployeeDetails() throws SQLException {
        List<JSONObject> list = new ArrayList<JSONObject>();
        // Open a connection
        String QUERY = "SELECT CONCAT(employees.first_name, ' ', employees.last_name) AS employee_name, salaries.salary FROM employees JOIN salaries ON employees.emp_no = salaries.emp_no WHERE salaries.salary = (SELECT MAX(salaries.salary) FROM salaries);";
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

    /**
     * 5. Display the first name, last name, and salary of the second highest paid employee.
     */
    public static List<JSONObject> getSecondHighestPaidEmployeeDetails() throws SQLException {
        List<JSONObject> list = new ArrayList<JSONObject>();
        // Open a connection
        String QUERY = "SELECT CONCAT(employees.first_name, ' ', employees.last_name) AS employee_name, salaries.salary FROM employees JOIN salaries ON employees.emp_no = salaries.emp_no WHERE salaries.salary < (SELECT MAX(salaries.salary) FROM salaries) ORDER BY salaries.salary DESC LIMIT 10;";
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

    /**
     * 6. Display the month and total hires for the month with the most hires.
     */
    public static List<JSONObject> getMonthWithMostHires() throws SQLException {
        List<JSONObject> list = new ArrayList<JSONObject>();
        // Open a connection
        String QUERY = "SELECT DATE_FORMAT(hire_date, '%M') AS month, COUNT(*) AS total_hires FROM employees GROUP BY month ORDER BY total_hires DESC LIMIT 10;\n";
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

    /**
     * 7. Display each department and the age of the youngest employee at hire date.
     */
    public static List<JSONObject> getYoungestEmployeeDetails() throws SQLException {
        List<JSONObject> list = new ArrayList<JSONObject>();
        // Open a connection
        String QUERY = "SELECT dept.dept_name, MIN(TIMESTAMPDIFF(YEAR, e.birth_date, e.hire_date)) AS age_hire_date FROM employees e JOIN dept_emp d_emp ON e.emp_no = d_emp.emp_no JOIN departments dept ON d_emp.dept_no = dept.dept_no GROUP BY dept.dept_name LIMIT 30;";
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


    /**
     * 8. Find all the employees that do not contain vowels in their first name and display the department they work in.
     */
    public static List<JSONObject> getEmployeeDetailsWithoutVowelInName() throws SQLException {
        List<JSONObject> list = new ArrayList<JSONObject>();
        // Open a connection
        String QUERY = "SELECT e.first_name, dep.dept_name FROM employees e JOIN dept_emp de ON e.emp_no = de.emp_no JOIN departments dep ON de.dept_no = dep.dept_no WHERE e.first_name NOT LIKE '%a%' AND e.first_name NOT LIKE '%e%' AND e.first_name NOT LIKE '%i%' AND e.first_name NOT LIKE '%o%' AND e.first_name NOT LIKE '%u%' LIMIT 20;";
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
