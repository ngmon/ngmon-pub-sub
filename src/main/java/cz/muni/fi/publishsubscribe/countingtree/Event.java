package cz.muni.fi.publishsubscribe.countingtree;

import java.util.ArrayList;
import java.util.List;

public class Event {

	private List<Attribute<Comparable<?>>> attributes = new ArrayList<>();

	public boolean addAttribute(Attribute<Comparable<?>> attribute) {
		return this.attributes.add(attribute);
	}

	public List<Attribute<Comparable<?>>> getAttributes() {
		return attributes;
	}

}
