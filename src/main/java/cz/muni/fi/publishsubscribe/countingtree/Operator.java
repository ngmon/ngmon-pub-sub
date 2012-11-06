package cz.muni.fi.publishsubscribe.countingtree;

import java.util.*;

public enum Operator {
	EQUALS, LESS_THAN, LESS_THAN_OR_EQUAL_TO, GREATER_THAN, GREATER_THAN_OR_EQUAL_TO, PREFIX;

	private static Map<Class, Set<Operator>> operatorMap = new HashMap<>(2);

	static {
		Set<Operator> stringOperators = new HashSet<>();
		stringOperators.add(EQUALS);
		stringOperators.add(PREFIX);

		Set<Operator> longOperators = new HashSet<>();
		longOperators.add(EQUALS);
		longOperators.add(LESS_THAN);
		longOperators.add(LESS_THAN_OR_EQUAL_TO);
		longOperators.add(GREATER_THAN);
		longOperators.add(GREATER_THAN_OR_EQUAL_TO);

		operatorMap.put(String.class, stringOperators);
		operatorMap.put(Long.class, longOperators);

	}

	public static Set<Operator> getSupportedOperators(Class type) {

		if (!operatorMap.containsKey(type)) {
			return Collections.emptySet();
		}

		return operatorMap.get(type);
	}
}