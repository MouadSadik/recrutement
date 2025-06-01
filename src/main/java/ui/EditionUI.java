package main.java.ui;

import main.java.DAO.EditionDAO;
import main.java.models.Edition;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class EditionUI extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;
    private int codeJournal;

    public EditionUI(int codeJournal) {
        this.codeJournal = codeJournal;

        setTitle("Éditions du Journal #" + codeJournal);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 400);
        setLocationRelativeTo(null);

        String[] columnNames = {"Code Journal", "Numéro d'Édition", "Date de Parution"};
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
