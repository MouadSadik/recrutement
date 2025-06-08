package main.java.ui;

import main.java.DAO.*;
import main.java.models.Edition;
//import main.java.models.OffreEmploi;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class EditionUI extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;
    private int codeJournal;

    public EditionUI(int codeDemandeur, int codeJournal) {
        this.codeJournal = codeJournal;

        setTitle("Éditions du Journal : " + JournalDAO.getJournalById(codeJournal).getNomJournal());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);

        String[] columnNames = { "Code Journal", "Numéro d'Édition", "Date de Parution" };
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton refreshButton = new JButton("Rafraîchir");
        refreshButton.addActionListener(e -> loadEditions());
        topPanel.add(refreshButton);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        loadEditions();

        table.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int selectedRow = table.getSelectedRow();

                int numEdition = (int) tableModel.getValueAt(selectedRow, 1);
                OffresEditionUI offresUI = new OffresEditionUI(codeDemandeur, codeJournal, numEdition);
                offresUI.setVisible(true);
            }
        });

    }

    private void loadEditions() {
        tableModel.setRowCount(0);
        List<Edition> all = EditionDAO.getAllEditions();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (Edition e : all) {
            if (e.getCodeJournal() == codeJournal) {
                Object[] row = {
                        e.getCodeJournal(),
                        e.getNumEdition(),
                        e.getDateParution().format(formatter)
                };
                tableModel.addRow(row);
            }
        }
    }
}
