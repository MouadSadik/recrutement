package main.java.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Optional;

import main.java.DAO.*;
import main.java.models.*;

public class LancerOffreUI extends JFrame {

    public LancerOffreUI(Entreprise entreprise) {
        setTitle("Lancer une Offre");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(7, 2, 10, 10));

        JTextField txtTitre = new JTextField();
        JTextArea txtCompetences = new JTextArea(3, 20);
        JTextField txtExperience = new JTextField();
        JTextField txtNbPostes = new JTextField();

        // Liste des journaux
        List<Journal> journaux = JournalDAO.getAllJournals();
        JComboBox<Journal> comboJournaux = new JComboBox<>(journaux.toArray(new Journal[0]));

        JButton btnPublier = new JButton("Publier l'offre");

        panel.add(new JLabel("Journal :")); panel.add(comboJournaux);
        panel.add(new JLabel("Titre de l'offre :")); panel.add(txtTitre);
        panel.add(new JLabel("Compétences :")); panel.add(new JScrollPane(txtCompetences));
        panel.add(new JLabel("Années d'expérience :")); panel.add(txtExperience);
        panel.add(new JLabel("Nombre de postes :")); panel.add(txtNbPostes);
        panel.add(new JLabel()); panel.add(btnPublier);

        add(panel);

        btnPublier.addActionListener((ActionEvent e) -> {
            try {
                Journal selectedJournal = (Journal) comboJournaux.getSelectedItem();
                if (selectedJournal == null) {
                    JOptionPane.showMessageDialog(this, "Veuillez sélectionner un journal.");
                    return;
                }

                // Vérifier l'abonnement
                List<Abonnement> abonnements = AbonnementDAO.getAbonnementsParClient(entreprise.getCodeClient());
                Optional<Abonnement> abonnementOpt = abonnements.stream()
                        .filter(ab -> ab.getCodeJournal() == selectedJournal.getCodeJournal())
                        .findFirst();

                if (!abonnementOpt.isPresent()) {
                    JOptionPane.showMessageDialog(this, "Vous n'êtes pas abonné à ce journal.");
                    return;
                }

                Abonnement abonnement = abonnementOpt.get();

                // Récupération de la dernière édition du journal sélectionné
                Edition derniereEdition = EditionDAO.getAllEditions().stream()
                        .filter(e1 -> e1.getCodeJournal() == selectedJournal.getCodeJournal())
                        .max((e1, e2) -> e1.getDateParution().compareTo(e2.getDateParution()))
                        .orElse(null);

                if (derniereEdition == null) {
                    JOptionPane.showMessageDialog(this, "Aucune édition trouvée pour ce journal.");
                    return;
                }

                // Création de l'offre
                OffreEmploi offre = new OffreEmploi(
                        0,
                        txtTitre.getText().trim(),
                        txtCompetences.getText().trim(),
                        Integer.parseInt(txtExperience.getText().trim()),
                        Integer.parseInt(txtNbPostes.getText().trim()),
                        OffreEmploi.EtatOffre.ACTIVE,
                        derniereEdition,
                        abonnement
                );

                OffreEmploiDAO.addOffreEmploi(offre);
                JOptionPane.showMessageDialog(this, "Offre publiée avec succès !");
                dispose();

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erreur lors de la publication : " + ex.getMessage());
            }
        });

        setVisible(true);
    }
}
