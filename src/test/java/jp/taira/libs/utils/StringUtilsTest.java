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

    @Test
    public void endsWithIgnoreCaseTest() throws Exception {
        assertFalse(StringUtils.endsWithIgnoreCase(null, null));
        assertFalse(StringUtils.endsWithIgnoreCase(null, ""));
        assertThrows(NullPointerException.class, () -> {
            StringUtils.endsWithIgnoreCase(" ", null);
        });
        assertTrue(StringUtils.endsWithIgnoreCase(" ", ""));
        assertTrue(StringUtils.endsWithIgnoreCase("", ""));

        assertTrue(StringUtils.endsWithIgnoreCase("abc", ""));
        assertFalse(StringUtils.endsWithIgnoreCase("abc", "a"));
        assertFalse(StringUtils.endsWithIgnoreCase("abc", "A"));
        assertFalse(StringUtils.endsWithIgnoreCase("abc", "b"));
        assertFalse(StringUtils.endsWithIgnoreCase("abc", "B"));
        assertTrue(StringUtils.endsWithIgnoreCase("abc", "c"));
        assertTrue(StringUtils.endsWithIgnoreCase("abc", "C"));
        assertTrue(StringUtils.endsWithIgnoreCase("ABC", ""));
        assertFalse(StringUtils.endsWithIgnoreCase("ABC", "a"));
        assertFalse(StringUtils.endsWithIgnoreCase("ABC", "A"));
        assertFalse(StringUtils.endsWithIgnoreCase("ABC", "b"));
        assertFalse(StringUtils.endsWithIgnoreCase("ABC", "B"));
        assertTrue(StringUtils.endsWithIgnoreCase("ABC", "c"));
        assertTrue(StringUtils.endsWithIgnoreCase("ABC", "C"));
    }

    @Test
    public void trimTest() throws Exception {
        { /* 指定あり(文字) */
            assertNull(StringUtils.trim(null, "a"));
            assertEquals(StringUtils.trim("", "a"), "");
            assertEquals(StringUtils.trim("  ", "a"), "  ");
            assertEquals(StringUtils.trim(" 　", "a"), " 　");
            assertEquals(StringUtils.trim("　 ", "a"), "　 ");

            assertEquals(StringUtils.trim("AAA", "a"), "AAA");
            assertEquals(StringUtils.trim("A A A", "a"), "A A A");
            assertEquals(StringUtils.trim(" A A A ", "a"), " A A A ");
            assertEquals(StringUtils.trim(" 　A A A 　", "a"), " 　A A A 　");
            assertEquals(StringUtils.trim("　 A A A　 ", "a"), "　 A A A　 ");
            assertEquals(StringUtils.trim("　　A A A　　", "a"), "　　A A A　　");
            assertEquals(StringUtils.trim("    A A A    ", "a"), "    A A A    ");

            assertEquals(StringUtils.trim("A　A　A", "a"), "A　A　A");
            assertEquals(StringUtils.trim(" A　A　A ", "a"), " A　A　A ");
            assertEquals(StringUtils.trim(" 　A　A　A 　", "a"), " 　A　A　A 　");
            assertEquals(StringUtils.trim("　 A　A　A　 ", "a"), "　 A　A　A　 ");
            assertEquals(StringUtils.trim("　　A　A　A　　", "a"), "　　A　A　A　　");
            assertEquals(StringUtils.trim("    A　A　A    ", "a"), "    A　A　A    ");

            assertEquals(StringUtils.trim("a", "a"), "");
            assertEquals(StringUtils.trim("aaa", "a"), "");

            assertEquals(StringUtils.trim("abc", "a"), "bc");
            assertEquals(StringUtils.trim("abcabcabc", "a"), "bcabcabc");
            assertEquals(StringUtils.trim(" abc ", "a"), " abc ");
            assertEquals(StringUtils.trim(" abcabcabc ", "a"), " abcabcabc ");
            assertEquals(StringUtils.trim("abc abc abc", "a"), "bc abc abc");
            assertEquals(StringUtils.trim("abc abc", "a"), "bc abc");
        }

        { /* 指定あり(文字列) */
            assertNull(StringUtils.trim(null, "a"));
            assertEquals(StringUtils.trim("", "abc"), "");
            assertEquals(StringUtils.trim("  ", "abc"), "  ");
            assertEquals(StringUtils.trim(" 　", "abc"), " 　");
            assertEquals(StringUtils.trim("　 ", "abc"), "　 ");

            assertEquals(StringUtils.trim("AAA", "abc"), "AAA");
            assertEquals(StringUtils.trim("A A A", "abc"), "A A A");
            assertEquals(StringUtils.trim(" A A A ", "abc"), " A A A ");
            assertEquals(StringUtils.trim(" 　A A A 　", "abc"), " 　A A A 　");
            assertEquals(StringUtils.trim("　 A A A　 ", "abc"), "　 A A A　 ");
            assertEquals(StringUtils.trim("　　A A A　　", "abc"), "　　A A A　　");
            assertEquals(StringUtils.trim("    A A A    ", "abc"), "    A A A    ");

            assertEquals(StringUtils.trim("A　A　A", "abc"), "A　A　A");
            assertEquals(StringUtils.trim(" A　A　A ", "abc"), " A　A　A ");
            assertEquals(StringUtils.trim(" 　A　A　A 　", "abc"), " 　A　A　A 　");
            assertEquals(StringUtils.trim("　 A　A　A　 ", "abc"), "　 A　A　A　 ");
            assertEquals(StringUtils.trim("　　A　A　A　　", "abc"), "　　A　A　A　　");
            assertEquals(StringUtils.trim("    A　A　A    ", "abc"), "    A　A　A    ");

            assertEquals(StringUtils.trim("a", "abc"), "a");
            assertEquals(StringUtils.trim("aaa", "abc"), "aaa");

            assertEquals(StringUtils.trim("abc", "abc"), "");
            assertEquals(StringUtils.trim("abcabcabc", "abc"), "");
            assertEquals(StringUtils.trim(" abc ", "abc"), " abc ");
            assertEquals(StringUtils.trim(" abcabcabc ", "abc"), " abcabcabc ");
            assertEquals(StringUtils.trim("abc abc abc", "abc"), " abc ");
            assertEquals(StringUtils.trim("abc abc", "abc"), " ");
        }

        { /* 指定なし=空白(全角、半角) */
            assertNull(StringUtils.trim(null));
            assertEquals(StringUtils.trim(""), "");
            assertEquals(StringUtils.trim("  "), "");
            assertEquals(StringUtils.trim(" 　"), "");
            assertEquals(StringUtils.trim("　 "), "");

            assertEquals(StringUtils.trim("AAA"), "AAA");
            assertEquals(StringUtils.trim("A A A"), "A A A");
            assertEquals(StringUtils.trim(" A A A "), "A A A");
            assertEquals(StringUtils.trim(" 　A A A 　"), "A A A");
            assertEquals(StringUtils.trim("　 A A A　 "), "A A A");
            assertEquals(StringUtils.trim("　　A A A　　"), "A A A");
            assertEquals(StringUtils.trim("    A A A    "), "A A A");

            assertEquals(StringUtils.trim("A　A　A"), "A　A　A");
            assertEquals(StringUtils.trim(" A　A　A "), "A　A　A");
            assertEquals(StringUtils.trim(" 　A　A　A 　"), "A　A　A");
            assertEquals(StringUtils.trim("　 A　A　A　 "), "A　A　A");
            assertEquals(StringUtils.trim("　　A　A　A　　"), "A　A　A");
            assertEquals(StringUtils.trim("    A　A　A    "), "A　A　A");
        }
    }

    public void isAsciiTest() throws Exception {
        assertFalse(StringUtils.isAscii(null));
        assertFalse(StringUtils.isAscii(""));
        assertTrue(StringUtils.isAscii("  "));

        assertTrue(StringUtils.isAscii("ABCDEFGHIJKLMNOPQRSTUVWXYZ"));
        assertTrue(StringUtils.isAscii("AAA"));
        assertTrue(StringUtils.isAscii("abcdefghijklmnopqrstuvwxyz"));
        assertTrue(StringUtils.isAscii("aaa"));
        assertTrue(StringUtils.isAscii("  aaa"));
        assertTrue(StringUtils.isAscii("aaa  "));

        assertTrue(StringUtils.isAscii("012"));
        assertTrue(StringUtils.isAscii("0123456789"));
        assertTrue(StringUtils.isAscii("  012"));
        assertTrue(StringUtils.isAscii("  0123456789"));
        assertTrue(StringUtils.isAscii("012  "));
        assertTrue(StringUtils.isAscii("0123456789  "));

        assertTrue(StringUtils.isAscii("012aaa"));
        assertTrue(StringUtils.isAscii("aaa012"));
        assertTrue(StringUtils.isAscii("012 aaa"));
        assertTrue(StringUtils.isAscii("aaa 012"));
        assertTrue(StringUtils.isAscii("012AAA"));
        assertTrue(StringUtils.isAscii("AAA012"));
        assertTrue(StringUtils.isAscii("012 AAA"));
        assertTrue(StringUtils.isAscii("AAA 012"));

        assertFalse(StringUtils.isAscii("　"));
        assertFalse(StringUtils.isAscii("０１２３４５６７８９"));
        assertFalse(StringUtils.isAscii("ＡＢＣＤＥＦＧＨＩＪＫＬＭＮＯＰＱＲＳＴＵＶＷＸＹＺ"));
        assertFalse(StringUtils.isAscii("ａｂｃｄｅｆｇｈｉｊｋｌｍｎｏｐｑｒｓｔｕｖｗｘｙｚ"));
        assertFalse(StringUtils.isAscii("あいうえお"));
        assertFalse(StringUtils.isAscii("アイウエオ"));
    }

    @Test
    public void isAlphaTest() throws Exception {
        assertFalse(StringUtils.isAlpha(null));
        assertFalse(StringUtils.isAlpha(""));
        assertFalse(StringUtils.isAlpha("  "));

        assertTrue(StringUtils.isAlpha("ABCDEFGHIJKLMNOPQRSTUVWXYZ"));
        assertTrue(StringUtils.isAlpha("AAA"));
        assertTrue(StringUtils.isAlpha("abcdefghijklmnopqrstuvwxyz"));
        assertTrue(StringUtils.isAlpha("aaa"));
        assertFalse(StringUtils.isAlpha("  aaa"));
        assertFalse(StringUtils.isAlpha("aaa  "));

        assertFalse(StringUtils.isAlpha("012"));
        assertFalse(StringUtils.isAlpha("0123456789"));
        assertFalse(StringUtils.isAlpha("  012"));
        assertFalse(StringUtils.isAlpha("  0123456789"));
        assertFalse(StringUtils.isAlpha("012  "));
        assertFalse(StringUtils.isAlpha("0123456789  "));

        assertFalse(StringUtils.isAlpha("012aaa"));
        assertFalse(StringUtils.isAlpha("aaa012"));
        assertFalse(StringUtils.isAlpha("012 aaa"));
        assertFalse(StringUtils.isAlpha("aaa 012"));
        assertFalse(StringUtils.isAlpha("012AAA"));
        assertFalse(StringUtils.isAlpha("AAA012"));
        assertFalse(StringUtils.isAlpha("012 AAA"));
        assertFalse(StringUtils.isAlpha("AAA 012"));

        assertFalse(StringUtils.isAlpha("　"));
        assertFalse(StringUtils.isAlpha("０１２３４５６７８９"));
        assertFalse(StringUtils.isAlpha("ＡＢＣＤＥＦＧＨＩＪＫＬＭＮＯＰＱＲＳＴＵＶＷＸＹＺ"));
        assertFalse(StringUtils.isAlpha("ａｂｃｄｅｆｇｈｉｊｋｌｍｎｏｐｑｒｓｔｕｖｗｘｙｚ"));
        assertFalse(StringUtils.isAlpha("あいうえお"));
        assertFalse(StringUtils.isAlpha("アイウエオ"));
    }

    @Test
    public void isHalfAlphaNumericTest() throws Exception {
        assertFalse(StringUtils.isHalfAlphaNumeric(null));
        assertFalse(StringUtils.isHalfAlphaNumeric(""));
        assertFalse(StringUtils.isHalfAlphaNumeric("  "));

        assertTrue(StringUtils.isHalfAlphaNumeric("ABCDEFGHIJKLMNOPQRSTUVWXYZ"));
        assertTrue(StringUtils.isHalfAlphaNumeric("AAA"));
        assertTrue(StringUtils.isHalfAlphaNumeric("abcdefghijklmnopqrstuvwxyz"));
        assertTrue(StringUtils.isHalfAlphaNumeric("aaa"));
        assertFalse(StringUtils.isHalfAlphaNumeric("  aaa"));
        assertFalse(StringUtils.isHalfAlphaNumeric("aaa  "));

        assertTrue(StringUtils.isHalfAlphaNumeric("012"));
        assertTrue(StringUtils.isHalfAlphaNumeric("0123456789"));
        assertFalse(StringUtils.isHalfAlphaNumeric("  012"));
        assertFalse(StringUtils.isHalfAlphaNumeric("  0123456789"));
        assertFalse(StringUtils.isHalfAlphaNumeric("012  "));
        assertFalse(StringUtils.isHalfAlphaNumeric("0123456789  "));

        assertTrue(StringUtils.isHalfAlphaNumeric("012aaa"));
        assertTrue(StringUtils.isHalfAlphaNumeric("aaa012"));
        assertFalse(StringUtils.isHalfAlphaNumeric("012 aaa"));
        assertFalse(StringUtils.isHalfAlphaNumeric("aaa 012"));
        assertTrue(StringUtils.isHalfAlphaNumeric("012AAA"));
        assertTrue(StringUtils.isHalfAlphaNumeric("AAA012"));
        assertFalse(StringUtils.isHalfAlphaNumeric("012 AAA"));
        assertFalse(StringUtils.isHalfAlphaNumeric("AAA 012"));

        assertFalse(StringUtils.isHalfAlphaNumeric("　"));
        assertFalse(StringUtils.isHalfAlphaNumeric("０１２３４５６７８９"));
        assertFalse(StringUtils.isHalfAlphaNumeric("ＡＢＣＤＥＦＧＨＩＪＫＬＭＮＯＰＱＲＳＴＵＶＷＸＹＺ"));
        assertFalse(StringUtils.isHalfAlphaNumeric("ａｂｃｄｅｆｇｈｉｊｋｌｍｎｏｐｑｒｓｔｕｖｗｘｙｚ"));
        assertFalse(StringUtils.isHalfAlphaNumeric("あいうえお"));
        assertFalse(StringUtils.isHalfAlphaNumeric("アイウエオ"));
    }
}
