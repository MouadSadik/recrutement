package recrutement.classes;

import java.util.ArrayList;
import java.util.List;

public class CategorieJournal {
    private int idCategorie;
    private String libelle;
    private List<Journal> journals;

    public CategorieJournal(int idCategorie, String libelle) {
        this.idCategorie = idCategorie;
        this.libelle = libelle;
        journals = new ArrayList<>();
    }

    public int getIdCategorie() {
        return idCategorie;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setIdCategorie(int idCategorie) {
        this.idCategorie = idCategorie;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public void ajouterJournal(Journal journal){
        if (journal != null) {
            journals.add(journal);
            System.out.println("Journal Ajoutee");
        } else {
            System.out.println("Journal n'est pas ajoutee car elle est null");
        }
    }

    public void supprimerJournal(Journal journal) {
        if (journals.contains(journal)) {
            journals.remove(journal);
            System.out.println("Journal Supprimer");
        } else {
            System.out.println("Journal n'existe pas");
        }
    }

    @Override
    public String toString() {
        return "Categorie{" +
                "id=" + id +
                ", libelle='" + libelle + '\'' +
                '}';
    }

    public static void main(String[] args) {
        System.out.println("Test");
    }
}