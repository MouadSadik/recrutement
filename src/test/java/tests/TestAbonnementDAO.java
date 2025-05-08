package test.java.tests;

// import java.time.LocalDate;

import main.java.DAO.AbonnementDAO;
import main.java.models.Abonnement;

public class TestAbonnementDAO {
    
    public static void main(String[] args) {
        
        // Entreprise e=EntrepriseDAO.getEntrepriseById(2);
        // Journal j =JournalDAO.getJournalById(1);
        // Abonnement abonnement = new Abonnement(e, j, null, 2, LocalDate.of(2027, 1, 1), true);
        // boolean a = AbonnementDAO.ajouterAbonnement(abonnement);
        // System.out.println("ajout ?"+a);
        // System.out.println(abonnement.toString());


        Abonnement abonnement = AbonnementDAO.getAbonnementById(6);
        if (abonnement != null) {
            abonnement.setActif(false);
            boolean updated = AbonnementDAO.modifierAbonnement(abonnement);
            System.out.println("Abonnement mis à jour ? " + updated);

            // abonnement.setIdAbonnement(1);
            // boolean update=AbonnementDAO.modifierAbonnement(abonnement);
            // System.out.println("Abonnement mis à jour ? " + update);
        }
    
        // Exemple de suppression d'un abonnement
        // boolean deleted = AbonnementDAO.supprimerAbonnement(2);  // Suppression de l'abonnement avec id = 2
        // System.out.println("Abonnement supprimé ? " + deleted);
    }
}
