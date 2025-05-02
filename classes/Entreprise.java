package classes;


import java.util.List;

public class Entreprise extends Client {
    private String raisonSociale;
    private String descriptionActive;
    private List<Abonnement> abonnements;

    public Entreprise(int codeClient, String adresse, String telephone,
                      String raisonSociale, String descriptionActivite) {
        super(codeClient, adresse, telephone);
        this.raisonSociale = raisonSociale;
        this.descriptionActive = descriptionActivite;
    }

    public String getRaisonSociale() {
        return this.raisonSociale;
    }

    public void setRaisonSociale(String raisonSociale) {
        this.raisonSociale = raisonSociale;
    }

    public String getDescriptionActive() {
        return this.descriptionActive;
    }

    public void setDescriptionActive(String descriptionActive) {
        this.descriptionActive = descriptionActive;
    }

    public List<Abonnement> getAbonnements() {
        return this.abonnements;
    }

    public void ajouterAbonnement(Abonnement abonnement){
        abonnements.add(abonnement);
    }

}
