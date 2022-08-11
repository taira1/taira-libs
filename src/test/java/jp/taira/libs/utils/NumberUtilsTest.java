package jp.taira.libs.utils;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

public class NumberUtilsTest {

    @Test
    public void constractorTest() {
        assertThrows(IllegalAccessError.class, () -> {
            try {
                Constructor<?> constructor = NumberUtils.class.getDeclaredConstructor();
                constructor.setAccessible(true);
                constructor.newInstance();
            } catch (InvocationTargetException e) {
                throw e.getCause();
            }
        });
    }

    @Test
    public void isNumberTest() {
        assertFalse(NumberUtils.isNumber(null));
        assertFalse(NumberUtils.isNumber(""));
        assertFalse(NumberUtils.isNumber("  "));

        assertFalse(NumberUtils.isNumber("ABCDEFGHIJKLMNOPQRSTUVWXYZ"));
        assertFalse(NumberUtils.isNumber("AAA"));
        assertFalse(NumberUtils.isNumber("abcdefghijklmnopqrstuvwxyz"));
        assertFalse(NumberUtils.isNumber("aaa"));
        assertFalse(NumberUtils.isNumber("   aaa"));
        assertFalse(NumberUtils.isNumber("aaa   "));

        assertFalse(NumberUtils.isNumber("012 aaa"));
        assertFalse(NumberUtils.isNumber("aaa 012"));
        assertFalse(NumberUtils.isNumber("012AAA"));
        assertFalse(NumberUtils.isNumber("AAA012"));
        assertFalse(NumberUtils.isNumber("012 AAA"));
        assertFalse(NumberUtils.isNumber("AAA 012"));
        assertFalse(NumberUtils.isNumber("1.23,"));
        assertFalse(NumberUtils.isNumber(",1.23"));
        assertFalse(NumberUtils.isNumber(","));

        assertTrue(NumberUtils.isNumber("1"));
        assertTrue(NumberUtils.isNumber("1.23"));

        assertTrue(NumberUtils.isInteger("1"));
        assertTrue(NumberUtils.isInteger("123"));
        assertTrue(NumberUtils.isInteger("012"));
        assertTrue(NumberUtils.isInteger("0123456789"));
        assertFalse(NumberUtils.isInteger("  012"));
        assertFalse(NumberUtils.isInteger("  0123456789"));
        assertFalse(NumberUtils.isInteger("012  "));
        assertFalse(NumberUtils.isInteger("0123456789  "));

        assertTrue(NumberUtils.isInteger("+1"));
        assertTrue(NumberUtils.isInteger("+123"));
        assertTrue(NumberUtils.isInteger("+012"));
        assertTrue(NumberUtils.isInteger("+0123456789"));
        assertFalse(NumberUtils.isInteger("+"));
        assertFalse(NumberUtils.isInteger("+  012"));
        assertFalse(NumberUtils.isInteger("+  0123456789"));
        assertFalse(NumberUtils.isInteger("+012  "));
        assertFalse(NumberUtils.isInteger("+0123456789  "));

        assertTrue(NumberUtils.isInteger("-1"));
        assertTrue(NumberUtils.isInteger("-123"));
        assertTrue(NumberUtils.isInteger("-012"));
        assertTrue(NumberUtils.isInteger("-0123456789"));
        assertFalse(NumberUtils.isInteger("-"));
        assertFalse(NumberUtils.isInteger("-  012"));
        assertFalse(NumberUtils.isInteger("-  0123456789"));
        assertFalse(NumberUtils.isInteger("-012  "));
        assertFalse(NumberUtils.isInteger("-0123456789  "));

        assertTrue(NumberUtils.isDecimal("1.2"));
        assertTrue(NumberUtils.isDecimal("0.123"));
        assertTrue(NumberUtils.isDecimal("01.2"));
        assertTrue(NumberUtils.isDecimal("01234.56789"));
        assertFalse(NumberUtils.isDecimal(".56789"));
        assertFalse(NumberUtils.isDecimal("  01.2"));
        assertFalse(NumberUtils.isDecimal("  01234.56789"));
        assertFalse(NumberUtils.isDecimal("01.2  "));
        assertFalse(NumberUtils.isDecimal("01234.56789  "));

        assertTrue(NumberUtils.isDecimal("+1.2"));
        assertTrue(NumberUtils.isDecimal("+0.123"));
        assertTrue(NumberUtils.isDecimal("+01.2"));
        assertTrue(NumberUtils.isDecimal("+01234.56789"));
        assertFalse(NumberUtils.isDecimal("+.56789"));
        assertFalse(NumberUtils.isDecimal("+"));
        assertFalse(NumberUtils.isDecimal("+  01.2"));
        assertFalse(NumberUtils.isDecimal("+  01234.56789"));
        assertFalse(NumberUtils.isDecimal("+01.2  "));
        assertFalse(NumberUtils.isDecimal("+01234.56789  "));

        assertTrue(NumberUtils.isDecimal("-1.2"));
        assertTrue(NumberUtils.isDecimal("-0.123"));
        assertTrue(NumberUtils.isDecimal("-01.2"));
        assertTrue(NumberUtils.isDecimal("-01234.56789"));
        assertFalse(NumberUtils.isDecimal("-.56789"));
        assertFalse(NumberUtils.isDecimal("-"));
        assertFalse(NumberUtils.isDecimal("-  01.2"));
        assertFalse(NumberUtils.isDecimal("-  01234.56789"));
        assertFalse(NumberUtils.isDecimal("-01.2  "));
        assertFalse(NumberUtils.isDecimal("-01234.56789  "));
    }

    @Test
    public void isIntegerTest() {
        assertFalse(NumberUtils.isInteger(null));
        assertFalse(NumberUtils.isInteger(""));
        assertFalse(NumberUtils.isInteger("  "));

        assertFalse(NumberUtils.isInteger("ABCDEFGHIJKLMNOPQRSTUVWXYZ"));
        assertFalse(NumberUtils.isInteger("AAA"));
        assertFalse(NumberUtils.isInteger("abcdefghijklmnopqrstuvwxyz"));
        assertFalse(NumberUtils.isInteger("aaa"));
        assertFalse(NumberUtils.isInteger("   aaa"));
        assertFalse(NumberUtils.isInteger("aaa   "));

        assertTrue(NumberUtils.isInteger("1"));
        assertTrue(NumberUtils.isInteger("123"));
        assertTrue(NumberUtils.isInteger("012"));
        assertTrue(NumberUtils.isInteger("0123456789"));
        assertFalse(NumberUtils.isInteger("  012"));
        assertFalse(NumberUtils.isInteger("  0123456789"));
        assertFalse(NumberUtils.isInteger("012  "));
        assertFalse(NumberUtils.isInteger("0123456789  "));

        assertFalse(NumberUtils.isInteger("1,"));
        assertFalse(NumberUtils.isInteger(",1"));
        assertFalse(NumberUtils.isInteger(","));

        assertTrue(NumberUtils.isInteger("+1"));
        assertTrue(NumberUtils.isInteger("+123"));
        assertTrue(NumberUtils.isInteger("+012"));
        assertTrue(NumberUtils.isInteger("+0123456789"));
        assertFalse(NumberUtils.isInteger("+"));
        assertFalse(NumberUtils.isInteger("+  012"));
        assertFalse(NumberUtils.isInteger("+  0123456789"));
        assertFalse(NumberUtils.isInteger("+012  "));
        assertFalse(NumberUtils.isInteger("+0123456789  "));

        assertTrue(NumberUtils.isInteger("-1"));
        assertTrue(NumberUtils.isInteger("-123"));
        assertTrue(NumberUtils.isInteger("-012"));
        assertTrue(NumberUtils.isInteger("-0123456789"));
        assertFalse(NumberUtils.isInteger("-"));
        assertFalse(NumberUtils.isInteger("-  012"));
        assertFalse(NumberUtils.isInteger("-  0123456789"));
        assertFalse(NumberUtils.isInteger("-012  "));
        assertFalse(NumberUtils.isInteger("-0123456789  "));

        assertFalse(NumberUtils.isInteger("012 aaa"));
        assertFalse(NumberUtils.isInteger("aaa 012"));
        assertFalse(NumberUtils.isInteger("012AAA"));
        assertFalse(NumberUtils.isInteger("AAA012"));
        assertFalse(NumberUtils.isInteger("012 AAA"));
        assertFalse(NumberUtils.isInteger("AAA 012"));
    }

    @Test
    public void isDecimalTest() {
        assertFalse(NumberUtils.isDecimal(null));
        assertFalse(NumberUtils.isDecimal(""));
        assertFalse(NumberUtils.isDecimal("  "));

        assertFalse(NumberUtils.isDecimal("ABCDEFGHIJKLMNOPQRSTUVWXYZ"));
        assertFalse(NumberUtils.isDecimal("AAA"));
        assertFalse(NumberUtils.isDecimal("abcdefghijklmnopqrstuvwxyz"));
        assertFalse(NumberUtils.isDecimal("aaa"));
        assertFalse(NumberUtils.isDecimal("   aaa"));
        assertFalse(NumberUtils.isDecimal("aaa   "));

        assertTrue(NumberUtils.isDecimal("1.2"));
        assertTrue(NumberUtils.isDecimal("0.123"));
        assertTrue(NumberUtils.isDecimal("01.2"));
        assertTrue(NumberUtils.isDecimal("01234.56789"));
        assertFalse(NumberUtils.isDecimal(".56789"));
        assertFalse(NumberUtils.isDecimal("  01.2"));
        assertFalse(NumberUtils.isDecimal("  01234.56789"));
        assertFalse(NumberUtils.isDecimal("01.2  "));
        assertFalse(NumberUtils.isDecimal("01234.56789  "));

        assertTrue(NumberUtils.isDecimal("+1.2"));
        assertTrue(NumberUtils.isDecimal("+0.123"));
        assertTrue(NumberUtils.isDecimal("+01.2"));
        assertTrue(NumberUtils.isDecimal("+01234.56789"));
        assertFalse(NumberUtils.isDecimal("+.56789"));
        assertFalse(NumberUtils.isDecimal("+"));
        assertFalse(NumberUtils.isDecimal("+  01.2"));
        assertFalse(NumberUtils.isDecimal("+  01234.56789"));
        assertFalse(NumberUtils.isDecimal("+01.2  "));
        assertFalse(NumberUtils.isDecimal("+01234.56789  "));

        assertTrue(NumberUtils.isDecimal("-1.2"));
        assertTrue(NumberUtils.isDecimal("-0.123"));
        assertTrue(NumberUtils.isDecimal("-01.2"));
        assertTrue(NumberUtils.isDecimal("-01234.56789"));
        assertFalse(NumberUtils.isDecimal("-.56789"));
        assertFalse(NumberUtils.isDecimal("-"));
        assertFalse(NumberUtils.isDecimal("-  01.2"));
        assertFalse(NumberUtils.isDecimal("-  01234.56789"));
        assertFalse(NumberUtils.isDecimal("-01.2  "));
        assertFalse(NumberUtils.isDecimal("-01234.56789  "));

        assertFalse(NumberUtils.isDecimal("012 aaa"));
        assertFalse(NumberUtils.isDecimal("aaa 012"));
        assertFalse(NumberUtils.isDecimal("012AAA"));
        assertFalse(NumberUtils.isDecimal("AAA012"));
        assertFalse(NumberUtils.isDecimal("012 AAA"));
        assertFalse(NumberUtils.isDecimal("AAA 012"));
    }

    @Test
    public void getDigitLengthTest() {
        assertEquals(NumberUtils.getDigitLength(BigInteger.valueOf(1)), 1);
        assertEquals(NumberUtils.getDigitLength(BigInteger.valueOf(100)), 3);
        assertEquals(NumberUtils.getDigitLength(BigInteger.valueOf(-1)), 1);
        assertEquals(NumberUtils.getDigitLength(BigInteger.valueOf(-100)), 3);

        assertEquals(NumberUtils.getDigitLength(1), 1);
        assertEquals(NumberUtils.getDigitLength(100), 3);
        assertEquals(NumberUtils.getDigitLength(-1), 1);
        assertEquals(NumberUtils.getDigitLength(-100), 3);
    }

    @Test
    public void maxTest() {
        assertEquals(NumberUtils.getMax(BigInteger.valueOf(1)), BigInteger.valueOf(1));
        assertEquals(NumberUtils.getMax(BigInteger.valueOf(1)), BigInteger.valueOf(1));
        assertEquals(NumberUtils.getMax(BigInteger.valueOf(0),BigInteger.valueOf(1)), BigInteger.valueOf(1));
        assertEquals(NumberUtils.getMax(BigInteger.valueOf(-1),BigInteger.valueOf(0)), BigInteger.valueOf(0));
        assertEquals(NumberUtils.getMax(BigInteger.valueOf(-1),BigInteger.valueOf(0),BigInteger.valueOf(1)), BigInteger.valueOf(1));
        assertEquals(NumberUtils.getMax(BigInteger.valueOf(1),BigInteger.valueOf(2),BigInteger.valueOf(3)), BigInteger.valueOf(3));
        assertEquals(NumberUtils.getMax(BigInteger.valueOf(-1),BigInteger.valueOf(-2),BigInteger.valueOf(-3)), BigInteger.valueOf(-1));

        assertEquals(NumberUtils.getMax(1), Integer.valueOf(1));
        assertEquals(NumberUtils.getMax(0,1), Integer.valueOf(1));
        assertEquals(NumberUtils.getMax(-1,0), Integer.valueOf(0));
        assertEquals(NumberUtils.getMax(-1,0,1), Integer.valueOf(1));
        assertEquals(NumberUtils.getMax(1,2,3), Integer.valueOf(3));
        assertEquals(NumberUtils.getMax(-1,-2,-3), Integer.valueOf(-1));
    }

    @Test
    public void minTest() {
        assertEquals(NumberUtils.getMin(BigInteger.valueOf(1)), BigInteger.valueOf(1));
        assertEquals(NumberUtils.getMin(BigInteger.valueOf(0),BigInteger.valueOf(1)), BigInteger.valueOf(0));
        assertEquals(NumberUtils.getMin(BigInteger.valueOf(-1),BigInteger.valueOf(0)), BigInteger.valueOf(-1));
        assertEquals(NumberUtils.getMin(BigInteger.valueOf(-1),BigInteger.valueOf(0),BigInteger.valueOf(1)), BigInteger.valueOf(-1));
        assertEquals(NumberUtils.getMin(BigInteger.valueOf(1),BigInteger.valueOf(2),BigInteger.valueOf(3)), BigInteger.valueOf(1));
        assertEquals(NumberUtils.getMin(BigInteger.valueOf(-1),BigInteger.valueOf(-2),BigInteger.valueOf(-3)), BigInteger.valueOf(-3));

        assertEquals(NumberUtils.getMin(1), Integer.valueOf(1));
        assertEquals(NumberUtils.getMin(0,1), Integer.valueOf(0));
        assertEquals(NumberUtils.getMin(-1,0), Integer.valueOf(-1));
        assertEquals(NumberUtils.getMin(-1,0,1), Integer.valueOf(-1));
        assertEquals(NumberUtils.getMin(1,2,3), Integer.valueOf(1));
        assertEquals(NumberUtils.getMin(-1,-2,-3), Integer.valueOf(-3));
    }

    @Test
    public void parseLongTest() {
        { // null
            assertNull(NumberUtils.parseLong(null));
        }
        { // 空
            final Object object = "";
            assertNull(NumberUtils.parseLong(object));
        }
        { // 不正
            final Object object = "test";
            assertNull(NumberUtils.parseLong(object));
        }
        { // 不正
            final Object object = ".123";
            assertNull(NumberUtils.parseLong(object));
        }
        { // 数値
            final Object object = "1";
            assertEquals(NumberUtils.parseLong(object), Long.valueOf(1));
        }
        { // 数値
            final Object object = "1.";
            assertNull(NumberUtils.parseLong(object));
        }
        { // 数値
            final Object object = "1.0";
            assertEquals(NumberUtils.parseLong(object), Long.valueOf(1));
        }
        { // 数値
            final Object object = "123.0";
            assertEquals(NumberUtils.parseLong(object), Long.valueOf(123));
        }
        { // 数値
            final Object object = "123.000";
            assertEquals(NumberUtils.parseLong(object), Long.valueOf(123));
        }
        assertThrows(NumberFormatException.class, () -> {
            // 小数
            final Object object = "123.456";
            NumberUtils.parseLong(object);
        });
        assertThrows(NumberFormatException.class, () -> {
            // 小数
            final Object object = "-123.456";
            NumberUtils.parseLong(object);
        });

        { // null
            assertNull(NumberUtils.parseLong(null));
        }
        { // 空
            final String object = "";
            assertNull(NumberUtils.parseLong(object));
        }
        { // 不正
            final String object = "test";
            assertNull(NumberUtils.parseLong(object));
        }
        { // 不正
            final String object = ".123";
            assertNull(NumberUtils.parseLong(object));
        }
        { // 数値
            final String object = "1";
            assertEquals(NumberUtils.parseLong(object), Long.valueOf(1));
        }
        { // 数値
            final String object = "1.";
            assertNull(NumberUtils.parseLong(object));
        }
        { // 数値
            final String object = "1.0";
            assertEquals(NumberUtils.parseLong(object), Long.valueOf(1));
        }
        { // 数値
            final String object = "123.0";
            assertEquals(NumberUtils.parseLong(object), Long.valueOf(123));
        }
        { // 数値
            final String object = "123.000";
            assertEquals(NumberUtils.parseLong(object), Long.valueOf(123));
        }
        assertThrows(NumberFormatException.class, () -> {
            // 小数
            final String object = "123.456";
            NumberUtils.parseLong(object);
        });
        assertThrows(NumberFormatException.class, () -> {
            // 小数
            final String object = "-123.456";
            NumberUtils.parseLong(object);
        });
    }

    @Test
    public void parseBigIntegerTest() {
        { // null
            assertNull(NumberUtils.parseBigInteger(null));
        }
        { // 空
            final Object object = "";
            assertNull(NumberUtils.parseBigInteger(object));
        }
        { // 不正
            final Object object = "test";
            assertNull(NumberUtils.parseBigInteger(object));
        }
        { // 不正
            final Object object = ".123";
            assertNull(NumberUtils.parseBigInteger(object));
        }
        { // 数値
            final Object object = "1";
            assertEquals(NumberUtils.parseBigInteger(object), BigInteger.valueOf(1));
        }
        { // 数値
            final Object object = "1.";
            assertNull(NumberUtils.parseBigInteger(object));
        }
        { // 数値
            final Object object = "1.0";
            assertEquals(NumberUtils.parseBigInteger(object), BigInteger.valueOf(1));
        }
        { // 数値
            final Object object = "123.0";
            assertEquals(NumberUtils.parseBigInteger(object), BigInteger.valueOf(123));
        }
        { // 数値
            final Object object = "123.000";
            assertEquals(NumberUtils.parseBigInteger(object), BigInteger.valueOf(123));
        }
        assertThrows(NumberFormatException.class, () -> {
            // 小数
            final Object object = "123.456";
            NumberUtils.parseBigInteger(object);
        });
        assertThrows(NumberFormatException.class, () -> {
            // 小数
            final Object object = "-123.456";
            NumberUtils.parseBigInteger(object);
        });

        { // null
            assertNull(NumberUtils.parseBigInteger(null));
        }
        { // 空
            final String object = "";
            assertNull(NumberUtils.parseBigInteger(object));
        }
        { // 不正
            final String object = "test";
            assertNull(NumberUtils.parseBigInteger(object));
        }
        { // 不正
            final String object = ".123";
            assertNull(NumberUtils.parseBigInteger(object));
        }
        { // 数値
            final String object = "1";
            assertEquals(NumberUtils.parseBigInteger(object), BigInteger.valueOf(1));
        }
        { // 数値
            final String object = "1.";
            assertNull(NumberUtils.parseBigInteger(object));
        }
        { // 数値
            final String object = "1.0";
            assertEquals(NumberUtils.parseBigInteger(object), BigInteger.valueOf(1));
        }
        { // 数値
            final String object = "123.0";
            assertEquals(NumberUtils.parseBigInteger(object), BigInteger.valueOf(123));
        }
        { // 数値
            final String object = "123.000";
            assertEquals(NumberUtils.parseBigInteger(object), BigInteger.valueOf(123));
        }
        assertThrows(NumberFormatException.class, () -> {
            // 小数
            final String object = "123.456";
            NumberUtils.parseBigInteger(object);
        });
        assertThrows(NumberFormatException.class, () -> {
            // 小数
            final String object = "-123.456";
            NumberUtils.parseBigInteger(object);
        });
    }

    @Test
    public void parseIntegerTest() {
        { // null
            assertNull(NumberUtils.parseInteger(null));
        }
        { // 空
            final Object object = "";
            assertNull(NumberUtils.parseInteger(object));
        }
        { // 不正
            final Object object = "test";
            assertNull(NumberUtils.parseInteger(object));
        }
        { // 不正
            final Object object = ".123";
            assertNull(NumberUtils.parseInteger(object));
        }
        { // 数値
            final Object object = "1";
            assertEquals(NumberUtils.parseInteger(object), Integer.valueOf(1));
        }
        { // 数値
            final Object object = "1.";
            assertNull(NumberUtils.parseInteger(object));
        }
        { // 数値
            final Object object = "1.0";
            assertEquals(NumberUtils.parseInteger(object), Integer.valueOf(1));
        }
        { // 数値
            final Object object = "123.0";
            assertEquals(NumberUtils.parseInteger(object), Integer.valueOf(123));
        }
        { // 数値
            final Object object = "123.000";
            assertEquals(NumberUtils.parseInteger(object), Integer.valueOf(123));
        }
        assertThrows(NumberFormatException.class, () -> {
            // 小数
            final Object object = "123.456";
            NumberUtils.parseInteger(object);
        });
        assertThrows(NumberFormatException.class, () -> {
            // 小数
            final Object object = "-123.456";
            NumberUtils.parseInteger(object);
        });

        { // null
            final String object = "";
            assertNull(NumberUtils.parseInteger(object));
        }
        { // 空
            final String object = "";
            assertNull(NumberUtils.parseInteger(object));
        }
        { // 不正
            final String object = "test";
            assertNull(NumberUtils.parseInteger(object));
        }
        { // 不正
            final String object = ".123";
            assertNull(NumberUtils.parseInteger(object));
        }
        { // 数値
            final String object = "1";
            assertEquals(NumberUtils.parseInteger(object), Integer.valueOf(1));
        }
        { // 数値
            final String object = "1.";
            assertNull(NumberUtils.parseInteger(object));
        }
        { // 数値
            final String object = "1.0";
            assertEquals(NumberUtils.parseInteger(object), Integer.valueOf(1));
        }
        { // 数値
            final String object = "123.0";
            assertEquals(NumberUtils.parseInteger(object), Integer.valueOf(123));
        }
        { // 数値
            final String object = "123.000";
            assertEquals(NumberUtils.parseInteger(object), Integer.valueOf(123));
        }
        assertThrows(NumberFormatException.class, () -> {
            // 小数
            final String object = "123.456";
            NumberUtils.parseInteger(object);
        });
        { // 数値
            final String object = "123";
            assertEquals(NumberUtils.parseInteger(object, 111), Integer.valueOf(123));
        }
        { // 数値
            final String object = "あいう123.456えお";
            assertEquals(NumberUtils.parseInteger(object, 111), Integer.valueOf(111));
        }
        { // 数値
            final String object = "123.456";
            assertEquals(NumberUtils.parseInteger(object, 111), Integer.valueOf(111));
        }
        assertThrows(NumberFormatException.class, () -> {
            // 小数
            final String object = "-123.456";
            NumberUtils.parseInteger(object);
        });
    }

    @Test
    public void parseDoubleTest() {
        { // 数値(デフォルト値になるケース)
            final String object = "あいう123.456えお";
            assertEquals(NumberUtils.parseDouble(object, 111.11), Double.valueOf(111.11));
        }
        { // 数値(デフォルト値にならないケース)
            final String object = "123.456";
            assertEquals(NumberUtils.parseDouble(object, 111.11), Double.valueOf(123.456));
        }
    }

    @Test
    public void toFormatStringTest() {
        assertEquals(NumberUtils.toFormatString(BigInteger.ZERO), "0");
        assertEquals(NumberUtils.toFormatString(BigInteger.valueOf(100)), "100");
        assertEquals(NumberUtils.toFormatString(BigInteger.valueOf(10000)), "10,000");
        assertEquals(NumberUtils.toFormatString(BigInteger.valueOf(10000000)), "10,000,000");
        assertEquals(NumberUtils.toFormatString(BigInteger.valueOf(-100)), "-100");
        assertEquals(NumberUtils.toFormatString(BigInteger.valueOf(-10000)), "-10,000");
        assertEquals(NumberUtils.toFormatString(BigInteger.valueOf(-10000000)), "-10,000,000");

        assertEquals(NumberUtils.toFormatString(0), "0");
        assertEquals(NumberUtils.toFormatString(100), "100");
        assertEquals(NumberUtils.toFormatString(10000), "10,000");
        assertEquals(NumberUtils.toFormatString(10000000), "10,000,000");
        assertEquals(NumberUtils.toFormatString(-100), "-100");
        assertEquals(NumberUtils.toFormatString(-10000), "-10,000");
        assertEquals(NumberUtils.toFormatString(-10000000), "-10,000,000");
    }
}
