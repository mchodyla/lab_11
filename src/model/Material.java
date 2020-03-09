package model;

import db.DB_utility;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import oracle.sql.DATE;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Material implements Resource{
    private Integer id;
    private String name;
    private String amount;
    private Date purchase_date;
    private Float price;
    private Location location;

    public static ObservableList<Material> getListFromTable(){
        ResultSet rs = DB_utility.executeQuery("SELECT * FROM MATERIAL");
        ObservableList<Material> outputList = FXCollections.observableArrayList();
        try {
            while (rs.next()) {
                Material material = new Material(
                        rs.getInt("ID"),
                        rs.getString("NAME"),
                        rs.getString("AMOUNT"),
                        rs.getDate("PURCHASE_DATE"),
                        rs.getFloat("PRICE"),
                        new Location(rs.getString("LOCATION_ROOM"),rs.getString("LOCATION_PLACE"))
                );
                outputList.add(material);
            }
            return outputList;
        } catch (SQLException e){
            System.err.println("SQLException ! : " + e.toString());
            return null;
        }
    }

    public Material(Integer id, String name, String amount, Date purchase_date, Float price, Location location) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.purchase_date = purchase_date;
        this.price = price;
        this.location = location;
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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
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

    @Override
    public String getResourceName() {
        return name + "-" + id.toString();
    }
}
