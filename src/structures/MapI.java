package structures;

public interface MapI<K,V> extends Iterable<MapI.Entry<K,V>>{

	public boolean contains(K search);
	
	public V get(K search);
	
	public int size();
	
	
	public boolean remove(K search);
	
	
	public boolean set(K newK,V newV);
	
	public static interface Entry<K,V>{
		public void setValue(V newV);
		public V getValue();
		public K getKey();
	}

	public MapI.Entry<K,V>[] toArray();
}