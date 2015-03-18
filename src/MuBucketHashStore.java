import java.util.Iterator;
import java.util.LinkedList;

public class MuBucketHashStore<E> implements structures.CollectionI<E> {

	protected LinkedList<E>[] $store;

	protected int $size;
	protected int $seed;

	public MuBucketHashStore() {
		this(10);
	}

	public MuBucketHashStore(int size) {
		this(size, size);
	}

	protected MuBucketHashStore(int size, int seed) {
		$size = size;
		$seed = seed;
		$store = new LinkedList[size];
		for (int i = 0; i < $store.length; i++) {
			$store[i] = new LinkedList<E>();
		}
	}

	protected int hash(E e) {
		if (e instanceof String) {
			String s = (String)(e);
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
	 * Add Object to Store.
	 *
	 * @param val to add
	 */
	@Override
	public void add(E val) {
		$store[hash(val)].add(val);
	}

	/**
	 * Determine whether Object is present in Store.
	 *
	 * @param val to search for
	 * @return
	 */
	@Override
	public boolean contains(E val) {
		return $store[hash(val)].contains(val);
	}

	/**
	 * Get array of non-null values in Store.
	 *
	 * @return array of values
	 */
	@Override
	public Object[] toArray() {
		Object[] array = new Object[0];
		Object[] temp;
		for (LinkedList l: $store) {
			temp = l.toArray();
			int leftlength = array.length;
			int rightlength = temp.length;
			Object[] buffer = new Object[rightlength + leftlength];
			System.arraycopy(array, 0, buffer, 0, leftlength);
			System.arraycopy(temp, 0, buffer, leftlength, rightlength);
			array = buffer;
		}
		return array;
	}

	/**
	 * Determine whether Store is empty.
	 *
	 * @return empty
	 */
	@Override
	public boolean isEmpty() {
		for (LinkedList l: $store) {
			if (!l.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Determine number of non-null elements in Store.
	 *
	 * @return size
	 */
	@Override
	public int size() {
		int size = 0;
		for (LinkedList l: $store) {
			size += l.size();
		}
		return size;
	}

	/**
	 * Remove an Object from the Store.
	 *
	 * @param val to remove
	 *
	 * @return whether value was removed
	 */
	@Override
	public boolean remove(E val) {
		$store[hash(val)].remove(val);
		return false;
	}


	/**
	 * Clear the Store.
	 */
	@Override
	public void clear() {
		$store = new LinkedList[$size];
		for (int i = 0; i < $store.length; i++) {
			$store[i] = new LinkedList<E>();
		}
	}

	/**
	 * Get iterator.
	 *
	 * @return iterator
	 */
	@Override
	public Iterator<E> iterator() {
		return new HashStoreIterator();
	}

	protected void resize() {
		MuBucketHashStore<E> buffer = new MuBucketHashStore<E>($size + $seed, $seed);
		for (int i = 0; i < $size; i++) {
			for (E e: $store[i]) {
				buffer.add(e);
			}
		}
		$size += $seed;
		$store = buffer.$store;
	}

	protected class HashStoreIterator implements Iterator<E> {

		int index = 0;
		Iterator<E> innerIterator = new Iterator<E>() {
			@Override
			public boolean hasNext() {
				return false;
			}

			@Override
			public E next() {
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
		public E next() {
			return innerIterator.next();
		}

		@Override
		public void remove() {
			innerIterator.remove();
		}
	}
}
