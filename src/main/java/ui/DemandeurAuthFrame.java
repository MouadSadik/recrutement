package main.java.ui;

// Exemple 1: Page de Connexion / Inscription
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import main.java.DAO.DemandeurDAO;
import main.java.models.Demandeur;

public class DemandeurAuthFrame extends JFrame {
    public DemandeurAuthFrame() {
        setTitle("Connexion / Inscription - Demandeur");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabs = new JTabbedPane();

        // --- Style commun ---
        Font labelFont = new Font("Arial", Font.BOLD, 14);
        Font fieldFont = new Font("Arial", Font.PLAIN, 14);
        Dimension fieldDim = new Dimension(200, 30);

        // Panel Connexion
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField loginNom = new JTextField();
        loginNom.setPreferredSize(fieldDim);
        JButton loginBtn = new JButton("Se connecter");

        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel lblNom = new JLabel("Nom:");
        lblNom.setFont(labelFont);
        loginPanel.add(lblNom, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        loginNom.setFont(fieldFont);
        loginPanel.add(loginNom, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        loginBtn.setFont(labelFont);
        loginPanel.add(loginBtn, gbc);

        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nom = loginNom.getText();
                boolean existe = DemandeurDAO.getAllDemandeurs().stream()
                        .anyMatch(d -> d.getNom().equalsIgnoreCase(nom));
                if (existe) {
                    JOptionPane.showMessageDialog(DemandeurAuthFrame.this, "Connexion réussie !");
                    Demandeur d = DemandeurDAO.getAllDemandeurs()
                            .stream().filter(x -> x.getNom().equalsIgnoreCase(loginNom.getText())).findFirst()
                            .orElse(null);
                    if (d != null) {
                        dispose(); // fermer la fenêtre actuelle
                        NavigationDemandeuar.openDashboard(d);
                    }
                } else {
                    JOptionPane.showMessageDialog(DemandeurAuthFrame.this, "Nom introuvable.", "Erreur",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Panel Inscription
        JPanel registerPanel = new JPanel(new GridBagLayout());
        JTextField nom = new JTextField();
        nom.setPreferredSize(fieldDim);
        JTextField prenom = new JTextField();
        prenom.setPreferredSize(fieldDim);
        JTextField diplome = new JTextField();
        diplome.setPreferredSize(fieldDim);
        JTextField adresse = new JTextField();
        adresse.setPreferredSize(fieldDim);
        JTextField telephone = new JTextField();
        telephone.setPreferredSize(fieldDim);
        JTextField fax = new JTextField();
        fax.setPreferredSize(fieldDim);
        JSpinner experience = new JSpinner(new SpinnerNumberModel(0, 0, 50, 1));
        experience.setPreferredSize(fieldDim);
        JTextField salaire = new JTextField();
        salaire.setPreferredSize(fieldDim);
        JButton registerBtn = new JButton("S'inscrire");

        String[] labels = { "Nom:", "Prénom:", "Diplôme:", "Adresse:", "Téléphone:", "Fax:", "Expérience (années):",
                "Salaire souhaité:" };
        Component[] fields = { nom, prenom, diplome, adresse, telephone, fax, experience, salaire };

        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0;
            gbc.gridy = i;
            JLabel l = new JLabel(labels[i]);
            l.setFont(labelFont);
            registerPanel.add(l, gbc);

            gbc.gridx = 1;
            if (fields[i] instanceof JComponent) {
                ((JComponent) fields[i]).setFont(fieldFont);
            }
            registerPanel.add(fields[i], gbc);
        }

        gbc.gridx = 1;
        gbc.gridy = labels.length;
        registerBtn.setFont(labelFont);
        registerPanel.add(registerBtn, gbc);

        registerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nomStr = nom.getText();
                boolean existe = DemandeurDAO.getAllDemandeurs().stream()
                        .anyMatch(d -> d.getNom().equalsIgnoreCase(nomStr));

                if (!existe) {
                    Demandeur demandeur = new Demandeur(0,
                            adresse.getText(),
                            telephone.getText(),
                            nomStr,
                            prenom.getText(),
                            (Integer) experience.getValue(),
                            Double.parseDouble(salaire.getText()),
                            diplome.getText());
                    boolean success = DemandeurDAO.ajouterDemandeur(demandeur);
                    if (success) {
                        JOptionPane.showMessageDialog(DemandeurAuthFrame.this, "Connexion réussie !");
                        Demandeur d = DemandeurDAO.getAllDemandeurs()
                                .stream().filter(x -> x.getNom().equalsIgnoreCase(loginNom.getText())).findFirst()
                                .orElse(null);
                        if (d != null) {
                            dispose(); // fermer la fenêtre actuelle
                            NavigationDemandeuar.openDashboard(d);
                        }

                    } else {
                        JOptionPane.showMessageDialog(DemandeurAuthFrame.this, "Erreur lors de l'inscription.",
                                "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(DemandeurAuthFrame.this, "Ce nom est déjà utilisé.", "Erreur",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        tabs.add("Connexion", loginPanel);
        tabs.add("Inscription", registerPanel);

        add(tabs);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(DemandeurAuthFrame::new);
    }
}