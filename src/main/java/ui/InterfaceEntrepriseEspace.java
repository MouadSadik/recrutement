package main.java.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import main.java.models.Entreprise;
import main.java.models.OffreEmploi;
import main.java.models.OffreEmploi.EtatOffre;
import main.java.DAO.EntrepriseDAO;
import main.java.DAO.OffreEmploiDAO;

public class InterfaceEntrepriseEspace extends JFrame {

    public InterfaceEntrepriseEspace(String raisonSociale) {
        setTitle("Espace Entreprise");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);

        // Vérifier la validité du nom de l’entreprise
        if (raisonSociale == null || raisonSociale.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nom de l'entreprise manquant !");
            dispose();
            return;
        }

        // Récupérer l’entreprise
        Entreprise entreprise = EntrepriseDAO.getEntrepriseParNom(raisonSociale.trim());
        if (entreprise == null) {
            JOptionPane.showMessageDialog(this, "Entreprise non trouvée !");
            dispose();
            return;
        }

        // Panel principal
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        //  Haut : Infos entreprise + boutons
        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setBorder(BorderFactory.createTitledBorder("Informations Entreprise"));

        // Infos entreprise (à gauche)
        JPanel infos = new JPanel(new GridLayout(3, 1));
        infos.add(new JLabel(" Raison sociale : " + entreprise.getRaisonSociale()));
        infos.add(new JLabel(" Activités : " + entreprise.getDescriptionActivite()));
        infos.add(new JLabel(" Abonnements actifs : " + entreprise.getAbonnements()));

        // Boutons (à droite)
        JPanel boutonsPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        JButton btnCreerOffre = new JButton("Créer Offre");
        JButton btnGererCandidats = new JButton("Gérer Candidatures");

        // ➡ Actions des boutons
        btnCreerOffre.addActionListener(e -> {
            dispose();
            new OffreEmploiFormUI(); // On passe l’objet Entreprise pour pré-remplir
        });

        btnGererCandidats.addActionListener(e -> {
            dispose();
            new GestionCandidatsUI(entreprise.getRaisonSociale());
        });

        boutonsPanel.add(btnCreerOffre);
        boutonsPanel.add(btnGererCandidats);

        infoPanel.add(infos, BorderLayout.CENTER);
        infoPanel.add(boutonsPanel, BorderLayout.EAST);

        //  Centre : tableau des offres
        JPanel offresPanel = new JPanel(new BorderLayout());
        offresPanel.setBorder(BorderFactory.createTitledBorder(" Offres publiées"));

        String[] colonnes = {"Titre", "Compétences", "État"};
        DefaultTableModel model = new DefaultTableModel(colonnes, 0);

        List<OffreEmploi> offres = OffreEmploiDAO.getOffresParEntreprise(entreprise.getCodeClient());
        for (OffreEmploi offre : offres) {
            model.addRow(new Object[]{
                offre.getTitre(),
                offre.getCompetences(),
                offre.getEtat() == EtatOffre.ACTIVE ? "Active" : "Désactivée"
            });
        }

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        offresPanel.add(scrollPane, BorderLayout.CENTER);

        //  Bas : Bouton retour
        JPanel basPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnRetour = new JButton("Retour");
        btnRetour.addActionListener(e -> {
            dispose();
            new ConnexionEntrepriseUI();
        });
        basPanel.add(btnRetour);

        // Ajout des panels au panel principal
        panel.add(infoPanel, BorderLayout.NORTH);
        panel.add(offresPanel, BorderLayout.CENTER);
        panel.add(basPanel, BorderLayout.SOUTH);

        add(panel);
        setVisible(true);
    }
}
