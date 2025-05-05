package main.java.DAO;

import main.java.classes.Demandeur;
import main.java.utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DemandeurDAO {

    public static boolean ajouterDemandeur(Demandeur demandeur) {
        String sql = "INSERT INTO demandeur (codeclient, nom, prenom, nbanneesexperience, salairesouhaite, diplome) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, demandeur.getCodeClient());
            stmt.setString(2, demandeur.getNom());
            stmt.setString(3, demandeur.getPrenom());
            stmt.setInt(4, demandeur.getAnneeExp());
            stmt.setDouble(5, demandeur.getSalaiireSouhaite());
            stmt.setString(6, demandeur.getDiplome());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            System.err.println("Erreur SQL (ajout) : " + e.getMessage());
            return false;
        }
    }

    public static boolean supprimerDemandeur(int codeClient) {
        String sql = "DELETE FROM demandeur WHERE codeclient = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, codeClient);

            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;

        } catch (SQLException e) {
            System.err.println("Erreur SQL (suppression) : " + e.getMessage());
            return false;
        }
    }

    public static boolean modifierDemandeur(Demandeur demandeur) {
        String sql = "UPDATE demandeur SET nom = ?, prenom = ?, nbanneesexperience = ?, salairesouhaite = ?, diplome = ? WHERE codeclient = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, demandeur.getNom());
            stmt.setString(2, demandeur.getPrenom());
            stmt.setInt(3, demandeur.getAnneeExp());
            stmt.setDouble(4, demandeur.getSalaiireSouhaite());
            stmt.setString(5, demandeur.getDiplome());
            stmt.setInt(6, demandeur.getCodeClient());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            System.err.println("Erreur SQL (modification) : " + e.getMessage());
            return false;
        }
    }

    public static Demandeur getDemandeurById(int codeClient) {
        String sql = "SELECT * FROM demandeur WHERE codeclient = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, codeClient);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Demandeur(
                        rs.getInt("codeclient"),
                        "", // adresse ignorée
                        "", // tel ignoré
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        "", // fax ignoré
                        rs.getInt("nbanneesexperience"),
                        rs.getDouble("salairesouhaite"),
                        rs.getString("diplome")
                );
            }

        } catch (SQLException e) {
            System.err.println("Erreur SQL (lecture par id) : " + e.getMessage());
        }

        return null;
    }

    public static List<Demandeur> getAllDemandeurs() {
        List<Demandeur> demandeurs = new ArrayList<>();
        String sql = "SELECT * FROM demandeur";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Demandeur d = new Demandeur(
                        rs.getInt("codeclient"),
                        "", "", // adresse, tel ignorés
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        "", // fax ignoré
                        rs.getInt("nbanneesexperience"),
                        rs.getDouble("salairesouhaite"),
                        rs.getString("diplome")
                );
                demandeurs.add(d);
            }

        } catch (SQLException e) {
            System.err.println("Erreur SQL (lecture tous) : " + e.getMessage());
        }

        return demandeurs;
    }

    // MAIN DE TEST
    public static void main(String[] args) {
        // Ajouter
        Demandeur d1 = new Demandeur(1, "", "", "Ali", "Benali", "", 5, 8000, "Master");
        if (ajouterDemandeur(d1)) {
            System.out.println("Demandeur ajouté.");
        }

        Demandeur d2 = new Demandeur(2, "", "", "Sara", "ElAmrani", "", 3, 7000, "Licence");
        if (ajouterDemandeur(d2)) {
            System.out.println("Demandeur ajouté.");
        }

        // Modifier d2
        d2.setSalaiireSouhaite(7500);
        if (modifierDemandeur(d2)) {
            System.out.println("Demandeur modifié.");
        }

        // Rechercher
        Demandeur found = getDemandeurById(2);
        if (found != null) {
            System.out.println("Demandeur trouvé : " + found.getNom() + " " + found.getPrenom());
        }

        // Afficher tout
        List<Demandeur> liste = getAllDemandeurs();
        System.out.println("Tous les demandeurs :");
        for (Demandeur d : liste) {
            System.out.println(d.getCodeClient() + " | " + d.getNom() + " | " + d.getPrenom() + " | " + d.getAnneeExp() + " ans | " + d.getSalaiireSouhaite() + " MAD | " + d.getDiplome());
        }

        // Supprimer
        if (supprimerDemandeur(1)) {
            System.out.println("Demandeur supprimé.");
        }

    }
}
