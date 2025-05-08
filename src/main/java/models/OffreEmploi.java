package main.java.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OffreEmploi {

	private int numOffre;
	private String titre;
	private String competences;
	private int nbAnneeExperienceDemandee;
	private int nbPostes;

	public enum EtatOffre {
		ACTIVE, DESACTIVEE, SUSPENDUE
	}

	private EtatOffre etat;
	private Abonnement abonnement;
	private Edition edition;

	private List<Postulation> postulations;
	private List<Recrutement> recrutements;

	public OffreEmploi(int numOffre, String titre, String competences, int nbAnneeExperienceDemandee, int nbPostes,
			EtatOffre etat, Edition edition,Abonnement abonnement) {
		if (nbAnneeExperienceDemandee < 0 || nbPostes < 0) {
			throw new IllegalArgumentException("Les valeurs ne peuvent pas être négatives.");
		}
		this.numOffre = numOffre;
		this.titre = titre;
		this.competences = competences;
		this.nbAnneeExperienceDemandee = nbAnneeExperienceDemandee;
		this.nbPostes = nbPostes;
		this.etat = etat;
		this.edition = edition;
		this.abonnement = abonnement;
		this.postulations = new ArrayList<>();
		this.recrutements = new ArrayList<>();
	}

	// Getters et Setters
	public int getNumOffre() {
		return numOffre;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getCompetences() {
		return competences;
	}

	public void setCompetences(String competences) {
		this.competences = competences;
	}

	public int getNbAnneeExperienceDemandee() {
		return nbAnneeExperienceDemandee;
	}

	public void setNbAnneeExperienceDemandee(int nbAnneeExperienceDemandee) {
		if (nbAnneeExperienceDemandee < 0) {
			throw new IllegalArgumentException("Le nombre d'années ne peut pas être négatif.");
		}
		this.nbAnneeExperienceDemandee = nbAnneeExperienceDemandee;
	}

	public int getNbPostes() {
		return nbPostes;
	}

	public void setNbPostes(int nbPostes) {
		if (nbPostes < 0) {
			throw new IllegalArgumentException("Le nombre de postes ne peut pas être négatif.");
		}
		this.nbPostes = nbPostes;
	}

	public EtatOffre getEtat() {
		return etat;
	}

	public void setEtat(EtatOffre etat) {
		this.etat = etat;
	}

	public Abonnement getAbonnement() {
		return abonnement;
	}

	public void setAbonnement(Abonnement abonnement) {
		this.abonnement = Objects.requireNonNull(abonnement, "L'abonnement ne peut pas être null.");
	}

	public Edition getEdition() {
		return edition;
	}

	public void setEdition(Edition edition) {
		this.edition = edition;
	}

	public List<Postulation> getPostulations() {
		return new ArrayList<>(postulations);
	}

	public boolean peutPostuler(Demandeur demandeur) {
		if (demandeur == null)
			return false;

		boolean experienceSuffisante = demandeur.getAnneeExp() >= this.nbAnneeExperienceDemandee;

		boolean dejaPostule = postulations.stream()
				.anyMatch(p -> p.getDemandeur().equals(demandeur));

		return etat == EtatOffre.ACTIVE && experienceSuffisante && !dejaPostule;
	}

	public boolean ajouterPostulation(Postulation postulation) {
		if (postulation == null || !peutPostuler(postulation.getDemandeur())) {
			return false;
		}
		postulations.add(postulation);
		return true;
	}
	

	public List<Recrutement> getRecrutements() {
		return new ArrayList<>(recrutements);
	}

	public boolean ajouterRecrutement(Recrutement recrutement) {
		if (peutAjouterRecrutement() && recrutement != null) {
			recrutements.add(recrutement);
			cloturerOffre(); // vérifie si on doit désactiver l’offre
			return true;
		}
		return false;
	}

	// Méthodes métier
	public boolean estDisponible() {
		return getNbPostesRestants() > 0 && etat == EtatOffre.ACTIVE;
	}

	public int getNbRecrutementsEffectues() {
		return recrutements.size();
	}

	public int getNbPostesRestants() {
		return nbPostes - recrutements.size();
	}

	public void cloturerOffre() {
		if (getNbPostesRestants() <= 0) {
			setEtat(EtatOffre.DESACTIVEE);
		}
	}

	public List<Recrutement> getRecrutementsParCandidat(String nomDemandeur) {
		List<Recrutement> resultats = new ArrayList<>();
		for (Recrutement r : recrutements) {
			if (r.getDemandeur().getNom().equalsIgnoreCase(nomDemandeur)) {
				resultats.add(r);
			}
		}
		return resultats;
	}

	public boolean peutAjouterRecrutement() {
		return getNbPostesRestants() > 0 && etat == EtatOffre.ACTIVE;
	}

	@Override
	public String toString() {
		return "OffreEmploi{" +
				"numOffre=" + numOffre +
				", titre='" + titre + '\'' +
				", competences='" + competences + '\'' +
				", nbAnneeExperienceDemandee=" + nbAnneeExperienceDemandee +
				", nbPostes=" + nbPostes +
				", etat=" + etat +
				", abonnement=" + (abonnement != null ? abonnement.getIdAbonnement() : "null") +
				", postulations=" + postulations.size() +
				", edition=" + edition +
				", recrutements=" + recrutements.size() +
				'}';
	}
}
