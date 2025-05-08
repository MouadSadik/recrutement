package main.java.DAO;

import main.java.models.Client;
import main.java.utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDAO {

    public static boolean ajouterClient(Client client) {
        String sql = "INSERT INTO client (codeclient, adresse, telephone) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, client.getCodeClient());
            stmt.setString(2, client.getAdresse());
            stmt.setString(3, client.getTelephone());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            System.err.println("Erreur SQL (ajout client) : " + e.getMessage());
            return false;
        }
    }

    public static boolean supprimerClient(int codeClient) {
        String sql = "DELETE FROM client WHERE codeclient = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, codeClient);

            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;

        } catch (SQLException e) {
            System.err.println("Erreur SQL (suppression client) : " + e.getMessage());
            return false;
        }
    }

    public static boolean modifierClient(Client client) {
        String sql = "UPDATE client SET adresse = ?, telephone = ? WHERE codeclient = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, client.getAdresse());
            stmt.setString(2, client.getTelephone());
            stmt.setInt(3, client.getCodeClient());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            System.err.println("Erreur SQL (modification client) : " + e.getMessage());
            return false;
        }
    }

    public static Client getClientById(int codeClient) {
        String sql = "SELECT * FROM client WHERE codeclient = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, codeClient);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Comme Client est abstrait, on crée une instance anonyme
                return new Client(rs.getInt("codeclient"), rs.getString("adresse"), rs.getString("telephone")) {};
            }

        } catch (SQLException e) {
            System.err.println("Erreur SQL (lecture par id client) : " + e.getMessage());
        }

        return null;
    }

    public static List<Client> getAllClients() {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT * FROM client";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Client c = new Client(rs.getInt("codeclient"), rs.getString("adresse"), rs.getString("telephone")) {};
                clients.add(c);
            }

        } catch (SQLException e) {
            System.err.println("Erreur SQL (lecture tous clients) : " + e.getMessage());
        }

        return clients;
    }

    // MAIN DE TEST
    public static void main(String[] args) {
        // Ajouter clients
        Client c1 = new Client(1, "Rue 123, Casablanca", "0612345678") {};
        if (ajouterClient(c1)) {
            System.out.println("Client ajouté.");
        }

        Client c2 = new Client(2, "Avenue Hassan II, Rabat", "0623456789") {};
        if (ajouterClient(c2)) {
            System.out.println("Client ajouté.");
        }

        // Modifier c2
        c2.setAdresse("Avenue Hassan II, Rabat - Maroc");
        if (modifierClient(c2)) {
            System.out.println("Client modifié.");
        }

        // Rechercher par ID
        Client found = getClientById(2);
        if (found != null) {
            System.out.println("Client trouvé : " + found);
        }

        // Afficher tous
        List<Client> liste = getAllClients();
        System.out.println("Tous les clients :");
        for (Client c : liste) {
            System.out.println(c);
        }

        // Supprimer c1
        if (supprimerClient(1)) {
            System.out.println("Client supprimé.");
        }

    }
}
