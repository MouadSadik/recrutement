package recrutement.classes;
import java.util.Date;

public class Recrutement {
    
    private Date dateRecrutement;

    public Recrutement(Date dateRecrutement) {
		super();
		this.dateRecrutement = dateRecrutement;
	}

	public Date getDateRecrutement() {
		return dateRecrutement;
	}

	public void setDateRecrutement(Date dateRecrutement) {
		this.dateRecrutement = dateRecrutement;
	}
	
}
