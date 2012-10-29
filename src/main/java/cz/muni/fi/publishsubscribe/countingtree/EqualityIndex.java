package cz.muni.fi.publishsubscribe.countingtree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EqualityIndex {

	// private HashMap<String, TableConstraint> attributeNamesMap = new
	// HashMap<String, TableConstraint>();

	/** Map from attribute name to equality index */
	private HashMap<String, HashMap<AttributeValue, TableConstraint>> equalityMaps = new HashMap<String, HashMap<AttributeValue, TableConstraint>>();

	public EqualityIndex(List<Predicate> predicates) {
		for (Predicate subscription : predicates) {

			List<Filter> predicate = subscription.getFilters();
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
