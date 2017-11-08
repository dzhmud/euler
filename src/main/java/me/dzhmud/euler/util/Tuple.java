package me.dzhmud.euler.util;

import java.util.Map;
import java.util.Objects;

/**
 * The very basic tuple implementation.
 *
 * @author dzhmud
 */
public class Tuple<K,V> implements Map.Entry<K,V> {

	public static <K,V> Tuple<K,V> of(K key, V value) {
		return new Tuple<>(key, value);
	}

	private final K key;
	private final V value;

	public Tuple(K key, V value) {
		this.key = key;
		this.value = value;
	}

	@Override
	public K getKey() {
		return key;
	}

	@Override
	public V getValue() {
		return value;
	}

	@Override
	public V setValue(Object value) {
		throw new UnsupportedOperationException();
	}

	public final boolean equals(Object o) {
		if (o == this)
			return true;
		if (o instanceof Tuple) {
			Tuple tuple = (Tuple)o;
			if (Objects.equals(key, tuple.getKey()) && Objects.equals(value, tuple.getValue()))
				return true;
		}
		return false;
	}

	public final int hashCode() {
		return Objects.hashCode(key) ^ Objects.hashCode(value);
	}

}
