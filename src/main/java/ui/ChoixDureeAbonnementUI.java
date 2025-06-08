package main.java.ui;

import javax.swing.*;
import java.awt.*;
import main.java.DAO.AbonnementDAO;
import main.java.models.Entreprise;
import main.java.models.Journal;

public class ChoixDureeAbonnementUI extends JFrame {

    public ChoixDureeAbonnementUI(Entreprise entreprise, Journal journal) {
        setTitle("Choix de la durée d'abonnement");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JLabel label = new JLabel("Choisissez le nombre de mois d'abonnement pour : " + journal.getNomJournal());
        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(1, 1, 36, 1); // de 1 à 36 mois
        JSpinner spinner = new JSpinner(spinnerModel);
        JButton btnConfirmer = new JButton("Confirmer l'abonnement");

        btnConfirmer.addActionListener(e -> {
            int nbrMois = (int) spinner.getValue();
            boolean success = AbonnementDAO.ajouterAbonnement(entreprise.getCodeClient(), journal.getCodeJournal(),
                    nbrMois);

            if (success) {
                JOptionPane.showMessageDialog(this, "Abonnement ajouté avec succès !");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout de l'abonnement.");
            }
        });

        setLayout(new GridLayout(3, 1, 10, 10));
        add(label);
        add(spinner);
        add(btnConfirmer);

        setVisible(true);
    }
}
