package cz.muni.fi.publishsubscribe.countingtree.index.type;

import cz.muni.fi.publishsubscribe.countingtree.Operator;
import cz.muni.fi.publishsubscribe.countingtree.index.Index;
import cz.muni.fi.publishsubscribe.countingtree.index.operator.OperatorIndex;

public interface TypeIndex<T1 extends Comparable<T1>> extends Index<T1> {

	public boolean addOperatorIndex(Operator operator, OperatorIndex<T1> operatorIndex);
}
