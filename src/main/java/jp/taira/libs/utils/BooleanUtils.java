package jp.taira.libs.utils;

/**
 * Booleanユーティリティクラス
 */
public class BooleanUtils {

    private BooleanUtils() {
        throw new IllegalAccessError("Constants class.");
    }

    public static boolean isTrue(final Boolean object) {
        return object != null && object.equals(Boolean.TRUE);
    }

    public static boolean isFalse(final Boolean object) {
        return object != null && object.equals(Boolean.FALSE);
    }

    public static Boolean toBoolean(final String target) {
        if (StringUtils.isBlank(target)) {
            return null;
        }
        if (target.equals("1") || target.equalsIgnoreCase(Boolean.TRUE.toString())) {
            return Boolean.TRUE;
        }
        if (target.equals("0") || target.equalsIgnoreCase(Boolean.FALSE.toString())) {
            return Boolean.FALSE;
        }
        return null;
    }

    public static Boolean toBoolean(final Integer target) {
        return target == 0 ? Boolean.FALSE : Boolean.TRUE;
    }

    public static Integer toInteger(final Boolean object) {
        if (object == null) {
            return null;
        }
        return object.equals(Boolean.TRUE) ? 1 : 0;
    }
}
