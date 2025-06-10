package main.java.ui;

import main.java.DAO.PostulationDAO;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class VoirPostulationsUI extends JFrame {

    private JList<String> list;
    private DefaultListModel<String> listModel;
    private List<Object[]> postulations;

    public VoirPostulationsUI(int numOffre) {
        setTitle("Postulations de l'offre " + numOffre);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        postulations = PostulationDAO.getPostulationsParOffre(numOffre);
        listModel = new DefaultListModel<>();

        for (Object[] postulation : postulations) {
            String ligne = "Nom: " + postulation[2] + " " + postulation[3] +
                    ", adresse: " + postulation[7] +
                    ", Téléphone: " + postulation[8] +
                    ", date postulation: " + postulation[9];

            listModel.addElement(ligne);
        }

        list = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(list);

        JButton btnAccepter = new JButton("Accepter");
        JButton btnRefuser = new JButton("Refuser");
        JButton btnRetour = new JButton("Retour");

        JPanel panelBoutons = new JPanel();
        panelBoutons.add(btnAccepter);
        panelBoutons.add(btnRefuser);
        panelBoutons.add(btnRetour);

        add(scrollPane, BorderLayout.CENTER);
        add(panelBoutons, BorderLayout.SOUTH);

        btnAccepter.addActionListener(e -> {
            int selected = list.getSelectedIndex();
            if (selected != -1) {
                int idPostulation = (int) postulations.get(selected)[0];
                PostulationDAO.mettreAJourEtat(idPostulation, "ACCEPTEE");
                JOptionPane.showMessageDialog(this, "Postulation acceptée.");
                listModel.set(selected, listModel.get(selected).replace("EN_ATTENTE", "ACCEPTEE"));
            }
        });

        btnRefuser.addActionListener(e -> {
            int selected = list.getSelectedIndex();
            if (selected != -1) {
                int idPostulation = (int) postulations.get(selected)[0];
                PostulationDAO.mettreAJourEtat(idPostulation, "REFUSEE");
                JOptionPane.showMessageDialog(this, "Postulation refusée.");
                listModel.set(selected, listModel.get(selected).replace("EN_ATTENTE", "REFUSEE"));
            }
        });

        btnRetour.addActionListener(e -> dispose());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new VoirPostulationsUI(9).setVisible(true);
        });
    }

}
