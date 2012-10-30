package cz.muni.fi.publishsubscribe.countingtree.matcher;

import cz.muni.fi.publishsubscribe.countingtree.index.*;

public class LongMatcher extends AbstractMatcher<Long> {

	public LongMatcher() {
		super();

		this.addOperationIndex(new EqualIndex<Long>());
		this.addOperationIndex(new LessThanIndex<Long>());
		this.addOperationIndex(new LessThanOrEqualToIndex<Long>());
		this.addOperationIndex(new GreaterThanIndex<Long>());
		this.addOperationIndex(new GreaterThanOrEqualToIndex<Long>());
	}
}
