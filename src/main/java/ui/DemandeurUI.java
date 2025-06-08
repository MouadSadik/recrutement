package main.java.ui;

import main.java.DAO.DemandeurDAO;
import main.java.models.Demandeur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DemandeurUI extends JFrame {

    private JTextField nomField;
    private JButton loginButton;
    private JButton registerButton;

    public DemandeurUI() {
        setTitle("Connexion / Inscription Demandeur");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // UI Elements
        nomField = new JTextField(20);
        loginButton = new JButton("Se connecter");
        registerButton = new JButton("S'inscrire");

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Nom:"));
        panel.add(nomField);
        panel.add(loginButton);
        panel.add(registerButton);

        add(panel);

        // Login Action
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nom = nomField.getText().trim();
                if (!nom.isEmpty()) {
                    try {
                        Demandeur d = DemandeurDAO.getDemandeurByNom(nom);

                        if (d != null) {
                            String message = "Bienvenue " + d.getPrenom() + " " + d.getNom() +
                                            "\nExpérience: " + d.getAnneeExp() + " ans" +
                                            "\nSalaire souhaité: " + d.getSalaiireSouhaite();

                            Object[] options = {"Liste des journaux", "Mes postulations", "Fermer"};
                            int choice = JOptionPane.showOptionDialog(
                                    null,
                                    message,
                                    "Connexion réussie",
                                    JOptionPane.YES_NO_CANCEL_OPTION,
                                    JOptionPane.INFORMATION_MESSAGE,
                                    null,
                                    options,
                                    options[0]
                            );

                            if (choice == 0) {
                                SwingUtilities.invokeLater(() -> {
                                    new JournalUI(d.getCodeClient()).setVisible(true);
                                });
                            } else if (choice == 1) {
                                SwingUtilities.invokeLater(() -> {
                                    new PostulationsUI(d.getCodeClient()).setVisible(true);
                                });
                            }
                           
                        }

                        else {
                            JOptionPane.showMessageDialog(null, "Demandeur non trouvé.", "Erreur", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Erreur lors de la connexion : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        // Register Action
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ouvrirFormulaireInscription();
            }
        });
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

        valider.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
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
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new DemandeurUI().setVisible(true);
        });
    }
}

