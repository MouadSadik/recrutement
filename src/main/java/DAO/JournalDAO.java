package main.java.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//import main.java.models.CategorieJournal;
import main.java.models.Journal;
import main.java.utils.DatabaseConnection;

public class JournalDAO {
    // Create
    public static boolean ajouterJournal(Journal journal) {
        String sql = "INSERT INTO journal (nom, periodicite, langue, idcategorie) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, journal.getNomJournal());
            stmt.setString(2, journal.getPeriodicite());
            stmt.setString(3, journal.getLangue());
            stmt.setInt(4, journal.getIdCategorie());
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            System.err.println("Erreur SQL (ajout) : " + e.getMessage());
            return false;
        }
    }

    // Read By id
    public static Journal getJournalById(int id) {
        String sql = "SELECT * FROM journal WHERE codejournal = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Journal(
                        rs.getInt("codejournal"),
                        rs.getString("nom"),
                        rs.getString("periodicite"),
                        rs.getString("langue"),
                        rs.getInt("idcategorie"));
            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL (lecture par id) : " + e.getMessage());
        }
        return null;
    }

    // Read (all journals)
    public static List<Journal> getAllJournals() {
        List<Journal> journals = new ArrayList<>();
        String sql = "SELECT * FROM journal";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Journal j = new Journal(
                        rs.getInt("codejournal"),
                        rs.getString("nom"),
                        rs.getString("periodicite"),
                        rs.getString("langue"),
                        rs.getInt("idcategorie"));
                journals.add(j);
            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL (lecture all) : " + e.getMessage());
        }
        return journals;
    }

    // Update
    public static boolean modifierJournal(Journal journal) {
        String sql = "UPDATE journal SET nom = ?, periodicite = ?, langue = ?, idcategorie = ? WHERE codejournal = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, journal.getNomJournal());
            stmt.setString(2, journal.getPeriodicite());
            stmt.setString(3, journal.getLangue());
            stmt.setInt(4, journal.getIdCategorie());
            stmt.setInt(5, journal.getCodeJournal());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.err.println("Erreur SQL (modification) : " + e.getMessage());
            return false;
        }
    }

    // Delete
    public static boolean supprimerJournal(int id) {
        String sql = "DELETE FROM journal WHERE codejournal = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            System.err.println("Erreur SQL (suppression) : " + e.getMessage());
            return false;
        }
    }

    public static void main(String[] args) {

        Journal nouvelleJournal = new Journal(1, "MonJournal", "Period", "English", 2);
        if (ajouterJournal(nouvelleJournal)) {
            System.out.println("Journal ajoutee");
        }

        Journal journal1 = getJournalById(1);
        if (journal1 != null) {
            System.out.println("Categorie trouvee : " + journal1);
        }

        List<Journal> allJournals = getAllJournals();
        System.out.println("Toutes les categories :");
        for (Journal j : allJournals) {
            System.out.println(j);
        }

        Journal journal2 = getJournalById(2);
        if (journal2 != null) {
            journal2.setLangue("Francais");
            journal2.setPeriodicite("Periodicite 2");
            if (modifierJournal(journal2)) {
                System.out.println("Journal modifiee");
            }
        }

        if (supprimerJournal(2)) {
            System.out.println("Journal Suprimee");
        }
    }
}
