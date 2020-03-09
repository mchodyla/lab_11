package model;

import db.DB_utility;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import oracle.sql.DATE;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Tool implements Resource{
    private Integer id;
    private String name;
    private Date purchase_date;
    private Float price;
    private Location location;

    public static ObservableList<Tool> getListFromTable() {
        ResultSet rs = DB_utility.executeQuery("SELECT * FROM TOOL");
        ObservableList<Tool> outputList = FXCollections.observableArrayList();
        try{
            while (rs.next()) {
                Tool tool = new Tool(
                        rs.getInt("ID"),
                        rs.getString("NAME"),
                        rs.getDate("PURCHASE_DATE"),
                        rs.getFloat("PRICE"),
                        new Location(
                                rs.getString("LOCATION_ROOM"),
                                rs.getString("LOCATION_PLACE")));
                outputList.add(tool);
            }
            return outputList;
        } catch (SQLException e){
            System.err.println("SQL Exception ! : " + e.toString());
            return null;
        }
    }
    
    @Override
    public String toString() {
        return name +" "+ purchase_date.toString() +" "+ price.toString() +" "+ location.toString();
    }

    public Tool(Integer id, String name, Date purchase_date, Float price, Location location) {
        this.id = id;
        this.name = name;
        this.purchase_date = purchase_date;
        this.price = price;
        this.location = location;
    }

    public Tool(){ }

    public String getResourceName(){
        return name + "-" + id.toString();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getPurchase_date() {
        return purchase_date;
    }

    public void setPurchase_date(Date purchase_date) {
        this.purchase_date = purchase_date;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
