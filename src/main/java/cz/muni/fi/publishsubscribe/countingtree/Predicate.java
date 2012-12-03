package cz.muni.fi.publishsubscribe.countingtree;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores one or more Filters
 * Can be either true of false for a specific event
 * It's true for an event iff it's true for at least one of the Filters the Predicate contains
 * (We can also say the Predicate either does or does not satisfy an event)
 */
public class Predicate {

	private Long id;
	private List<Filter> filters = new ArrayList<>();

	public List<Filter> getFilters() {
		return filters;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void addFilter(Filter filter) {
		this.filters.add(filter);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Predicate predicate = (Predicate) o;

		if (id != predicate.id) return false;

		return true;
	}

	@Override
	public int hashCode() {
		return (int) (id ^ (id >>> 32));
	}
}
