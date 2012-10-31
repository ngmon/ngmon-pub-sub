package cz.muni.fi.publishsubscribe.countingtree;

public class Constraint<T_ConstraintType> {

	private String attributeName;
	private Operator operator;
	private AttributeValue<T_ConstraintType> attributeValue;

	public Constraint(String attributeName, AttributeValue<T_ConstraintType> attributeValue,
	                  Operator operator) {

		this.attributeName = attributeName;
		this.attributeValue = attributeValue;
		this.operator = operator;

		if (!this.allowedOperatorForType(operator, attributeValue.getType())) {
			throw new IllegalArgumentException(String.format("Unsupported Operator: %s for Class: %s", operator.toString(), attributeValue.getType()));
		}
	}

	private boolean allowedOperatorForType(Operator operator, Class type) {

		// TODO implement better

		if (type.equals(String.class)) {

			switch (operator) {
				case EQUALS: return true;
				default: return false;
			}

		} else if (type.equals(Long.class)) {

			switch (operator) {
				case EQUALS: return true;
				case LESS_THAN: return true;
				case LESS_THAN_OR_EQUAL_TO: return true;
				case GREATER_THAN: return true;
				case GREATER_THAN_OR_EQUAL_TO: return true;
				default: return false;
			}

		} else {
			return false;
		}

	}

	public String getAttributeName() {
		return attributeName;
	}

	public AttributeValue<T_ConstraintType> getAttributeValue() {
		return attributeValue;
	}

	public Operator getOperator() {
		return operator;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Constraint that = (Constraint) o;

		if (!attributeName.equals(that.attributeName)) return false;
		if (operator != that.operator) return false;
		if (!attributeValue.equals(that.attributeValue)) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = attributeName.hashCode();
		result = 31 * result + attributeValue.hashCode();
		result = 31 * result + operator.hashCode();
		return result;
	}
}
