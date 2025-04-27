package recrutement.classes;

public class CategorieJournal {
    private int idCategorie;
    private String libelle;

    public CategorieJournal(int idCategorie, String libelle) {
        this.idCategorie = idCategorie;
        this.libelle = libelle;
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

    @Override
    public String toString() {
        return "Categorie{" +
                "id=" + id +
                ", libelle='" + libelle + '\'' +
                '}';
    }
}
