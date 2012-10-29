package cz.muni.fi.publishsubscribe.countingtree;

public class AttributeValue {

	private AttributeValueType type;

	private Comparable<?> comparable;
	private String string;

	public AttributeValueType getType() {
		return type;
	}

	public Comparable<?> getComparable() {
		return comparable;
	}

	public String getString() {
		return string;
	}
	
	public AttributeValue(Comparable<?> comparable) {
		this.type = AttributeValueType.NUMBER;
		this.comparable = comparable;
	}
	
	public AttributeValue(String string) {
		this.type = AttributeValueType.STRING;
		this.string = string;
	}

	@Override
	public int hashCode() {
		// TODO - value must be present after the object is constructed
		if (type.equals(AttributeValueType.NUMBER))
			return comparable.hashCode();
		else
			return string.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AttributeValue other = (AttributeValue) obj;
		
		// TODO - all values must be entered
		if (type.equals(AttributeValueType.NUMBER))
			return comparable.equals(other.getComparable());
		else
			return string.equals(other.getString());
	}
	
	

}
