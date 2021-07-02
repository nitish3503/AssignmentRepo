package custom.gc;

import custom.gc.model.Child;
import custom.gc.model.Parent;

public class GarbageRunner {

	/**
	 * test garbage collector for reference (from memory )
	 */
	public static void executeGCForCustomClass() {
		try {
			System.out.println("executeGCForCustomClass");
			Parent parent = new Parent();
			GarbageCollector.get(parent);
			GarbageCollector.release(parent);
			GarbageCollector.runGC();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * test garbage collector with has-a relation ship
	 */
	public static void executeGCForHierachyClass() {
		try {
			System.out.println("executeGCForHierachyClass");
			Parent parent = new Parent();
			Child firstChild = new Child(parent);
			Child secondChild = new Child(parent);
			GarbageCollector.get(firstChild);
			GarbageCollector.get(secondChild);
			GarbageCollector.release(secondChild);
			GarbageCollector.runGC();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		System.out.println("Starting Garbage Collector Process");
		executeGCForCustomClass();
		executeGCForHierachyClass();
	}

}
