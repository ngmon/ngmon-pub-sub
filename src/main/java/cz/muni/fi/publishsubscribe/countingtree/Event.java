package cz.muni.fi.publishsubscribe.countingtree;

import java.util.List;

public interface Event {

	public List<Attribute<? extends Comparable<?>>> getAttributes();

}