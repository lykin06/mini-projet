package action;

/**
 * @author M.Boutelier
 */
public enum CommandWord {
	UNKNOWN(null), STOP("stop"), START("start"), HELP("help"), NEXTDAY("next"), QUIT(
			"exit"), JUMP("jump"), EMPRUNTER("emprunter"), RENDRE("rendre"), RESERVER(
			"reserver"), AFFICHER("afficher"), VALIDER("valider"), VERIFIER("verifier");

	private String commandString;

	CommandWord(String commandString) {
		this.commandString = commandString;
	}

	public String toString() {
		return commandString;
	}
}
