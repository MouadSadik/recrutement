package classes;

import java.time.LocalDate;

public class Abonnement {
    private Entreprise entreprise;
    private Journal journal;
    private int idAbonnement;
    private LocalDate dateExpiration;
    private boolean actif;


    public Abonnement(Entreprise entreprise, Journal journal, int idAbonnement, LocalDate dateExpiration, boolean actif) {
        this.entreprise = entreprise;
        this.journal = journal;
        this.idAbonnement = idAbonnement;
        this.dateExpiration = dateExpiration;
        this.actif = actif;
    }


    public Entreprise getEntreprise() {
        return this.entreprise;
    }



    public Journal getJournal() {
        return this.journal;
    }


    public int getIdAbonnement() {
        return this.idAbonnement;
    }


    public LocalDate getDateExpiration() {
        return this.dateExpiration;
    }

    public void setDateExpiration(LocalDate dateExpiration) {
        this.dateExpiration = dateExpiration;
    }



    public void setActif(boolean actif) {
        this.actif = actif;
    }


    public boolean estActif() {
        return actif && LocalDate.now().isBefore(dateExpiration);
    }

    public void desactiver() {
        this.actif = false;
    }

}
