package model;

import db.DB_utility;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Employee
{
    private String name;
    private String email;
    private String departmentName;

    public static ObservableList<Employee> getListFromTable() {
        ResultSet rs = DB_utility.executeQuery("SELECT * FROM EMPLOYEE");
        ObservableList<Employee> outputList = FXCollections.observableArrayList();
        try{
            while (rs.next()){
                Employee employee = new Employee(
                        rs.getString("NAME"),
                        rs.getString("EMAIL"),
                        rs.getString("DEPARTMENT_NAME"));
                outputList.add(employee);
            }
            return outputList;
        }catch(SQLException e){
            System.err.println("SQLException ! : " + e.toString());
            return null;
        }
    }

    public Employee(String name, String email, String departmentName) {
        this.name = name;
        this.email = email;
        this.departmentName = departmentName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()){
            return false;
        } else {
            return (this.getName().equals(((Employee) obj).getName()));
        }
    }

    @Override
    public int hashCode() {
        return 7 + 5*this.getName().hashCode();
    }
}