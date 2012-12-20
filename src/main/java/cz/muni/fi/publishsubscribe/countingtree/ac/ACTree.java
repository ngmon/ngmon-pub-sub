package cz.muni.fi.publishsubscribe.countingtree.ac;

import cz.muni.fi.publishsubscribe.countingtree.Constraint;
import cz.muni.fi.publishsubscribe.countingtree.Filter;
import cz.muni.fi.publishsubscribe.countingtree.Predicate;
import cz.muni.fi.publishsubscribe.countingtree.index.AttributeIndex;

public class ACTree {
    
    private Long userId;
    private AttributeIndex attributeIndex = new AttributeIndex();

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public <T extends Comparable<?>> boolean isAllowed(Predicate predicate) { //TODO generics?
        for (Filter filter : predicate.getFilters()) {
            for (Constraint<?> constraint : filter.getConstraints()) {
                if (!attributeIndex.getConflictingConstraints(constraint).isEmpty()) {
                    return false;
                }
            }
        }
        
        return true;
    }

    public <T extends Comparable<T>> void add(Constraint<T> constraint) {
        this.attributeIndex.addConstraint(constraint);
    }
    
}
