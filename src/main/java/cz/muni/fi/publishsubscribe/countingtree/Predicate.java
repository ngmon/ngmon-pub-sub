package cz.muni.fi.publishsubscribe.countingtree;

import java.util.ArrayList;
import java.util.List;

public class Predicate {

	private Long id;
	private List<Filter> filters = new ArrayList<Filter>();

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
		filter.setPredicate(this);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Predicate that = (Predicate) o;

		if (!id.equals(that.id)) return false;

		return true;
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}
}
