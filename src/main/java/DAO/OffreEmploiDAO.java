package main.java.DAO;

import main.java.classes.OffreEmploi;
import main.java.classes.Edition;
import main.java.classes.Abonnement;
import main.java.utils.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OffreEmploiDAO {

    // Ajouter une offre d'emploi
    public static void addOffreEmploi(OffreEmploi offreEmploi) throws SQLException {
        String sql = "INSERT INTO OffreEmploi (titre, competences, nbAnneeExperienceDemandee, nbPostes, etat, idAbonnement, codeJournal, numEdition) " +
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
        String sql = "UPDATE OffreEmploi SET titre = ?, competences = ?, nbAnneeExperienceDemandee = ?, nbPostes = ?, " +
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

        return new OffreEmploi(numOffre, titre, competences, nbAnneeExperienceDemandee, nbPostes, etat, edition, abonnement);
    }
}
