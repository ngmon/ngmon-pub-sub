package cz.muni.fi.publishsubscribe.countingtree;

import java.util.ArrayList;
import java.util.List;

public class Filter {

	private List<Constraint<?>> constraints = new ArrayList<Constraint<?>>();

	public void addConstraint(Constraint constraint) {
		this.constraints.add(constraint);
	}

	public List<Constraint<?>> getConstraints() {
		return constraints;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Filter filter = (Filter) o;

		if (!constraints.equals(filter.constraints)) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = constraints.hashCode();
		return result;
	}
}
