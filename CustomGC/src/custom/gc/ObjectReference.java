package custom.gc;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


public class ObjectReference {

    private Object object;
    private Set<ObjectReference> references;

    public ObjectReference(Object object) {
        this.object = object;
        references = new HashSet<>();
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public Set<ObjectReference> getReferences() {
        return references;
    }

    public void addReference(ObjectReference reference) {
        this.references.add(reference);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ObjectReference reference = (ObjectReference) o;
        return Objects.equals(object, reference.object);
    }

    @Override
    public int hashCode() {
        return Objects.hash(object);
    }
}