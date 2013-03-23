package cz.muni.fi.publishsubscribe.countingtree.test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import cz.muni.fi.publishsubscribe.countingtree.Attribute;
import cz.muni.fi.publishsubscribe.countingtree.AttributeValue;
import cz.muni.fi.publishsubscribe.countingtree.Constraint;
import cz.muni.fi.publishsubscribe.countingtree.CountingTree;
import cz.muni.fi.publishsubscribe.countingtree.EventImpl;
import cz.muni.fi.publishsubscribe.countingtree.Filter;
import cz.muni.fi.publishsubscribe.countingtree.LongRange;
import cz.muni.fi.publishsubscribe.countingtree.Operator;
import cz.muni.fi.publishsubscribe.countingtree.Predicate;
import cz.muni.fi.publishsubscribe.countingtree.Subscription;

public class RangeTestCase {

	private static final String STRING_ATTRIBUTE_NAME = "stringAttribute";
	private static final String LONG_ATTRIBUTE_NAME = "longAttribute";

	public static final Attribute<String> STRING_ATTRIBUTE = new Attribute<>(
			STRING_ATTRIBUTE_NAME, new AttributeValue<>("foo", String.class));

	public static final Attribute<Long> LONG_ATTRIBUTE1 = new Attribute<>(
			LONG_ATTRIBUTE_NAME, new AttributeValue<>(2000L, Long.class));

	public static final Attribute<Long> LONG_ATTRIBUTE2 = new Attribute<>(
			LONG_ATTRIBUTE_NAME, new AttributeValue<>(5000L, Long.class));

	public Constraint<LongRange> RANGE_CONSTRAINT;

	private CountingTree tree;

	@Before
	public void prepareTree() {
		prepareConstants();

		this.tree = new CountingTree();

		Filter filter = new Filter();
		filter.addConstraint(RANGE_CONSTRAINT);

		Predicate predicate = new Predicate();
		predicate.addFilter(filter);

		this.tree.subscribe(predicate, new Subscription());

	}

	private void prepareConstants() {
		RANGE_CONSTRAINT = new Constraint<>(LONG_ATTRIBUTE_NAME,
				new AttributeValue<>(new LongRange(1000L, 3000L),
						LongRange.class), Operator.RANGE);
	}

	@Test
	public void testMatchRange() {
		EventImpl event = new EventImpl();
		event.addAttribute(STRING_ATTRIBUTE);
		event.addAttribute(LONG_ATTRIBUTE1);

		List<Subscription> predicates = tree.match(event);
		assertEquals(1, predicates.size());
	}

	@Test
	public void testNoMatchRange() {
		EventImpl event = new EventImpl();
		event.addAttribute(STRING_ATTRIBUTE);
		event.addAttribute(LONG_ATTRIBUTE2);

		List<Subscription> predicates = tree.match(event);
		assertEquals(0, predicates.size());
	}

}
