package main.java.ui;

import main.java.DAO.DemandeurDAO;
import main.java.models.Demandeur;
import javax.swing.*;
import java.awt.*;

public class DemandeurProfileFrame extends JFrame {
    private final int codeClient;
    private JTextField nomField;
    private JTextField prenomField;
    private JTextField diplomeField;
    private JSpinner experienceSpinner;
    private JSpinner salaireSpinner;
    private JTextField adresseField;
    private JTextField telephoneField;

    public DemandeurProfileFrame(int codeClient) {
        this.codeClient = codeClient;
        
        setTitle("Mon Profil");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initComponents();
        loadProfile();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));

        // Create form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Add form fields
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Nom:"), gbc);
        gbc.gridx = 1;
        nomField = new JTextField(20);
        formPanel.add(nomField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Prénom:"), gbc);
        gbc.gridx = 1;
        prenomField = new JTextField(20);
        formPanel.add(prenomField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Adresse:"), gbc);
        gbc.gridx = 1;
        adresseField = new JTextField(20);
        formPanel.add(adresseField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Téléphone:"), gbc);
        gbc.gridx = 1;
        telephoneField = new JTextField(20);
        formPanel.add(telephoneField, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(new JLabel("Diplôme:"), gbc);
        gbc.gridx = 1;
        diplomeField = new JTextField(20);
        formPanel.add(diplomeField, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        formPanel.add(new JLabel("Années d'expérience:"), gbc);
        gbc.gridx = 1;
        experienceSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 50, 1));
        formPanel.add(experienceSpinner, gbc);

        gbc.gridx = 0; gbc.gridy = 6;
        formPanel.add(new JLabel("Salaire souhaité:"), gbc);
        gbc.gridx = 1;
        salaireSpinner = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 1000000.0, 1000.0));
        formPanel.add(salaireSpinner, gbc);

        // Add save button
        JButton saveButton = new JButton("Enregistrer les modifications");
        saveButton.addActionListener(e -> saveProfile());

        // Add components to frame
        add(new JLabel("Modifier mon profil", SwingConstants.CENTER), BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
        add(saveButton, BorderLayout.SOUTH);
    }

    private void loadProfile() {
        Demandeur demandeur = DemandeurDAO.getDemandeurById(codeClient);
        if (demandeur != null) {
            nomField.setText(demandeur.getNom());
            prenomField.setText(demandeur.getPrenom());
            diplomeField.setText(demandeur.getDiplome());
            experienceSpinner.setValue(demandeur.getAnneeExp());
            salaireSpinner.setValue(demandeur.getSalaiireSouhaite());
            adresseField.setText(demandeur.getAdresse());
            telephoneField.setText(demandeur.getTelephone());
        }
    }

    private void saveProfile() {
        try {
            Demandeur demandeur = new Demandeur(
                codeClient,
                adresseField.getText(),
                telephoneField.getText(),
                nomField.getText(),
                prenomField.getText(),
                (Integer) experienceSpinner.getValue(),
                (Double) salaireSpinner.getValue(),
                diplomeField.getText()
            );

            boolean success = DemandeurDAO.modifierDemandeur(demandeur);
            if (success) {
                JOptionPane.showMessageDialog(this,
                    "Profil mis à jour avec succès!",
                    "Succès",
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                    "Erreur lors de la mise à jour du profil",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Erreur lors de la mise à jour: " + e.getMessage(),
                "Erreur",
                JOptionPane.ERROR_MESSAGE);
        }
    }
} 