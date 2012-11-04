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

	private final Map<String, TypeIndex<?>> attributes = new HashMap<>(10);

	public <T_ValueType> boolean addConstraint(Constraint<T_ValueType> constraint) {

		String attributeName = constraint.getAttributeName();
		AttributeValue<T_ValueType> attributeValue = constraint.getAttributeValue();

		TypeIndex<T_ValueType> typeIndex = (TypeIndex<T_ValueType>) this.attributes.get(attributeName);

		if (typeIndex != null) {

			typeIndex.addConstraint(constraint);

		} else {

			typeIndex = TypeIndexFactory.getTypeIndex(attributeValue.getType());
			typeIndex.addConstraint(constraint);
			this.attributes.put(attributeName, typeIndex);
		}

		return true;
	}

	public List<Constraint<?>> getConstraints(String attributeName, AttributeValue<?> attributeValue) {

		TypeIndex typeIndex = this.attributes.get(attributeName);

		if (typeIndex == null) {
			return Collections.emptyList();
		}

		return typeIndex.getConstraints(attributeValue.getValue()); //TODO typesafe solution
	}
}
