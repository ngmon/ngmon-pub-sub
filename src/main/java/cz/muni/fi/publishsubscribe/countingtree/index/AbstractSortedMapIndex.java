package cz.muni.fi.publishsubscribe.countingtree.index;

import cz.muni.fi.publishsubscribe.countingtree.Constraint;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public abstract class AbstractSortedMapIndex<T_ValueType> implements Index<T_ValueType> {

	protected SortedMap<T_ValueType, Constraint<T_ValueType>> constraints = new TreeMap<T_ValueType, Constraint<T_ValueType>>();

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
