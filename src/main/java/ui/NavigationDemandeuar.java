package main.java.ui;

import main.java.models.Demandeur;

import javax.swing.*;

public class NavigationDemandeuar {

    private static JFrame currentFrame;

    // Ouvre la fenêtre d'authentification (connexion)
    public static void openAuthFrame() {
        closeCurrentFrame();
        currentFrame = new DemandeurAuthFrame();
        currentFrame.setVisible(true);
    }

    // Ouvre le tableau de bord du demandeur après authentification
    public static void openDashboard(Demandeur demandeur) {
        closeCurrentFrame();
        currentFrame = new DemandeurDashboardFrame(demandeur);
        currentFrame.setVisible(true);
    }

    // Ouvre la liste des offres d'emploi pour le demandeur
    public static void openOffresFrame(Demandeur demandeur) {
        closeCurrentFrame();
        currentFrame = new DemandeurOffresFrame(demandeur);
        currentFrame.setVisible(true);
    }

    // Ferme la fenêtre courante si elle existe
    private static void closeCurrentFrame() {
        if (currentFrame != null) {
            currentFrame.dispose();
            currentFrame = null;
        }
    }
}
