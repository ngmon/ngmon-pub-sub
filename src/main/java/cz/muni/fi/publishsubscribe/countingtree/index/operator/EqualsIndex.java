package cz.muni.fi.publishsubscribe.countingtree.index.operator;

import cz.muni.fi.publishsubscribe.countingtree.Constraint;

import java.util.*;

public class EqualsIndex<T_ValueType> implements OperatorIndex<T_ValueType> {

	protected Map<T_ValueType, Constraint<T_ValueType>> constraints = new HashMap<T_ValueType, Constraint<T_ValueType>>();

	public boolean addConstraint(Constraint<T_ValueType> constraint) {
		T_ValueType value = constraint.getAttributeValue().getValue();

		if (this.constraints.containsKey(value)) {
			return false;
		}

		this.constraints.put(value, constraint);

		return true;
	}

	// Returning only single Constraint!!
	public List<Constraint<T_ValueType>> getConstraints(T_ValueType attributeValue) {

		Constraint<T_ValueType> constraint = this.constraints.get(attributeValue);

		if (constraint == null) {
			return Collections.emptyList();
		}

		List<Constraint<T_ValueType>> constraintList = new ArrayList<Constraint<T_ValueType>>();

		constraintList.add(constraint);

		return constraintList;
	}
}
