package cz.muni.fi.publishsubscribe.countingtree.index.type;

public class TypeIndexFactory {

	public static <T1 extends Comparable<T1>> TypeIndex<T1> getTypeIndex(Class<T1> type) {

		if (type == String.class) {
			return (TypeIndex<T1>) new StringIndex();
		} else if (type == Long.class) {
			return (TypeIndex<T1>) new LongIndex();
		} else {
			throw new IllegalArgumentException(String.format("Index for type %s is not supported", type.getClass().getName()));
		}

	}
}
