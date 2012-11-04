package cz.muni.fi.publishsubscribe.countingtree.index.operator;

import cz.muni.fi.publishsubscribe.countingtree.Constraint;

import java.util.ArrayList;
import java.util.List;

public class GreaterThanOrEqualToIndex<T_ValueType extends Comparable<T_ValueType>> extends AbstractNavigableMapIndex<T_ValueType> {

	@Override
	public List<Constraint<T_ValueType>> getConstraints(T_ValueType attributeValue) {

		return new ArrayList<>(this.constraints.headMap(attributeValue, true).values());
	}
}
