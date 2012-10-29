package cz.muni.fi.publishsubscribe.countingtree;

public class Constraint {

	private String name;
	private AttributeValue attributeValue;
	private Operator operator;

	public Constraint(String name, AttributeValue attributeValue,
	                  Operator operator) {
		this.name = name;
		this.attributeValue = attributeValue;
		this.operator = operator;
	}

	public String getName() {
		return name;
	}

	public AttributeValue getAttributeValue() {
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

		if (!name.equals(that.name)) return false;
		if (operator != that.operator) return false;
		if (!attributeValue.equals(that.attributeValue)) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = name.hashCode();
		result = 31 * result + attributeValue.hashCode();
		result = 31 * result + operator.hashCode();
		return result;
	}
}
