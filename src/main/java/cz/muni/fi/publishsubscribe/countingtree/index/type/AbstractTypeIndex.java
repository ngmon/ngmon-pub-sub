package cz.muni.fi.publishsubscribe.countingtree.index.type;

import cz.muni.fi.publishsubscribe.countingtree.Constraint;
import cz.muni.fi.publishsubscribe.countingtree.Operator;
import cz.muni.fi.publishsubscribe.countingtree.index.operator.OperatorIndex;

import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public abstract class AbstractTypeIndex<T_ValueType extends Comparable<T_ValueType>>
		implements TypeIndex<T_ValueType> {

	private final Map<Operator, OperatorIndex<T_ValueType>> operatorIndexes;
	private final Class<T_ValueType> type;

	public AbstractTypeIndex(Class<T_ValueType> type) {
		this.type = type;
		this.operatorIndexes = new EnumMap<>(Operator.class);
	}

	@Override
	public List<Constraint> getConstraints(Comparable<?> attributeValue) {
		List<Constraint> constraints = new LinkedList<>();

		for (OperatorIndex<T_ValueType> operatorIndex : this.operatorIndexes
				.values()) {
			constraints.addAll(operatorIndex.getConstraints(attributeValue));
		}

		return constraints;
	}

	private void checkType(Constraint constraint) {
		if (!this.type.equals(constraint.getAttributeValue().getType())) {
			throw new IllegalArgumentException(String.format(
					"The AttributeValue type should be %s, but it is %s",
					this.type, constraint.getAttributeValue().getType()));
		}
	}

	@Override
	public boolean addConstraint(Constraint constraint) {
		checkType(constraint);
		return this.operatorIndexes.get(constraint.getOperator())
				.addConstraint(constraint);
	}

	@Override
	public boolean removeConstraint(Constraint constraint) {
		checkType(constraint);
		return this.operatorIndexes.get(constraint.getOperator())
				.removeConstraint(constraint);
	}

	public final boolean addOperatorIndex(Operator operator,
			OperatorIndex<T_ValueType> operatorIndex) {

		if (this.operatorIndexes.containsKey(operator)) {
			return false;
		}

		this.operatorIndexes.put(operator, operatorIndex);

		return true;
	}

}
