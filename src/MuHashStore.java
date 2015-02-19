import java.util.Iterator;

public class MuHashStore<E> implements structures.CollectionI<E> {

	protected E[] $store;

	protected int $size;
	protected int $seed;

	public MuHashStore() {
		this(10);
	}

	public MuHashStore(int size) {
		this(size, size);
	}

	protected MuHashStore(int size, int seed) {
		$size = size;
		$seed = seed;
		$store = (E[])(new Object[$size]);
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

	@Override
	public void add(E val) {
		if (size() == $size) {
			resize();
		}
		int pointer = hash(val);
		while ($store[pointer] != null) {
			pointer++;
			if (pointer >= $size) {
				pointer = 0;
			}
		}
		$store[pointer] = val;
	}

	@Override
	public boolean contains(E val) {
		int pointer = hash(val);
		int first = pointer;
		while ($store[pointer] != null) {
			if ($store[pointer].equals(val)) {
				return true;
			}
			pointer++;
			if (pointer >= $size) {
				pointer = 0;
			}
			if (pointer == first) {
				break;
			}
		}
		return false;
	}

	@Override
	public Object[] toArray() {
		Object[] a = new Object[$size];
		int pointer = 0;
		for (int i = 0; i < $size; i++) {
			if ($store[i] != null) {
				a[pointer++] = $store[i];
			}
		}
		Object[] compact = new Object[pointer];
		for (int i = 0; i < pointer; i++) {
			compact[i] = a[i];
		}
		return compact;
	}

	@Override
	public boolean isEmpty() {
		for (int i = 0; i < $store.length; i++) {
			if ($store[i] != null) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int size() {
		int size = 0;
		for (int i = 0; i < $store.length; i++) {
			if ($store[i] != null) {
				size++;
			}
		}
		return size;
	}

	@Override
	public boolean remove(E val) {
		int hash = hash(val);
		int index;
		for (int i = 0; i < $size; i++) {
			index = (i + hash) % $size;
			if ($store[index].equals(val)) {
				$store[index] = null;
				return true;
			}
		}
		return false;
	}

	@Override
	public void clear() {
		$store = (E[])(new Object[$size]);
	}

	@Override
	public Iterator<E> iterator() {
		return new HashStoreIterator();
	}

	protected void resize() {
		MuHashStore<E> buffer = new MuHashStore<E>($size + $seed, $seed);
		for (int i = 0; i < $size; i++) {
			if ($store[i] != null) {
				buffer.add($store[i]);
			}
		}
		$size += $seed;
		$store = buffer.$store;
	}

	protected class HashStoreIterator implements Iterator<E> {

		int index = 0;

		@Override
		public boolean hasNext() {
			for (int i = index; i < $size; i++) {
				if ($store[i] != null) {
					return true;
				}
			}
			return false;
		}

		@Override
		public E next() {
			while ($store[index] == null) {
				index++;
			}
			return $store[index++];
		}
	}
}
