package cz.muni.fi.publishsubscribe.countingtree;

public class AttributeValue<T_ValueType> {

	private final Class<T_ValueType> type;
	private final T_ValueType value;

	public AttributeValue(T_ValueType value, Class<T_ValueType> type) {
		this.value = value;
		this.type = type;
	}

	public T_ValueType getValue() {
		return value;
	}

	public Class<T_ValueType> getType() {
		return type;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		AttributeValue that = (AttributeValue) o;

		if (!type.equals(that.type)) return false;
		if (value != null ? !value.equals(that.value) : that.value != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = type.hashCode();
		result = 31 * result + (value != null ? value.hashCode() : 0);
		return result;
	}
}
