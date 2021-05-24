package service;

import model.*;
import repository.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class PlatformService {

    private ConcertRepository concertRepository = new ConcertRepository();
    private SportMatchRepository sportMatchRepository = new SportMatchRepository();
    private TheaterPlayRepository theaterPlayRepository = new TheaterPlayRepository();
    private TicketRepository ticketRepository = new TicketRepository();
    private ReaderService readerService = ReaderService.getInstance();
    private WriterService writerSercice = WriterService.getInstance();
    private static PlatformService INSTANCE;
    private PlatformService() {}

    public static PlatformService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PlatformService();
        }
        return INSTANCE;
    }

    public void Initialization(Platform platform) {
        String fileText = readerService.readFile("src/csvfiles/Events.csv");
        String[] eventsText = fileText.split("\n");
        for(String eventText : eventsText) {
            String[] eventComponents = eventText.split(",");
            Location location = new Location(eventComponents[2], eventComponents[3]);
            switch (eventComponents[4]) {
                case "concert":
                    Concert concert = new Concert(eventComponents[1], location, eventComponents[5], eventComponents[6]);
                    addEvent(platform, concert, "Concert");
                    break;
                case "sport match":
                    SportMatch sportMatch = new SportMatch(eventComponents[1], location, eventComponents[5], eventComponents[6], eventComponents[7], Integer.parseInt(eventComponents[8]));
                    addEvent(platform, sportMatch, "Sport Match");
                    break;
                case "theater play":
                    TheaterPlay theaterPlay = new TheaterPlay(eventComponents[1], location, eventComponents[5], eventComponents[6], Integer.parseInt(eventComponents[7]));
                    addEvent(platform, theaterPlay, "Theater Play");
                    break;
                default:
                    System.out.println("Event type not defined.");
                    break;
            }
        }

        fileText = readerService.readFile("src/csvfiles/Tickets.csv");
        String[] ticketsText = fileText.split("\n");
        for(String ticketText : ticketsText) {
            String[] ticketComponents = ticketText.split(",");
            addTicket(platform, new Ticket(platform.getEvents().get(Integer.parseInt(ticketComponents[0])),
                    Double.parseDouble(ticketComponents[1]),
                    ticketComponents[2],
                    Integer.parseInt(ticketComponents[3])));

        }


    }

    public void addEvent(Platform platform, Event event, String type){
        platform.getEvents().add(event);
        if (type == "Concert")
            concertRepository.addConcert((Concert) event);
        else if (type == "Sport Match")
            sportMatchRepository.addSportMatch((SportMatch) event);
        else if (type == "Theater Play")
            theaterPlayRepository.addTheaterPlay((TheaterPlay) event);

    }

    public void addTicket(Platform platform, Ticket ticket){
        platform.getTickets().add(ticket);
        ticketRepository.addTicket(ticket);
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
        Collections.sort(platform.getTickets(), (t1, t2) -> {
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
        user.getTickets().add(ticket);
        ticketRepository.decreaseNumberOfTickets(ticket);
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

    public void printTicketsUser(Platform platform, User user) {
        int index = 1;
        for (Ticket t : user.getTickets()) {
            if (t != null) {
                System.out.print(index + ") ");
                System.out.println(t);
                index++;
            }
        }
    }

    public void record(Platform platform, AuditLine auditLine) {
        String path = "src/csvfiles/Audit.csv";
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        platform.getAudit().add(auditLine);
        writerSercice.writeInFile(path, auditLine + "," + timeStamp);

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
        String fileText = readerService.readFile("src/csvfiles/Audit.csv");
        String[] auditText = fileText.split("\n");
        for (String auditLine : auditText) {
            if (auditLine != null) {
                System.out.print(index + ") ");
                System.out.println(auditLine);
                index++;
            }
        }
    }
}
