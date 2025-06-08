package main.java.ui;

import javax.swing.*;
import java.awt.*;

import main.java.DAO.EntrepriseDAO;
import main.java.models.Entreprise;

public class InterfaceEntrepriseEspace extends JFrame {

    public InterfaceEntrepriseEspace(String nomEntreprise) {
        setTitle("Espace Entreprise");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout(20, 20));

        // Récupération de l'entreprise
        Entreprise entreprise = EntrepriseDAO.getEntrepriseParNom(nomEntreprise);
        if (entreprise == null) {
            JOptionPane.showMessageDialog(this, "Erreur : entreprise non trouvée.");
            dispose();
            return;
        }

        // Informations entreprise (haut de la fenêtre)
        JPanel infoPanel = new JPanel(new GridLayout(4, 1));
        infoPanel.setBorder(BorderFactory.createTitledBorder("Informations de l'entreprise"));
        infoPanel.add(new JLabel("Raison Sociale : " + entreprise.getRaisonSociale()));
        infoPanel.add(new JLabel("Adresse : " + entreprise.getAdresse()));
        infoPanel.add(new JLabel("Téléphone : " + entreprise.getTelephone()));
        infoPanel.add(new JLabel("Activités : " + entreprise.getDescriptionActivite()));
        add(infoPanel, BorderLayout.NORTH);

        // Boutons 
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        buttonPanel.setBorder(BorderFactory.createTitledBorder("Actions"));

        JButton btnAjouterAbonnement = new JButton("Ajouter Abonnement");
        JButton btnLancerOffre = new JButton("Lancer Offre");
        JButton btnVoirPostulations = new JButton("Voir les Postulations");
        JButton btnVoirAbonnements = new JButton("Voir les Abonnements");

        buttonPanel.add(btnAjouterAbonnement);
        buttonPanel.add(btnLancerOffre);
        buttonPanel.add(btnVoirPostulations);
        buttonPanel.add(btnVoirAbonnements);

        add(buttonPanel, BorderLayout.CENTER);

        
        btnAjouterAbonnement.addActionListener(e -> {
            new SelectionJournalUI(entreprise);
        });

        btnLancerOffre.addActionListener(e -> {
            new LancerOffreUI(entreprise);
        });

        btnVoirPostulations.addActionListener(e -> {
            new SelectionOffreUI(entreprise.getCodeClient()).setVisible(true);
        });


        btnVoirAbonnements.addActionListener(e -> {
            new VoirAbonnementsUI(entreprise.getCodeClient()).setVisible(true);
        });

        setVisible(true);
    }
}
