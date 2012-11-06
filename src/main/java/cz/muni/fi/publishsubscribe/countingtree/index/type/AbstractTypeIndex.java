package cz.muni.fi.publishsubscribe.countingtree.index.type;

import cz.muni.fi.publishsubscribe.countingtree.Constraint;
import cz.muni.fi.publishsubscribe.countingtree.Operator;
import cz.muni.fi.publishsubscribe.countingtree.index.operator.OperatorIndex;

import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public abstract class AbstractTypeIndex<T_ValueType extends Comparable<?>> implements TypeIndex<T_ValueType> {

	private final Map<Operator, OperatorIndex<T_ValueType>> operatorIndexes;

	public AbstractTypeIndex() {
		this.operatorIndexes = new EnumMap<>(Operator.class);
	}

	@Override
	public List<Constraint<T_ValueType>> getConstraints(T_ValueType attributeValue) {
		List<Constraint<T_ValueType>> constraints = new LinkedList<>();

		for (OperatorIndex<T_ValueType> operatorIndex : this.operatorIndexes.values()) {
			constraints.addAll(operatorIndex.getConstraints(attributeValue));
		}

		return constraints;
	}

	@Override
	public boolean addConstraint(Constraint<T_ValueType> constraint) {

		return this.operatorIndexes.get(constraint.getOperator()).addConstraint(constraint);
	}

	@Override
	public boolean removeConstraint(Constraint<T_ValueType> constraint) {

		return this.operatorIndexes.get(constraint.getOperator()).removeConstraint(constraint);
	}

	public final boolean addOperatorIndex(Operator operator, OperatorIndex<T_ValueType> operatorIndex) {

		if (this.operatorIndexes.containsKey(operator)) {
			return false;
		}

		this.operatorIndexes.put(operator, operatorIndex);

		return true;
	}

}
