package cz.muni.fi.publishsubscribe.countingtree;

import java.util.ArrayList;
import java.util.List;

/**
 * A constraint in the "middle" of the forwarding table
 */
public class TableConstraint {
	private Operator operator;
	private AttributeValue value;
	private List<Filter> filters = new ArrayList<Filter>();

	public Operator getOperator() {
		return operator;
	}

	public AttributeValue getValue() {
		return value;
	}

	public List<Filter> getFilters() {
		return filters;
	}

	public void addFilter(Filter filter) {
		this.filters.add(filter);
	}

	public TableConstraint(Operator operator, AttributeValue value) {
		this.operator = operator;
		this.value = value;
	}

	public TableConstraint(Operator operator,
	                       AttributeValue value, Filter filter) {
		this.operator = operator;
		this.value = value;
		this.filters.add(filter);
	}

}