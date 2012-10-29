package cz.muni.fi.publishsubscribe.countingtree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cz.muni.fi.publishsubscribe.countingtree.Event.EventAttribute;

public class IndexTable {

	/** A constraint in the "middle" of the forwarding table */
	private static class TableConstraint {

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

	// private HashMap<String, TableConstraint> attributeNamesMap = new
	// HashMap<String, TableConstraint>();

	/** Map from attribute name to equality index */
	private HashMap<String, HashMap<AttributeValue, TableConstraint>> equalityMaps = new HashMap<String, HashMap<AttributeValue, TableConstraint>>();

	public IndexTable(List<Subscription> subscriptions) {
		for (Subscription subscription : subscriptions) {

			List<Filter> predicate = subscription.getPredicate();
			for (Filter filter : predicate) {

				List<Constraint> constraints = filter.getConstraints();
				for (Constraint constraint : constraints) {

					Operator operator = constraint.getOperator();
					String attributeName = constraint.getName();
					AttributeValue value = constraint.getAttributeValue();
					switch (operator) {
					case EQUALS: {
						// get the index for the attribute name
						HashMap<AttributeValue, TableConstraint> index = equalityMaps
								.get(attributeName);
						if (index == null) {
							index = new HashMap<AttributeValue, TableConstraint>();
							equalityMaps.put(attributeName, index);
						}

						// if the value is in the index already, add the filter
						TableConstraint storedConstraint = index.get(value);
						if (storedConstraint != null) {
							storedConstraint.addFilter(filter);
							// otherwise add the value to the index
						} else {
							index.put(value, new TableConstraint(operator,
									value, filter));
						}
					}
					}
				}
			}
		}
	}

	public List<Filter> getMatchingFilters(Event event) {
		List<Filter> filters = new ArrayList<Filter>();

		// search matching constraints for each event attribute

		List<EventAttribute> attributes = event.getAttributes();
		for (EventAttribute attribute : attributes) {
			String attributeName = attribute.getName();
			AttributeValue attributeValue = attribute.getValue();
			// get the branch corresponding to the attribute name

			// search the equality matches
			HashMap<AttributeValue, TableConstraint> index = equalityMaps
					.get(attributeName);
			if (index != null) {
				TableConstraint tableConstraint = index.get(attributeValue);
				if (tableConstraint != null) {
					filters.addAll(tableConstraint.getFilters());
				}
			}
			
			// TODO - <, > matches
		}

		return filters;
	}
}
