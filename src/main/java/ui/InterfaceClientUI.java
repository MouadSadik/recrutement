package main.java.ui;

import javax.swing.*;
import java.awt.*;

public class InterfaceClientUI extends JFrame {

    public InterfaceClientUI() {
        setTitle("Espace Client");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabs = new JTabbedPane();

        tabs.add("Formulaire d'Abonnement", creerPanelFormulaireAbonnement());
        tabs.add("Mes Abonnements", creerPanelListeAbonnements());
        tabs.add("Profil Client", creerPanelProfilClient());

        add(tabs);
    }

    private JPanel creerPanelFormulaireAbonnement() {
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Nouveau Abonnement"));

        JComboBox<String> journalBox = new JComboBox<>(new String[] { "Le Monde", "TechMag", "Business Weekly" });
        JTextField dureeField = new JTextField();
        JButton boutonSAbonner = new JButton("S'abonner");

        panel.add(new JLabel("Journal :"));
        panel.add(journalBox);
        panel.add(new JLabel("Durée (mois) :"));
        panel.add(dureeField);
        panel.add(new JLabel(""));
        panel.add(boutonSAbonner);

        boutonSAbonner.addActionListener(e -> {
            String journal = (String) journalBox.getSelectedItem();
            String duree = dureeField.getText();
            JOptionPane.showMessageDialog(panel,
                    "Abonnement au journal '" + journal + "' pour " + duree + " mois ajouté !");
        });

        return panel;
    }

    private JPanel creerPanelListeAbonnements() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Mes Abonnements"));

        String[] colonnes = { "Journal", "Date Début", "Date Expiration", "État" };
        String[][] data = {
                { "Le Monde", "2024-01-01", "2024-06-30", "Actif" },
                { "TechMag", "2023-01-01", "2023-06-30", "Expiré" },
        };

        JTable table = new JTable(data, colonnes);
        JScrollPane scrollPane = new JScrollPane(table);

        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    private JPanel creerPanelProfilClient() {
        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Profil Client"));

        JTextField nomField = new JTextField("Ali");
        JTextField adresseField = new JTextField("Casablanca");
        JTextField telephoneField = new JTextField("0612345678");
        JTextField emailField = new JTextField("ali@example.com");

        JButton modifierBtn = new JButton("Modifier");

        panel.add(new JLabel("Nom :"));
        panel.add(nomField);
        panel.add(new JLabel("Adresse :"));
        panel.add(adresseField);
        panel.add(new JLabel("Téléphone :"));
        panel.add(telephoneField);
        panel.add(new JLabel("Email :"));
        panel.add(emailField);
        panel.add(new JLabel(""));
        panel.add(modifierBtn);

        modifierBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(panel, "Profil mis à jour !");
        });

        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new InterfaceClientUI().setVisible(true));
    }
}
