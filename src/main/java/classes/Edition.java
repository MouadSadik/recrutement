package main.java.classes;

import java.sql.Date;
import java.time.LocalDate;

public class Edition {
    private int numEdition;
    private LocalDate dateParution;
    private int codeJournal;

    public Edition(int numEdition, int codeJournal, LocalDate dateparution){
        this.numEdition = numEdition;
        this.dateParution = dateparution;
        this.codeJournal = codeJournal;
    }

    //getters
    public LocalDate getDateParution() {
        return dateParution;
    }

    public int getNumEdition() {
        return numEdition;
    }

    public int getCodeJournal() {
        return codeJournal;
    }

    //setters
    public void setDateParution(LocalDate dateParution) {
        this.dateParution = dateParution;
    }

    public void setNumEdition(int numEdition) {
        this.numEdition = numEdition;
    }

    public void setCodeJournal(int codeJournal) {
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
