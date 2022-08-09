package jp.taira.libs.utils;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

public class StringUtilsTest {

    @Test
    public void constractorTest() {
        assertThrows(IllegalAccessError.class, () -> {
            try {
                Constructor<?> constructor = StringUtils.class.getDeclaredConstructor();
                constructor.setAccessible(true);
                constructor.newInstance();
            } catch (InvocationTargetException e) {
                throw e.getCause();
            }
        });
    }

    @Test
    public void isEmptyTest() throws Exception {
        assertTrue(StringUtils.isEmpty(null));
        assertTrue(StringUtils.isEmpty(""));
        assertFalse(StringUtils.isEmpty("  "));

        assertFalse(StringUtils.isEmpty("AAA"));
    }

    @Test
    public void isBlankTest() throws Exception {
        assertTrue(StringUtils.isBlank(null));
        assertTrue(StringUtils.isBlank(""));
        assertTrue(StringUtils.isBlank("  "));

        assertFalse(StringUtils.isBlank("AAA"));
    }

    @Test
    public void startsWithTest() throws Exception {
        assertFalse(StringUtils.startsWith(null, null));
        assertFalse(StringUtils.startsWith(null, ""));
        assertThrows(NullPointerException.class, () -> {
            StringUtils.startsWith(" ", null);
        });
        assertTrue(StringUtils.startsWith(" ", ""));
        assertTrue(StringUtils.startsWith("", ""));

        assertTrue(StringUtils.startsWith("abc", ""));
        assertTrue(StringUtils.startsWith("abc", "a"));
        assertFalse(StringUtils.startsWith("abc", "A"));
        assertFalse(StringUtils.startsWith("abc", "b"));
        assertFalse(StringUtils.startsWith("abc", "B"));
        assertFalse(StringUtils.startsWith("abc", "c"));
        assertFalse(StringUtils.startsWith("abc", "C"));
        assertTrue(StringUtils.startsWith("ABC", ""));
        assertFalse(StringUtils.startsWith("ABC", "a"));
        assertTrue(StringUtils.startsWith("ABC", "A"));
        assertFalse(StringUtils.startsWith("ABC", "b"));
        assertFalse(StringUtils.startsWith("ABC", "B"));
        assertFalse(StringUtils.startsWith("ABC", "c"));
        assertFalse(StringUtils.startsWith("ABC", "C"));
    }

    @Test
    public void startsWithIgnoreCaseTest() throws Exception {
        assertFalse(StringUtils.startsWithIgnoreCase(null, null));
        assertFalse(StringUtils.startsWithIgnoreCase(null, ""));
        assertThrows(NullPointerException.class, () -> {
            StringUtils.startsWithIgnoreCase(" ", null);
        });
        assertTrue(StringUtils.startsWithIgnoreCase(" ", ""));
        assertTrue(StringUtils.startsWithIgnoreCase("", ""));

        assertTrue(StringUtils.startsWithIgnoreCase("abc", ""));
        assertTrue(StringUtils.startsWithIgnoreCase("abc", "a"));
        assertTrue(StringUtils.startsWithIgnoreCase("abc", "A"));
        assertFalse(StringUtils.startsWithIgnoreCase("abc", "b"));
        assertFalse(StringUtils.startsWithIgnoreCase("abc", "B"));
        assertFalse(StringUtils.startsWithIgnoreCase("abc", "c"));
        assertFalse(StringUtils.startsWithIgnoreCase("abc", "C"));
        assertTrue(StringUtils.startsWithIgnoreCase("ABC", ""));
        assertTrue(StringUtils.startsWithIgnoreCase("ABC", "a"));
        assertTrue(StringUtils.startsWithIgnoreCase("ABC", "A"));
        assertFalse(StringUtils.startsWithIgnoreCase("ABC", "b"));
        assertFalse(StringUtils.startsWithIgnoreCase("ABC", "B"));
        assertFalse(StringUtils.startsWithIgnoreCase("ABC", "c"));
        assertFalse(StringUtils.startsWithIgnoreCase("ABC", "C"));
    }

    @Test
    public void endsWithTest() throws Exception {
        assertFalse(StringUtils.endsWith(null, null));
        assertFalse(StringUtils.endsWith(null, ""));
        assertThrows(NullPointerException.class, () -> {
            StringUtils.endsWith(" ", null);
        });

        assertTrue(StringUtils.endsWith(" ", ""));
        assertTrue(StringUtils.endsWith("", ""));

        assertTrue(StringUtils.endsWith("abc", ""));
        assertFalse(StringUtils.endsWith("abc", "a"));
        assertFalse(StringUtils.endsWith("abc", "A"));
        assertFalse(StringUtils.endsWith("abc", "b"));
        assertFalse(StringUtils.endsWith("abc", "B"));
        assertTrue(StringUtils.endsWith("abc", "c"));
        assertFalse(StringUtils.endsWith("abc", "C"));
        assertTrue(StringUtils.endsWith("ABC", ""));
        assertFalse(StringUtils.endsWith("ABC", "a"));
        assertFalse(StringUtils.endsWith("ABC", "A"));
        assertFalse(StringUtils.endsWith("ABC", "b"));
        assertFalse(StringUtils.endsWith("ABC", "B"));
        assertFalse(StringUtils.endsWith("ABC", "c"));
        assertTrue(StringUtils.endsWith("ABC", "C"));
    }
}
