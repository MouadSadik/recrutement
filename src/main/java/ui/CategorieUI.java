package main.java.ui;

import javax.swing.*;
import java.awt.*;

public class CategorieUI extends JFrame {
    
    private JButton btnAjouterCategorie;
    private JButton btnSupprimerCategorie;
    private JButton btnModifierCategorie;
    private JButton btnAfficherParId;
    private JButton btnAfficherTout;
    private JButton btnRetour;

    public CategorieUI(){
        setTitle("Gestion des categories de journal");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        //creer les buttons
        btnAjouterCategorie = new JButton("Ajouter Categorie");
        btnSupprimerCategorie = new JButton("Supprimer Categorie");

    }
}
