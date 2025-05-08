package main.java.models;


import java.util.List;
import java.util.ArrayList;

public class Entreprise extends Client {
    private String raisonSociale;
    private String descriptionActivite;
    private List<Abonnement> abonnements;

    public Entreprise(String adresse, String telephone, String raisonSociale, String descriptionActivite) {
        super(adresse, telephone);
        this.raisonSociale = raisonSociale;
        this.descriptionActivite = descriptionActivite;
        this.abonnements = new ArrayList<>();
    }

    public Entreprise(int codeClient, String adresse, String telephone, String raisonSociale, String descriptionActivite) {
        super(codeClient, adresse, telephone);
        this.raisonSociale = raisonSociale;
        this.descriptionActivite = descriptionActivite;
        this.abonnements = new ArrayList<>();
    }

    public String getRaisonSociale() {
        return this.raisonSociale;
    }

    public void setRaisonSociale(String raisonSociale) {
        this.raisonSociale = raisonSociale;
    }

    public String getDescriptionActivite() {
        return this.descriptionActivite;
    }

    public void setDescriptionActivite(String descriptionActivite) {
        this.descriptionActivite = descriptionActivite;
    }

    public List<Abonnement> getAbonnements() {
        return this.abonnements;
    }

    public void ajouterAbonnement(Abonnement abonnement){
        abonnements.add(abonnement);
    }


    @Override
    public String toString() {
        return "{" + super.toString() + 
            " raisonSociale='" + getRaisonSociale() + "'" +
            ", descriptionActive='" + getDescriptionActivite() + "'" +
            ", abonnements='" + getAbonnements() + "'" +
            "}";
    }


}
