package cz.muni.fi.publishsubscribe.countingtree;

import java.util.List;

import cz.muni.fi.publishsubscribe.countingtree.Event.EventAttribute;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Hello world!");

		// create the tree, add some subscriptions...
		CountingTree countingTree = new CountingTree();

		Constraint apacheServer = new Constraint("application",
				new AttributeValue("Apache Server"), Operator.EQUALS);
		/*-
		Constraint processId = new Constraint("processId", new AttributeValue(
				4219), Operator.EQUALS);*/

		Filter apacheFilter = new Filter();
		apacheFilter.addConstraint(apacheServer);

		Filter apacheFilter2 = new Filter();
		apacheFilter2.addConstraint(apacheServer);

		Subscription apacheSubscription = new Subscription();
		apacheSubscription.addFilter(apacheFilter);

		/* Long apacheSubscriptionId = */countingTree
				.subscribe(apacheSubscription);

		Subscription apacheSubscription2 = new Subscription();
		apacheSubscription2.addFilter(apacheFilter2);

		/* Long apacheSubscriptionId2 = */countingTree
				.subscribe(apacheSubscription2);

		countingTree.createIndexTable();

		// create some events
		Event event1 = new Event();
		event1.addAttribute(new EventAttribute("application",
				new AttributeValue("PostgreSQL")));

		// create some events
		Event event2 = new Event();
		event2.addAttribute(new EventAttribute("application",
				new AttributeValue("Apache Server")));

		// and match them
		@SuppressWarnings("unused")
		List<Subscription> subscriptions = countingTree.match(event1);

		subscriptions = countingTree.match(event2);
	}

}
