package test.java.tests;

import javax.swing.*;
import java.awt.*;

public class TestUI extends JFrame {

    public TestUI() {
        setTitle("RecrutAg - Menu Principal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(600, 400);

        // Titre
        JLabel lblTitle = new JLabel("Bonjour, Welcome à RecrutAg", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 22));

        // Boutons
        JButton btnEspaceEmploye = new JButton("Espace Employé");
        JButton btnEspaceEntreprise = new JButton("Espace Entreprise");

        btnEspaceEmploye.setPreferredSize(new Dimension(200, 50));
        btnEspaceEntreprise.setPreferredSize(new Dimension(200, 50));

        // Panel boutons
        JPanel panelButtons = new JPanel();
        panelButtons.add(btnEspaceEmploye);
        panelButtons.add(btnEspaceEntreprise);

        // Layout principal
        setLayout(new BorderLayout());
        add(lblTitle, BorderLayout.NORTH);
        add(panelButtons, BorderLayout.CENTER);

        // Actions
        btnEspaceEmploye.addActionListener(e -> ouvrirEspaceEmploye());
        btnEspaceEntreprise.addActionListener(e -> ouvrirEspaceEntreprise());

        setVisible(true);
    }

    private void ouvrirEspaceEmploye() {
        JFrame employeFrame = new JFrame("Espace Employé");
        employeFrame.setSize(400, 300);
        employeFrame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JButton("Créer Compte"));
        panel.add(new JButton("Voir les offres"));
        panel.add(new JButton("Postuler"));

        employeFrame.add(panel);
        employeFrame.setVisible(true);
    }

    private void ouvrirEspaceEntreprise() {
        JFrame entrepriseFrame = new JFrame("Espace Entreprise");
        entrepriseFrame.setSize(400, 300);
        entrepriseFrame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JButton("S’abonner"));
        panel.add(new JButton("Ajouter offre"));
        panel.add(new JButton("Recruter"));
        panel.add(new JButton("Voir les offres"));

        entrepriseFrame.add(panel);
        entrepriseFrame.setVisible(true);
    }

    public static void main(String[] args) {
        new TestUI();
    }
}
