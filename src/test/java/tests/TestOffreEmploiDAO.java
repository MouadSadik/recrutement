package test.java.tests;

import java.sql.SQLException;
import java.util.List;

import main.java.DAO.AbonnementDAO;
import main.java.DAO.EditionDAO;
import main.java.DAO.OffreEmploiDAO;
import main.java.models.Abonnement;
import main.java.models.OffreEmploi;

public class TestOffreEmploiDAO {
    public static void main(String[] args) {
        try {
            OffreEmploi nouvelleOffre = new OffreEmploi(
                0, // le champ numOffre sera généré par la base si auto-incrémenté
                "Développeur Java",
                "Java, SQL, Spring",
                2,
                3,
                OffreEmploi.EtatOffre.ACTIVE,
                EditionDAO.getEditionById(1, 1),
                AbonnementDAO.getAbonnementById(6)
            );
            OffreEmploiDAO.addOffreEmploi(nouvelleOffre);
            System.out.println("Offre ajoutée avec succès.");
            List<OffreEmploi> offres = OffreEmploiDAO.getAllOffresEmploi();
            System.out.println("Liste des offres :");
            for (OffreEmploi offre : offres) {
                System.out.println(offre);
            }
            if (!offres.isEmpty()) {
                int id = offres.get(0).getNumOffre();
                OffreEmploi retrieved = OffreEmploiDAO.getOffreEmploiById(id);
                System.out.println("Offre récupérée : " + retrieved);


            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
}
