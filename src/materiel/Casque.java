package materiel;

/**
 * Les casques ne sont disponible que pendant une seule journée
 * @author M.Boutelier
 *
 */
public class Casque extends Materiel {

	public Casque() {
		super(1);
	}
	
	public String toString() {
		return "casque";
	}

}
