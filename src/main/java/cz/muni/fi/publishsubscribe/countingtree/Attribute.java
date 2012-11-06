package cz.muni.fi.publishsubscribe.countingtree;

public class Attribute<T1 extends Comparable<?>> {

		private String name;

		private AttributeValue<T1> value;

		public Attribute(String name, AttributeValue<T1> value) {
			this.name = name;
			this.value = value;
		}

		public String getName() {
			return name;
		}

		public AttributeValue<T1> getValue() {
			return value;
		}

	}