package cz.muni.fi.publishsubscribe.countingtree.index.type;

import cz.muni.fi.publishsubscribe.countingtree.Operator;
import cz.muni.fi.publishsubscribe.countingtree.index.operator.EqualsIndex;

public class StringIndex extends AbstractTypeIndex<String> {

	public StringIndex() {
		super();

		this.addOperatorIndex(Operator.EQUALS, new EqualsIndex<String>());
	}


}
