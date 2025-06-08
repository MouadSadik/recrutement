package main.java.ui;

import javax.swing.*;
import java.awt.*;
import main.java.DAO.EntrepriseDAO;

public class ConnexionEntrepriseUI extends JFrame {

    public ConnexionEntrepriseUI() {
        setTitle("Connexion Entreprise");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new GridBagLayout());

        // Composants
        JTextField txtNom = new JTextField(20);
        JButton btnEntrer = new JButton("Entrer");
        JLabel lblTitre = new JLabel("Connexion Entreprise");
        lblTitre.setFont(new Font("Arial", Font.BOLD, 32));
        JLabel lblNom = new JLabel("Raison Sociale :");
        JButton btnRetour = new JButton("Retour");

        // Panel central avec GridLayout
        JPanel panelForm = new JPanel(new GridLayout(4, 1, 10, 10));
        panelForm.add(lblNom);
        panelForm.add(txtNom);
        panelForm.add(btnEntrer);
        panelForm.setPreferredSize(new Dimension(400, 150));
        panelForm.add(btnRetour);

        // Placement avec GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 20, 20, 20);
        add(lblTitre, gbc);

        gbc.gridy = 1;
        add(panelForm, gbc);

        // Action bouton
        btnEntrer.addActionListener(e -> {
            String nom = txtNom.getText().trim();
            if (EntrepriseDAO.verifierEntrepriseParNom(nom)) {
                JOptionPane.showMessageDialog(this, "Connexion réussie !");
                dispose();

                new InterfaceEntrepriseEspace(nom);
            } else {
                JOptionPane.showMessageDialog(this, "Entreprise introuvable.");
            }
        });

        
        btnRetour.addActionListener(e -> {
            dispose();
            new AccueilEntrepriseUI();
        });

        setVisible(true);
    }
}
