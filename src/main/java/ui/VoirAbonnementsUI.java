package main.java.ui;

import main.java.DAO.AbonnementDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VoirAbonnementsUI extends JFrame {

    public VoirAbonnementsUI(int codeClient) {
        setTitle("Mes Abonnements Actifs");
        setSize(600, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] columns = {"ID Abonnement", "ID Journal", "Nom Journal", "Date Début", "Date Expiration"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        List<Object[]> abonnements = AbonnementDAO.getAbonnementsActifsParClient(codeClient);
        for (Object[] row : abonnements) {
            model.addRow(row);
        }

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }
}
