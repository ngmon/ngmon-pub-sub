package cz.muni.fi.publishsubscribe.countingtree;

public class Attribute<T_ValueType> {

		private String name;

		private AttributeValue<T_ValueType> value;

		public Attribute(String name, AttributeValue<T_ValueType> value) {
			this.name = name;
			this.value = value;
		}

		public String getName() {
			return name;
		}

		public AttributeValue<T_ValueType> getValue() {
			return value;
		}

	}