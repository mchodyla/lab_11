package model;

import oracle.sql.DATE;

public class Tool {
    private Integer id;
    private String name;
    private DATE purchase_date;
    private Float price;
    private Location location;

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

    public DATE getPurchase_date() {
        return purchase_date;
    }

    public void setPurchase_date(DATE purchase_date) {
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
