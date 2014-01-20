package materiel;
/**
 * @author M.Boutelier
 */
public class MaterielAvecOs extends Materiel {
	protected String os;

	public MaterielAvecOs(int dureeMax, String os) {
		super(dureeMax);
		this.os = os;
	}
}
