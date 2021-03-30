package model;

public abstract class Event {
    private final int id;
    private String name;
    private Location location;
    static int numberOfEvents = 0;

    public Event(String name, Location location) {
        this.id = numberOfEvents;
        this.name = name;
        this.location = location;
        numberOfEvents++;
    }

    public int getId() {
        return id;
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

    public static int getNumberOfEvents() {
        return numberOfEvents;
    }

    @Override
    public String toString(){
        return name + " / " + location;
    }
}
