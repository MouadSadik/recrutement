package main.java.ui;

import javax.swing.*;
import java.awt.*;

public class ClientUI extends JFrame {

    public ClientUI() {
        setTitle("Gestion des Clients");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20); // Espacement autour des boutons

        // Titre
        JLabel titre = new JLabel("Bienvenue dans l'agence de Recrutement");
        titre.setFont(new Font("Arial", Font.BOLD, 28));
        titre.setForeground(new Color(40, 40, 40));
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(titre, gbc);

        // Bouton Demandeur
        JButton btnDemandeur = new JButton("Demandeur");
        btnDemandeur.setPreferredSize(new Dimension(250, 60)); // Agrandit le bouton
        btnDemandeur.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> new DemandeurUI().setVisible(true));
        });

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(btnDemandeur, gbc);

        // Bouton Entreprise
        JButton btnEntreprise = new JButton("Entreprise");
        btnEntreprise.setPreferredSize(new Dimension(250, 60)); // Agrandit le bouton
        btnEntreprise.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> new AccueilEntrepriseUI().setVisible(true));
        });

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(btnEntreprise, gbc);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ClientUI().setVisible(true);
        });
    }
}
