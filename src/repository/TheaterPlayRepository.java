package repository;

import config.*;
import model.*;
import java.sql.*;
import java.util.Optional;

public class TheaterPlayRepository {
    public void addTheaterPlay(TheaterPlay theaterPlay) {
        String sql = "insert into theaterplays values (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sql)) {
            if (getTheaterById(theaterPlay.getId()).isEmpty()) {
                statement.setInt(1, theaterPlay.getId());
                statement.setString(2, theaterPlay.getName());
                statement.setString(3, theaterPlay.getLocation().getCountry());
                statement.setString(4, theaterPlay.getLocation().getCity());
                statement.setString(5, theaterPlay.getPlayName());
                statement.setString(6, theaterPlay.getDirector());
                statement.setInt(7, theaterPlay.getTime());
                statement.executeUpdate();
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public Optional<TheaterPlay> getTheaterById(int id) {
        String sql = "select * from theaterplays where id = ?";
        try (PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            while(result.next()) {
                int theaterPlayId = result.getInt("id");
                String name = result.getString("name");
                String country = result.getString("country");
                String city = result.getString("city");
                String playName = result.getString("playName");
                String director = result.getString("director");
                int time = result.getInt("time");
                return Optional.of(new TheaterPlay(theaterPlayId, name, new Location(country, city), playName, director, time));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public void updateTheaterPlayName(TheaterPlay theaterPlay, String playName) {
        String sql = "update theaterplays set playname = ? where id = ?";
        try (PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sql)) {
            statement.setString(1, playName);
            statement.setInt(2, theaterPlay.getId());
            statement.executeUpdate();
        }catch(SQLException e) {
            e.printStackTrace();
        }

    }

    public void removeTheaterPlay(TheaterPlay theaterPlay) {
        String sql = "delete from theaterplays where id = ?";
        try (PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sql)) {
            statement.setInt(1, theaterPlay.getId());
            statement.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
}
