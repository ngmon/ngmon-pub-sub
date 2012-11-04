package cz.muni.fi.publishsubscribe.countingtree.index.type;

public class TypeIndexFactory {

	public static TypeIndex<? extends Comparable<?>> getTypeIndex(Class<? extends Comparable<?>> type) {

		if (type == String.class) {
			return new StringIndex();
		} else if (type == Long.class) {
			return new StringIndex();
		} else {
			throw new IllegalArgumentException(String.format("Index for type %s is not supported", type.getClass().getName()));
		}

	}
}
