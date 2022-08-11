package jp.taira.libs.utils;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ArrayUtilsTest {

    @Test
    public void constractorTest() {
        assertThrows(IllegalAccessError.class, () -> {
            try {
                Constructor<?> constructor = ArrayUtils.class.getDeclaredConstructor();
                constructor.setAccessible(true);
                constructor.newInstance();
            } catch (InvocationTargetException e) {
                throw e.getCause();
            }
        });
    }

    @Test
    public void isEmptyTest() {
        { // String配列
            final String[] array = null;

            assertTrue(ArrayUtils.isEmpty(array));
        }
        { // String配列
            final String[] array = {};

            assertTrue(ArrayUtils.isEmpty(array));
        }
        { // String配列
            final String[] array = {"value1", "value2", "value3"};

            assertFalse(ArrayUtils.isEmpty(array));
        }

        { // int配列
            final int[] array = null;

            assertTrue(ArrayUtils.isEmpty(array));
        }
        { // int配列
            final int[] array = {};

            assertTrue(ArrayUtils.isEmpty(array));
        }
        { // int配列
            final int[] array = {1, 2, 3};

            assertFalse(ArrayUtils.isEmpty(array));
        }

        { // long配列
            final long[] array = null;

            assertTrue(ArrayUtils.isEmpty(array));
        }
        { // long配列
            final long[] array = {};

            assertTrue(ArrayUtils.isEmpty(array));
        }
        { // long配列
            final long[] array = {1, 2, 3};

            assertFalse(ArrayUtils.isEmpty(array));
        }

        { // byte配列
            final byte[] array = null;

            assertTrue(ArrayUtils.isEmpty(array));
        }
        { // byte配列
            final byte[] array = {};

            assertTrue(ArrayUtils.isEmpty(array));
        }
        { // byte配列
            final byte[] array = {0x01, 0x02, 0x03};

            assertFalse(ArrayUtils.isEmpty(array));
        }

        { // boolean配列
            final boolean[] array = null;

            assertTrue(ArrayUtils.isEmpty(array));
        }
        { // boolean配列
            final boolean[] array = {};

            assertTrue(ArrayUtils.isEmpty(array));
        }
        { // boolean配列
            final boolean[] array = {true, false};

            assertFalse(ArrayUtils.isEmpty(array));
        }
    }

    @Test
    public void sizeTest() {
        { // String配列
            final String[] array = null;

            assertEquals(0, ArrayUtils.size(array));
        }
        { // String配列
            final String[] array = {};

            assertEquals(0, ArrayUtils.size(array));
        }
        { // String配列
            final String[] array = {"value1", "value2", "value3"};

            assertEquals(3, ArrayUtils.size(array));
        }
    }

    @Test
    public void indexOfTest() {
        { // null
            final String[] array = null;

            assertEquals(-1, ArrayUtils.indexOf(array, null));
        }
        { // 空
            final String[] array = {};

            assertEquals(-1, ArrayUtils.indexOf(array, null));
        }
        { // 対象外
            final String[] array = {"value1", "value2", "value3"};

            assertEquals(-1, ArrayUtils.indexOf(array, null));
        }

        { // null
            final String[] array = null;

            assertEquals(-1, ArrayUtils.indexOf(array, ""));
        }
        { // 空
            final String[] array = {};

            assertEquals(-1, ArrayUtils.indexOf(array, ""));
        }
        { // 対象外
            final String[] array = {"value1", "value2", "value3"};

            assertEquals(-1, ArrayUtils.indexOf(array, ""));
        }

        { // null
            final String[] array = null;

            assertEquals(-1, ArrayUtils.indexOf(array, "value"));
        }
        { // 空
            final String[] array = {};

            assertEquals(-1, ArrayUtils.indexOf(array, "value"));
        }
        { // 対象外
            final String[] array = {"value1", "value2", "value3"};

            assertEquals(-1, ArrayUtils.indexOf(array, "value"));
        }
        { // 対象
            final String[] array = {"value1", "value2", "value3"};

            assertEquals(1, ArrayUtils.indexOf(array, "value2"));
        }

        { // null
            final Integer[] array = null;

            assertEquals(-1, ArrayUtils.indexOf(array, 0));
        }
        { // 空
            final Integer[] array = {};

            assertEquals(-1, ArrayUtils.indexOf(array, 0));
        }
        { // 対象外
            final Integer[] array = {1, 2, 3};

            assertEquals(-1, ArrayUtils.indexOf(array, 0));
        }
        { // 対象
            final Integer[] array = {1, 2, 3};

            assertEquals(1, ArrayUtils.indexOf(array, 2));
        }
    }

    @Test
    public void containsTest_Object() {
        { // null
            final String[] array = null;

            assertFalse(ArrayUtils.contains(array, null));
        }
        { // 空
            final String[] array = {};

            assertFalse(ArrayUtils.contains(array, null));
        }
        { // 対象外
            final String[] array = {"value1", "value2", "value3"};

            assertFalse(ArrayUtils.contains(array, null));
        }

        { // null
            final String[] array = null;

            assertFalse(ArrayUtils.contains(array, ""));
        }
        { // 空
            final String[] array = {};

            assertFalse(ArrayUtils.contains(array, ""));
        }
        { // 対象外
            final String[] array = {"value1", "value2", "value3"};

            assertFalse(ArrayUtils.contains(array, ""));
        }

        { // null
            final String[] array = null;

            assertFalse(ArrayUtils.contains(array, "value"));
        }
        { // 空
            final String[] array = {};

            assertFalse(ArrayUtils.contains(array, "value"));
        }
        { // 対象外
            final String[] array = {"value1", "value2", "value3"};

            assertFalse(ArrayUtils.contains(array, "value"));
        }
        { // 対象
            final String[] array = {"value1", "value2", "value3"};

            assertTrue(ArrayUtils.contains(array, "value2"));
        }

        { // null
            final Integer[] array = null;

            assertFalse(ArrayUtils.contains(array, 0));
        }
        { // 空
            final Integer[] array = {};

            assertFalse(ArrayUtils.contains(array, 0));
        }
        { // 対象外
            final Integer[] array = {1, 2, 3};

            assertFalse(ArrayUtils.contains(array, 0));
        }
        { // 対象
            final Integer[] array = {1, 2, 3};

            assertTrue(ArrayUtils.contains(array, 2));
        }
    }

    @Test
    public void containsTest_int() {
        { // null
            final int[] array = null;

            assertFalse(ArrayUtils.contains(array, 0));
        }
        { // 空
            final int[] array = {};

            assertFalse(ArrayUtils.contains(array, 0));
        }
        { // 対象外
            final int[] array = {1, 2, 3};

            assertFalse(ArrayUtils.contains(array, 0));
        }
        { // 対象
            final int[] array = {1, 2, 3};

            assertTrue(ArrayUtils.contains(array, 2));
        }
    }

    @Test
    public void containsTest_long() {
        { // null
            final long[] array = null;

            assertFalse(ArrayUtils.contains(array, 0L));
        }
        { // 空
            final long[] array = {};

            assertFalse(ArrayUtils.contains(array, 0L));
        }
        { // 対象外
            final long[] array = {1L, 2L, 3L};

            assertFalse(ArrayUtils.contains(array, 0L));
        }
        { // 対象
            final long[] array = {1L, 2L, 3L};

            assertTrue(ArrayUtils.contains(array, 2L));
        }
    }

    @Test
    public void containsTest_char() {
        { // null
            final char[] array = null;

            assertFalse(ArrayUtils.contains(array, '-'));
        }
        { // 空
            final char[] array = {};

            assertFalse(ArrayUtils.contains(array, '-'));
        }
        { // 対象外
            final char[] array = {'a', 'b', 'c'};

            assertFalse(ArrayUtils.contains(array, '-'));
        }
        { // 対象
            final char[] array = {'a', 'b', 'c'};

            assertTrue(ArrayUtils.contains(array, 'a'));
        }
    }

    @Test
    public void containsAllTest() {
        { // null
            final String[] array = null;

            assertFalse(ArrayUtils.containsAll(array, null));
        }
        { // 空
            final String[] array = {};

            assertFalse(ArrayUtils.containsAll(array, null));
        }
        { // 対象外
            final String[] array = {"value1", "value2", "value3"};

            assertFalse(ArrayUtils.containsAll(array, null));
        }

        { // null
            final String[] array = null;

            assertFalse(ArrayUtils.containsAll(array, new String[] {}));
        }
        { // 空
            final String[] array = {};

            assertTrue(ArrayUtils.containsAll(array, new String[] {}));
        }
        { // 空
            final String[] array = {};

            assertFalse(ArrayUtils.containsAll(array, new String[] {""}));
        }
        { // 対象外
            final String[] array = {"value1", "value2", "value3"};

            assertFalse(ArrayUtils.containsAll(array, new String[] {}));
        }

        { // null
            final String[] array = null;
            final String[] check = {"value1", "value2", "value3"};

            assertFalse(ArrayUtils.containsAll(array, check));
        }
        { // 空
            final String[] array = {};
            final String[] check = {"value1", "value2", "value3"};

            assertFalse(ArrayUtils.containsAll(array, check));
        }
        { // 対象
            final String[] array = {"value1", "value2", "value3"};
            final String[] check = {"value1", "value2"};

            assertTrue(ArrayUtils.containsAll(array, check));
        }
        { // 対象
            final String[] array = {"value1", "value2", "value3"};
            final String[] check = {"value1", "value2", "value3"};

            assertTrue(ArrayUtils.containsAll(array, check));
        }
        { // 対象外
            final String[] array = {"value1", "value2", "value3"};
            final String[] check = {"value1", "value2", "value3", "value4"};

            assertFalse(ArrayUtils.containsAll(array, check));
        }
        { // 対象外
            final String[] array = {"value1", "value2", "value3"};
            final String[] check = {"value4", "value5", "value6"};

            assertFalse(ArrayUtils.containsAll(array, check));
        }

        { // null
            final Integer[] array = null;
            final Integer[] check = {1, 2, 3};

            assertFalse(ArrayUtils.containsAll(array, check));
        }
        { // 空
            final Integer[] array = {};
            final Integer[] check = {1, 2, 3};

            assertFalse(ArrayUtils.containsAll(array, check));
        }
        { // 対象
            final Integer[] array = {1, 2, 3};
            final Integer[] check = {1, 2};

            assertTrue(ArrayUtils.containsAll(array, check));
        }
        { // 対象
            final Integer[] array = {1, 2, 3};
            final Integer[] check = {1, 2, 3};

            assertTrue(ArrayUtils.containsAll(array, check));
        }
        { // 対象外
            final Integer[] array = {1, 2, 3};
            final Integer[] check = {1, 2, 3, 4};

            assertFalse(ArrayUtils.containsAll(array, check));
        }
        { // 対象外
            final Integer[] array = {1, 2, 3};
            final Integer[] check = {4, 5, 6};

            assertFalse(ArrayUtils.containsAll(array, check));
        }
    }

    @Test
    public void containsAnyTest() {
        { // null
            final String[] array = null;

            assertFalse(ArrayUtils.containsAny(array, null));
        }
        { // 空
            final String[] array = {};

            assertFalse(ArrayUtils.containsAny(array, null));
        }
        { // 対象外
            final String[] array = {"value1", "value2", "value3"};

            assertFalse(ArrayUtils.containsAny(array, null));
        }

        { // null
            final String[] array = null;

            assertFalse(ArrayUtils.containsAny(array, new String[] {}));
        }
        { // 空
            final String[] array = {};

            assertTrue(ArrayUtils.containsAny(array, new String[] {}));
        }
        { // 空
            final String[] array = {};

            assertFalse(ArrayUtils.containsAny(array, new String[] {""}));
        }
        { // 対象外
            final String[] array = {"value1", "value2", "value3"};

            assertFalse(ArrayUtils.containsAny(array, new String[] {}));
        }

        { // null
            final String[] array = null;
            final String[] check = {"value1", "value2", "value3"};

            assertFalse(ArrayUtils.containsAny(array, check));
        }
        { // 空
            final String[] array = {};
            final String[] check = {"value1", "value2", "value3"};

            assertFalse(ArrayUtils.containsAny(array, check));
        }
        { // 対象
            final String[] array = {"value1", "value2", "value3"};
            final String[] check = {"value1", "value2"};

            assertTrue(ArrayUtils.containsAny(array, check));
        }
        { // 対象
            final String[] array = {"value1", "value2", "value3"};
            final String[] check = {"value1", "value2", "value3"};

            assertTrue(ArrayUtils.containsAny(array, check));
        }
        { // 対象外
            final String[] array = {"value1", "value2", "value3"};
            final String[] check = {"value1", "value2", "value3", "value4"};

            assertTrue(ArrayUtils.containsAny(array, check));
        }
        { // 対象外
            final String[] array = {"value1", "value2", "value3"};
            final String[] check = {"value4", "value5", "value6"};

            assertFalse(ArrayUtils.containsAny(array, check));
        }

        { // null
            final Integer[] array = null;
            final Integer[] check = {1, 2, 3};

            assertFalse(ArrayUtils.containsAny(array, check));
        }
        { // 空
            final Integer[] array = {};
            final Integer[] check = {1, 2, 3};

            assertFalse(ArrayUtils.containsAny(array, check));
        }
        { // 対象
            final Integer[] array = {1, 2, 3};
            final Integer[] check = {1, 2};

            assertTrue(ArrayUtils.containsAny(array, check));
        }
        { // 対象
            final Integer[] array = {1, 2, 3};
            final Integer[] check = {1, 2, 3};

            assertTrue(ArrayUtils.containsAny(array, check));
        }
        { // 対象外
            final Integer[] array = {1, 2, 3};
            final Integer[] check = {1, 2, 3, 4};

            assertTrue(ArrayUtils.containsAny(array, check));
        }
        { // 対象外
            final Integer[] array = {1, 2, 3};
            final Integer[] check = {4, 5, 6};

            assertFalse(ArrayUtils.containsAny(array, check));
        }
    }

    @Test
    public void sumTest() {
        { // null
            final int[] array = null;

            assertEquals(0, ArrayUtils.sum(array));
        }
        { // 空
            final int[] array = {};

            assertEquals(0, ArrayUtils.sum(array));
        }
        { // 対象
            final int[] array = {0};

            assertEquals(0, ArrayUtils.sum(array));
        }
        { // 対象
            final int[] array = {1, 2, 3};

            assertEquals(6, ArrayUtils.sum(array));
        }
        { // 対象
            final int[] array = {-1, -2, -3};

            assertEquals(-6, ArrayUtils.sum(array));
        }

        { // null
            final long[] array = null;

            assertEquals(0L, ArrayUtils.sum(array));
        }
        { // 空
            final long[] array = {};

            assertEquals(0L, ArrayUtils.sum(array));
        }
        { // 対象
            final long[] array = {0L};

            assertEquals(0L, ArrayUtils.sum(array));
        }
        { // 対象
            final long[] array = {1L, 2L, 3L};

            assertEquals(6L, ArrayUtils.sum(array));
        }
        { // 対象
            final long[] array = {-1L, -2L, -3L};

            assertEquals(-6L, ArrayUtils.sum(array));
        }
    }

    @Test
    public void convertTest_ListInteger() {
        { // null
            final List<Integer> list = null;

            final int[] array = ArrayUtils.convert(list);
            assertNull(array);
        }
        // null
        assertThrows(NullPointerException.class, () -> {
            final List<Integer> list = Arrays.asList(1, null, 3);

            ArrayUtils.convert(list);
        });
        { // 空
            final List<Integer> list = Collections.emptyList();

            final int[] array = ArrayUtils.convert(list);
            assertNotNull(array);
            assertEquals(0, array.length);
        }
        { // 対象
            final List<Integer> list = Arrays.asList(1, 2, 3);

            final int[] array = ArrayUtils.convert(list);
            assertNotNull(array);
            assertEquals(3, array.length);
            assertEquals(1, array[0]);
            assertEquals(2, array[1]);
            assertEquals(3, array[2]);
        }
        { // 対象
            final List<Integer> list = Arrays.asList(-1, -2, -3);

            final int[] array = ArrayUtils.convert(list);
            assertNotNull(array);
            assertEquals(3, array.length);
            assertEquals(-1, array[0]);
            assertEquals(-2, array[1]);
            assertEquals(-3, array[2]);
        }
        { // 対象
            final List<Integer> list = Arrays.asList(Integer.MIN_VALUE, Integer.MAX_VALUE);

            final int[] array = ArrayUtils.convert(list);
            assertNotNull(array);
            assertEquals(2, array.length);
            assertEquals(Integer.MIN_VALUE, array[0]);
            assertEquals(Integer.MAX_VALUE, array[1]);
        }
        { // 対象
            final List<Integer> list = Arrays.asList(Integer.MIN_VALUE - 1, Integer.MAX_VALUE + 1);

            final int[] array = ArrayUtils.convert(list);
            assertNotNull(array);
            assertEquals(2, array.length);
            assertEquals(Integer.MIN_VALUE - 1, array[0]);
            assertEquals(Integer.MAX_VALUE + 1, array[1]);
        }
    }

    @Test
    public void convertTest_intArray() {
        { // null
            final int[] intArray = null;

            final long[] array = ArrayUtils.convert(intArray);
            assertNull(array);
        }
        { // 空
            final int[] intArray = {};

            final long[] array = ArrayUtils.convert(intArray);
            assertNotNull(array);
            assertEquals(0, array.length);
        }
        { // 対象
            final int[] intArray = {1, 2, 3};

            final long[] array = ArrayUtils.convert(intArray);
            assertNotNull(array);
            assertEquals(3, array.length);
            assertEquals(1L, array[0]);
            assertEquals(2L, array[1]);
            assertEquals(3L, array[2]);
        }
        { // 対象
            final int[] intArray = {-1, -2, -3};

            final long[] array = ArrayUtils.convert(intArray);
            assertNotNull(array);
            assertEquals(3, array.length);
            assertEquals(-1L, array[0]);
            assertEquals(-2L, array[1]);
            assertEquals(-3L, array[2]);
        }
        { // 対象
            final int[] intArray = {Integer.MIN_VALUE, Integer.MAX_VALUE};

            final long[] array = ArrayUtils.convert(intArray);
            assertNotNull(array);
            assertEquals(2, array.length);
            assertEquals(Integer.MIN_VALUE, array[0]);
            assertEquals(Integer.MAX_VALUE, array[1]);
        }
        { // 対象
            final int[] intArray = {Integer.MIN_VALUE - 1, Integer.MAX_VALUE + 1};

            final long[] array = ArrayUtils.convert(intArray);
            assertNotNull(array);
            assertEquals(2, array.length);
            assertEquals(Integer.MIN_VALUE - 1, array[0]);
            assertEquals(Integer.MAX_VALUE + 1, array[1]);
        }
    }

    @Test
    public void appendTest_allowDuplicates() {
        { // null
            final Integer[] intArray = null;

            final Integer[] array = ArrayUtils.append(Integer.class, intArray, null, true);
            assertNotNull(array);
            assertEquals(1, array.length);
            assertNull(array[0]);
        }
        { // null
            final Integer[] intArray = null;

            final Integer[] array = ArrayUtils.append(Integer.class, intArray, 1, true);
            assertNotNull(array);
            assertEquals(1, array.length);
            assertEquals(array[0], 1);
        }
        { // null
            final Integer[] intArray = {1, 2, 3};

            final Integer[] array = ArrayUtils.append(Integer.class, intArray, null, true);
            assertNotNull(array);
            assertEquals(4, array.length);
            assertEquals(1, array[0]);
            assertEquals(2, array[1]);
            assertEquals(3, array[2]);
            assertNull(array[3]);
        }
        { // 空
            final Integer[] intArray = {};

            final Integer[] array = ArrayUtils.append(Integer.class, intArray, null, true);
            assertNotNull(array);
            assertEquals(1, array.length);
            assertNull(array[0]);
        }
        { // 空
            final Integer[] intArray = {};

            final Integer[] array = ArrayUtils.append(Integer.class, intArray, 1, true);
            assertNotNull(array);
            assertEquals(1, array.length);
            assertEquals(1, array[0]);
        }
        { // append
            final Integer[] intArray = {1, 2, 3};

            final Integer[] array = ArrayUtils.append(Integer.class, intArray, 4, true);
            assertNotNull(array);
            assertEquals(4, array.length);
            assertEquals(1, array[0]);
            assertEquals(2, array[1]);
            assertEquals(3, array[2]);
            assertEquals(4, array[3]);
        }
        { // append
            final Integer[] intArray = {1, 2, 3};

            final Integer[] array = ArrayUtils.append(Integer.class, intArray, 3, true);
            assertNotNull(array);
            assertEquals(4, array.length);
            assertEquals(1, array[0]);
            assertEquals(2, array[1]);
            assertEquals(3, array[2]);
            assertEquals(3, array[3]);
        }
    }

    @Test
    public void appendTest() {
        { // null
            final Integer[] intArray = null;

            final Integer[] array = ArrayUtils.append(Integer.class, intArray, null);
            assertNotNull(array);
            assertEquals(1, array.length);
            assertNull(array[0]);
        }
        { // null
            final Integer[] intArray = null;

            final Integer[] array = ArrayUtils.append(Integer.class, intArray, 1);
            assertNotNull(array);
            assertEquals(1, array.length);
            assertEquals(1, array[0]);
        }
        { // null
            final Integer[] intArray = {1, 2, 3};

            final Integer[] array = ArrayUtils.append(Integer.class, intArray, null);
            assertNotNull(array);
            assertEquals(4, array.length);
            assertEquals(1, array[0]);
            assertEquals(2, array[1]);
            assertEquals(3, array[2]);
            assertNull(array[3]);
        }
        { // 空
            final Integer[] intArray = {};

            final Integer[] array = ArrayUtils.append(Integer.class, intArray, null);
            assertNotNull(array);
            assertEquals(1, array.length);
            assertNull(array[0]);
        }
        { // 空
            final Integer[] intArray = {};

            final Integer[] array = ArrayUtils.append(Integer.class, intArray, 1);
            assertNotNull(array);
            assertEquals(1, array.length);
            assertEquals(1, array[0]);
        }
        { // append
            final Integer[] intArray = {1, 2, 3};

            final Integer[] array = ArrayUtils.append(Integer.class, intArray, 4);
            assertNotNull(array);
            assertEquals(4, array.length);
            assertEquals(1, array[0]);
            assertEquals(2, array[1]);
            assertEquals(3, array[2]);
            assertEquals(4, array[3]);
        }
        { // append
            final Integer[] intArray = {1, 2, 3};

            final Integer[] array = ArrayUtils.append(Integer.class, intArray, 3);
            assertNotNull(array);
            assertEquals(3, array.length);
            assertEquals(1, array[0]);
            assertEquals(2, array[1]);
            assertEquals(3, array[2]);
        }
    }
}
