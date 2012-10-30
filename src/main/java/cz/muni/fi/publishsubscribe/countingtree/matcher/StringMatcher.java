package cz.muni.fi.publishsubscribe.countingtree.matcher;

import cz.muni.fi.publishsubscribe.countingtree.Operator;
import cz.muni.fi.publishsubscribe.countingtree.index.EqualsIndex;

public class StringMatcher extends AbstractMatcher<String> {

	private StringMatcher() {
		super();

		this.addOperationIndex(Operator.EQUALS, new EqualsIndex<String>());
	}
}
