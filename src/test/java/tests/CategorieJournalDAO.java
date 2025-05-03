package test.java.tests;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import main.java.classes.CategorieJournal;
import main.java.utils.DatabaseConnection;

public class CategorieJournalDAO {

    // Create
    public static boolean ajouterCategorie(CategorieJournal categorie) {
        String sql = "INSERT INTO categorie (libelle) VALUES (?)";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, categorie.getLibelle());
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            System.err.println("Erreur SQL (ajout) : " + e.getMessage());
            return false;
        }
    }

    // Read (par id)
    public static CategorieJournal getCategorieById(int id) {
        String sql = "SELECT * FROM categorie WHERE idcategorie = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new CategorieJournal(
                        rs.getInt("idcategorie"),
                        rs.getString("libelle"));
            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL (lecture) : " + e.getMessage());
        }
        return null;
    }

    // Read (toutes les categories)
    public static List<CategorieJournal> getAllCategories() {
        List<CategorieJournal> categories = new ArrayList<>();
        String sql = "SELECT * FROM categorie";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                CategorieJournal cat = new CategorieJournal(
                        rs.getInt("idcategorie"),
                        rs.getString("libelle"));
                categories.add(cat);
            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL (lecture all) : " + e.getMessage());
        }
        return categories;
    }

    // Update
    public static boolean modifierCategorie(CategorieJournal categorie) {
        String sql = "UPDATE categorie SET libelle = ? WHERE idcategorie = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, categorie.getLibelle());
            stmt.setInt(2, categorie.getIdCategorie());
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.err.println("Erreur SQL (modification) : " + e.getMessage());
            return false;
        }
    }

    // Delete
    public static boolean supprimerCategorie(int id) {
        String sql = "DELETE FROM categorie WHERE idcategorie = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            System.err.println("Erreur SQL (suppression) : " + e.getMessage());
            return false;
        }
    }

    public static void main(String[] args) {
        CategorieJournal nouvelleCat = new CategorieJournal(1, "Informatique");
        if (ajouterCategorie(nouvelleCat)) {
            System.out.println("Categorie ajoutee");
        }

        CategorieJournal cat = getCategorieById(1);
        if (cat != null) {
            System.out.println("Categorie trouvee : " + cat);
        }

        if (cat != null) {
            cat.setLibelle("Sciences & Technologie");
            if (modifierCategorie(cat)) {
                System.out.println("Categorie modifiee");
            }
        }

        if (supprimerCategorie(1)) {
            System.out.println("Categorie supprimee");
        }

        List<CategorieJournal> allCats = getAllCategories();
        System.out.println("Toutes les categories :");
        for (CategorieJournal c : allCats) {
            System.out.println(c);
        }
    }
}
