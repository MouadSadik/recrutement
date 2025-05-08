package main.java.models;

import java.sql.Date;
import java.util.Objects;

public class Recrutement {

	private int idRecrutement;
	private Date dateRecrutement;
	private Demandeur demandeur;
	private OffreEmploi offreEmploi;

	public Recrutement(int idRecrutement,Date dateRecrutement, Demandeur demandeur, OffreEmploi offreEmploi) {
		if (dateRecrutement == null || demandeur == null || offreEmploi == null) {
			throw new IllegalArgumentException("Aucun champ ne peut être null dans Recrutement.");
		}
		this.idRecrutement = idRecrutement;
		this.dateRecrutement = dateRecrutement;
		this.demandeur = demandeur;
		this.offreEmploi = offreEmploi;
	}

	public int getIdRecrutement() {
		return idRecrutement;
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

	// methodes
	public boolean estAssocieAOffre(int numOffre) {
		return offreEmploi != null && offreEmploi.getNumOffre() == numOffre;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Recrutement that = (Recrutement) o;
		return demandeur.equals(that.demandeur) && offreEmploi.equals(that.offreEmploi);
	}

	@Override
	public int hashCode() {
		return Objects.hash(demandeur, offreEmploi);
	}

}
