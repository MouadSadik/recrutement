package main.java.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

// import main.java.DAO.EntrepriseDAO;
import main.java.DAO.JournalDAO;
import main.java.models.Entreprise;
import main.java.models.Journal;

public class SelectionJournalUI extends JFrame {

    public SelectionJournalUI(Entreprise entreprise) {

        setTitle("Sélection du journal");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        List<Journal> journals = JournalDAO.getAllJournals();
        String[] columnNames = { "ID", "Nom", "Périodicité", "Langue", "ID Catégorie" };
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        for (Journal j : journals) {
            Object[] row = {
                    j.getCodeJournal(),
                    j.getNomJournal(),
                    j.getPeriodicite(),
                    j.getLangue(),
                    j.getIdCategorie()
            };
            model.addRow(row);
        }

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        JButton btnSAbonner = new JButton("S'abonner au journal sélectionné");
        JButton btnRetour = new JButton("Retour");

        btnSAbonner.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                int codeJournal = (int) model.getValueAt(selectedRow, 0);
                Journal selectedJournal = JournalDAO.getJournalById(codeJournal);
                new ChoixDureeAbonnementUI(entreprise, selectedJournal);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner un journal.");
            }
        });

         
        btnRetour.addActionListener(e -> {
            dispose();
            new AccueilEntrepriseUI();
        });

        setLayout(new BorderLayout());
        add(new JLabel("Sélectionnez un journal pour vous abonner :", JLabel.CENTER), BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(btnSAbonner, BorderLayout.SOUTH);

        setVisible(true);
    }
}
