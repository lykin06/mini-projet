package users;

/**
 * @author M.Boutelier
 */
public class Gestionnaire extends User {
	
	public Gestionnaire(String name) {
		super(Type.GESTIONNAIRE, name);
	}
	
	public String toString() {
		return "M." + super.getName();
	}

}
