package jp.taira.libs.utils;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
        assertEquals(StringUtils.isEmpty(null), true);
        assertEquals(StringUtils.isEmpty(""), true);
        assertEquals(StringUtils.isEmpty("  "), false);

        assertEquals(StringUtils.isEmpty("AAA"), false);
    }

    @Test
    public void isBlankTest() throws Exception {
        assertEquals(StringUtils.isBlank(null), true);
        assertEquals(StringUtils.isBlank(""), true);
        assertEquals(StringUtils.isBlank("  "), true);

        assertEquals(StringUtils.isBlank("AAA"), false);
    }

    @Test
    public void startsWithTest() throws Exception {
        assertEquals(StringUtils.startsWith(null, null), false);
        assertEquals(StringUtils.startsWith(null, ""), false);
        assertThrows(NullPointerException.class, () -> {
            StringUtils.startsWith(" ", null);
        });
        assertEquals(StringUtils.startsWith(" ", ""), true);
        assertEquals(StringUtils.startsWith("", ""), true);

        assertEquals(StringUtils.startsWith("abc", ""), true);
        assertEquals(StringUtils.startsWith("abc", "a"), true);
        assertEquals(StringUtils.startsWith("abc", "A"), false);
        assertEquals(StringUtils.startsWith("abc", "b"), false);
        assertEquals(StringUtils.startsWith("abc", "B"), false);
        assertEquals(StringUtils.startsWith("abc", "c"), false);
        assertEquals(StringUtils.startsWith("abc", "C"), false);
        assertEquals(StringUtils.startsWith("ABC", ""), true);
        assertEquals(StringUtils.startsWith("ABC", "a"), false);
        assertEquals(StringUtils.startsWith("ABC", "A"), true);
        assertEquals(StringUtils.startsWith("ABC", "b"), false);
        assertEquals(StringUtils.startsWith("ABC", "B"), false);
        assertEquals(StringUtils.startsWith("ABC", "c"), false);
        assertEquals(StringUtils.startsWith("ABC", "C"), false);
    }

    @Test
    public void startsWithIgnoreCaseTest() throws Exception {
        assertEquals(StringUtils.startsWithIgnoreCase(null, null), false);
        assertEquals(StringUtils.startsWithIgnoreCase(null, ""), false);
        assertThrows(NullPointerException.class, () -> {
            StringUtils.startsWithIgnoreCase(" ", null);
        });
        assertEquals(StringUtils.startsWithIgnoreCase(" ", ""), true);
        assertEquals(StringUtils.startsWithIgnoreCase("", ""), true);

        assertEquals(StringUtils.startsWithIgnoreCase("abc", ""), true);
        assertEquals(StringUtils.startsWithIgnoreCase("abc", "a"), true);
        assertEquals(StringUtils.startsWithIgnoreCase("abc", "A"), true);
        assertEquals(StringUtils.startsWithIgnoreCase("abc", "b"), false);
        assertEquals(StringUtils.startsWithIgnoreCase("abc", "B"), false);
        assertEquals(StringUtils.startsWithIgnoreCase("abc", "c"), false);
        assertEquals(StringUtils.startsWithIgnoreCase("abc", "C"), false);
        assertEquals(StringUtils.startsWithIgnoreCase("ABC", ""), true);
        assertEquals(StringUtils.startsWithIgnoreCase("ABC", "a"), true);
        assertEquals(StringUtils.startsWithIgnoreCase("ABC", "A"), true);
        assertEquals(StringUtils.startsWithIgnoreCase("ABC", "b"), false);
        assertEquals(StringUtils.startsWithIgnoreCase("ABC", "B"), false);
        assertEquals(StringUtils.startsWithIgnoreCase("ABC", "c"), false);
        assertEquals(StringUtils.startsWithIgnoreCase("ABC", "C"), false);
    }
}
