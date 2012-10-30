package cz.muni.fi.publishsubscribe.countingtree.index;

import cz.muni.fi.publishsubscribe.countingtree.Constraint;

import java.util.*;

public abstract class AbstractNavigableMapIndex<T_ValueType extends Comparable<T_ValueType>> implements OperationIndex<T_ValueType> {

	protected NavigableMap<T_ValueType, Constraint<T_ValueType>> constraints;

	protected AbstractNavigableMapIndex() {
		this(null);
	}

	protected AbstractNavigableMapIndex(Comparator<T_ValueType> comparator) {
		this.constraints =  new TreeMap<T_ValueType, Constraint<T_ValueType>>(comparator);
	}

	public boolean addConstraint(Constraint<T_ValueType> constraint) {
		T_ValueType value = constraint.getAttributeValue().getValue();

		if (this.constraints.containsKey(value)) {
			return false;
		}

		this.constraints.put(value, constraint);

		return true;
	}

	public abstract List<Constraint<T_ValueType>> getConstraints(T_ValueType attributeValue);
}
