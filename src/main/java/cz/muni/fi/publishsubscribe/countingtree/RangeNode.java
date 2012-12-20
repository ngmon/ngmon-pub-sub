package cz.muni.fi.publishsubscribe.countingtree;

import java.util.*;

/**
 * The Node class contains the RangeTree information for a single node
 *
 * @author Kevin Dolan (http://www.thekevindolan.com/2010/02/interval-tree/index.html), adapted
 */
public class RangeNode<T extends Comparable<T>> {

    private T centre;
    private SortedSet<Range<T>> ranges; //all ranges overlapping the centre
    private RangeNode<T> leftNode;
    private RangeNode<T> rightNode;

    public RangeNode() {
        ranges = new TreeSet<>();
        centre = null;
        leftNode = null;
        rightNode = null;
    }

    public RangeNode(Set<Range<T>> rangeSet) {
        ranges = new TreeSet<>();
        SortedSet<T> endpoints = new TreeSet<>();

        for (Range<T> r : rangeSet) {
            if (r.getStart() != null) {
                endpoints.add(r.getStart());
            }
            if (r.getEnd() != null) {
                endpoints.add(r.getEnd());
            }
        }

        T median = getMedian(endpoints);
        centre = median;

        Set<Range<T>> left = new HashSet<>();
        Set<Range<T>> right = new HashSet<>();
        
        if (median != null) {
            for (Range<T> r : rangeSet) {
                if (r.isLeftUnbounded()) {
                    if (r.isRightUnbounded()) {
                        ranges.add(r);
                    } else {
                        if (r.getEnd().compareTo(median) < 0) {
                            left.add(r);
                        } else {
                            ranges.add(r);
                        }
                    }
                } else {
                    if (r.isRightUnbounded()) {
                        if (r.getStart().compareTo(median) > 0) {
                            right.add(r);
                        } else {
                            ranges.add(r);
                        }
                    } else {
                        if (r.getEnd().compareTo(median) < 0) {
                            left.add(r);
                        } else {
                            if (r.getStart().compareTo(median) > 0) {
                                right.add(r);
                            } else {
                                ranges.add(r);
                            }
                        }
                    }
                }
            }
        } else {
            ranges.addAll(rangeSet);
        }
        
        if (left.size() > 0) {
            leftNode = new RangeNode<>(left);
        }
        if (right.size() > 0) {
            rightNode = new RangeNode<>(right);
        }
    }

    public T getCentre() {
        return centre;
    }

    public void setCentre(T centre) {
        this.centre = centre;
    }

    public RangeNode<T> getLeft() {
        return leftNode;
    }

    public void setLeft(RangeNode<T> left) {
        this.leftNode = left;
    }

    public RangeNode<T> getRight() {
        return rightNode;
    }

    public void setRight(RangeNode<T> right) {
        this.rightNode = right;
    }

    /**
     * Perform a stabbing getRanges on the node
     *
     * @param value
     * @return all ranges containing value
     */
    public Set<Range<T>> getRangesContaining(T value) {
        Set<Range<T>> result = new HashSet<>();

        if (this.ranges.isEmpty()) {
                return Collections.emptySet();
        }

        for (Range<T> r : ranges) {
            if (r.contains(value)) {
                result.add(r);
            }
        }
        
        if (leftNode != null && centre != null && value.compareTo(centre) < 0) {
            result.addAll(leftNode.getRangesContaining(value));
        } else if (rightNode != null && centre != null && value.compareTo(centre) > 0) {
            result.addAll(rightNode.getRangesContaining(value));
        }

        return result;
    }

    /**
     * Perform a range intersection getRanges on the node
     *
     * @param target
     * @return all Ranges intersecting with target
     */
    public Set<Range<T>> getRangesIntersecting(Range<T> target) {
        Set<Range<T>> result = new HashSet<>();

        for (Range<T> r : ranges) {
            if (r.intersects(target)) {
                result.add(r);
            }
        }

        if (leftNode != null && centre != null && target.getStart().compareTo(centre) < 0) {
            result.addAll(leftNode.getRangesIntersecting(target));
        }
        if (rightNode != null && centre != null && target.getEnd().compareTo(centre) > 0) {
            result.addAll(rightNode.getRangesIntersecting(target));
        }

        return result;
    }

    /**
     * @param set
     * @return the median of the set, not interpolated
     */
    private T getMedian(SortedSet<T> set) {
        int i = 0;
        int middle = set.size() / 2;
        for (T item : set) {
            if (i == middle) {
                return item;
            }
            i++;
        }
        return null;
    }
}
