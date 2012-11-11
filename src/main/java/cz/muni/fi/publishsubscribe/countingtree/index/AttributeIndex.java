package cz.muni.fi.publishsubscribe.countingtree.index;

import cz.muni.fi.publishsubscribe.countingtree.AttributeValue;
import cz.muni.fi.publishsubscribe.countingtree.Constraint;
import cz.muni.fi.publishsubscribe.countingtree.index.type.TypeIndex;
import cz.muni.fi.publishsubscribe.countingtree.index.type.TypeIndexFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttributeIndex {

	private final Map<String, TypeIndex<Comparable<?>>> attributes = new HashMap<>(10);
	private Map<Constraint, Integer> constraintCounter = new HashMap<>();

	public boolean addConstraint(Constraint<Comparable<?>> constraint) {

		String attributeName = constraint.getAttributeName();
		AttributeValue<Comparable<?>> attributeValue = constraint.getAttributeValue();

		TypeIndex<Comparable<?>> typeIndex = this.attributes
				.get(attributeName);

		if (typeIndex != null) {

			typeIndex.addConstraint(constraint);

		} else {

			typeIndex = (TypeIndex<Comparable<?>>) TypeIndexFactory.getTypeIndex(attributeValue.getType());

			typeIndex.addConstraint(constraint);
			this.attributes.put(attributeName, typeIndex);
		}

		incrementConstraintCounter(constraint);

		return true;
	}

	private void incrementConstraintCounter(Constraint<Comparable<?>> constraint) {
		Integer count = constraintCounter.get(constraint);
		if (count == null)
			constraintCounter.put(constraint, 1);
		else
			constraintCounter.put(constraint, count + 1);
	}

	/**
	 * Removes a constraint only if removeConstraint() has been called so many
	 * times as addConstraint() (for the specific Constraint) (in other words,
	 * if the associated counter is 1)
	 * @return true if the Constraint is found, false otherwise
	 */
	public boolean removeConstraint(Constraint<Comparable<?>> constraint) {
		Integer count = constraintCounter.get(constraint);
		if (count == null)
			return false;

		String attributeName = constraint.getAttributeName();
		TypeIndex<Comparable<?>> typeIndex = this.attributes.get(attributeName);
		// no index for the attribute
		if (typeIndex == null)
			return false;

		count--;

		if (count == 0) {
			typeIndex.removeConstraint(constraint);
			constraintCounter.remove(constraint);
		}
		else
			constraintCounter.put(constraint, count);

		return true;

	}

	public List<Constraint<Comparable<?>>> getConstraints(String attributeName, AttributeValue<Comparable<?>> attributeValue) {

		TypeIndex<Comparable<?>> typeIndex = this.attributes.get(attributeName);

		if (typeIndex == null) {
			return Collections.emptyList();
		}

		return typeIndex.getConstraints(attributeValue.getValue());
	}
}
