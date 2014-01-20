package materiel;

/**
 * Les cameras ne scont disponibles que pendant une seule journee
 * @author M.Boutelier
 *
 */
public class Cameras extends Materiel {

	public Cameras() {
		super(1);
	}
	
	public String toString() {
		return "camera";
	}

}
