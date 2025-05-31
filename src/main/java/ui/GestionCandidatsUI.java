package main.java.ui;

import main.java.DAO.*;
import main.java.models.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class GestionCandidatsUI extends JFrame {

    private String entrepriseNom;

    public GestionCandidatsUI(String entrepriseNom) {
        this.entrepriseNom = entrepriseNom;
        setTitle("Gestion des Candidats");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JTabbedPane tabs = new JTabbedPane();

        tabs.addTab(" Postulations", creerPanelPostulations());
        tabs.addTab(" Recruter", creerPanelRecrutement());
        tabs.addTab(" Historique", creerPanelHistorique());

        add(tabs, BorderLayout.CENTER);
        setVisible(true);
    }

    private JPanel creerPanelPostulations() {
        JPanel panel = new JPanel(new BorderLayout());

        String[] colonnes = {"Nom", "Prenom", "Offre", "Edition", "Date Postulation"};
        DefaultTableModel model = new DefaultTableModel(colonnes, 0);

        try {
            List<Postulation> postulations = PostulationDAO.getAllPostulations();
            for (Postulation p : postulations) {
                if (p.getOffreEmploi().getAbonnement().getEntreprise().getRaisonSociale().equals(entrepriseNom)) {
                    model.addRow(new Object[]{
                        p.getDemandeur().getNom(),
                        p.getDemandeur().getPrenom(),
                        p.getOffreEmploi().getTitre(),
                        p.getEdition().toString(),
                        p.getDatePostulation()
                    });
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        JTable table = new JTable(model);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        return panel;
    }

    private JPanel creerPanelRecrutement() {
        JPanel panel = new JPanel(new BorderLayout());

        String[] colonnes = {"Nom", "Prenom", "Offre", "Edition", "Date Postulation"};
        DefaultTableModel model = new DefaultTableModel(colonnes, 0);
        JTable table = new JTable(model);

        try {
            List<Postulation> postulations = PostulationDAO.getAllPostulations();
            for (Postulation p : postulations) {
                if (p.getOffreEmploi().getAbonnement().getEntreprise().getRaisonSociale().equals(entrepriseNom)) {
                    model.addRow(new Object[]{
                        p.getDemandeur().getNom(),
                        p.getDemandeur().getPrenom(),
                        p.getOffreEmploi().getTitre(),
                        p.getEdition().toString(),
                        p.getDatePostulation()
                    });
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        JButton btnRecruter = new JButton("Recruter");
        btnRecruter.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                try {
                    Demandeur d = DemandeurDAO.getDemandeurByNom((String) table.getValueAt(row, 1));
                    OffreEmploi o = OffreEmploiDAO.getOffreEmploiByTitre((String) table.getValueAt(row, 2));
                    RecrutementDAO.addRecrutement(new Recrutement(0, new java.sql.Date(System.currentTimeMillis()), d, o));
                    JOptionPane.showMessageDialog(this, "Candidat recruté !");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Sélectionnez une ligne.");
            }
        });

        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        panel.add(btnRecruter, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel creerPanelHistorique() {
        JPanel panel = new JPanel(new BorderLayout());

        String[] colonnes = {"Nom", "Email", "Offre", "Date Recrutement"};
        DefaultTableModel model = new DefaultTableModel(colonnes, 0);

        try {
            List<Recrutement> recrutements = RecrutementDAO.getAllRecrutements();
            for (Recrutement r : recrutements) {
                if (r.getOffreEmploi().getAbonnement().getEntreprise().getRaisonSociale().equals(entrepriseNom)) {
                    model.addRow(new Object[]{
                        r.getDemandeur().getNom(),
                        r.getDemandeur().getPrenom(),
                        r.getOffreEmploi().getTitre(),
                        r.getDateRecrutement()
                    });
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        JTable table = new JTable(model);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        return panel;
    }

    

    public static void main(String[] args) {
        new GestionCandidatsUI("TechCorp");
    }
}
