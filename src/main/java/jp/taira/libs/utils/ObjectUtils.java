package jp.taira.libs.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * オブジェクトユーティリティクラス
 */
public class ObjectUtils {

    private static final ObjectMapper mapper = new ObjectMapper();

    /** バッファサイズ */
    private static final int BUFFER_SIZE = 4096;

    private ObjectUtils() {
        throw new IllegalAccessError("Constants class.");
    }

    private static ObjectMapper getObjectMapper(final boolean ignoreNullField) {
        ObjectMapper objectMapper = mapper;
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        if (ignoreNullField) {
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        }
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

        return objectMapper;
    }

    /**
     * インスタンスを生成する。
     *
     * @param <T> インスタンスを生成する型
     * @param clazz インスタンスを生成するClassオブジェクト
     * @return 生成されたインスタンス
     * @throws Exception 例外が発生した場合。
     */
    public static <T> T newInstance(Class<T> clazz) throws Exception {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
            throw new Exception(e);
        }
    }

    /**
     * オブジェクトの文字列表現を返す。
     *
     * @param object 対象オブジェクト
     * @return このオブジェクトの文字列表現。
     */
    public static String toString(Object object) {
        return object != null ? object.toString() : null;
    }

    /**
     * 残りのすべてのバイトを入力ストリームから読み込みます。
     *
     * @param inputStream 入力ストリーム
     * @param close trueの場合は読み込み後に入力ストリームを解放する。falseの場合は解放しない。
     * @return この入力ストリームから読み込まれたバイトを含むバイト配列
     * @throws IOException ファイル入出力処理で例外が発生した場合。
     */
    public static byte[] readAllBytes(final InputStream inputStream, final boolean close) throws IOException {
        final ByteArrayOutputStream bout = new ByteArrayOutputStream();
        final byte[] buffer = new byte[BUFFER_SIZE];
        int readSize = -1;
        try {
            while ((readSize = inputStream.read(buffer)) != -1) {
                bout.write(buffer, 0, readSize);
            }
            return bout.toByteArray();
        } finally {
            if (close && inputStream != null) {
                inputStream.close();
            }
        }
    }

    /**
     * 残りのすべてのバイトを入力ストリームから読み込みます。<br>また、読み込み後に入力ストリームを解放する。
     *
     * @param inputStream 入力ストリーム
     * @return この入力ストリームから読み込まれたバイトを含むバイト配列
     * @throws IOException ファイル入出力処理で例外が発生した場合。
     */
    public static byte[] readAllBytes(final InputStream inputStream) throws IOException {
        return readAllBytes(inputStream, true);
    }

    /**
     * すべてのバイトをPathオブジェクトから読み込みます。
     *
     * @param path Pathオブジェクト
     * @return Pathオブジェクトから読み込まれたバイト配列
     * @throws IOException ファイル入出力処理で例外が発生した場合。
     */
    public static byte[] readAllBytes(final Path path) throws IOException {
        return readAllBytes(Files.newInputStream(path));
    }

    /**
     * キャストする。
     *
     * @param <T> キャストする型
     * @param object キャストする対象オブジェクト
     * @return キャストされたオブジェクト
     */
    @SuppressWarnings("unchecked")
    public static <T> T cast(final Object object) {
        T result = (T)object;
        return result;
    }

    /**
     * オブジェクトをディープコピーする。
     *
     * @param <T> コピー先オブジェクトの型
     * @param object コピー元オブジェクト
     * @return ディープコピーされたオブジェクト
     * @throws IOException ファイル入出力処理で例外が発生した場合。
     * @throws ClassNotFoundException 指定された名前のクラスの定義が見つからなかった場合。
     */
    @SuppressWarnings("unchecked")
    public static <T> T deepCopy(T object) throws IOException, ClassNotFoundException {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(object);
        }

        final ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        try (ObjectInputStream ois = new ObjectInputStream(bais)) {
            return (T) ois.readObject();
        }
    }

    /**
     * オブジェクトをマッピングする。
     *
     * @param <T> マッピング先オブジェクトの型
     * @param object マッピング元オブジェクト
     * @param clazz マッピングするクラス
     * @return マッピングされたオブジェクト
     */
    public static <T> T map(Object object, final Class<T> clazz) {
        final ObjectMapper mapper = getObjectMapper(false);
        try {
            return mapper.readValue(mapper.writeValueAsString(object), clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
