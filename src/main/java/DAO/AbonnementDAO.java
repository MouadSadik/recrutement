package main.java.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.java.classes.Entreprise;
import main.java.classes.Journal;
import main.java.classes.OffreEmploi;
import main.java.classes.Abonnement;
import main.java.utils.DatabaseConnection;

public class AbonnementDAO {
    // Create
    public static boolean ajouterAbonnement(Abonnement abonnement) {
        String sql = "INSERT INTO abonnement (codeclient, codejournal, datedebut, dateexpiration, etat) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, abonnement.getCodeClient());
            stmt.setString(2, abonnement.getCodeJournal());
            stmt.setString(3, LocalDate.now().toString());
            stmt.setString(4, abonnement.getDateExpiration());
            stmt.setInt(5, abonnement.estActif());
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            System.err.println("Erreur SQL (ajout) : " + e.getMessage());
            return false;
        }
    }

    // Read By id
    public static Abonnement getAbonnementById(int id) {
        String sql = "SELECT * FROM abonnement WHERE idabonnement = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Abonnement(
                        rs.getInt("idabonnement"),
                        rs.getString("codeclient"),
                        rs.getString("codejournal"),
                        rs.getString("datedebut"),
                        rs.getString("dateexpiration"),
                        rs.getInt("etat"));
            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL (lecture par id) : " + e.getMessage());
        }
        return null;
    }

    // Read (tous les abonnements)
    public static List<Abonnement> getAllAbonnements() {
        List<Abonnement> abonnements = new ArrayList<>();
        String sql = "SELECT * FROM abonnement";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Abonnement a = new Abonnement(
                        rs.getInt("idabonnement"),
                        rs.getString("codeclient"),
                        rs.getString("codejournal"),
                        rs.getString("datedebut"),
                        rs.getString("dateexpiration"),
                        rs.getInt("etat"));
                abonnements.add(a);
            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL (lecture all) : " + e.getMessage());
        }
        return abonnements;
    }

    // Update
    public static boolean modifierAbonnement(Abonnement abonnement) {
        String sql = "UPDATE abonnement SET codeclient = ?, codeJournal = ?, datedebut = ?, dateexpiration = ?, etat = ? WHERE idabonnement = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, abonnement.getCodeClient());
            stmt.setString(2, abonnement.getCodeJournal());
            stmt.setString(3, LocalDate.now().toString());
            stmt.setString(4, abonnement.getDateExpiration());
            stmt.setInt(5, abonnement.estActif());
            stmt.setInt(6, abonnement.getIdAbonnement());
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.err.println("Erreur SQL (modification) : " + e.getMessage());
            return false;
        }
    }

    // Delete
    public static boolean supprimerAbonnement(int id) {
        String sql = "DELETE FROM abonnement WHERE idabonnement = ?";
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

        //Entreprise entreprise = new Entreprise(1, "Rue X", "0611223344", "Ma Société", "Informatique");
        // Journal journal = new Journal(1, "Le Monde", "Hebdomadaire", "Français", 2);

        Abonnement nouvelleAbonnement = new Abonnement(getEntrepriseById(1), getJournalById(1), LocalDate.now().toString(), "2026-03-03", 1);

        if (ajouterAbonnement(nouvelleAbonnement)) {
            System.out.println("Abonnement ajoutee");
        }

        Abonnement abonnement1 = getAbonnementById(1);
        if (abonnement1 != null) {
            System.out.println("abonnement trouvee : " + abonnement1);
        }

        List<Abonnement> allAbonnements = getAllAbonnements();
        System.out.println("Toutes les Abonnements :");
        for (Abonnement a : allAbonnements) {
            System.out.println(a);
        }

        Abonnement abonnement2 = getAbonnementById(2);
        if (abonnement2 != null) {
            abonnement2.setCodeJournal("Francais");
            if (modifierAbonnement(abonnement2)) {
                System.out.println("abonnement modifiee");
            }
        }

        if (supprimerAbonnement(2)) {
            System.out.println("Abonnement Suprimee");
        }
    }
}
