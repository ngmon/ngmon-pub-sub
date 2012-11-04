package cz.muni.fi.publishsubscribe.countingtree;

import java.util.ArrayList;
import java.util.List;

public class Event {

	private List<Attribute<?>> attributes = new ArrayList<>();

	public void addAttribute(Attribute<?> attribute) {
		this.attributes.add(attribute);
	}

	public List<Attribute<?>> getAttributes() {
		return attributes;
	}

}
