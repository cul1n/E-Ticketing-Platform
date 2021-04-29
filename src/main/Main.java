package main;


import model.*;
import service.*;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Platform platform = new Platform();
        UserService userService = UserService.getInstance();
        PlatformService platformService = PlatformService.getInstance();
        Scanner scanner = new Scanner(System.in);
        User currentUser = null;

        userService.Initialization(platform);
        platformService.Initialization(platform);

        while(true) {
            System.out.println("Please choose one: login, register or exit");
            String line = scanner.nextLine();
            switch(line) {
                case "register":
                    System.out.print("Email: ");
                    String email = scanner.nextLine();
                    System.out.print("Username: ");
                    String username = scanner.nextLine();
                    System.out.print("Password: ");
                    String password = scanner.nextLine();
                    userService.register(platform, new User(username, password, email));
                    System.out.println("Successfully created user " + username + "!");
                    platformService.record(platform, new AuditLine(username, "has registered"));
                    break;

                case "login":
                    System.out.print("Username: ");
                    username = scanner.nextLine();
                    System.out.print("Password: ");
                    password = scanner.nextLine();

                    if (userService.findUser(platform, username) != null){
                        if (userService.authentication(platform, username, password)) {
                            System.out.println("Logged in as " + username + "!");
                            currentUser = userService.findUser(platform, username);
                            platformService.record(platform, new AuditLine(username, "has logged in"));
                        }
                        else {
                            System.out.println("Wrong password!");
                        }

                    }
                    else {
                        System.out.println("Wrong username!");
                    }
                    break;

                case "exit":
                    System.out.println("Closing down the app...");
                    System.exit(0);
                    break;
                default: System.out.println("This command doesn't exist.");
            }
            while(currentUser != null) {
                if(!currentUser.isAdmin()) {
                    System.out.println("Please choose one: account info, add funds, " +
                            "owned tickets, buy tickets or log out");
                    line = scanner.nextLine();
                    switch(line) {
                        case "account info":
                            System.out.println(currentUser);
                            break;
                        case "add funds":
                            System.out.println("Your current balance: $" + currentUser.getFunds());
                            System.out.print("Enter sum of money you want to add to your balance: ");
                            double sum = Double.parseDouble(scanner.nextLine());
                            platformService.record(platform, new AuditLine(currentUser.getUsername(),
                                    "has added $" + sum + " to their balance"));
                            currentUser.setFunds(currentUser.getFunds() + sum);
                            System.out.println("Your new balance: $" + currentUser.getFunds());
                            break;
                        case "owned tickets":
                            System.out.println("Your owned tickets: ");
                            platformService.printTicketsUser(platform, currentUser);
                            break;
                        case "buy tickets":
                            System.out.println("List of tickets sorted by price: ");
                            platformService.printTickets(platform);
                            System.out.println("Please choose one: view, filter or buy");
                            String line2 = scanner.nextLine();
                            switch(line2){
                                case "view":
                                    System.out.print("Pick a ticket id to view details of the event: ");
                                    int id = Integer.parseInt(scanner.nextLine());
                                    System.out.println(platform.getTickets().get(id - 1).getEvent());
                                    break;
                                case "buy":
                                    System.out.print("Pick a ticket id to buy or type '-1' to cancel: ");
                                    id = Integer.parseInt(scanner.nextLine());
                                    if (id == -1){
                                        System.out.println("Transaction canceled...");
                                    }
                                    else {
                                        if(currentUser.getFunds() < platform.getTickets().get(id - 1).getPrice()) {
                                            System.out.println("Not enough funds.");
                                            System.out.println("Transaction canceled...");
                                        }
                                        else if(platform.getTickets().get(id - 1).getNumberOfTickets() == 0) {
                                            System.out.println("No tickets left.");
                                            System.out.println("Transaction canceled...");
                                        }
                                        else {
                                            System.out.println("Ticket bought successfully.");
                                            platformService.record(platform, new AuditLine(currentUser.getUsername(),
                                                    "has bought a ticket for " +
                                                            platform.getTickets().get(id - 1).getEvent().getName()));
                                            platformService.buyTicket(platform.getTickets().get(id - 1),currentUser);
                                        }
                                    }
                                    break;
                                case "filter":
                                    System.out.print("Pick a country: ");
                                    String country = scanner.nextLine();
                                    System.out.println("List of tickets from " + country + ":");
                                    platformService.filterTickets(platform, country);
                                    break;
                                default: System.out.println("This command doesn't exist.");
                            }
                            break;
                        case "log out":
                            System.out.println("Goodbye " + currentUser.getUsername() + "!");
                            System.out.println("Logging out...");
                            platformService.record(platform, new AuditLine(currentUser.getUsername(), "has logged out"));
                            currentUser = null;
                            break;
                        default: System.out.println("This command doesn't exist.");
                    }
                }
                else {
                    System.out.println("Please choose one: users, audit, events, tickets or log out");
                    line = scanner.nextLine();
                    switch(line){
                        case "users":
                            System.out.println("List of users: ");
                            userService.printUsers(platform);
                            break;

                        case "audit":
                            System.out.println("Audit: ");
                            platformService.printAudit(platform);
                            break;

                        case "events":
                            System.out.println("Please choose one: view, add");
                            String line2 = scanner.nextLine();
                            switch(line2) {
                                case "view":
                                    System.out.println("List of events:");
                                    platformService.printEvents(platform);
                                    break;
                                case "add":
                                    System.out.println("Please choose a type of event: concert, " +
                                            "sport match, theater play");
                                    String line3 = scanner.nextLine();
                                    System.out.print("Name of the event: ");
                                    String name = scanner.nextLine();
                                    System.out.print("Country: ");
                                    String country = scanner.nextLine();
                                    System.out.print("City: ");
                                    String city = scanner.nextLine();

                                    switch(line3){
                                        case "concert":
                                            System.out.print("Name of the artist: ");
                                            String artistName = scanner.nextLine();
                                            System.out.print("Genre of music: ");
                                            String musicGenre = scanner.nextLine();
                                            Location location = new Location(country, city);
                                            Concert concert = new Concert(name, location, artistName, musicGenre);
                                            platformService.addEvent(platform, concert);
                                            platformService.record(platform, new AuditLine(currentUser.getUsername(), "has added a new concert: " + concert.getName()));
                                            break;
                                        case "sport match":
                                            System.out.print("Name of the sport: ");
                                            String sportName = scanner.nextLine();
                                            System.out.print("First team: ");
                                            String team1 = scanner.nextLine();
                                            System.out.print("Second team: ");
                                            String team2 = scanner.nextLine();
                                            System.out.print("Estimated duration: ");
                                            int estimatedTime = Integer.parseInt(scanner.nextLine());
                                            location = new Location(country, city);
                                            SportMatch sportMatch = new SportMatch(name, location, sportName,
                                                    team1, team2, estimatedTime);
                                            platformService.addEvent(platform, sportMatch);
                                            platformService.record(platform, new AuditLine(currentUser.getUsername(), "has added a new sport match: " + sportMatch.getName()));
                                            break;
                                        case "theater play":
                                            System.out.print("Name of the play: ");
                                            String playName = scanner.nextLine();
                                            System.out.print("Director: ");
                                            String director = scanner.nextLine();
                                            System.out.print("Duration of the play: ");
                                            int time = Integer.parseInt(scanner.nextLine());
                                            location = new Location(country, city);
                                            TheaterPlay theaterPlay = new TheaterPlay(name, location,
                                                    playName, director, time);
                                            platformService.addEvent(platform, theaterPlay);
                                            platformService.record(platform, new AuditLine(currentUser.getUsername(), "has added a new theater play: " + theaterPlay.getName()));
                                            break;
                                        default: System.out.println("This event type doesn't exist.");
                                    }
                                    break;
                                default: System.out.println("This command doesn't exist.");
                            }
                            break;

                        case "tickets":
                            System.out.println("Please choose one: view, add");
                            line2 = scanner.nextLine();
                            switch(line2) {
                                case "view":
                                    System.out.println("List of tickets:");
                                    platformService.printTickets(platform);
                                    break;
                                case "add":
                                    System.out.print("Id of event: ");
                                    int id = Integer.parseInt(scanner.nextLine());
                                    System.out.print("Ticket type: ");
                                    String type = scanner.nextLine();
                                    System.out.print("Price: ");
                                    double price = Double.parseDouble(scanner.nextLine());
                                    System.out.print("Number of tickets available: ");
                                    int numberOfTickets = Integer.parseInt(scanner.nextLine());
                                    Ticket ticket = new Ticket(platform.getEvents().get(id - 1),price,type,numberOfTickets);
                                    platformService.addTicket(platform, ticket);
                                    platformService.record(platform, new AuditLine(currentUser.getUsername(), "has added a new ticket for " + platform.getEvents().get(id - 1).getName()));
                                    break;
                                default: System.out.println("This command doesn't exist.");
                            }
                            break;

                        case "log out":
                            System.out.println("Goodbye " + currentUser.getUsername() + "!");
                            System.out.println("Logging out...");
                            platformService.record(platform, new AuditLine(currentUser.getUsername(), "has logged out"));
                            currentUser = null;
                            break;
                        default: System.out.println("This command doesn't exist.");
                    }

                }
            }
        }

    }
}
