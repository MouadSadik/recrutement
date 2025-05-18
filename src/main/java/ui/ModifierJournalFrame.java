package main.java.ui;

import main.java.DAO.JournalDAO;
import main.java.models.Journal;

import javax.swing.*;
import java.awt.*;

public class ModifierJournalFrame extends JFrame {
    private JTextField nomField;
    private JTextField periodiciteField;
    private JTextField langueField;
    private JTextField categorieField;
    private int idCategorie;

    public ModifierJournalFrame(String nom, String langue, int idCategorie) {
        setTitle("Modifier le Journal");
        setSize(300, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(5, 2, 5, 5));

        // Sauvegarde de l'ID de la catégorie
        this.idCategorie = idCategorie;

        // Champs du formulaire pré-rempli
        add(new JLabel("Nom :"));
        nomField = new JTextField(nom);
        add(nomField);

        add(new JLabel("Périodicité :"));
        periodiciteField = new JTextField();
        add(periodiciteField);

        add(new JLabel("Langue :"));
        langueField = new JTextField(langue);
        add(langueField);

        add(new JLabel("ID Catégorie :"));
        categorieField = new JTextField(String.valueOf(idCategorie));
        add(categorieField);

        // Bouton pour sauvegarder la modification
        JButton saveButton = new JButton("Enregistrer");
        saveButton.addActionListener(e -> enregistrerModification());
        add(saveButton);

        JButton cancelButton = new JButton("Annuler");
        cancelButton.addActionListener(e -> dispose());
        add(cancelButton);
    }

    private void enregistrerModification() {
        try {
            String nom = nomField.getText();
            String periodicite = periodiciteField.getText();
            String langue = langueField.getText();
            int idCategorie = Integer.parseInt(categorieField.getText());

            Journal journalModifie = new Journal(0, nom, periodicite, langue, idCategorie);
            boolean success = JournalDAO.modifierJournal(journalModifie);

            if (success) {
                JOptionPane.showMessageDialog(this, "Journal modifié avec succès !");
                dispose();  // Fermer la fenêtre de modification
            } else {
                JOptionPane.showMessageDialog(this, "Échec de la modification du journal.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "L'ID Catégorie doit être un nombre.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}
