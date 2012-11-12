package cz.muni.fi.publishsubscribe.countingtree.index.operator;

import cz.muni.fi.publishsubscribe.countingtree.Constraint;
import cz.muni.fi.publishsubscribe.countingtree.Operator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NavigableMap;
import java.util.TreeMap;

public class RangeIndex<T_ValueType extends Comparable<?>> implements OperatorIndex<T_ValueType> {

    //asi by to malo pouzivat uz existujuce LessThanOrEqualsIndex a GreaterThanOrEqualsIndex, ale neviem ako :/
    private NavigableMap<T_ValueType, Constraint<T_ValueType>> lowerBoundConstraints;
    private NavigableMap<T_ValueType, Constraint<T_ValueType>> upperBoundConstraints;

    public RangeIndex() {
        this(null);
    }

    public RangeIndex(Comparator<T_ValueType> comparator) {
        this.lowerBoundConstraints = new TreeMap<>(comparator);
        this.upperBoundConstraints = new TreeMap<>(comparator);
    }

    //co s tymto?
    @Override
    public boolean addConstraint(Constraint<T_ValueType> constraint) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public boolean addLowerBoundConstraint(Constraint<T_ValueType> constraint) {
        T_ValueType value = constraint.getAttributeValue().getValue();

        if (this.lowerBoundConstraints.containsKey(value)) {
            return false;
        }

        this.lowerBoundConstraints.put(value, constraint);

        return true;
    }
    
    public boolean addUpperBoundConstraint(Constraint<T_ValueType> constraint) {
        T_ValueType value = constraint.getAttributeValue().getValue();

        if (this.upperBoundConstraints.containsKey(value)) {
            return false;
        }

        this.upperBoundConstraints.put(value, constraint);

        return true;
    }
    
    @Override
    public List<Constraint<T_ValueType>> getConstraints(T_ValueType attributeValue) {
        List<Constraint<T_ValueType>> constraints = new ArrayList<>(this.lowerBoundConstraints.tailMap(attributeValue, true).values());
        constraints.addAll(new ArrayList<>(this.upperBoundConstraints.headMap(attributeValue, true).values()));
        
        return constraints;
    }

    //?
    @Override
    public boolean removeConstraint(Constraint<T_ValueType> constraint) {
        if (constraint.getOperator() == Operator.LESS_THAN_OR_EQUAL_TO) {
            lowerBoundConstraints.remove(constraint.getAttributeValue().getValue());
            return true;
        } else {
            if (constraint.getOperator() == Operator.GREATER_THAN_OR_EQUAL_TO) {
                upperBoundConstraints.remove(constraint.getAttributeValue().getValue());
                return true;
            } else {
                return false;
            }
        }
    }

}
