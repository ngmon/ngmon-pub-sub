package cz.muni.fi.publishsubscribe.countingtree;

public class Range<T1 extends Comparable<T1>> {
    private final T1 start;
    private final T1 end;
    private final boolean leftUnbounded;
    private final boolean rightUnbounded;

    public Range(T1 first, T1 second) {
        leftUnbounded = (first == null);
        rightUnbounded = (second == null);
        if (!leftUnbounded && !rightUnbounded) {
            if (first.compareTo(second) <= 0) {
                this.start = first;
                this.end = second;
            } else {
                this.start = second;
                this.end = first;
            }
        } else {
            this.start = first;
            this.end = second;
        }
    }

    public T1 getStart() {
        return start;
    }

    public T1 getEnd() {
        return end;
    }

    public boolean isLeftUnbounded() {
        return leftUnbounded;
    }

    public boolean isRightUnbounded() {
        return rightUnbounded;
    }

    public boolean contains(T1 value) {
        if (leftUnbounded) {
            if (rightUnbounded) {
                return true;
            } else {
                if (end.compareTo(value) >= 0) {
                    return true;
                } else {
                    return false;
                }
            }
        } else {
            if (rightUnbounded) {
                if (start.compareTo(value) <= 0) {
                    return true;
                } else {
                    return false;
                }
            } else {
                if ((start.compareTo(value) <= 0) && (end.compareTo(value) >= 0)) {
                    return true;
                } else {
                    return false;
                }
            }
        }
    }
    
    public boolean intersects(Range<T1> other) {
        if (leftUnbounded) {
            if (rightUnbounded) {
                return true;
            } else {
                if (other.leftUnbounded) {
                    return true;
                } else {
                    if (end.compareTo(other.start) >= 0) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        } else {
            if (rightUnbounded) {
                if (other.rightUnbounded) {
                    return true;
                } else {
                    if (start.compareTo(other.end) <= 0) {
                        return true;
                    } else {
                        return false;
                    }
                }
            } else {
                if (other.leftUnbounded) {
                    if (other.rightUnbounded) {
                        return true;
                    } else {
                        if (start.compareTo(other.end) <= 0) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                } else {
                    if (other.rightUnbounded) {
                        if (end.compareTo(other.start) >= 0) {
                            return true;
                        } else {
                            return false;
                        }
                    } else {
                        if ((start.compareTo(other.end) <= 0) && (end.compareTo(other.start) >= 0)) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                }
            }
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
        
        if ((this.leftUnbounded == other.leftUnbounded) && (this.rightUnbounded == other.rightUnbounded)) {
            if (leftUnbounded) {
                if (rightUnbounded) {
                    return true;
                } else {
                    if (this.end.equals(other.end)) {
                        return true;
                    }
                }
            } else {
                if (this.start.equals(other.start)) {
                    if (rightUnbounded) {
                        return true;
                    } else {
                        if (this.end.equals(other.end)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = 0;
        if (start != null) {
            result = start.hashCode();
        }
        result = 31 * result;
        if (end != null) {
            result += end.hashCode();
        }
        return result;
    }
}