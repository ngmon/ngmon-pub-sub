package cz.muni.fi.publishsubscribe.countingtree.test;

import cz.muni.fi.publishsubscribe.countingtree.AttributeValue;
import cz.muni.fi.publishsubscribe.countingtree.Constraint;
import cz.muni.fi.publishsubscribe.countingtree.Filter;
import cz.muni.fi.publishsubscribe.countingtree.LongRange;
import cz.muni.fi.publishsubscribe.countingtree.Operator;
import cz.muni.fi.publishsubscribe.countingtree.Predicate;
import cz.muni.fi.publishsubscribe.countingtree.ac.AccessController;
import static org.junit.Assert.*;
import org.junit.Test;

public class AccessControlTestCase {
    
    AccessController acl;
    
    @Test
    public void testDenyEquals() {
        Constraint<String> constrString = new Constraint<>("attr1", new AttributeValue<>("Abcd", String.class), Operator.EQUALS);
        Constraint<Long> constrLong = new Constraint<>("attr2", new AttributeValue<>(100L, Long.class), Operator.EQUALS);

        Filter f = new Filter();
        f.addConstraint(constrString);
        f.addConstraint(constrLong);
        Predicate pred = new Predicate();
        pred.addFilter(f);

        acl = new AccessController();
        acl.deny(1L, constrString);
        acl.deny(1L, constrLong);
        
        assertTrue(isAllowed(new Constraint<>("attr2", new AttributeValue<>(100L, Long.class), Operator.LESS_THAN)));
        assertTrue(isAllowed(new Constraint<>("attr2", new AttributeValue<>(99L, Long.class), Operator.LESS_THAN_OR_EQUAL_TO)));
        assertTrue(isAllowed(new Constraint<>("attr2", new AttributeValue<>(100L, Long.class), Operator.GREATER_THAN)));
        assertTrue(isAllowed(new Constraint<>("attr2", new AttributeValue<>(101L, Long.class), Operator.GREATER_THAN_OR_EQUAL_TO)));
        assertTrue(isAllowed(new Constraint<>("attr2", new AttributeValue<>(new LongRange(0L, 99L), LongRange.class), Operator.RANGE)));
        assertTrue(isAllowed(new Constraint<>("attr2", new AttributeValue<>(new LongRange(null, 99L), LongRange.class), Operator.RANGE)));
        assertTrue(isAllowed(new Constraint<>("attr2", new AttributeValue<>(new LongRange(101L, null), LongRange.class), Operator.RANGE)));
        assertTrue(isAllowed(new Constraint<>("attr2", new AttributeValue<>(99L, Long.class), Operator.EQUALS)));
        assertTrue(isAllowed(new Constraint<>("attr1", new AttributeValue<>("Abcdefgh", String.class), Operator.EQUALS)));
        assertTrue(isAllowed(new Constraint<>("attr1", new AttributeValue<>("Xyz", String.class), Operator.PREFIX)));
        
        assertTrue(! acl.subscribe(1L, pred));
        assertTrue(! isAllowed(new Constraint<>("attr2", new AttributeValue<>(101L, Long.class), Operator.LESS_THAN)));
        assertTrue(! isAllowed(new Constraint<>("attr2", new AttributeValue<>(100L, Long.class), Operator.LESS_THAN_OR_EQUAL_TO)));
        assertTrue(! isAllowed(new Constraint<>("attr2", new AttributeValue<>(99L, Long.class), Operator.GREATER_THAN)));
        assertTrue(! isAllowed(new Constraint<>("attr2", new AttributeValue<>(100L, Long.class), Operator.GREATER_THAN_OR_EQUAL_TO)));
        assertTrue(! isAllowed(new Constraint<>("attr2", new AttributeValue<>(new LongRange(100L, 200L), LongRange.class), Operator.RANGE)));
        assertTrue(! isAllowed(new Constraint<>("attr2", new AttributeValue<>(new LongRange(0L, null), LongRange.class), Operator.RANGE)));
        assertTrue(! isAllowed(new Constraint<>("attr2", new AttributeValue<>(new LongRange(null, 100L), LongRange.class), Operator.RANGE)));
        assertTrue(! isAllowed(new Constraint<>("attr2", new AttributeValue<>(new LongRange(null, null), LongRange.class), Operator.RANGE)));
        assertTrue(! isAllowed(new Constraint<>("attr2", new AttributeValue<>(100L, Long.class), Operator.EQUALS)));
        assertTrue(! isAllowed(new Constraint<>("attr1", new AttributeValue<>("Abcd", String.class), Operator.EQUALS)));
        assertTrue(! isAllowed(new Constraint<>("attr1", new AttributeValue<>("Ab", String.class), Operator.PREFIX)));
        assertTrue(! isAllowed(new Constraint<>("attr1", new AttributeValue<>("Abcd", String.class), Operator.PREFIX)));
    }
    
    @Test
    public void testDenyGreaterThan() {
        Constraint<Long> constrLong = new Constraint<>("attr2", new AttributeValue<>(100L, Long.class), Operator.GREATER_THAN);

        Filter f = new Filter();
        f.addConstraint(constrLong);
        Predicate pred = new Predicate();
        pred.addFilter(f);

        acl = new AccessController();
        acl.deny(1L, constrLong);
        
        assertTrue(isAllowed(new Constraint<>("attr2", new AttributeValue<>(100L, Long.class), Operator.LESS_THAN)));
        assertTrue(isAllowed(new Constraint<>("attr2", new AttributeValue<>(100L, Long.class), Operator.LESS_THAN_OR_EQUAL_TO)));
        assertTrue(isAllowed(new Constraint<>("attr2", new AttributeValue<>(new LongRange(0L, 100L), LongRange.class), Operator.RANGE)));
        assertTrue(isAllowed(new Constraint<>("attr2", new AttributeValue<>(new LongRange(null, 100L), LongRange.class), Operator.RANGE)));
        assertTrue(isAllowed(new Constraint<>("attr2", new AttributeValue<>(100L, Long.class), Operator.EQUALS)));
        
        assertTrue(! acl.subscribe(1L, pred));
        assertTrue(! isAllowed(new Constraint<>("attr2", new AttributeValue<>(101L, Long.class), Operator.LESS_THAN)));
        assertTrue(! isAllowed(new Constraint<>("attr2", new AttributeValue<>(101L, Long.class), Operator.LESS_THAN_OR_EQUAL_TO)));
        assertTrue(! isAllowed(new Constraint<>("attr2", new AttributeValue<>(150L, Long.class), Operator.GREATER_THAN)));
        assertTrue(! isAllowed(new Constraint<>("attr2", new AttributeValue<>(300L, Long.class), Operator.GREATER_THAN_OR_EQUAL_TO)));
        assertTrue(! isAllowed(new Constraint<>("attr2", new AttributeValue<>(new LongRange(50L, 101L), LongRange.class), Operator.RANGE)));
        assertTrue(! isAllowed(new Constraint<>("attr2", new AttributeValue<>(new LongRange(100L, null), LongRange.class), Operator.RANGE)));
        assertTrue(! isAllowed(new Constraint<>("attr2", new AttributeValue<>(new LongRange(null, 101L), LongRange.class), Operator.RANGE)));
        assertTrue(! isAllowed(new Constraint<>("attr2", new AttributeValue<>(new LongRange(null, null), LongRange.class), Operator.RANGE)));
        assertTrue(! isAllowed(new Constraint<>("attr2", new AttributeValue<>(300L, Long.class), Operator.EQUALS)));
    }
    
    @Test
    public void testDenyGreaterThanOrEqualTo() {
        Constraint<Long> constrLong = new Constraint<>("attr2", new AttributeValue<>(100L, Long.class), Operator.GREATER_THAN_OR_EQUAL_TO);

        Filter f = new Filter();
        f.addConstraint(constrLong);
        Predicate pred = new Predicate();
        pred.addFilter(f);

        acl = new AccessController();
        acl.deny(1L, constrLong);
        
        assertTrue(isAllowed(new Constraint<>("attr2", new AttributeValue<>(100L, Long.class), Operator.LESS_THAN)));
        assertTrue(isAllowed(new Constraint<>("attr2", new AttributeValue<>(99L, Long.class), Operator.LESS_THAN_OR_EQUAL_TO)));
        assertTrue(isAllowed(new Constraint<>("attr2", new AttributeValue<>(new LongRange(0L, 99L), LongRange.class), Operator.RANGE)));
        assertTrue(isAllowed(new Constraint<>("attr2", new AttributeValue<>(new LongRange(null, 99L), LongRange.class), Operator.RANGE)));
        assertTrue(isAllowed(new Constraint<>("attr2", new AttributeValue<>(99L, Long.class), Operator.EQUALS)));
        
        assertTrue(! acl.subscribe(1L, pred));
        assertTrue(! isAllowed(new Constraint<>("attr2", new AttributeValue<>(101L, Long.class), Operator.LESS_THAN)));
        assertTrue(! isAllowed(new Constraint<>("attr2", new AttributeValue<>(100L, Long.class), Operator.LESS_THAN_OR_EQUAL_TO)));
        assertTrue(! isAllowed(new Constraint<>("attr2", new AttributeValue<>(10L, Long.class), Operator.GREATER_THAN)));
        assertTrue(! isAllowed(new Constraint<>("attr2", new AttributeValue<>(10L, Long.class), Operator.GREATER_THAN_OR_EQUAL_TO)));
        assertTrue(! isAllowed(new Constraint<>("attr2", new AttributeValue<>(new LongRange(50L, 100L), LongRange.class), Operator.RANGE)));
        assertTrue(! isAllowed(new Constraint<>("attr2", new AttributeValue<>(new LongRange(100L, null), LongRange.class), Operator.RANGE)));
        assertTrue(! isAllowed(new Constraint<>("attr2", new AttributeValue<>(new LongRange(null, 100L), LongRange.class), Operator.RANGE)));
        assertTrue(! isAllowed(new Constraint<>("attr2", new AttributeValue<>(new LongRange(null, null), LongRange.class), Operator.RANGE)));
        assertTrue(! isAllowed(new Constraint<>("attr2", new AttributeValue<>(100L, Long.class), Operator.EQUALS)));
    }
    
    @Test
    public void testDenyLessThan() {
        Constraint<Long> constrLong = new Constraint<>("attr2", new AttributeValue<>(100L, Long.class), Operator.LESS_THAN);

        Filter f = new Filter();
        f.addConstraint(constrLong);
        Predicate pred = new Predicate();
        pred.addFilter(f);

        acl = new AccessController();
        acl.deny(1L, constrLong);
        
        assertTrue(isAllowed(new Constraint<>("attr2", new AttributeValue<>(100L, Long.class), Operator.GREATER_THAN)));
        assertTrue(isAllowed(new Constraint<>("attr2", new AttributeValue<>(100L, Long.class), Operator.GREATER_THAN_OR_EQUAL_TO)));
        assertTrue(isAllowed(new Constraint<>("attr2", new AttributeValue<>(new LongRange(100L, 150L), LongRange.class), Operator.RANGE)));
        assertTrue(isAllowed(new Constraint<>("attr2", new AttributeValue<>(new LongRange(100L, null), LongRange.class), Operator.RANGE)));
        assertTrue(isAllowed(new Constraint<>("attr2", new AttributeValue<>(100L, Long.class), Operator.EQUALS)));
        
        assertTrue(! acl.subscribe(1L, pred));
        assertTrue(! isAllowed(new Constraint<>("attr2", new AttributeValue<>(100L, Long.class), Operator.LESS_THAN)));
        assertTrue(! isAllowed(new Constraint<>("attr2", new AttributeValue<>(100L, Long.class), Operator.LESS_THAN_OR_EQUAL_TO)));
        assertTrue(! isAllowed(new Constraint<>("attr2", new AttributeValue<>(98L, Long.class), Operator.GREATER_THAN)));
        assertTrue(! isAllowed(new Constraint<>("attr2", new AttributeValue<>(99L, Long.class), Operator.GREATER_THAN_OR_EQUAL_TO)));
        assertTrue(! isAllowed(new Constraint<>("attr2", new AttributeValue<>(new LongRange(99L, 150L), LongRange.class), Operator.RANGE)));
        assertTrue(! isAllowed(new Constraint<>("attr2", new AttributeValue<>(new LongRange(99L, null), LongRange.class), Operator.RANGE)));
        assertTrue(! isAllowed(new Constraint<>("attr2", new AttributeValue<>(new LongRange(null, 100L), LongRange.class), Operator.RANGE)));
        assertTrue(! isAllowed(new Constraint<>("attr2", new AttributeValue<>(new LongRange(null, null), LongRange.class), Operator.RANGE)));
        assertTrue(! isAllowed(new Constraint<>("attr2", new AttributeValue<>(99L, Long.class), Operator.EQUALS)));
    }
    
    @Test
    public void testDenyLessThanOrEqualTo() {
        Constraint<Long> constrLong = new Constraint<>("attr2", new AttributeValue<>(100L, Long.class), Operator.LESS_THAN_OR_EQUAL_TO);

        Filter f = new Filter();
        f.addConstraint(constrLong);
        Predicate pred = new Predicate();
        pred.addFilter(f);

        acl = new AccessController();
        acl.deny(1L, constrLong);
        
        assertTrue(isAllowed(new Constraint<>("attr2", new AttributeValue<>(100L, Long.class), Operator.GREATER_THAN)));
        assertTrue(isAllowed(new Constraint<>("attr2", new AttributeValue<>(101L, Long.class), Operator.GREATER_THAN_OR_EQUAL_TO)));
        assertTrue(isAllowed(new Constraint<>("attr2", new AttributeValue<>(new LongRange(101L, 150L), LongRange.class), Operator.RANGE)));
        assertTrue(isAllowed(new Constraint<>("attr2", new AttributeValue<>(new LongRange(101L, null), LongRange.class), Operator.RANGE)));
        assertTrue(isAllowed(new Constraint<>("attr2", new AttributeValue<>(101L, Long.class), Operator.EQUALS)));
        
        assertTrue(! acl.subscribe(1L, pred));
        assertTrue(! isAllowed(new Constraint<>("attr2", new AttributeValue<>(100L, Long.class), Operator.LESS_THAN)));
        assertTrue(! isAllowed(new Constraint<>("attr2", new AttributeValue<>(100L, Long.class), Operator.LESS_THAN_OR_EQUAL_TO)));
        assertTrue(! isAllowed(new Constraint<>("attr2", new AttributeValue<>(99L, Long.class), Operator.GREATER_THAN)));
        assertTrue(! isAllowed(new Constraint<>("attr2", new AttributeValue<>(100L, Long.class), Operator.GREATER_THAN_OR_EQUAL_TO)));
        assertTrue(! isAllowed(new Constraint<>("attr2", new AttributeValue<>(new LongRange(100L, 200L), LongRange.class), Operator.RANGE)));
        assertTrue(! isAllowed(new Constraint<>("attr2", new AttributeValue<>(new LongRange(100L, null), LongRange.class), Operator.RANGE)));
        assertTrue(! isAllowed(new Constraint<>("attr2", new AttributeValue<>(new LongRange(null, 100L), LongRange.class), Operator.RANGE)));
        assertTrue(! isAllowed(new Constraint<>("attr2", new AttributeValue<>(new LongRange(null, null), LongRange.class), Operator.RANGE)));
        assertTrue(! isAllowed(new Constraint<>("attr2", new AttributeValue<>(100L, Long.class), Operator.EQUALS)));
    }
    
    @Test
    public void testDenyRange() {
        Constraint<LongRange> constrRng = new Constraint<>("attr2", new AttributeValue<>(new LongRange(0L, 100L), LongRange.class), Operator.RANGE);

        Filter f = new Filter();
        f.addConstraint(constrRng);
        Predicate pred = new Predicate();
        pred.addFilter(f);

        acl = new AccessController();
        acl.deny(1L, constrRng);
        
        assertTrue(isAllowed(new Constraint<>("attr2", new AttributeValue<>(0L, Long.class), Operator.LESS_THAN)));
        assertTrue(isAllowed(new Constraint<>("attr2", new AttributeValue<>(-1L, Long.class), Operator.LESS_THAN_OR_EQUAL_TO)));
        assertTrue(isAllowed(new Constraint<>("attr2", new AttributeValue<>(100L, Long.class), Operator.GREATER_THAN)));
        assertTrue(isAllowed(new Constraint<>("attr2", new AttributeValue<>(101L, Long.class), Operator.GREATER_THAN_OR_EQUAL_TO)));
        assertTrue(isAllowed(new Constraint<>("attr2", new AttributeValue<>(new LongRange(101L, 150L), LongRange.class), Operator.RANGE)));
        assertTrue(isAllowed(new Constraint<>("attr2", new AttributeValue<>(new LongRange(101L, null), LongRange.class), Operator.RANGE)));
        assertTrue(isAllowed(new Constraint<>("attr2", new AttributeValue<>(new LongRange(null, -1L), LongRange.class), Operator.RANGE)));
        assertTrue(isAllowed(new Constraint<>("attr2", new AttributeValue<>(101L, Long.class), Operator.EQUALS)));
        
        assertTrue(! acl.subscribe(1L, pred));
        assertTrue(! isAllowed(new Constraint<>("attr2", new AttributeValue<>(1L, Long.class), Operator.LESS_THAN)));
        assertTrue(! isAllowed(new Constraint<>("attr2", new AttributeValue<>(0L, Long.class), Operator.LESS_THAN_OR_EQUAL_TO)));
        assertTrue(! isAllowed(new Constraint<>("attr2", new AttributeValue<>(99L, Long.class), Operator.GREATER_THAN)));
        assertTrue(! isAllowed(new Constraint<>("attr2", new AttributeValue<>(100L, Long.class), Operator.GREATER_THAN_OR_EQUAL_TO)));
        assertTrue(! isAllowed(new Constraint<>("attr2", new AttributeValue<>(new LongRange(100L, 200L), LongRange.class), Operator.RANGE)));
        assertTrue(! isAllowed(new Constraint<>("attr2", new AttributeValue<>(new LongRange(100L, null), LongRange.class), Operator.RANGE)));
        assertTrue(! isAllowed(new Constraint<>("attr2", new AttributeValue<>(new LongRange(null, 0L), LongRange.class), Operator.RANGE)));
        assertTrue(! isAllowed(new Constraint<>("attr2", new AttributeValue<>(new LongRange(null, null), LongRange.class), Operator.RANGE)));
        assertTrue(! isAllowed(new Constraint<>("attr2", new AttributeValue<>(100L, Long.class), Operator.EQUALS)));
    }
    
    @Test
    public void testDenyPrefix() {
        Constraint<String> constrString = new Constraint<>("attr1", new AttributeValue<>("Abcd", String.class), Operator.PREFIX);

        Filter f = new Filter();
        f.addConstraint(constrString);
        Predicate pred = new Predicate();
        pred.addFilter(f);

        acl = new AccessController();
        acl.deny(1L, constrString);
        
        assertTrue(isAllowed(new Constraint<>("attr1", new AttributeValue<>("Ab", String.class), Operator.EQUALS)));
        assertTrue(isAllowed(new Constraint<>("attr1", new AttributeValue<>("Xyz", String.class), Operator.EQUALS)));
        assertTrue(isAllowed(new Constraint<>("attr1", new AttributeValue<>("Xyz", String.class), Operator.PREFIX)));
        
        assertTrue(! acl.subscribe(1L, pred));
        assertTrue(! isAllowed(new Constraint<>("attr1", new AttributeValue<>("Abcd", String.class), Operator.EQUALS)));
        assertTrue(! isAllowed(new Constraint<>("attr1", new AttributeValue<>("Ab", String.class), Operator.PREFIX)));
        assertTrue(! isAllowed(new Constraint<>("attr1", new AttributeValue<>("Abcd", String.class), Operator.PREFIX)));
        assertTrue(! isAllowed(new Constraint<>("attr1", new AttributeValue<>("Abcdefgh", String.class), Operator.PREFIX)));
    }
    
    private <T extends Comparable<T>> boolean isAllowed(Constraint<T> constraint) {
        Filter filter = new Filter();
        filter.addConstraint(constraint);
        Predicate predicate = new Predicate();
        predicate.addFilter(filter);
        return acl.subscribe(1L, predicate);
    }
}
