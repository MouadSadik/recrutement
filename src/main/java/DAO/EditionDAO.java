package main.java.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import main.java.models.Edition;
//import main.java.models.Journal;
import main.java.utils.DatabaseConnection;

public class EditionDAO {

    public static boolean ajouterEdition(Edition edition) {
        String sql = "Insert into edition (codejournal, numedition, dateparution) values (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, edition.getCodeJournal());
            statement.setInt(2, edition.getNumEdition());
            statement.setDate(3, java.sql.Date.valueOf(edition.getDateParution()));

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            System.out.println("Erreur SQL (Ajout): " + e.getMessage());
            return false;
        }
    }

    public static boolean supprimerEdition(int codeJournal, int numEdition) {
        String sql = "DELETE FROM edition where codeJournal = ? AND numedition = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, codeJournal);
            statement.setInt(2, numEdition);
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            System.out.println("Erreur SQL (Delete) : " + e.getMessage());
            return false;
        }
    }

    public static boolean modifierEdition(Edition edition) {
        String sql = "UPDATE edition SET dateparution = ? WHERE codejournal = ? AND numedition = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setDate(1, java.sql.Date.valueOf(edition.getDateParution()));
            statement.setInt(2, edition.getCodeJournal());
            statement.setInt(3, edition.getNumEdition());

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.out.println("Erreur SQL (UPDATE) :" + e.getMessage());
            return false;
        }
    }

    public static Edition getEditionById(int codeJournal, int numEdition) {
        String sql = "SELECT * FROM edition WHERE numEdition = ? AND codejournal = ? ";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, numEdition);
            stmt.setInt(2, codeJournal);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                java.sql.Date sqlDate = rs.getDate("dateparution");
                LocalDate dateParution = sqlDate.toLocalDate();

                return new Edition(
                        rs.getInt("numEdition"),
                        rs.getInt("codejournal"),
                        dateParution);

            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL (lecture par id) : " + e.getMessage());
        }
        return null;
    }

    public static List<Edition> getAllEditions() {
        List<Edition> editions = new ArrayList<>();
        String sql = "SELECT * FROM edition";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                java.sql.Date sqlDate = rs.getDate("dateparution");
                LocalDate dateParution = sqlDate.toLocalDate();
                Edition e = new Edition(
                        rs.getInt("numEdition"),
                        rs.getInt("codejournal"),
                        dateParution);

                editions.add(e);
            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL (lecture par id) : " + e.getMessage());
        }
        return editions;
    }

}