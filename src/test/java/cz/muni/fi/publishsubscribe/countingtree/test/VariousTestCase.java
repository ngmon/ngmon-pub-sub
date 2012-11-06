package cz.muni.fi.publishsubscribe.countingtree.test;

import cz.muni.fi.publishsubscribe.countingtree.*;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class VariousTestCase {

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

		Constraint<String> applicationFoo = new Constraint<>("app", new AttributeValue<>(
				"foo", String.class), Operator.EQUALS);
		Filter fooFilter = new Filter();
		fooFilter.addConstraint(applicationFoo);
		Predicate predicateFoo = new Predicate();
		predicateFoo.addFilter(fooFilter);

		assertFalse(tree.unsubscribe(predicateFoo));
	}

}
