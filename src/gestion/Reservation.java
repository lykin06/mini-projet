package gestion;

import java.util.Date;

import materiel.Materiel;
import users.Emprunteur;

/**
 * @author Félix
 */
public class Reservation extends Emprunt {
	private Date debut;

	public Reservation(Date debut, Date fin, Emprunteur emprunteur,
			Materiel materiel, int nombre) {
		super(fin, emprunteur, materiel, nombre);
		this.setDebut(debut);	
	}

	public Date getDebut() {
		return debut;
	}

	public void setDebut(Date debut) {
		this.debut = debut;
	}
	
	public String toString() {
		return nombre + " " + materiel.toString()+" du "+debut.toString()
				+ " au " + fin.toString();
	}

}
