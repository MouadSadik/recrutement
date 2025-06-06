package main.java.ui;

import main.java.DAO.OffreEmploiDAO;
import main.java.DAO.PostulationDAO;
import main.java.models.OffreEmploi;

import javax.swing.*;
import java.awt.*;

public class DetailOffreUI extends JFrame {

    public DetailOffreUI(int codeDemandeur, int codeJournal, int numEdition, int numOffre) {
        try {
            OffreEmploi offre = OffreEmploiDAO.getOffreEmploiById(numOffre);

            if (offre == null) {
                JOptionPane.showMessageDialog(this, "Offre non trouvée.", "Erreur", JOptionPane.ERROR_MESSAGE);
                dispose();
                return;
            }

            setTitle("Détail de l'offre : " + offre.getTitre());
            setSize(500, 400);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            JPanel content = new JPanel();
            content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
            content.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            content.add(new JLabel("Titre : " + offre.getTitre()));
            content.add(new JLabel("Compétences : " + offre.getCompetences()));
            content.add(new JLabel("Expérience requise : " + offre.getNbAnneeExperienceDemandee() + " années"));
            content.add(new JLabel("Nombre de postes : " + offre.getNbPostes()));
            content.add(new JLabel("État : " + offre.getEtat()));
            content.add(new JLabel("Édition : " + offre.getEdition().getNumEdition()));
            content.add(new JLabel("Journal : " + offre.getEdition().getCodeJournal()));
            content.add(Box.createRigidArea(new Dimension(0, 20)));

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            JButton postulerButton = new JButton("Postuler");
            JButton retourButton = new JButton("Retour");

            postulerButton.addActionListener(e -> {
                try {
                    PostulationDAO.ajouterPostulation(codeDemandeur, codeJournal, numEdition, numOffre);
                    JOptionPane.showMessageDialog(this, "Votre candidature a été enregistrée avec succès !");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Erreur lors de la postulation : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            });

            retourButton.addActionListener(e -> dispose());

            buttonPanel.add(postulerButton);
            buttonPanel.add(retourButton);
            content.add(buttonPanel);

            add(content);

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement de l'offre : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            dispose();
        }
    }

}

