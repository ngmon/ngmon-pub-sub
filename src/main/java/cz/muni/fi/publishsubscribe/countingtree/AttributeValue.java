package cz.muni.fi.publishsubscribe.countingtree;

public class AttributeValue {

	private final Comparable<?> value;
	private final Class<? extends Comparable<?>> type;


	public <T1 extends Comparable<?>> AttributeValue(T1 value, Class<T1> type) {
		this.value = value;
		this.type = type;
	}

	public Comparable<?> getValue() {
		return value;
	}

	public Class<? extends Comparable<?>> getType() {
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
