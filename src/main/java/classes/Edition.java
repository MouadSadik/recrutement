package main.java.classes;

import java.sql.Date;

public class Edition {
    private int numEdition;
    private Date dateParution;
    private String codeJournal;

    public Edition(int numEdition, String codeJournal){
        this.numEdition = numEdition;
        this.dateParution = new Date(System.currentTimeMillis());
        this.codeJournal = codeJournal;
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

    public void publierOffre(OffreEmploi offre) {

    }

    public void consulterOffre(OffreEmploi offre) {

    }

    @Override
    public String toString() {
        return "Edition{" +
                "numero=" + numEdition +
                ", dateParution=" + dateParution +
                ", codeJournal='" + codeJournal + '\'' +
                '}';
    }

    public static void main(String[] args) {
        System.out.println("Test");
    }
}
