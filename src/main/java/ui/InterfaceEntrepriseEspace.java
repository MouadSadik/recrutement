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
        setSize(800, 600);
        setLocationRelativeTo(null);

        // 🔎 Debug : afficher le nom reçu
        System.out.println("Raison sociale reçue : " + raisonSociale);

        if (raisonSociale == null || raisonSociale.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nom de l'entreprise manquant !");
            dispose();
            return;
        }

        Entreprise entreprise = EntrepriseDAO.getEntrepriseParNom(raisonSociale.trim());
        if (entreprise == null) {
            JOptionPane.showMessageDialog(this, "Entreprise non trouvée");
            dispose();
            return;
        }

        // Panel principal
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 🔷 Haut : Infos entreprise
        JPanel infoPanel = new JPanel(new GridLayout(3, 1));
        infoPanel.setBorder(BorderFactory.createTitledBorder("Informations Entreprise"));

        JLabel lblNom = new JLabel("📌 Raison sociale : " + entreprise.getRaisonSociale());
        JLabel lblActivite = new JLabel("💼 Activités : " + entreprise.getDescriptionActivite());
        JLabel lblAbos = new JLabel("🔢 Abonnements actifs : " + entreprise.getAbonnements());

        infoPanel.add(lblNom);
        infoPanel.add(lblActivite);
        infoPanel.add(lblAbos);

        // 🔶 Centre : Offres
        JPanel offresPanel = new JPanel(new BorderLayout());
        offresPanel.setBorder(BorderFactory.createTitledBorder("📊 Offres publiées"));

        String[] colonnes = {"Titre", "Compétences", "État"};
        DefaultTableModel model = new DefaultTableModel(colonnes, 0);

        List<OffreEmploi> offres = OffreEmploiDAO.getOffresParEntreprise(entreprise.getCodeClient());
        for (OffreEmploi offre : offres) {
            model.addRow(new Object[]{
                offre.getTitre(),
                offre.getCompetences(), // ou getDescription si c’est plutôt ça
                offre.getEtat() == EtatOffre.ACTIVE ? "Active" : "Désactivée"
            });
        }

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        offresPanel.add(scrollPane, BorderLayout.CENTER);

        // 🔙 Bas : Bouton retour
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

    // Pour test en local
    /*public static void main(String[] args) {
        new InterfaceEntrepriseEspace("TechCorp");
    }*/
}
