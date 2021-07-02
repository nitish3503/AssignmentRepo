package custom.gc;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.BlockingQueue;

public class HeapOperation implements Runnable {

	private ObjectReference root;
	private Set<Integer> releaseObj;
	private BlockingQueue<Object> refQ;

	public HeapOperation(ObjectReference root, Set<Integer> releaseObj, BlockingQueue<Object> referenceQueue) {
		this.root = root;
		this.releaseObj = releaseObj;
		this.refQ = referenceQueue;
	}

	@Override
	public void run() {
		System.out.println("Starting GC Task thread");
		Set<Integer> markSetBit = new HashSet<>();
		mark(root, markSetBit);
		sweepReference(root, markSetBit);
	}

	// remove the unused reference .
	private ObjectReference sweepReference(ObjectReference root, Set<Integer> markSet) {
		Object obj = root.getObject();

		int hashCode = System.identityHashCode(obj);

		Set<ObjectReference> deleteReferences = new HashSet<>();
		for (ObjectReference reference : root.getReferences()) {
			if (sweepReference(reference, markSet) == null)
				deleteReferences.add(reference);
		}

		addObjectToQueue(deleteReferences);

		root.getReferences().removeAll(deleteReferences);

		if (markSet.contains(hashCode))
			return root;
		return null;
	}

//add release reference to queue to finalize the object
	private void addObjectToQueue(Set<ObjectReference> deleteReferences) {
		for (ObjectReference reference : deleteReferences) {
			try {
				if (reference.getObject().getClass().getDeclaredMethod("finalize") == null)
					continue;
				refQ.add(reference.getObject());
			} catch (NoSuchMethodException e) {
			}
		}
	}

	// mark the used reference
	private void mark(ObjectReference root, Set<Integer> markSet) {

		Object obj = root.getObject();
		int hashCode = System.identityHashCode(obj);
		if (releaseObj.contains(hashCode)) {
			return;
		} else if (!markSet.add(hashCode)) {
			return;
		}
		for (ObjectReference reference : root.getReferences()) {
			mark(reference, markSet);
		}
	}

}