package custom.gc;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class GarbageCollector {

	private static ObjectReference root = new ObjectReference(new Object());
	private static Map<Integer, ObjectReference> allReferences = new HashMap<>();
	private static ExecutorService gcExecutor = Executors.newSingleThreadExecutor();
	private static ExecutorService finalizeExecutor = Executors.newSingleThreadExecutor();
	private static BlockingQueue<Object> refObj = new ArrayBlockingQueue<>(10000);
	private static Set<Integer> releaseObj = new HashSet<>();

	private GarbageCollector() {
	}

	static {
		TaskFinalizer<Object> finalizeTask = new TaskFinalizer<>(refObj);
		finalizeExecutor.submit(finalizeTask);
	}

	/**
	 * Function used to get Object refrence
	 * @param object
	 * @return ObjectReference
	 */
	public static ObjectReference get(Object object) {
		return createReferences(object, 0);
	}

	/**
	 * create the reference in the graph 
	 * @param object
	 * @param count
	 * @return ObjectReference
	 */
	private static ObjectReference createReferences(Object object, int count) {

		if (null == object) {
			return null;
		}
		ObjectReference reference = allReferences.get(object.hashCode());
		if (reference == null) {
			System.out.println("Create reference for Object :: " + object.getClass().getName());
			reference = new ObjectReference(object);
		}
		if (0 == count) {
			root.addReference(reference);
		}
		for (Field field : object.getClass().getDeclaredFields()) {
			if (field instanceof Object) {
				try {
					System.out.println("Create reference for Object :: " + field.getName());
					field.setAccessible(true);
					reference.addReference(createReferences(field.get(object), ++count));
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
		return reference;
	}


	/**
	 * Release the reference from the graph 
	 * @param object
	 */
	public static synchronized void release(Object object) {
		System.out.println("Release the object initiated.");
		if (null == object)
			return;
		releaseObj.add(object.hashCode());
	}


	/**
	 * 	Function to call gc for running the clean up process
	 */
	public static synchronized void runGC() {
		System.out.println("Garbage Collector process initiated.");
		//submitting task to execute
		gcExecutor.submit(new HeapOperation(root, new HashSet<Integer>(releaseObj), refObj));
		//release memory
		releaseObj.clear();
		System.out.println("Garbage Collector process completed.");
	}

}