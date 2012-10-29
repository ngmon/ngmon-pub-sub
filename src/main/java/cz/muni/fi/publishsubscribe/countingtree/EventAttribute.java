package cz.muni.fi.publishsubscribe.countingtree;

public class EventAttribute {
		private String name;
		private AttributeValue value;

		public EventAttribute(String name, AttributeValue value) {
			this.name = name;
			this.value = value;
		}

		public String getName() {
			return name;
		}

		public AttributeValue getValue() {
			return value;
		}

	}