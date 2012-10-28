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
	private List<Subscription> subscriptions = new ArrayList<Subscription>();
	private IndexTable indexTable = null;

	public Long subscribe(Subscription subscription) {
		this.subscriptions.add(subscription);

		// TODO - fwd table

		/*-
		List<Filter> filters = subscription.getPredicate();
		for (Filter filter : filters) {
			List<Constraint> constraints = filter.getConstraints();
			for (Constraint constraint : constraints) {
				
			}
		}*/

		subscription.setId(subscriptionCounter);
		return subscriptionCounter++;
	}

	public void createIndexTable() {
		if (subscriptions != null && !subscriptions.isEmpty()) {
			indexTable = new IndexTable(subscriptions);
		} else {
			indexTable = null;
		}
	}

	private boolean unsubscribe(Long subscriptionId) {
		Iterator<Subscription> iterator = subscriptions.iterator();
		while (iterator.hasNext()) {
			Subscription subscription = iterator.next();
			if (subscriptionId.equals(subscription.getId())) {
				iterator.remove();
				return true;
			}
		}

		return false;
	}

	public List<Subscription> match(Event event) {

		if (indexTable == null) {
			return new ArrayList<Subscription>();
		}
		
		List<Subscription> subscriptions = new ArrayList<Subscription>();
		
		int subscriptionCount = this.subscriptions.size();
		
		Map<Filter, Integer> counters = new HashMap<Filter, Integer>();
		Set<Subscription> matched = new HashSet<Subscription>();

		// first get the filters associated to the matching constraints
		List<Filter> matchingFilters = indexTable.getMatchingFilters(event);
		
		for (Filter filter : matchingFilters) {
			Subscription subscription = filter.getSubscription();
			if (!matched.contains(subscription)) {
				Integer count = counters.get(filter);
				if (count == null)
					count = 0;
				counters.put(filter, ++count);
				if (count.equals(filter.getConstraints().size())) {
					subscriptions.add(subscription);
					matched.add(subscription);
					if (matched.size() == subscriptionCount)
						break;
				}
			}
		}

		return subscriptions;
	}
}
