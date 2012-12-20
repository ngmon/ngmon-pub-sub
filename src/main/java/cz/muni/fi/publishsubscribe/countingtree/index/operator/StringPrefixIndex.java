package cz.muni.fi.publishsubscribe.countingtree.index.operator;

import cz.muni.fi.publishsubscribe.countingtree.Constraint;
import cz.muni.fi.publishsubscribe.countingtree.ternarysearchtree.TernarySearchTree;
import java.util.ArrayList;
import java.util.List;

public class StringPrefixIndex implements OperatorIndex<String> {

	protected TernarySearchTree<Constraint<String>> tree = new TernarySearchTree<>();

	@Override
	public boolean addConstraint(Constraint<String> constraint) {
		String value = constraint.getAttributeValue().getValue();
		return tree.put(value, constraint) != null;
	}

	@Override
	public boolean removeConstraint(Constraint<String> constraint) {
		return (tree.remove(constraint.getAttributeValue().getValue()) != null);
	}

	@Override
	public List<Constraint<String>> getConstraints(String attributeValue) {
		return tree.getAllPrefixes(attributeValue);
	}
        
        @Override
        public List<Constraint<String>> getConflictingConstraints(Constraint<String> constraint) {
            List<Constraint<String>> conflicts = new ArrayList<>();
            switch (constraint.getOperator()) {
                case EQUALS:
                    return this.getConstraints(constraint.getAttributeValue().getValue());
                case PREFIX:
                    String prefix = constraint.getAttributeValue().getValue();
                    if (conflicts.addAll(this.getConstraints(prefix))) {
                        return conflicts;
                    } else {
                        for (CharSequence p : tree.keySet()) {
                            if ((prefix.length() < p.length()) && (p.toString().startsWith(prefix))) {
                                conflicts.addAll(this.getConstraints(p.toString()));
                                return conflicts;
                            }
                        }
                    }
            }
            
            
            return conflicts;
        }
}
