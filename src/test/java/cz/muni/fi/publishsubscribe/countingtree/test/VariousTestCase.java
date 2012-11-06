package cz.muni.fi.publishsubscribe.countingtree.test;

import static org.junit.Assert.*;

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

	@Test
	public void testEmptyTree() {
		CountingTree tree = new CountingTree();

		Event event = new Event();
		event.addAttribute(new Attribute("app", new AttributeValue("foo",
				String.class)));

		List<Predicate> predicates = tree.match(event);
		assertEquals(0, predicates.size());
	}

	@Test
	public void testUnsubscribeFromEmptyTree() {
		CountingTree tree = new CountingTree();

		Constraint applicationFoo = new Constraint("app", new AttributeValue(
				"foo", String.class), Operator.EQUALS);
		Filter fooFilter = new Filter();
		fooFilter.addConstraint(applicationFoo);
		Predicate predicateFoo = new Predicate();
		predicateFoo.addFilter(fooFilter);

		assertFalse(tree.unsubscribe(predicateFoo));
	}

}
