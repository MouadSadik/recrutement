package main.java.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import main.java.models.Abonnement;
import main.java.models.Entreprise;
import main.java.models.Journal;
import main.java.utils.DatabaseConnection;

public class AbonnementDAO {
    // Create
    public static boolean ajouterAbonnement(Abonnement abonnement) {
    String sql = "INSERT INTO abonnement (codeclient, codejournal, dateexpiration, etat) VALUES (?, ?, ?, ?)";
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, abonnement.getEntreprise().getCodeClient());
        stmt.setInt(2, abonnement.getJournal().getCodeJournal());  // assure-toi que getIdJournal() existe
        stmt.setDate(3, java.sql.Date.valueOf(abonnement.getDateExpiration()));
        stmt.setBoolean(4, abonnement.estActif());

        int rowsInserted = stmt.executeUpdate();
        return rowsInserted > 0;
    } catch (SQLException e) {
        System.err.println("Erreur SQL (ajout abonnement) : " + e.getMessage());
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
                int idAbonnement = rs.getInt("idabonnement");
                int codeClient = rs.getInt("codeclient");
                int codeJournal = rs.getInt("codejournal");
                LocalDate dateExpiration = rs.getDate("dateexpiration").toLocalDate();
                boolean etat = rs.getBoolean("etat");
    
                Entreprise entreprise = EntrepriseDAO.getEntrepriseById(codeClient); // à créer
                Journal journal = JournalDAO.getJournalById(codeJournal);           // à créer
    
                return new Abonnement( idAbonnement, entreprise, journal, null, dateExpiration, etat);
            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL (getAbonnementById) : " + e.getMessage());
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
                int id = rs.getInt("idabonnement");
                int codeClient = rs.getInt("codeclient");
                int codeJournal = rs.getInt("codejournal");
                LocalDate dateExpiration = rs.getDate("dateexpiration").toLocalDate();
                boolean actif = rs.getBoolean("etat");
    
                Entreprise entreprise = EntrepriseDAO.getEntrepriseById(codeClient);
                Journal journal = JournalDAO.getJournalById(codeJournal);
    
                Abonnement abonnement = new Abonnement(id, entreprise, journal, null, dateExpiration, actif);
                abonnements.add(abonnement);
            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL (getAllAbonnements) : " + e.getMessage());
        }
        return abonnements;
    }

    // Update
    public static boolean modifierAbonnement(Abonnement abonnement) {
        String sql = "UPDATE abonnement SET codeclient = ?, codejournal = ?, dateexpiration = ?, etat = ? WHERE idabonnement = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
    
            stmt.setInt(1, abonnement.getEntreprise().getCodeClient());  // Utilisation de l'objet Entreprise
            stmt.setInt(2, abonnement.getJournal().getCodeJournal());      // Utilisation de l'objet Journal
            stmt.setDate(3, java.sql.Date.valueOf(abonnement.getDateExpiration()));  // Conversion LocalDate -> Date SQL
            stmt.setBoolean(4, abonnement.estActif());                   // État actif
            stmt.setInt(5, abonnement.getIdAbonnement());               // L'ID de l'abonnement à modifier
    
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.err.println("Erreur SQL (modification abonnement) : " + e.getMessage());
            return false;
        }
    }
    
    //Delete
    public static boolean supprimerAbonnement(int id) {
        String sql = "DELETE FROM abonnement WHERE idabonnement = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
    
            stmt.setInt(1, id);  // L'ID de l'abonnement à supprimer
    
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            System.err.println("Erreur SQL (suppression abonnement) : " + e.getMessage());
            return false;
        }
    }


    public static void main(String[] args) {
        // Exemple de mise à jour d'un abonnement
        Abonnement abonnement = AbonnementDAO.getAbonnementById(1);
        if (abonnement != null) {
            abonnement.setDateExpiration(LocalDate.now().plusMonths(12));  // Exemple de mise à jour de la date d'expiration
            boolean updated = AbonnementDAO.modifierAbonnement(abonnement);
            System.out.println("Abonnement mis à jour ? " + updated);
        }
    
        // Exemple de suppression d'un abonnement
        boolean deleted = AbonnementDAO.supprimerAbonnement(2);  // Suppression de l'abonnement avec id = 2
        System.out.println("Abonnement supprimé ? " + deleted);
    }
}
