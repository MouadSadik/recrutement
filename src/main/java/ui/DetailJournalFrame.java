package main.java.ui;

import javax.swing.*;

import main.java.DAO.CategorieJournalDAO;
import main.java.DAO.DemandeurDAO;

import java.awt.*;

public class DetailJournalFrame extends JFrame {

    public DetailJournalFrame(int codeDemandeur, int codeJournal, String nom, String langue, int idCategorie) {
        setTitle("Détail du Journal");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Labels
        JLabel nomLabel = new JLabel("Nom : " + nom);
        JLabel langueLabel = new JLabel("Langue : " + langue);
        JLabel categorieLabel = new JLabel(
                "Catégorie : " + CategorieJournalDAO.getCategorieById(idCategorie).getLibelle());

        setLayout(new GridLayout(5, 1));
        add(nomLabel);
        add(langueLabel);
        add(categorieLabel);

        JButton voirEditionsButton = new JButton("Voir les Éditions");
        voirEditionsButton.addActionListener(e -> {
            new EditionUI(codeDemandeur, codeJournal).setVisible(true);
            dispose();
        });

        JButton retourButton = new JButton("Retour");
        retourButton.addActionListener(e -> {
            new JournalUI(codeDemandeur).setVisible(true);
            dispose();
        });


        add(voirEditionsButton);
        add(retourButton);
    }
}