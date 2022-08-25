package jp.taira.libs.utils;

import org.apache.poi.hssf.OldExcelFormatException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class ExcelUtilsTest {

    @BeforeEach
    public void beforeEach() {
    }

    private Path getResourceFile(final String path) {
        URI resourceUri = null;
        try {
            resourceUri = Objects.requireNonNull(getClass().getClassLoader().getResource(path)).toURI();
            return Paths.get(resourceUri);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unused")
    private String getResourceFilePath(final String path) {
        return getResourceFile(path).toString();
    }

    @Test
    public void constractorTest() {
        assertThrows(IllegalAccessError.class, () -> {
            try {
                Constructor<?> constructor = ExcelUtils.class.getDeclaredConstructor();
                constructor.setAccessible(true);
                constructor.newInstance();
            } catch (InvocationTargetException e) {
                throw e.getCause();
            }
        });
    }

    @Test
    public void getWorkbookTest_InputStream() {
        /* xls */
        assertDoesNotThrow(() -> {
            InputStream inputStream = new FileInputStream(getResourceFile("testExcel/test.xls").toFile());
            assertNotNull(ExcelUtils.getWorkbook(inputStream));
        });

        /* xls(BIFF5) */
        assertThrows(OldExcelFormatException.class, () -> {
            InputStream inputStream = new FileInputStream(getResourceFile("testExcel/test_BIFF5.xls").toFile());
            assertNotNull(ExcelUtils.getWorkbook(inputStream));
        });

        /* xlsm */
        assertDoesNotThrow(() -> {
            InputStream inputStream = new FileInputStream(getResourceFile("testExcel/test.xlsm").toFile());
            assertNotNull(ExcelUtils.getWorkbook(inputStream));
        });

        /* xlsx */
        assertDoesNotThrow(() -> {
            InputStream inputStream = new FileInputStream(getResourceFile("testExcel/test.xlsx").toFile());
            assertNotNull(ExcelUtils.getWorkbook(inputStream));
        });
    }
}
