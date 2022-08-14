package jp.taira.libs.utils;

/**
 * 名前/値ペアを表すクラス
 */
public class Pair<T1, T2> {

    /** ペアのキー */
    private final T1 key;
    /** ペアの値 */
    private final T2 value;

    /**
     * コンストラクタ
     *
     * @param key ペアのキー
     * @param value ペアの値
     */
    public Pair(T1 key, T2 value) {
        this.key = key;
        this.value = value;
    }

    /**
     * ペアのキーを取得する。
     *
     * @return ペアのキー
     */
    public T1 getKey() {
        return key;
    }

    /**
     * ペアの値を取得する。
     *
     * @return ペアの値
     */
    public T2 getValue() {
        return value;
    }

    /**
     * このオブジェクトと他のオブジェクトが等しいかどうかを示す。
     *
     * @param object 比較対象の参照オブジェクト
     * @return このオブジェクトが引数と同じである場合はtrue、それ以外の場合はfalse。
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (!(object instanceof Pair)) {
            return false;
        }

        Pair<?, ?> rhs = (Pair<?, ?>)object;
        return (key == null ? rhs.key == null : key.equals(rhs.key))
                && (value == null ? rhs.value == null : value.equals(rhs.value));
    }

    /**
     * オブジェクトのハッシュコード値を返す。
     *
     * @return このオブジェクトのハッシュコード値
     */
    @Override
    public int hashCode() {
        return (key != null ? key.hashCode() : 0) ^ (value != null ? value.hashCode() : 0);
    }

    /**
     * オブジェクトの文字列表現を返す。
     *
     * @return このオブジェクトの文字列表現
     */
    @Override
    public String toString() {
        return "[" + (key != null ? key.toString() : "null") + " : " + (value != null ? value.toString() : "null") + "]";
    }
}
