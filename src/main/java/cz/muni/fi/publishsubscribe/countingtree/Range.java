package cz.muni.fi.publishsubscribe.countingtree;

public class Range<T1 extends Comparable<T1>> implements Comparable<Range<T1>> {
    private final T1 start;
    private final T1 end;

    public Range(T1 first, T1 second) {
        this.start = first;
        this.end = second;
    }

    public T1 getStart() {
        return start;
    }

    public T1 getEnd() {
        return end;
    }
    
    public boolean contains(T1 value) {
        if ((start.compareTo(value) <= 0) && (end.compareTo(value) >= 0)) {
            return true;
        }
        return false;
    }
    
    public boolean intersects(Range<T1> other) {
        if ((start.compareTo(other.end) <= 0) && (end.compareTo(other.start) >= 0)) {
            return true;
        }
        return false;
    }

    @Override
    public int compareTo(Range<T1> other) {
        if (start.compareTo(other.getStart()) != 0) {
            return start.compareTo(other.getStart());
        } else {
            return end.compareTo(other.getEnd());
        }
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Range)) {
            return false;
        }

        Range other = (Range) o;

        if (!this.start.equals(other.start)) {
            return false;
        }
        if (!this.end.equals(other.end)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = start.hashCode();
        result = 31 * result + end.hashCode();
        return result;
    }
}