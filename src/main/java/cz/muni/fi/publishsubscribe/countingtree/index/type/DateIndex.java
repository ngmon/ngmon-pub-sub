package cz.muni.fi.publishsubscribe.countingtree.index.type;

import java.util.Date;

import cz.muni.fi.publishsubscribe.countingtree.Operator;
import cz.muni.fi.publishsubscribe.countingtree.index.operator.RangeIndex;

public class DateIndex extends AbstractTypeIndex<Date> {

	public DateIndex() {
		super();
		
		this.addOperatorIndex(Operator.RANGE, new RangeIndex<Date>());
	}
}
