package structures;

/**
 * A number of elements, not necessarily ordered. Duplicates may or may not be accepted
 *
 * @param <V>
 */
public interface CollectionI<V> extends Iterable<V> {
	
	

	/**
	 * Add an element to the Collection
	 * @param val value to add 
	 */
	public void add(V val);
	
	/**
	 * Determine if an element is in the collection, using .equals()
	 * @param val search for this  
	 * @return true if found
	 */
	public boolean contains(V val);
 

	/** @return all elements*/
	public Object[] toArray( ); 
	 

	/**
	 * 
	 * @return true if there's an element in the list
	 */
	public boolean isEmpty();
	

	/**
	 * @return element count
	 */
	public int size();

	/**
	 * Remove an element from the collection
	 * @param val remove this element
	 * @return true if element was present and removed
	 */
	public boolean remove(V val);

	/**
	 * Remove all items
	 */
	public void clear();
}
