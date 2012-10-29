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
	private List<Predicate> predicates = new ArrayList<Predicate>();
	private EqualityIndex indexTable = null;

	public Long subscribe(Predicate predicate) {
		this.predicates.add(predicate);

		// TODO - fwd table

		/*-
		List<Filter> filters = predicate.getFilters();
		for (Filter filter : filters) {
			List<Constraint> constraints = filter.getConstraints();
			for (Constraint constraint : constraints) {
				
			}
		}*/

		predicate.setId(subscriptionCounter);
		return subscriptionCounter++;
	}

	public void createIndexTable() {
		if (predicates != null && !predicates.isEmpty()) {
			indexTable = new EqualityIndex(predicates);
		} else {
			indexTable = null;
		}
	}

	private boolean unsubscribe(Long subscriptionId) {
		Iterator<Predicate> iterator = predicates.iterator();
		while (iterator.hasNext()) {
			Predicate predicate = iterator.next();
			if (subscriptionId.equals(predicate.getId())) {
				iterator.remove();
				return true;
			}
		}

		return false;
	}

	public List<Predicate> match(Event event) {

		if (indexTable == null) {
			return new ArrayList<Predicate>();
		}
		
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		int subscriptionCount = this.predicates.size();
		
		Map<Filter, Integer> counters = new HashMap<Filter, Integer>();
		Set<Predicate> matched = new HashSet<Predicate>();

		// first get the filters associated to the matching constraints
		List<Filter> matchingFilters = indexTable.getMatchingFilters(event);
		
		for (Filter filter : matchingFilters) {
			Predicate predicate = filter.getPredicate();
			if (!matched.contains(predicate)) {
				Integer count = counters.get(filter);
				if (count == null)
					count = 0;
				counters.put(filter, ++count);
				if (count.equals(filter.getConstraints().size())) {
					predicates.add(predicate);
					matched.add(predicate);
					if (matched.size() == subscriptionCount)
						break;
				}
			}
		}

		return predicates;
	}
}
