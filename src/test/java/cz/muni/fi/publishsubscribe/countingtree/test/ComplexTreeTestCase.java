package cz.muni.fi.publishsubscribe.countingtree.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import cz.muni.fi.publishsubscribe.countingtree.Attribute;
import cz.muni.fi.publishsubscribe.countingtree.AttributeValue;
import cz.muni.fi.publishsubscribe.countingtree.Constraint;
import cz.muni.fi.publishsubscribe.countingtree.CountingTree;
import cz.muni.fi.publishsubscribe.countingtree.Event;
import cz.muni.fi.publishsubscribe.countingtree.Filter;
import cz.muni.fi.publishsubscribe.countingtree.Operator;
import cz.muni.fi.publishsubscribe.countingtree.Predicate;

public class ComplexTreeTestCase {

	private static final String PROCESS_ID_ATTR = "processId";
	private static final String APPLICATION_ATTR = "application";

	private static final String APACHE_SERVER = "Apache Server";
	private static final String POSTGRE_SQL = "PostgreSQL";

	private CountingTree tree;
	
	private Predicate predicate01;
	private Predicate predicate02;
	private Predicate predicate03;
	private Predicate predicate04;

	@Before
	public void prepareTree() {
		tree = new CountingTree();

		// Apache, processId < 1000
		Constraint<?> apacheConstraint = new Constraint<String>(
				APPLICATION_ATTR, new AttributeValue<String>(APACHE_SERVER,
						String.class), Operator.EQUALS);

		Constraint<?> pIdLessThan1000 = new Constraint<Long>(PROCESS_ID_ATTR,
				new AttributeValue<Long>(1000L, Long.class), Operator.LESS_THAN);

		Filter filter01 = new Filter();
		filter01.addConstraint(apacheConstraint);
		filter01.addConstraint(pIdLessThan1000);

		predicate01 = new Predicate();
		predicate01.addFilter(filter01);

		tree.subscribe(predicate01);

		// PostgreSQL, processId >= 1000
		Constraint<?> postgreSqlConstraint = new Constraint<String>(
				APPLICATION_ATTR, new AttributeValue<String>(POSTGRE_SQL,
						String.class), Operator.EQUALS);

		Constraint<?> pIdGreaterThanOrEqual1000 = new Constraint<Long>(
				PROCESS_ID_ATTR, new AttributeValue<Long>(1000L, Long.class),
				Operator.GREATER_THAN_OR_EQUAL_TO);

		Filter filter02 = new Filter();
		filter02.addConstraint(postgreSqlConstraint);
		filter02.addConstraint(pIdGreaterThanOrEqual1000);

		predicate02 = new Predicate();
		predicate02.addFilter(filter02);

		tree.subscribe(predicate02);

		// processId > 2000
		Constraint<?> pIdGreaterThan2000 = new Constraint<Long>(
				PROCESS_ID_ATTR, new AttributeValue<Long>(2000L, Long.class),
				Operator.GREATER_THAN);

		Filter filter03 = new Filter();
		filter03.addConstraint(pIdGreaterThan2000);

		predicate03 = new Predicate();
		predicate03.addFilter(filter03);

		tree.subscribe(predicate03);

		// Apache OR PostgreSQL, processId >= 2000
		Filter apacheFilter = new Filter();
		apacheFilter.addConstraint(apacheConstraint);

		Constraint<?> pIdGreaterThanOrEqual2000 = new Constraint<Long>(
				PROCESS_ID_ATTR, new AttributeValue<Long>(2000L, Long.class),
				Operator.GREATER_THAN_OR_EQUAL_TO);

		Filter filter04 = new Filter();
		filter04.addConstraint(postgreSqlConstraint);
		filter04.addConstraint(pIdGreaterThanOrEqual2000);

		predicate04 = new Predicate();
		predicate04.addFilter(apacheFilter);
		predicate04.addFilter(filter04);

		tree.subscribe(predicate04);

		tree.createIndexTable();
	}

	@Test
	public void testNoMatchingSubscribers() {
		Event event = new Event();
		event.addAttribute(new Attribute(APPLICATION_ATTR,
				new AttributeValue<String>("foo", String.class)));
		
		List<Predicate> predicates = tree.match(event);
		assertEquals(0, predicates.size());
	}
	
	@Test
	public void testApacheEvent() {
		Event event = new Event();
		event.addAttribute(new Attribute(APPLICATION_ATTR,
				new AttributeValue<String>(APACHE_SERVER, String.class)));
		event.addAttribute(new Attribute(PROCESS_ID_ATTR,
				new AttributeValue<Long>(1234L, Long.class)));

		List<Predicate> predicates = tree.match(event);
		assertEquals(1, predicates.size());
		assertTrue(predicates.contains(predicate04));
	}
}
