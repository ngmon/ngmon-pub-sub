package cz.muni.fi.publishsubscribe.countingtree.index;

import cz.muni.fi.publishsubscribe.countingtree.Constraint;

import java.util.List;

public interface Index<T_ValueType extends Comparable<T_ValueType>> {

	public boolean addConstraint(Constraint<T_ValueType> constraint);
	public boolean removeConstraint(Constraint<T_ValueType> constraint);
	public List<Constraint<T_ValueType>> getConstraints(T_ValueType attributeValue);

}
