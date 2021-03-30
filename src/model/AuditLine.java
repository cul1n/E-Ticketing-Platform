package model;

public class AuditLine {
    private final String username;
    private final String action;

    public AuditLine(String username, String action) {
        this.username = username;
        this.action = action;
    }

    public String getUsername() {
        return username;
    }

    public String getAction() {
        return action;
    }

    @Override
    public String toString(){
        return username + " " + action + ".";
    }
}
