package cz.muni.fi.publishsubscribe.countingtree.index;

import cz.muni.fi.publishsubscribe.countingtree.Constraint;

import java.util.List;

public interface Index<T1 extends Comparable<T1>> {

	public boolean addConstraint(Constraint<T1> constraint);
	public boolean removeConstraint(Constraint<T1> constraint);
	public List<Constraint<T1>> getConstraints(T1 attributeValue);
        public List<Constraint<T1>> getConflictingConstraints(Constraint<T1> constraint);

}
