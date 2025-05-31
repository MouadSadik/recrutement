package main.java.ui;

import main.java.models.Demandeur;
import main.java.DAO.DemandeurDAO;
import main.java.DAO.PostulationDAO;
import main.java.DAO.RecrutementDAO;

import javax.swing.*;
import java.awt.*;

public class DemandeurDashboardFrame extends JFrame {
    private Demandeur demandeur;

    public DemandeurDashboardFrame(Demandeur demandeur) {
        this.demandeur = demandeur;
        setTitle("Tableau de bord - " + demandeur.getNom());
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // --- PROFIL ---
        JPanel profilePanel = new JPanel(new GridLayout(0, 2, 10, 10));
        profilePanel.setBorder(BorderFactory.createTitledBorder("Profil"));

        profilePanel.add(new JLabel("Nom:"));
        profilePanel.add(new JLabel(demandeur.getNom()));
        profilePanel.add(new JLabel("Prénom:"));
        profilePanel.add(new JLabel(demandeur.getPrenom()));
        profilePanel.add(new JLabel("Téléphone:"));
        profilePanel.add(new JLabel(demandeur.getTelephone()));
        profilePanel.add(new JLabel("Adresse:"));
        profilePanel.add(new JLabel(demandeur.getAdresse()));
        profilePanel.add(new JLabel("Diplôme:"));
        profilePanel.add(new JLabel(demandeur.getDiplome()));
        profilePanel.add(new JLabel("Années d'expérience:"));
        profilePanel.add(new JLabel(String.valueOf(demandeur.getAnneeExp())));
        profilePanel.add(new JLabel("Salaire souhaité:"));
        profilePanel.add(new JLabel(String.valueOf(demandeur.getSalaiireSouhaite())));

        // Bouton Modifier Profil
        JButton editProfileButton = new JButton("Modifier mon profil");
        editProfileButton.addActionListener(e -> {
            DemandeurProfileFrame profileFrame = new DemandeurProfileFrame(demandeur.getCodeClient());
            profileFrame.setVisible(true);
            profileFrame.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                    refreshDashboard();
                }
            });
        });
        profilePanel.add(editProfileButton);

        // --- STATS POSTULATIONS ---
        JPanel statsPanel = new JPanel(new GridLayout(0, 1, 10, 10));
        statsPanel.setBorder(BorderFactory.createTitledBorder("Statistiques"));

        int totalPost = 0;
        int totalRecrutements = 0;

        try {
            totalPost = PostulationDAO.getAllPostulationsByDemandeur(demandeur.getCodeClient()).size();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            totalRecrutements = RecrutementDAO.getAllRecrutementsByDemandeur(demandeur.getCodeClient()).size();
        } catch (Exception e) {
            e.printStackTrace();
        }

        JLabel postulationsLbl = new JLabel("Nombre total de postulations : " + totalPost);
        postulationsLbl.setFont(new Font("Arial", Font.PLAIN, 15));

        JLabel recrutementsLbl = new JLabel("Nombre de recrutements obtenus : " + totalRecrutements);
        recrutementsLbl.setFont(new Font("Arial", Font.PLAIN, 15));

        statsPanel.add(postulationsLbl);
        statsPanel.add(recrutementsLbl);

        // --- PANEL DE NAVIGATION ---
        JPanel navigationPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        navigationPanel.setBorder(BorderFactory.createTitledBorder("Navigation"));

        // Bouton Voir les offres
        JButton btnVoirOffres = new JButton("Consulter les offres");
        btnVoirOffres.setFont(new Font("Arial", Font.BOLD, 14));
        btnVoirOffres.addActionListener(e -> {
            DemandeurOffresFrame offresFrame = new DemandeurOffresFrame(demandeur);
            offresFrame.setVisible(true);
        });

        // Bouton Voir mes postulations
        JButton btnVoirPostulations = new JButton("Mes postulations");
        btnVoirPostulations.setFont(new Font("Arial", Font.BOLD, 14));
        btnVoirPostulations.addActionListener(e -> {
            DemandeurPostulationsFrame postulationsFrame = new DemandeurPostulationsFrame(demandeur.getCodeClient());
            postulationsFrame.setVisible(true);
        });

        // Bouton déconnexion
        JButton logoutButton = new JButton("Se déconnecter");
        logoutButton.setFont(new Font("Arial", Font.BOLD, 14));
        logoutButton.addActionListener(e -> {
            dispose();
            new DemandeurAuthFrame().setVisible(true);
        });

        navigationPanel.add(btnVoirOffres);
        navigationPanel.add(btnVoirPostulations);
        navigationPanel.add(logoutButton);

        // Organisation finale
        mainPanel.add(profilePanel, BorderLayout.NORTH);
        mainPanel.add(statsPanel, BorderLayout.CENTER);
        mainPanel.add(navigationPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    private void refreshDashboard() {
        // Recharger les données du demandeur
        Demandeur updatedDemandeur = DemandeurDAO.getDemandeurById(demandeur.getCodeClient());
        if (updatedDemandeur != null) {
            this.demandeur = updatedDemandeur;
            // Recréer l'interface
            dispose();
            new DemandeurDashboardFrame(updatedDemandeur).setVisible(true);
        }
    }
}
