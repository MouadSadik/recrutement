package classes;

public abstract class Client {
    protected int codeClient;
    protected String adresse;
    protected String telephone;

    public Client(int codeClient, String adresse, String telephone) {
        this.codeClient = codeClient;
        this.adresse = adresse;
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


}