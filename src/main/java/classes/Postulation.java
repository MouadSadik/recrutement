package main.java.classes;

import java.sql.Date;
import java.util.Objects;

public class Postulation {
	private int idPostulation;
	private Date datePostulation;
	private OffreEmploi offreEmploi;
	private Demandeur demandeur;
	private Edition edition;

	public Postulation(int idPostulation,Date datePostulation, OffreEmploi offreEmploi, Demandeur demandeur, Edition edition) {
		if (datePostulation == null || offreEmploi == null || demandeur == null || edition == null) {
			throw new IllegalArgumentException("Aucun paramètre ne peut être null dans une postulation.");
		}
		this.idPostulation = idPostulation;
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

	public int getIdPostulation() {
		return idPostulation;
	}

	@Override
	public String toString() {
		return "Postulation [datePostulation=" + datePostulation + ", offreEmploi=" + offreEmploi + ", demandeur="
				+ demandeur + ", edition=" + edition + "]";
	}

	// methodes
	public boolean estEncoreValide() {
		return offreEmploi != null && offreEmploi.getEtat() == OffreEmploi.EtatOffre.ACTIVE;

	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Postulation that = (Postulation) o;
		return demandeur.equals(that.demandeur) && offreEmploi.equals(that.offreEmploi);
	}

	@Override
	public int hashCode() {
		return Objects.hash(demandeur, offreEmploi);
	}

}
