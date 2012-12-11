package cz.muni.fi.publishsubscribe.countingtree.index.operator;

import cz.muni.fi.publishsubscribe.countingtree.Constraint;
import cz.muni.fi.publishsubscribe.countingtree.Range;

import java.util.ArrayList;
import java.util.List;

public class LessThanIndex<T1 extends Comparable<T1>> extends AbstractNavigableMapIndex<T1> {

	@Override
	public List<Constraint<T1>> getConstraints(T1 attributeValue) {

		return new ArrayList<>(this.constraints.tailMap(attributeValue, false).values());
	}

        @Override
        public List<Constraint<T1>> getIntersectingConstraints(Constraint<T1> constraint) {
            switch (constraint.getOperator()) {
                case LESS_THAN:
                case LESS_THAN_OR_EQUAL_TO:
                    return new ArrayList<>(constraints.values()); //koliduju urcite uplne vsetky
                case GREATER_THAN:
                case GREATER_THAN_OR_EQUAL_TO:
                case EQUALS:
                    return this.getConstraints(constraint.getAttributeValue().getValue());
                case RANGE:
                    Range<T1> rng = (Range<T1>)(constraint.getAttributeValue().getValue());
                    if (rng.isLeftUnbounded()) {
                        return new ArrayList<>();
                    } else {
                        return this.getConstraints(rng.getStart());
                    }
            }
            
            return new ArrayList<>();
        }
}
