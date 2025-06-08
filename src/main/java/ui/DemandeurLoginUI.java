package main.java.ui;

import main.java.DAO.DemandeurDAO;
import main.java.models.Demandeur;

import javax.swing.*;
import java.awt.*;

public class DemandeurLoginUI extends JFrame {

    private JTextField nomField;
    private JButton loginButton;
    private JButton registerButton;

    public DemandeurLoginUI() {
        setTitle("Connexion / Inscription Demandeur");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        nomField = new JTextField(20);
        loginButton = new JButton("Se connecter");
        registerButton = new JButton("S'inscrire");

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        formPanel.add(new JLabel("Nom:"));
        formPanel.add(nomField);
        formPanel.add(loginButton);
        formPanel.add(registerButton);

        add(formPanel, BorderLayout.CENTER);

        loginButton.addActionListener(e -> {
            String nom = nomField.getText().trim();
            if (!nom.isEmpty()) {
                try {
                    Demandeur d = DemandeurDAO.getDemandeurByNom(nom);
                    if (d != null) {
                        new DemandeurDashboardUI(d).setVisible(true);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(this, "Demandeur non trouvé.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erreur lors de la connexion : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        registerButton.addActionListener(e -> ouvrirFormulaireInscription());
    }

    private void ouvrirFormulaireInscription() {
        JFrame frame = new JFrame("Inscription Demandeur");
        frame.setSize(400, 400);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(8, 2, 5, 5));
        JTextField nom = new JTextField();
        JTextField prenom = new JTextField();
        JTextField adresse = new JTextField();
        JTextField telephone = new JTextField();
        JTextField experience = new JTextField();
        JTextField salaire = new JTextField();
        JTextField diplome = new JTextField();
        JButton valider = new JButton("Valider");

        panel.add(new JLabel("Nom:"));
        panel.add(nom);
        panel.add(new JLabel("Prénom:"));
        panel.add(prenom);
        panel.add(new JLabel("Adresse:"));
        panel.add(adresse);
        panel.add(new JLabel("Téléphone:"));
        panel.add(telephone);
        panel.add(new JLabel("Années d'expérience:"));
        panel.add(experience);
        panel.add(new JLabel("Salaire souhaité:"));
        panel.add(salaire);
        panel.add(new JLabel("Diplôme:"));
        panel.add(diplome);
        panel.add(new JLabel(""));
        panel.add(valider);

        frame.add(panel);
        frame.setVisible(true);

        valider.addActionListener(e -> {
            try {
                Demandeur nouveau = new Demandeur(
                        0,
                        adresse.getText(),
                        telephone.getText(),
                        nom.getText(),
                        prenom.getText(),
                        Integer.parseInt(experience.getText()),
                        Double.parseDouble(salaire.getText()),
                        diplome.getText()
                );
                boolean success = DemandeurDAO.ajouterDemandeur(nouveau);
                if (success) {
                    JOptionPane.showMessageDialog(frame, "Inscription réussie !", "Succès", JOptionPane.INFORMATION_MESSAGE);
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(frame, "Erreur lors de l'inscription.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Erreur : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DemandeurLoginUI().setVisible(true));
    }
}
