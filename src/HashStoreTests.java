import org.junit.Test;
import structures.CollectionI;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

public class HashStoreTests {

	@Test
	public void itInitializes() {
		CollectionI<String> store = new MuBucketHashStore<String>();
	}

	@Test
	public void itAdds() {
		CollectionI<String> store = new MuBucketHashStore<String>();
		store.add("Hello");
	}

	@Test
	public void itAddsAndContains() {
		CollectionI<String> store = new MuBucketHashStore<String>();
		String s = "Hello";
		store.add(s);
		assertTrue(store.contains(s));
	}

	@Test
	public void itAddsAndContainsAmbiguous() {
		CollectionI<String> store = new MuBucketHashStore<String>();
		String s = "Hello";
		String t = "Hello";
		store.add(s);
		assertTrue(store.contains(t));
	}

	@Test
	public void itAddsAndRemovesAndNoLongerContainsAmbiguous() {
		CollectionI<String> store = new MuBucketHashStore<String>();
		String s = "Hello";
		String t = "Hello";
		store.add(s);
		store.remove(t);
		assertFalse(store.contains(t));
	}

	@Test
	public void itAddsAndKeepsProperSize() {
		CollectionI<String> store = new MuBucketHashStore<String>();
		String s = "Hello";
		String t = "Hello";
		String u = "Goodbye";
		store.add(s);
		store.add(t);
		store.add(u);
		assertEquals(3, store.size());
	}

	@Test
	public void itAddsAndClears() {
		CollectionI<String> store = new MuBucketHashStore<String>();
		String s = "Hello";
		store.add(s);
		store.clear();
		assertEquals(0, store.size());
	}

	@Test
	public void itAddsCorrectly() {
		CollectionI<String> store = new MuBucketHashStore<String>();
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < 7; i++) {
			list.add(String.valueOf(i * 13));
			store.add(String.valueOf(i * 13));
		}
		assertEquals(7, store.size());
		for (String s: list) {
			assertTrue(store.contains(s));
		}
		for (String s: store) {
			assertTrue(list.contains(s));
		}
	}

	@Test
	public void itAddsPastInitialCapacity() {
		CollectionI<String> store = new MuBucketHashStore<String>();
		for (int i = 0; i < 25; i++) {
			store.add(String.valueOf(i));
		}
		assertEquals(25, store.size());
	}

	@Test
	public void itAddsPastInitialCapacityAndIsCorrect() {
		CollectionI<String> store = new MuBucketHashStore<String>();
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < 13; i++) {
			list.add(String.valueOf(i * 7));
			store.add(String.valueOf(i * 7));
		}
		assertEquals(13, store.size());
		for (String s: list) {
			assertTrue(store.contains(s));
		}
		for (String s: store) {
			assertTrue(list.contains(s));
		}
	}

	@Test
	public void itIteratesWhenEmpty() {
		CollectionI<String> store = new MuBucketHashStore<String>();
		for (String s: store) {
			fail();
		}
	}

	@Test
	public void itDoesNotHaveNextWhenEmpty() {
		CollectionI<String> store = new MuBucketHashStore<String>();
		assertFalse(store.iterator().hasNext());
	}

	@Test
	public void itIterates() {
		CollectionI<String> store = new MuBucketHashStore<String>();
		String[] values = {"A", "B", "C", "D", "E"};
		for (int i = 0; i < values.length; i++) {
			store.add(values[i]);
		}
		for (int i = 0; i < values.length; i++) {
			assertTrue(store.contains(values[i]));
		}
	}

	@Test
	public void itRemoves() {
		CollectionI<String> store = new MuBucketHashStore<String>();
		String[] values = {"A", "B", "C", "D", "E"};
		for (int i = 0; i < values.length; i++) {
			store.add(values[i]);
		}
		Iterator<String> iterator = store.iterator();
		if (iterator.hasNext()) {
			iterator.next();
			iterator.remove();
		}
		if (iterator.hasNext()) {
			iterator.next();
			iterator.remove();
		}
		assertArrayEquals(store.toArray(), new String[] {"C", "D", "E"});
	}

	@Test
	public void itRemovesEntirely() {
		CollectionI<String> store = new MuBucketHashStore<String>();
		String[] values = {"A", "B", "C", "D", "E"};
		for (int i = 0; i < values.length; i++) {
			store.add(values[i]);
		}
		Iterator<String> iterator = store.iterator();
		while (iterator.hasNext()) {
			iterator.next();
			iterator.remove();
		}
		assertArrayEquals(store.toArray(), new String[] {});
	}

}
