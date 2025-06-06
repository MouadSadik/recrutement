package main.java.ui;

import main.java.DAO.OffreEmploiDAO;
import main.java.models.OffreEmploi;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class OffreParEditionGUI extends JFrame {

    private JTextField codeJournalField;
    private JTextField numEditionField;
    private JTable table;
    private DefaultTableModel tableModel;

    public OffreParEditionGUI() {
        setTitle("Offres d'emploi par Édition");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 400);
        setLocationRelativeTo(null);

        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Code Journal:"));
        codeJournalField = new JTextField(5);
        inputPanel.add(codeJournalField);

        inputPanel.add(new JLabel("Numéro Édition:"));
        numEditionField = new JTextField(5);
        inputPanel.add(numEditionField);

        JButton searchButton = new JButton("Rechercher");
        inputPanel.add(searchButton);

        add(inputPanel, BorderLayout.NORTH);

        // Table
        String[] columnNames = { "Num Offre", "Titre", "Compétences", "Expérience", "Postes", "État" };
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Action bouton
        searchButton.addActionListener(e -> fetchAndDisplayOffres());
    }

    private void fetchAndDisplayOffres() {
        try {
            int codeJournal = Integer.parseInt(codeJournalField.getText());
            int numEdition = Integer.parseInt(numEditionField.getText());

            List<OffreEmploi> offres = OffreEmploiDAO.getOffresParEdition(codeJournal, numEdition);

            tableModel.setRowCount(0); // clear
            for (OffreEmploi o : offres) {
                tableModel.addRow(new Object[] {
                        o.getNumOffre(),
                        o.getTitre(),
                        o.getCompetences(),
                        o.getNbAnneeExperienceDemandee(),
                        o.getNbPostes(),
                        o.getEtat()
                });
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Veuillez saisir des entiers valides.");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erreur lors de la récupération des données : " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new OffreParEditionGUI().setVisible(true);
        });
    }
}
