package cz.muni.fi.publishsubscribe.countingtree;

import java.util.ArrayList;
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
	// private Long subscriptionCount = 0L;

	// I need to be able to get the Predicate with all the data (list of
	// subscriptions)
	private Map<Predicate, Predicate> predicates = new HashMap<>();
	private FilterMatcher matcher = new FilterMatcher();

	public Long subscribe(Predicate predicate, Subscription subscription) {
		subscription.setId(subscriptionNextId);

		Predicate fullPredicate = predicates.get(predicate);
		// the same predicate has already been inserted
		if (fullPredicate != null) {
			fullPredicate.addSubscription(subscription);
		} else {
			predicate.addSubscription(subscription);
			predicates.put(predicate, predicate);
			matcher.addPredicate(predicate);
		}

		// subscriptionCount++;

		return subscriptionNextId++;
	}

	/*-public void createIndexTable() {
		if (predicates != null && !predicates.isEmpty()) {
			matcher = new FilterMatcher(predicates);
		} else {
			matcher = null;
		}
	}*/

	public boolean unsubscribe(Long subscriptionId) {
		/*-Subscription subscription = new Subscription();
		subscription.setId(subscriptionId);
		return unsubscribe(subscription);*/

		throw new UnsupportedOperationException("not yet implemented");
	}

	public boolean unsubscribe(Subscription subscription) {
		/*-if (subscription.getId() == null)
			return false;
		
		Predicate predicate = predicateLookup.get(subscription);
		// this subscription has never been inserted
		if (predicate == null)
			return false;
		
		Set<Subscription> subscriptionSet = subscriptionLookup.get(predicate);
		subscriptionSet.remove(subscription);
		if (subscriptionSet.isEmpty()) {
			matcher.removePredicate(predicate);
			subscriptionSet.remove(predicate);
		}
		predicateLookup.remove(subscription);
		
		return true;*/

		throw new UnsupportedOperationException("not yet implemented");
	}

	public List<Subscription> match(Event event) {

		if (matcher == null) {
			return new ArrayList<Subscription>();
		}

		List<Subscription> subscriptions = new ArrayList<>();
		List<Filter> filtersToReset = new ArrayList<>();
		List<Predicate> predicatesToReset = new ArrayList<>();
		matcher.iterateThroughMatchedConstraints(event, subscriptions,
				filtersToReset, predicatesToReset);

		// int predicateCount = subscriptionLookup.size();
		//
		// //Map<Filter, Integer> counters = new HashMap<Filter, Integer>();
		// Set<Predicate> matched = new HashSet<Predicate>();
		//
		// // first get the filters associated to the matching constraints
		// List<Filter> matchingFilters = matcher.getMatchingFilters(event);
		//
		// for (Filter filter : matchingFilters) {
		//
		// // increment counter for the filter
		// /*-Integer count = counters.get(filter);
		// if (count == null)
		// count = 0;
		// counters.put(filter, ++count);
		//
		// // filter has matched -> add all subscriptions related to
		// // all the predicates that contain this filter
		// if (count.equals(filter.getConstraints().size())) {*/
		// if (filter.matchAfterIncrementing()) {
		// Set<Predicate> matchedPredicates = matcher
		// .getPredicates(filter);
		// for (Predicate matchedPredicate : matchedPredicates) {
		// if (!matched.contains(matchedPredicate)) {
		// subscriptions.addAll(subscriptionLookup
		// .get(matchedPredicate));
		// matched.add(matchedPredicate);
		// if (matched.size() == predicateCount)
		// break;
		// }
		// }
		// }
		// }
		//
		// for (Filter filter : matchingFilters) {
		// filter.resetMatchedCount();
		// }

		for (Filter filter : filtersToReset) {
			filter.resetMatchedCount();
		}

		for (Predicate predicate : predicatesToReset) {
			predicate.resetMatched();
		}

		return subscriptions;
	}
}
