package service;

import model.*;

import java.util.Arrays;

public class PlatformService {

    public void addEvent(Platform platform, Event event){
        platform.getEvents()[getNumberOfEvents(platform)] = event;
    }

    public void addTicket(Platform platform, Ticket ticket){
        platform.getTickets()[getNumberOfTickets(platform)] = ticket;
    }

    private int getNumberOfEvents(Platform platform) {
        int numberOfEvents = 0;
        for (Event e : platform.getEvents()) {
            if(e != null){
                numberOfEvents++;
            }
        }
        return numberOfEvents;
    }

    private int getNumberOfTickets(Platform platform) {
        int numberOfTickets = 0;
        for (Ticket t : platform.getTickets()) {
            if(t != null){
                numberOfTickets++;
            }
        }
        return numberOfTickets;
    }


    public void printEvents(Platform platform) {
        int index = 1;
        for (Event e : platform.getEvents()) {
            if (e != null) {
                System.out.print(index + ") ");
                System.out.println(e);
                index++;
            }
        }
    }

    public void printTickets(Platform platform) {
        Arrays.sort(platform.getTickets(), (t1, t2) -> {
            if (t1 == null && t2 != null) {
                return 1;
            }
            else if (t1 != null && t2 == null){
                return -1;
            }
            else if (t1 == null && t2 == null) {
                return 0;
            }
            else if (t1.getPrice() > t2.getPrice()){
                return 1;
            }
            else if (t1.getPrice() < t2.getPrice()) {
                return -1;
            }
            else {
                return 0;
            }
        } );

        int index = 1;
        for (Ticket t : platform.getTickets()) {
            if (t != null) {
                System.out.print(index + ") ");
                System.out.println(t);
                index++;
            }
        }
    }

    public void filterTickets(Platform platform, String country) {
        int index = 1;
        for (Ticket t : platform.getTickets()) {
            if (t != null && t.getEvent().getLocation().getCountry().equals(country)) {
                System.out.print(index + ") ");
                System.out.println(t);
                index++;
            }
        }
    }



    public void buyTicket(Ticket ticket, User user){
        user.getTickets()[getNumberOfTicketsUser(user)] = ticket;
        ticket.setNumberOfTickets(ticket.getNumberOfTickets() - 1);
        user.setFunds(user.getFunds() - ticket.getPrice());
    }

    private int getNumberOfTicketsUser(User user) {
        int numberOfTickets = 0;
        for (Ticket t : user.getTickets()) {
            if(t != null){
                numberOfTickets++;
            }
        }
        return numberOfTickets;
    }

    public void printTicketsUser(Platform platform, User u) {
        int index = 1;
        for (Ticket t : u.getTickets()) {
            if (t != null) {
                System.out.print(index + ") ");
                System.out.println(t);
                index++;
            }
        }
    }

    public void record(Platform platform, AuditLine auditLine) {
        platform.getAudit()[getNumberOfLines(platform)] = auditLine;
    }


    private int getNumberOfLines(Platform platform) {
        int numberOfLines = 0;
        for (AuditLine a : platform.getAudit()) {
            if(a != null){
                numberOfLines++;
            }
        }
        return numberOfLines;
    }

    public void printAudit(Platform platform) {
        int index = 1;
        for (AuditLine a : platform.getAudit()) {
            if (a != null) {
                System.out.print(index + ") ");
                System.out.println(a);
                index++;
            }
        }
    }
}
