package repository;

import config.*;
import model.*;
import java.sql.*;
import java.util.Optional;

public class SportMatchRepository {
    public void addSportMatch(SportMatch sportMatch) {
        String sql = "insert into sportmatches values (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sql)) {
            if (getSportMatchById(sportMatch.getId()).isEmpty()) {
                statement.setInt(1, sportMatch.getId());
                statement.setString(2, sportMatch.getName());
                statement.setString(3, sportMatch.getLocation().getCountry());
                statement.setString(4, sportMatch.getLocation().getCity());
                statement.setString(5, sportMatch.getSportName());
                statement.setString(6, sportMatch.getTeam1());
                statement.setString(7, sportMatch.getTeam2());
                statement.setInt(8, sportMatch.getEstimatedTime());
                statement.executeUpdate();
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public Optional<SportMatch> getSportMatchById(int id) {
        String sql = "select * from sportmatches where id = ?";
        try (PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            while(result.next()) {
                int sportMatchId = result.getInt("id");
                String name = result.getString("name");
                String country = result.getString("country");
                String city = result.getString("city");
                String sportName = result.getString("sportName");
                String team1 = result.getString("team1");
                String team2 = result.getString("team2");
                int estimatedTime = result.getInt("estimatedTime");
                return Optional.of(new SportMatch(sportMatchId, name, new Location(country, city), sportName, team1, team2, estimatedTime));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public void updateSportMatchName(SportMatch sportMatch, String name) {
        String sql = "update sportmatches set sportName = ? where id = ?";
        try (PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setInt(2, sportMatch.getId());
            statement.executeUpdate();
        }catch(SQLException e) {
            e.printStackTrace();
        }

    }

    public void removeSportMatch(SportMatch sportMatch) {
        String sql = "delete from sportmatches where id = ?";
        try (PreparedStatement statement = DatabaseConnection.getInstance().prepareStatement(sql)) {
            statement.setInt(1, sportMatch.getId());
            statement.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
}
