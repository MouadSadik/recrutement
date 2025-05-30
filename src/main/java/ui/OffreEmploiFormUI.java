package main.java.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.List;

import main.java.DAO.AbonnementDAO;
import main.java.DAO.EditionDAO;
import main.java.DAO.OffreEmploiDAO;
import main.java.models.*;
import main.java.models.OffreEmploi.EtatOffre;

public class OffreEmploiFormUI extends JFrame {

    private JTextField titreField;
    private JTextArea competencesArea;
    private JTextField nbAnneesExpField;
    private JTextField nbPostesField;
    private JComboBox<EtatOffre> etatComboBox;
    private JComboBox<Abonnement> abonnementComboBox;
    private JComboBox<Edition> editionComboBox;
    private JButton ajouterButton;

    public OffreEmploiFormUI() {
        setTitle("Ajouter une offre d'emploi");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initUI();
        setVisible(true);
    }

    private void initUI() {
        JPanel panel = new JPanel(new GridLayout(8, 2, 10, 10));

        panel.add(new JLabel("Titre :"));
        titreField = new JTextField();
        panel.add(titreField);

        panel.add(new JLabel("Compétences :"));
        competencesArea = new JTextArea(3, 20);
        panel.add(new JScrollPane(competencesArea));

        panel.add(new JLabel("Années d'expérience :"));
        nbAnneesExpField = new JTextField();
        panel.add(nbAnneesExpField);

        panel.add(new JLabel("Nombre de postes :"));
        nbPostesField = new JTextField();
        panel.add(nbPostesField);

        panel.add(new JLabel("État :"));
        etatComboBox = new JComboBox<>(EtatOffre.values());
        panel.add(etatComboBox);

        panel.add(new JLabel("Abonnement :"));
        abonnementComboBox = new JComboBox<>();
        chargerAbonnements();
        panel.add(abonnementComboBox);

        panel.add(new JLabel("Édition :"));
        editionComboBox = new JComboBox<>();
        chargerEditions();
        panel.add(editionComboBox);

        ajouterButton = new JButton("Ajouter");
        ajouterButton.addActionListener(e -> ajouterOffreEmploi());
        panel.add(ajouterButton);

        add(panel);
    }

    private void chargerAbonnements() {
        List<Abonnement> abonnements = AbonnementDAO.getAllAbonnements();
        for (Abonnement ab : abonnements) {
            abonnementComboBox.addItem(ab);
        }
    }

    private void chargerEditions() {
        List<Edition> editions = EditionDAO.getAllEditions();
        for (Edition ed : editions) {
            editionComboBox.addItem(ed);
        }
    }

    private void ajouterOffreEmploi() {
        try {
            String titre = titreField.getText();
            String competences = competencesArea.getText();
            int nbAnnees = Integer.parseInt(nbAnneesExpField.getText());
            int nbPostes = Integer.parseInt(nbPostesField.getText());
            EtatOffre etat = (EtatOffre) etatComboBox.getSelectedItem();
            Abonnement abonnement = (Abonnement) abonnementComboBox.getSelectedItem();
            Edition edition = (Edition) editionComboBox.getSelectedItem();

            OffreEmploi offre = new OffreEmploi(
                    0, titre, competences, nbAnnees, nbPostes, etat, edition, abonnement);

            OffreEmploiDAO.addOffreEmploi(offre);

            JOptionPane.showMessageDialog(this, "Offre d'emploi ajoutée avec succès !");
            clearFields();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout : " + ex.getMessage());
        }
    }

    private void clearFields() {
        titreField.setText("");
        competencesArea.setText("");
        nbAnneesExpField.setText("");
        nbPostesField.setText("");
        etatComboBox.setSelectedIndex(0);
        abonnementComboBox.setSelectedIndex(0);
        editionComboBox.setSelectedIndex(0);
    }

    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(OffreEmploiFormUI::new);
    }
}
