package action;

import gestion.Emprunt;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

import materiel.Cameras;
import materiel.Casque;
import materiel.Materiel;
import materiel.Tablette;
import materiel.Telephone;
import users.*;

/**
 * 
 * @author Félix & M.Boutelier
 */
public class Parser {
	private ValidCommand commands;
	private Scanner reader;
	private String word;
	private int number;

	public Parser() {
		commands = new ValidCommand();
		reader = new Scanner(System.in);
	}

	/**
	 * @return The next command from the user.
	 */
	public CommandWord getCommand() {
		System.out.println("Que voulez-vous faire ?");
		System.out.print("> ");
		word = reader.nextLine();
		return commands.getCommandWord(word);
	}

	/**
	 * Print out a list of valid command words.
	 */
	public void showCommands() {
		commands.showAll();
	}

	/**
	 * To get the number of things you asked
	 * 
	 * @param asked
	 * @return number
	 */
	public int askNumber(String question, int maxValu){
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		while (true) {
			System.out.println(question);
			System.out.print("> ");
			try {
				number = Integer.parseInt(reader.readLine());
				if (number < 1 || (maxValu != -1 && number > maxValu) )
					throw new Exception();
				break;
			} catch (Exception e) {System.out.println("entre invalide");;}
		}
		return number;
	}

	/**
	 * @return the user's name
	 */
	public String askName() {
		word = "";
		while (word == "") {
			System.out.println("Quel est votre nom ?");
			System.out.print("> ");
			word = reader.nextLine();
		}
		return word;
	}


	/**
	 * Prompteur pour obtenir un materiel contenu dans la
	 * liste d'emprunt d'user
	 * @param user Emprunteur voulant rendre quelquechose
	 * @return Emprunt a rendre
	 */
	public Emprunt askMaterielARendre(Emprunteur user) {
		word = "";
		while (!user.isInTheList(word)) {
			System.out.println("Que voulez-vous rendre ?");
			System.out.print("> ");
			word = reader.nextLine();
		}

		return user.rendre(word);
	}
	
	/**
	 * Prompteur pour obtenir un materiel existant
	 * @return Materiel choisi par l'utilisateur
	 */
	public Materiel askMateriel() {
		while (true) {

			System.out.println("Quel materiel vous interresse ?");
			System.out.print("> ");
			word = reader.nextLine();
			String OS;
			switch (word) {
			case "camera":
				return new Cameras();
			case "casque":
				return new Casque();
			case "telephone":
				do {
					OS = askOS();
				} while(!(OS.equals("apple") || OS.equals("android")));
				return new Telephone(OS);
			case "tablette":
				do {
					OS = askOS();
				} while(!(OS.equals("apple") || OS.equals("android")));
				return new Tablette(OS);
			case "help":
				System.out.println("commandes possibles : camera, casque, telephone, tablette");
				break;
			default:
				System.out
						.println("Choix invalide, tapez \"help\" pour afficher les choix possible");

			}
		}
	}

	private String askOS() {
		System.out.print("De quelle marque : android ou apple?\n> ");
		return reader.nextLine();
	}

	/**
	 * Demande si l'emprunt precedent est validé ou non
	 * @return boolean
	 */
	public boolean askAutorisation() {
		System.out.print("Voulez vous autoriser cette emprunt : (y/n)");
		String word = reader.nextLine();
		switch (word) {
		case "y":
		case "Y":
		case "O":
		case "o":
			return true;
		case "N":
		case "n":
			return false;
		}
		System.out.println("PAS COMPRIS, emprunt non autoriser");
		return false;
	}
}
