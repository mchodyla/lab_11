package model;

import db.DB_utility;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Machine implements Resource{
    private Integer id;
    private String name;
    private Location location;

    public static ObservableList<Machine> getListFromTable(){
        ResultSet rs = DB_utility.executeQuery("SELECT * FROM MACHINE");
        ObservableList<Machine> outputList = FXCollections.observableArrayList();
        try {
            while (rs.next()) {
                Machine machine = new Machine(
                        rs.getInt("ID"),
                        rs.getString("NAME"),
                        new Location(rs.getString("LOCATION_ROOM"),rs.getString("LOCATION_PLACE")));
                outputList.add(machine);
            }
            return outputList;
        } catch (SQLException e){
            System.err.println("SQLException ! : " + e.toString());
            return null;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Machine(Integer id, String name, Location location) {
        this.id = id;
        this.name = name;
        this.location = location;
    }

    public Machine(){

    };

    @Override
    public String getResourceName() {
        return name + "-" + id.toString();
    }
}
