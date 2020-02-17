package model;

public class Location {
    private String room;
    private String place;

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
