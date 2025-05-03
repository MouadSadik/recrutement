package main.java.classes;

import java.util.ArrayList;
import java.util.List;

public class Demandeur extends Client {
    private String nom;
    private String prenom;
    private String fax;
    private int anneeExp;
    private double salaiireSouhaite;
    private String diplome;
    private List<OffreEmploi> emplois;
    


    public Demandeur(int codeClient,String adresse, String tel, String nom, String prenom, String fax, int anneeExp,
            double salaiireSouhaite, String diplome) {
                super(codeClient, adresse, tel);
        this.nom = nom;
        this.prenom = prenom;
        this.fax = fax;
        this.anneeExp = anneeExp;
        this.salaiireSouhaite = salaiireSouhaite;
        this.diplome = diplome;
        emplois =new ArrayList<>();
    }
    
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public int getAnneeExp() {
        return anneeExp;
    }

    public void setAnneeExp(int anneeExp) {
        this.anneeExp = anneeExp;
    }

    public double getSalaiireSouhaite() {
        return salaiireSouhaite;
    }

    public void setSalaiireSouhaite(double salaiireSouhaite) {
        this.salaiireSouhaite = salaiireSouhaite;
    }

    public String getDiplome() {
        return diplome;
    }

    public void setDiplome(String diplome) {
        this.diplome = diplome;
    }
    public List<OffreEmploi> getEmplois() {
        return emplois;
    }
    public void ajouterEmploi(OffreEmploi emploi){
        emplois.add(emploi);
    }
}