package main.java.ui;

import main.java.DAO.JournalDAO;
import main.java.DAO.CategorieJournalDAO;
import main.java.DAO.DemandeurDAO;
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
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 
        
        // Colonnes
        String[] columnNames = { "Code", "Nom", "Périodicité", "Langue", "Catégorie" };

        // Modèle de tableau
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        JButton rechercherButton = new JButton("Rechercher un journal par ID");
        rechercherButton.addActionListener(e -> {
            String idStr = JOptionPane.showInputDialog(this, "Entrez l'ID du journal :");
            if (idStr != null && !idStr.isEmpty()) {
                try {
                    int id = Integer.parseInt(idStr);
                    Journal journal = JournalDAO.getJournalById(id);
                    if (journal != null) {
                        new DetailJournalFrame(codeDemandeur, journal.getCodeJournal(), journal.getNomJournal(),
                                journal.getLangue(), journal.getIdCategorie()).setVisible(true);

                                dispose();
                    } else {
                        JOptionPane.showMessageDialog(this, "Aucun journal trouvé avec cet ID.", "Erreur",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "L'ID doit être un nombre entier.", "Erreur",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));


        topPanel.add(rechercherButton);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        
        JButton retourButton = new JButton("Retour");
        retourButton.setPreferredSize(new Dimension(250, 60));
        retourButton.addActionListener(e -> {
            new DemandeurDashboardUI(DemandeurDAO.getDemandeurById(codeDemandeur)).setVisible(true);
            dispose();
        });

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        bottomPanel.add(retourButton);
        add(bottomPanel, BorderLayout.SOUTH);

        // Ajouter les données
        chargerJournaux();

        
        add(scrollPane, BorderLayout.CENTER);

        table.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int selectedRow = table.getSelectedRow();

                int code = Integer.parseInt(table.getValueAt(selectedRow, 0).toString());
                String nom = table.getValueAt(selectedRow, 1).toString();
                String langue = table.getValueAt(selectedRow, 3).toString();

                // CategorieJournalDAO.getCategorieByIdJournal(code);
                new DetailJournalFrame(codeDemandeur, code, nom, langue,
                        CategorieJournalDAO.getCategorieByIdJournal(code)).setVisible(true);
                dispose();
            }
        });


    }

    public void rechargerTable() {
        tableModel.setRowCount(0);
        chargerJournaux(); 
    }

    private void chargerJournaux() {
        List<Journal> journaux = JournalDAO.getAllJournals();
        for (Journal j : journaux) {
            Object[] row = {
                    j.getCodeJournal(),
                    j.getNomJournal(),
                    j.getPeriodicite(),
                    j.getLangue(),
                    CategorieJournalDAO.getCategorieById(j.getIdCategorie()).getLibelle()
            };
            tableModel.addRow(row);
        }

    }

}
