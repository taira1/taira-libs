package jp.taira.libs.utils;

import org.apache.poi.hssf.OldExcelFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
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
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class ExcelUtilsTest {

    @BeforeEach
    public void beforeEach() {
    }

    private Path getResourceFile(final String path) {
        URI resourceUri;
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

    @Test
    public void getWorkbookTest_Path() {
        assertNull(ExcelUtils.getWorkbook(Paths.get("")));
        assertNull(ExcelUtils.getWorkbook(getResourceFile("testFile.txt")));

        assertNotNull(ExcelUtils.getWorkbook(getResourceFile("testExcel/empty.xls")));
        assertNotNull(ExcelUtils.getWorkbook(getResourceFile("testExcel/empty.xlsx")));
        assertNotNull(ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xls")));
        assertNotNull(ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xlsx")));
    }

    @Test
    public void getSheetTest() {
        // null
        assertNull(ExcelUtils.getSheet(null));

        { /* xls */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xls"));
            final Sheet sheet = ExcelUtils.getSheet(workbook);

            assertNotNull(sheet);
        }

        { /* xlsx */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xlsx"));
            final Sheet sheet = ExcelUtils.getSheet(workbook);

            assertNotNull(sheet);
        }

        { /* インデックス(正常) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xls"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);

            assertNotNull(sheet);
        }

        { /* インデックス(マイナス) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xls"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, -1);

            assertNull(sheet);
        }

        { /* インデックス(異常プラス) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xls"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 9999);

            assertNull(sheet);
        }

        { /* シート名(正常) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xls"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, "Sheet1");

            assertNotNull(sheet);
        }

        { /* シート名(null) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xls"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, null);

            assertNull(sheet);
        }

        { /* シート名(空) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xls"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, "");

            assertNull(sheet);
        }

        { /* シート名(存在しない) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xls"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, "Sheet9999");

            assertNull(sheet);
        }

        { /* インデックス(正常) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xlsx"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);

            assertNotNull(sheet);
        }

        { /* インデックス(マイナス) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xlsx"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, -1);

            assertNull(sheet);
        }

        { /* インデックス(異常プラス) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xlsx"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 9999);

            assertNull(sheet);
        }

        { /* シート名(正常) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xlsx"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, "Sheet1");

            assertNotNull(sheet);
        }

        { /* シート名(null) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xlsx"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, null);

            assertNull(sheet);
        }

        { /* シート名(空) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xlsx"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, "");

            assertNull(sheet);
        }

        { /* シート名(存在しない) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xlsx"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, "Sheet9999");

            assertNull(sheet);
        }
    }

    @Test
    public void createSheetTest() {
        { /* null */
            final Sheet sheet = ExcelUtils.createSheet(null, null, null);

            assertNull(sheet);
        }

        { /* null */
            final Sheet sheet = ExcelUtils.createSheet(null, "templateSheetName", "sheetName");

            assertNull(sheet);
        }

        { /* シート名(null) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xls"));
            final Sheet sheet = ExcelUtils.createSheet(workbook, null, "sheetName");

            assertNull(sheet);
        }

        { /* シート名(null) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xls"));
            final Sheet sheet = ExcelUtils.createSheet(workbook, "templateSheetName", null);

            assertNull(sheet);
        }

        { /* シート名(null) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xls"));
            final Sheet sheet = ExcelUtils.createSheet(workbook, "Sheet1", null);

            assertNull(sheet);
        }

        { /* シート名(空) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xls"));
            final Sheet sheet = ExcelUtils.createSheet(workbook, "Sheet1", "");

            assertNull(sheet);
        }

        { /* 通常 */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xls"));
            final int sheetNum = Objects.requireNonNull(workbook).getNumberOfSheets();
            final Sheet sheet = ExcelUtils.createSheet(workbook, "Sheet1", "Sheet9");

            assertNotNull(sheet);
            assertEquals(sheet.getSheetName(), "Sheet9");
            assertNotNull(workbook.getSheet("Sheet9"));
            // テンプレートシートは存在しない。
            assertNull(workbook.getSheet("Sheet1"));
            assertEquals(workbook.getNumberOfSheets(), sheetNum);
        }

        { /* シート名(null) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xlsx"));
            final Sheet sheet = ExcelUtils.createSheet(workbook, null, "sheetName");

            assertNull(sheet);
        }

        { /* シート名(null) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xlsx"));
            final Sheet sheet = ExcelUtils.createSheet(workbook, "templateSheetName", null);

            assertNull(sheet);
        }

        { /* シート名(null) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xlsx"));
            final Sheet sheet = ExcelUtils.createSheet(workbook, "Sheet1", null);

            assertNull(sheet);
        }

        { /* シート名(空) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xlsx"));
            final Sheet sheet = ExcelUtils.createSheet(workbook, "Sheet1", "");

            assertNull(sheet);
        }

        { /* 通常 */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xlsx"));
            final int sheetNum = Objects.requireNonNull(workbook).getNumberOfSheets();
            final Sheet sheet = ExcelUtils.createSheet(workbook, "Sheet1", "Sheet9");

            assertNotNull(sheet);
            assertEquals(sheet.getSheetName(), "Sheet9");
            assertNotNull(workbook.getSheet("Sheet9"));
            // テンプレートシートは存在しない。
            assertNull(workbook.getSheet("Sheet1"));
            assertEquals(workbook.getNumberOfSheets(), sheetNum);
        }
    }

    @Test
    public void createSheetsTest() {
        { /* 通常 */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xlsx"));
            final String[] sheetNameArray = new String[]{ "Sheet10", "Sheet11", "Sheet12" };
            final List<Sheet> sheetList = ExcelUtils.createSheets(workbook, "Sheet1", sheetNameArray);

            assertNotNull(sheetList);
            assertEquals(sheetList.get(0).getSheetName(), "Sheet10");
            assertEquals(sheetList.get(1).getSheetName(), "Sheet11");
            assertEquals(sheetList.get(2).getSheetName(), "Sheet12");
            // テンプレートシートは存在しない。
            assertNull(Objects.requireNonNull(workbook).getSheet("Sheet1"));
            assertEquals(workbook.getNumberOfSheets(), sheetNameArray.length);
        }
    }

    @Test
    public void copySheetTest() {
        { /* null */
            final Sheet sheet = ExcelUtils.copySheet(null, null, null);

            assertNull(sheet);
        }

        { /* null */
            final Sheet sheet = ExcelUtils.copySheet(null, "templateSheetName", "sheetName");

            assertNull(sheet);
        }

        { /* シート名(null) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xls"));
            final Sheet sheet = ExcelUtils.copySheet(workbook, null, "sheetName");

            assertNull(sheet);
        }

        { /* シート名(null) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xls"));
            final Sheet sheet = ExcelUtils.copySheet(workbook, "templateSheetName", null);

            assertNull(sheet);
        }

        { /* シート名(null) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xls"));
            final Sheet sheet = ExcelUtils.copySheet(workbook, "Sheet1", null);

            assertNull(sheet);
        }

        { /* シート名(空) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xls"));
            final Sheet sheet = ExcelUtils.copySheet(workbook, "Sheet1", "");

            assertNull(sheet);
        }

        { /* 通常 */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xls"));
            final int sheetNum = Objects.requireNonNull(workbook).getNumberOfSheets();
            final Sheet sheet = ExcelUtils.copySheet(workbook, "Sheet1", "Sheet9");

            assertNotNull(sheet);
            assertEquals(sheet.getSheetName(), "Sheet9");
            assertNotNull(workbook.getSheet("Sheet9"));
            // コピー元は存在する。
            assertNotNull(workbook.getSheet("Sheet1"));
            assertEquals(workbook.getNumberOfSheets(), sheetNum + 1);
        }

        { /* シート名(null) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xlsx"));
            final Sheet sheet = ExcelUtils.copySheet(workbook, null, "sheetName");

            assertNull(sheet);
        }

        { /* シート名(null) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xlsx"));
            final Sheet sheet = ExcelUtils.copySheet(workbook, "templateSheetName", null);

            assertNull(sheet);
        }

        { /* シート名(null) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xlsx"));
            final Sheet sheet = ExcelUtils.copySheet(workbook, "Sheet1", null);

            assertNull(sheet);
        }

        { /* シート名(空) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xlsx"));
            final Sheet sheet = ExcelUtils.copySheet(workbook, "Sheet1", "");

            assertNull(sheet);
        }

        { /* 通常 */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xlsx"));
            final int sheetNum = Objects.requireNonNull(workbook).getNumberOfSheets();
            final Sheet sheet = ExcelUtils.copySheet(workbook, "Sheet1", "Sheet9");

            assertNotNull(sheet);
            assertEquals(sheet.getSheetName(), "Sheet9");
            assertNotNull(workbook.getSheet("Sheet9"));
            // コピー元は存在する。
            assertNotNull(workbook.getSheet("Sheet1"));
            assertEquals(workbook.getNumberOfSheets(), sheetNum + 1);
        }
    }

    @Test
    public void removeSheetTest_シートインデックス() {
        { /* xls */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xls"));
            final int sheetNum = Objects.requireNonNull(workbook).getNumberOfSheets();
            ExcelUtils.removeSheet(workbook, 0);

            assertNotNull(workbook);
            assertEquals(workbook.getNumberOfSheets(), sheetNum - 1);
            assertNull(ExcelUtils.getSheet(workbook, "Sheet1"));
        }

        { /* xls */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test-multi.xls"));
            final int sheetNum = Objects.requireNonNull(workbook).getNumberOfSheets();
            ExcelUtils.removeSheet(workbook, 0);

            assertNotNull(workbook);
            assertEquals(workbook.getNumberOfSheets(), sheetNum - 1);
            assertNull(ExcelUtils.getSheet(workbook, "Sheet1"));
            assertNotNull(ExcelUtils.getSheet(workbook, "Sheet2"));
        }

        { /* xlsx */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xlsx"));
            final int sheetNum = Objects.requireNonNull(workbook).getNumberOfSheets();
            ExcelUtils.removeSheet(workbook, 0);

            assertNotNull(workbook);
            assertEquals(workbook.getNumberOfSheets(), sheetNum - 1);
            assertNull(ExcelUtils.getSheet(workbook, "Sheet1"));
        }

        { /* xlsx */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test-multi.xlsx"));
            final int sheetNum = Objects.requireNonNull(workbook).getNumberOfSheets();
            ExcelUtils.removeSheet(workbook, 0);

            assertNotNull(workbook);
            assertEquals(workbook.getNumberOfSheets(), sheetNum - 1);
            assertNull(ExcelUtils.getSheet(workbook, "Sheet1"));
            assertNotNull(ExcelUtils.getSheet(workbook, "Sheet2"));
        }
    }
}
