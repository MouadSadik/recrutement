package main.java.ui;

import javax.swing.*;
import java.awt.*;

public class DetailJournalFrame extends JFrame {

    public DetailJournalFrame(String nom, String langue, int idCategorie) {
        setTitle("Détail du Journal");
        setSize(300, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Labels
        JLabel nomLabel = new JLabel("Nom : " + nom);
        JLabel langueLabel = new JLabel("Langue : " + langue);
        JLabel categorieLabel = new JLabel("ID Catégorie : " + idCategorie);

        // Layout
        setLayout(new GridLayout(4, 1));
        add(nomLabel);
        add(langueLabel);
        add(categorieLabel);

        // Ajouter le bouton de modification
        JButton modifierButton = new JButton("Modifier");
        modifierButton.addActionListener(e -> {
            new ModifierJournalFrame(nom, langue, idCategorie).setVisible(true);
            dispose();  // Fermer la fenêtre de détail
        });

        // add(modifierButton);
    }
}
