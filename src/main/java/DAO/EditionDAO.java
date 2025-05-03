package main.java.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

import main.java.classes.Edition;
import main.java.utils.DatabaseConnection;

public class EditionDAO {
    
    public static boolean ajouterEdition(Edition edition){
        String sql = "Insert into edition (codejournal, numedition, dateparution) values (?, ?, ?)";

        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql) ) {
                statement.setInt(1, edition.getCodeJournal());
                statement.setInt(2, edition.getNumEdition());
                statement.setDate(3, java.sql.Date.valueOf(edition.getDateParution()));

                int rowsInserted = statement.executeUpdate();
                return rowsInserted>0;

            } catch(SQLException e) {
                System.out.println("Erreur SQL: " + e.getMessage());
                return false;
            }
        
    }

    public static void main(String[] args) {
        Edition edition1 = new Edition(2, 1, LocalDate.of(2025, 10, 10));
        if(ajouterEdition(edition1)){
            System.out.println("Edition Ajoute");
        }
    }
}