package repository;

import config.*;
import model.*;
import java.sql.*;
import java.util.Optional;


public class ConcertRepository {

    public void addConcert(Concert concert) {
        String sql = "insert into concerts values (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sql)) {
            if (getConcertById(concert.getId()).isEmpty()) {
                statement.setInt(1, concert.getId());
                statement.setString(2, concert.getName());
                statement.setString(3, concert.getLocation().getCountry());
                statement.setString(4, concert.getLocation().getCity());
                statement.setString(5, concert.getArtistName());
                statement.setString(6, concert.getMusicGenre());
                statement.executeUpdate();
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public Optional<Concert> getConcertById(int id) {
        String sql = "select * from concerts where id = ?";
        try (PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            while(result.next()) {
                int concertId = result.getInt("id");
                String name = result.getString("name");
                String country = result.getString("country");
                String city = result.getString("city");
                String artistName = result.getString("artistName");
                String musicGenre = result.getString("musicGenre");
                return Optional.of(new Concert(concertId, name, new Location(country, city),artistName, musicGenre ));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public void updateConcertArtistName(Concert concert, String name) {
        String sql = "update concerts set artistName = ? where id = ?";
        try (PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setInt(2, concert.getId());
            statement.executeUpdate();
        }catch(SQLException e) {
            e.printStackTrace();
        }

    }

    public void removeConcert(Concert concert) {
        String sql = "delete from concerts where id = ?";
        try (PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sql)) {
            statement.setInt(1, concert.getId());
            statement.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
}
