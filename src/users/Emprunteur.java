package users;

import gestion.Emprunt;
import gestion.Reservation;

import java.util.ArrayList;

import action.Parser;

/**
 * @author Félix & M.Boutelier
 */
public class Emprunteur extends User {
	private ArrayList<Emprunt> emprunts = new ArrayList<Emprunt>();
	
	public Emprunteur(Type type, String name) {
		super(type, name);
	}

	/**
	 * Recupere la liste de tout les emprunts
	 * @return String
	 */
	public String getAllEmprunt() {	
		
		if(!emprunts.isEmpty()) {
			String result = "Voici la liste de vos emprunts:\n";
			for (Emprunt empr : emprunts) {
				if (empr.isActive())
					result += "(actif) ";
				else if (empr instanceof Reservation)
					result += "(reservation)";
				else 
					result += "(en attente)";
				result += empr.toString() + "\n";
			}
			return result;	
		}
		return null; //si la liste ne contient rien
	}

	public boolean isInTheList(String s){
		for (Emprunt empr : emprunts)
			if (empr.getMateriel().toString().equals(s))
				return true;
		return false;
		
	}

	public void addEmprunt(Emprunt emprunt){
		emprunts.add(emprunt);
	}
	
	/**
	 * Enleve de la liste {@code emprunts} un emprunt de materiel {@code key}
	 * @param key
	 * @return Emprunt enleve
	 */
	public Emprunt rendre(String key) {
		System.out.println("Emprunteur.rendre");
		ArrayList<Emprunt> tmp = new ArrayList<Emprunt>();
		int counter=0, n;
		Emprunt aRendre;
		for (Emprunt empr : emprunts) {
			if (empr.getMateriel().toString().equals(key)){
				counter ++;
				System.out.println(counter+" : "+empr.toString());
				tmp.add(empr);
			}
		}
		if (tmp.isEmpty()){
			System.out.println("Vous n'avez pas de "+key+" a rendre.");
			return null;
		}
		else {
			n = new Parser().askNumber("lequel voulez vous rendre ? ",counter);
			aRendre = tmp.get(n-1);
			emprunts.remove(aRendre);
			return aRendre;
		}
	}
}
