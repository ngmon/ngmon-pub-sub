package cz.muni.fi.publishsubscribe.countingtree;

public class Constraint<T1 extends Comparable<T1>> {

	private String attributeName;
	private Operator operator;
	private AttributeValue<T1> attributeValue;

	public Constraint(String attributeName, AttributeValue<T1> attributeValue,
	                  Operator operator) {

		this.attributeName = attributeName;
		this.attributeValue = attributeValue;
		this.operator = operator;

		if (!Operator.getSupportedOperators(attributeValue.getType()).contains(operator)) {
			throw new IllegalArgumentException(String.format("Unsupported Operator: %s for Class: %s", operator.toString(), attributeValue.getType()));
		}
	}

	public String getAttributeName() {
		return attributeName;
	}

	public AttributeValue<T1> getAttributeValue() {
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
