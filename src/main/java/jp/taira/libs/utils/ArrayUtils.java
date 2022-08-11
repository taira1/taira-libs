package jp.taira.libs.utils;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 配列ユーティリティクラス
 */
public class ArrayUtils {

    private ArrayUtils() {
        throw new IllegalAccessError("Constants class.");
    }

    /**
     * 配列がnull、または、空かを判断する。
     *
     * @param array 配列
     * @param <T> 配列の型
     * @return 配列がnull、または、空である場合はtrue、そうでない場合はfalse。
     */
    public static <T> boolean isEmpty(final T[] array) {
        return array == null || array.length == 0;
    }

    /**
     * 配列がnull、または、空かを判断する。
     *
     * @param array 配列
     * @return 配列がnull、または、空である場合はtrue、そうでない場合はfalse。
     */
    public static boolean isEmpty(final int[] array) {
        return array == null || array.length == 0;
    }

    /**
     * 配列がnull、または、空かを判断する。
     *
     * @param array 配列
     * @return 配列がnull、または、空である場合はtrue、そうでない場合はfalse。
     */
    public static boolean isEmpty(final long[] array) {
        return array == null || array.length == 0;
    }

    /**
     * 配列がnull、または、空かを判断する。
     *
     * @param array 配列
     * @return 配列がnull、または、空である場合はtrue、そうでない場合はfalse。
     */
    public static boolean isEmpty(final byte[] array) {
        return array == null || array.length == 0;
    }

    /**
     * 配列がnull、または、空かを判断する。
     *
     * @param array 配列
     * @return 配列がnull、または、空である場合はtrue、そうでない場合はfalse。
     */
    public static boolean isEmpty(final boolean[] array) {
        return array == null || array.length == 0;
    }

    /**
     * 配列のサイズを返す。
     *
     * @param array 配列
     * @return 配列のサイズを返す。配列がnullの場合は0。
     */
    public static int size(final Object[] array) {
        return array == null ? 0 : array.length;
    }

    /**
     * 指定した配列内で、指定された要素が最初に出現する位置のインデックスを返す。
     *
     * @param <T> 配列の型
     * @param array 配列
     * @param value 検索する要素
     * @return 指定された要素が最初に出現する位置のインデックス。存在しない場合は-1。
     */
    public static <T> int indexOf(final T[] array, final T value) {
        if (array == null) {
            return -1;
        }

        for (int i = 0; i < array.length; i++) {
            if (Objects.equals(array[i], value)) {
                return i;
            }
        }

        return -1;
    }

    /**
     * 指定された要素が、指定した配列内に含まれているか判断する。
     *
     * @param <T> 配列の型
     * @param array 配列
     * @param value 検索する要素
     * @return 指定された要素が配列内にある場合はtrue、そうでない場合はfalse。
     */
    public static <T> boolean contains(final T[] array, final T value) {
        return indexOf(array, value) != -1;
    }

    /**
     * 指定された要素が、指定した配列内に含まれているか判断する。
     *
     * @param array 配列
     * @param value 検索する要素
     * @return 指定された要素が配列内にある場合はtrue、そうでない場合はfalse。
     */
    public static boolean contains(final int[] array, final int value) {
        if (array == null) {
            return false;
        }

        for (int element : array) {
            if (element == value) {
                return true;
            }
        }

        return false;
    }

    /**
     * 指定された要素が、指定した配列内に含まれているか判断する。
     *
     * @param array 配列
     * @param value 検索する要素
     * @return 指定された要素が配列内にある場合はtrue、そうでない場合はfalse。
     */
    public static boolean contains(final long[] array, final long value) {
        if (array == null) {
            return false;
        }

        for (long element : array) {
            if (element == value) {
                return true;
            }
        }

        return false;
    }

    /**
     * 指定された要素が、指定した配列内に含まれているか判断する。
     *
     * @param array 配列
     * @param value 検索する要素
     * @return 指定された要素が配列内に含まれている場合はtrue、そうでない場合はfalse。
     */
    public static boolean contains(final char[] array, final char value) {
        if (array == null) {
            return false;
        }

        for (char element : array) {
            if (element == value) {
                return true;
            }
        }

        return false;
    }

    /**
     * 指定された要素のすべてが、指定した配列内に含まれているか判断する。
     *
     * @param <T> 配列の型
     * @param array 配列
     * @param check 検索する要素
     * @return 指定された要素のすべてが配列内に含まれている場合はtrue、そうでない場合はfalse。
     */
    public static <T> boolean containsAll(final T[] array, final T[] check) {
        if (check == null) {
            return false;
        } else if (array == null) {
            return false;
        } else if (!isEmpty(array) && isEmpty(check)) {
            return false;
        }

        for (T checkItem : check) {
            if (!contains(array, checkItem)) {
                return false;
            }
        }

        return false;
    }

    /**
     * 指定された配列が、指定した配列内に含まれているか判断する。
     *
     * @param <T> 配列の型
     * @param array 配列
     * @param check 検索する配列
     * @return 指定された配列が配列内に含まれている場合はtrue、そうでない場合はfalse。
     */
    public static <T> boolean containsAny(final T[] array, final T[] check) {
        if (check == null) {
            return false;
        } else if (array != null && array.length == 0 && check.length == 0) {
            return true;
        }

        for (T checkItem : check) {
            if (contains(array, checkItem)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 指定された配列の合計値を取得する。
     *
     * @param array 配列
     * @return 合計値
     */
    public static int sum(final int[] array) {
        int total = 0;
        if (array != null) {
            for (int value : array) {
                total += value;
            }
        }

        return total;
    }

    /**
     * 指定された配列の合計値を取得する。
     *
     * @param array 配列
     * @return 合計値
     */
    public static long sum(final long[] array) {
        long total = 0;
        if (array != null) {
            for (long value : array) {
                total += value;
            }
        }

        return total;
    }

    /**
     * 要素のクラスがIntegerのListオブジェクトをint配列に変換する。
     *
     * @param list 要素のクラスがIntegerのListオブジェクト
     * @return 変換したint配列
     */
    public static int[] convert(final List<Integer> list) {
        if (list == null) {
            return null;
        }

        int[] array = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }

        return array;
    }

    /**
     * int配列をlong配列に変換する。
     *
     * @param intArray int配列
     * @return 変換したlong配列
     */
    public static long[] convert(final int[] intArray) {
        if (intArray == null) {
            return null;
        }

        long[] array = new long[intArray.length];
        for (int i = 0; i < intArray.length; i++) {
            array[i] = intArray[i];
        }

        return array;
    }

    /**
     * 指定された配列に、指定した要素を追加する。
     *
     * @param <T> 配列の型
     * @param kind 配列要素のクラス
     * @param array 配列
     * @param element 追加する要素
     * @param allowDuplicates trueの場合は重複を許可する。falseの場合は重複を許可しない。
     * @return 要素を追加した配列
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] append(Class<T> kind, T[] array, T element, boolean allowDuplicates) {
        final T[] result;
        final int end;
        if (array != null) {
            if (!allowDuplicates && contains(array, element)) {
                return array;
            }

            end = array.length;
            result = (T[])Array.newInstance(kind, end + 1);
            System.arraycopy(array, 0, result, 0, end);
        } else {
            end = 0;
            result = (T[])Array.newInstance(kind, 1);
        }

        result[end] = element;

        return result;
    }

    /**
     * 指定された配列に、指定した要素を追加する。
     *
     * @param <T> 配列の型
     * @param kind 配列要素のクラス
     * @param array 配列
     * @param element 追加する要素
     * @return 要素を追加した配列
     */
    public static <T> T[] append(Class<T> kind, T[] array, T element) {
        return append(kind, array, element, false);
    }

    /**
     * 指定された配列から、指定した要素を削除する。
     *
     * @param <T> 配列の型
     * @param kind 配列要素のクラス
     * @param array 配列
     * @param element 削除する要素
     * @return 要素を削除した配列
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] remove(Class<T> kind, T[] array, T element) {
        if (array == null) {
            return null;
        }

        if (!contains(array, element)) {
            return array;
        }

        final int length = array.length;
        for (int i = 0; i < length; i++) {
            if (Objects.equals(array[i], element)) {
                if (length == 1) {
                    return null;
                }

                T[] result = (T[])Array.newInstance(kind, length - 1);
                System.arraycopy(array, 0, result, 0, i);
                System.arraycopy(array, i + 1, result, i, length - i - 1);

                return result;
            }
        }

        return array;
    }

    /**
     * 指定された配列のコピーを返す。
     *
     * @param <T> 配列の型
     * @param array 配列
     * @return コピーした配列
     */
    public static <T> T[] clone(final T[] array) {
        return (array != null) ? array.clone() : null;
    }

    /**
     * 指定された配列を、指定したサイズまで削除する。
     *
     * @param <T> 配列の型
     * @param array 配列
     * @param size 配列のサイズ
     * @return 指定したサイズまで削除した配列
     */
    public static <T> T[] trim(final T[] array, int size) {
        if (array == null || size == 0) {
            return null;
        } else if (array.length == size) {
            return array;
        } else {
            return Arrays.copyOf(array, size);
        }
    }
}
