package jp.taira.libs.utils;

/**
 * 3つの情報のセットを表すクラス
 */
public class Tuple3<T1, T2, T3> extends Pair<T1, Pair<T2, T3>> {

    public Tuple3(T1 value1, T2 value2, T3 value3) {
        super(value1, new Pair<>(value2, value3));
    }

    public T1 getValue1() {
        return super.getKey();
    }

    public T2 getValue2() {
        try {
            return super.getValue().getKey();
        } catch (Exception e) {
            return null;
        }
    }

    public T3 getValue3() {
        try {
            return super.getValue().getValue();
        } catch (Exception e) {
            return null;
        }
    }
}
