package main.java.ui;

import main.java.models.Abonnement;
import main.java.models.Entreprise;
import main.java.models.Journal;
import main.java.DAO.AbonnementDAO;
import main.java.DAO.JournalDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class AbonnementUI extends JFrame {
    private Entreprise entrepriseConnectee;
    private JTable tableAbonnements;
    private JComboBox<Journal> comboJournaux;
    private JButton btnSAbonner;

    public AbonnementUI(Entreprise entreprise) {
        this.entrepriseConnectee = entreprise;

        setTitle("Mes Abonnements - " + entreprise.getRaisonSociale());
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Tableau des abonnements
        tableAbonnements = new JTable();
        JScrollPane scrollPane = new JScrollPane(tableAbonnements);
        add(scrollPane, BorderLayout.CENTER);

        // Panel bas : sélection journal + bouton
        JPanel panelBas = new JPanel();
        comboJournaux = new JComboBox<>();
        btnSAbonner = new JButton("S'abonner");

        panelBas.add(new JLabel("Journal :"));
        panelBas.add(comboJournaux);
        panelBas.add(btnSAbonner);
        add(panelBas, BorderLayout.SOUTH);

        // Charger données
        chargerAbonnements();
        chargerJournaux();

        // Événement bouton
        btnSAbonner.addActionListener(e -> ajouterAbonnement());

        setVisible(true);
    }

    private void chargerAbonnements() {
        List<Abonnement> abonnements = AbonnementDAO.getAllAbonnements();
        DefaultTableModel model = new DefaultTableModel(new Object[]{"Journal", "Début", "Expiration"}, 0);

        for (Abonnement a : abonnements) {
            if (a.getEntreprise().getCodeClient() == entrepriseConnectee.getCodeClient()) {
                model.addRow(new Object[]{
                    a.getJournal().getNomJournal(),
                    a.getDateDebut(),
                    a.getDateExpiration()
                });
            }
        }
        tableAbonnements.setModel(model);
    }

    private void chargerJournaux() {
        List<Journal> journaux = JournalDAO.getAllJournals();
        comboJournaux.removeAllItems();
        for (Journal j : journaux) {
            comboJournaux.addItem(j);
        }
    }

    private void ajouterAbonnement() {
        Journal journal = (Journal) comboJournaux.getSelectedItem();
        if (journal == null) return;

        LocalDate dateDebut = LocalDate.now();
        LocalDate dateExpiration = dateDebut.plusMonths(1); // exemple : abonnement d’un mois

        Abonnement nouvelAbonnement = new Abonnement(
            0, entrepriseConnectee, journal, null, dateDebut, dateExpiration, true
        );

        if (AbonnementDAO.ajouterAbonnement(nouvelAbonnement)) {
            JOptionPane.showMessageDialog(this, "Abonnement ajouté !");
            chargerAbonnements();
        } else {
            JOptionPane.showMessageDialog(this, "Erreur lors de l’ajout.");
        }
    }

    // Pour tester l’interface indépendamment
    public static void main(String[] args) {
        Entreprise entrepriseFake = new Entreprise("lot  jamal", "07455656", "Mtedx", "fsfff");
        new AbonnementUI(entrepriseFake);
    }
}
