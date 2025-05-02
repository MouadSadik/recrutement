package classes;

import java.util.Date;

public class Recrutement {

	private Date dateRecrutement;
	private Demandeur demandeur;
	private OffreEmploi offreEmploi;

	public Recrutement(Date dateRecrutement, Demandeur demandeur, OffreEmploi offreEmploi) {
	    this.dateRecrutement = dateRecrutement;
	    this.demandeur = demandeur;
	    this.offreEmploi = offreEmploi;
	}
	
	
	public Demandeur getDemandeur() {
		return demandeur;
	}

	public void setDemandeur(Demandeur demandeur) {
		this.demandeur = demandeur;
	}

	public Date getDateRecrutement() {
		return dateRecrutement;
	}

	public void setDateRecrutement(Date dateRecrutement) {
		this.dateRecrutement = dateRecrutement;
	}
	
	public OffreEmploi getOffreEmploi() {
	    return offreEmploi;
	}

	public void setOffreEmploi(OffreEmploi offreEmploi) {
	    this.offreEmploi = offreEmploi;
	}
	
	
	@Override
	public String toString() {
		return "Recrutement [dateRecrutement=" + dateRecrutement + ", demandeur=" + demandeur + ", offreEmploi="
				+ offreEmploi + "]";
	}

	//methodes
	public boolean estAssocieAOffre(int numOffre) {
	    return offreEmploi != null && offreEmploi.getNumOffre() == numOffre;
	}
	
	
}
