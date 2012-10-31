package cz.muni.fi.publishsubscribe.countingtree.index.operator;

import cz.muni.fi.publishsubscribe.countingtree.Constraint;

import java.util.Collections;
import java.util.List;

public class LessThanOrEqualToIndex<T_ValueType extends Comparable<T_ValueType>> extends AbstractNavigableMapIndex<T_ValueType> {

	@Override
	public List<Constraint<T_ValueType>> getConstraints(T_ValueType attributeValue) {
		List<Constraint<T_ValueType>> constraintList = (List<Constraint<T_ValueType>>) this.constraints.headMap(attributeValue, true).values();

		if (constraintList == null) {
			return Collections.emptyList();
		}

		return constraintList;
	}
}
