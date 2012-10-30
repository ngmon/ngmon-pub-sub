package cz.muni.fi.publishsubscribe.countingtree.matcher;

import cz.muni.fi.publishsubscribe.countingtree.Constraint;
import cz.muni.fi.publishsubscribe.countingtree.index.OperationIndex;

import java.util.List;

public interface ConstraintMatcher<T_ValueType> {

	public List<Constraint<T_ValueType>> getConstraints(T_ValueType attributeValue);
}
