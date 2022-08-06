package jp.taira.libs.utils;

import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

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
}
