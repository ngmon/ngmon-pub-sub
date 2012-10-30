package cz.muni.fi.publishsubscribe.countingtree.matcher;

import cz.muni.fi.publishsubscribe.countingtree.Constraint;
import cz.muni.fi.publishsubscribe.countingtree.Operator;
import cz.muni.fi.publishsubscribe.countingtree.index.Index;

import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public abstract class AbstractMatcher<T_ValueType> implements Index<T_ValueType> {

	private final Map<Operator, Index<T_ValueType>> operatorIndexes;

	public AbstractMatcher() {
		this.operatorIndexes = new EnumMap<Operator, Index<T_ValueType>>(Operator.class);
	}

	@Override
	public List<Constraint<T_ValueType>> getConstraints(T_ValueType attributeValue) {
		List<Constraint<T_ValueType>> constraints = new LinkedList<Constraint<T_ValueType>>();

		for (Index<T_ValueType> operatorIndex : this.operatorIndexes.values()) {
			constraints.addAll(operatorIndex.getConstraints(attributeValue));
		}

		return constraints;
	}

	@Override
	public boolean addConstraint(Constraint<T_ValueType> constraint) {

		return this.operatorIndexes.get(constraint.getOperator()).addConstraint(constraint);
	}

	protected boolean addOperationIndex(Operator operator, Index<T_ValueType> operatorIndex) {

		if (this.operatorIndexes.containsKey(operator)) {
			return false;
		}

		this.operatorIndexes.put(operator, operatorIndex);

		return true;
	}


}
