package users;

/**
 * @author M.Boutelier
 */
public class User {
	private Type type;
	private String name;

	public User(Type type, String name) {
		this.type = type;
		this.name = name;
	}

	public Type getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public String toString() {
		return type + " " + name + ".";
	}

	public boolean equals(User user) {
		if (user.getName().equals(name) && user.getType().equals(type))
			return true;
		return false;
	}
}
