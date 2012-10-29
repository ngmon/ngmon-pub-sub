package cz.muni.fi.publishsubscribe.countingtree;

import java.util.ArrayList;
import java.util.List;

public class Event {

	private List<EventAttribute> attributes = new ArrayList<EventAttribute>();

	public void addAttribute(EventAttribute attribute) {
		this.attributes.add(attribute);
	}

	public List<EventAttribute> getAttributes() {
		return attributes;
	}

}
