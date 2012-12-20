package cz.muni.fi.publishsubscribe.countingtree;

public class LongRange extends Range<Long> implements Comparable<LongRange> {

	public LongRange(Long first, Long second) {
		super(first, second);
	}

	@Override
	public int compareTo(LongRange other) {
            if (super.isLeftUnbounded()) {
                if (super.isRightUnbounded()) {
                    if (other.isLeftUnbounded() || other.isRightUnbounded()) {
                        return 0;
                    } else {
                        return 1;
                    }
                } else {
                    if (other.isLeftUnbounded()) {
                        if (other.isRightUnbounded()) {
                            return -1;
                        } else {
                            return super.getEnd().compareTo(other.getEnd());
                        }
                    } else {
                        return super.getEnd().compareTo(other.getStart());
                    }
                }
            } else {
                if (super.isRightUnbounded()) {
                    if (other.isLeftUnbounded()) {
                        if (other.isRightUnbounded()) {
                            return -1;
                        } else {
                            return super.getStart().compareTo(other.getEnd());
                        }
                    } else {
                        return super.getStart().compareTo(other.getStart());
                    }
                } else {
                    if (other.isLeftUnbounded()) {
                        if (other.isRightUnbounded()) {
                            return -1;
                        } else {
                            return super.getStart().compareTo(other.getEnd());
                        }
                    } else {
                        return super.getStart().compareTo(other.getStart());
                    }
                }
            }
	}
}
