package main.java.DAO;

import main.java.models.Abonnement;
import main.java.models.Edition;
import main.java.models.OffreEmploi;
import main.java.models.OffreEmploi.EtatOffre;
import main.java.utils.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OffreEmploiDAO {

    // Ajouter une offre d'emploi
    public static void addOffreEmploi(OffreEmploi offreEmploi) throws SQLException {
        String sql = "INSERT INTO OffreEmploi (titre, competences, nbAnneeExperienceDemandee, nbPostes, etat, idAbonnement, codeJournal, numEdition) "
                +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setString(1, offreEmploi.getTitre());
            statement.setString(2, offreEmploi.getCompetences());
            statement.setInt(3, offreEmploi.getNbAnneeExperienceDemandee());
            statement.setInt(4, offreEmploi.getNbPostes());
            statement.setString(5, offreEmploi.getEtat().name());
            statement.setInt(6, offreEmploi.getAbonnement().getIdAbonnement());
            statement.setInt(7, offreEmploi.getEdition().getCodeJournal());
            statement.setInt(8, offreEmploi.getEdition().getNumEdition());

            statement.executeUpdate();
        }
    }

    // Récupérer une offre d'emploi par son numéro
    public static OffreEmploi getOffreEmploiById(int numOffre) throws SQLException {
        String sql = "SELECT * FROM OffreEmploi WHERE numOffre = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, numOffre);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToOffreEmploi(resultSet);
                }
            }
        }
        return null;
    }

    // Récupérer toutes les offres d'emploi
    public static List<OffreEmploi> getAllOffresEmploi() throws SQLException {
        String sql = "SELECT * FROM OffreEmploi";
        List<OffreEmploi> offresEmploi = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement statement = conn.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                OffreEmploi offreEmploi = mapResultSetToOffreEmploi(resultSet);
                offresEmploi.add(offreEmploi);
            }
        }
        return offresEmploi;
    }

    // Mettre à jour une offre d'emploi
    public static void updateOffreEmploi(OffreEmploi offreEmploi) throws SQLException {
        String sql = "UPDATE OffreEmploi SET titre = ?, competences = ?, nbAnneeExperienceDemandee = ?, nbPostes = ?, "
                +
                "etat = ?, idAbonnement = ?, codeJournal = ?, numEdition = ? WHERE numOffre = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setString(1, offreEmploi.getTitre());
            statement.setString(2, offreEmploi.getCompetences());
            statement.setInt(3, offreEmploi.getNbAnneeExperienceDemandee());
            statement.setInt(4, offreEmploi.getNbPostes());
            statement.setString(5, offreEmploi.getEtat().name());
            statement.setInt(6, offreEmploi.getAbonnement().getIdAbonnement());
            statement.setInt(7, offreEmploi.getEdition().getCodeJournal());
            statement.setInt(8, offreEmploi.getEdition().getNumEdition());
            statement.setInt(9, offreEmploi.getNumOffre());

            statement.executeUpdate();
        }
    }

    // Supprimer une offre d'emploi
    public static void deleteOffreEmploi(int numOffre) throws SQLException {
        String sql = "DELETE FROM OffreEmploi WHERE numOffre = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, numOffre);
            statement.executeUpdate();
        }
    }

    // Mapper un ResultSet en une instance de OffreEmploi
    private static OffreEmploi mapResultSetToOffreEmploi(ResultSet resultSet) throws SQLException {
        int numOffre = resultSet.getInt("numOffre");
        String titre = resultSet.getString("titre");
        String competences = resultSet.getString("competences");
        int nbAnneeExperienceDemandee = resultSet.getInt("nbAnneeExperienceDemandee");
        int nbPostes = resultSet.getInt("nbPostes");
        OffreEmploi.EtatOffre etat = OffreEmploi.EtatOffre.valueOf(resultSet.getString("etat"));
        int idAbonnement = resultSet.getInt("idAbonnement");
        int codeJournal = resultSet.getInt("codeJournal");
        int numEdition = resultSet.getInt("numEdition");

        // Récupérer l'édition et l'abonnement à partir des ID
        Edition edition = EditionDAO.getEditionById(codeJournal, numEdition);
        Abonnement abonnement = AbonnementDAO.getAbonnementById(idAbonnement);

        return new OffreEmploi(numOffre, titre, competences, nbAnneeExperienceDemandee, nbPostes, etat, edition,
                abonnement);
    }

    public static List<OffreEmploi> getOffresParEntreprise(int entrepriseId) {
        List<OffreEmploi> offres = new ArrayList<>();
        String sql = "SELECT * FROM offreemploi WHERE numoffre = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, entrepriseId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int numOffre = rs.getInt("numoffre");
                String titre = rs.getString("titre");
                String competences = rs.getString("competences");
                int nbAnneeExperience = rs.getInt("nbanneeexperiencedemandee");
                int nbPostes = rs.getInt("nbpostes");
                int codeJournal = rs.getInt("codejournal");

                EtatOffre etat = EtatOffre.valueOf(rs.getString("etat"));

                int editionId = rs.getInt("numedition");
                Edition edition = EditionDAO.getEditionById(codeJournal, editionId);

                int abonnementId = rs.getInt("idabonnement");
                Abonnement abonnement = AbonnementDAO.getAbonnementById(abonnementId);

                // Création de l'objet OffreEmploi
                OffreEmploi offre = new OffreEmploi(
                        numOffre,
                        titre,
                        competences,
                        nbAnneeExperience,
                        nbPostes,
                        etat,
                        edition,
                        abonnement);

                offres.add(offre);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return offres;
    }

    public static OffreEmploi getOffreEmploiByTitre(String titre) throws SQLException {
    String sql = "SELECT * FROM OffreEmploi WHERE titre = ?";

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement statement = conn.prepareStatement(sql)) {

        statement.setString(1, titre);

        try (ResultSet rs = statement.executeQuery()) {
            if (rs.next()) {
                int numOffre = rs.getInt("numOffre");
                String competences = rs.getString("competences");
                int nbAnneeExp = rs.getInt("nbAnneeExperienceDemandee");
                int nbPostes = rs.getInt("nbPostes");
                EtatOffre etat = EtatOffre.valueOf(rs.getString("etat"));

                int codeJournal = rs.getInt("codeJournal");
                int numEdition = rs.getInt("numEdition");
                Edition edition = EditionDAO.getEditionById(codeJournal, numEdition);

                int idAbonnement = rs.getInt("idAbonnement");
                Abonnement abonnement = AbonnementDAO.getAbonnementById(idAbonnement);

                return new OffreEmploi(numOffre, titre, competences, nbAnneeExp, nbPostes, etat, edition, abonnement);
            }
        }
    }
    return null;
}



}
