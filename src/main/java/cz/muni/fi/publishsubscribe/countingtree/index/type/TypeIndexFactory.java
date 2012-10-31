package cz.muni.fi.publishsubscribe.countingtree.index.type;

public class TypeIndexFactory {

	public static <T_ValueType> TypeIndex<T_ValueType> getTypeIndex(Class<T_ValueType> type) {

		if (type == String.class) {
			return (TypeIndex<T_ValueType>) new StringIndex();
		} else if (type == Long.class) {
			return (TypeIndex<T_ValueType>) new LongIndex();
		} else {
			throw new IllegalArgumentException(String.format("Index for type %s is not supported", type.getClass().getName()));
		}

	}
}
