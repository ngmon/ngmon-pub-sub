package cz.muni.fi.publishsubscribe.countingtree.test;

import cz.muni.fi.publishsubscribe.countingtree.*;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class RangeTestCase {

	private static final String STRING_ATTRIBUTE_NAME = "stringAttribute";
	private static final String LONG_ATTRIBUTE_NAME = "longAttribute";

	public static final Attribute<String> STRING_ATTRIBUTE = new Attribute<>(STRING_ATTRIBUTE_NAME,
			new AttributeValue<>("foo", String.class));

	public static final Attribute<Long> LONG_ATTRIBUTE1 = new Attribute<>(LONG_ATTRIBUTE_NAME,
			new AttributeValue<>(2000L, Long.class));

	public static final Attribute<Long> LONG_ATTRIBUTE2 = new Attribute<>(LONG_ATTRIBUTE_NAME,
			new AttributeValue<>(5000L, Long.class));


	public static final Constraint<LongRange> RANGE_CONSTRAINT = new Constraint<>(LONG_ATTRIBUTE_NAME,
			new AttributeValue<>(new LongRange(1000L, 3000L), LongRange.class), Operator.RANGE);

	private CountingTree tree;


	@Before
	public void prepareTree() {
		this.tree = new CountingTree();

		Filter filter = new Filter();
		filter.addConstraint(RANGE_CONSTRAINT);

		Predicate predicate = new Predicate();
		predicate.addFilter(filter);

		this.tree.subscribe(predicate);

	}

	@Test
	public void testMatchRange() {
		Event event = new Event();
		event.addAttribute(STRING_ATTRIBUTE);
		event.addAttribute(LONG_ATTRIBUTE1);

		List<Predicate> predicates = tree.match(event);
		assertEquals(1, predicates.size());
	}

	@Test
	public void testNoMatchRange() {
		Event event = new Event();
		event.addAttribute(STRING_ATTRIBUTE);
		event.addAttribute(LONG_ATTRIBUTE2);

		List<Predicate> predicates = tree.match(event);
		assertEquals(0, predicates.size());
	}
        
        @Test
        public void testRangeContains() {
            Range range = new Range(null, 0L);
            assertTrue(range.contains(-1L));
            assertTrue(range.contains(0L));
            assertTrue(!range.contains(1L));
            
            range = new Range(0L, null);
            assertTrue(!range.contains(-1L));
            assertTrue(range.contains(0L));
            assertTrue(range.contains(1L));
            
            range = new Range(null, null);
            assertTrue(range.contains(-1L));
            assertTrue(range.contains(0L));
            assertTrue(range.contains(1L));
        }
        
        @Test
        public void testRangeIntersects() {
            Range rangeA = new Range(null, 0L);
            Range rangeB = new Range(0L, null);
            Range rangeC = new Range(null, null);
            Range range0 = new Range(-1L, 1L);
            assertTrue(rangeA.intersects(range0));
            assertTrue(rangeB.intersects(range0));
            assertTrue(rangeC.intersects(range0));
            
            range0 = new Range(-5L, 0L);
            assertTrue(rangeA.intersects(range0));
            assertTrue(rangeB.intersects(range0));
            assertTrue(rangeC.intersects(range0));
            
            range0 = new Range(0L, null);
            assertTrue(rangeA.intersects(range0));
            assertTrue(rangeB.intersects(range0));
            assertTrue(rangeC.intersects(range0));
            
            range0 = new Range(null, -5L);
            assertTrue(rangeA.intersects(range0));
            assertTrue(!rangeB.intersects(range0));
            assertTrue(rangeC.intersects(range0));
            
            range0 = new Range(null, null);
            assertTrue(rangeA.intersects(range0));
            assertTrue(rangeB.intersects(range0));
            assertTrue(rangeC.intersects(range0));
        }
        
        @Test
        public void testRangeTreeContains() {
            RangeTree rngTree = new RangeTree();
            rngTree.addRange(new LongRange(5L, 10L));
            rngTree.addRange(new LongRange(-5L, null));
            rngTree.addRange(new LongRange(-10L, 0L));
            rngTree.addRange(new LongRange(null, -10L));
            rngTree.addRange(new LongRange(null, null));
            
            assertTrue(rngTree.getRangesContaining(-1L).size() == 3);
            assertTrue(rngTree.getRangesContaining(-5L).size() == 3);
            assertTrue(rngTree.getRangesContaining(150L).size() == 2);
            assertTrue(rngTree.getRangesContaining(0L).size() == 3);
            
            assertTrue(rngTree.getRangesContaining(-150L).size() == 2);
            assertTrue(rngTree.getRangesContaining(-10L).size() == 3);
            assertTrue(rngTree.getRangesContaining(-15L).size() == 2);
        }

        @Test
        public void testRangeTreeIntersects() {
            RangeTree rngTree = new RangeTree();
            rngTree.addRange(new LongRange(5L, 10L));
            rngTree.addRange(new LongRange(-5L, null));
            rngTree.addRange(new LongRange(-10L, 0L));
            rngTree.addRange(new LongRange(null, -10L));
            rngTree.addRange(new LongRange(null, null));
            
            assertTrue(rngTree.getRangesIntersecting(new LongRange(8L,9L)).size() == 3);
            assertTrue(rngTree.getRangesIntersecting(new LongRange(8L,11L)).size() == 3);
            assertTrue(rngTree.getRangesIntersecting(new LongRange(10L,11L)).size() == 3);
            assertTrue(rngTree.getRangesIntersecting(new LongRange(800L,1100L)).size() == 2);
            assertTrue(rngTree.getRangesIntersecting(new LongRange(-1L,1L)).size() == 3);
            assertTrue(rngTree.getRangesIntersecting(new LongRange(-5L,0L)).size() == 3);
            assertTrue(rngTree.getRangesIntersecting(new LongRange(-6L,9L)).size() == 4);
            assertTrue(rngTree.getRangesIntersecting(new LongRange(-9L,-8L)).size() == 2);
        }
}
