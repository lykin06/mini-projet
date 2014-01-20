package gestion;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import action.Parser;
import users.Emprunteur;
import users.Gestionnaire;
import users.Type;
import users.User;

/**
 * 
 * @author Félix & M.Boutelier
 *
 */
public class Gestion {
	private HashMap<String, Integer> stock;
	private Parser parser = new Parser();
	private ArrayList<User> users;
	private ArrayList<Emprunt> empruntEnCours;
	private ArrayList<Reservation> reservations;
	private ArrayList<Emprunt>	aValider;
	private Gestionnaire gest = new Gestionnaire("le gestionnaire");
	
	private static final int MAX_CAMERA = 10;
	private static final int MAX_CASQUE = 20;
	private static final int MAX_TABLETTE_ANDROID = 100;
	private static final int MAX_TABLETTE_APPLE = 50;
	private static final int MAX_TELEPHONE_ANDROID = 40;
	private static final int MAX_TELEPHONE_APPLE = 20;
	
	/**
	 * contructeur de gestion.
	 * Initialise le stock et cree les listes. 
	 */
	public Gestion() {
		stock = new HashMap<String, Integer>();
		initStock();
		users = new ArrayList<User>();
		empruntEnCours = new ArrayList<Emprunt>();
		aValider = new ArrayList<Emprunt>();
		reservations = new ArrayList<Reservation>();
	}
	/**
	 * Initialise le stock. les valeurs du stock par default sont fixe
	 */
	private void initStock() {
		stock.put("tablette android", MAX_TABLETTE_ANDROID);
		stock.put("tablette apple", MAX_TABLETTE_APPLE);
		stock.put("telephone android", MAX_TELEPHONE_ANDROID);
		stock.put("telephone apple", MAX_TELEPHONE_APPLE);
		stock.put("casque", MAX_CASQUE);
		stock.put("camera", MAX_CAMERA);
	}

	// ******setter, getter et affichage********
	
	public Emprunteur getUser(String name, Type type) {
		Emprunteur newUser = new Emprunteur(type, name);
		for (User user : users)
			if (user.equals(newUser))
				return (Emprunteur) user;
		users.add(newUser);
		return newUser;
	}
	

	public Gestionnaire getGestionnaire() {
		return gest;
	}
	
	/**
	 * @param key nom de l'objet
	 * @return nombre de d'objet au maximum dans le stock
	 */
	public int getMaxStock(String key) {
		switch (key) {
		case "camera" : return MAX_CAMERA;
		case "casque" : return MAX_CASQUE;
		case "tablette andoid" : return MAX_TABLETTE_ANDROID;
		case "tablette apple" : return MAX_TABLETTE_APPLE;
		case "telephone apple" : return MAX_TABLETTE_APPLE;
		case "telephone android" : return MAX_TABLETTE_ANDROID;
		default : return 10;
		}
	}
	
	/**
	 * 
	 * @param key nom de l'objet
	 * @return nombre d'objet actuelement dans le stock
	 */
	public int getNumberItemInStock(String key){
		Set<Entry<String, Integer>> set = stock.entrySet();
		Iterator<Entry<String, Integer>> i = set.iterator();
		while(i.hasNext()) {
			Map.Entry<String, Integer> me = i.next();
			if (key.equals(me.getKey()))
					return me.getValue();	
		}
		return 0;
	}

	/**
	 * Gere l'affichage des differentes listes possibles :<br />
	 * Si c'est un emprunteur, il affiche sa liste d'emprunt. <br />
	 * Le gestionnaire choisi les differents listes qu'il peut afficher 
	 * @param user utilisateur voulant afficher 
	 * @return String
	 */
	public String affichage(User user) {
		if(user instanceof Gestionnaire) {
			int choice = parser.askNumber("Que voulez vous afficher :\n1 : Etat du stock\n" +
					"2 : Liste des emprunts a valider\n3 : Liste des emprunts en cours\n" +
					"4 : Liste des reservations",4);
			switch (choice){
			case 1 : return affichageStock();
			case 2 : return affichageEmpruntAValider();
			case 3 : return affichageEmpruntEnCours();
			case 4 : return affichageReservations();
			}
		} else if(user instanceof Emprunteur) {
			return ((Emprunteur) user).getAllEmprunt();
		}
		
		return null;
	}
	
	/**
	 * Renvoi un string contenant l'etat du stock
	 * @return String
	 */
	private String affichageStock() {
		String str = "Etat du stock:\n";
		Set<Entry<String, Integer>> set = stock.entrySet();
		// Get an iterator
		Iterator<Entry<String, Integer>> i = set.iterator();
		// Display elements
		while(i.hasNext()) {
			Map.Entry<String, Integer> me = (Map.Entry<String, Integer>)i.next();
			str += me.getKey() + ": " + me.getValue()+"\n";
		}
		return str;
	}
	
	/**
	 * Renvoi un string contenant l'etat des emprunts a valider
	 * @return String
	 */
	private String affichageEmpruntAValider() {
		String str = "Emprunt a Valider:\n";
		if (aValider.isEmpty())
			return str+"aucun\n";
		for (Emprunt resa : aValider)
			str += resa+"\n";
		return str;
	}
	
	/**
	 * Renvoi un string contenant l'etat des emprunts en cours
	 * @return String
	 */
	private String affichageEmpruntEnCours() {
		String str = "Emprunt en cours:\n";
		if (empruntEnCours.isEmpty())
			return str+"aucun\n";
		for (Emprunt resa : empruntEnCours)
			str += resa+"\n";
		return str;
	}
	
	/**
	 * Renvoi un string contenant l'ensemble des reservation possible
	 * @return String
	 */
	private String affichageReservations() {
		String str = "Liste des reservations:\n";
		if (reservations.isEmpty())
			return str+"aucunes reservations\n";
		for (Reservation resa : reservations)
			str += resa+"\n";
		return str;
	}
	

	// *****METHODES******
	
	/**
	 * Valide le rendu.<br />
	 * Enleve l'emprunt a la liste {@code empruntEnCours} et actualise le {@code stock}
	 * @param emprunt
	 */
	public void rendre(Emprunt emprunt) {
		if (emprunt==null)
			return;
		if (emprunt.isActive())
			empruntEnCours.remove(emprunt);
		emprunt.switchActive();
		int nombre = stock.get(emprunt.getMateriel().toString());
		stock.remove(emprunt.getMateriel().toString());
		stock.put(emprunt.getMateriel().toString(), nombre + emprunt.getNombre());
		System.out.println("Le rendu a bien etait effectuer, merci.");
		
	}
	
	
	/**
	 * Confirme l'emprunt si {@code emprunt} different de {@code null} <br />
	 * actualise la liste {@code aValider}<br />
	 * Attention : ne modifie pas le {@code stock}
	 * @param emprunt
	 */
	public void emprunter(Emprunt emprunt) {
		if (emprunt==null)
			System.out.println("desolé, il n'y a pas assez de materiel disponible pour donner suite a votre demande." +
					"\nAbandon...");
		else {
			System.out.println("Votre emprunt a bien etait enregistre.\nEn attente d'un gestionnaire pour le valider.");
			aValider.add(emprunt);
			emprunt.getEmprunteur().addEmprunt(emprunt);
			}
	}
	/**
	 * Confirme l'emprunt si {@code reservation} different de {@code null} <br />
	 * actualise la liste {@code reservations}<br />
	 * Attention : ne modifie pas le {@code stock}
	 * @param reservation
	 */
	public void reserver(Reservation r) {
		if (r==null)
			System.out.println("desolé, il n'y a pas assez de materiel disponible pour donner suite a votre demande." +
					"\nAbandon...");
		else {
			System.out.println("Votre reservation a bien etait enregistre.");
	
		reservations.add(r);
		r.getEmprunteur().addEmprunt(r);
		}
	}

	/**
	 * Valide un emprunt, modifie le {@code stock} et la liste {@code empruntEnCours}<br />
	 * Attention : le modifie pas la liste {@code aValider} 
	 * @param emprunt
	 */
	public void valider(Emprunt emprunt) {
		empruntEnCours.add(emprunt);
		int nombre = stock.get(emprunt.getMateriel().toString());
		stock.remove(emprunt.getMateriel().toString());
		stock.put(emprunt.getMateriel().toString(), nombre - emprunt.getNombre());
		emprunt.switchActive();
	}
	
	/**
	 * Lance la validation de la liste {@code aValider}
	 */
	public void validerTout() {
		System.out.println("Liste de demande a valider :");
		for (Emprunt resa : aValider){
			System.out.println(resa);
			if (parser.askAutorisation())
				valider(resa);
			else
				resa.annulerEmprunt();
		}
		aValider = new ArrayList<Emprunt>();
	}

	/**
	 * lance le processus de verification
	 * @param date
	 */
	public void verification(Date date) {
			int choice = parser.askNumber("Que voulez vous verifier :\n1 : Emprunts en cours\n" +
					"2 : Reservations",2);
			switch (choice){
			case 1 : verifierEmprunt(date);
			case 2 : gererReservation(date);
			}
	}
	
	/**
	 * Verifie si les emprunts de {@code empruntEnCours} <br />
	 * sont termines a la Date {@code date}
	 * @param date
	 */
	@SuppressWarnings("unchecked")
	private void verifierEmprunt(Date date) {
		if (empruntEnCours.isEmpty())
			System.out.println("Aucun emprunt a recuperer");
		else {
			ArrayList<Emprunt> tmp = (ArrayList<Emprunt>) empruntEnCours.clone();
			for (Emprunt emprunt : tmp) {
				if (emprunt.getFin().before(date)){
					System.out.println("Attention : "+emprunt.toString()+" a expirer.");
					System.out.println("Recuperation automagiquement du pret en cours...");
					rendre(emprunt);
				}
			}
		}		
	}
	
	
	/**
	 * Verifie si les reservations doivent devenir active a la Date {@code date}
	 * @param date
	 */
	@SuppressWarnings("unchecked")
	private void gererReservation(Date date) {
		if (reservations.isEmpty())
			System.out.println("Aucune reservations a mettre a jour");
		else {
			ArrayList<Reservation> tmp = (ArrayList<Reservation>) reservations.clone();		
			for (Reservation resa : tmp)
				if (resa.getDebut().before(date)){
					System.out.println("Reservation a mettre a jour : "+resa);
					if (resa.getNombre() < stock.get(resa.getMateriel().toString()) && parser.askAutorisation())
							valider(resa);
					else {
							System.out.println("reservation annuler");
							reservations.remove(resa);
							resa.annulerEmprunt();
					}
				}
		}
	}
}
