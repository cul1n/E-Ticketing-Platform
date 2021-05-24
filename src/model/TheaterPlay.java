package model;

public class TheaterPlay extends Event{
    private String playName;
    private String director;
    private int time;

    public TheaterPlay(String name, Location location, String playName, String director, int time) {
        super(name, location);
        this.playName = playName;
        this.director = director;
        this.time = time;
    }

    public TheaterPlay(int id, String name, Location location, String playName, String director, int time) {
        super(id, name, location);
        this.playName = playName;
        this.director = director;
        this.time = time;
    }

    public String getPlayName() {
        return playName;
    }

    public void setPlayName(String playName) {
        this.playName = playName;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return super.toString() + " / " + playName + " / " + director + " / " + time + " minutes \n";
    }
}
