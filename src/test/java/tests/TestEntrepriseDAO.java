package test.java.tests;
import main.java.DAO.EntrepriseDAO;
import main.java.classes.Entreprise;

public class TestEntrepriseDAO {


    public static void main(String[] args) {
        Entreprise e = EntrepriseDAO.getEntrepriseById(2);
        if (e != null) {
            System.out.println("✅ Entreprise trouvée :");
            System.out.println(e);
        } else {
            System.out.println("❌ Aucune entreprise trouvée.");
        }
    }
}