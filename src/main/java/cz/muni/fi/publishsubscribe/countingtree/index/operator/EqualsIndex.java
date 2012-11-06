package cz.muni.fi.publishsubscribe.countingtree.index.operator;

import cz.muni.fi.publishsubscribe.countingtree.Constraint;

import java.util.*;

public class EqualsIndex<T_ValueType extends Comparable<T_ValueType>>
		implements OperatorIndex<T_ValueType> {

	protected Map<Comparable<?>, Constraint> constraints = new HashMap<>();

	public boolean addConstraint(Constraint constraint) {
		Comparable<?> value = constraint.getAttributeValue().getValue();

		if (this.constraints.containsKey(value)) {
			return false;
		}

		this.constraints.put(value, constraint);

		return true;
	}

	@Override
	public boolean removeConstraint(Constraint constraint) {
		return (constraints.remove(constraint.getAttributeValue().getValue()) != null);
	}

	// Returning only single Constraint!!
	public List<Constraint> getConstraints(Comparable<?> attributeValue) {

		Constraint constraint = this.constraints.get(attributeValue);

		if (constraint == null) {
			return Collections.emptyList();
		}

		List<Constraint> constraintList = new ArrayList<>();

		constraintList.add(constraint);

		return constraintList;
	}
}
