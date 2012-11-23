package cz.muni.fi.publishsubscribe.countingtree.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import cz.muni.fi.publishsubscribe.countingtree.Attribute;
import cz.muni.fi.publishsubscribe.countingtree.AttributeValue;
import cz.muni.fi.publishsubscribe.countingtree.Constraint;
import cz.muni.fi.publishsubscribe.countingtree.CountingTree;
import cz.muni.fi.publishsubscribe.countingtree.Event;
import cz.muni.fi.publishsubscribe.countingtree.Filter;
import cz.muni.fi.publishsubscribe.countingtree.Operator;
import cz.muni.fi.publishsubscribe.countingtree.Predicate;

public class VariousTestCase {

	private static class FooClass implements Comparable<FooClass> {

		@Override
		public int compareTo(FooClass arg0) {
			return 0;
		}

	}

	@Test
	public void testEmptyTree() {
		CountingTree tree = new CountingTree();

		Event event = new Event();
		event.addAttribute(new Attribute<>("app", new AttributeValue<>("foo",
				String.class)));

		List<Predicate> predicates = tree.match(event);
		assertEquals(0, predicates.size());
	}

	@Test
	public void testUnsubscribeFromEmptyTree() {
		CountingTree tree = new CountingTree();

		Constraint<String> applicationFoo = new Constraint<>("app",
				new AttributeValue<>("foo", String.class), Operator.EQUALS);
		Filter fooFilter = new Filter();
		fooFilter.addConstraint(applicationFoo);
		Predicate predicateFoo = new Predicate();
		predicateFoo.addFilter(fooFilter);

		assertFalse(tree.unsubscribe(predicateFoo));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testUnsupportedOperator() {
		@SuppressWarnings("unused")
		Constraint<String> illegalConstraint = new Constraint<>("foo",
				new AttributeValue<>("foo", String.class), Operator.RANGE);
	}

	@Test
	public void unsubscribePredicateWithNullId() {
		CountingTree tree = new CountingTree();

		Predicate predicate = new Predicate();
		predicate.setId(null);

		assertFalse(tree.unsubscribe(predicate));
	}

	@Test
	public void removeConstraintFromEqualsIndexTwice() {
		CountingTree tree = new CountingTree();

		Constraint<String> constraint = new Constraint<>("foo",
				new AttributeValue<>("foo", String.class), Operator.EQUALS);
		Filter filter = new Filter();
		filter.addConstraint(constraint);
		Predicate predicate = new Predicate();
		predicate.addFilter(filter);

		long subscriptionId = tree.subscribe(predicate);
		assertTrue(tree.unsubscribe(subscriptionId));
		assertFalse(tree.unsubscribe(subscriptionId));
	}

}
