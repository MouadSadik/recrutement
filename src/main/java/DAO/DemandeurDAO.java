package main.java.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.java.models.Demandeur;
import main.java.utils.DatabaseConnection;

public class DemandeurDAO {

    // Create
    public static boolean ajouterDemandeur(Demandeur demandeur) {
        String sqlClient = "INSERT INTO client (adresse, telephone) VALUES (?, ?)";
        String sqlDemandeur = "INSERT INTO demandeur (codeclient, nom, prenom, nbanneesexperience, salairesouhaite, diplome) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false); // Début de transaction

            // Insertion dans la table client
            try (PreparedStatement psClient = conn.prepareStatement(sqlClient,
                    PreparedStatement.RETURN_GENERATED_KEYS)) {
                psClient.setString(1, demandeur.getAdresse());
                psClient.setString(2, demandeur.getTelephone());
                psClient.executeUpdate();

                ResultSet generatedKeys = psClient.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int idClient = generatedKeys.getInt(1);
                    demandeur.setCodeClient(idClient); // Mise à jour du codeClient dans l’objet Demandeur
                } else {
                    conn.rollback();
                    throw new SQLException("Échec de la création du client : aucun ID généré.");
                }
            }

            // Insertion dans la table demandeur
            try (PreparedStatement stmt = conn.prepareStatement(sqlDemandeur)) {
                stmt.setInt(1, demandeur.getCodeClient());
                stmt.setString(2, demandeur.getNom());
                stmt.setString(3, demandeur.getPrenom());
                stmt.setInt(4, demandeur.getAnneeExp());
                stmt.setDouble(5, demandeur.getSalaiireSouhaite());
                stmt.setString(6, demandeur.getDiplome());
                int rowsInserted = stmt.executeUpdate();

                if (rowsInserted > 0) {
                    conn.commit(); // Si tout s'est bien passé
                    return true;
                } else {
                    conn.rollback();
                    return false;
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL (ajout demandeur) : " + e.getMessage());
            return false;
        }
    }

    // Read By id
    public static Demandeur getDemandeurById(int id) {
        String sql = "SELECT c.codeclient, c.adresse, c.telephone, d.nom, d.prenom, d.nbanneesexperience, d.salairesouhaite, d.diplome "
                +
                "FROM client c JOIN demandeur d ON c.codeclient = d.codeclient WHERE c.codeclient = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Demandeur(
                        rs.getInt("codeclient"),
                        rs.getString("adresse"),
                        rs.getString("telephone"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getInt("nbanneesexperience"),
                        rs.getDouble("salairesouhaite"),
                        rs.getString("diplome"));
            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL (lecture demandeur par ID) : " + e.getMessage());
        }
        return null;
    }

    // Update
    public static boolean modifierDemandeur(Demandeur demandeur) {
        String sqlClient = "UPDATE client SET adresse = ?, telephone = ? WHERE codeclient = ?";
        String sqlDemandeur = "UPDATE demandeur SET nom = ?, prenom = ?, nbanneesexperience = ?, salairesouhaite = ?, diplome = ? WHERE codeclient = ?";

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false); // Démarrer la transaction

            // Mise à jour dans la table client
            try (PreparedStatement psClient = conn.prepareStatement(sqlClient)) {
                psClient.setString(1, demandeur.getAdresse());
                psClient.setString(2, demandeur.getTelephone());
                psClient.setInt(3, demandeur.getCodeClient());
                int rowsUpdated1 = psClient.executeUpdate();

                // Mise à jour dans la table demandeur
                try (PreparedStatement psDemandeur = conn.prepareStatement(sqlDemandeur)) {
                    psDemandeur.setString(1, demandeur.getNom());
                    psDemandeur.setString(2, demandeur.getPrenom());
                    psDemandeur.setInt(3, demandeur.getAnneeExp());
                    psDemandeur.setDouble(4, demandeur.getSalaiireSouhaite());
                    psDemandeur.setString(5, demandeur.getDiplome());
                    psDemandeur.setInt(6, demandeur.getCodeClient());
                    int rowsUpdated2 = psDemandeur.executeUpdate();

                    if (rowsUpdated1 > 0 && rowsUpdated2 > 0) {
                        conn.commit(); // Tout s'est bien passé
                        return true;
                    } else {
                        conn.rollback();
                        return false;
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL (modification demandeur) : " + e.getMessage());
            return false;
        }
    }

    // Delete
    public static boolean supprimerDemandeur(int codeClient) {
        String sqlDeleteDemandeur = "DELETE FROM demandeur WHERE codeclient = ?";
        String sqlDeleteClient = "DELETE FROM client WHERE codeclient = ?";

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false); // Démarrer la transaction

            // Suppression dans la table demandeur
            try (PreparedStatement psDemandeur = conn.prepareStatement(sqlDeleteDemandeur)) {
                psDemandeur.setInt(1, codeClient);
                int rowsDemandeur = psDemandeur.executeUpdate();

                // Suppression dans la table client
                try (PreparedStatement psClient = conn.prepareStatement(sqlDeleteClient)) {
                    psClient.setInt(1, codeClient);
                    int rowsClient = psClient.executeUpdate();

                    if (rowsDemandeur > 0 && rowsClient > 0) {
                        conn.commit();
                        return true;
                    } else {
                        conn.rollback();
                        return false;
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL (suppression demandeur) : " + e.getMessage());
            return false;
        }
    }

    // Read (tous les demandeurs)
    public static List<Demandeur> getAllDemandeurs() {
        List<Demandeur> demandeurs = new ArrayList<>();
        String sql = "SELECT c.codeclient, c.adresse, c.telephone, d.nom, d.prenom, d.nbanneesexperience, d.salairesouhaite, d.diplome "
                +
                "FROM client c JOIN demandeur d ON c.codeclient = d.codeclient";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Demandeur demandeur = new Demandeur(
                        rs.getInt("codeclient"),
                        rs.getString("adresse"),
                        rs.getString("telephone"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getInt("nbanneesexperience"),
                        rs.getDouble("salairesouhaite"),
                        rs.getString("diplome"));
                demandeurs.add(demandeur);
            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL (lecture de tous les demandeurs) : " + e.getMessage());
        }

        return demandeurs;
    }

    public static Demandeur getDemandeurByNom(String nom) throws SQLException {
    String sql = "SELECT * FROM Demandeur WHERE nom = ?";

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement statement = conn.prepareStatement(sql)) {

        statement.setString(1, nom);

        try (ResultSet rs = statement.executeQuery()) {
            if (rs.next()) {
                return new Demandeur(
                    rs.getInt("codeClient"),
                    rs.getString("adresse"),
                    rs.getString("tel"),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getInt("nbanneesexperience"),
                    rs.getDouble("salairesouhaite"),
                    rs.getString("diplome")
                );
            }
        }
    }
    return null;
}


    public static void main(String[] args) {

        System.out.println("==== TEST DEMANDEUR DAO ====");

        // 🔹 1. Création d'un demandeur
        Demandeur demandeur = new Demandeur(
                0, // codeClient sera généré automatiquement
                "123 Rue Principale",
                "0600000000",
                "Dupont",
                "Jean",
                5, // 5 années d'expérience
                3000.0, // Salaire souhaité
                "Master en Informatique");

        // 🔹 2. Insertion
        boolean ajoutRéussi = DemandeurDAO.ajouterDemandeur(demandeur);
        System.out.println("Ajout réussi ? " + ajoutRéussi);

        // 🔹 3. Affichage
        System.out.println("\nDemandeurs enregistrés :");
        List<Demandeur> demandeurs = DemandeurDAO.getAllDemandeurs();
        for (Demandeur d : demandeurs) {
            System.out.println(d);
        }

        // 🔹 4. Modification
        if (demandeur.getCodeClient() > 0) {
            demandeur.setSalaiireSouhaite(3500.0); // Modifier le salaire souhaité
            boolean modifRéussi = DemandeurDAO.modifierDemandeur(demandeur);
            System.out.println("\nModification réussie ? " + modifRéussi);
        }

        // 🔹 5. Affichage après modification
        System.out.println("\nDemandeurs après modification :");
        demandeurs = DemandeurDAO.getAllDemandeurs();
        for (Demandeur d : demandeurs) {
            System.out.println(d);
        }

        // 🔹 6. Suppression
        if (demandeur.getCodeClient() > 0) {
            boolean suppressionRéussie = DemandeurDAO.supprimerDemandeur(demandeur.getCodeClient());
            System.out.println("\nSuppression réussie ? " + suppressionRéussie);
        }

        // 🔹 7. Affichage final
        System.out.println("\nDemandeurs après suppression :");
        demandeurs = DemandeurDAO.getAllDemandeurs();
        for (Demandeur d : demandeurs) {
            System.out.println(d);
        }

        System.out.println("\n==== FIN TEST ====");
    }

}
