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

    public boolean isAllowed(Predicate predicate) {
        for (Filter filter : predicate.getFilters()) {
            for (Constraint constraint : filter.getConstraints()) {
                if (!attributeIndex.getIntersectingConstraints(constraint).isEmpty()) {
                    return false;
                }
            }
        }
        
        return true;
    }

    public void add(Constraint constraint) {
        this.attributeIndex.addConstraint(constraint);
    }
    
}
