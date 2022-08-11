package jp.taira.libs.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.NumberFormat;
import java.util.Arrays;

/**
 * 数値ユーティリティクラス
 */
public class NumberUtils {

    private NumberUtils() {
        throw new IllegalAccessError("Constants class.");
    }

    /**
     * 整数のみかを判断する
     *
     * @param target 対象文字列
     * @return 整数である場合はtrue、そうでない場合はfalse。
     */
    public static boolean isInteger(final String target) {
        return !StringUtils.isEmpty(target) && target.replaceAll(".*,.*", "").matches("^[\\-+]?[0-9]+$");
    }

    /**
     * 少数のみかを判断する
     *
     * @param target 対象文字列
     * @return 少数である場合はtrue、そうでない場合はfalse。
     */
    public static boolean isDecimal(final String target) {
        return !StringUtils.isEmpty(target) && target.replaceAll(".*,.*", "").matches("^[\\-\\+]?[0-9]+\\.[0-9]+$");
    }

    /**
     * 整数か小数かを判断する。
     *
     * @param target 対象文字列
     * @return 整数か小数である場合はtrue、そうでない場合はfalse。
     */
    public static boolean isNumber(final String target) {
        return isInteger(target) || isDecimal(target);
    }

    /**
     * 桁数を取得する。
     *
     * @param value 対象
     * @return 桁数
     */
    public static int getDigitLength(final BigInteger value) {
        final int length = String.valueOf(value).replaceAll(",", "").length();
        if (BigInteger.ZERO.compareTo(value) > 0) {
            return length - 1;
        }

        return length;
    }

    /**
     * 桁数を取得する。
     *
     * @param value 対象
     * @return 桁数
     */
    public static int getDigitLength(final Integer value) {
        return getDigitLength(new BigInteger(String.valueOf(value)));
    }

    /**
     * 最大値を取得する。
     *
     * @param args 対象リスト
     * @return 対象リストの最大値のみ
     */
    public static BigInteger getMax(BigInteger ... args) {
        if (args.length == 0) {
            return null;
        }

        BigInteger maxValue = args[0];
        for (final BigInteger value : args) {
            maxValue = value.max(maxValue);
        }

        return maxValue;
    }

    /**
     * 最大値を取得する。
     *
     * @param args 対象リスト
     * @return 対象リストの最大値のみ
     */
    public static Integer getMax(Integer ... args) {
        if (args.length == 0) {
            return null;
        }
        return Arrays.stream(args).reduce(Integer::max).get();
    }

    /**
     * 最小値を取得する。
     *
     * @param args 対象リスト
     * @return 対象リストの最小値のみ
     */
    public static BigInteger getMin(BigInteger ... args) {
        if (args.length == 0) {
            return null;
        }

        BigInteger minValue = args[0];
        for (final BigInteger value : args) {
            minValue = value.min(minValue);
        }

        return minValue;
    }

    /**
     * 最小値を取得する。
     *
     * @param args 対象リスト
     * @return 対象リストの最小値のみ
     */
    public static Integer getMin(Integer ... args) {
        if (args.length == 0) {
            return null;
        }

        return Arrays.stream(args).reduce(Integer::min).get();
    }

    /**
     * Longオブジェクトに変換する。
     *
     * @param target 対象のObjectオブジェクト
     * @param defaultValue 変換に失敗した場合のデフォルト値
     * @return 変換したLongオブジェクトを返す。変換に失敗した場合は、デフォルト値を返す。
     */
    public static Long parseLong(final Object target, final Long defaultValue) {
        try {
            final String value = target.toString();

            return StringUtils.isBlank(value) || !NumberUtils.isNumber(value) ? defaultValue : Long.valueOf(value.replaceAll("\\.0+", ""));
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * Longオブジェクトに変換する。
     *
     * @param target 対象のObjectオブジェクト
     * @return 変換したLongオブジェクトを返す。変換に失敗した場合は、nullを返す。
     */
    public static Long parseLong(final Object target) {
        final String value = String.valueOf(target);
        if (value != null && isNumber(value) && isDecimal(value) && !value.matches("^.*\\.0+$")) {
            throw new NumberFormatException();
        }

        return parseLong(target, null);
    }

    /**
     * BigIntegerオブジェクトに変換する。
     *
     * @param target 対象のObjectオブジェクト
     * @param defaultValue 変換に失敗した場合のデフォルト値
     * @return 変換したBigIntegerオブジェクトを返す。変換に失敗した場合は、デフォルト値を返す。
     */
    public static BigInteger parseBigInteger(final Object target, final BigInteger defaultValue) {
        try {
            final Long value = parseLong(target, defaultValue != null ? Long.valueOf(defaultValue.toString()) : null);

            return value != null ? BigInteger.valueOf(value) : defaultValue;
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * BigIntegerオブジェクトに変換する。
     *
     * @param target 対象のObjectオブジェクト
     * @return 変換したBigIntegerオブジェクトを返す。変換に失敗した場合は、nullを返す。
     */
    public static BigInteger parseBigInteger(final Object target) {
        final Long value = parseLong(target);

        return value != null ? BigInteger.valueOf(value) : null;
    }

    /**
     * Integerオブジェクトに変換する。
     *
     * @param target 対象のObjectオブジェクト
     * @param defaultValue 変換に失敗した場合のデフォルト値
     * @return 変換したIntegerオブジェクトを返す。変換に失敗した場合は、デフォルト値を返す。
     */
    public static Integer parseInteger(final Object target, final Integer defaultValue) {
        try {
            final Long value = parseLong(target, Long.valueOf(defaultValue));

            return value != null ? Integer.valueOf(value.toString()) : defaultValue;
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * Integerオブジェクトに変換する。
     *
     * @param target 対象のObjectオブジェクト
     * @return 変換したIntegerオブジェクトを返す。変換に失敗した場合は、nullを返す。
     */
    public static Integer parseInteger(final Object target) {
        final Long value = parseLong(target);

        return value != null ? Integer.valueOf(value.toString()) : null;
    }

    /**
     * Doubleオブジェクトに変換する。
     *
     * @param target 対象のObjectオブジェクト
     * @param defaultValue 変換に失敗した場合のデフォルト値
     * @return 変換したDoubleオブジェクトを返す。変換に失敗した場合は、デフォルト値を返す。
     */
    public static Double parseDouble(final Object target, final Double defaultValue) {
        try {
            return target != null ? Double.valueOf(target.toString()) : defaultValue;
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * Doubleオブジェクトに変換する。
     *
     * @param target 対象のObjectオブジェクト
     * @return 変換したDoubleオブジェクトを返す。変換に失敗した場合は、nullを返す。
     */
    public static Double parseDouble(final Object target) {
        return parseDouble(target, null);
    }

    /**
     * Floatオブジェクトに変換する。
     *
     * @param target 対象のObjectオブジェクト
     * @param defaultValue 変換に失敗した場合のデフォルト値
     * @return 変換したFloatオブジェクトを返す。変換に失敗した場合は、デフォルト値を返す。
     */
    public static Float parseFloat(final Object target, final Float defaultValue) {
        try {
            return target != null ? Float.valueOf(target.toString()) : defaultValue;
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * Floatオブジェクトに変換する。
     *
     * @param target 対象のObjectオブジェクト
     * @return 変換したFloatオブジェクトを返す。変換に失敗した場合は、nullを返す。
     */
    public static Float parseFloat(final Object target) {
        return parseFloat(target, null);
    }

    /**
     * 3桁区切りの文字列を取得する。
     *
     * @param value 対象
     * @return 3桁区切りに変換した文字列
     */
    public static String toFormatString(final BigInteger value) {
        NumberFormat nf = NumberFormat.getInstance();

        return nf.format(value);
    }

    /**
     * 3桁区切りの文字列を取得する。
     *
     * @param value 対象
     * @return 3桁区切りに変換した文字列
     */
    public static String toFormatString(final Long value) {
        return toFormatString(new BigInteger(String.valueOf(value)));
    }

    /**
     * 3桁区切りの文字列を取得する。
     *
     * @param value 対象
     * @return 3桁区切りに変換した文字列
     */
    public static String toFormatString(final long value) {
        return toFormatString(Long.valueOf(value));
    }

    /**
     * 3桁区切りの文字列を取得する。
     *
     * @param value 対象
     * @return 3桁区切りに変換した文字列
     */
    public static String toFormatString(final Integer value) {
        return toFormatString(Long.valueOf(value));
    }

    /**
     * 3桁区切りの文字列を取得する。
     *
     * @param value 対象
     * @return 3桁区切りに変換した文字列
     */
    public static String toFormatString(final int value) {
        return toFormatString(Integer.valueOf(value));
    }
}
