package main.java.classes;

import java.time.LocalDate;

public class Abonnement {
    private Entreprise entreprise;
    private Journal journal;
    private OffreEmploi offreEmploi;
    private int idAbonnement;
    private LocalDate dateDebut;
    private LocalDate dateExpiration;
    private boolean actif;


    // pour cree une nouvelle abonnement
    public Abonnement(Entreprise entreprise, Journal journal, OffreEmploi offreEmploi, int idAbonnement, LocalDate dateExpiration, boolean actif) {
        this.entreprise = entreprise;
        this.journal = journal;
        this.offreEmploi=offreEmploi;
        this.idAbonnement = idAbonnement;
        this.dateExpiration = dateExpiration;
        this.actif = actif;
        this.dateDebut=LocalDate.now();
    }

    // utiliser dans DAO
    public Abonnement(int idAbonnement, Entreprise entreprise, Journal journal, OffreEmploi offreEmploi,LocalDate dateDebut, LocalDate dateExpiration, boolean actif) {
        this.idAbonnement = idAbonnement;
        this.entreprise = entreprise;
        this.journal = journal;
        this.offreEmploi = offreEmploi;
        this.dateExpiration = dateExpiration;
        this.actif = actif;
        this.dateDebut=dateDebut;
    }


    public Entreprise getEntreprise() {
        return entreprise;
    }

    public void setEntreprise(Entreprise entreprise) {
        this.entreprise = entreprise;
    }

    public Journal getJournal() {
        return journal;
    }

    public void setJournal(Journal journal) {
        this.journal = journal;
    }

    public OffreEmploi getOffreEmploi() {
        return offreEmploi;
    }

    public void setOffreEmploi(OffreEmploi offreEmploi) {
        this.offreEmploi = offreEmploi;
    }

    public int getIdAbonnement() {
        return idAbonnement;
    }

    public void setIdAbonnement(int idAbonnement) {
        this.idAbonnement = idAbonnement;
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

    public void setActif(boolean actif) {
        this.actif = actif;
    }

    public boolean estActif() {
        return actif && LocalDate.now().isBefore(dateExpiration);
    }

    public void desactiver() {
        this.actif = false;
    }

    @Override
	public String toString() {
		return "Abonnement [entreprise=" + entreprise + ", journal=" + journal + ", offreEmploi=" + offreEmploi
				+ ", idAbonnement=" + idAbonnement + ", dateExpiration=" + dateExpiration + ", actif=" + actif + "]";
	}
}
