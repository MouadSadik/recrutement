package recrutement.classes;

import java.util.ArrayList;
import java.util.List;

public class OffreEmploi {
    
	private static int compteur = 1;
    private int numOffre;
    private String titre;
    private String competences;
    private int nbAnneeExperienceDemandee;
    private int nbPostes;
    private String etat;
	private Abonnement abonnement;
	private Postulation postulation;
	private Edition edition;
	private List<Recrutement> recrutements;

	
    public OffreEmploi( String titre, String competences, int nbAnneeExperienceDemandee, int nbPostes,
			String etat,Postulation postulation, Edition edition) {
		this.numOffre = compteur++;
		this.titre = titre;
		this.competences = competences;
		this.nbAnneeExperienceDemandee = nbAnneeExperienceDemandee;
		this.nbPostes = nbPostes;
		this.etat = etat;
		this.postulation = postulation;
		this.edition = edition;
		this.recrutements = new ArrayList<>();
	}
    
//    public enum EtatOffre {
//        OUVERTE,
//        FERMEE,
//        SUSPENDUE
//    }
//    private EtatOffre etat;
    
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
		    throw new IllegalArgumentException("Le nombre d'annees ne peut pas être négatif.");
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

	public String getEtat() {
		return etat;
	}

	public void setEtat(String etat) {
		this.etat = etat;
	}

	public Abonnement getAbonnement() {
		return abonnement;
	}

	public void setAbonnement(Abonnement abonnement) {
		this.abonnement = abonnement;
	}

	public Postulation getPostulation() {
		return postulation;
	}

	public void setPostulation(Postulation postulation) {
		this.postulation = postulation;
	}

	public Edition getEdition() {
		return edition;
	}

	public void setEdition(Edition edition) {
		this.edition = edition;
	}
	
	public List<Recrutement> getRecrutements() {
		return recrutements;
	}
	
	public void ajouterRecrutement(Recrutement recrutement){
		recrutements.add(recrutement);
    } 


	@Override
	public String toString() {
		return "OffreEmploi [numOffre=" + numOffre + ", titre=" + titre + ", competences=" + competences
				+ ", nbAnneeExperienceDemandee=" + nbAnneeExperienceDemandee + ", nbPostes=" + nbPostes + ", etat="
				+ etat + ", abonnement=" + abonnement + ", postulation=" + postulation + ", edition=" + edition
				+ ", recrutements=" + recrutements.size() + "]";
	}

	//methodes
	public boolean estDisponible() {
	    return recrutements.size() < nbPostes && "OUVERTE".equalsIgnoreCase(etat);
	}
	
	public int getNbRecrutementsEffectues() {
	    return recrutements.size();
	}
	
	public int getNbPostesRestants() {
	    return nbPostes - recrutements.size();
	}
	
	public void cloturerOffre() {
	    if (getNbPostesRestants() <= 0) {
	        this.etat = "FERMEE";
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
	    return getNbPostesRestants() > 0 && "OUVERTE".equalsIgnoreCase(etat);
	}
	
}
