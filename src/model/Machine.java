package model;

public class Machine implements Resource{
    private Integer id;
    private String name;
    private Location location;

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
