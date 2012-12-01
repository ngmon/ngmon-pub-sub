package cz.muni.fi.publishsubscribe.countingtree.benchmark;

import java.util.ArrayList;
import java.util.List;

import com.google.caliper.SimpleBenchmark;

import cz.muni.fi.publishsubscribe.countingtree.Attribute;
import cz.muni.fi.publishsubscribe.countingtree.AttributeValue;
import cz.muni.fi.publishsubscribe.countingtree.Constraint;
import cz.muni.fi.publishsubscribe.countingtree.CountingTree;
import cz.muni.fi.publishsubscribe.countingtree.Event;
import cz.muni.fi.publishsubscribe.countingtree.Operator;

/**
 * One attribute (of type Long), 25 % predicates matched, constraint operators
 * less than and greater than or equal to
 */
public class OneAttributeOperatorsLessThanAndGreaterThanOrEqual extends
		SimpleBenchmark {

	private static final String LONG_ATTRIBUTE_NAME = "longAttribute";

	private static final int PREDICATE_COUNT = 500;
	private static final int EVENT_COUNT = 10000;

	private static final long GREATER_THAN_OR_EQUAL_MIN_VALUE = 100L;
	private static final long GREATER_THAN_OR_EQUAL_MAX_VALUE = 200L;

	private static final long LESS_THAN_MIN_VALUE = 0L;
	private static final long LESS_THAN_MAX_VALUE = 99L;

	private static final long LESS_THAN_ONE_EVENT_MATCH_25_VALUE = 50L;
	private static final long GREATER_THAN_ONE_EVENT_MATCH_25_VALUE = 150L;

	private CountingTree tree;

	private List<Event> eventsFromLessThanMinValueToLessThanMaxValue;
	private List<Event> eventsFromGreaterThanOrEqualMinValueToMaxValue;

	/**
	 * Half of the constraints/predicates have values between
	 * LESS_THAN_MIN_VALUE and LESS_THAN_MAX_VALUE and operator less than and
	 * the second half have values between GREATER_THAN_OR_EQUAL_MIN_VALUE and
	 * GREATER_THAN_OR_EQUAL_MAX_VALUE and operator greater than or equal
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();

		this.tree = new CountingTree();

		int i = 0;
		while (i < PREDICATE_COUNT) {
			long constraintValue = i % (GREATER_THAN_OR_EQUAL_MAX_VALUE + 1);
			Constraint<Long> constraint = new Constraint<Long>(
					LONG_ATTRIBUTE_NAME,
					new AttributeValue<Long>(constraintValue, Long.class),
					constraintValue > LESS_THAN_MAX_VALUE ? Operator.GREATER_THAN_OR_EQUAL_TO
							: Operator.LESS_THAN);
			tree.subscribe(UtilityMethods
					.createPredicateFromConstraint(constraint));

			i++;
		}

		// events with values ranging from LESS_THAN_MIN_VALUE
		// to GREATER_THAN_OR_EQUAL_MAX_VALUE
		eventsFromLessThanMinValueToLessThanMaxValue = prepareEvents(
				LESS_THAN_MIN_VALUE, LESS_THAN_MAX_VALUE);
		eventsFromGreaterThanOrEqualMinValueToMaxValue = prepareEvents(
				GREATER_THAN_OR_EQUAL_MIN_VALUE,
				GREATER_THAN_OR_EQUAL_MAX_VALUE);
	}

	private List<Event> prepareEvents(long minValue, long maxValue) {
		List<Event> events = new ArrayList<>(EVENT_COUNT);
		for (long i = 0, value = minValue; i < EVENT_COUNT; i++) {
			Event event = new Event();
			event.addAttribute(new Attribute<Long>(LONG_ATTRIBUTE_NAME,
					new AttributeValue<Long>(value, Long.class)));
			events.add(event);

			value = value >= maxValue ? minValue : value + 1;
		}

		return events;
	}

	/**
	 * Matches less than predicates only, one event (value in the middle between
	 * LESS_THAN_MIN_VALUE and LESS_THAN_MAX_VALUE
	 */
	public void timeMatch25LessThanOneEvent(int reps) {
		Event event = new Event();
		event.addAttribute(new Attribute<Long>(LONG_ATTRIBUTE_NAME,
				new AttributeValue<Long>(LESS_THAN_ONE_EVENT_MATCH_25_VALUE,
						Long.class)));

		for (int i = 0; i < reps; i++) {
			for (int j = 0; j < EVENT_COUNT; j++) {
				tree.match(event);
			}
		}
	}

	/**
	 * Matches greater than or equal to predicates only, one event (value in the
	 * middle between GREATER_THAN_OR_EQUAL_MIN_VALUE and
	 * GREATER_THAN_OR_EQUAL_MAX_VALUE)
	 */
	public void timeMatch25GreaterThanOrEqualOneEvent(int reps) {
		Event event = new Event();
		event.addAttribute(new Attribute<Long>(LONG_ATTRIBUTE_NAME,
				new AttributeValue<Long>(GREATER_THAN_ONE_EVENT_MATCH_25_VALUE,
						Long.class)));

		for (int i = 0; i < reps; i++) {
			for (int j = 0; j < EVENT_COUNT; j++) {
				tree.match(event);
			}
		}
	}

	/**
	 * Matches less than predicates only, events with values ranging from
	 * LESS_THAN_MIN_VALUE to LESS_THAN_MAX_VALUE
	 */
	public void timeMatch25LessThanDifferentEventsLessThan(int reps) {
		for (int i = 0; i < reps; i++) {
			for (Event event : eventsFromLessThanMinValueToLessThanMaxValue) {
				tree.match(event);
			}
		}
	}

	/**
	 * Matches greater than or equal to predicates only, events with values
	 * ranging from GREATER_THAN_OR_EQUAL_MIN_VALUE to
	 * GREATER_THAN_OR_EQUAL_MAX_VALUE
	 */
	public void timeMatch25GreaterThanOrEqualDifferentEventsGreaterThan(int reps) {
		for (int i = 0; i < reps; i++) {
			for (Event event : eventsFromGreaterThanOrEqualMinValueToMaxValue) {
				tree.match(event);
			}
		}
	}

}
