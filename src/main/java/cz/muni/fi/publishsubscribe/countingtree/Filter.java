package cz.muni.fi.publishsubscribe.countingtree;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores one or more Constraints
 * Can be either true of false for a specific event
 * It's true for an event iff it's true for all the Constraints the Filter contains
 * (We can also say the Filter either does or does not satisfy an event)
 */
public class Filter {

	private Long id = null;
	private List<Constraint<? extends Comparable<?>>> constraints = new ArrayList<>();
	private Integer cachedHashCode = null;

	public <T1 extends Comparable<T1>, T2 extends Constraint<T1>> boolean addConstraint(T2 constraint) {
		return this.constraints.add(constraint);
	}

	public List<Constraint<? extends Comparable<?>>> getConstraints() {
		return constraints;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Filter filter = (Filter) o;
		
		// if both IDs have been set, I can just compare IDs
		if (id != null && filter.id != null) {
			return id.equals(filter.id);
		} else {
			return constraints.equals(filter.constraints);
		}
	}

	@Override
	public int hashCode() {
		if (cachedHashCode == null)
			cachedHashCode = constraints.hashCode();
		return cachedHashCode;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
