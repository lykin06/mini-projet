package materiel;
/**
 * @author M.Boutelier
 */
public class Tablette extends MaterielAvecOs {

	public Tablette(String os) {
		super(365, os);
	}
	
	public String toString() {
		return "tablette "+os;
	}

}
