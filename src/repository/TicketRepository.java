package repository;

import config.*;
import model.*;
import java.sql.*;
import java.util.Optional;

public class TicketRepository {
    private ConcertRepository concertRepository = new ConcertRepository();
    private SportMatchRepository sportMatchRepository = new SportMatchRepository();
    private TheaterPlayRepository theaterPlayRepository = new TheaterPlayRepository();

    public void addTicket(Ticket ticket) {
        String sql = "insert into tickets values (?, ?, ?, ?)";
        try (PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sql)) {
            if (getTicket(ticket.getEvent().getId(), ticket.getType()).isEmpty()) {
                statement.setInt(1, ticket.getEvent().getId());
                statement.setDouble(2, ticket.getPrice());
                statement.setString(3, ticket.getType());
                statement.setInt(4, ticket.getNumberOfTickets());
                statement.executeUpdate();
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public Optional<Ticket> getTicket(int eventId, String type) {
        String sql = "select * from tickets where eventId = ? and type = ?";
        try (PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sql)) {
            statement.setInt(1, eventId);
            statement.setString(2, type);
            ResultSet result = statement.executeQuery();
            while(result.next()) {
                int numberOfTickets = result.getInt("numberOfTickets");
                double price = result.getDouble("price");
                Optional<Concert> concert = concertRepository.getConcertById(eventId);
                Optional<SportMatch> sportMatch = sportMatchRepository.getSportMatchById(eventId);
                Optional<TheaterPlay> theaterPlay = theaterPlayRepository.getTheaterById(eventId);
                if (concert.isPresent())
                    return Optional.of(new Ticket(concert.orElse(null), price, type, numberOfTickets));
                if (sportMatch.isPresent())
                    return Optional.of(new Ticket(sportMatch.orElse(null), price, type, numberOfTickets));
                return Optional.of(new Ticket(theaterPlay.orElse(null), price, type, numberOfTickets));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public void updateTicketPrice(Ticket ticket, double price) {
        String sql = "update tickets set price = ? where eventId = ? and type = ? ";
        try (PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sql)) {
            statement.setDouble(1, price);
            statement.setInt(2, ticket.getEvent().getId());
            statement.setString(3,ticket.getType());
            statement.executeUpdate();
        }catch(SQLException e) {
            e.printStackTrace();
        }

    }

    public void decreaseNumberOfTickets(Ticket ticket) {
        String sql = "update tickets set numberOfTickets = ? where eventId = ? and type = ? ";
        try (PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sql)) {
            statement.setDouble(1, ticket.getNumberOfTickets() - 1);
            statement.setInt(2, ticket.getEvent().getId());
            statement.setString(3,ticket.getType());
            statement.executeUpdate();
        }catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeTicket(Ticket ticket) {
        String sql = "delete from tickets where eventId = ? and type = ? ";
        try (PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sql)) {
            statement.setInt(1, ticket.getEvent().getId());
            statement.setString(2,ticket.getType());
            statement.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
}
