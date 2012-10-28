package cz.muni.fi.publishsubscribe.countingtree.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import cz.muni.fi.publishsubscribe.countingtree.AttributeValue;
import cz.muni.fi.publishsubscribe.countingtree.Constraint;
import cz.muni.fi.publishsubscribe.countingtree.ConstraintOperator;
import cz.muni.fi.publishsubscribe.countingtree.CountingTree;
import cz.muni.fi.publishsubscribe.countingtree.Event;
import cz.muni.fi.publishsubscribe.countingtree.Event.EventAttribute;
import cz.muni.fi.publishsubscribe.countingtree.Filter;
import cz.muni.fi.publishsubscribe.countingtree.Subscription;

public class EqualityTestCase {

	private static final String PROCESS_ID_ATTR = "processId";
	private static final String APPLICATION_ATTR = "application";

	private static final String APACHE_SERVER = "Apache Server";
	private static final String POSTGRE_SQL = "PostgreSQL";

	private CountingTree tree;

	private Subscription apache1000Subscription;
	private Subscription apacheSubscription1;
	private Subscription apacheSubscription2;
	private Subscription processId1000Subscription;
	private Subscription processId2000Subscription;
	private Subscription postgreSql3000Subscription;
	private Subscription postgreSqlSubscription;
	private Subscription apacheOrPostgreSqlSubscription;

	@Before
	public void prepareTree() {
		tree = new CountingTree();

		// Apache, 1000
		Constraint apacheConstraint = new Constraint(APPLICATION_ATTR,
				new AttributeValue(APACHE_SERVER), ConstraintOperator.EQUALS);
		Constraint processId1000Constraint = new Constraint(PROCESS_ID_ATTR,
				new AttributeValue(1000), ConstraintOperator.EQUALS);
		Filter apache1000Filter = new Filter();
		apache1000Filter.addConstraint(apacheConstraint);
		apache1000Filter.addConstraint(processId1000Constraint);
		apache1000Subscription = new Subscription();
		apache1000Subscription.addFilter(apache1000Filter);

		tree.subscribe(apache1000Subscription);

		// Apache - two subscriptions
		Filter apacheFilter = new Filter();
		apacheFilter.addConstraint(apacheConstraint);
		apacheSubscription1 = new Subscription();
		apacheSubscription1.addFilter(apacheFilter);

		tree.subscribe(apacheSubscription1);

		Filter apacheFilter2 = new Filter();
		apacheFilter2.addConstraint(apacheConstraint);
		apacheSubscription2 = new Subscription();
		apacheSubscription2.addFilter(apacheFilter2);

		tree.subscribe(apacheSubscription2);

		// Process ID = 1000
		Filter processId1000Filter = new Filter();
		processId1000Filter.addConstraint(processId1000Constraint);
		processId1000Subscription = new Subscription();
		processId1000Subscription.addFilter(processId1000Filter);

		tree.subscribe(processId1000Subscription);

		// Process ID = 2000
		Constraint processId2000Constraint = new Constraint(PROCESS_ID_ATTR,
				new AttributeValue(2000), ConstraintOperator.EQUALS);
		Filter processId2000Filter = new Filter();
		processId2000Filter.addConstraint(processId2000Constraint);
		processId2000Subscription = new Subscription();
		processId2000Subscription.addFilter(processId2000Filter);

		tree.subscribe(processId2000Subscription);

		// PostgreSQL, 3000
		Constraint postgreSqlConstraint = new Constraint(APPLICATION_ATTR,
				new AttributeValue(POSTGRE_SQL), ConstraintOperator.EQUALS);
		Constraint processId3000Constraint = new Constraint(PROCESS_ID_ATTR,
				new AttributeValue(3000), ConstraintOperator.EQUALS);
		Filter postgreSql3000Filter = new Filter();
		postgreSql3000Filter.addConstraint(postgreSqlConstraint);
		postgreSql3000Filter.addConstraint(processId3000Constraint);
		postgreSql3000Subscription = new Subscription();
		postgreSql3000Subscription.addFilter(postgreSql3000Filter);

		tree.subscribe(postgreSql3000Subscription);

		// PostgreSQL
		Filter postgreSqlFilter = new Filter();
		postgreSqlFilter.addConstraint(postgreSqlConstraint);
		postgreSqlSubscription = new Subscription();
		postgreSqlSubscription.addFilter(postgreSqlFilter);

		tree.subscribe(postgreSqlSubscription);
		
		// Apache or PostgreSQL
		Filter apacheFilter3 = new Filter();
		apacheFilter3.addConstraint(apacheConstraint);
		postgreSqlFilter = new Filter();
		postgreSqlFilter.addConstraint(postgreSqlConstraint);
		apacheOrPostgreSqlSubscription = new Subscription();
		apacheOrPostgreSqlSubscription.addFilter(apacheFilter3);
		apacheOrPostgreSqlSubscription.addFilter(postgreSqlFilter);
		
		tree.subscribe(apacheOrPostgreSqlSubscription);

		tree.createIndexTable();
	}

	@Test
	public void testNoMatchingSubscribers() {
		Event event = new Event();
		event.addAttribute(new EventAttribute(APPLICATION_ATTR,
				new AttributeValue("foo")));
		event.addAttribute(new EventAttribute(PROCESS_ID_ATTR,
				new AttributeValue(1234)));
		event.addAttribute(new EventAttribute("severity", new AttributeValue(1)));

		List<Subscription> subscriptions = tree.match(event);
		assertEquals(0, subscriptions.size());
	}

	@Test
	public void testApacheEvent() {
		Event event = new Event();
		event.addAttribute(new EventAttribute(APPLICATION_ATTR,
				new AttributeValue(APACHE_SERVER)));
		event.addAttribute(new EventAttribute(PROCESS_ID_ATTR,
				new AttributeValue(1234)));

		List<Subscription> subscriptions = tree.match(event);
		assertEquals(3, subscriptions.size());
		assertTrue(subscriptions.contains(apacheSubscription1));
		assertTrue(subscriptions.contains(apacheSubscription2));
		assertTrue(subscriptions.contains(apacheOrPostgreSqlSubscription));
	}

	@Test
	public void testApache1000Event() {
		Event event = new Event();
		event.addAttribute(new EventAttribute(APPLICATION_ATTR,
				new AttributeValue(APACHE_SERVER)));
		event.addAttribute(new EventAttribute(PROCESS_ID_ATTR,
				new AttributeValue(1000)));

		List<Subscription> subscriptions = tree.match(event);
		assertEquals(5, subscriptions.size());
		assertTrue(subscriptions.contains(apache1000Subscription));
		assertTrue(subscriptions.contains(apacheSubscription1));
		assertTrue(subscriptions.contains(apacheSubscription2));
		assertTrue(subscriptions.contains(processId1000Subscription));
		assertTrue(subscriptions.contains(apacheOrPostgreSqlSubscription));
	}

	@Test
	public void testApache2000Event() {
		Event event = new Event();
		event.addAttribute(new EventAttribute(APPLICATION_ATTR,
				new AttributeValue(APACHE_SERVER)));
		event.addAttribute(new EventAttribute(PROCESS_ID_ATTR,
				new AttributeValue(2000)));

		List<Subscription> subscriptions = tree.match(event);
		assertEquals(4, subscriptions.size());
		assertTrue(subscriptions.contains(apacheSubscription1));
		assertTrue(subscriptions.contains(apacheSubscription2));
		assertTrue(subscriptions.contains(processId2000Subscription));
		assertTrue(subscriptions.contains(apacheOrPostgreSqlSubscription));
	}

	@Test
	public void testPostgreSqlEvent() {
		Event event = new Event();
		event.addAttribute(new EventAttribute(APPLICATION_ATTR,
				new AttributeValue(POSTGRE_SQL)));
		event.addAttribute(new EventAttribute(PROCESS_ID_ATTR,
				new AttributeValue(2000)));
		
		List<Subscription> subscriptions = tree.match(event);
		assertEquals(3, subscriptions.size());
		assertTrue(subscriptions.contains(processId2000Subscription));
		assertTrue(subscriptions.contains(postgreSqlSubscription));
		assertTrue(subscriptions.contains(apacheOrPostgreSqlSubscription));
	}
	
	@Test
	public void testProcessId1000Event() {
		Event event = new Event();
		event.addAttribute(new EventAttribute(PROCESS_ID_ATTR,
				new AttributeValue(1000)));
		
		List<Subscription> subscriptions = tree.match(event);
		assertEquals(1, subscriptions.size());
		assertTrue(subscriptions.contains(processId1000Subscription));
		
	}
}
