package main.java.DAO;

import main.java.models.Demandeur;
import main.java.models.OffreEmploi;
import main.java.models.Recrutement;
import main.java.utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RecrutementDAO {

    // Ajouter un recrutement
    public static void addRecrutement(Recrutement recrutement) throws SQLException {
        String sql = "INSERT INTO Recrutement (numOffre, codeClient, dateRecrutement) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setInt(1, recrutement.getOffreEmploi().getNumOffre());
            statement.setInt(2, recrutement.getDemandeur().getCodeClient());
            statement.setDate(3, recrutement.getDateRecrutement());

            statement.executeUpdate();
        }
    }

    // Récupérer un recrutement par son ID
    public static Recrutement getRecrutementById(int idRecrutement) throws SQLException {
        String sql = "SELECT * FROM Recrutement WHERE idRecrutement = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setInt(1, idRecrutement);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToRecrutement(resultSet);
                }
            }
        }
        return null;
    }

    // Récupérer tous les recrutements
    public static List<Recrutement> getAllRecrutements() throws SQLException {
        String sql = "SELECT * FROM Recrutement";
        List<Recrutement> recrutements = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Recrutement recrutement = mapResultSetToRecrutement(resultSet);
                recrutements.add(recrutement);
            }
        }
        return recrutements;
    }

    // Supprimer un recrutement
    public static void deleteRecrutement(int idRecrutement) throws SQLException {
        String sql = "DELETE FROM Recrutement WHERE idRecrutement = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setInt(1, idRecrutement);
            statement.executeUpdate();
        }
    }

    // Mapper un ResultSet vers une instance de Recrutement
    private static Recrutement mapResultSetToRecrutement(ResultSet resultSet) throws SQLException {
        int idRecrutement = resultSet.getInt("idRecrutement");
        Date dateRecrutement = resultSet.getDate("dateRecrutement");
        int numOffre = resultSet.getInt("numOffre");
        int codeClient = resultSet.getInt("codeClient");

        OffreEmploi offreEmploi = OffreEmploiDAO.getOffreEmploiById(numOffre);
        Demandeur demandeur = DemandeurDAO.getDemandeurById(codeClient);

        return new Recrutement(idRecrutement, dateRecrutement, demandeur, offreEmploi);
    }
}
