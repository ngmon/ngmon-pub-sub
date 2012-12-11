package cz.muni.fi.publishsubscribe.countingtree.index.operator;

import cz.muni.fi.publishsubscribe.countingtree.Constraint;
import cz.muni.fi.publishsubscribe.countingtree.Range;
import cz.muni.fi.publishsubscribe.countingtree.RangeTree;
import java.util.*;

public class RangeIndex<T1 extends Comparable<T1>> implements OperatorIndex<T1> {

	private RangeTree<T1> rangeTree = new RangeTree<>();
	private Map<Range<T1>, Constraint<T1>> constraintLookup = new HashMap<>();

	@Override
	public boolean addConstraint(Constraint<T1> constraint) {
		Range<T1> range = (Range<T1>) constraint.getAttributeValue().getValue();

		this.rangeTree.addRange(range);
		this.constraintLookup.put(range, constraint);

		return true;
	}

	@Override
	public boolean removeConstraint(Constraint<T1> constraint) {
		return false;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public List<Constraint<T1>> getConstraints(T1 attributeValue) {

		Set<Range<T1>> ranges = this.rangeTree.getRangesContaining(attributeValue);

		if (ranges.isEmpty()) {
			return Collections.emptyList();
		}

		List<Constraint<T1>> constraints = new ArrayList<>();

		for (Range<T1> range : ranges) {
			constraints.add(this.constraintLookup.get(range));
		}

		return constraints;
	}

        @Override
        public List<Constraint<T1>> getIntersectingConstraints(Constraint<T1> constraint) {
            List<Constraint<T1>> conflicts = new ArrayList<>();
            //TODO skontrolovat, otestovat, vymazat blbe poznamky
            switch (constraint.getOperator()) {
                case LESS_THAN:
                    Set<Range<T1>> rangesIntersecting = this.rangeTree.getRangesIntersecting(null, constraint.getAttributeValue().getValue());
                    for (Range<T1> r : rangesIntersecting) {
                        if ((!r.isLeftUnbounded()) && (r.getStart().compareTo(constraint.getAttributeValue().getValue()) < 0)) {
                            conflicts.add(this.constraintLookup.get(r));
                        }
                    }
                    break;
                case LESS_THAN_OR_EQUAL_TO:
                    rangesIntersecting = this.rangeTree.getRangesIntersecting(null, constraint.getAttributeValue().getValue());
                    for (Range<T1> r : rangesIntersecting) {
                        conflicts.add(this.constraintLookup.get(r));
                    }
                    break;
                case GREATER_THAN:
                    rangesIntersecting = this.rangeTree.getRangesIntersecting(constraint.getAttributeValue().getValue(), null);
                    for (Range<T1> r : rangesIntersecting) {
                        if ((!r.isRightUnbounded()) && (r.getEnd().compareTo(constraint.getAttributeValue().getValue()) > 0)) {
                            conflicts.add(this.constraintLookup.get(r));
                        }
                    }
                    break;
                case GREATER_THAN_OR_EQUAL_TO:
                    rangesIntersecting = this.rangeTree.getRangesIntersecting(constraint.getAttributeValue().getValue(), null);
                    for (Range<T1> r : rangesIntersecting) {
                        conflicts.add(this.constraintLookup.get(r));
                    }
                    break;
                case EQUALS:
                    conflicts = this.getConstraints(constraint.getAttributeValue().getValue());
                    break;
                case RANGE:
                    rangesIntersecting = this.rangeTree.getRangesIntersecting((Range<T1>)(constraint.getAttributeValue().getValue()));
                    for (Range<T1> r : rangesIntersecting) {
                        conflicts.add(this.constraintLookup.get(r));
                    }
                    break;
            }
            
            return conflicts;
        }
}
