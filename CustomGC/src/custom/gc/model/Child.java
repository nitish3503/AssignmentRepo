package custom.gc.model;

public class Child {
	
	Parent parent;

	/**
	 * @param parent
	 */
	public Child(Parent parent) {
		super();
		this.parent = parent;
	}

	public void finalized() {
		System.out.println("Releasing memory for Child object");
	}

}
