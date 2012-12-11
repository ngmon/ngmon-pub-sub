package cz.muni.fi.publishsubscribe.countingtree.ac;

import cz.muni.fi.publishsubscribe.countingtree.Constraint;
import cz.muni.fi.publishsubscribe.countingtree.Predicate;
import java.util.HashMap;
import java.util.Map;

public class AccessController {
    private Map<Long, ACTree> acl = new HashMap<>();
        
    public void deny(Long userId, Constraint constraint) {
        ACTree acTree = this.acl.get(userId);
        if (acTree == null) {
            acTree = new ACTree();
        }
        acTree.add(constraint);
        acl.put(userId, acTree);
    }

    public boolean subscribe(Long userId, Predicate predicate) {
        ACTree acTree = this.acl.get(userId);
        if (acTree != null) {
            if (acTree.isAllowed(predicate)) {
                return true;
            } else {
                return false;
                //TODO dat vediet, kvoli comu to nepreslo
            }
        } else {
            return true;
        }
    }
}
