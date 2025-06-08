package main.java.ui;

import main.java.DAO.OffreEmploiDAO;
import main.java.models.OffreEmploi;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SelectionOffreUI extends JFrame {

    private JList<String> list;
    private DefaultListModel<String> listModel;
    private List<OffreEmploi> offres;

    public SelectionOffreUI(int entrepriseId) {
        setTitle("Sélectionner une offre pour voir les postulations");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        offres = OffreEmploiDAO.getOffresParEntreprise(entrepriseId);
        listModel = new DefaultListModel<>();

        for (OffreEmploi offre : offres) {
            String line = "Offre #" + offre.getNumOffre() + " | " + offre.getTitre() +
                    " | Compétences: " + offre.getCompetences();
            listModel.addElement(line);
        }

        list = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(list);

        JButton btnVoirPostulations = new JButton("Voir Postulations");
        btnVoirPostulations.addActionListener(e -> {
            int selectedIndex = list.getSelectedIndex();
            if (selectedIndex != -1) {
                int numOffre = offres.get(selectedIndex).getNumOffre();
                new VoirPostulationsUI(numOffre).setVisible(true);
            }
        });

        add(scrollPane, BorderLayout.CENTER);
        add(btnVoirPostulations, BorderLayout.SOUTH);
    }
}
