package cz.muni.fi.publishsubscribe.countingtree.index.operator;

import cz.muni.fi.publishsubscribe.countingtree.Constraint;
import java.util.List;

public class RangeIndex<T extends Comparable<T>> implements OperatorIndex<T> {
    
    //private RangeTree<T> rangeTree;
    
    
    @Override
    public boolean addConstraint(Constraint<T> constraint) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean removeConstraint(Constraint<T> constraint) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Constraint<T>> getConstraints(T attributeValue) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
