package main.java.DAO;

import main.java.models.Demandeur;
import main.java.models.Edition;
import main.java.models.OffreEmploi;
import main.java.models.Postulation;
import main.java.utils.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PostulationDAO {

    // Ajouter une postulation

    public static void ajouterPostulation(int codeDemandeur, int codeJournal, int numEdition, int numOffre)
            throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);

            try {
                // 1. Charger l'offre
                OffreEmploi offre = OffreEmploiDAO.getOffreEmploiById(numOffre);
                if (offre == null)
                    throw new SQLException("Offre introuvable.");

                // 2. Vérifier que l'offre est ACTIVE
                if (offre.getEtat() != OffreEmploi.EtatOffre.ACTIVE) {
                    throw new SQLException("Offre non active.");
                }

                // 3. Charger le demandeur
                Demandeur demandeur = DemandeurDAO.getDemandeurById(codeDemandeur);
                if (demandeur == null)
                    throw new SQLException("Demandeur introuvable.");

                // 4. Vérifier l'expérience
                if (demandeur.getAnneeExp() < offre.getNbAnneeExperienceDemandee()) {
                    throw new SQLException("Expérience insuffisante pour postuler à cette offre.");
                }

                // 5. Compter le nombre de postulations actuelles
                String countSql = "SELECT COUNT(*) FROM Postulation WHERE numOffre = ?";
                int nbPostulations = 0;

                try (PreparedStatement countStmt = conn.prepareStatement(countSql)) {
                    countStmt.setInt(1, numOffre);
                    try (ResultSet rs = countStmt.executeQuery()) {
                        if (rs.next())
                            nbPostulations = rs.getInt(1);
                    }
                }

                if (nbPostulations >= offre.getNbPostes()) {
                    throw new SQLException("Le nombre de postes a déjà été atteint.");
                }

                // 6. Insérer la postulation
                String insertSql = "INSERT INTO Postulation (codeClient, numOffre, codeJournal, numEdition, datePostulation) "
                        +
                        "VALUES (?, ?, ?, ?, ?)";

                try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                    insertStmt.setInt(1, codeDemandeur);
                    insertStmt.setInt(2, numOffre);
                    insertStmt.setInt(3, codeJournal);
                    insertStmt.setInt(4, numEdition);
                    insertStmt.setDate(5, Date.valueOf(LocalDate.now()));
                    insertStmt.executeUpdate();
                }

                // 7. Mettre à jour l'état si besoin
                if (nbPostulations + 1 >= offre.getNbPostes()) {
                    String updateEtatSql = "UPDATE OffreEmploi SET etat = ? WHERE numOffre = ?";
                    try (PreparedStatement updateStmt = conn.prepareStatement(updateEtatSql)) {
                        updateStmt.setString(1, OffreEmploi.EtatOffre.DESACTIVEE.name());
                        updateStmt.setInt(2, numOffre);
                        updateStmt.executeUpdate();
                    }
                }

                conn.commit();

            } catch (Exception e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        }
    }

    // Récupérer une postulation par son id
    public static Postulation getPostulationById(int idPostulation) throws SQLException {
        String sql = "SELECT * FROM Postulation WHERE idPostulation = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setInt(1, idPostulation);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToPostulation(resultSet);
                }
            }
        }
        return null;
    }

    public static List<Postulation> getAllPostulationsByDemandeur(int idClient) throws SQLException {
        String sql = "SELECT * FROM Postulation WHERE codeClient = ?";
        List<Postulation> postulations = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setInt(1, idClient);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Postulation postulation = mapResultSetToPostulation(resultSet);
                    postulations.add(postulation);
                }
            }
        }
        return postulations;
    }

    public static List<OffreEmploi> getPostulationsByDemandeur(int codeDemandeur) throws SQLException {
        List<OffreEmploi> offres = new ArrayList<>();
        String sql = "SELECT o.* FROM Postulation p JOIN OffreEmploi o ON p.numOffre = o.numOffre WHERE p.codeClient = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, codeDemandeur);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                offres.add(OffreEmploiDAO.getOffreEmploiById(rs.getInt("numOffre")));
            }
        }
        return offres;
    }

    // Récupérer toutes les postulations
    public static List<Postulation> getAllPostulations() throws SQLException {
        String sql = "SELECT * FROM Postulation";
        List<Postulation> postulations = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement statement = conn.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Postulation postulation = mapResultSetToPostulation(resultSet);
                postulations.add(postulation);
            }
        }
        return postulations;
    }

    // Supprimer une postulation
    public static void deletePostulation(int idPostulation) throws SQLException {
        String sql = "DELETE FROM Postulation WHERE idPostulation = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setInt(1, idPostulation);
            statement.executeUpdate();
        }
    }

    public static List<Postulation> getPostulationsByEntreprise(String nomEntreprise) throws SQLException {
        String sql = """
                    SELECT p.* FROM Postulation p
                    JOIN OffreEmploi o ON p.numOffre = o.numOffre
                    JOIN Abonnement a ON o.idAbonnement = a.idAbonnement
                    JOIN Entreprise e ON a.idEntreprise = e.idEntreprise
                    WHERE e.nom = ?
                """;

        List<Postulation> postulations = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nomEntreprise);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    postulations.add(mapResultSetToPostulation(rs));
                }
            }
        }
        return postulations;
    }

    // Mapper un ResultSet vers une instance de Postulation
    private static Postulation mapResultSetToPostulation(ResultSet resultSet) throws SQLException {
        int idPostulation = resultSet.getInt("idPostulation");
        Date datePostulation = resultSet.getDate("datePostulation");
        int codeClient = resultSet.getInt("codeClient");
        int numOffre = resultSet.getInt("numOffre");
        int codeJournal = resultSet.getInt("codeJournal");
        int numEdition = resultSet.getInt("numEdition");

        Demandeur demandeur = DemandeurDAO.getDemandeurById(codeClient);
        OffreEmploi offreEmploi = OffreEmploiDAO.getOffreEmploiById(numOffre);
        Edition edition = EditionDAO.getEditionById(codeJournal, numEdition);

        return new Postulation(idPostulation, datePostulation, offreEmploi, demandeur, edition);
    }

    public static List<Object[]> getPostulationsParOffre(int numOffre) {
        List<Object[]> postulations = new ArrayList<>();

        String sql = """
                    SELECT
                        p.idpostulation,
                        d.codeclient,
                        d.nom,
                        d.prenom,
                        d.nbanneesexperience,
                        d.salairesouhaite,
                        d.diplome,
                        c.adresse,
                        c.telephone,
                        p.datepostulation
                    FROM postulation p
                    JOIN demandeur d ON p.codeclient = d.codeclient
                    JOIN client c ON d.codeclient = c.codeclient
                    WHERE p.numoffre = ?
                """;

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, numOffre);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Object[] row = new Object[] {
                        rs.getInt("idpostulation"),
                        rs.getInt("codeclient"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getInt("nbanneesexperience"),
                        rs.getDouble("salairesouhaite"),
                        rs.getString("diplome"),
                        rs.getString("adresse"),
                        rs.getString("telephone"),
                        rs.getDate("datepostulation"),
                };
                postulations.add(row);
            }

        } catch (SQLException e) {
            System.err.println("Erreur SQL (récupération postulations) : " + e.getMessage());
        }

        return postulations;
    }

    public static void mettreAJourEtat(int idPostulation, String nouvelEtat) {
        String sql = "UPDATE postulation SET etat = ? WHERE idpostulation = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nouvelEtat);
            stmt.setInt(2, idPostulation);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur SQL (maj état postulation) : " + e.getMessage());
        }
    }

}
