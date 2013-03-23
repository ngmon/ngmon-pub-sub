package cz.muni.fi.publishsubscribe.countingtree;

import java.util.ArrayList;
import java.util.List;

public class EventImpl implements Event {

	private List<Attribute<? extends Comparable<?>>> attributes = new ArrayList<>();

	public boolean addAttribute(Attribute<? extends Comparable<?>> attribute) {
		return this.attributes.add(attribute);
	}

	@Override
	public List<Attribute<? extends Comparable<?>>> getAttributes() {
		return attributes;
	}

}
