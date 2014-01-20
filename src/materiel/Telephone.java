package materiel;
/**
 * @author M.Boutelier
 */
public class Telephone extends MaterielAvecOs {

	public Telephone(String os) {
		super(25,os);
	}
	
	public String toString() {
		return "telephone "+os;
	}

}
