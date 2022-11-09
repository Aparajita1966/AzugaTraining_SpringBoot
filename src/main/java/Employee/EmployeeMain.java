package Employee;


public class EmployeeMain {


    public static void main(String[] args) {
        EmployeeController employeeController = new EmployeeController();
        employeeController.getEmployeeGender();
        employeeController.getEmployeeAvgSalary();
        employeeController.getEmployeeDetailsWhoWorkedInTwoDepartments();
        employeeController.getHighestPaidEmployeeDetails();
        employeeController.getSecondHighestPaidEmployeeDetails();
        employeeController.getMonthWithMostHires();
        employeeController.getYoungestEmployeeDetails();
        employeeController.getEmployeeDetailsWithoutVowelInName();
    }
}
