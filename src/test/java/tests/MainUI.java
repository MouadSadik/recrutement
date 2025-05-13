package test.java.tests;

import javax.swing.*;

import java.awt.*;

public class MainUI extends JFrame {

    private JButton btnClient;
    private JButton btnDemandeur;
    private JButton btnEntreprise;
    private JButton btnJournal;
    private JButton btnCategorie;
    private JButton btnEdition;
    private JButton btnAbonnement;
    private JButton btnPostulation;
    private JButton btnRecrutement;

    public MainUI() {
        setTitle("Agence de Recrutement - Menu Principal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Mettre plein écran

        // Crée les boutons
        btnClient = new JButton("Client");
        btnDemandeur = new JButton("Demandeur");
        btnEntreprise = new JButton("Entreprise");
        btnJournal = new JButton("Journal");
        btnCategorie = new JButton("Catégorie");
        btnEdition = new JButton("Édition");
        btnAbonnement = new JButton("Abonnement");
        btnPostulation = new JButton("Postulation");
        btnRecrutement = new JButton("Recrutement");

        // Crée le panel avec le layout
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(10, 1, 10, 10)); // 10 lignes (titre + 9 boutons)
        panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50)); // margin

        // Ajoute le titre et les boutons au panel
        JLabel lblTitle = new JLabel("Welcome to Mouad Recrutement Agency:", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(lblTitle);

        panel.add(btnClient);
        panel.add(btnDemandeur);
        panel.add(btnEntreprise);
        panel.add(btnJournal);
        panel.add(btnCategorie);
        panel.add(btnEdition);
        panel.add(btnAbonnement);
        panel.add(btnPostulation);
        panel.add(btnRecrutement);

        // Ajoute le panel à la JFrame
        add(panel);

        // Actions des boutons
        btnJournal.addActionListener(e -> {
            JournalUI journalUI = new JournalUI();
            journalUI.setVisible(true);
        });

        btnCategorie.addActionListener(e -> {
            CategorieUI categorieUI = new CategorieUI();
            categorieUI.setVisible(true);
        });

        btnClient.addActionListener(e -> JOptionPane.showMessageDialog(this, "Interface Client pas encore implémentée."));
        btnDemandeur.addActionListener(e -> JOptionPane.showMessageDialog(this, "Interface Demandeur pas encore implémentée."));
        btnEntreprise.addActionListener(e -> JOptionPane.showMessageDialog(this, "Interface Entreprise pas encore implémentée."));
        //btnCategorie.addActionListener(e -> JOptionPane.showMessageDialog(this, "Interface Catégorie pas encore implémentée."));
        btnEdition.addActionListener(e -> JOptionPane.showMessageDialog(this, "Interface Édition pas encore implémentée."));
        btnAbonnement.addActionListener(e -> JOptionPane.showMessageDialog(this, "Interface Abonnement pas encore implémentée."));
        btnPostulation.addActionListener(e -> JOptionPane.showMessageDialog(this, "Interface Postulation pas encore implémentée."));
        btnRecrutement.addActionListener(e -> JOptionPane.showMessageDialog(this, "Interface Recrutement pas encore implémentée."));

        setVisible(true);
    }

    public static void main(String[] args) {
        new MainUI();
    }
}
