package jp.taira.libs.utils;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

public class BooleanUtilsTest {

    @Test
    public void constractorTest() {
        assertThrows(IllegalAccessError.class, () -> {
            try {
                Constructor<?> constructor = BooleanUtils.class.getDeclaredConstructor();
                constructor.setAccessible(true);
                constructor.newInstance();
            } catch (InvocationTargetException e) {
                throw e.getCause();
            }
        });
    }

    @Test
    public void isTrueTest() {
        assertFalse(BooleanUtils.isTrue(null));

        assertTrue(BooleanUtils.isTrue(Boolean.TRUE));
        assertTrue(BooleanUtils.isTrue(true));

        assertFalse(BooleanUtils.isTrue(Boolean.FALSE));
        assertFalse(BooleanUtils.isTrue(false));
    }

    @Test
    public void isFalseTest() {
        assertFalse(BooleanUtils.isFalse(null));

        assertFalse(BooleanUtils.isFalse(Boolean.TRUE));
        assertFalse(BooleanUtils.isFalse(true));

        assertTrue(BooleanUtils.isFalse(Boolean.FALSE));
        assertTrue(BooleanUtils.isFalse(false));
    }

    @Test
    public void toBooleanTest_String() {
        assertNull(BooleanUtils.toBoolean(""));
        assertNull(BooleanUtils.toBoolean(" "));
        assertNull(BooleanUtils.toBoolean("　"));
        assertNull(BooleanUtils.toBoolean("123"));
        assertNull(BooleanUtils.toBoolean(" 1 "));
        assertNull(BooleanUtils.toBoolean(" 0 "));
        assertNull(BooleanUtils.toBoolean(" TRUE "));
        assertNull(BooleanUtils.toBoolean(" true "));
        assertNull(BooleanUtils.toBoolean(" FALSE "));
        assertNull(BooleanUtils.toBoolean(" false "));

        assertEquals(Boolean.TRUE, BooleanUtils.toBoolean("1"));
        assertNotEquals(Boolean.TRUE, BooleanUtils.toBoolean("0"));

        assertEquals(Boolean.TRUE, BooleanUtils.toBoolean("TRUE"));
        assertEquals(Boolean.TRUE, BooleanUtils.toBoolean("tRUE"));
        assertEquals(Boolean.TRUE, BooleanUtils.toBoolean("TrUE"));
        assertEquals(Boolean.TRUE, BooleanUtils.toBoolean("TRuE"));
        assertEquals(Boolean.TRUE, BooleanUtils.toBoolean("TRUe"));
        assertEquals(Boolean.TRUE, BooleanUtils.toBoolean("true"));
        assertEquals(Boolean.TRUE, BooleanUtils.toBoolean("True"));
        assertEquals(Boolean.TRUE, BooleanUtils.toBoolean("tRue"));
        assertEquals(Boolean.TRUE, BooleanUtils.toBoolean("trUe"));
        assertEquals(Boolean.TRUE, BooleanUtils.toBoolean("truE"));

        assertNotEquals(Boolean.TRUE, BooleanUtils.toBoolean("FALSE"));
        assertNotEquals(Boolean.TRUE, BooleanUtils.toBoolean("fALSE"));
        assertNotEquals(Boolean.TRUE, BooleanUtils.toBoolean("FaLSE"));
        assertNotEquals(Boolean.TRUE, BooleanUtils.toBoolean("FAlSE"));
        assertNotEquals(Boolean.TRUE, BooleanUtils.toBoolean("FALsE"));
        assertNotEquals(Boolean.TRUE, BooleanUtils.toBoolean("FALSe"));
        assertNotEquals(Boolean.TRUE, BooleanUtils.toBoolean("false"));
        assertNotEquals(Boolean.TRUE, BooleanUtils.toBoolean("False"));
        assertNotEquals(Boolean.TRUE, BooleanUtils.toBoolean("fAlse"));
        assertNotEquals(Boolean.TRUE, BooleanUtils.toBoolean("faLse"));
        assertNotEquals(Boolean.TRUE, BooleanUtils.toBoolean("falSe"));
        assertNotEquals(Boolean.TRUE, BooleanUtils.toBoolean("falsE"));
    }

    @Test
    public void toBooleanTest_Integer() {
        assertTrue(BooleanUtils.toBoolean(-1));
        assertTrue(BooleanUtils.toBoolean(1));
        assertTrue(BooleanUtils.toBoolean(2));

        assertFalse(BooleanUtils.toBoolean(0));
    }

    @Test
    public void toIntegerTest() {
        assertNull(BooleanUtils.toInteger(null));

        assertEquals(1, BooleanUtils.toInteger(Boolean.TRUE));
        assertEquals(1, BooleanUtils.toInteger(true));

        assertEquals(0, BooleanUtils.toInteger(Boolean.FALSE));
        assertEquals(0, BooleanUtils.toInteger(false));
    }
}
