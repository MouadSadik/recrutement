package recrutement.classes;

import java.sql.Date;

public class Edition {
    private int numEdition;
    private Date dateParution;

    public Edition(int numEdition) {
        this.numEdition = numEdition;
        this.dateParution = new Date(System.currentTimeMillis());
    }

    public Date getDateParution() {
        return dateParution;
    }

    public int getNumEdition() {
        return numEdition;
    }

    public void setDateParution(Date dateParution) {
        this.dateParution = dateParution;
    }

    public void setNumEdition(int numEdition) {
        this.numEdition = numEdition;
    }
}
