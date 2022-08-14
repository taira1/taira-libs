package jp.taira.libs.utils;

import java.util.*;

/**
 * コレクションユーティリティクラス
 */
public class CollectionUtils {

    private CollectionUtils() {
        throw new IllegalAccessError("Constants class.");
    }

    /**
     * コレクションがnull、または、空かを判断する。
     *
     * @param collection コレクション
     * @return コレクションがnull、または、空である場合はtrue、そうでない場合はfalse。
     */
    public static boolean isEmpty(final Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * Mapオブジェクトがnull、または、空かを判断する。
     *
     * @param map Mapオブジェクト
     * @return Mapオブジェクトがnull、または、空である場合はtrue、そうでない場合はfalse。
     */
    public static boolean isEmpty(final Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    /**
     * コレクションの要素数を取得する。
     *
     * @param collection コレクション
     * @return コレクションがnull、または、空である場合は0、そうでない場合は要素数を返す。
     */
    public static int size(final Collection<?> collection) {
        return isEmpty(collection) ? 0 : collection.size();
    }

    /**
     * Mapオブジェクトの要素数を取得する。
     *
     * @param map Mapオブジェクト
     * @return Mapオブジェクトがnull、または、空である場合は0、そうでない場合は要素数を返す。
     */
    public static int size(final Map<?, ?> map) {
        return isEmpty(map) ? 0 : map.size();
    }

    /**
     * 指定したインデックスの、Mapオブジェクトに含まれるマッピングのSetビューを返します。
     *
     * @param <T> Map.Entryオブジェクトの型
     * @param map Mapオブジェクト
     * @param index インデックス
     * @return Mapオブジェクト
     */
    @SuppressWarnings("unchecked")
    public static <T> T getMapEntry(final Map<?, ?> map, final int index) {
        if (isEmpty(map) || index < 0 || index >= map.size()) {
            return null;
        }

        int i = 0;
        for (final Map.Entry<?, ?> entry : map.entrySet()) {
            if (i == index) {
                return (T) entry;
            }
            i++;
        }

        return null;
    }

    /**
     * Mapオブジェクトにエントリーを追加する。
     *
     * @param <K> Mapオブジェクトのキーの型
     * @param <V> Mapオブジェクトの値の型
     * @param map Mapオブジェクト
     * @param key キー
     * @param value 値
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static <K, V> void addMapEntry(Map<K, V> map, final K key, final V value) {
        if (map == null) {
            return;
        }

        if (map.containsKey(key)) {
            final V object = map.get(key);
            if (object instanceof Collection && value instanceof Collection) {
                ((Collection)object).addAll((Collection)value);
            } else if (object instanceof Map && value instanceof Map) {
                ((Map)object).putAll((Map)value);
            } else {
                map.put(key, value);
            }
        } else {
            map.put(key, value);
        }
    }

    /**
     * Mapオブジェクトのキーを置換する。
     *
     * @param <V> Mapオブジェクトの値の型
     * @param map Mapオブジェクト
     * @param source 検索する文字列
     * @param dest 置き換えられる文字列
     */
    public static <V> void replaceAllMapKey(final Map<String, V> map, final String source, final String dest) {
        if (isEmpty(map)) {
            return;
        }

        final Map<String, V> resultMap = new LinkedHashMap<>();
        map.forEach((key, value) -> {
            resultMap.put(key.replaceAll(source, dest), value);
        });
        map.clear();
        map.putAll(resultMap);
    }

    /**
     * Mapオブジェクトからキーのマッピングを削除する。
     *
     * @param <K> Mapオブジェクトのキーの型
     * @param map Mapオブジェクト
     * @param keyList Mapオブジェクトから削除するキーのリスト
     */
    public static <K> void removeAll(final Map<K, ?> map, final List<K> keyList) {
        if (isEmpty(map) || isEmpty(keyList)) {
            return;
        }

        keyList.forEach(map::remove);
    }

    /**
     * Listオブジェクトから、重複した要素を削除する。
     *
     * @param <V> Listオブジェクトの要素の型
     * @param list Listオブジェクト
     */
    public static <V> void removeDuplicate(final List<V> list) {
        if (isEmpty(list)) {
            return;
        }

        final Set<V> resultSet = new HashSet<>();
        list.removeIf(value -> !resultSet.add(value));
    }
}
