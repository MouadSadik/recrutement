package main.java.ui;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        setExtendedState(JFrame.MAXIMIZED_BOTH); // pour l'ecran


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

        // Layout
        setLayout(new GridLayout(9, 1, 10, 10));
        add(new JLabel("Welcome to Mouad Recrutement Agency:"));
        add(btnClient);
        add(btnDemandeur);
        add(btnEntreprise);
        add(btnJournal);
        add(btnCategorie);
        add(btnEdition);
        add(btnAbonnement);
        add(btnPostulation);
        add(btnRecrutement);

        // Action pour le bouton Journal
        btnJournal.addActionListener(e -> {
            JournalUI journalUI = new JournalUI();
            journalUI.setVisible(true);
        });

        // Pour l'instant, les autres boutons affichent un message (on complètera plus tard)
        btnClient.addActionListener(e -> JOptionPane.showMessageDialog(this, "Interface Client pas encore implémentée."));
        btnDemandeur.addActionListener(e -> JOptionPane.showMessageDialog(this, "Interface Demandeur pas encore implémentée."));
        btnEntreprise.addActionListener(e -> JOptionPane.showMessageDialog(this, "Interface Entreprise pas encore implémentée."));
        btnCategorie.addActionListener(e -> JOptionPane.showMessageDialog(this, "Interface Catégorie pas encore implémentée."));
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
