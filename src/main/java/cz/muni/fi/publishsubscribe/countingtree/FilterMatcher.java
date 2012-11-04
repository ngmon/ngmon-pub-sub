package cz.muni.fi.publishsubscribe.countingtree;

import cz.muni.fi.publishsubscribe.countingtree.index.AttributeIndex;

import java.util.*;

public class FilterMatcher {

	private AttributeIndex attributeIndex = new AttributeIndex();

	private Map<Constraint<?>, Set<Filter>> reverseLookup = new HashMap<>();
	private Map<Filter, Set<Predicate>> filterPredicateLookup = new HashMap<>();

	public FilterMatcher(List<Predicate> predicates) {
		Long id = 0L;
		
		for (Predicate predicate : predicates) {

			List<Filter> filters = predicate.getFilters();

			for (Filter filter : filters) {
				
				filter.setId(id++);
				
				if (this.filterPredicateLookup.containsKey(filter)) {
					this.filterPredicateLookup.get(filter).add(predicate);
				} else {
					Set<Predicate> predicateHashSet = new HashSet<>();
					predicateHashSet.add(predicate);
					this.filterPredicateLookup.put(filter, predicateHashSet);
				}

				List<Constraint<?>> constraints = filter.getConstraints();
				for (Constraint<?> constraint : constraints) {

					this.attributeIndex.addConstraint(constraint);

					if (this.reverseLookup.containsKey(constraint)) {
						this.reverseLookup.get(constraint).add(filter);
					} else {

						Set<Filter> filterHashSet = new HashSet<>();
						filterHashSet.add(filter);

						this.reverseLookup.put(constraint, filterHashSet);
					}

				}
			}
		}
	}


	public List<Constraint<?>> getMatchingConstraints(Event event) {
		List<Constraint<?>> constraints = new ArrayList<>();

		List<Attribute<?>> attributes = event.getAttributes();

		for (Attribute<?> attribute : attributes) {
			List<Constraint<?>> foundConstraints = this.attributeIndex.getConstraints(attribute.getName(), attribute.getValue());

			constraints.addAll(foundConstraints);
		}

		return constraints;
	}

	public List<Filter> getMatchingFilters(Event event) {
		List<Filter> filters = new ArrayList<>();

		for (Constraint<?> c : this.getMatchingConstraints(event)) {

			Set<Filter> foundFilters = this.reverseLookup.get(c);

			if (foundFilters != null) {
				filters.addAll(foundFilters);
			}

		}

		return filters;
	}
	
	public List<Predicate> getPredicates(Filter filter) {
		List<Predicate> predicates = new ArrayList<>();
		
		Set<Predicate> foundPredicates = this.filterPredicateLookup.get(filter);
		
		if (foundPredicates != null) {
			predicates.addAll(foundPredicates);
		}
		
		return predicates;
	}
}
