package cz.muni.fi.publishsubscribe.countingtree;

import java.util.ArrayList;
import java.util.List;

public class Filter {

	private List<Constraint> constraints = new ArrayList<Constraint>();

	/**
	 * Associated Subscription (Two Subscriptions shouldn't share the same
	 * Filter)
	 */
	private Subscription subscription;

	public void addConstraint(Constraint constraint) {
		this.constraints.add(constraint);
	}

	public List<Constraint> getConstraints() {
		return constraints;
	}

	public Subscription getSubscription() {
		return subscription;
	}

	public void setSubscription(Subscription subscription) {
		this.subscription = subscription;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Filter filter = (Filter) o;

		if (!constraints.equals(filter.constraints)) return false;
		if (!subscription.equals(filter.subscription)) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = constraints.hashCode();
		result = 31 * result + subscription.hashCode();
		return result;
	}
}
