package cz.muni.fi.publishsubscribe.countingtree.index.operator;

import cz.muni.fi.publishsubscribe.countingtree.Constraint;
import java.util.List;

public class RangeIndex<T1 extends Comparable<T1>> implements OperatorIndex<T1> {
    
    //private RangeTree<T1> rangeTree;
    
    
    @Override
    public boolean addConstraint(Constraint<T1> constraint) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean removeConstraint(Constraint<T1> constraint) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Constraint<T1>> getConstraints(T1 attributeValue) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
