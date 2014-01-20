package users;

/**
 * @author M.Boutelier
 */
public enum Type {
	ETUDIANT("etudiant"), PROFESSEUR("professeur"), GESTIONNAIRE(
			"gestionnaire");

	private String type;

	Type(String type) {
		this.type = type;
	}

	public String toString() {
		return type;
	}
}
