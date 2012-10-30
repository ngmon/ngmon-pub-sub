package cz.muni.fi.publishsubscribe.countingtree.matcher;

import cz.muni.fi.publishsubscribe.countingtree.index.EqualIndex;

public class StringMatcher extends AbstractMatcher<String> {

	private StringMatcher() {
		super();
		this.addOperationIndex(new EqualIndex<String>());
	}
}
