package cz.muni.fi.publishsubscribe.countingtree.index.operator;

import cz.muni.fi.publishsubscribe.countingtree.Constraint;
import cz.muni.fi.publishsubscribe.countingtree.Range;

import java.util.*;

public class EqualsIndex<T1 extends Comparable<T1>> implements OperatorIndex<T1> {

	protected Map<T1, Constraint<T1>> constraints = new HashMap<>();

	public boolean addConstraint(Constraint<T1> constraint) {

		T1 value = constraint.getAttributeValue().getValue();

		if (this.constraints.containsKey(value)) {
			return false;
		}

		this.constraints.put(value, constraint);

		return true;
	}

	@Override
	public boolean removeConstraint(Constraint<T1> constraint) {
		return (constraints.remove(constraint.getAttributeValue().getValue()) != null);
	}

	// Returning only single Constraint!!
	public List<Constraint<T1>> getConstraints(T1 attributeValue) {

		Constraint<T1> constraint = this.constraints.get(attributeValue);

		if (constraint == null) {
			return Collections.emptyList();
		}

		List<Constraint<T1>> constraintList = new ArrayList<>();

		constraintList.add(constraint);

		return constraintList;
	}

        @Override
        public List<Constraint<T1>> getIntersectingConstraints(Constraint<T1> constraint) {
            List<Constraint<T1>> conflicts = new ArrayList<>();
            switch (constraint.getOperator()) {
                case LESS_THAN:
                    for (T1 value : constraints.keySet()) {
                        if (value.compareTo(constraint.getAttributeValue().getValue()) < 0) {
                            conflicts.add(constraints.get(value));
                            return conflicts;
                        }
                    }
                    break;
                case LESS_THAN_OR_EQUAL_TO:
                    for (T1 value : constraints.keySet()) {
                        if (value.compareTo(constraint.getAttributeValue().getValue()) <= 0) {
                            conflicts.add(constraints.get(value));
                            return conflicts;
                        }
                    }
                    break;
                case GREATER_THAN:
                    for (T1 value : constraints.keySet()) {
                        if (value.compareTo(constraint.getAttributeValue().getValue()) > 0) {
                            conflicts.add(constraints.get(value));
                            return conflicts;
                        }
                    }
                    break;
                case GREATER_THAN_OR_EQUAL_TO:
                    for (T1 value : constraints.keySet()) {
                        if (value.compareTo(constraint.getAttributeValue().getValue()) >= 0) {
                            conflicts.add(constraints.get(value));
                            return conflicts;
                        }
                    }
                    break;
                case EQUALS:
                    return this.getConstraints(constraint.getAttributeValue().getValue());
                case RANGE:
                    for (T1 value : constraints.keySet()) {
                        if (((Range<T1>)(constraint.getAttributeValue().getValue())).contains(value)) {
                            conflicts.add(constraints.get(value));
                            return conflicts;
                        }
                    }
                    break;
                case PREFIX:
                    String prefix = (String)(constraint.getAttributeValue().getValue());
                    for (T1 value : constraints.keySet()) {
                        if (prefix.length() <= ((String)value).length()) {
                            if (((String)value).startsWith(prefix)) {
                                conflicts.add(constraints.get(value));
                                return conflicts;
                            }
                        }
                    }
                    break;
            }
            
            return conflicts;
        }
}
