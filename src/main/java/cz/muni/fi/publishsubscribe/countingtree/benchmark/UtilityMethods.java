package cz.muni.fi.publishsubscribe.countingtree.benchmark;

import cz.muni.fi.publishsubscribe.countingtree.Constraint;
import cz.muni.fi.publishsubscribe.countingtree.Filter;
import cz.muni.fi.publishsubscribe.countingtree.Predicate;

public class UtilityMethods {

	public static Predicate createPredicateFromConstraint(
			Constraint<?> constraint) {
		Filter filter = new Filter();
		filter.addConstraint(constraint);
		Predicate predicate = new Predicate();
		predicate.addFilter(filter);

		return predicate;
	}

}
