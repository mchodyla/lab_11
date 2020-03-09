package model;

import db.DB_utility;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ServiceType {
    private String name;

    public static ObservableList<ServiceType> getListFromTable(){
        ResultSet rs = DB_utility.executeQuery("SELECT * FROM SERVICE_TYPE");
        ObservableList<ServiceType> outputList = FXCollections.observableArrayList();
        try{
            while (rs.next()){
                ServiceType serviceType = new ServiceType(rs.getString("SERVICE_TYPE"));
                outputList.add(serviceType);
            }
            return outputList;
        }catch(SQLException e){
            System.err.println("SQLException ! : " + e.toString());
            return null;
        }
    }

    public ServiceType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
