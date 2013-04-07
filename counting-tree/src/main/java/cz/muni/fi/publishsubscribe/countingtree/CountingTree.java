package cz.muni.fi.publishsubscribe.countingtree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The main (front-end) class for the algorithm; Usage: Add the required
 * Predicates (subscriptions) first, then get the Predicates matching the
 * Event(s)
 */
public class CountingTree {

	private Long subscriptionNextId = 1L;

	// I need to be able to get the Predicate with all the data (list of
	// subscriptions)
	private Map<Predicate, Predicate> predicates = new HashMap<>();
	private FilterMatcher matcher = new FilterMatcher();
	private Map<Subscription, Predicate> subscriptionToPredicate = new HashMap<>();

	public Long subscribe(Predicate predicate, Subscription subscription) {
		subscription.setId(subscriptionNextId);

		// compute hash codes now to slightly improve match() performance
		predicate.computeHashCode();
		for (Filter filter : predicate.getFilters())
			filter.computeHashCode();

		Predicate fullPredicate = predicates.get(predicate);
		// the same predicate has already been inserted
		if (fullPredicate != null) {
			fullPredicate.addSubscription(subscription);
			subscriptionToPredicate.put(subscription, fullPredicate);
		} else {
			predicate.addSubscription(subscription);
			predicates.put(predicate, predicate);

			Set<Predicate> predicateSet = new HashSet<>();
			predicateSet.add(predicate);
			subscriptionToPredicate.put(subscription, predicate);

			matcher.addPredicate(predicate);
		}

		return subscriptionNextId++;
	}

	/**
	 * Only for compatibility reasons, not intended for common use
	 */
	@Deprecated
	public Long subscribe(Predicate predicate) {
		return subscribe(predicate, new Subscription());
	}

	public boolean unsubscribe(Long subscriptionId) {
		Subscription subscription = new Subscription();
		subscription.setId(subscriptionId);
		return unsubscribe(subscription);
	}

	public boolean unsubscribe(Subscription subscription) {
		if (subscription.getId() == null)
			return false;

		if (!subscriptionToPredicate.containsKey(subscription))
			return false;

		Predicate predicate = subscriptionToPredicate.get(subscription);
		subscriptionToPredicate.remove(subscription);
		
		predicate.removeSubscription(subscription);
		// no subscriptions using this predicate -> remove it
		if (predicate.getSubscriptions().isEmpty()) {
			matcher.removePredicate(predicate);
			predicates.remove(predicate);
		}

		return true;
	}

	private <T1 extends Comparable<T1>, T2 extends Constraint<T1>> List<Subscription> matchSubscriptions(
			Event event, List<Filter> filtersToReset,
			List<Predicate> predicatesToReset) {
		if (matcher == null) {
			return new ArrayList<Subscription>();
		}

		int allSubcriptionsSize = subscriptionToPredicate.size();

		List<Subscription> subscriptions = new ArrayList<>();

		List<Attribute<? extends Comparable<?>>> attributes = event
				.getAttributes();

		for (Attribute<? extends Comparable<?>> uncastAttribute : attributes) {
			Attribute<T1> attribute = (Attribute<T1>) uncastAttribute;

			List<Collection<Constraint<T1>>> foundConstraintLists = this.matcher
					.getConstraintLists(attribute);
			for (Collection<Constraint<T1>> foundConstraints : foundConstraintLists) {
				for (Constraint<T1> constraint : foundConstraints) {
					for (Filter filter : constraint.getFilters()) {
						if (!filter.isAddedToReset()) {
							filtersToReset.add(filter);
							filter.setAddedToReset();
						}
						if (filter.incrementAndGetCounter() >= filter.getConstraints().size()) {
							for (Predicate predicate : filter.getPredicates()) {
								if (!predicate.isAddedToReset()) {
									subscriptions.addAll(predicate
											.getSubscriptions());
									predicate.setAddedToReset();
									predicatesToReset.add(predicate);
									if (subscriptions.size() >= allSubcriptionsSize)
										return subscriptions;
								}
							}
						}
					}
				}
			}
		}

		return subscriptions;
	}

	public <T1 extends Comparable<T1>, T2 extends Constraint<T1>> List<Subscription> match(
			Event event) {

		List<Filter> filtersToReset = new ArrayList<>();
		List<Predicate> predicatesToReset = new ArrayList<>();

		List<Subscription> subscriptions = matchSubscriptions(event,
				filtersToReset, predicatesToReset);

		for (Filter filter : filtersToReset) {
			filter.clearAddedToReset();
			filter.resetCounter();
		}

		for (Predicate predicate : predicatesToReset) {
			predicate.clearAddedToReset();
		}

		return subscriptions;
	}
}
