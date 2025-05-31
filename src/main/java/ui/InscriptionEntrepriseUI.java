package main.java.ui;

import javax.swing.*;
import java.awt.*;
import main.java.models.Entreprise;
import main.java.DAO.EntrepriseDAO;

public class InscriptionEntrepriseUI extends JFrame {

    public InscriptionEntrepriseUI() {
        setTitle("Inscription Entreprise");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new GridBagLayout());

        JTextField txtAdresse = new JTextField(20);
        JTextField txtTelephone = new JTextField(20);
        JTextField txtRaison = new JTextField(20);
        JTextField txtDescription = new JTextField(20);
        JButton btnValider = new JButton("S'inscrire");
        JLabel lblTitre = new JLabel("Inscription Entreprise");
        lblTitre.setFont(new Font("Arial", Font.BOLD, 32));
        JButton btnRetour = new JButton("Retour");
        


        JPanel panelForm = new JPanel(new GridLayout(6, 2, 10, 10));
        panelForm.add(new JLabel("Adresse :"));
        panelForm.add(txtAdresse);
        panelForm.add(new JLabel("Téléphone :"));
        panelForm.add(txtTelephone);
        panelForm.add(new JLabel("Raison Sociale :"));
        panelForm.add(txtRaison);
        panelForm.add(new JLabel("Description Activité :"));
        panelForm.add(txtDescription);
        panelForm.add(new JLabel(""));
        panelForm.add(btnValider);
        panelForm.setPreferredSize(new Dimension(500, 250));
        panelForm.add(btnRetour);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 20, 20, 20);
        add(lblTitre, gbc);

        gbc.gridy = 1;
        add(panelForm, gbc);

        btnValider.addActionListener(e -> {
            Entreprise entreprise = new Entreprise(
                txtAdresse.getText(),
                txtTelephone.getText(),
                txtRaison.getText(),
                txtDescription.getText()
            );

            boolean success = EntrepriseDAO.ajouterEntreprise(entreprise);
            if (success) {
                JOptionPane.showMessageDialog(this, "Inscription réussie !");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Échec de l'inscription.");
            }
        });

        btnRetour.addActionListener(e -> {
            dispose();
            new AccueilEntrepriseUI();
        });

        setVisible(true);
    }
}
