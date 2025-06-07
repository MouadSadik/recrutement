package main.java.models;

import java.time.LocalDate;

public class Abonnement {
    private int idAbonnement;
    private int codeClient;
    private int codeJournal;
    private LocalDate dateDebut;
    private LocalDate dateExpiration;
    private boolean etat;

    // Constructeur
    public Abonnement(int idAbonnement, int codeClient, int codeJournal, LocalDate dateDebut, LocalDate dateExpiration, boolean etat) {
        this.idAbonnement = idAbonnement;
        this.codeClient = codeClient;
        this.codeJournal = codeJournal;
        this.dateDebut = dateDebut;
        this.dateExpiration = dateExpiration;
        this.etat = etat;
    }

    // Getters et Setters
    public int getIdAbonnement() {
        return idAbonnement;
    }

    public void setIdAbonnement(int idAbonnement) {
        this.idAbonnement = idAbonnement;
    }

    public int getCodeClient() {
        return codeClient;
    }

    public void setCodeClient(int codeClient) {
        this.codeClient = codeClient;
    }

    public int getCodeJournal() {
        return codeJournal;
    }

    public void setCodeJournal(int codeJournal) {
        this.codeJournal = codeJournal;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateExpiration() {
        return dateExpiration;
    }

    public void setDateExpiration(LocalDate dateExpiration) {
        this.dateExpiration = dateExpiration;
    }

    public boolean isEtat() {
        return etat;
    }

    public void setEtat(boolean etat) {
        this.etat = etat;
    }

    @Override
    public String toString() {
        return "Abonnement [id=" + idAbonnement +
                ", client=" + codeClient +
                ", journal=" + codeJournal +
                ", debut=" + dateDebut +
                ", expiration=" + dateExpiration +
                ", actif=" + etat + "]";
    }
}
