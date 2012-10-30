package cz.muni.fi.publishsubscribe.countingtree.matcher;

import cz.muni.fi.publishsubscribe.countingtree.Operator;
import cz.muni.fi.publishsubscribe.countingtree.index.*;

public class LongMatcher extends AbstractMatcher<Long> {

	public LongMatcher() {
		super();

		this.addOperationIndex(Operator.EQUALS, new EqualsIndex<Long>());
		this.addOperationIndex(Operator.LESS_THAN, new LessThanIndex<Long>());
		this.addOperationIndex(Operator.LESS_THAN_OR_EQUAL_TO, new LessThanOrEqualToIndex<Long>());
		this.addOperationIndex(Operator.GREATER_THAN, new GreaterThanIndex<Long>());
		this.addOperationIndex(Operator.GREATER_THAN_OR_EQUAL_TO, new GreaterThanOrEqualToIndex<Long>());
	}
}
