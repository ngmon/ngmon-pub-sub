package cz.muni.fi.publishsubscribe.countingtree.benchmark;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;

import com.google.caliper.SimpleBenchmark;

import cz.muni.fi.publishsubscribe.countingtree.Attribute;
import cz.muni.fi.publishsubscribe.countingtree.AttributeValue;
import cz.muni.fi.publishsubscribe.countingtree.Constraint;
import cz.muni.fi.publishsubscribe.countingtree.CountingTree;
import cz.muni.fi.publishsubscribe.countingtree.Event;
import cz.muni.fi.publishsubscribe.countingtree.Filter;
import cz.muni.fi.publishsubscribe.countingtree.Operator;
import cz.muni.fi.publishsubscribe.countingtree.Predicate;

public class TestBenchmark extends SimpleBenchmark {

	private Random generator;
	// total number of attributes - 26^ATTRIBUTE_NAME_LENGTH
	private static final int ATTRIBUTE_NAME_LENGTH = 1;
	private static final int RANDOM_STRING_VALUE_LENGTH = 2;
	private static final String CHARS = "abcdefghijklmnopqrstuvwxyz";
	// minimum value is always 0
	private static final Long VALUES_COUNT = 1000L;

	private CountingTree tree;

	private List<Event> events;

	/*-
	public void timeNanoTime(int reps) {
		System.out.println(reps);
		for (int i = 0; i < reps; i++) {
			System.nanoTime();
		}
	}*/

	/*-
	public void timeSleepTest(int reps) throws InterruptedException {
		for (int i = 0; i < 10; i++)
			Thread.sleep(100);
	}*/

	@Override
	protected void setUp() throws Exception {
		this.generator = new Random(0L);
		this.createTree();
		this.events = this.createEvents();
	}

	private void createTree() {
		this.tree = new CountingTree();

		// less than (10000) longs from 0 to VALUES_COUNT - 1
		for (int j = 0; j < 10000; j++) {
			Constraint<Long> constraint = new Constraint<Long>(
					getRandomAttributeName("L"), new AttributeValue<Long>(
							generator.nextLong() % VALUES_COUNT, Long.class),
					Operator.LESS_THAN);
			tree.subscribe(UtilityMethods
					.createPredicateFromConstraint(constraint));
		}

		// equal longs from 0 to VALUES_COUNT - 1
		for (int j = 0; j < 10000; j++) {
			Constraint<Long> constraint = new Constraint<Long>(
					getRandomAttributeName("L"), new AttributeValue<Long>(
							generator.nextLong() % VALUES_COUNT, Long.class),
					Operator.EQUALS);
			tree.subscribe(UtilityMethods
					.createPredicateFromConstraint(constraint));
		}

		// greater than (10000) longs from 0 to VALUES_COUNT - 1
		for (int j = 0; j < 10000; j++) {
			Constraint<Long> constraint = new Constraint<Long>(
					getRandomAttributeName("L"), new AttributeValue<Long>(
							generator.nextLong() % VALUES_COUNT, Long.class),
					Operator.GREATER_THAN);
			tree.subscribe(UtilityMethods
					.createPredicateFromConstraint(constraint));
		}

		// equal 20 000 Strings (676 different Strings)
		for (int j = 0; j < 20000; j++) {
			Constraint<String> constraint = new Constraint<String>(
					getRandomAttributeName("S"), new AttributeValue<String>(
							RandomStringUtils.random(
									RANDOM_STRING_VALUE_LENGTH, CHARS),
							String.class), Operator.EQUALS);
			tree.subscribe(UtilityMethods
					.createPredicateFromConstraint(constraint));
		}
	}

	private String getRandomAttributeName(String prefix) {
		return prefix + RandomStringUtils.random(ATTRIBUTE_NAME_LENGTH, CHARS);
	}

	/*-public void timeCreateTree(int reps) {
		for (int i = 0; i < reps; i++) {
			createTree();
		}
	}*/

	private Event getRandomEvent(int attributeCount) {
		Event event = new Event();
		// TODO - event might have the same attribute with different values
		// (but is it really a problem?)
		for (int i = 0; i < attributeCount; i++) {
			int type = generator.nextInt() % 2;
			if (type == 0) {
				long randomValue = generator.nextLong() % VALUES_COUNT;
				Attribute<Long> attribute = new Attribute<Long>(
						getRandomAttributeName("L"), new AttributeValue<Long>(
								randomValue, Long.class));
				event.addAttribute(attribute);
			} else {
				String randomValue = RandomStringUtils.random(
						RANDOM_STRING_VALUE_LENGTH, CHARS);
				Attribute<String> attribute = new Attribute<String>(
						getRandomAttributeName("S"),
						new AttributeValue<String>(randomValue, String.class));
				event.addAttribute(attribute);
			}
		}

		return event;
	}

	private List<Event> createEvents() {
		// 1 million events, each has 3 attributes
		int eventCount = 1000;
		int attributeCount = 3;
		events = new ArrayList<Event>(eventCount);
		for (int j = 0; j < eventCount; j++) {
			events.add(getRandomEvent(attributeCount));
		}

		return events;
	}

	public void timeMatchEvents(int reps) {
		for (int i = 0; i < reps; i++) {
			for (Event event : events) {
				this.tree.match(event);
			}
		}
	}
}
