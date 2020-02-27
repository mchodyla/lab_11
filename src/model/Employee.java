package model;

public class Employee
{
    private String name;
    private String email;
    private String departmentName;

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