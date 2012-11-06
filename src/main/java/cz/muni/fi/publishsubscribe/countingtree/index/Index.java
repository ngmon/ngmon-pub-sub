package cz.muni.fi.publishsubscribe.countingtree.index;

import cz.muni.fi.publishsubscribe.countingtree.Constraint;

import java.util.List;

public interface Index<T_ValueType extends Comparable<T_ValueType>> {

	public boolean addConstraint(Constraint constraint);
	public boolean removeConstraint(Constraint constraint);
	public List<Constraint> getConstraints(Comparable<?> attributeValue);

}
