package cz.muni.fi.publishsubscribe.countingtree.index.operator;

import cz.muni.fi.publishsubscribe.countingtree.Constraint;

import java.util.ArrayList;
import java.util.List;

public class GreaterThanIndex<T1 extends Comparable<T1>> extends AbstractNavigableMapIndex<T1> {

	@Override
	public List<Constraint<T1>> getConstraints(T1 attributeValue) {

		return new ArrayList<>(this.constraints.headMap(attributeValue, false).values());
	}
}
