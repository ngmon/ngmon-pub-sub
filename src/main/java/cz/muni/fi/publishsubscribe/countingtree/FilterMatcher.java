package cz.muni.fi.publishsubscribe.countingtree;

import cz.muni.fi.publishsubscribe.countingtree.index.AttributeIndex;

import java.util.*;

public class FilterMatcher {

	private AttributeIndex attributeIndex = new AttributeIndex();

	private Map<Constraint<Comparable<?>>, Set<Filter>> reverseLookup = new HashMap<>();
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

				List<Constraint<Comparable<?>>> constraints = filter.getConstraints();
				for (Constraint<Comparable<?>> constraint : constraints) {

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


	public List<Constraint<Comparable<?>>> getMatchingConstraints(Event event) {
		List<Constraint<Comparable<?>>> constraints = new ArrayList<>();

		List<Attribute<Comparable<?>>> attributes = event.getAttributes();

		for (Attribute<Comparable<?>> attribute : attributes) {
			List<Constraint<Comparable<?>>> foundConstraints = this.attributeIndex.getConstraints(attribute.getName(), attribute.getValue());

			constraints.addAll(foundConstraints);
		}

		return constraints;
	}

	public List<Filter> getMatchingFilters(Event event) {
		List<Filter> filters = new ArrayList<>();

		for (Constraint<Comparable<?>> c : this.getMatchingConstraints(event)) {

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
