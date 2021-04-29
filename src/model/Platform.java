package model;

import java.util.ArrayList;
import java.util.List;

public class Platform {
    private List<User> users = new ArrayList<User>();
    private List<Event> events = new ArrayList<Event>();
    private List<Ticket> tickets = new ArrayList<Ticket>();
    private List<AuditLine> audit = new ArrayList<AuditLine>();

    public List<User> getUsers() {
        return users;
    }

    public List<Event> getEvents() {
        return events;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public List<AuditLine> getAudit() {
        return audit;
    }

}
