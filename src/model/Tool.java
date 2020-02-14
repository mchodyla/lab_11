package model;

import oracle.sql.DATE;

import java.sql.Date;

public class Tool {
    private Integer id;
    private String name;
    private Date purchase_date;
    private Float price;
    private Location location;

    @Override
    public String toString() {
        return name +" "+ purchase_date.toString() +" "+ price.toString() +" "+ location.toString();
    }

    public String getUniqueName(){
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
