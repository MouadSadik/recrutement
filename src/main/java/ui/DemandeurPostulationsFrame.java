package main.java.ui;

import main.java.DAO.PostulationDAO;
import main.java.models.Postulation;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;
import java.sql.SQLException;

public class DemandeurPostulationsFrame extends JFrame {
    private final int codeClient;
    private JTable postulationsTable;
    private DefaultTableModel tableModel;

    public DemandeurPostulationsFrame(int codeClient) {
        this.codeClient = codeClient;
        
        setTitle("Historique des postulations");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        initComponents();
        loadPostulations();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // Create table model
        String[] columns = {"ID", "Offre", "Date de postulation", "État de l'offre"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        // Create and configure table
        postulationsTable = new JTable(tableModel);
        postulationsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        postulationsTable.getColumnModel().getColumn(1).setPreferredWidth(300);
        
        // Add components
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.add(new JLabel("Mes postulations", SwingConstants.CENTER), BorderLayout.CENTER);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JScrollPane scrollPane = new JScrollPane(postulationsTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        
        // Add refresh button
        JButton refreshButton = new JButton("Rafraîchir");
        refreshButton.addActionListener(e -> loadPostulations());
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(refreshButton);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        
        add(headerPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadPostulations() {
        // Clear existing rows
        tableModel.setRowCount(0);
        
        try {
            // Get postulations for the current user
            List<Postulation> postulations = PostulationDAO.getAllPostulationsByDemandeur(codeClient);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            
            // Add postulations to table
            for (Postulation postulation : postulations) {
                Object[] row = {
                    postulation.getIdPostulation(),
                    postulation.getOffreEmploi().getTitre(),
                    dateFormat.format(postulation.getDatePostulation()),
                    postulation.getOffreEmploi().getEtat().toString()
                };
                tableModel.addRow(row);
            }
            
            // Add double-click listener to show offer details
            postulationsTable.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    if (evt.getClickCount() == 2) {
                        int row = postulationsTable.getSelectedRow();
                        if (row != -1) {
                            Postulation postulation = postulations.get(row);
                            new OffreEmploiDetailFrame(
                                postulation.getOffreEmploi().getNumOffre(),
                                codeClient
                            ).setVisible(true);
                        }
                    }
                }
            });
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                "Erreur lors du chargement des postulations: " + e.getMessage(),
                "Erreur",
                JOptionPane.ERROR_MESSAGE);
        }
    }
} 