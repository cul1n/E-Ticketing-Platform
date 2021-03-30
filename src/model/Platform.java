package model;

public class Platform {
    private User[] users = new User[100];
    private Event[] events = new Event[100];
    private Ticket[] tickets = new Ticket[100];
    private AuditLine[] audit = new AuditLine[200];

    public User[] getUsers() {
        return users;
    }

    public Event[] getEvents() {
        return events;
    }

    public Ticket[] getTickets() {
        return tickets;
    }

    public AuditLine[] getAudit() {
        return audit;
    }

}
