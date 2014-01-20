package main;

import gestion.Gestion;
import action.Action;

/**
 * <b>Classe principale du programme</b>
 * Lance le programme
 * @author M.Boutelier
 */
public class Launcher {
	public static void main(String [] args) {
		Gestion gestion = new Gestion();
		Action action = new Action(gestion);
		action.run();
	}
}
