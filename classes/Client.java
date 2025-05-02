package classes;

public abstract class Client {
    private int codecl;
    private String adresse;
    static protected int id=0;
    
    private String tel;


    public Client( String adresse, String tel) {
        this.codecl = ++id;
        this.adresse = adresse;
        this.tel = tel;
    }

    public int getCodecl() {
        return codecl;
    }

    public void setCodecl(int codecl) {
        this.codecl = codecl;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

}