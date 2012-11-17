package cz.muni.fi.publishsubscribe.countingtree.index.operator;

import cz.muni.fi.publishsubscribe.countingtree.Constraint;

import java.util.Comparator;
import java.util.List;
import java.util.NavigableMap;
import java.util.TreeMap;

public abstract class AbstractNavigableMapIndex<T1 extends Comparable<T1>> implements OperatorIndex<T1> {

	protected NavigableMap<T1, Constraint<T1>> constraints;

	protected AbstractNavigableMapIndex() {
		this(null);
	}

	protected AbstractNavigableMapIndex(Comparator<T1> comparator) {
		this.constraints = new TreeMap<>(comparator);
	}

	public boolean addConstraint(Constraint<T1> constraint) {
		T1 value = constraint.getAttributeValue().getValue();

		if (this.constraints.containsKey(value)) {
			return false;
		}

		this.constraints.put(value, constraint);

		return true;
	}

	@Override
	public boolean removeConstraint(Constraint constraint) {
		throw new RuntimeException("not implemented yet");
	}

	public abstract List<Constraint<T1>> getConstraints(T1 attributeValue);
}
