import org.junit.Test;
import structures.MapI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import static org.junit.Assert.*;

public class MuMapTests {

	@Test
	public void itInitializes() {
		new MuBucketHashMap();
	}

	@Test
	public void itSets() {
		MuBucketHashMap<String, String> map = new MuBucketHashMap<String, String>();
		map.set("A", "B");
	}

	@Test
	public void itSetsAndGets() {
		MuBucketHashMap<String, String> map = new MuBucketHashMap<String, String>();
		map.set("A", "B");
		assertEquals(map.get("A"), "B");
	}

	@Test
	public void itCorrectlyReportsOnSet() {
		MuBucketHashMap<String, String> map = new MuBucketHashMap<String, String>();
		assertTrue(map.set("A", "B"));
		assertFalse(map.set("A", "C"));
	}

	@Test
	public void itGetsSize() {
		MuBucketHashMap<String, String> map = new MuBucketHashMap<String, String>();
		map.set("A", "B");
		map.set("A", "C");
		map.set("B", "B");
		map.set("C", "B");
		assertEquals(map.size(), 3);
	}

	@Test
	public void itContains() {
		MuBucketHashMap<String, String> map = new MuBucketHashMap<String, String>();
		map.set("A", "B");
		assertTrue(map.contains("A"));
		map.remove("A");
		assertFalse(map.contains("A"));
	}

	@Test
	public void itRemoves() {
		MuBucketHashMap<String, String> map = new MuBucketHashMap<String, String>();
		map.set("A", "B");
		map.remove("A");
		assertEquals(map.size(), 0);
	}

	@Test
	public void itConvertsToArray() {
		MuBucketHashMap<String, String> map = new MuBucketHashMap<String, String>();
		map.set("A", "B");
		map.set("A", "C");
		map.set("B", "B");
		map.set("C", "B");
		assertArrayEquals(map.toArray(), new MuBucketHashMap.Entry[] {
			new MuBucketHashMap.ListEntry<String, String>("A", "C"), new MuBucketHashMap.ListEntry<String, String>("B", "B"), new MuBucketHashMap.ListEntry<String, String>("C", "B")
		});
	}

	@Test
	public void itIterates() {
		MuBucketHashMap<String, String> map = new MuBucketHashMap<String, String>();
		map.set("A", "B");
		map.set("A", "C");
		map.set("B", "B");
		map.set("C", "B");
		ArrayList<MapI.Entry<String, String>> list = new ArrayList<MapI.Entry<String, String>>();
		for (MapI.Entry<String, String> entry: map) {
			list.add(entry);
		}
		assertArrayEquals(list.toArray(), map.toArray());
	}

	@Test
	public void itImitatesRegular() {
		HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
		MuBucketHashMap<String, Integer> map = new MuBucketHashMap<String, Integer>();
		Random r = new Random();
		for (int i = 0; i < 1000; i++) {
			String s = "" + r.nextInt(10) + r.nextInt(10) + r.nextInt(10);
			int value = r.nextInt();
			hashMap.put(s, value);
			map.set(s, value);
		}
		for (String s: hashMap.keySet()) {
			assertEquals(map.get(s), hashMap.get(s));
		}
		for (MapI.Entry<String, Integer> e: map) {
			assertEquals(map.get(e.getKey()), hashMap.get(e.getKey()));
		}
	}

}
