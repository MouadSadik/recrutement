package recrutement.classes;

public class Edition {
    private int numEdition;
    private date dateParution;

    public Edition(int numEdition) {
        this.numEdition = numEdition;
        dateParution = new date();
    }

    public date getDateParution() {
        return dateParution;
    }

    public int getNumEdition() {
        return numEdition;
    }

    public void setDateParution(date dateParution) {
        this.dateParution = dateParution;
    }

    public void setNumEdition(int numEdition) {
        this.numEdition = numEdition;
    }
}
