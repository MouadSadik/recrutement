package recrutement.classes;

import java.util.Date;

public class Postulation {

	private Date datePostulation;
	private OffreEmploi offreEmploi;
	private Demandeur demandeur;
	private Edition edition;

	public Postulation(Date datePostulation, OffreEmploi offreEmploi, Demandeur demandeur, Edition edition) {
	    this.datePostulation = datePostulation;
	    this.offreEmploi = offreEmploi;
	    this.demandeur = demandeur;
	    this.edition = edition;
	}
	
	public Demandeur getDemandeur() {
		return demandeur;
	}

	public void setDemandeur(Demandeur demandeur) {
		this.demandeur = demandeur;
	}

	public Date getDatePostulation() {
		return datePostulation;
	}

	public void setDatePostulation(Date datePostulation) {
		this.datePostulation = datePostulation;
	}

	public OffreEmploi getOffreEmploi() {
		return offreEmploi;
	}

	public void setOffreEmploi(OffreEmploi offreEmploi) {
		this.offreEmploi = offreEmploi;
	}
	
	

	public Edition getEdition() {
		return edition;
	}

	public void setEdition(Edition edition) {
		this.edition = edition;
	}
	
	
	@Override
	public String toString() {
		return "Postulation [datePostulation=" + datePostulation + ", offreEmploi=" + offreEmploi + ", demandeur="
				+ demandeur + ", edition=" + edition + "]";
	}

	//methodes
	public boolean estEncoreValide() {
	    return offreEmploi != null && "OUVERTE".equalsIgnoreCase(offreEmploi.getEtat());
	}
	
	
}
