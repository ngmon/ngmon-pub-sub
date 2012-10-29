package cz.muni.fi.publishsubscribe.countingtree.index;

import cz.muni.fi.publishsubscribe.countingtree.Constraint;

import java.util.ArrayList;
import java.util.List;

public class LessThanIndex<T_ValueType extends Comparable<T_ValueType>> extends AbstractSortedMapIndex<T_ValueType> {

	@Override
	public List<Constraint<T_ValueType>> getConstraints(T_ValueType attributeValue) {
		List<Constraint<T_ValueType>> constraintList = new ArrayList<Constraint<T_ValueType>>(this.constraints.headMap(attributeValue).values());

		Index i = new LessThanIndex<Long>();

		return constraintList;
	}
}
