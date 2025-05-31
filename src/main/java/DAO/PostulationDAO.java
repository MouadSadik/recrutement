package main.java.DAO;

import main.java.models.Demandeur;
import main.java.models.Edition;
import main.java.models.OffreEmploi;
import main.java.models.Postulation;
import main.java.utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostulationDAO {

    // Ajouter une postulation
    public static void addPostulation(Postulation postulation) throws SQLException {
        String sql = "INSERT INTO Postulation (codeClient, numOffre, codeJournal, numEdition, datePostulation) " +
<<<<<<< HEAD
                "VALUES (?, ?, ?, ?, ?)";

=======
                     "VALUES (?, ?, ?, ?, ?)";
                     
>>>>>>> 117a4c9e6ee2cb6ea2376760bb8063b4bbacafe3
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setInt(1, postulation.getDemandeur().getCodeClient());
            statement.setInt(2, postulation.getOffreEmploi().getNumOffre());
            statement.setInt(3, postulation.getEdition().getCodeJournal());
            statement.setInt(4, postulation.getEdition().getNumEdition());
            statement.setDate(5, postulation.getDatePostulation());

            statement.executeUpdate();
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
}
