package jp.taira.libs.utils;

import lombok.Data;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ObjectUtilsTest {

    protected Path tempFile = null;

    @BeforeEach
    public void beforeEach() throws IOException {
        this.tempFile = Files.createTempFile(null, null);
    }

    @AfterEach
    public void afterEach() throws IOException {
        Files.deleteIfExists(this.tempFile);
    }

    @Test
    public void constractorTest() {
        assertThrows(IllegalAccessError.class, () -> {
            try {
                Constructor<?> constructor = ObjectUtils.class.getDeclaredConstructor();
                constructor.setAccessible(true);
                constructor.newInstance();
            } catch (InvocationTargetException e) {
                throw e.getCause();
            }
        });
    }

    @Test
    public void toStringTest() {
        { // null
            final String target = null;
            String actual = ObjectUtils.toString(target);

            assertNull(actual);
        }

        { // String
            final String target = "";
            String actual = ObjectUtils.toString(target);

            assertNotNull(actual);
            assertEquals(actual, target.toString());
        }

        { // Integer
            final Integer target = 0;
            String actual = ObjectUtils.toString(target);

            assertNotNull(actual);
            assertEquals(actual, target.toString());
        }

        { // List
            final List<String> target = Arrays.asList("", "");
            String actual = ObjectUtils.toString(target);

            assertNotNull(actual);
            assertEquals(actual, target.toString());
        }
    }

    @Test
    public void readAllBytesTest() {
        final List<String> list = new ArrayList<>();
        list.add("あいうえお");
        try {
            Files.write(this.tempFile, list, StringUtils.UTF_8, StandardOpenOption.WRITE);
            final byte[] bytes = ObjectUtils.readAllBytes(Files.newInputStream(this.tempFile));
            assertNotEquals(bytes.length, 0);
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    public void deepCopyTest() {
        TestObjectA objectA = new TestObjectA();
        objectA.setString("test1");
        objectA.setInteger(1);
        objectA.setDecimal(2L);
        objectA.setStringList(Arrays.asList("item1", "item2", "item3"));
        objectA.setIntegerList(Arrays.asList(11, 12, 13));
        objectA.setDecimalList(Arrays.asList(21L, 22L, 23L));

        { /* null */
            TestObjectA actual = new TestObjectA();

            try {
                TestObjectA objectNull = null;
                actual = ObjectUtils.deepCopy(objectNull);
            } catch (Exception e) {
                fail();
            }

            assertNull(actual);
        }

        { /* コピー先を空 */
            TestObjectA actual = new TestObjectA();

            try {
                actual = ObjectUtils.deepCopy(objectA);
            } catch (Exception e) {
                fail();
            }

            assertNotNull(objectA);
            assertNotNull(objectA.getString());
            assertEquals(objectA.getString(), "test1");
            assertNotNull(objectA.getInteger());
            assertEquals(objectA.getInteger(), Integer.valueOf(1));
            assertNotNull(objectA.getDecimal());
            assertEquals(objectA.getDecimal(), Long.valueOf(2));
            assertNotNull(objectA.getStringList());
            assertEquals(objectA.getStringList(), Arrays.asList("item1", "item2", "item3"));
            assertNotNull(objectA.getIntegerList());
            assertEquals(objectA.getIntegerList(), Arrays.asList(11, 12, 13));
            assertNotNull(objectA.getDecimalList());
            assertEquals(objectA.getDecimalList(), Arrays.asList(21L, 22L, 23L));

            assertNotNull(actual);
            assertNotNull(actual.getString());
            assertEquals(actual.getString(), "test1");
            assertNotNull(actual.getInteger());
            assertEquals(actual.getInteger(), Integer.valueOf(1));
            assertNotNull(actual.getDecimal());
            assertEquals(actual.getDecimal(), Long.valueOf(2));
            assertNotNull(actual.getStringList());
            assertEquals(actual.getStringList(), Arrays.asList("item1", "item2", "item3"));
            assertNotNull(actual.getIntegerList());
            assertEquals(actual.getIntegerList(), Arrays.asList(11, 12, 13));
            assertNotNull(actual.getDecimalList());
            assertEquals(actual.getDecimalList(), Arrays.asList(21L, 22L, 23L));
        }

        { /* ディープコピー後に更新 */
            TestObjectA actual = new TestObjectA();

            try {
                actual = ObjectUtils.deepCopy(objectA);
            } catch (Exception e) {
                fail();
            }

            actual.setString("test-1");
            actual.setInteger(-1);
            actual.setDecimal(Long.valueOf(-2));
            actual.setStringList(Arrays.asList("item-1", "item-2", "item-3"));
            actual.setIntegerList(Arrays.asList(-11, -12, -13));
            actual.setDecimalList(Arrays.asList(Long.valueOf(-21), Long.valueOf(-22), Long.valueOf(-23)));

            assertNotNull(objectA);
            assertNotNull(objectA.getString());
            assertEquals(objectA.getString(), "test1");
            assertNotNull(objectA.getInteger());
            assertEquals(objectA.getInteger(), Integer.valueOf(1));
            assertNotNull(objectA.getDecimal());
            assertEquals(objectA.getDecimal(), Long.valueOf(2));
            assertNotNull(objectA.getStringList());
            assertEquals(objectA.getStringList(), Arrays.asList("item1", "item2", "item3"));
            assertNotNull(objectA.getIntegerList());
            assertEquals(objectA.getIntegerList(), Arrays.asList(11, 12, 13));
            assertNotNull(objectA.getDecimalList());
            assertEquals(objectA.getDecimalList(), Arrays.asList(Long.valueOf(21), Long.valueOf(22), Long.valueOf(23)));

            assertNotNull(actual);
            assertNotNull(actual.getString());
            assertEquals(actual.getString(), "test-1");
            assertNotNull(actual.getInteger());
            assertEquals(actual.getInteger(), Integer.valueOf(-1));
            assertNotNull(actual.getDecimal());
            assertEquals(actual.getDecimal(), Long.valueOf(-2));
            assertNotNull(actual.getStringList());
            assertEquals(actual.getStringList(), Arrays.asList("item-1", "item-2", "item-3"));
            assertNotNull(actual.getIntegerList());
            assertEquals(actual.getIntegerList(), Arrays.asList(-11, -12, -13));
            assertNotNull(actual.getDecimalList());
            assertEquals(actual.getDecimalList(), Arrays.asList(Long.valueOf(-21), Long.valueOf(-22), Long.valueOf(-23)));
        }

    }

    @Test
    public void deepCopyTest_gson() {
        TestObjectA objectA = new TestObjectA();
        objectA.setString("test1");
        objectA.setInteger(1);
        objectA.setDecimal(2L);
        objectA.setStringList(Arrays.asList("item1", "item2", "item3"));
        objectA.setIntegerList(Arrays.asList(11, 12, 13));
        objectA.setDecimalList(Arrays.asList(21L, 22L, 23L));

        { /* null */
            TestObjectA actual = new TestObjectA();

            try {
                TestObjectA objectNull = null;
                actual = ObjectUtils.deepCopy(objectNull);
            } catch (Exception e) {
                fail();
            }

            assertNull(actual);
        }

        { /* コピー先を空 */
            TestObjectA actual = new TestObjectA();

            try {
                actual = ObjectUtils.map(objectA, TestObjectA.class);
            } catch (Exception e) {
                fail();
            }

            assertNotNull(objectA);
            assertNotNull(objectA.getString());
            assertEquals(objectA.getString(), "test1");
            assertNotNull(objectA.getInteger());
            assertEquals(objectA.getInteger(), Integer.valueOf(1));
            assertNotNull(objectA.getDecimal());
            assertEquals(objectA.getDecimal(), Long.valueOf(2));
            assertNotNull(objectA.getStringList());
            assertEquals(objectA.getStringList(), Arrays.asList("item1", "item2", "item3"));
            assertNotNull(objectA.getIntegerList());
            assertEquals(objectA.getIntegerList(), Arrays.asList(11, 12, 13));
            assertNotNull(objectA.getDecimalList());
            assertEquals(objectA.getDecimalList(), Arrays.asList(21L, 22L, 23L));

            assertNotNull(actual);
            assertNotNull(actual.getString());
            assertEquals(actual.getString(), "test1");
            assertNotNull(actual.getInteger());
            assertEquals(actual.getInteger(), Integer.valueOf(1));
            assertNotNull(actual.getDecimal());
            assertEquals(actual.getDecimal(), Long.valueOf(2));
            assertNotNull(actual.getStringList());
            assertEquals(actual.getStringList(), Arrays.asList("item1", "item2", "item3"));
            assertNotNull(actual.getIntegerList());
            assertEquals(actual.getIntegerList(), Arrays.asList(11, 12, 13));
            assertNotNull(actual.getDecimalList());
            assertEquals(actual.getDecimalList(), Arrays.asList(21L, 22L, 23L));
        }

        { /* ディープコピー後に更新 */
            TestObjectA actual = new TestObjectA();

            try {
                actual = ObjectUtils.map(objectA, TestObjectA.class);
            } catch (Exception e) {
                fail();
            }

            actual.setString("test-1");
            actual.setInteger(-1);
            actual.setDecimal(Long.valueOf(-2));
            actual.setStringList(Arrays.asList("item-1", "item-2", "item-3"));
            actual.setIntegerList(Arrays.asList(-11, -12, -13));
            actual.setDecimalList(Arrays.asList(Long.valueOf(-21), Long.valueOf(-22), Long.valueOf(-23)));

            assertNotNull(objectA);
            assertNotNull(objectA.getString());
            assertEquals(objectA.getString(), "test1");
            assertNotNull(objectA.getInteger());
            assertEquals(objectA.getInteger(), Integer.valueOf(1));
            assertNotNull(objectA.getDecimal());
            assertEquals(objectA.getDecimal(), Long.valueOf(2));
            assertNotNull(objectA.getStringList());
            assertEquals(objectA.getStringList(), Arrays.asList("item1", "item2", "item3"));
            assertNotNull(objectA.getIntegerList());
            assertEquals(objectA.getIntegerList(), Arrays.asList(11, 12, 13));
            assertNotNull(objectA.getDecimalList());
            assertEquals(objectA.getDecimalList(), Arrays.asList(21L, 22L, 23L));

            assertNotNull(actual);
            assertNotNull(actual.getString());
            assertEquals(actual.getString(), "test-1");
            assertNotNull(actual.getInteger());
            assertEquals(actual.getInteger(), Integer.valueOf(-1));
            assertNotNull(actual.getDecimal());
            assertEquals(actual.getDecimal(), Long.valueOf(-2));
            assertNotNull(actual.getStringList());
            assertEquals(actual.getStringList(), Arrays.asList("item-1", "item-2", "item-3"));
            assertNotNull(actual.getIntegerList());
            assertEquals(actual.getIntegerList(), Arrays.asList(-11, -12, -13));
            assertNotNull(actual.getDecimalList());
            assertEquals(actual.getDecimalList(), Arrays.asList(Long.valueOf(-21), Long.valueOf(-22), Long.valueOf(-23)));
        }

    }

    @SuppressWarnings("serial")
    @Data
    private static class TestObjectA implements Serializable {
        private String string;
        private Integer integer;
        private Long decimal;
        private List<String> stringList;
        private List<Integer> integerList;
        private List<Long> decimalList;
    }

    @SuppressWarnings("serial")
    @Data
    private static class TestObjectB implements Serializable {
        private String stringB;
        private Integer integerB;
        private Long decimalB;
        private List<String> stringBList;
        private List<Integer> integerBList;
        private List<Long> decimalBList;
    }
}
