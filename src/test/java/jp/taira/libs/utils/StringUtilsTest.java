package jp.taira.libs.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
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

    @Test
    public void isHiraganaTest() throws Exception {
        assertFalse(StringUtils.isHiragana(null));
        assertFalse(StringUtils.isHiragana(""));
        assertFalse(StringUtils.isHiragana("  "));

        assertFalse(StringUtils.isHiragana("ABCDEFGHIJKLMNOPQRSTUVWXYZ"));
        assertFalse(StringUtils.isHiragana("AAA"));
        assertFalse(StringUtils.isHiragana("abcdefghijklmnopqrstuvwxyz"));
        assertFalse(StringUtils.isHiragana("aaa"));
        assertFalse(StringUtils.isHiragana("  aaa"));
        assertFalse(StringUtils.isHiragana("aaa  "));

        assertFalse(StringUtils.isHiragana("012"));
        assertFalse(StringUtils.isHiragana("0123456789"));
        assertFalse(StringUtils.isHiragana("  012"));
        assertFalse(StringUtils.isHiragana("  0123456789"));
        assertFalse(StringUtils.isHiragana("012  "));
        assertFalse(StringUtils.isHiragana("0123456789  "));

        assertFalse(StringUtils.isHiragana("012aaa"));
        assertFalse(StringUtils.isHiragana("aaa012"));
        assertFalse(StringUtils.isHiragana("012 aaa"));
        assertFalse(StringUtils.isHiragana("aaa 012"));
        assertFalse(StringUtils.isHiragana("012AAA"));
        assertFalse(StringUtils.isHiragana("AAA012"));
        assertFalse(StringUtils.isHiragana("012 AAA"));
        assertFalse(StringUtils.isHiragana("AAA 012"));

        assertFalse(StringUtils.isHiragana("　"));
        assertFalse(StringUtils.isHiragana("０１２３４５６７８９"));
        assertFalse(StringUtils.isHiragana("ＡＢＣＤＥＦＧＨＩＪＫＬＭＮＯＰＱＲＳＴＵＶＷＸＹＺ"));
        assertFalse(StringUtils.isHiragana("ａｂｃｄｅｆｇｈｉｊｋｌｍｎｏｐｑｒｓｔｕｖｗｘｙｚ"));
        assertTrue(StringUtils.isHiragana("あいうえお"));
        assertFalse(StringUtils.isHiragana("アイウエオ"));
    }

    @Test
    public void isKatakanaTest() throws Exception {
        assertFalse(StringUtils.isKatakana(null));
        assertFalse(StringUtils.isKatakana(""));
        assertFalse(StringUtils.isKatakana("  "));

        assertFalse(StringUtils.isKatakana("ABCDEFGHIJKLMNOPQRSTUVWXYZ"));
        assertFalse(StringUtils.isKatakana("AAA"));
        assertFalse(StringUtils.isKatakana("abcdefghijklmnopqrstuvwxyz"));
        assertFalse(StringUtils.isKatakana("aaa"));
        assertFalse(StringUtils.isKatakana("  aaa"));
        assertFalse(StringUtils.isKatakana("aaa  "));

        assertFalse(StringUtils.isKatakana("012"));
        assertFalse(StringUtils.isKatakana("0123456789"));
        assertFalse(StringUtils.isKatakana("  012"));
        assertFalse(StringUtils.isKatakana("  0123456789"));
        assertFalse(StringUtils.isKatakana("012  "));
        assertFalse(StringUtils.isKatakana("0123456789  "));

        assertFalse(StringUtils.isKatakana("012aaa"));
        assertFalse(StringUtils.isKatakana("aaa012"));
        assertFalse(StringUtils.isKatakana("012 aaa"));
        assertFalse(StringUtils.isKatakana("aaa 012"));
        assertFalse(StringUtils.isKatakana("012AAA"));
        assertFalse(StringUtils.isKatakana("AAA012"));
        assertFalse(StringUtils.isKatakana("012 AAA"));
        assertFalse(StringUtils.isKatakana("AAA 012"));

        assertFalse(StringUtils.isKatakana("　"));
        assertFalse(StringUtils.isKatakana("０１２３４５６７８９"));
        assertFalse(StringUtils.isKatakana("ＡＢＣＤＥＦＧＨＩＪＫＬＭＮＯＰＱＲＳＴＵＶＷＸＹＺ"));
        assertFalse(StringUtils.isKatakana("ａｂｃｄｅｆｇｈｉｊｋｌｍｎｏｐｑｒｓｔｕｖｗｘｙｚ"));
        assertFalse(StringUtils.isKatakana("あいうえお"));
        assertTrue(StringUtils.isKatakana("アイウエオ"));
    }

    @Test
    public void toHalfOfNumberTest() throws Exception {
        assertNull(StringUtils.toHalfOfNumber(null));
        assertEquals(StringUtils.toHalfOfNumber(""), "");
        assertEquals(StringUtils.toHalfOfNumber(" "), " ");
        assertEquals(StringUtils.toHalfOfNumber("　"), "　");

        assertEquals(StringUtils.toHalfOfNumber("０"), "0");
        assertEquals(StringUtils.toHalfOfNumber("１"), "1");
        assertEquals(StringUtils.toHalfOfNumber("２"), "2");
        assertEquals(StringUtils.toHalfOfNumber("３"), "3");
        assertEquals(StringUtils.toHalfOfNumber("４"), "4");
        assertEquals(StringUtils.toHalfOfNumber("５"), "5");
        assertEquals(StringUtils.toHalfOfNumber("６"), "6");
        assertEquals(StringUtils.toHalfOfNumber("７"), "7");
        assertEquals(StringUtils.toHalfOfNumber("８"), "8");
        assertEquals(StringUtils.toHalfOfNumber("９"), "9");

        assertEquals(StringUtils.toHalfOfNumber("0"), "0");
        assertEquals(StringUtils.toHalfOfNumber("1"), "1");
        assertEquals(StringUtils.toHalfOfNumber("2"), "2");
        assertEquals(StringUtils.toHalfOfNumber("3"), "3");
        assertEquals(StringUtils.toHalfOfNumber("4"), "4");
        assertEquals(StringUtils.toHalfOfNumber("5"), "5");
        assertEquals(StringUtils.toHalfOfNumber("6"), "6");
        assertEquals(StringUtils.toHalfOfNumber("7"), "7");
        assertEquals(StringUtils.toHalfOfNumber("8"), "8");
        assertEquals(StringUtils.toHalfOfNumber("9"), "9");

        assertEquals(StringUtils.toHalfOfNumber("０１２３４５６７８９"), "0123456789");
        assertEquals(StringUtils.toHalfOfNumber(" ０１２３４５６７８９"), " 0123456789");
        assertEquals(StringUtils.toHalfOfNumber("０１２３４５６７８９ "), "0123456789 ");
        assertEquals(StringUtils.toHalfOfNumber(" ０１２３４５６７８９ "), " 0123456789 ");
        assertEquals(StringUtils.toHalfOfNumber("０１２３４ ５６７８９"), "01234 56789");
        assertEquals(StringUtils.toHalfOfNumber("　０１２３４５６７８９"), "　0123456789");
        assertEquals(StringUtils.toHalfOfNumber("０１２３４５６７８９　"), "0123456789　");
        assertEquals(StringUtils.toHalfOfNumber("　０１２３４５６７８９　"), "　0123456789　");
        assertEquals(StringUtils.toHalfOfNumber("０１２３４　５６７８９"), "01234　56789");

        assertEquals(StringUtils.toHalfOfNumber("0123456789"), "0123456789");
        assertEquals(StringUtils.toHalfOfNumber(" 0123456789"), " 0123456789");
        assertEquals(StringUtils.toHalfOfNumber("0123456789 "), "0123456789 ");
        assertEquals(StringUtils.toHalfOfNumber(" 0123456789 "), " 0123456789 ");
        assertEquals(StringUtils.toHalfOfNumber("01234 56789"), "01234 56789");
        assertEquals(StringUtils.toHalfOfNumber("　0123456789"), "　0123456789");
        assertEquals(StringUtils.toHalfOfNumber("0123456789　"), "0123456789　");
        assertEquals(StringUtils.toHalfOfNumber("　0123456789　"), "　0123456789　");
        assertEquals(StringUtils.toHalfOfNumber("01234　56789"), "01234　56789");

        assertEquals(StringUtils.toHalfOfNumber("００１１２２３３４４５５６６７７８８９９"), "00112233445566778899");
        assertEquals(StringUtils.toHalfOfNumber("0123456789"), "0123456789");
        assertEquals(StringUtils.toHalfOfNumber("あいうえお"), "あいうえお");
        assertEquals(StringUtils.toHalfOfNumber("あいうえお１かきくけこ"), "あいうえお1かきくけこ");
        assertEquals(StringUtils.toHalfOfNumber("あいうえお1かきくけこ"), "あいうえお1かきくけこ");

        assertEquals(StringUtils.toHalfOfNumber("ＡＢＣ"), "ＡＢＣ");
        assertEquals(StringUtils.toHalfOfNumber("abc"), "abc");
    }

    @Test
    public void toHalfOfAlphaTest() throws Exception {
        assertNull(StringUtils.toHalfOfAlpha(null));
        assertEquals(StringUtils.toHalfOfAlpha(""), "");

        assertEquals(StringUtils.toHalfOfAlpha("０１２３４５６７８９"), "０１２３４５６７８９");
        assertEquals(StringUtils.toHalfOfAlpha("ＡＢＣＤＥＦＧＨＩＪＫＬＭＮＯＰＱＲＳＴＵＶＷＸＹＺ"), "ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        assertEquals(StringUtils.toHalfOfAlpha("ａｂｃｄｅｆｇｈｉｊｋｌｍｎｏｐｑｒｓｔｕｖｗｘｙｚ"), "abcdefghijklmnopqrstuvwxyz");
    }

    @Test
    public void toHalfOfAlphaUpperTest() throws Exception {
        assertNull(StringUtils.toHalfOfAlphaUpper(null));
        assertEquals(StringUtils.toHalfOfAlphaUpper(""), "");

        assertEquals(StringUtils.toHalfOfAlphaUpper("０１２３４５６７８９"), "０１２３４５６７８９");
        assertEquals(StringUtils.toHalfOfAlphaUpper("ＡＢＣＤＥＦＧＨＩＪＫＬＭＮＯＰＱＲＳＴＵＶＷＸＹＺ"), "ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        assertEquals(StringUtils.toHalfOfAlphaUpper("ａｂｃｄｅｆｇｈｉｊｋｌｍｎｏｐｑｒｓｔｕｖｗｘｙｚ"), "ａｂｃｄｅｆｇｈｉｊｋｌｍｎｏｐｑｒｓｔｕｖｗｘｙｚ");
    }

    @Test
    public void toHalfOfAlphaLowerTest() throws Exception {
        assertNull(StringUtils.toHalfOfAlphaLower(null));
        assertEquals(StringUtils.toHalfOfAlphaLower(""), "");

        assertEquals(StringUtils.toHalfOfAlphaLower("０１２３４５６７８９"), "０１２３４５６７８９");
        assertEquals(StringUtils.toHalfOfAlphaLower("ＡＢＣＤＥＦＧＨＩＪＫＬＭＮＯＰＱＲＳＴＵＶＷＸＹＺ"), "ＡＢＣＤＥＦＧＨＩＪＫＬＭＮＯＰＱＲＳＴＵＶＷＸＹＺ");
        assertEquals(StringUtils.toHalfOfAlphaLower("ａｂｃｄｅｆｇｈｉｊｋｌｍｎｏｐｑｒｓｔｕｖｗｘｙｚ"), "abcdefghijklmnopqrstuvwxyz");
    }

    @Test
    public void toFullOfNumberTest() throws Exception {
        assertNull(StringUtils.toFullOfNumber(null));
        assertEquals(StringUtils.toFullOfNumber(""), "");
        assertEquals(StringUtils.toFullOfNumber(" "), " ");
        assertEquals(StringUtils.toFullOfNumber("　"), "　");

        assertEquals(StringUtils.toFullOfNumber("０"), "０");
        assertEquals(StringUtils.toFullOfNumber("１"), "１");
        assertEquals(StringUtils.toFullOfNumber("２"), "２");
        assertEquals(StringUtils.toFullOfNumber("３"), "３");
        assertEquals(StringUtils.toFullOfNumber("４"), "４");
        assertEquals(StringUtils.toFullOfNumber("５"), "５");
        assertEquals(StringUtils.toFullOfNumber("６"), "６");
        assertEquals(StringUtils.toFullOfNumber("７"), "７");
        assertEquals(StringUtils.toFullOfNumber("８"), "８");
        assertEquals(StringUtils.toFullOfNumber("９"), "９");

        assertEquals(StringUtils.toFullOfNumber("0"), "０");
        assertEquals(StringUtils.toFullOfNumber("1"), "１");
        assertEquals(StringUtils.toFullOfNumber("2"), "２");
        assertEquals(StringUtils.toFullOfNumber("3"), "３");
        assertEquals(StringUtils.toFullOfNumber("4"), "４");
        assertEquals(StringUtils.toFullOfNumber("5"), "５");
        assertEquals(StringUtils.toFullOfNumber("6"), "６");
        assertEquals(StringUtils.toFullOfNumber("7"), "７");
        assertEquals(StringUtils.toFullOfNumber("8"), "８");
        assertEquals(StringUtils.toFullOfNumber("9"), "９");

        assertEquals(StringUtils.toFullOfNumber("０１２３４５６７８９"), "０１２３４５６７８９");
        assertEquals(StringUtils.toFullOfNumber(" ０１２３４５６７８９"), " ０１２３４５６７８９");
        assertEquals(StringUtils.toFullOfNumber("０１２３４５６７８９ "), "０１２３４５６７８９ ");
        assertEquals(StringUtils.toFullOfNumber(" ０１２３４５６７８９ "), " ０１２３４５６７８９ ");
        assertEquals(StringUtils.toFullOfNumber("０１２３４ ５６７８９"), "０１２３４ ５６７８９");
        assertEquals(StringUtils.toFullOfNumber("　０１２３４５６７８９"), "　０１２３４５６７８９");
        assertEquals(StringUtils.toFullOfNumber("０１２３４５６７８９　"), "０１２３４５６７８９　");
        assertEquals(StringUtils.toFullOfNumber("　０１２３４５６７８９　"), "　０１２３４５６７８９　");
        assertEquals(StringUtils.toFullOfNumber("０１２３４　５６７８９"), "０１２３４　５６７８９");

        assertEquals(StringUtils.toFullOfNumber("0123456789"), "０１２３４５６７８９");
        assertEquals(StringUtils.toFullOfNumber(" 0123456789"), " ０１２３４５６７８９");
        assertEquals(StringUtils.toFullOfNumber("0123456789 "), "０１２３４５６７８９ ");
        assertEquals(StringUtils.toFullOfNumber(" 0123456789 "), " ０１２３４５６７８９ ");
        assertEquals(StringUtils.toFullOfNumber("01234 56789"), "０１２３４ ５６７８９");
        assertEquals(StringUtils.toFullOfNumber("　0123456789"), "　０１２３４５６７８９");
        assertEquals(StringUtils.toFullOfNumber("0123456789　"), "０１２３４５６７８９　");
        assertEquals(StringUtils.toFullOfNumber("　0123456789　"), "　０１２３４５６７８９　");
        assertEquals(StringUtils.toFullOfNumber("01234　56789"), "０１２３４　５６７８９");

        assertEquals(StringUtils.toFullOfNumber("００１１２２３３４４５５６６７７８８９９"), "００１１２２３３４４５５６６７７８８９９");
        assertEquals(StringUtils.toFullOfNumber("0123456789"), "０１２３４５６７８９");
        assertEquals(StringUtils.toFullOfNumber("あいうえお"), "あいうえお");
        assertEquals(StringUtils.toFullOfNumber("あいうえお１かきくけこ"), "あいうえお１かきくけこ");
        assertEquals(StringUtils.toFullOfNumber("あいうえお1かきくけこ"), "あいうえお１かきくけこ");

        assertEquals(StringUtils.toFullOfNumber("ＡＢＣ"), "ＡＢＣ");
        assertEquals(StringUtils.toFullOfNumber("abc"), "abc");
    }

    @Test
    public void isEmailTest() throws Exception {
        // 通常
        assertTrue(StringUtils.isEmail("x@xx.xx"));

        // 通常
        assertTrue(StringUtils.isEmail("test@test.test"));
        assertTrue(StringUtils.isEmail("TEST@test.test"));
        assertTrue(StringUtils.isEmail("test@TEST.test"));
        assertTrue(StringUtils.isEmail("test@test.TEST"));
        assertTrue(StringUtils.isEmail("TEST@TEST.TEST"));

        // 通常
        assertTrue(StringUtils.isEmail("te.st@test.test"));
        assertTrue(StringUtils.isEmail("TE.ST@test.test"));
        assertTrue(StringUtils.isEmail("te.st@TEST.test"));
        assertTrue(StringUtils.isEmail("te.st@test.TEST"));

        // 通常
        assertTrue(StringUtils.isEmail("tEsT@test.test"));
        assertTrue(StringUtils.isEmail("test@tEsT.test"));
        assertTrue(StringUtils.isEmail("test@test.tEsT"));

        // 通常
        assertTrue(StringUtils.isEmail("test123@test.test"));
        assertTrue(StringUtils.isEmail("test123@test123.test"));
        assertTrue(StringUtils.isEmail("TEST123@test123.test"));
        assertTrue(StringUtils.isEmail("test123@TEST123.test"));
        assertTrue(StringUtils.isEmail("test@aa.bb.cc"));
        assertTrue(StringUtils.isEmail("test@aa.bb.cc.dd"));

        // ローカル部： 記号
        assertTrue(StringUtils.isEmail("xx-123@xxx.jp"));
        assertTrue(StringUtils.isEmail("xx_123@xxx.jp"));
        assertTrue(StringUtils.isEmail("x-x-123@xxx.jp"));
        assertTrue(StringUtils.isEmail("x_x_123@xxx.jp"));
        assertTrue(StringUtils.isEmail("-xx-123@xxx.jp"));
        assertTrue(StringUtils.isEmail("_xx_123@xxx.jp"));
        assertTrue(StringUtils.isEmail("xx--123@xxx.jp"));
        assertTrue(StringUtils.isEmail("xx__123@xxx.jp"));

        // ドメイン部： 記号
        assertTrue(StringUtils.isEmail("xxx123@x-xx.jp"));
        assertTrue(StringUtils.isEmail("xxx123@x-x-x.jp"));
        assertTrue(StringUtils.isEmail("xxx123@x--xx.jp"));

        // UTF-8
        assertFalse(StringUtils.isEmail("あいうえお@test.test"));
        assertFalse(StringUtils.isEmail("test@あいうえお.test"));
        assertFalse(StringUtils.isEmail("test@test.あいうえお"));
        assertFalse(StringUtils.isEmail("アイウエオ@test.test"));
        assertFalse(StringUtils.isEmail("test@アイウエオ.test"));
        assertFalse(StringUtils.isEmail("test@test.アイウエオ"));
        assertFalse(StringUtils.isEmail("漢字@test.test"));
        assertFalse(StringUtils.isEmail("test@漢字.test"));
        assertFalse(StringUtils.isEmail("test@test.漢字"));

        // 不正な文字列
        assertFalse(StringUtils.isEmail("test"));
        assertFalse(StringUtils.isEmail("@test"));
        assertFalse(StringUtils.isEmail("test@"));
        assertFalse(StringUtils.isEmail("@test@test.test"));
        assertFalse(StringUtils.isEmail("test@@test.test"));
        assertFalse(StringUtils.isEmail("test@te@st.test"));
        assertFalse(StringUtils.isEmail("test@test.test@"));
        assertFalse(StringUtils.isEmail(".test@test.test"));
        assertFalse(StringUtils.isEmail("test.@test.test"));
        assertFalse(StringUtils.isEmail("test@.test.test"));
        assertFalse(StringUtils.isEmail("test@test..test"));
        assertFalse(StringUtils.isEmail("test@test.test."));
        assertFalse(StringUtils.isEmail("xxx123@-xxx.jp"));
        assertFalse(StringUtils.isEmail("xxx123@xxx-.jp"));
        assertFalse(StringUtils.isEmail("xxx123@x_xx.jp"));

        // 不正な文字列
        assertFalse(StringUtils.isEmail(" test@test.test"));
        assertFalse(StringUtils.isEmail("te st@test.test"));
        assertFalse(StringUtils.isEmail("test @test.test"));
        assertFalse(StringUtils.isEmail("　test@test.test"));
        assertFalse(StringUtils.isEmail("te　st@test.test"));
        assertFalse(StringUtils.isEmail("test　@test.test"));

        // 不正な文字列
        assertFalse(StringUtils.isEmail("test@ test.test"));
        assertFalse(StringUtils.isEmail("test@te st.test"));
        assertFalse(StringUtils.isEmail("test@test .test"));
        assertFalse(StringUtils.isEmail("test@　test.test"));
        assertFalse(StringUtils.isEmail("test@te　st.test"));
        assertFalse(StringUtils.isEmail("test@test　.test"));

        // 不正な文字列
        assertFalse(StringUtils.isEmail("test@test. test"));
        assertFalse(StringUtils.isEmail("test@test.te st"));
        assertFalse(StringUtils.isEmail("test@test.test "));
        assertFalse(StringUtils.isEmail("test@test.　test"));
        assertFalse(StringUtils.isEmail("test@test.te　st"));
        assertFalse(StringUtils.isEmail("test@test.test　"));
    }

    @Test
    public void fillSpaceTest() throws Exception {
        { // fixed = true
            assertNull(StringUtils.fillSpace(null, 0, true));
            assertEquals(StringUtils.fillSpace(null, 4, true), "    ");
            assertEquals(StringUtils.fillSpace(null, -4, true), "    ");
            assertEquals(StringUtils.fillSpace("", 0, true), "");
            assertEquals(StringUtils.fillSpace("", 4, true), "    ");
            assertEquals(StringUtils.fillSpace("", -4, true), "    ");

            assertEquals(StringUtils.fillSpace("123", 0, true), "123");
            assertEquals(StringUtils.fillSpace("123", 4, true), " 123");
            assertEquals(StringUtils.fillSpace("123", -4, true), "123 ");
        }
        { // fixed = false
            assertNull(StringUtils.fillSpace(null, 0));
            assertEquals(StringUtils.fillSpace(null, 4), "    ");
            assertEquals(StringUtils.fillSpace(null, -4), "    ");
            assertEquals(StringUtils.fillSpace("", 0), "");
            assertEquals(StringUtils.fillSpace("", 4), "    ");
            assertEquals(StringUtils.fillSpace("", -4), "    ");

            assertEquals(StringUtils.fillSpace("123", 0), "123");
            assertEquals(StringUtils.fillSpace("123", 4), "    123");
            assertEquals(StringUtils.fillSpace("123", -4), "123    ");
        }
    }

    @Test
    public void fillZeroTest() throws Exception {
        { // fixed = true
            assertNull(StringUtils.fillZero(null, 0, true));
            assertEquals(StringUtils.fillZero(null, 4, true), "0000");
            assertEquals(StringUtils.fillZero(null, -4, true), "0000");
            assertEquals(StringUtils.fillZero("", 0, true), "");
            assertEquals(StringUtils.fillZero("", 4, true), "0000");
            assertEquals(StringUtils.fillZero("", -4, true), "0000");

            assertEquals(StringUtils.fillZero("123", 0, true), "123");
            assertEquals(StringUtils.fillZero("123", 4, true), "0123");
            assertEquals(StringUtils.fillZero("123", -4, true), "1230");

            assertEquals(StringUtils.fillZero("12 3", 0, true), "12 3");
            assertEquals(StringUtils.fillZero("12 3", 6, true), "0012 3");
            assertEquals(StringUtils.fillZero("12 3", -6, true), "12 300");
        }
        { // fixed = false
            assertNull(StringUtils.fillZero(null, 0));
            assertEquals(StringUtils.fillZero(null, 4), "0000");
            assertEquals(StringUtils.fillZero(null, -4), "0000");
            assertEquals(StringUtils.fillZero("", 0), "");
            assertEquals(StringUtils.fillZero("", 4), "0000");
            assertEquals(StringUtils.fillZero("", -4), "0000");

            assertEquals(StringUtils.fillZero("123", 0), "123");
            assertEquals(StringUtils.fillZero("123", 4), "0000123");
            assertEquals(StringUtils.fillZero("123", -4), "1230000");

            assertEquals(StringUtils.fillZero("12 3", 0), "12 3");
            assertEquals(StringUtils.fillZero("12 3", 6), "00000012 3");
            assertEquals(StringUtils.fillZero("12 3", -6), "12 3000000");
        }
    }

    @Test
    public void repeatTest() throws Exception {
        assertNull(StringUtils.repeat(null, -1));
        assertNull(StringUtils.repeat(null, 0));
        assertNull(StringUtils.repeat(null, 1));
        assertNull(StringUtils.repeat(null, 4));

        assertNull(StringUtils.repeat("", -1));
        assertNull(StringUtils.repeat("", 0));
        assertEquals(StringUtils.repeat("",  1), "");
        assertEquals(StringUtils.repeat("",  4), "");

        assertNull(StringUtils.repeat(" ", -1));
        assertNull(StringUtils.repeat(" ", 0));
        assertEquals(StringUtils.repeat(" ",  1), " ");
        assertEquals(StringUtils.repeat(" ",  4), "    ");

        assertNull(StringUtils.repeat("a", -1));
        assertNull(StringUtils.repeat("a", 0));
        assertEquals(StringUtils.repeat("a",  1), "a");
        assertEquals(StringUtils.repeat("a",  4), "aaaa");

        assertNull(StringUtils.repeat("あ", -1));
        assertNull(StringUtils.repeat("あ", 0));
        assertEquals(StringUtils.repeat("あ",  1), "あ");
        assertEquals(StringUtils.repeat("あ",  4), "ああああ");

        assertNull(StringUtils.repeat("ＡＢＣ", -1));
        assertNull(StringUtils.repeat("ＡＢＣ", 0));
        assertEquals(StringUtils.repeat("ＡＢＣ",  1), "ＡＢＣ");
        assertEquals(StringUtils.repeat("ＡＢＣ",  4), "ＡＢＣＡＢＣＡＢＣＡＢＣ");
    }

    @Test
    public void isUTF8Test() throws Exception {
        assertTrue(StringUtils.isUTF8("０１２３４５６７８９"));
        assertTrue(StringUtils.isUTF8("ＡＢＣＤＥＦＧＨＩＪＫＬＭＮＯＰＱＲＳＴＵＶＷＸＹＺ"));
        assertTrue(StringUtils.isUTF8("ａｂｃｄｅｆｇｈｉｊｋｌｍｎｏｐｑｒｓｔｕｖｗｘｙｚ"));
        assertTrue(StringUtils.isUTF8("あいうえお"));
        assertTrue(StringUtils.isUTF8("アイウエオ"));

        assertTrue(StringUtils.isUTF8("㈱"));
        assertTrue(StringUtils.isUTF8("森鷗外"));
        assertTrue(StringUtils.isUTF8("Καλημέρα κόσμε"));
    }

    @Test
    public void splitReturnCodeTest() throws Exception {
        { /* null */
            List<String> actual = StringUtils.splitReturnCode(null);
            assertNull(actual);
        }

        { /* 空文字 */
            List<String> actual = StringUtils.splitReturnCode("");
            assertNull(actual);
        }

        { /* 空白 */
            List<String> actual = StringUtils.splitReturnCode(" ");
            assertNotNull(actual);
            assertEquals(actual.size(), 1);
            assertEquals(actual.get(0), " ");
        }

        { /* 空白 */
            List<String> actual = StringUtils.splitReturnCode(" \n ");
            assertNotNull(actual);
            assertEquals(actual.size(), 2);
            assertEquals(actual.get(0), " ");
            assertEquals(actual.get(1), " ");
        }

        { /* 空白 */
            List<String> actual = StringUtils.splitReturnCode("　");
            assertNotNull(actual);
            assertEquals(actual.size(), 1);
            assertEquals(actual.get(0), "　");
        }

        { /* 空白 */
            List<String> actual = StringUtils.splitReturnCode("　\n　");
            assertNotNull(actual);
            assertEquals(actual.size(), 2);
            assertEquals(actual.get(0), "　");
            assertEquals(actual.get(1), "　");
        }

        { /* 改行コードなし */
            List<String> actual = StringUtils.splitReturnCode("aaabbb");
            assertNotNull(actual);
            assertEquals(actual.size(), 1);
            assertEquals(actual.get(0), "aaabbb");
        }

        { /* 改行コードのみ */
            List<String> actual;

            actual = StringUtils.splitReturnCode("\n");
            assertNotNull(actual);
            assertEquals(actual.size(), 0);

            actual = StringUtils.splitReturnCode("\r\n");
            assertNotNull(actual);
            assertEquals(actual.size(), 0);

            actual = StringUtils.splitReturnCode("\r");
            assertNotNull(actual);
            assertEquals(actual.size(), 0);
        }

        { /* 改行コードあり('\r\n') */
            List<String> actual;

            actual = StringUtils.splitReturnCode(" \r\n ");
            assertNotNull(actual);
            assertEquals(actual.size(), 2);
            assertEquals(actual.get(0), " ");
            assertEquals(actual.get(1), " ");

            actual = StringUtils.splitReturnCode("\r\naaa");
            assertNotNull(actual);
            assertEquals(actual.size(), 2);
            assertEquals(actual.get(0), "");
            assertEquals(actual.get(1), "aaa");

            actual = StringUtils.splitReturnCode("aaa\r\n");
            assertNotNull(actual);
            assertEquals(actual.size(), 1);
            assertEquals(actual.get(0), "aaa");

            actual = StringUtils.splitReturnCode("aaa\r\nbbb");
            assertNotNull(actual);
            assertEquals(actual.size(), 2);
            assertEquals(actual.get(0), "aaa");
            assertEquals(actual.get(1), "bbb");

            actual = StringUtils.splitReturnCode("aaa\r\n\r\nccc");
            assertNotNull(actual);
            assertEquals(actual.size(), 3);
            assertEquals(actual.get(0), "aaa");
            assertEquals(actual.get(1), "");
            assertEquals(actual.get(2), "ccc");
        }

        { /* 改行コードあり('\n') */
            List<String> actual;

            actual = StringUtils.splitReturnCode(" \n ");
            assertNotNull(actual);
            assertEquals(actual.size(), 2);
            assertEquals(actual.get(0), " ");
            assertEquals(actual.get(1), " ");

            actual = StringUtils.splitReturnCode("\naaa");
            assertNotNull(actual);
            assertEquals(actual.size(), 2);
            assertEquals(actual.get(0), "");
            assertEquals(actual.get(1), "aaa");

            actual = StringUtils.splitReturnCode("aaa\n");
            assertNotNull(actual);
            assertEquals(actual.size(), 1);
            assertEquals(actual.get(0), "aaa");

            actual = StringUtils.splitReturnCode("aaa\nbbb");
            assertNotNull(actual);
            assertEquals(actual.size(), 2);
            assertEquals(actual.get(0), "aaa");
            assertEquals(actual.get(1), "bbb");

            actual = StringUtils.splitReturnCode("aaa\n\nccc");
            assertNotNull(actual);
            assertEquals(actual.size(), 3);
            assertEquals(actual.get(0), "aaa");
            assertEquals(actual.get(1), "");
            assertEquals(actual.get(2), "ccc");
        }

        { /* 改行コードあり('\r') */
            List<String> actual;

            actual = StringUtils.splitReturnCode(" \r ");
            assertNotNull(actual);
            assertEquals(actual.size(), 2);
            assertEquals(actual.get(0), " ");
            assertEquals(actual.get(1), " ");

            actual = StringUtils.splitReturnCode("\raaa");
            assertNotNull(actual);
            assertEquals(actual.size(), 2);
            assertEquals(actual.get(0), "");
            assertEquals(actual.get(1), "aaa");

            actual = StringUtils.splitReturnCode("aaa\r");
            assertNotNull(actual);
            assertEquals(actual.size(), 1);
            assertEquals(actual.get(0), "aaa");

            actual = StringUtils.splitReturnCode("aaa\rbbb");
            assertNotNull(actual);
            assertEquals(actual.size(), 2);
            assertEquals(actual.get(0), "aaa");
            assertEquals(actual.get(1), "bbb");

            actual = StringUtils.splitReturnCode("aaa\r\rccc");
            assertNotNull(actual);
            assertEquals(actual.size(), 3);
            assertEquals(actual.get(0), "aaa");
            assertEquals(actual.get(1), "");
            assertEquals(actual.get(2), "ccc");
        }

        { /* 改行コードあり */
            List<String> actual;

            actual = StringUtils.splitReturnCode(" \r\n\n\r ");
            assertNotNull(actual);
            assertEquals(actual.size(), 4);
            assertEquals(actual.get(0), " ");
            assertEquals(actual.get(1), "");
            assertEquals(actual.get(2), "");
            assertEquals(actual.get(3), " ");

            actual = StringUtils.splitReturnCode("aaa\r\nbbb\nccc\rddd");
            assertNotNull(actual);
            assertEquals(actual.size(), 4);
            assertEquals(actual.get(0), "aaa");
            assertEquals(actual.get(1), "bbb");
            assertEquals(actual.get(2), "ccc");
            assertEquals(actual.get(3), "ddd");
        }
    }

    @Test
    public void stripTest() throws Exception {
        assertNull(StringUtils.strip(null, null, 0));
        assertEquals(StringUtils.strip("123.456789", ".", 1), "123.45678");
        assertEquals(StringUtils.strip("123.456789", ".", 6), "123.");
        assertEquals(StringUtils.strip("123.456789", ".", 7), "123");
        assertThrows(StringIndexOutOfBoundsException.class, () -> {
            StringUtils.strip("123.456789", ".", 8);
        });
        assertEquals(StringUtils.strip("123.456789", ".", -1), "23.456789");
        assertEquals(StringUtils.strip("123.456789", ".", -3), ".456789");
        assertEquals(StringUtils.strip("123.456789", ".", -4), "456789");
        assertThrows(StringIndexOutOfBoundsException.class, () -> {
            assertEquals(StringUtils.strip("123.456789", ".", -5), ".456789");
        });
    }

    @Test
    public void camelToUpperSnakeTest() throws Exception {
        assertNull(StringUtils.camelToUpperSnake(null));
        assertEquals(StringUtils.camelToUpperSnake(""), "");
        assertEquals(StringUtils.camelToUpperSnake(" "), " ");
        assertEquals(StringUtils.camelToUpperSnake("　"), "　");
        assertEquals(StringUtils.camelToUpperSnake("a"), "a");
        assertEquals(StringUtils.camelToUpperSnake("A"), "A");
        assertEquals(StringUtils.camelToUpperSnake("1"), "1");
        assertEquals(StringUtils.camelToUpperSnake("あ"), "あ");

        assertEquals(StringUtils.camelToUpperSnake("abc"), "ABC");
        assertEquals(StringUtils.camelToUpperSnake("ABC"), "A_B_C");
        assertEquals(StringUtils.camelToUpperSnake("Abc"), "ABC");
        assertEquals(StringUtils.camelToUpperSnake("aBc"), "A_BC");
        assertEquals(StringUtils.camelToUpperSnake("abC"), "AB_C");

        assertEquals(StringUtils.camelToUpperSnake("ABC_DEF"), "ABC_DEF");
        assertEquals(StringUtils.camelToUpperSnake("ABC-DEF"), "A_B_C-_D_E_F");
        assertEquals(StringUtils.camelToUpperSnake("Abc_Def"), "ABC_DEF");
        assertEquals(StringUtils.camelToUpperSnake("abc_def"), "ABC_DEF");
        assertEquals(StringUtils.camelToUpperSnake("Abc-Def"), "ABC-_DEF");
        assertEquals(StringUtils.camelToUpperSnake("abc-def"), "ABC-DEF");

        assertEquals(StringUtils.camelToUpperSnake("abcDef"), "ABC_DEF");
        assertEquals(StringUtils.camelToUpperSnake("AbcDef"), "ABC_DEF");
    }

    @Test
    public void camelToLowerSnakeTest() throws Exception {
        assertNull(StringUtils.camelToLowerSnake(null));
        assertEquals(StringUtils.camelToLowerSnake(""), "");
        assertEquals(StringUtils.camelToLowerSnake(" "), " ");
        assertEquals(StringUtils.camelToLowerSnake("　"), "　");
        assertEquals(StringUtils.camelToLowerSnake("a"), "a");
        assertEquals(StringUtils.camelToLowerSnake("A"), "A");
        assertEquals(StringUtils.camelToLowerSnake("1"), "1");
        assertEquals(StringUtils.camelToLowerSnake("あ"), "あ");

        assertEquals(StringUtils.camelToLowerSnake("abc"), "abc");
        assertEquals(StringUtils.camelToLowerSnake("ABC"), "a_b_c");
        assertEquals(StringUtils.camelToLowerSnake("Abc"), "abc");
        assertEquals(StringUtils.camelToLowerSnake("aBc"), "a_bc");
        assertEquals(StringUtils.camelToLowerSnake("abC"), "ab_c");

        assertEquals(StringUtils.camelToLowerSnake("ABC_DEF"), "abc_def");
        assertEquals(StringUtils.camelToLowerSnake("ABC-DEF"), "a_b_c-_d_e_f");
        assertEquals(StringUtils.camelToLowerSnake("Abc_Def"), "abc_def");
        assertEquals(StringUtils.camelToLowerSnake("abc_def"), "abc_def");
        assertEquals(StringUtils.camelToLowerSnake("Abc-Def"), "abc-_def");
        assertEquals(StringUtils.camelToLowerSnake("abc-def"), "abc-def");

        assertEquals(StringUtils.camelToLowerSnake("abcDef"), "abc_def");
        assertEquals(StringUtils.camelToLowerSnake("AbcDef"), "abc_def");
    }

    @Test
    public void snakeToUpperCamelTest() throws Exception {
    }

    @Test
    public void snakeToLowerCamelTest() throws Exception {
    }

    @Test
    public void isPhoneNumberTest() throws Exception {
        { /* null */
            assertFalse(StringUtils.isPhoneNumber(null));
        }

        { /* 空文字 */
            assertFalse(StringUtils.isPhoneNumber(""));
        }

        { /* 空白 */
            assertFalse(StringUtils.isPhoneNumber(" "));
        }

        { /* 空白 */
            assertFalse(StringUtils.isPhoneNumber("　"));
        }

        { // 9桁
            assertFalse(StringUtils.isPhoneNumber("123456789"));
        }
        { // 10桁 ハイフン不正
            assertFalse(StringUtils.isPhoneNumber("-123456789"));
        }
        { // 10桁 ハイフン不正
            assertFalse(StringUtils.isPhoneNumber("123456789-"));
        }
        { // 10桁 ハイフン不正
            assertFalse(StringUtils.isPhoneNumber("12345-6789"));
        }

        { // 10桁
            assertTrue(StringUtils.isPhoneNumber("1234567890"));
        }
        { // 11桁
            assertTrue(StringUtils.isPhoneNumber("12345678901"));
        }
        { // 00-0000-0000
            assertTrue(StringUtils.isPhoneNumber("00-0000-0000"));
        }
        { // (000)000-0000
            assertTrue(StringUtils.isPhoneNumber("(000)000-0000"));
        }
        { // (099)999-9999
            assertTrue(StringUtils.isPhoneNumber("(099)999-9999"));
        }
        { // 0000-00-0000
            assertTrue(StringUtils.isPhoneNumber("0000-00-0000"));
        }
        { // 00000-0-0000
            assertTrue(StringUtils.isPhoneNumber("00000-0-0000"));
        }
        { // (00)0000-0000
            assertTrue(StringUtils.isPhoneNumber("(00)0000-0000"));
        }
        { // (0000)00-0000
            assertTrue(StringUtils.isPhoneNumber("(0000)00-0000"));
        }
        { // (00000)0-0000
            assertTrue(StringUtils.isPhoneNumber("(00000)0-0000"));
        }
        { // 070-0000-0000
            assertTrue(StringUtils.isPhoneNumber("070-0000-0000"));
        }
        { // 080-0000-0000
            assertTrue(StringUtils.isPhoneNumber("080-0000-0000"));
        }
        { // 090-0000-0000
            assertTrue(StringUtils.isPhoneNumber("090-0000-0000"));
        }
        { // 050-0000-0000
            assertTrue(StringUtils.isPhoneNumber("050-0000-0000"));
        }
        { // 0120-000-000
            assertTrue(StringUtils.isPhoneNumber("0120-000-000"));
        }

        { // 99-9999-9999
            assertFalse(StringUtils.isPhoneNumber("99-9999-9999"));
        }
        { // 9999-99-9999
            assertFalse(StringUtils.isPhoneNumber("9999-99-9999"));
        }
        { // (99)9999-9999
            assertFalse(StringUtils.isPhoneNumber("(99)9999-9999"));
        }
        { // (9999)99-9999
            assertFalse(StringUtils.isPhoneNumber("(9999)99-9999"));
        }
        { // (99999)9-9999
            assertFalse(StringUtils.isPhoneNumber("(99999)9-9999"));
        }
        { // 070-9999-9999
            assertTrue(StringUtils.isPhoneNumber("070-9999-9999"));
        }
        { // 080-9999-9999
            assertTrue(StringUtils.isPhoneNumber("080-9999-9999"));
        }
        { // 090-9999-9999
            assertTrue(StringUtils.isPhoneNumber("090-9999-9999"));
        }
        { // 050-9999-9999
            assertTrue(StringUtils.isPhoneNumber("050-9999-9999"));
        }
        { // 0120-999-999
            assertTrue(StringUtils.isPhoneNumber("0120-999-999"));
        }
    }
}
