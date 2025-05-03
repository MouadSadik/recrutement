package main.java.classes;

public class PreferencesClientCategorie {
    private Client client;
    private CategorieJournal categorie;

    public PreferencesClientCategorie(Client client, CategorieJournal categorie) {
        this.client = client;
        this.categorie = categorie;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public CategorieJournal getCategorie() {
        return categorie;
    }

    public void setCategorie(CategorieJournal categorie) {
        this.categorie = categorie;
    }

    @Override
    public String toString() {
        return "PreferencesClientCategorie [client=" + client + ", categorie=" + categorie + "]";
    }
}
