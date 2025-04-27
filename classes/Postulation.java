package recrutement.classes;
import java.util.Date;

public class Postulation {
    private Date datePostulation;

    public Postulation(Date datePostulation) {
		super();
		this.datePostulation = datePostulation;
	}

	public Date getDatePostulation() {
		return datePostulation;
	}

	public void setDatePostulation(Date datePostulation) {
		this.datePostulation = datePostulation;
	}
}
