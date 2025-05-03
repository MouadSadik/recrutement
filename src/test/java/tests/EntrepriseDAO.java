package test.java.tests;
import main.java.classes.Entreprise;
import main.java.utils.DatabaseConnection;
import java.sql.*;

public class EntrepriseDAO {

    public static Entreprise getEntrepriseById(int id) {
        Entreprise entreprise = null;

        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = """
                SELECT c.codeClient, c.adresse, c.telephone,
                       e.raisonSociale, e.descriptionActivite
                FROM Client c
                JOIN Entreprise e ON c.codeClient = e.codeClient
                WHERE c.codeClient = ?
            """;

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        entreprise = new Entreprise(
                                rs.getInt("codeClient"),
                                rs.getString("adresse"),
                                rs.getString("telephone"),
                                rs.getString("raisonSociale"),
                                rs.getString("descriptionActivite")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Erreur SQL : " + e.getMessage());
        }

        return entreprise;
    }

    public static void main(String[] args) {
        Entreprise e = getEntrepriseById(1);
        if (e != null) {
            System.out.println("✅ Entreprise trouvée :");
            System.out.println(e);
        } else {
            System.out.println("❌ Aucune entreprise trouvée.");
        }
    }
}