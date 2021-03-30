package model;

public class Ticket {
    private Event event;
    private double price;
    private String type;
    private int numberOfTickets;

    public Ticket(Event event, double price, String type, int numberOfTickets) {
        this.event = event;
        this.type = type;
        this.price = price;
        this.numberOfTickets = numberOfTickets;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getNumberOfTickets() {
        return numberOfTickets;
    }

    public void setNumberOfTickets(int numberOfTickets) {
        this.numberOfTickets = numberOfTickets;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString(){
        return "Name: " + event.getName() + " / " + type + " Ticket / Price: $" + price
                + " / Tickets left: " + numberOfTickets + "\n";
    }

}
