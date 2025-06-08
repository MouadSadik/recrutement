package main.java.ui;

import main.java.DAO.JournalDAO;
import main.java.DAO.CategorieJournalDAO;
import main.java.models.Journal;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class JournalUI extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;

    public JournalUI(int codeDemandeur) {
        
        setTitle("Liste des Journaux");
        // setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 400);
        setLocationRelativeTo(null); // Centrer la fenêtre

        // Colonnes
        String[] columnNames = {"Code", "Nom", "Périodicité", "Langue", "Catégorie"};

        // Modèle de tableau
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // JButton ajouterButton = new JButton("Ajouter un journal");
        // ajouterButton.addActionListener(e -> {
        //     new AjouterJournalFrame(this).setVisible(true);
        // });

        JButton rechercherButton = new JButton("Rechercher un journal par ID");
            rechercherButton.addActionListener(e -> {
                String idStr = JOptionPane.showInputDialog(this, "Entrez l'ID du journal :");
                if (idStr != null && !idStr.isEmpty()) {
                    try {
                        int id = Integer.parseInt(idStr);
                        Journal journal = JournalDAO.getJournalById(id);
                        if (journal != null) {
                            new DetailJournalFrame(codeDemandeur, journal.getCodeJournal(),journal.getNomJournal(), journal.getLangue(), journal.getIdCategorie()).setVisible(true);
                            
                        } else {
                            JOptionPane.showMessageDialog(this, "Aucun journal trouvé avec cet ID.", "Erreur", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "L'ID doit être un nombre entier.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));


        // topPanel.add(ajouterButton);

        // Ajout bouton dans le panneau supérieur

        topPanel.add(rechercherButton);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Ajouter les données
        chargerJournaux();

        // Layout
        add(scrollPane, BorderLayout.CENTER);

        // Listener de clic sur la ligne
        // Listener de clic sur la ligne
        table.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int selectedRow = table.getSelectedRow();

                // Extract values from selected row
                int code = Integer.parseInt(table.getValueAt(selectedRow, 0).toString()); // Fix here
                String nom = table.getValueAt(selectedRow, 1).toString();
                String langue = table.getValueAt(selectedRow, 3).toString();
                // String Categorie = table.getValueAt(selectedRow, 4).toString();

                // Ouvre la fenêtre de détail
                // CategorieJournalDAO.getCategorieByIdJournal(code);
                new DetailJournalFrame(codeDemandeur, code, nom, langue, CategorieJournalDAO.getCategorieByIdJournal(code)).setVisible(true);
            }
        });

    }

    public void rechargerTable() {
        tableModel.setRowCount(0); // Vide la table
        chargerJournaux();         // Recharge à partir de la base
    }


    private void chargerJournaux() {
        List<Journal> journaux = JournalDAO.getAllJournals();
        for (Journal j : journaux) {
            Object[] row = {
                j.getCodeJournal(),
                j.getNomJournal(),
                j.getPeriodicite(),
                j.getLangue(),
                // j.getIdCategorie()
                CategorieJournalDAO.getCategorieById(j.getIdCategorie()).getLibelle()
            };
            tableModel.addRow(row);
        }
   
        

        


}

    
}
