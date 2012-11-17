package cz.muni.fi.publishsubscribe.countingtree.index.operator;

import java.util.List;

import cz.muni.fi.publishsubscribe.countingtree.Constraint;
import cz.muni.fi.publishsubscribe.countingtree.ternarysearchtree.TernarySearchTree;

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

}
