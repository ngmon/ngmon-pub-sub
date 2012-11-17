package cz.muni.fi.publishsubscribe.countingtree;

import cz.muni.fi.publishsubscribe.countingtree.index.AttributeIndex;

import java.util.*;

public class FilterMatcher {

	private AttributeIndex attributeIndex = new AttributeIndex();

	private Map<Constraint<? extends Comparable<?>>, Set<Filter>> reverseLookup = new HashMap<>();
	private Map<Filter, Predicate> filterPredicateLookup = new HashMap<>();

	private Long filterId = 1L;

	public FilterMatcher() {
	}

	public void addPredicate(Predicate predicate) {
		List<Filter> filters = predicate.getFilters();

		for (Filter filter : filters) {

			filter.setId(filterId++);

			this.filterPredicateLookup.put(filter, predicate);

			List<Constraint<? extends Comparable<?>>> constraints = filter.getConstraints();
			for (Constraint<? extends Comparable<?>> constraint : constraints) {

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

	public void removePredicate(Predicate predicate) {
		// remove the relevant items from reverse lookup maps
		List<Filter> filters = predicate.getFilters();
		for (Filter filter : filters) {
			List<Constraint<? extends Comparable<?>>> constraints = filter.getConstraints();
			for (Constraint<? extends Comparable<?>> constraint : constraints) {
				Set<Filter> associatedFilters = this.reverseLookup
						.get(constraint);
				if (associatedFilters != null) {
					associatedFilters.remove(filter);
					if (associatedFilters.isEmpty()) {
						this.reverseLookup.remove(constraint);
					}
				}
				this.attributeIndex.removeConstraint(constraint);
			}
			this.filterPredicateLookup.remove(predicate);
		}
		
		// TODO - remove the constraints from the indices
	}

	public <T1 extends Comparable<T1>, T2 extends Constraint<T1>> List<Constraint<? extends Comparable<?>>> getMatchingConstraints(Event event) {
		List<Constraint<? extends Comparable<?>>> constraints = new ArrayList<>();

		List<Attribute<? extends Comparable<?>>> attributes = event.getAttributes();

		for (Attribute<? extends Comparable<?>> uncastAttribute :  attributes) {
			Attribute<T1> attribute = (Attribute<T1>) uncastAttribute;

			List<T2> foundConstraints = this.attributeIndex.getConstraints(attribute.getName(), attribute.getValue());

			constraints.addAll(foundConstraints);
		}

		return constraints;
	}

	public List<Filter> getMatchingFilters(Event event) {
		List<Filter> filters = new ArrayList<>();

		for (Constraint c : this.getMatchingConstraints(event)) {

			Set<Filter> foundFilters = this.reverseLookup.get(c);

			if (foundFilters != null) {
				filters.addAll(foundFilters);
			}

		}

		return filters;
	}

	public Predicate getPredicate(Filter filter) {
		return this.filterPredicateLookup.get(filter);
	}
}
