package main.java.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Assurez-vous que JournalUI et EntrepriseUI sont bien importés
// import main.java.UI.JournalUI;
// import main.java.UI.EntrepriseUI;

public class ClientUI extends JFrame {

    public ClientUI() {
        setTitle("Gestion des Clients");
        setSize(400, 200);
        setLocationRelativeTo(null); // Centre la fenêtre
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        JButton btnDemandeur = new JButton("Demandeur");
        JButton btnEntreprise = new JButton("Entreprise");

        // Action pour le bouton Demandeur
        btnDemandeur.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lancer l'UI de Journal
                SwingUtilities.invokeLater(() -> {
                     new DemandeurUI().setVisible(true);
                });
            }
        });

        // Action pour le bouton Entreprise
        btnEntreprise.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lancer l'UI d'Entreprise
                SwingUtilities.invokeLater(() -> {
                    // new InterfaceEntrepriseEspace("").setVisible(true);
                });
            }
        });

        add(btnDemandeur);
        add(btnEntreprise);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ClientUI().setVisible(true);
        });
    }
}
