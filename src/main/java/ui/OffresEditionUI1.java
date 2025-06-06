package main.java.ui;

import main.java.DAO.EditionDAO;
import main.java.DAO.OffreEmploiDAO;
import main.java.models.Edition;
import main.java.models.OffreEmploi;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class OffresEditionUI1 extends JFrame {
    

    public OffresEditionUI1(int codeDemandeur, int codeJournal, int numedition) {

        Edition edition = EditionDAO.getEditionById(codeJournal, numedition);
        setTitle("Offres d'emploi pour l'édition : " + edition.getNumEdition());
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
     
        try {
            List<OffreEmploi> offres = OffreEmploiDAO.getOffresParEdition(edition.getCodeJournal(), edition.getNumEdition());
            DefaultListModel<String> listModel = new DefaultListModel<>();
            for (OffreEmploi offre : offres) {
                listModel.addElement(offre.getTitre() + " - " + offre.getEtat());
            }

            JList<String> offresList = new JList<>(listModel);
            JScrollPane scrollPane = new JScrollPane(offresList);

            add(scrollPane, BorderLayout.CENTER);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement des offres : " + e.getMessage());
        }
    }
}
