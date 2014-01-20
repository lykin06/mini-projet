package action;

import java.util.Date;
import materiel.Materiel;
import users.*;
import gestion.*;

/**
 * @author Félix & M.Boutelier
 */
public class Action {
	private Date date;
	private Parser parser = new Parser();
	private boolean inAction;
	private Gestion gestion;
	private Gestionnaire gestionnaire;
	private User user;

	public Action(Gestion gestion) {
		date = new Date();
		System.out.println(date.toString());
		System.out.println("======================");
		inAction = false;
		this.gestion = gestion;
		gestionnaire = gestion.getGestionnaire();
	}

	public void run() {
		boolean finished = false;
		while (!finished) {
			finished = processCommand(parser.getCommand());
		}
		System.out.println("======================");
	}

	private boolean processCommand(CommandWord command) {
		boolean quit = false;

		switch (command) {
		case QUIT:
			if (inAction) {
				System.out
						.println("Quittez le session avant, entrez \"stop\".");
			} else {
				quit = true;
			}
			break;
		case NEXTDAY:
			if (inAction) {
				System.out
						.println("Quittez le session avant, entrez \"stop\".");
			} else {
				forward(1);
			}
			break;
		case HELP:
			parser.showCommands();
			break;
		case START:
			if (inAction) {
				System.out.println("Une session est en cours.");
			} else {
				System.out.println("=========== Debut Session ===========");
				startSession();
			}
			break;
		case STOP:
			if (inAction) {
				inAction = false;
				user = null;
				System.out.println("=========== Fin Session ===========");
			} else {
				System.out.println("Pas de sessions en cours");
			}
			break;
		case JUMP:
			if (inAction) {
				System.out
						.println("Quittez le session avant, entrez \"stop\".");
			} else {
				forward(parser.askNumber(
						"De combien de jours voulez-vous avancer", -1));
			}
			break;
		case AFFICHER:
			if (inAction) {
				String s = gestion.affichage(user);
				if (s != null)
					System.out.println(s);
				else
					System.out.println("Aucun emprunt en cours");
			} else {
				System.out
						.println("Vous devez etre en session pour afficher cela.");
			}
			break;
		case EMPRUNTER:
			if (inAction && user instanceof Emprunteur) {
				Emprunt r = emprunter(false);
				gestion.emprunter(r);
			} else if (!inAction) {
				System.out
						.println("Vous devez etre en session pour afficher cela.");
			} else
				System.out
						.println("Pourquoi devriez vous passer par moi, M. le Gestionnaire?\nSERVEZ VOUS!");
			break;
		case RESERVER:
			if (inAction && user instanceof Emprunteur) {
				gestion.reserver((Reservation) emprunter(true));
			} else if (!inAction) {
				System.out
						.println("Vous devez etre en session pour afficher cela.");
			} else
				System.out
						.println("Pourquoi devriez vous passer par moi, M. le Gestionnaire?\nSERVEZ VOUS!");
			break;
		case RENDRE:
			if (inAction && user instanceof Emprunteur) {
				String liste = gestion.affichage(user);
				if (liste == null) {
					System.out.println("Vous n'avez rien a rendre");
					break;
				}
				System.out.println(liste);
				gestion.rendre(parser.askMaterielARendre((Emprunteur) user));
			} else if (!inAction) {
				System.out
						.println("Vous devez etre en session pour afficher cela.");
			} else
				System.out
						.println("Pourquoi devriez vous passer par moi, M. le Gestionnaire?\nAller le ranger directement!");

			break;

		case VALIDER:
			if (inAction && user instanceof Gestionnaire) {
				gestion.validerTout();
			} else {
				System.out
						.println("vous devez etre gestionnaire pour faire cela.");
			}
			break;
		case VERIFIER:
			if (inAction && user instanceof Gestionnaire) {
				
				gestion.verification(date);
			} else {
				System.out
						.println("vous devez etre gestionnaire pour faire cela.");
			}
			break;
		default:
			System.out
					.println("Commande invalide, entrez \"help\" pour avoir la liste des commandes valides.");
		}

		return quit;
	}

	private void startSession() {
		inAction = true;

		Type type = getType();
		switch (type) {
		case GESTIONNAIRE:
			user = gestionnaire;
			break;
		default:
			String name = parser.askName();
			;
			user = gestion.getUser(name, type);
		}
		System.out.println("Bonjour " + user.toString());

	}

	private Type getType() {
		switch (parser
				.askNumber(
						"Qui-etes vous ?\n1 : professeur| 2 : etudiant| 3 : gestionnaire",
						3)) {
		case 1:
			return Type.PROFESSEUR;
		case 2:
			return Type.ETUDIANT;
		case 3:
			return Type.GESTIONNAIRE;
		default:
			return null;
		}
	}

	@SuppressWarnings("deprecation")
	private void forward(int day) {
		date.setDate(date.getDate() + day);
		System.out.println("======================");
		System.out.println(date.toString());
		System.out.println("======================");
	}

	@SuppressWarnings("deprecation")
	private Emprunt emprunter(boolean reservation) {
		Date debut = date;
		
		Materiel materiel = parser.askMateriel();
		if (reservation)
			debut = new Date(date.getYear(), date.getMonth(), date.getDate()
							+ parser.askNumber("Dans combien de jours voulez-vous l'objet",-1));
		
		int dureeMax = materiel.getDureeMax();
		if (user.getType().equals(Type.PROFESSEUR))
			dureeMax *= 2;
		Date fin = new Date(
				debut.getYear(),
				debut.getMonth(),
				debut.getDate()
						+ parser.askNumber("Pendant combien de jours voulez-vous garder l'objet (max : "
				+dureeMax+")", dureeMax));
	
		
		int quantite = 1;
		if (user.getType().equals(Type.PROFESSEUR)) {
			quantite = parser.askNumber("nombre d'objet(s) que vous voulez",-1);
		}
		if (!reservation && gestion.getNumberItemInStock(materiel.toString()) >= quantite) {
			Emprunt res = new Emprunt(fin, (Emprunteur) user, materiel, quantite);
			return res;
		}
		if (reservation && gestion.getMaxStock(materiel.toString()) >= quantite) {
			Reservation res = new Reservation(debut, fin,(Emprunteur) user, materiel, quantite);
			return res;
		}
		return null;
	}
}
