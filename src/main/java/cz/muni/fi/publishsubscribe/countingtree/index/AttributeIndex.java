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

	private final Map<String, TypeIndex<? extends Comparable<?>>> attributes = new HashMap<>(10);

	public boolean addConstraint(Constraint constraint) {

		String attributeName = constraint.getAttributeName();
		AttributeValue attributeValue = constraint.getAttributeValue();

		TypeIndex<? extends Comparable<?>> typeIndex = this.attributes.get(attributeName);

		if (typeIndex != null) {

			typeIndex.addConstraint(constraint);

		} else {

			typeIndex = TypeIndexFactory.getTypeIndex(attributeValue.getType());

			typeIndex.addConstraint(constraint);
			this.attributes.put(attributeName, typeIndex);
		}

		return true;
	}

	public List<Constraint> getConstraints(String attributeName, AttributeValue attributeValue) {

		TypeIndex<? extends Comparable<?>> typeIndex =  this.attributes.get(attributeName);

		if (typeIndex == null) {
			return Collections.emptyList();
		}

		return typeIndex.getConstraints(attributeValue.getValue());
	}
}
