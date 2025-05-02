import classes.Entreprise;
import java.sql.*;

public class EntrepriseDAO {

    private static final String URL = "jdbc:postgresql://aws-0-eu-west-2.pooler.supabase.com:5432/postgres";
    private static final String USER = "postgres.mlargwldfixnhgdppvje";
    private static final String PASSWORD = "Badrmane2020.";

    public static Entreprise getEntrepriseById(int id) {
        Entreprise entreprise = null;

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
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
            e.printStackTrace();
        }

        return entreprise;
    }

    public static void main(String[] args) {
        Entreprise e = getEntrepriseById(1);
        if (e != null) {
           System.out.println( e.toString());
        } else {
            System.out.println("❌ Aucune entreprise trouvée.");
        }
    }
}
