package jp.taira.libs.utils;

import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class CollectionUtilsTest {

    @Test
    public void constractorTest() {
        assertThrows(IllegalAccessError.class, () -> {
            try {
                Constructor<?> constructor = CollectionUtils.class.getDeclaredConstructor();
                constructor.setAccessible(true);
                constructor.newInstance();
            } catch (InvocationTargetException e) {
                throw e.getCause();
            }
        });
    }

    @Test
    public void isEmptyTest() {
        { // 空のListオブジェクト
            final List<String> list = null;

            assertTrue(CollectionUtils.isEmpty(list));
        }

        { // 空のListオブジェクト
            final List<String> list = new ArrayList<>();

            assertTrue(CollectionUtils.isEmpty(list));
        }

        { // Listオブジェクト
            final List<String> list = new LinkedList<>();
            list.add("value1");
            list.add("value2");
            list.add("value3");

            assertFalse(CollectionUtils.isEmpty(list));
        }

        { // 空のMapオブジェクト
            final Map<String, String> map = null;

            assertTrue(CollectionUtils.isEmpty(map));
        }

        { // 空のMapオブジェクト
            final Map<String, String> map = new HashMap<>();

            assertTrue(CollectionUtils.isEmpty(map));
        }

        { // Mapオブジェクト
            final Map<String, String> map = new HashMap<>();
            map.put("key1", "value1");
            map.put("key2", "value2");
            map.put("key3", "value3");

            assertFalse(CollectionUtils.isEmpty(map));
        }
    }

    @Test
    public void sizeTest() {
        { // 空のListオブジェクト
            final List<String> list = null;

            assertEquals(0, CollectionUtils.size(list));
        }

        { // 空のListオブジェクト
            final List<String> list = new ArrayList<>();

            assertEquals(0, CollectionUtils.size(list));
        }

        { // Listオブジェクト
            final List<String> list = new LinkedList<>();
            list.add("value1");
            list.add("value2");
            list.add("value3");

            assertEquals(3, CollectionUtils.size(list));
        }

        { // 空のMapオブジェクト
            final Map<String, String> map = null;

            assertEquals(0, CollectionUtils.size(map));
        }

        { // 空のMapオブジェクト
            final Map<String, String> map = new HashMap<>();

            assertEquals(0, CollectionUtils.size(map));
        }

        { // Mapオブジェクト
            final Map<String, String> map = new HashMap<>();
            map.put("key1", "value1");
            map.put("key2", "value2");
            map.put("key3", "value3");

            assertEquals(3, CollectionUtils.size(map));
        }
    }

    @Test
    public void getMapEntryTest() {
        final Map<String, String> map = new LinkedHashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        map.put("key3", "value3");

        { // インデックスが範囲外
            Map.Entry<String, String> mapEntry = CollectionUtils.getMapEntry(map, -1);
            assertNull(mapEntry);
        }

        { // インデックスが範囲外
            Map.Entry<String, String> mapEntry = CollectionUtils.getMapEntry(map, 3);
            assertNull(mapEntry);
        }

        { // インデックスが範囲外
            Map.Entry<String, String> mapEntry = CollectionUtils.getMapEntry(map, 100);
            assertNull(mapEntry);
        }

        { // インデックスが0
            Map.Entry<String, String> mapEntry = CollectionUtils.getMapEntry(map, 0);
            assertNotNull(mapEntry);
            assertEquals(mapEntry.getKey(), "key1");
            assertEquals(mapEntry.getValue(), "value1");
        }

        { // インデックスが1
            Map.Entry<String, String> mapEntry = CollectionUtils.getMapEntry(map, 1);
            assertNotNull(mapEntry);
            assertEquals(mapEntry.getKey(), "key2");
            assertEquals(mapEntry.getValue(), "value2");
        }

        { // インデックスが2
            Map.Entry<String, String> mapEntry = CollectionUtils.getMapEntry(map, 2);
            assertNotNull(mapEntry);
            assertEquals(mapEntry.getKey(), "key3");
            assertEquals(mapEntry.getValue(), "value3");
        }
    }

    @Test
    public void addMapEntryTest() {
        { // 値がObjectオブジェクトの、Mapオブジェクト
            final Map<String, Object> map = new HashMap<>();

            CollectionUtils.addMapEntry(map, "key", 1);
            assertEquals(1, map.get("key"));

            CollectionUtils.addMapEntry(map, "key", 9);
            assertEquals(9, map.get("key"));
        }

        { // 値がObjectオブジェクトの、Mapオブジェクト
            final Map<String, Object> map = new HashMap<>();

            CollectionUtils.addMapEntry(map, "key1", 1);
            CollectionUtils.addMapEntry(map, "key2", 2);
            CollectionUtils.addMapEntry(map, "key3", 3);
            assertEquals(1, map.get("key1"));
            assertEquals(2, map.get("key2"));
            assertEquals(3, map.get("key3"));

            CollectionUtils.addMapEntry(map, "key1", 7);
            CollectionUtils.addMapEntry(map, "key2", 8);
            CollectionUtils.addMapEntry(map, "key3", 9);
            assertEquals(7, map.get("key1"));
            assertEquals(8, map.get("key2"));
            assertEquals(9, map.get("key3"));
        }

        { // 値がIntegerオブジェクトの、Mapオブジェクト
            final Map<String, Integer> map = new HashMap<>();

            CollectionUtils.addMapEntry(map, "key", 1);
            assertEquals(1, map.get("key"));
        }

        { // 値がIntegerオブジェクトの、Mapオブジェクト
            final Map<String, Integer> map = new HashMap<>();

            CollectionUtils.addMapEntry(map, "key1", 1);
            CollectionUtils.addMapEntry(map, "key2", 2);
            CollectionUtils.addMapEntry(map, "key3", 3);
            assertEquals(1, map.get("key1"));
            assertEquals(2, map.get("key2"));
            assertEquals(3, map.get("key3"));
        }

        { // 値がStringオブジェクトの、Mapオブジェクト
            final Map<String, String> map = new HashMap<>();

            CollectionUtils.addMapEntry(map, "key", "value");
            assertEquals("value", map.get("key"));
        }

        { // 値がStringオブジェクトの、Mapオブジェクト
            final Map<String, String> map = new HashMap<>();

            CollectionUtils.addMapEntry(map, "key1", "value1");
            CollectionUtils.addMapEntry(map, "key2", "value2");
            CollectionUtils.addMapEntry(map, "key3", "value3");
            assertEquals("value1", map.get("key1"));
            assertEquals("value2", map.get("key2"));
            assertEquals("value3", map.get("key3"));
        }

        { // 空のListオブジェクト
            final Map<String, List<String>> map = new HashMap<>();
            final List<String> list = new ArrayList<>();

            CollectionUtils.addMapEntry(map, "key", list);
            assertEquals(list, map.get("key"));

            final List<String> actual = map.get("key");
            assertEquals(list.size(), actual.size());
        }

        { // Listオブジェクト
            final Map<String, List<String>> map = new HashMap<>();
            final List<String> list = new LinkedList<>();
            list.add("value1");
            list.add("value2");
            list.add("value3");

            CollectionUtils.addMapEntry(map, "key", list);
            assertEquals(map.get("key"), list);

            final List<String> actual = map.get("key");
            assertEquals(list.size(), actual.size());
            assertEquals("value1", actual.get(0));
            assertEquals("value2", actual.get(1));
            assertEquals("value3", actual.get(2));
        }

        { // ImmutableListオブジェクト
            final Map<String, List<String>> map = new HashMap<>();
            final List<String> list = ImmutableList.of("value1", "value2", "value3");

            CollectionUtils.addMapEntry(map, "key", list);
            assertEquals(list, map.get("key"));

            final List<String> actual = map.get("key");
            assertEquals(list.size(), actual.size());
            assertEquals("value1", actual.get(0));
            assertEquals("value2", actual.get(1));
            assertEquals("value3", actual.get(2));
        }

        { // ImmutableListオブジェクト
            final Map<String, List<String>> map = new HashMap<>();
            map.put("key", ImmutableList.of("value1", "value2", "value3"));
            final List<String> list = ImmutableList.of("value+1", "value+2", "value+3");

            assertThrows(UnsupportedOperationException.class, () -> {
                CollectionUtils.addMapEntry(map, "key", list);
            });
        }

        { // 空のMapオブジェクト
            final Map<String, Map<String, String>> map = new HashMap<>();
            final Map<String, String> valueMap = new HashMap<>();

            CollectionUtils.addMapEntry(map, "key", valueMap);
            assertEquals(valueMap, map.get("key"));

            final Map<String, String> actual = map.get("key");
            assertEquals(valueMap.size(), actual.size());
        }

        { // 値がMapオブジェクトの、Mapオブジェクト
            final Map<String, Map<String, String>> map = new HashMap<>();
            final Map<String, String> valueMap = new HashMap<>();
            valueMap.put("key1", "value1");
            valueMap.put("key2", "value2");
            valueMap.put("key3", "value3");

            CollectionUtils.addMapEntry(map, "key", valueMap);
            assertEquals(valueMap, map.get("key"));

            final Map<String, String> actual = map.get("key");
            assertEquals(valueMap.size(), actual.size());
            assertEquals("value1", actual.get("key1"));
            assertEquals("value2", actual.get("key2"));
            assertEquals("value3", actual.get("key3"));
        }
    }

    @Test
    public void replaceAllMapKeyTest() {
    }

    @Test
    public void removeAllTest() {
        { // 空のMapオブジェクト
            final Map<String, String> map = null;
            final List<String> list = null;

            CollectionUtils.removeAll(map, list);

            assertNull(map);
        }

        { // 空のMapオブジェクト
            final Map<String, String> map = new HashMap<>();
            final List<String> list = null;

            CollectionUtils.removeAll(map, list);

            assertNotNull(map);
            assertEquals(0, map.size());
        }

        { // Mapオブジェクト
            final Map<String, String> map = new HashMap<>();
            map.put("key1", "value1");
            map.put("key2", "value2");
            map.put("key3", "value3");
            final List<String> list = null;

            CollectionUtils.removeAll(map, list);

            assertNotNull(map);
            assertEquals(3, map.size());
        }

        { // Mapオブジェクト
            final Map<Integer, String> map = new HashMap<>();
            map.put(1, "value1");
            map.put(2, "value2");
            map.put(3, "value3");
            final List<Integer> list = null;

            CollectionUtils.removeAll(map, list);

            assertNotNull(map);
            assertEquals(3, map.size());
        }

        { // Mapオブジェクト
            final Map<String, String> map = new HashMap<>();
            map.put("key1", "value1");
            map.put("key2", "value2");
            map.put("key3", "value3");
            final List<String> list = ImmutableList.of("key1");

            CollectionUtils.removeAll(map, list);

            assertNotNull(map);
            assertEquals(2, map.size());
            assertNull(map.get("key1"));
            assertNotNull(map.get("key2"));
            assertNotNull(map.get("key3"));
        }

        { // Mapオブジェクト
            final Map<Integer, String> map = new HashMap<>();
            map.put(1, "value1");
            map.put(2, "value2");
            map.put(3, "value3");
            final List<Integer> list = ImmutableList.of(1);

            CollectionUtils.removeAll(map, list);

            assertNotNull(map);
            assertEquals(2, map.size());
            assertNull(map.get(1));
            assertNotNull(map.get(2));
            assertNotNull(map.get(3));
        }

        { // Mapオブジェクト
            final Map<String, String> map = new HashMap<>();
            map.put("key1", "value1");
            map.put("key2", "value2");
            map.put("key3", "value3");
            final List<String> list = ImmutableList.of("value1");

            CollectionUtils.removeAll(map, list);

            assertNotNull(map);
            assertEquals(3, map.size());
            assertNotNull(map.get("key1"));
            assertNotNull(map.get("key2"));
            assertNotNull(map.get("key3"));
        }

        { // Mapオブジェクト
            final Map<String, String> map = new HashMap<>();
            map.put("key1", "value1");
            map.put("key2", "value2");
            map.put("key3", "value3");
            final List<String> list = ImmutableList.of("key4");

            CollectionUtils.removeAll(map, list);

            assertNotNull(map);
            assertEquals(map.size(), 3);
            assertNotNull(map.get("key1"));
            assertNotNull(map.get("key2"));
            assertNotNull(map.get("key3"));
        }
    }

    @Test
    public void removeDuplicateTest() {
        { // 空のListオブジェクト
            final List<String> list = new ArrayList<>();

            CollectionUtils.removeDuplicate(list);

            assertEquals(list.size(), 0);
        }

        { // Listオブジェクト 重複なし
            final List<String> list = new LinkedList<>();
            list.add("value1");
            list.add("value2");
            list.add("value3");

            CollectionUtils.removeDuplicate(list);

            assertEquals(3, list.size());
            assertEquals("value1", list.get(0));
            assertEquals("value2", list.get(1));
            assertEquals("value3", list.get(2));
        }

        { // Listオブジェクト 重複あり
            final List<String> list = new LinkedList<>();
            list.add("value1");
            list.add("value1");
            list.add("value2");
            list.add("value3");

            CollectionUtils.removeDuplicate(list);

            assertEquals(3, list.size());
            assertEquals("value1", list.get(0));
            assertEquals("value2", list.get(1));
            assertEquals("value3", list.get(2));
        }

        { // Listオブジェクト 重複あり
            final List<String> list = new LinkedList<>();
            list.add("value1");
            list.add("value2");
            list.add("value1");
            list.add("value3");

            CollectionUtils.removeDuplicate(list);

            assertEquals(3, list.size());
            assertEquals("value1", list.get(0));
            assertEquals("value2", list.get(1));
            assertEquals("value3", list.get(2));
        }

        { // Listオブジェクト 重複あり
            final List<String> list = new LinkedList<>();
            list.add("value1");
            list.add("value2");
            list.add("value3");
            list.add("value1");

            CollectionUtils.removeDuplicate(list);

            assertEquals(3, list.size());
            assertEquals("value1", list.get(0));
            assertEquals("value2", list.get(1));
            assertEquals("value3", list.get(2));
        }

        { // Listオブジェクト 重複あり
            final List<String> list = new LinkedList<>();
            list.add("value1");
            list.add("value1");
            list.add("value2");
            list.add("value2");
            list.add("value3");
            list.add("value3");

            CollectionUtils.removeDuplicate(list);

            assertEquals(3, list.size());
            assertEquals("value1", list.get(0));
            assertEquals("value2", list.get(1));
            assertEquals("value3", list.get(2));
        }
    }
}
