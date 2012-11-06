package cz.muni.fi.publishsubscribe.countingtree.index.operator;

import java.util.ArrayList;
import java.util.List;

import cz.muni.fi.publishsubscribe.countingtree.Constraint;

public class PrefixIndex<T_ValueType extends Comparable<T_ValueType>>
		implements OperatorIndex<T_ValueType> {

	@Override
	public boolean addConstraint(Constraint constraint) {
		// TODO - implement
		throw new UnsupportedOperationException("not yet implemented");
	}

	@Override
	public boolean removeConstraint(Constraint constraint) {
		// TODO - implement
		throw new UnsupportedOperationException("not yet implemented");
	}

	@Override
	public List<Constraint> getConstraints(Comparable<?> attributeValue) {
		// TODO - implement
		return new ArrayList<>();
	}

}
