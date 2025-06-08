package main.java.ui;

import main.java.models.Demandeur;

import javax.swing.*;
import java.awt.*;

public class DemandeurDashboardUI extends JFrame {

    public DemandeurDashboardUI(Demandeur demandeur) {
        
        setTitle("Espace Demandeur");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel welcomeLabel = new JLabel("Bienvenue " + demandeur.getPrenom() + " " + demandeur.getNom());
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JButton btnJournaux = new JButton("Liste des journaux");
        JButton btnPostulations = new JButton("Mes postulations");
        JButton btnRetour = new JButton("Retour");

        btnJournaux.addActionListener(e -> {
            new JournalUI(demandeur.getCodeClient()).setVisible(true);
            dispose();
        });

        btnPostulations.addActionListener(e -> {
            new PostulationsUI(demandeur.getCodeClient()).setVisible(true);
            dispose();
        });

        btnRetour.addActionListener(e -> {
            new DemandeurLoginUI().setVisible(true);
            dispose();
        });

        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 20, 20));
        buttonPanel.add(btnJournaux);
        buttonPanel.add(btnPostulations);
        buttonPanel.add(btnRetour);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        centerPanel.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));
        centerPanel.add(buttonPanel, BorderLayout.CENTER);

        add(welcomeLabel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
    }
}
