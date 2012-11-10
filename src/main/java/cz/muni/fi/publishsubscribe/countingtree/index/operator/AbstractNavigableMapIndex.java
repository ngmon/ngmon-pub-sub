package cz.muni.fi.publishsubscribe.countingtree.index.operator;

import cz.muni.fi.publishsubscribe.countingtree.Constraint;

import java.util.Comparator;
import java.util.List;
import java.util.NavigableMap;
import java.util.TreeMap;

public abstract class AbstractNavigableMapIndex<T_ValueType extends Comparable<?>> implements OperatorIndex<T_ValueType> {

	protected NavigableMap<T_ValueType, Constraint<T_ValueType>> constraints;

	protected AbstractNavigableMapIndex() {
		this(null);
	}

	protected AbstractNavigableMapIndex(Comparator<T_ValueType> comparator) {
		this.constraints = new TreeMap<>(comparator);
	}

	public boolean addConstraint(Constraint<T_ValueType> constraint) {
		T_ValueType value = constraint.getAttributeValue().getValue();

		if (this.constraints.containsKey(value)) {
			return false;
		}

		this.constraints.put(value, constraint);

		return true;
	}

	@Override
	public boolean removeConstraint(Constraint<T_ValueType> constraint) {
		Comparable<?> value = constraint.getAttributeValue().getValue();
		return this.constraints.remove(value) != null;
	}

	public abstract List<Constraint<T_ValueType>> getConstraints(T_ValueType attributeValue);
}
