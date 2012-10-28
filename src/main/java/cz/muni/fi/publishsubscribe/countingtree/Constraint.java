package cz.muni.fi.publishsubscribe.countingtree;

public class Constraint {

	private String name;
	private AttributeValue value;
	private ConstraintOperator operator;

	public Constraint(String name, AttributeValue value,
			ConstraintOperator operator) {
		this.name = name;
		this.value = value;
		this.operator = operator;
	}

	public String getName() {
		return name;
	}

	public AttributeValue getValue() {
		return value;
	}

	public ConstraintOperator getOperator() {
		return operator;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((operator == null) ? 0 : operator.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Constraint other = (Constraint) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (operator != other.operator)
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

}
