package main.java.models;

public abstract class Client {
    protected int codeClient;
    protected String adresse;
    protected String telephone;
    static protected int nextId=1;

    public Client(String adresse, String telephone) {
        this.codeClient = nextId++;
        this.adresse = adresse;
        this.telephone = telephone;
    }

    // Constructeur alternatif si tu veux créer un client avec un ID connu
    public Client(int codeClient, String adresse, String telephone) {
        this.codeClient = codeClient;
        this.adresse = adresse;
        this.telephone = telephone;
    }

    public void setCodeClient(int codeClient) {
        this.codeClient = codeClient;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public int getCodeClient() {
        return codeClient;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getTelephone() {
        return telephone;
    }



    @Override
    public String toString() {
        return "{" +
            " codeClient='" + getCodeClient() + "'" +
            ", adresse='" + getAdresse() + "'" +
            ", telephone='" + getTelephone() + "'" +
            "}";
    }


}