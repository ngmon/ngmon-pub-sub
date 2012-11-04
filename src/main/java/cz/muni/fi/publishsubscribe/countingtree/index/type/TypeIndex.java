package cz.muni.fi.publishsubscribe.countingtree.index.type;

import cz.muni.fi.publishsubscribe.countingtree.Operator;
import cz.muni.fi.publishsubscribe.countingtree.index.Index;
import cz.muni.fi.publishsubscribe.countingtree.index.operator.OperatorIndex;

public interface TypeIndex<T_ValueType extends Comparable<T_ValueType>> extends Index<T_ValueType> {

	public boolean addOperatorIndex(Operator operator, OperatorIndex<T_ValueType> operatorIndex);
}
