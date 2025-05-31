package main.java.ui;

import main.java.DAO.OffreEmploiDAO;
import main.java.DAO.PostulationDAO;
import main.java.models.Demandeur;
import main.java.models.Edition;
import main.java.models.OffreEmploi;
import main.java.models.Postulation;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.util.List;

public class DemandeurOffresFrame extends JFrame {
    private Demandeur demandeur;

    public DemandeurOffresFrame(Demandeur demandeur) {
        this.demandeur = demandeur;
        setTitle("Offres d'emploi disponibles");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel title = new JLabel("Liste des offres d'emploi disponibles");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        mainPanel.add(title, BorderLayout.NORTH);

        JPanel offresPanel = new JPanel();
        offresPanel.setLayout(new BoxLayout(offresPanel, BoxLayout.Y_AXIS));

        try {
            List<OffreEmploi> offres = OffreEmploiDAO.getAllOffresEmploi();
            for (OffreEmploi offre : offres) {
                JPanel offrePanel = new JPanel(new GridLayout(0, 2, 5, 5));
                offrePanel.setBorder(BorderFactory.createTitledBorder("Offre #" + offre.getNumOffre()));

                offrePanel.add(new JLabel("Titre:"));
                offrePanel.add(new JLabel(offre.getTitre()));

                offrePanel.add(new JLabel("Compétences:"));
                offrePanel.add(new JLabel(offre.getCompetences()));

                offrePanel.add(new JLabel("Expérience requise (ans):"));
                offrePanel.add(new JLabel(String.valueOf(offre.getNbAnneeExperienceDemandee())));

                offrePanel.add(new JLabel("Postes à pourvoir:"));
                offrePanel.add(new JLabel(offre.getNbPostes() + " (" + offre.getNbPostesRestants() + " restants)"));

                offrePanel.add(new JLabel("État:"));
                offrePanel.add(new JLabel(offre.getEtat().name()));

                JButton btnPostuler = new JButton("Postuler");
                btnPostuler.addActionListener(e -> {
                    try {
                        if (!offre.peutPostuler(demandeur)) {
                            JOptionPane.showMessageDialog(this,
                                    "Vous ne pouvez pas postuler à cette offre (déjà postulé ou expérience insuffisante).");
                            return;
                        }

                        Postulation postulation = new Postulation(0, new Date(System.currentTimeMillis()), offre,
                                demandeur, offre.getEdition());
                        PostulationDAO.addPostulation(postulation);
                        offre.ajouterPostulation(postulation); // facultatif pour mettre à jour l'objet localement
                        JOptionPane.showMessageDialog(this, "Postulation envoyée avec succès !");
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(this, "Erreur lors de la postulation", "Erreur",
                                JOptionPane.ERROR_MESSAGE);
                    }
                });

                JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
                bottom.add(btnPostuler);

                JPanel container = new JPanel(new BorderLayout());
                container.add(offrePanel, BorderLayout.CENTER);
                container.add(bottom, BorderLayout.SOUTH);

                offresPanel.add(container);
            }
        } catch (Exception e) {
            e.printStackTrace();
            offresPanel.add(new JLabel("Erreur de chargement des offres."));
        }

        JScrollPane scrollPane = new JScrollPane(offresPanel);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel);
        setVisible(true);
    }
}
