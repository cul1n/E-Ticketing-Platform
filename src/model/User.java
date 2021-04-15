package model;

public class User {
    private int id;
    private String username;
    private String password;
    private String email;
    private double funds;
    private boolean isAdmin;
    private Ticket[] tickets = new Ticket[100];
    static int numberOfUsers;

    public User(String username, String password, String email) {
        this.id = numberOfUsers;
        this.username = username;
        this.password = password;
        this.email = email;
        this.isAdmin = false;
        numberOfUsers++;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getFunds() {
        return funds;
    }

    public void setFunds(double funds) {
        this.funds = funds;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public Ticket[] getTickets() {
        return tickets;
    }

    public void setTickets(Ticket[] tickets) {
        this.tickets = tickets;
    }

    @Override
    public String toString(){
        return "Username: " + username + " / Password: " + password + " / Email: "
                + email + " / Balance: $" + funds + "\n";
    }
}
