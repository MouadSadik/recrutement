package main.java.ui;

import main.java.DAO.JournalDAO;
import main.java.models.Journal;

import javax.swing.*;
import java.awt.*;

public class AjouterJournalFrame extends JFrame {
    private JTextField nomField;
    private JTextField periodiciteField;
    private JTextField langueField;
    private JTextField categorieField;

    private JournalUI parent;

    public AjouterJournalFrame(JournalUI parent) {
        this.parent = parent;

        setTitle("Ajouter un Journal");
        setSize(300, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(5, 2, 5, 5));

        // Champs du formulaire
        add(new JLabel("Nom :"));
        nomField = new JTextField();
        add(nomField);

        add(new JLabel("Périodicité :"));
        periodiciteField = new JTextField();
        add(periodiciteField);

        add(new JLabel("Langue :"));
        langueField = new JTextField();
        add(langueField);

        add(new JLabel("ID Catégorie :"));
        categorieField = new JTextField();
        add(categorieField);

        JButton saveButton = new JButton("Enregistrer");
        saveButton.addActionListener(e -> enregistrerJournal());
        add(saveButton);

        JButton cancelButton = new JButton("Annuler");
        cancelButton.addActionListener(e -> dispose());
        add(cancelButton);
    }

    private void enregistrerJournal() {
        try {
            String nom = nomField.getText();
            String periodicite = periodiciteField.getText();
            String langue = langueField.getText();
            int idCategorie = Integer.parseInt(categorieField.getText());

            Journal nouveau = new Journal(0, nom, periodicite, langue, idCategorie);
            boolean success = JournalDAO.ajouterJournal(nouveau);

            if (success) {
                JOptionPane.showMessageDialog(this, "Journal ajouté avec succès !");
                parent.rechargerTable(); // ← méthode à créer
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Échec de l'ajout du journal.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID Catégorie doit être un nombre.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}

