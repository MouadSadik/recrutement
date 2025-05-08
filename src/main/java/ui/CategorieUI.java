package main.java.ui;

import javax.swing.*;

import main.java.DAO.CategorieJournalDAO;
import main.java.DAO.JournalDAO;
import main.java.models.CategorieJournal;
import main.java.models.Journal;

import java.awt.*;

public class CategorieUI extends JFrame {

    private JButton btnAjouterCategorie;
    private JButton btnSupprimerCategorie;
    private JButton btnModifierCategorie;
    private JButton btnAfficherParId;
    private JButton btnAfficherTout;
    private JButton btnRetour;

    public CategorieUI() {
        setTitle("Gestion des categories de journal");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // creer les buttons
        btnAjouterCategorie = new JButton("Ajouter Categorie");
        btnSupprimerCategorie = new JButton("Supprimer Categorie");
        btnModifierCategorie = new JButton("Modifier Categorie");
        btnAfficherParId = new JButton("Afficher Une Categorie");
        btnAfficherTout = new JButton("Afficher Tous les categories");
        btnRetour = new JButton("Retour");

        // Ajouter les boutons dans le layout
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        panel.add(btnAjouterCategorie);
        panel.add(btnSupprimerCategorie);
        panel.add(btnModifierCategorie);
        panel.add(btnAfficherParId);
        panel.add(btnAfficherTout);
        panel.add(btnRetour);

        add(panel);

        btnAjouterCategorie.addActionListener(e -> ajouterCategorie());
        btnSupprimerCategorie.addActionListener(e -> supprimerCategorie());
        btnModifierCategorie.addActionListener(e -> modifierCategorie());
        btnAfficherParId.addActionListener(e -> afficherCategorieParId());
        btnAfficherTout.addActionListener(e -> afficherToutesCategories());
        btnRetour.addActionListener((e) -> {
            dispose();
            new MainUI();
        });

    }

    // Ajouter une categorie
    private void ajouterCategorie() {
        JTextField txtLibelle = new JTextField();

        Object[] message = {
                "Libelle:", txtLibelle
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Ajouter Categorie", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String Libelle = txtLibelle.getText();

                CategorieJournal categorie = new CategorieJournal(0, Libelle);
                boolean success = CategorieJournalDAO.ajouterCategorie(categorie);

                if (success) {
                    JOptionPane.showMessageDialog(this, "Categorie ajoutée avec succès !");
                } else {
                    JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout du Categorie.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID catégorie invalide (entier attendu).");
            }
        }
    }

    private void supprimerCategorie() {
        String input = JOptionPane.showInputDialog(this, "Entrez l'ID du Categorie à supprimer:");
        if (input != null) {
            try {
                int id = Integer.parseInt(input);
                boolean success = CategorieJournalDAO.supprimerCategorie(id);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Categorie supprimée avec succès !");
                } else {
                    JOptionPane.showMessageDialog(this, "Categorie non trouvée ou erreur de suppression.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Veuillez entrer un ID valide (entier).");
            }
        }
    }

    // Modifier une categorie
    private void modifierCategorie() {
        String input = JOptionPane.showInputDialog(this, "Entrez l'ID de la catégorie à modifier:");
        if (input != null) {
            try {
                int id = Integer.parseInt(input);
                CategorieJournal categorie = CategorieJournalDAO.getCategorieById(id);
                if (categorie != null) {
                    JTextField txtLibelle = new JTextField(categorie.getLibelle());

                    Object[] message = {
                            "Nouveau libellé:", txtLibelle
                    };

                    int option = JOptionPane.showConfirmDialog(this, message, "Modifier Catégorie",
                            JOptionPane.OK_CANCEL_OPTION);
                    if (option == JOptionPane.OK_OPTION) {
                        String nouveauLibelle = txtLibelle.getText();
                        categorie.setLibelle(nouveauLibelle);

                        boolean success = CategorieJournalDAO.modifierCategorie(categorie);
                        if (success) {
                            JOptionPane.showMessageDialog(this, "Catégorie modifiée avec succès !");
                        } else {
                            JOptionPane.showMessageDialog(this, "Erreur lors de la modification de la catégorie.");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Catégorie non trouvée.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Veuillez entrer un ID valide (entier).");
            }
        }
    }

    // Afficher une categorie par ID
    private void afficherCategorieParId() {
        String input = JOptionPane.showInputDialog(this, "Entrez l'ID de la catégorie à afficher:");
        if (input != null) {
            try {
                int id = Integer.parseInt(input);
                CategorieJournal categorie = CategorieJournalDAO.getCategorieById(id);
                if (categorie != null) {
                    String info = "ID: " + categorie.getIdCategorie() + "\nLibellé: " + categorie.getLibelle();
                    JOptionPane.showMessageDialog(this, info, "Détails de la catégorie",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Catégorie non trouvée.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Veuillez entrer un ID valide (entier).");
            }
        }
    }

    // Afficher toutes les categories
    private void afficherToutesCategories() {
        java.util.List<CategorieJournal> categories = CategorieJournalDAO.getAllCategories();
        if (categories.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Aucune catégorie trouvée.");
        } else {
            StringBuilder info = new StringBuilder();
            for (CategorieJournal cat : categories) {
                info.append("ID: ").append(cat.getIdCategorie())
                        .append(", Libellé: ").append(cat.getLibelle()).append("\n");
            }
            JTextArea textArea = new JTextArea(info.toString());
            textArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(400, 300));
            JOptionPane.showMessageDialog(this, scrollPane, "Liste des catégories", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new CategorieUI().setVisible(true);
        });
    }
}
