package cz.muni.fi.publishsubscribe.countingtree.matcher;

import cz.muni.fi.publishsubscribe.countingtree.Constraint;
import cz.muni.fi.publishsubscribe.countingtree.index.OperationIndex;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public abstract class AbstractMatcher<T_ValueType> implements ConstraintMatcher<T_ValueType>  {

	private final List<OperationIndex<T_ValueType>> operationIndexes;

	public AbstractMatcher() {
		this.operationIndexes = new ArrayList<OperationIndex<T_ValueType>>(3);
	}

	@Override
	public List<Constraint<T_ValueType>> getConstraints(T_ValueType attributeValue) {
		List<Constraint<T_ValueType>> constraints = new LinkedList<Constraint<T_ValueType>>();

		for (OperationIndex<T_ValueType> operationIndex : operationIndexes) {
			constraints.addAll(operationIndex.getConstraints(attributeValue));
		}

		return constraints;
	}

	public boolean addOperationIndex(OperationIndex<T_ValueType> operationIndex) {

		return this.operationIndexes.add(operationIndex);
	}
}
