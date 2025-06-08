package main.java.DAO;

import main.java.models.Abonnement;
import main.java.utils.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AbonnementDAO {

    // Ajouter un abonnement
    public static boolean ajouterAbonnement(int codeClient, int codeJournal, int nbrMois) {
        String sql = "INSERT INTO abonnement (codeclient, codejournal, datedebut, dateexpiration, etat) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            LocalDate dateDebut = LocalDate.now();
            LocalDate dateExpiration = dateDebut.plusMonths(nbrMois);

            stmt.setInt(1, codeClient);
            stmt.setInt(2, codeJournal);
            stmt.setDate(3, Date.valueOf(dateDebut));
            stmt.setDate(4, Date.valueOf(dateExpiration));
            stmt.setBoolean(5, true);

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            System.err.println("Erreur SQL (ajout abonnement) : " + e.getMessage());
            return false;
        }
    }

    // Lire un abonnement par ID
    public static Abonnement getAbonnementById(int idAbonnement) {
        String sql = "SELECT * FROM abonnement WHERE idabonnement = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idAbonnement);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Abonnement(
                        rs.getInt("idabonnement"),
                        rs.getInt("codeclient"),
                        rs.getInt("codejournal"),
                        rs.getDate("datedebut").toLocalDate(),
                        rs.getDate("dateexpiration").toLocalDate(),
                        rs.getBoolean("etat"));
            }

        } catch (SQLException e) {
            System.err.println("Erreur SQL (lecture abonnement par ID) : " + e.getMessage());
        }
        return null;
    }

    // Vérifie si un client est abonné à un journal
    public static Abonnement getAbonnementActif(int codeClient, int codeJournal) {
        String sql = "SELECT * FROM abonnement WHERE codeclient = ? AND codejournal = ? AND etat = true AND dateexpiration > CURRENT_DATE";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, codeClient);
            stmt.setInt(2, codeJournal);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Abonnement(
                        rs.getInt("idabonnement"),
                        rs.getInt("codeclient"),
                        rs.getInt("codejournal"),
                        rs.getDate("datedebut").toLocalDate(),
                        rs.getDate("dateexpiration").toLocalDate(),
                        rs.getBoolean("etat"));
            }

        } catch (SQLException e) {
            System.err.println("Erreur SQL (recherche abonnement actif) : " + e.getMessage());
        }
        return null;
    }

    // Liste de tous les abonnements
    public static List<Abonnement> getAllAbonnements() {
        List<Abonnement> abonnements = new ArrayList<>();
        String sql = "SELECT * FROM abonnement";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Abonnement ab = new Abonnement(
                        rs.getInt("idabonnement"),
                        rs.getInt("codeclient"),
                        rs.getInt("codejournal"),
                        rs.getDate("datedebut").toLocalDate(),
                        rs.getDate("dateexpiration").toLocalDate(),
                        rs.getBoolean("etat"));
                abonnements.add(ab);
            }

        } catch (SQLException e) {
            System.err.println("Erreur SQL (lecture abonnements) : " + e.getMessage());
        }
        return abonnements;
    }

    // Supprimer un abonnement
    public static boolean supprimerAbonnement(int idAbonnement) {
        String sql = "DELETE FROM abonnement WHERE idabonnement = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idAbonnement);
            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            System.err.println("Erreur SQL (suppression abonnement) : " + e.getMessage());
            return false;
        }
    }

    // Désactiver un abonnement (mettre état à false)
    public static boolean desactiverAbonnement(int idAbonnement) {
        String sql = "UPDATE abonnement SET etat = false WHERE idabonnement = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idAbonnement);
            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            System.err.println("Erreur SQL (désactivation abonnement) : " + e.getMessage());
            return false;
        }
    }

    public static List<Abonnement> getAbonnementsParClient(int codeClient) {
        List<Abonnement> abonnements = new ArrayList<>();
        String sql = "SELECT * FROM abonnement WHERE codeclient = ? AND etat = true";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, codeClient);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                abonnements.add(new Abonnement(
                        rs.getInt("idabonnement"),
                        rs.getInt("codeclient"),
                        rs.getInt("codejournal"),
                        rs.getDate("datedebut").toLocalDate(),
                        rs.getDate("dateexpiration").toLocalDate(),
                        rs.getBoolean("etat")));
            }

        } catch (SQLException e) {
            System.err.println("Erreur SQL (abonnement par client) : " + e.getMessage());
        }

        return abonnements;
    }

    public static List<Object[]> getAbonnementsActifsParClient(int codeClient) {
        List<Object[]> resultats = new ArrayList<>();
        String sql = """
                    SELECT a.idabonnement, j.codejournal, j.nom, a.datedebut, a.dateexpiration
                    FROM abonnement a
                    JOIN journal j ON a.codejournal = j.codejournal
                    WHERE a.codeclient = ? AND a.etat = true AND a.dateexpiration > CURRENT_DATE
                """;

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, codeClient);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Object[] row = new Object[] {
                        rs.getInt("idabonnement"),
                        rs.getInt("codejournal"),
                        rs.getString("nom"),
                        rs.getDate("datedebut").toLocalDate(),
                        rs.getDate("dateexpiration").toLocalDate()
                };
                resultats.add(row);
            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL (abonnements actifs) : " + e.getMessage());
        }

        return resultats;
    }

}
