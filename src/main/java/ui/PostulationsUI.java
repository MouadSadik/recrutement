package main.java.ui;

import main.java.DAO.PostulationDAO;
import main.java.models.OffreEmploi;

import javax.swing.*;
import java.util.List;

public class PostulationsUI extends JFrame {

    public PostulationsUI(int codeDemandeur) {
        setTitle("Mes Postulations");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        DefaultListModel<String> model = new DefaultListModel<>();

        try {
            List<OffreEmploi> offres = PostulationDAO.getPostulationsByDemandeur(codeDemandeur);

            for (OffreEmploi offre : offres) {
                model.addElement(offre.getTitre() + " | " +
                        offre.getCompetences() + " | " +
                        offre.getEtat() + " | " +
                        "Journal : " + offre.getEdition().getCodeJournal() + " |  Édition "
                        + offre.getEdition().getNumEdition());
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement des postulations : " + e.getMessage());
        }

        JList<String> list = new JList<>(model);
        JScrollPane scrollPane = new JScrollPane(list);
        add(scrollPane);
    }
}
