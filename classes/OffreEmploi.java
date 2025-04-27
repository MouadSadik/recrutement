package recrutement.classes;

public class OffreEmploi {
    
    int numOffre;
    String titre;
    String competences;
    int experienceDemandee;
    int nbPostes;
    String etat;

    public OffreEmploi(int numOffre, String titre, String competences, int experienceDemandee, int nbPostes,
			String etat) {
		super();
		this.numOffre = numOffre;
		this.titre = titre;
		this.competences = competences;
		this.experienceDemandee = experienceDemandee;
		this.nbPostes = nbPostes;
		this.etat = etat;
	}

	public int getNumOffre() {
		return numOffre;
	}

	public void setNumOffre(int numOffre) {
		this.numOffre = numOffre;
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

	public int getExperienceDemandee() {
		return experienceDemandee;
	}

	public void setExperienceDemandee(int experienceDemandee) {
		this.experienceDemandee = experienceDemandee;
	}

	public int getNbPostes() {
		return nbPostes;
	}

	public void setNbPostes(int nbPostes) {
		this.nbPostes = nbPostes;
	}

	public String getEtat() {
		return etat;
	}

	public void setEtat(String etat) {
		this.etat = etat;
	}
}
