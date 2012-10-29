package cz.muni.fi.publishsubscribe.countingtree.index;

import cz.muni.fi.publishsubscribe.countingtree.Constraint;

import java.util.ArrayList;
import java.util.List;

public class GreaterThanOrEqualsIndex<T_ValueType> extends AbstractSortedMapIndex<T_ValueType> {

	@Override
	public List<Constraint<T_ValueType>> getConstraints(T_ValueType attributeValue) {
		List<Constraint<T_ValueType>> constraintList = new ArrayList<Constraint<T_ValueType>>(this.constraints.tailMap(attributeValue).values());

		return constraintList;
	}
}
