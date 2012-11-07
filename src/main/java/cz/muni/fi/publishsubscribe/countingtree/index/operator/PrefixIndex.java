package cz.muni.fi.publishsubscribe.countingtree.index.operator;

import java.util.List;

import cz.muni.fi.publishsubscribe.countingtree.Constraint;
import cz.muni.fi.publishsubscribe.countingtree.ternarysearchtree.TernarySearchTree;

public class PrefixIndex<T_ValueType extends Comparable<T_ValueType>>
		implements OperatorIndex<T_ValueType> {

	protected TernarySearchTree<Constraint> tree = new TernarySearchTree<>();

	@Override
	public boolean addConstraint(Constraint constraint) {
		/*-
		String value = constraint.getAttributeValue().getValue();
		
		return tree.put(value, constraint) != null;*/
		
		// DELETE THIS LATER!
		return true;
	}

	@Override
	public boolean removeConstraint(Constraint constraint) {
		return (tree.remove(constraint.getAttributeValue().getValue()) != null);
	}

	@Override
	public List<Constraint> getConstraints(Comparable<?> attributeValue) {
		/*-return tree.getAllPrefixes(attributeValue);*/
		
		// DELETE THIS LATER!
		return null;
	}

}
