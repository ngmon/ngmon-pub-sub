package cz.muni.fi.publishsubscribe.countingtree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CountingTree {

	private Long subscriptionCounter = 1L;
	private Map<Long, Predicate> predicates = new HashMap<>();
	private FilterMatcher matcher = new FilterMatcher();

	public Long subscribe(Predicate predicate) {
		predicate.setId(subscriptionCounter);
		this.predicates.put(subscriptionCounter, predicate);

		matcher.addPredicate(predicate);

		return subscriptionCounter++;
	}

	/*-public void createIndexTable() {
		if (predicates != null && !predicates.isEmpty()) {
			matcher = new FilterMatcher(predicates);
		} else {
			matcher = null;
		}
	}*/

	private boolean unsubscribe(Long subscriptionId) {
		return (this.predicates.remove(subscriptionId) != null);
	}

	public List<Predicate> match(Event event) {

		if (matcher == null) {
			return new ArrayList<Predicate>();
		}

		List<Predicate> predicates = new ArrayList<Predicate>();

		int subscriptionCount = this.predicates.size();

		Map<Filter, Integer> counters = new HashMap<Filter, Integer>();
		Set<Predicate> matched = new HashSet<Predicate>();

		// first get the filters associated to the matching constraints
		List<Filter> matchingFilters = matcher.getMatchingFilters(event);

		for (Filter filter : matchingFilters) {
			Predicate matchedPredicate = matcher.getPredicate(filter);
			if (!matched.contains(matchedPredicate)) {
				Integer count = counters.get(filter);
				if (count == null)
					count = 0;
				counters.put(filter, ++count);
				if (count.equals(filter.getConstraints().size())) {
					predicates.add(matchedPredicate);
					matched.add(matchedPredicate);
					if (matched.size() == subscriptionCount)
						break;
				}
			}
		}

		return predicates;
	}
}
