import structures.MapI;

import java.util.Iterator;

public class MuBucketHashMap<K, V> implements MapI<K, V> {

	protected MuListMap<K, V>[] $store;

	protected int $size;
	protected int $seed;

	public MuBucketHashMap() {
		this(10);
	}

	public MuBucketHashMap(int size) {
		this(size, size);
	}

	protected MuBucketHashMap(int size, int seed) {
		$size = size;
		$seed = seed;
		$store = new MuListMap[size];
		for (int i = 0; i < $store.length; i++) {
			$store[i] = new MuListMap<K, V>();
		}
	}

	/**
	 * Calculate a hash for a key.
	 *
	 * @param key to calculate hash for
	 *
	 * @return hash
	 */
	protected int hash(K key) {
		if (key instanceof String) {
			String s = (String)(key);
			int hash = 0;
			for (char c: s.toCharArray()) {
				hash += c;
			}
			return hash % $size;
		}
		else {
			System.out.println("Unsupported Type");
			return -1;
		}
	}

	/**
	 * Convert map to array of entries.
	 *
	 * @return array of entries
	 */
	@Override
	public Entry<K, V>[] toArray() {
		Entry<K, V>[] array = (Entry<K, V>[])(new Entry[0]);
		Object[] temp;
		for (MuListMap l: $store) {
			temp = l.toArray();
			int leftlength = array.length;
			int rightlength = temp.length;
			Entry[] buffer = new Entry[rightlength + leftlength];
			System.arraycopy(array, 0, buffer, 0, leftlength);
			System.arraycopy(temp, 0, buffer, leftlength, rightlength);
			array = buffer;
		}
		return array;
	}

	/**
	 * Determine whether map contains key.
	 *
	 * @param key to find
	 *
	 * @return whether key was found
	 */
	@Override
	public boolean contains(K key) {
		return $store[hash(key)].contains(key);
	}

	/**
	 * Retrieve a value by its key.
	 *
	 * @param key to use
	 *
	 * @return value associated with key
	 */
	@Override
	public V get(K key) {
		return $store[hash(key)].get(key);
	}

	/**
	 * Determine number of entries.
	 *
	 * @return number of entries
	 */
	@Override
	public int size() {
		int size = 0;
		for (MuListMap l: $store) {
			size += l.size();
		}
		return size;
	}

	/**
	 * Remove entry by key.
	 *
	 * @param key to use
	 *
	 * @return whether entry was removed
	 */
	@Override
	public boolean remove(K key) {
		return $store[hash(key)].remove(key);
	}

	/**
	 * Set or add an entry.
	 *
	 * @param key to use
	 * @param value to set
	 *
	 * @return whether a new entry was added
	 */
	@Override
	public boolean set(K key, V value) {
		return $store[hash(key)].set(key, value);
	}

	/**
	 * Clear the map.
	 */
	public void clear() {
		$store = new MuListMap[$size];
		for (int i = 0; i < $store.length; i++) {
			$store[i] = new MuListMap<K, V>();
		}
	}

	/**
	 * Get iterator for the map.
	 *
	 * @return iterator
	 */
	@Override
	public Iterator<Entry<K, V>> iterator() {
		return new BucketHashMapIterator();
	}

	/**
	 * Resize the map.
	 */
	protected void resize() {
		MuBucketHashMap<K, V> buffer = new MuBucketHashMap<K, V>($size + $seed, $seed);
		for (int i = 0; i < $size; i++) {
			for (Entry<K, V> e: $store[i]) {
				buffer.set(e.getKey(), e.getValue());
			}
		}
		$size += $seed;
		$store = buffer.$store;
	}

	protected class BucketHashMapIterator implements Iterator<Entry<K, V>> {

		int index = 0;
		Iterator<Entry<K, V>> innerIterator = new Iterator<Entry<K, V>>() {
			@Override
			public boolean hasNext() {
				return false;
			}

			@Override
			public Entry<K, V> next() {
				return null;
			}
		};

		@Override
		public boolean hasNext() {
			while (index < $size && !innerIterator.hasNext()) {
				innerIterator = $store[index++].iterator();
			}

			return innerIterator.hasNext();
		}

		@Override
		public Entry<K, V> next() {
			return innerIterator.next();
		}

		@Override
		public void remove() {
			innerIterator.remove();
		}
	}

	protected static class ListEntry<K, V> implements Entry<K, V> {

		protected K $key;
		protected V $value;

		public ListEntry(K key) {
			$key = key;
		}

		public ListEntry(K key, V value) {
			this(key);
			$value = value;
		}

		@Override
		public void setValue(V value) {
			$value = value;
		}

		@Override
		public V getValue() {
			return $value;
		}

		@Override
		public K getKey() {
			return $key;
		}

		@Override
		public boolean equals(Object o) {
			if (o instanceof Entry) {
				Entry<K, V> entry = (Entry<K, V>) (o);
				return entry.getKey().equals(getKey()) && entry.getValue().equals(getValue());
			}
			return false;
		}

		@Override
		public String toString() {
			return "<" + $key.toString() + ":" + $value.toString() + ">";
		}
	}
}
