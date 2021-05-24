package model;

public class SportMatch extends Event{
    private String sportName;
    private String team1;
    private String team2;
    private int estimatedTime;

    public SportMatch(String name, Location location, String sportName, String team1, String team2, int estimatedTime) {
        super(name, location);
        this.sportName = sportName;
        this.team1 = team1;
        this.team2 = team2;
        this.estimatedTime = estimatedTime;
    }

    public SportMatch(int id, String name, Location location, String sportName, String team1, String team2, int estimatedTime) {
        super(id, name, location);
        this.sportName = sportName;
        this.team1 = team1;
        this.team2 = team2;
        this.estimatedTime = estimatedTime;
    }

    public String getSportName() {
        return sportName;
    }

    public void setSportName(String sportName) {
        this.sportName = sportName;
    }

    public String getTeam1() {
        return team1;
    }

    public void setTeam1(String team1) {
        this.team1 = team1;
    }

    public String getTeam2() {
        return team2;
    }

    public void setTeam2(String team2) {
        this.team2 = team2;
    }

    public int getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(int estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    @Override
    public String toString() {
        return super.toString() + " / " + sportName + " : " + team1 + " vs " + team2 + " / "
                + estimatedTime + " minutes \n";
    }

}
