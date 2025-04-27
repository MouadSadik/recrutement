package recrutement.classes;

public class Journal {
    private int codeJournal;
    private String nomJournal;
    private String periodicite;
    private String langue;

    public Journal(int codeJournal, String nomJournal, String periodicite, String langue) {
        this.codeJournal = codeJournal;
        this.nomJournal = nomJournal;
        this.periodicite = periodicite;
        this.langue = langue;
    }

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
}
