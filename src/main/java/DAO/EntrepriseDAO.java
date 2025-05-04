package main.java.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.java.classes.Entreprise;
import main.java.utils.DatabaseConnection;

public class EntrepriseDAO {
    // Create
    public static boolean ajouterEntreprise(Entreprise entreprise) {
        String sqlClient = "INSERT INTO client (adresse, telephone) VALUES (?, ?)";
        String sqlEntreprise = "INSERT INTO entreprise (codeclient, raisonsociale, descriptionactivites) VALUES (?, ?, ?)";
    
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false); // Début de transaction
    
            // Insertion dans la table client
            try (PreparedStatement psClient = conn.prepareStatement(sqlClient, PreparedStatement.RETURN_GENERATED_KEYS)) {
                psClient.setString(1, entreprise.getAdresse());
                psClient.setString(2, entreprise.getTelephone());
                psClient.executeUpdate();
    
                ResultSet generatedKeys = psClient.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int idClient = generatedKeys.getInt(1);
                    entreprise.setCodeClient(idClient); // mise à jour dans l’objet
                } else {
                    conn.rollback();
                    throw new SQLException("Échec de la création du client : aucun ID généré.");
                }
            }
    
            // Insertion dans la table entreprise
            try (PreparedStatement stmt = conn.prepareStatement(sqlEntreprise)) {
                stmt.setInt(1, entreprise.getCodeClient());
                stmt.setString(2, entreprise.getRaisonSociale());
                stmt.setString(3, entreprise.getDescriptionActivite());
                int rowsInserted = stmt.executeUpdate();
    
                if (rowsInserted > 0) {
                    conn.commit(); // Tout s'est bien passé
                    return true;
                } else {
                    conn.rollback();
                    return false;
                }
            }
    
        } catch (SQLException e) {
            System.err.println("Erreur SQL (ajout entreprise) : " + e.getMessage());
            return false;
        }
    }

    // Read By id
    public static Entreprise getEntrepriseById(int id) {
        String sql = "SELECT c.codeclient, c.adresse, c.telephone, e.raisonsociale, e.descriptionactivites " +
                     "FROM client c JOIN entreprise e ON c.codeclient = e.codeclient WHERE c.codeclient = ?";
    
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
    
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
    
            if (rs.next()) {
                return new Entreprise(
                    rs.getInt("codeclient"),
                    rs.getString("adresse"),
                    rs.getString("telephone"),
                    rs.getString("raisonsociale"),
                    rs.getString("descriptionactivites")
                );
            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL (lecture entreprise par ID) : " + e.getMessage());
        }
        return null;
    }

    // Read (tous les entreprises)
    public static List<Entreprise> getAllEntreprises() {
        List<Entreprise> entreprises = new ArrayList<>();
        String sql = "SELECT c.codeclient, c.adresse, c.telephone, e.raisonsociale, e.descriptionactivites FROM client c JOIN entreprise e ON c.codeclient = e.codeclient";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Entreprise e = new Entreprise(
                        rs.getInt("codeclient"),
                        rs.getString("adresse"),
                        rs.getString("telephone"),
                        rs.getString("raisonsociale"),
                        rs.getString("descriptionactivites"));
                entreprises.add(e);
            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL (lecture all) : " + e.getMessage());
        }
        return entreprises;
    }

    // Update
    public static boolean modifierEntreprise(Entreprise entreprise) {
        String sqlClient = "UPDATE client SET adresse = ?, telephone = ? WHERE codeclient = ?";
        String sqlEntreprise = "UPDATE entreprise SET raisonsociale = ?, descriptionactivites = ? WHERE codeclient = ?";
    
        Connection conn = null;
        PreparedStatement psClient = null;
        PreparedStatement psEntreprise = null;
    
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);
    
            psClient = conn.prepareStatement(sqlClient);
            psClient.setString(1, entreprise.getAdresse());
            psClient.setString(2, entreprise.getTelephone());
            psClient.setInt(3, entreprise.getCodeClient());
            int rowsUpdated1 = psClient.executeUpdate();
    
            psEntreprise = conn.prepareStatement(sqlEntreprise);
            psEntreprise.setString(1, entreprise.getRaisonSociale());
            psEntreprise.setString(2, entreprise.getDescriptionActivite());
            psEntreprise.setInt(3, entreprise.getCodeClient());
            int rowsUpdated2 = psEntreprise.executeUpdate();
    
            if (rowsUpdated1 > 0 && rowsUpdated2 > 0) {
                conn.commit();
                return true;
            } else {
                conn.rollback();
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL (modification entreprise) : " + e.getMessage());
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return false;
        } finally {
            try { if (psClient != null) psClient.close(); } catch (Exception e) {}
            try { if (psEntreprise != null) psEntreprise.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }
    }

    // Delete
    public static boolean supprimerEntreprise(int codeClient) {
        String sqlDeleteEntreprise = "DELETE FROM entreprise WHERE codeclient = ?";
        String sqlDeleteClient = "DELETE FROM client WHERE codeclient = ?";

        Connection conn = null;
        PreparedStatement psEntreprise = null;
        PreparedStatement psClient = null;

        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false); // Démarrer une transaction

            // Étape 1 : Supprimer dans la table entreprise
            psEntreprise = conn.prepareStatement(sqlDeleteEntreprise);
            psEntreprise.setInt(1, codeClient);
            int rowsEntreprise = psEntreprise.executeUpdate();

            // Étape 2 : Supprimer dans la table client
            psClient = conn.prepareStatement(sqlDeleteClient);
            psClient.setInt(1, codeClient);
            int rowsClient = psClient.executeUpdate();

            if (rowsEntreprise > 0 && rowsClient > 0) {
                conn.commit();
                return true;
            } else {
                conn.rollback();
                return false;
            }

        } catch (SQLException ex) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
            System.err.println("Erreur SQL (suppression) : " + ex.getMessage());
            return false;

        } finally {
            try { if (psEntreprise != null) psEntreprise.close(); } catch (Exception e) {}
            try { if (psClient != null) psClient.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }
    }


    public static void main(String[] args) {

        System.out.println("==== TEST ENTREPRISE DAO ====");

        // 🔹 1. Création d'une entreprise
        Entreprise e = new Entreprise("123 Rue de l'Inno", "0123456789", "TechCorp", "Solutions numériques");

        // 🔹 2. Insertion
        boolean ajout = EntrepriseDAO.ajouterEntreprise(e);
        System.out.println("Ajout réussi ? " + ajout);

        // 🔹 3. Affichage
        System.out.println("\nEntreprises enregistrées :");
        List<Entreprise> entreprises = EntrepriseDAO.getAllEntreprises();
        for (Entreprise ent : entreprises) {
            System.out.println(ent);
        }

        // 🔹 4. Modification
        e.setAdresse("456 Avenue du Numérique");
        e.setTelephone("0987654321");
        e.setRaisonSociale("TechCorp International");
        e.setDescriptionActivite("Informatique & IA");

        boolean modif = EntrepriseDAO.modifierEntreprise(e);
        System.out.println("\nModification réussie ? " + modif);

        // 🔹 5. Affichage après modification
        System.out.println("\nEntreprises après modification :");
        entreprises = EntrepriseDAO.getAllEntreprises();
        for (Entreprise ent : entreprises) {
            System.out.println(ent);
        }

        // 🔹 6. Suppression
        boolean suppr = EntrepriseDAO.supprimerEntreprise(e.getCodeClient());
        System.out.println("\nSuppression réussie ? " + suppr);

        // 🔹 7. Affichage final
        System.out.println("\nEntreprises après suppression :");
        entreprises = EntrepriseDAO.getAllEntreprises();
        for (Entreprise ent : entreprises) {
            System.out.println(ent);
        }

        System.out.println("\n==== FIN TEST ====");
    }
}
