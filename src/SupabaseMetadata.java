

import java.sql.*;

public class SupabaseMetadata {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://aws-0-eu-west-2.pooler.supabase.com:5432/postgres";
        String user = "postgres.mlargwldfixnhgdppvje";
        String password = "Badrmane2020.";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            System.out.println("✅ Connexion réussie à Supabase !");

            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet tables = metaData.getTables(null, "public", "%", new String[] { "TABLE" });

            System.out.println("📋 Tables dans la base de données :");
            while (tables.next()) {
                String tableName = tables.getString("TABLE_NAME");
                System.out.println(" - " + tableName);
            }

        } catch (SQLException e) {
            System.err.println("❌ Erreur SQL : " + e.getMessage());
            e.printStackTrace();
        }
    }
}