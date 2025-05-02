
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

public class TestJDBC {

    public static void main(String[] args) {
        // Informations de connexion
        String url = "jdbc:mysql://localhost:3306/agence_recrutement"; // Remplacer par le nom de ta base
        String user = "root"; // Nom d'utilisateur
        String password = "12mouad12"; // Mot de passe

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            // 1. Charger le driver (optionnel à partir de Java 6)
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 2. Établir la connexion
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connexion réussie !");

            // 3. Créer et exécuter la requête SQL
            stmt = conn.createStatement();
            String sql = "SELECT * FROM journal";
            rs = stmt.executeQuery(sql);

            // 4. Afficher les résultats
            while (rs.next()) {
                int codeJournal = rs.getInt("codeJournal");
                String nomJournal = rs.getString("nomJournal");
                String periodicite = rs.getString("periodicite");

                System.out.println("ID: " + codeJournal + ", Nom: " + nomJournal + ", Periodicite: " + periodicite);
            }

        } catch (ClassNotFoundException e) {
            System.out.println("Erreur : Driver JDBC introuvable !");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Erreur SQL : " + e.getMessage());
        } finally {
            // 5. Fermer les ressources
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println("Erreur lors de la fermeture : " + e.getMessage());
            }
        }
    }
}
