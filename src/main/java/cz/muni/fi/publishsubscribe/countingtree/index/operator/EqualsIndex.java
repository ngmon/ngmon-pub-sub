package cz.muni.fi.publishsubscribe.countingtree.index.operator;

import cz.muni.fi.publishsubscribe.countingtree.Constraint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

		List<Constraint<T_ValueType>> constraint = new ArrayList<Constraint<T_ValueType>>(1);

		constraint.add(this.constraints.get(attributeValue));

		return constraint;
	}
}
