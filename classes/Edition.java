package recrutement.classes;

import java.sql.Date;

public class Edition {
    private int numEdition;
    private Date dateParution;
    private String codeJournal;

    public Edition(int numEdition) {
        this.numEdition = numEdition;
        this.dateParution = new Date(System.currentTimeMillis());
    }

    //getters
    public Date getDateParution() {
        return dateParution;
    }

    public int getNumEdition() {
        return numEdition;
    }

    public String getCodeJournal() {
        return codeJournal;
    }

    //setters
    public void setDateParution(Date dateParution) {
        this.dateParution = dateParution;
    }

    public void setNumEdition(int numEdition) {
        this.numEdition = numEdition;
    }

    public void setCodeJournal(String codeJournal) {
        this.codeJournal = codeJournal;
    }

    @Override
    public String toString() {
        return "Edition{" +
                "numero=" + numEdition +
                ", dateParution=" + dateParution +
                ", codeJournal='" + codeJournal + '\'' +
                '}';
    }
}
