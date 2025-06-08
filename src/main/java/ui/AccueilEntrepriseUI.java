package main.java.ui;

import javax.swing.*;
import java.awt.*;

public class AccueilEntrepriseUI extends JFrame {

    public AccueilEntrepriseUI() {

        setTitle("Bienvenue Entreprise");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 1, 10, 10));

        setLocationRelativeTo(null); // Centrer la fenêtre
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        

        // Composants
        JButton btnConnexion = new JButton("Connexion");
        JButton btnInscription = new JButton("Inscription");

        JLabel titre = new JLabel("Espace Entreprise", SwingConstants.CENTER);
        titre.setFont(new Font("Arial", Font.BOLD, 36));

        // Ajout des composants
        add(titre);
        add(btnConnexion);
        add(btnInscription);

        // Actions des boutons
        btnConnexion.addActionListener(e -> {
            dispose(); // Ferme cette fenêtre
            new ConnexionEntrepriseUI(); // Ouvre la fenêtre de connexion
        });

        btnInscription.addActionListener(e -> {
            dispose();
            new InscriptionEntrepriseUI(); // Ouvre la fenêtre d'inscription
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new AccueilEntrepriseUI();
    }
}
