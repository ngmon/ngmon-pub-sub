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

	public boolean addConstraint(Constraint<Comparable<?>> constraint) {

		String attributeName = constraint.getAttributeName();
		AttributeValue<Comparable<?>> attributeValue = constraint.getAttributeValue();

		TypeIndex<Comparable<?>> typeIndex = this.attributes.get(attributeName);

		if (typeIndex != null) {

			typeIndex.addConstraint(constraint);

		} else {

			//noinspection unchecked
			typeIndex = (TypeIndex<Comparable<?>>) TypeIndexFactory.getTypeIndex(attributeValue.getType());

			typeIndex.addConstraint(constraint);
			this.attributes.put(attributeName, typeIndex);
		}

		return true;
	}

	public List<Constraint<Comparable<?>>> getConstraints(String attributeName, AttributeValue<Comparable<?>> attributeValue) {

		TypeIndex<Comparable<?>> typeIndex =  this.attributes.get(attributeName);

		if (typeIndex == null) {
			return Collections.emptyList();
		}

		return typeIndex.getConstraints(attributeValue.getValue());
	}
}
