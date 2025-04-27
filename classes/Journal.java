package recrutement.classes;

import java.util.ArrayList;
import java.util.List;

public class Journal {
    private int codeJournal;
    private String nomJournal;
    private String periodicite;
    private String langue;
    private int idCategorie;
    private List<Edition> editions;
    private List<Abonnement> abonnements;
    private List<OffreEmploi> offres;

    public Journal(int codeJournal, String nomJournal, String periodicite, String langue, int idCategorie) {
        this.codeJournal = codeJournal;
        this.nomJournal = nomJournal;
        this.periodicite = periodicite;
        this.langue = langue;
        this.idCategorie = idCategorie;
        editions = new ArrayList<>();
        abonnements = new ArrayList<>();
        offres = new ArrayList<>();
    }

    // getters
    public int getCodeJournal() {
        return codeJournal;
    }

    public String getNomJournal() {
        return nomJournal;
    }

    public String getPeriodicite() {
        return periodicite;
    }

    public String getLangue() {
        return langue;
    }

    public int getIdCategorie() {
        return idCategorie;
    }

    // setters
    public void setCodeJournal(int codeJournal) {
        this.codeJournal = codeJournal;
    }

    public void setNomJournal(String nomJournal) {
        this.nomJournal = nomJournal;
    }

    public void setPeriodicite(String periodicite) {
        this.periodicite = periodicite;
    }

    public void setLangue(String langue) {
        this.langue = langue;
    }

    public void setIdCategorie(int idCategorie) {
        this.idCategorie = idCategorie;
    }

    

    public void ajouterEdition(Edition edition) {
        if (edition != null) {
            editions.add(edition);
            System.out.println("Edition Ajoutee");
        } else {
            System.out.println("Edition n'est pas ajoutee car elle est null");
        }
    }

    public void supprimerEdition(Edition edition) {
        if (editions.contains(edition)) {
            editions.remove(edition);
            System.out.println("Edition Supprimer");
        } else {
            System.out.println("Edition n'existe pas");
        }
    }

    public void afficherEditions() {
        for(Edition e : editions) {
            System.out.println("Numero D'edition: " + e.getNumEdition() + ", Date de parution: " + e.getDateParution());
        }
    }

    public void ajouterOffre(OffreEmploi offre) {
        if (offre != null) {
            offres.add(offre);
            System.out.println("Offre Ajoutee");
        } else {
            System.out.println("Offre n'est pas ajoutee car elle est null");
        }
    }

    public void supprimerOffre(OffreEmploi offre) {
        if (offres.contains(offre)) {
            offres.remove(offre);
            System.out.println("Edition Supprimer");
        } else {
            System.out.println("Edition n'existe pas");
        }
    }

    public void ajouterCategorie(CategorieJournal categorie) {
        this.idCategorie = categorie.getIdCategorie();
    }

    @Override
    public String toString() {
        return "Journal{" +
                "code='" + codeJournal + '\'' +
                ", nom='" + nomJournal + '\'' +
                ", periodicite='" + periodicite + '\'' +
                ", langue='" + langue + '\'' +
                ", categorieId=" + idCategorie +
                '}';
    }

    public static void main(String[] args) {
        System.out.println("Test");
    }
}