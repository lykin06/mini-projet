package gestion;

import java.util.Date;

import materiel.Materiel;
import users.Emprunteur;

public class Emprunt {
	protected Date fin;
	protected Emprunteur emprunteur;
	protected Materiel materiel;
	protected int nombre;
	protected boolean isActive = false;

	public Emprunt(Date fin, Emprunteur emprunteur,
			Materiel materiel, int nombre) {
		this.fin = fin;
		this.setEmprunteur(emprunteur);
		this.materiel = materiel;
		this.nombre = nombre;
	}

	public Emprunteur getEmprunteur() {
		return emprunteur;
	}
	
	public Date getFin(){
		return fin;
	}
	
	public void switchActive() {
		isActive = !isActive;
	}
	
	public boolean isActive(){
		return isActive;
	}

	public void setEmprunteur(Emprunteur emprunteur) {
		this.emprunteur = emprunteur;
	}

	public String toString() {
		return nombre + " " + materiel.toString()
				+ " a rendre le " + fin.toString();
	}

	public Materiel getMateriel() {
		return materiel;
	}
	
	public int getNombre() {
		return nombre;
	}
	
	
	public void annulerEmprunt(){
		getEmprunteur().rendre(getMateriel().toString());
	}
}
