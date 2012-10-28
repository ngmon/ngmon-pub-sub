package cz.muni.fi.publishsubscribe.countingtree;

import java.util.ArrayList;
import java.util.List;

public class Event {

	public static class EventAttribute {
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

	private List<EventAttribute> attributes = new ArrayList<EventAttribute>();

	public void addAttribute(EventAttribute attribute) {
		this.attributes.add(attribute);
	}

	public List<EventAttribute> getAttributes() {
		return attributes;
	}

}
