package main.java.ui;

import main.java.DAO.EditionDAO;
import main.java.DAO.OffreEmploiDAO;
import main.java.models.Edition;
import main.java.models.OffreEmploi;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class OffresEditionUI extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;

    public OffresEditionUI(int codeDemandeur, int codeJournal, int numEdition) {
        Edition edition = EditionDAO.getEditionById(codeJournal, numEdition);
        setTitle("Offres d'emploi pour l'édition : " + edition.getNumEdition());
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] columnNames = { "numOffre", "Titre", "État", "Competences", "Nbr Annee Experience Demandee" };
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        add(scrollPane, BorderLayout.CENTER);

        loadOffres(codeJournal, numEdition);

        table.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int selectedRow = table.getSelectedRow();
                int numOffre = (Integer) tableModel.getValueAt(selectedRow, 0);

                // Utiliser DAO pour récupérer l'offre complète
                try {
                    OffreEmploi offre = OffreEmploiDAO.getOffreEmploiById(numOffre);
                    if (offre != null) {

                        new DetailOffreUI(codeDemandeur, codeJournal, numEdition, numOffre).setVisible(true);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Erreur lors du chargement de l'offre.");
                }
            }
        });

    }

    private void loadOffres(int codeJournal, int numEdition) {
        tableModel.setRowCount(0);
        try {
            List<OffreEmploi> offres = OffreEmploiDAO.getOffresParEdition(codeJournal, numEdition);
            for (OffreEmploi offre : offres) {
                Object[] row = {
                        offre.getNumOffre(),
                        offre.getTitre(),
                        offre.getEtat(),
                        offre.getCompetences(),
                        offre.getNbAnneeExperienceDemandee()
                };
                tableModel.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement des offres : " + e.getMessage());
        }
    }
}
