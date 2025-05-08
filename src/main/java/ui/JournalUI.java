package main.java.ui;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import main.java.DAO.JournalDAO;
import main.java.models.Journal;

public class JournalUI extends JFrame {

    private JButton btnAjouterJournal;
    private JButton btnSupprimerJournal;
    private JButton btnModifierJournal;
    private JButton btnAfficherParId;
    private JButton btnAfficherTout;
    private JButton btnRetour;

    public JournalUI() {
        setTitle("Gestion des Journaux");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Creer les boutons
        btnAjouterJournal = new JButton("Ajouter Journal");
        btnSupprimerJournal = new JButton("Supprimer Journal");
        btnModifierJournal = new JButton("Modifier Journal");
        btnAfficherParId = new JButton("Afficher Journal par ID");
        btnAfficherTout = new JButton("Afficher tous les Journaux");
        btnRetour = new JButton("Retour");

        // Ajouter les boutons dans le layout
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        panel.add(btnAjouterJournal);
        panel.add(btnSupprimerJournal);
        panel.add(btnModifierJournal);
        panel.add(btnAfficherParId);
        panel.add(btnAfficherTout);
        panel.add(btnRetour);

        add(panel);

        // Actions des boutons
        btnAjouterJournal.addActionListener(e -> ajouterJournal());
        btnSupprimerJournal.addActionListener(e -> supprimerJournal());
        btnModifierJournal.addActionListener(e -> modifierJournal());
        btnAfficherParId.addActionListener(e -> afficherJournalParId());
        btnAfficherTout.addActionListener(e -> afficherTousLesJournaux());
        btnRetour.addActionListener(e -> {
            dispose();
            new MainUI();
        });
    }

    //Ajouter un journal
    private void ajouterJournal() {
        JTextField txtNomJournal = new JTextField();
        JTextField txtPeriodicite = new JTextField();
        JTextField txtLangue = new JTextField();
        JTextField txtIdCategorie = new JTextField();

        Object[] message = {
            "Nom du Journal:", txtNomJournal,
            "Périodicité:", txtPeriodicite,
            "Langue:", txtLangue,
            "ID Catégorie:", txtIdCategorie
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Ajouter Journal", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String nomJournal = txtNomJournal.getText();
                String periodicite = txtPeriodicite.getText();
                String langue = txtLangue.getText();
                int idCategorie = Integer.parseInt(txtIdCategorie.getText());

                Journal journal = new Journal(0, nomJournal, periodicite, langue, idCategorie);
                boolean success = JournalDAO.ajouterJournal(journal);

                if (success) {
                    JOptionPane.showMessageDialog(this, "Journal ajouté avec succès !");
                } else {
                    JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout du journal.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID catégorie invalide (entier attendu).");
            }
        }
    }

    //Supprimer un journal
    private void supprimerJournal() {
        String input = JOptionPane.showInputDialog(this, "Entrez l'ID (codeJournal) du journal à supprimer:");
        if (input != null) {
            try {
                int id = Integer.parseInt(input);
                boolean success = JournalDAO.supprimerJournal(id);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Journal supprimé avec succès !");
                } else {
                    JOptionPane.showMessageDialog(this, "Journal non trouvé ou erreur de suppression.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Veuillez entrer un ID valide (entier).");
            }
        }
    }

    //Modifier un journal
    private void modifierJournal() {
        String input = JOptionPane.showInputDialog(this, "Entrez l'ID (codeJournal) du journal à modifier:");
        if (input != null) {
            try {
                int id = Integer.parseInt(input);
                Journal journal = JournalDAO.getJournalById(id);
                if (journal != null) {
                    JTextField txtNomJournal = new JTextField(journal.getNomJournal());
                    JTextField txtPeriodicite = new JTextField(journal.getPeriodicite());
                    JTextField txtLangue = new JTextField(journal.getLangue());
                    JTextField txtIdCategorie = new JTextField(String.valueOf(journal.getIdCategorie()));

                    Object[] message = {
                        "Nom du Journal:", txtNomJournal,
                        "Périodicité:", txtPeriodicite,
                        "Langue:", txtLangue,
                        "ID Catégorie:", txtIdCategorie
                    };

                    int option = JOptionPane.showConfirmDialog(this, message, "Modifier Journal", JOptionPane.OK_CANCEL_OPTION);
                    if (option == JOptionPane.OK_OPTION) {
                        journal.setNomJournal(txtNomJournal.getText());
                        journal.setPeriodicite(txtPeriodicite.getText());
                        journal.setLangue(txtLangue.getText());
                        journal.setIdCategorie(Integer.parseInt(txtIdCategorie.getText()));

                        boolean success = JournalDAO.modifierJournal(journal);
                        if (success) {
                            JOptionPane.showMessageDialog(this, "Journal modifié avec succès !");
                        } else {
                            JOptionPane.showMessageDialog(this, "Erreur lors de la modification.");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Journal non trouvé.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Veuillez entrer un ID valide (entier).");
            }
        }
    }

    // Afficher journal par ID
    private void afficherJournalParId() {
        String input = JOptionPane.showInputDialog(this, "Entrez l'ID (codeJournal) du journal à afficher:");
        if (input != null) {
            try {
                int id = Integer.parseInt(input);
                Journal journal = JournalDAO.getJournalById(id);
                if (journal != null) {
                    JOptionPane.showMessageDialog(this, journal.toString(), "Journal trouvé", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Journal non trouvé.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Veuillez entrer un ID valide (entier).");
            }
        }
    }

    // Afficher tous les journaux
    private void afficherTousLesJournaux() {
        List<Journal> journaux = JournalDAO.getAllJournals();
        if (!journaux.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (Journal j : journaux) {
                sb.append(j.toString()).append("\n");
            }
            JTextArea textArea = new JTextArea(sb.toString());
            textArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(400, 300));

            JOptionPane.showMessageDialog(this, scrollPane, "Tous les journaux", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Aucun journal trouvé.");
        }
    }

    // Pour tester directement cette page seule
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new JournalUI().setVisible(true);
        });
    }
}
