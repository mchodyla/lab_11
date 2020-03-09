package model;

import db.DB_utility;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Location {
    private String room;
    private String place;

    public static ObservableList<Location> getListFromTable(){
        ResultSet rs = DB_utility.executeQuery("select * from location");
        ObservableList<Location> outputList = FXCollections.observableArrayList();
        try {
            while (rs.next()) {
                Location location = new Location(
                        rs.getString("ROOM"),
                        rs.getString("PLACE"));
                outputList.add(location);
            }
            return outputList;
        } catch (SQLException e){
            System.err.println("SQLException ! : " + e.toString());
            return null;
        }
    }

    public static ObservableList<Location> getFreeSpaceList(){
        ResultSet rs = DB_utility.executeQuery("select room, place from location left outer join tool on (location.room = tool.location_room and location.place=tool.location_place) left outer join machine on (location.room = machine.location_room and location.place=machine.location_place) left outer join material on (location.room = material.location_room and location.place=material.location_place) where tool.name is null and machine.name is null and material.name is null order by room");
        ObservableList<Location> outputList = FXCollections.observableArrayList();
        try {
            while (rs.next()) {
                Location location = new Location(
                        rs.getString("ROOM"),
                        rs.getString("PLACE"));
                outputList.add(location);
            }
            return outputList;
        } catch (SQLException e){
            System.err.println("SQLException ! : " + e.toString());
            return null;
        }
    }

    public String getRoom() {
        return room;
    }

    @Override
    public String toString() {
        return "pok. "+room +" , "+ place;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Location(String room, String place){
        this.room = room;
        this.place = place;
    }
}
