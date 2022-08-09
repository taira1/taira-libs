package jp.taira.libs.utils;

import com.fasterxml.jackson.dataformat.csv.CsvParser;
import com.google.common.base.CaseFormat;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.http11.filters.SavedRequestInputFilter;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 文字列ユーティリティクラス
 */
@Slf4j
public class StringUtils {

    /** 文字コード: Shift_JIS */
    public static final Charset SHIFT_JIS = Charset.forName("Shift_JIS");

    /** 文字コード: MS932 */
    public static final Charset MS932 = Charset.forName("MS932");

    /** 文字コード: EUC-JP */
    public static final Charset EUC_JP = Charset.forName("EUC-JP");

    /** 文字コード: ISO-2022-JP  */
    public static final Charset ISO_2022_JP = Charset.forName("ISO2022JP");

    /** 文字コード: ISO-8859-1  */
    public static final Charset ISO_8859_1 = StandardCharsets.ISO_8859_1;

    /** 文字コード: UTF8 */
    public static final Charset UTF_8 = StandardCharsets.UTF_8;

    /** 半角スペース */
    protected static final String HALF_SPACE = "\u0020";

    /** 全角スペース */
    protected static final String FULL_SPACE = "\u3000";

    /** 全角半角: 数字 */
    private static final Map<String, String> FULL_HALF_NUMBER;
    static {
        final Map<String, String> map = new LinkedHashMap<>();
        map.put("０", "0");
        map.put("１", "1");
        map.put("２", "2");
        map.put("３", "3");
        map.put("４", "4");
        map.put("５", "5");
        map.put("６", "6");
        map.put("７", "7");
        map.put("８", "8");
        map.put("９", "9");
        FULL_HALF_NUMBER = Collections.unmodifiableMap(map);
    }

    /** 全角半角: 大文字 */
    private static final Map<String, String> FULL_HALF_UPPER;
    static {
        final Map<String, String> map = new LinkedHashMap<>();
        map.put("Ａ", "A");
        map.put("Ｂ", "B");
        map.put("Ｃ", "C");
        map.put("Ｄ", "D");
        map.put("Ｅ", "E");
        map.put("Ｆ", "F");
        map.put("Ｇ", "G");
        map.put("Ｈ", "H");
        map.put("Ｉ", "I");
        map.put("Ｊ", "J");
        map.put("Ｋ", "K");
        map.put("Ｌ", "L");
        map.put("Ｍ", "M");
        map.put("Ｎ", "N");
        map.put("Ｏ", "O");
        map.put("Ｐ", "P");
        map.put("Ｑ", "Q");
        map.put("Ｒ", "R");
        map.put("Ｓ", "S");
        map.put("Ｔ", "T");
        map.put("Ｕ", "U");
        map.put("Ｖ", "V");
        map.put("Ｗ", "W");
        map.put("Ｘ", "X");
        map.put("Ｙ", "Y");
        map.put("Ｚ", "Z");
        FULL_HALF_UPPER = Collections.unmodifiableMap(map);
    }

    /** 全角半角: 小文字 */
    private static final Map<String, String> FULL_HALF_LOWER;
    static {
        final Map<String, String> map = new LinkedHashMap<>();
        map.put("ａ", "a");
        map.put("ｂ", "b");
        map.put("ｃ", "c");
        map.put("ｄ", "d");
        map.put("ｅ", "e");
        map.put("ｆ", "f");
        map.put("ｇ", "g");
        map.put("ｈ", "h");
        map.put("ｉ", "i");
        map.put("ｊ", "j");
        map.put("ｋ", "k");
        map.put("ｌ", "l");
        map.put("ｍ", "m");
        map.put("ｎ", "n");
        map.put("ｏ", "o");
        map.put("ｐ", "p");
        map.put("ｑ", "q");
        map.put("ｒ", "r");
        map.put("ｓ", "s");
        map.put("ｔ", "t");
        map.put("ｕ", "u");
        map.put("ｖ", "v");
        map.put("ｗ", "w");
        map.put("ｘ", "x");
        map.put("ｙ", "y");
        map.put("ｚ", "z");
        FULL_HALF_LOWER = Collections.unmodifiableMap(map);
    }

    /** 全角半角: 記号 */
    private static final Map<String, String> FULL_HALF_SYMBOL;
    static {
        final Map<String, String> map = new LinkedHashMap<>();
        map.put("！", "!");
        map.put("”",  "\"");
        map.put("＃", "#");
        map.put("＄", "$");
        map.put("￥", "\\");
        map.put("％", "%");
        map.put("＆", "&");
        map.put("’",  "'");
        map.put("（", "(");
        map.put("）", ")");
        map.put("＊", "*");
        map.put("＋", "+");
        map.put("，", ",");
        map.put("－", "-");
        map.put("．", ".");
        map.put("／", "/");
        map.put("：", ":");
        map.put("；", ";");
        map.put("＜", "<");
        map.put("＝", "=");
        map.put("＞", ">");
        map.put("？", "?");
        map.put("＠", "@");
        map.put("＾", "^");
        map.put("＿", "_");
        map.put("‘",  "`");
        map.put("｛", "{");
        map.put("｜", "|");
        map.put("｝", "}");
        map.put("。", "｡");
        map.put("「", "｢");
        map.put("」", "｣");
        map.put("、", "､");
        map.put("・", "･");
        map.put(FULL_SPACE, HALF_SPACE);
        FULL_HALF_SYMBOL = Collections.unmodifiableMap(map);
    }

    /** ひらがな */
    public static final String HIRAGANA_PATTERN = "あいうえおかがきぎくぐけげこごさざしじすずせぜそぞただちぢつづてでとどなにぬねのはばぱひびぴふぶぷへべぺほぼぽまみむめもゃやゅゆょよらりるれろわをんゔぁぃぅぇぉっ";

    /** 全角カタカナ */
    public static final String KATAKANA_PATTERN = "アイウエオカガキギクグケゲコゴサザシジスズセゼソゾタダチヂツヅテデトドナニヌネノハバパヒビピフブプヘベペホボポマミムメモャヤュユョヨラリルレロワヲンヴァィゥェォッ";

    /** 半角カタカナ */
    public static final String HALF_KATAKANA_PATTERN = "ｱｲｳｴｵｶｶﾞｷｷﾞｸｸﾞｹｹﾞｺｺﾞｻｻﾞｼｼﾞｽｽﾞｾｾﾞｿｿﾞﾀﾀﾞﾁﾁﾞﾂﾂﾞﾃﾃﾞﾄﾄﾞﾅﾆﾇﾈﾉﾊﾊﾞﾊﾟﾋﾋﾞﾋﾟﾌﾌﾞﾌﾟﾍﾍﾞﾍﾟﾎﾎﾞﾎﾟﾏﾐﾑﾒﾓｬﾔｭﾕｮﾖﾗﾘﾙﾚﾛﾜｦﾝｳﾞｧｨｩｪｫｯ";

    /** メールアドレス */
    public static final String EMAIL_PATTERN = "^(?:[\\w!#$%&'*+\\/\\-=?^`{|}~]+(?:\\.[\\w!#$%&'*+\\/\\-=?^`{|}~]+)*)@(?:[a-zA-Z]+[a-zA-Z0-9-]*[a-zA-Z0-9](?:\\.[a-zA-Z]+[a-zA-Z0-9-]*[a-zA-Z0-9])*(?:\\.[a-zA-Z0-9]+))$";

    private StringUtils() {
        throw new IllegalAccessError("Constants class.");
    }

    /**
     * nullか空文字かを判断する。
     *
     * @param target 対象文字列
     * @return nullか空文字である場合はtrue、そうでない場合はfalse。
     */
    public static boolean isEmpty(final String target) {
        return target == null || target.length() == 0;
    }

    /**
     * 全角スペースか半角スペースかを判断する。
     *
     * @param target 対象文字列
     * @return 全角スペースか半角スペースである場合はtrue、そうでない場合はfalse。
     */
    public static boolean isBlank(final String target) {
        String str = target == null ? null : target.replaceAll("[" + HALF_SPACE + FULL_SPACE + "]", "");

        return isEmpty(str);
    }

    /**
     * この文字列が、指定された接頭辞で始まるかどうかを判定します。
     *
     * @param target 対象文字列
     * @param prefix 接頭辞
     * @return 引数によって表される文字シーケンスが、この文字列によって表される文字シーケンスの接頭辞である場合はtrue、そうでない場合はfalse。
     */
    public static boolean startsWith(final String target, final String prefix) {
        return target != null && target.startsWith(prefix);
    }

    /**
     * 大文字と小文字の区別を無視して、この文字列が、指定された接頭辞で始まるかどうかを判定します。
     *
     * @param target 対象文字列
     * @param prefix 接頭辞
     * @return 引数によって表される文字シーケンスが、この文字列によって表される文字シーケンスの接頭辞である場合はtrue、そうでない場合はfalse。
     */
    public static boolean startsWithIgnoreCase(final String target, final String prefix) {
        return target != null && target.toLowerCase().startsWith(prefix != null ? prefix.toLowerCase() : prefix);
    }

    /**
     * この文字列が、指定された接尾辞で終るかどうかを判定します。
     *
     * @param target 対象文字列
     * @param suffix 接尾辞
     * @return 引数によって表される文字シーケンスが、このオブジェクトによって表される文字シーケンスの接尾辞である場合はtrue、そうでない場合はfalse。
     */
    public static boolean endsWith(final String target, final String suffix) {
        return target != null && target.endsWith(suffix);
    }

    /**
     * 大文字と小文字の区別を無視して、この文字列が、指定された接尾辞で終るかどうかを判定します。
     *
     * @param target 対象文字列
     * @param suffix 接尾辞
     * @return 引数によって表される文字シーケンスが、このオブジェクトによって表される文字シーケンスの接尾辞である場合はtrue、そうでない場合はfalse。
     */
    public static boolean endsWithIgnoreCase(final String target, final String suffix) {
        return target != null && target.toLowerCase().endsWith(suffix != null ? suffix.toLowerCase(): suffix);
    }

    /**
     * 指定された文字(列)が先頭および末尾に現れる箇所をすべて削除します。
     *
     * @param target 対象文字列
     * @param trimString 削除する文字(列)
     * @return 削除された文字列
     */
    public static String trim(final String target, final String trimString) {
        if (target == null) {
            return null;
        }

        // startWith, endWithの処理をする前に、それぞれが削除対象か否かを取得する
        boolean startsWith = target.startsWith(trimString);
        boolean endsWith = target.endsWith(trimString);

        String result = target;
        if (startsWith) {
            while (result.startsWith(trimString)) {
                result = result.substring(trimString.length());
            }
        }

        if (endsWith && result.length() >= trimString.length()) {
            while (result.endsWith(trimString)) {
                result = result.substring(0, result.length() - trimString.length());
            }
        }

        return result;
    }

    /**
     * 先頭および末尾にある空白文字(全角、半角)をすべて削除します。
     *
     * @param target 対象文字列
     * @return 削除された文字列
     */
    public static String trim(final String target) {
        if (target == null) {
            return null;
        }

        final String pattern = HALF_SPACE + FULL_SPACE + "\t\u00a0\u1680\u180e\u2000\u200a\u202f\u205f";

        return target.replaceAll("^[" + pattern + "]+", "").replaceAll("[" + pattern + "]+$", "");
    }

    /**
     * ASCIIのみかを判断する。
     *
     * @param target 対象文字列
     * @return ASCIIのみである場合はtrue、そうでない場合はfalse。
     */
    public static boolean isAscii(final String target) {
        return !isEmpty(target) && StandardCharsets.US_ASCII.newEncoder().canEncode(target);
    }

    /**
     * 英字のみかを判断する。
     *
     * @param target 対象文字列
     * @return 英字のみである場合はtrue、そうでない場合はfalse。
     */
    public static boolean isAlpha(final String target) {
        return !isEmpty(target) && target.matches("^[a-zA-Z]+$");
    }

    /**
     * 半角英数字のみかを判断する。
     *
     * @param target 対象文字列
     * @return 半角英数字のみである場合はtrue、そうでない場合はfalse。
     */
    public static boolean isHalfAlphaNumeric(final String target) {
        return !isEmpty(target) && target.matches("^[0-9a-zA-Z]+$");
    }

    /**
     * ひらがなのみかを判断する。
     *
     * @param target 対象文字列
     * @param containsSpace trueの場合は空白文字を含める。falseの場合は空白文字を含めない。
     * @return ひらがなのみである場合はtrue、そうでない場合はfalse。
     */
    public static boolean isHiragana(final String target, final boolean containsSpace) {
        if (isBlank(target)) {
            return false;
        }

        final String pattern = HIRAGANA_PATTERN + (containsSpace ? HALF_SPACE + FULL_SPACE : "");

        return target.matches("^[" + pattern + "]+$");
    }

    /**
     * ひらがなのみかを判断する。
     *
     * @param target 対象文字列
     * @return ひらがなのみである場合はtrue、そうでない場合はfalse。
     */
    public static boolean isHiragana(final String target) {
        return isHiragana(target, false);
    }

    /**
     * カタカナのみかを判断する。
     *
     * @param target 対象文字列
     * @param containsSpace trueの場合は空白文字を含める。falseの場合は空白文字を含めない。
     * @return カタカナのみである場合はtrue、そうでない場合はfalse。
     */
    public static boolean isKatakana(final String target, final boolean containsSpace) {
        if (isBlank(target)) {
            return false;
        }

        final String pattern = KATAKANA_PATTERN + (containsSpace ? HALF_SPACE + FULL_SPACE : "");

        return target.matches("^[" + pattern + "]+$");
    }

    /**
     * カタカナのみかを判断する。
     *
     * @param target 対象文字列
     * @return カタカナのみである場合はtrue、そうでない場合はfalse。
     */
    public static boolean isKatakana(final String target) {
        return isKatakana(target, false);
    }

    /**
     * 半角カタカナのみかを判断する。
     *
     * @param target 対象文字列
     * @param containsSpace trueの場合は空白文字を含める。falseの場合は空白文字を含めない。
     * @return カタカナのみである場合はtrue、そうでない場合はfalse。
     */
    public static boolean isHalfKatakana(final String target, final boolean containsSpace) {
        if (isBlank(target)) {
            return false;
        }

        final String pattern = HALF_KATAKANA_PATTERN + (containsSpace ? HALF_SPACE + FULL_SPACE : "");

        return target.matches("^[" + pattern + "]+$");
    }

    /**
     * 半角カタカナのみかを判断する。
     *
     * @param target 対象文字列
     * @return カタカナのみである場合はtrue、そうでない場合はfalse。
     */
    public static boolean isHalfKatakana(final String target) {
        return isHalfKatakana(target, false);
    }

    /**
     * 全角文字(数字)を半角文字(数字)に変換する。
     *
     * @param target 対象文字列
     * @return 変換された文字列
     */
    public static String toHalfOfNumber(final String target) {
        return toHalfString(target, FULL_HALF_NUMBER);
    }

    /**
     * 全角文字(英字)を半角文字(英字)に変換する。
     *
     * @param target 対象文字列
     * @return 変換された文字列
     */
    public static String toHalfOfAlpha(final String target) {
        Map<String, String> map = new HashMap<String, String>();
        map.putAll(FULL_HALF_UPPER);
        map.putAll(FULL_HALF_LOWER);

        return toHalfString(target, map);
    }

    /**
     * 全角文字(英字,大文字)を半角文字(英字,大文字)に変換する。
     *
     * @param target 対象文字列
     * @return 変換された文字列
     */
    public static String toHalfOfAlphaUpper(final String target) {
        return toHalfString(target, FULL_HALF_UPPER);
    }

    /**
     * 全角文字(英字,小文字)を半角文字(英字,小文字)に変換する。
     *
     * @param target 対象文字列
     * @return 変換された文字列
     */
    public static String toHalfOfAlphaLower(final String target) {
        return toHalfString(target, FULL_HALF_LOWER);
    }

    /**
     * 全角文字(記号)を半角文字(記号)に変換する。
     *
     * @param target 対象文字列
     * @return 変換された文字列
     */
    public static String toHalfOfSymbol(final String target) {
        return toHalfString(target, FULL_HALF_SYMBOL);
    }

    /**
     * 全角文字(ASCII)を半角文字(ASCII)に変換する。
     *
     * @param target 対象文字列
     * @return 変換された文字列
     */
    public static String toHalfOfAscii(final String target) {
        Map<String, String> map = new HashMap<String, String>();
        map.putAll(FULL_HALF_NUMBER);
        map.putAll(FULL_HALF_UPPER);
        map.putAll(FULL_HALF_LOWER);
        map.putAll(FULL_HALF_SYMBOL);

        return toHalfString(target, map);
    }

    /**
     * 半角文字に変換する。
     *
     * @param target 対象文字列
     * @param convertMap 文字列リストのセット
     * @return 変換された文字列
     */
    private static String toHalfString(final String target, final Map<String, String> convertMap) {
        if (isEmpty(target)) {
            return target;
        }

        String result = target;
        for (Map.Entry<String, String> map : convertMap.entrySet()) {
            result = result.replace(map.getKey(), map.getValue());
        }

        return result;
    }

    /**
     * 半角文字(数字)を全角文字(数字)に変換する。
     *
     * @param target 対象文字列
     * @return 変換された文字列
     */
    public static String toFullOfNumber(final String target) {
        return toFullString(target, FULL_HALF_NUMBER);
    }

    /**
     * 半角文字(英字)を全角文字(英字)に変換する。
     *
     * @param target 対象文字列
     * @return 変換された文字列
     */
    public static String toFullOfAlpha(final String target) {
        Map<String, String> map = new HashMap<String, String>();
        map.putAll(FULL_HALF_UPPER);
        map.putAll(FULL_HALF_LOWER);

        return toFullString(target, map);
    }

    /**
     * 半角文字(英字,大文字)を全角文字(英字,大文字)に変換する。
     *
     * @param target 対象文字列
     * @return 変換された文字列
     */
    public static String toFullOfAlphaUpper(final String target) {
        return toFullString(target, FULL_HALF_UPPER);
    }

    /**
     * 半角文字(英字,小文字)を全角文字(英字,小文字)に変換する。
     *
     * @param target 対象文字列
     * @return 変換された文字列
     */
    public static String toFullOfAlphaLower(final String target) {
        return toFullString(target, FULL_HALF_LOWER);
    }

    /**
     * 半角文字(記号)を全角文字(記号)に変換する。
     *
     * @param target 対象文字列
     * @return 変換された文字列
     */
    public static String toFullOfSymbol(final String target) {
        return toFullString(target, FULL_HALF_SYMBOL);
    }

    /**
     * 半角文字(ASCII)を全角文字(ASCII)に変換する。
     *
     * @param target 対象文字列
     * @return 変換された文字列
     */
    public static String toFullOfAscii(final String target) {
        Map<String, String> map = new HashMap<String, String>();
        map.putAll(FULL_HALF_NUMBER);
        map.putAll(FULL_HALF_UPPER);
        map.putAll(FULL_HALF_LOWER);
        map.putAll(FULL_HALF_SYMBOL);

        return toFullString(target, map);
    }

    /**
     * 全角文字に変換する。
     *
     * @param target 対象文字列
     * @param convertMap 文字列リストのセット
     * @return 変換された文字列
     */
    private static String toFullString(final String target, final Map<String, String> convertMap) {
        if (isEmpty(target)) {
            return target;
        }

        String result = target;
        for (Map.Entry<String, String> map : convertMap.entrySet()) {
            result = result.replace(map.getValue(), map.getKey());
        }

        return result;
    }

    /**
     * URL形式かを判断する。
     *
     * @param target 対象文字列
     * @return URL形式である場合はtrue、そうでない場合はfalse。
     */
    public static boolean isUrl(final String target) {
        if (isEmpty(target)) {
            return false;
        }

        try {
            @SuppressWarnings("unused")
            final URL url = new URL(target);
        } catch (MalformedURLException e) {
            log.debug(e.getMessage(), e);
        }

        return false;
    }

    public static boolean isEmail(final String target) {
        if (isEmpty(target) || !target.contains("@")) {
            return false;
        }

        // メールアドレス全体の長さの最大値 = 254文字
        if (target.length() > 254) {
            return false;
        }

        // ローカル部の長さの最大値 = 64文字
        if (target.split("@", -1)[0].length() > 64) {
            return false;
        }

        // ドメインの長さの最大値 = 253文字
        if (target.split("@", -1)[1].length() > 253) {
            return false;
        }

        return target.matches(EMAIL_PATTERN);
    }

    /**
     * 半角スペースで埋める。
     *
     * @param target 対象文字列
     * @param number 埋める個数を指定する。0の場合は処理しない。正の場合は先頭に埋める。負の場合は末尾に埋める。
     * @param fixed trueの場合は固定長に補正する。falseの場合は固定長に補正しない。
     * @return 半角で埋められた文字列
     */
    public static String fillSpace(final String target, final int number, final boolean fixed) {
        if (number == 0) {
            return target;
        }

        if (fixed && target != null && target.length() >= Math.abs(number)) {
            return target;
        }

        final String result = target == null ? "" : target;
        final int count =number + (fixed ? 0 : (result.length() * (number > 0 ? 1 : -1)));

        return String.format("%" + count + "s", result);
    }

    /**
     * 半角スペースで埋める。
     *
     * @param target 対象文字列
     * @param number 埋める個数を指定する。0の場合は処理しない。正の場合は先頭に埋める。負の場合は末尾に埋める。
     * @return 半角スペースで埋められた文字列
     */
    public static String fillSpace(final String target, final int number) {
        return fillSpace(target, number, false);
    }

    public static String fillZero(final String target, final int number, final boolean fixed) {
        String result = fillSpace(target, number, fixed);
        final int targetLength = target != null ? target.length() : 0;
        final int count = Math.max(Math.abs(fixed ? Math.abs(number) - targetLength: number), 0);

        return result != null ?
                result.replaceAll((number > 0 ? "^" : "") + " {" + count + "}" + (number < 0 ? "$" : ""), getNotNullString(repeat("0", count), "")) : result;
    }

    /**
     * ゼロで埋める。
     *
     * @param target 対象文字列
     * @param number 埋める個数を指定する。0の場合は処理しない。正の場合は先頭に埋める。負の場合は末尾に埋める。
     * @return ゼロで埋められた文字列
     */
    public static String fillZero(final String target, final int number) {
        return fillZero(target, number, false);
    }

    /**
     * 文字のエンコーディングを行う。
     *
     * @param target 対象バイト配列
     * @param sourceCharset チェックする文字コード
     * @param targetCharset チェックする文字コード
     * @return 文字エンコーディングしたバイト配列
     */
    public static byte[] encodeCharset(final byte[] target, final Charset sourceCharset, final Charset targetCharset) {
        final ByteBuffer inputBuffer = ByteBuffer.wrap(target);
        final CharBuffer charBuffer = sourceCharset.decode(inputBuffer);
        final ByteBuffer outputBuffer = targetCharset.encode(charBuffer);

        return outputBuffer.array();
    }

    /**
     * 文字コードをチェックする。
     *
     * @param target 対象文字列
     * @param charset チェックする文字コード
     * @return 指定した文字コードである場合はtrue、そうでない場合はfalse
     */
    private static boolean checkCharacterCode(final String target, final Charset charset) {
        if (isEmpty(target)) {
            return true;
        }

        return charset.newEncoder().canEncode(target);
    }

    /**
     * 'UTF-8'かを判断する。
     *
     * @param target 対象文字列
     * @return 文字コードが'UTF-8'である場合はtrue、そうでない場合はfalse。
     */
    public static boolean isUTF8(final String target) {
        return checkCharacterCode(target, UTF_8);
    }

    /**
     * 文字列を改行コードで分割し、文字列リストを取得する。
     *
     * @param target 対象文字列
     * @return 行ごとに格納された文字列リスト
     */
    public static List<String> splitReturnCode(final String target) {
        if (isEmpty(target)) {
            return null;
        }

        final String[] strs = target.split("(?:\r\n|\r|\n)");

        return new LinkedList<>(Arrays.asList(strs));
    }

    /**
     * 検索文字列を開始インデックスとして、先頭、または、末尾から、指定した文字数を削除する。
     * @param target 対象文字列
     * @param search 検索文字列
     * @param length 削除する文字数
     * @return 指定した文字数を削除した文字列
     */
    public static String strip(final String target, final String search, final int length) {
        if (target == null || isEmpty(search)) {
            return target;
        }

        final int index = target.indexOf(search);
        if (index == -1) {
            return target;
        }

        if (length < 0) {
            if (Math.abs(length) <= index + 1) {
                return target.substring(Math.abs(length));
            } else {
                throw new StringIndexOutOfBoundsException();
            }
        }

        final int targetLength = target.length();

        return target.substring(0, index) + target.substring(index, targetLength - Math.abs(length));
    }

    public static String camelToUpperSnake(final String target) {
        if (isEmpty(target) || target.length() == 1) {
            return target;
        }

        if (target.contains("_")) {
            return target.toUpperCase();
        }

        return CaseFormat.UPPER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, target.substring(0, 1).toUpperCase() + target.substring(1));
    }

    /**
     * キャメルケースを(ローワー)スネークケースに変換する。
     *
     * @param target 対象文字列
     * @return 変換された文字列
     */
    public static String camelToLowerSnake(final String target) {
        if (StringUtils.isEmpty(target) || target.length() == 1) {
            return target;
        }

        if (target.contains("_")) {
            return target.toLowerCase();
        }

        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, target.substring(0, 1).toUpperCase() + target.substring(1));
    }

    /**
     * スネークケースを(アッパー)キャメルケースに変換する。
     *
     * @param target 対象文字列
     * @return 変換された文字列
     */
    public static String snakeToUpperCamel(final String target) {
        if (StringUtils.isEmpty(target)) {
            return target;
        }

        if (!target.contains("_")) {
            if (target.matches("^[a-z0-9]+$")) {
                return target.toUpperCase();
            }
            if (target.matches("^[a-z].*")) {
                return target.substring(0, 1).toUpperCase() + target.substring(1, target.length());
            }
            
            return target;
        }

        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, target.toLowerCase());
    }

    /**
     * スネークケースを(ローワー)キャメルケースに変換する。
     *
     * @param target 対象文字列
     * @return 変換された文字列
     */
    public static String snakeToLowerCamel(final String target) {
        if (StringUtils.isEmpty(target)) {
            return target;
        }

        if (!target.contains("_")) {
            if (target.matches("^[A-Z0-9]+$")) {
                return target.toLowerCase();
            }
            if (target.matches("^[A-Z].*")) {
                return target.substring(0, 1).toLowerCase() + target.substring(1, target.length());
            }

            return target;
        }

        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, target.toLowerCase());
    }

    /**
     * 電話番号形式かを判断する。
     *
     * @param target 対象文字列
     * @return 電話番号形式である場合はtrue、そうでない場合はfalse。
     */
    public static boolean isPhoneNumber(final String target) {
        if (isBlank(target)) {
            return false;
        }

        /* 10桁,11桁の数字のみ */
        return Pattern.compile("^\\d{10,11}$").matcher(target).find() ||
                /* 固定電話 '00-0000-0000' */
                Pattern.compile("^0\\d-\\d{4}-\\d{4}$").matcher(target).find() ||
                /* 固定電話 '0000-00-0000' */
                Pattern.compile("^0\\d{3}-\\d{2}-\\d{4}$").matcher(target).find() ||
                /* 固定電話 '00000-0-0000' */
                Pattern.compile("^0\\d{4}-\\d-\\d{4}$").matcher(target).find() ||
                /* 固定電話 '000-000-0000' */
                Pattern.compile("^0\\d{2}-\\d{3}-\\d{4}$").matcher(target).find() ||
                /* 固定電話 '(000)000-0000' */
                Pattern.compile("^\\(0\\d{2}\\)\\d{3}-\\d{4}$").matcher(target).find() ||
                /* 固定電話 '(00)0000-0000' */
                Pattern.compile("^\\(0\\d\\)\\d{4}-\\d{4}$").matcher(target).find() ||
                /* 固定電話 '(0000)00-0000' */
                Pattern.compile("^\\(0\\d{3}\\)\\d{2}-\\d{4}$").matcher(target).find() ||
                /* 固定電話 '(00000)0-0000' */
                Pattern.compile("^\\(0\\d{4}\\)\\d-\\d{4}$").matcher(target).find() ||
                /* 携帯電話 '070,080,090-0000-0000' */
                Pattern.compile("^(070|080|090)-\\d{4}-\\d{4}$").matcher(target).find() ||
                /* IP電話 '050-0000-0000' */
                Pattern.compile("^050-\\d{4}-\\d{4}$").matcher(target).find() ||
                /* フリーダイヤル */
                Pattern.compile("^0120-\\d{3}-\\d{3}$").matcher(target).find() ||
                /* フリーダイヤル */
                Pattern.compile("^0800-\\d{3}-\\d{3}$").matcher(target).find();
    }

    /**
     * 文字列を指定した回数分繰り返す。
     *
     * @param target 対象文字列
     * @param count 繰り返す回数(自然数)
     * @return 繰り返しされた文字列
     */
    public static String repeat(final String target, final int count) {
        if (target == null || count <= 0) {
            return null;
        }

        return IntStream.range(0, count).mapToObj(i -> target).collect(Collectors.joining());
    }


    /**
     * nullでない文字列を取得する。
     *
     * @param args 対象リスト
     * @return 対象リストのnullでない最初の文字列
     */
    public static String getNotNullString(String ... args) {
        if (args.length == 0) {
            return null;
        }

        for (final String value : args) {
            if (value != null) {
                return value;
            }
        }

        return null;
    }
}
