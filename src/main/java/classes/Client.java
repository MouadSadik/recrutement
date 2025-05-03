package main.java.classes;

public abstract class Client {
    protected int codeClient;
    protected String adresse;
    protected String telephone;
    static protected int id=1;

    public Client(String adresse, String telephone) {
        this.codeClient = id++;
        this.adresse = adresse;
        this.telephone = telephone;
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