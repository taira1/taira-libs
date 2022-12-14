package jp.taira.libs.utils;

import org.apache.poi.hssf.OldExcelFormatException;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
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
import java.util.Date;
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

        { /* ??????????????????(??????) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xls"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);

            assertNotNull(sheet);
        }

        { /* ??????????????????(????????????) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xls"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, -1);

            assertNull(sheet);
        }

        { /* ??????????????????(???????????????) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xls"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 9999);

            assertNull(sheet);
        }

        { /* ????????????(??????) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xls"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, "Sheet1");

            assertNotNull(sheet);
        }

        { /* ????????????(null) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xls"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, null);

            assertNull(sheet);
        }

        { /* ????????????(???) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xls"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, "");

            assertNull(sheet);
        }

        { /* ????????????(???????????????) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xls"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, "Sheet9999");

            assertNull(sheet);
        }

        { /* ??????????????????(??????) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xlsx"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);

            assertNotNull(sheet);
        }

        { /* ??????????????????(????????????) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xlsx"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, -1);

            assertNull(sheet);
        }

        { /* ??????????????????(???????????????) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xlsx"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 9999);

            assertNull(sheet);
        }

        { /* ????????????(??????) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xlsx"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, "Sheet1");

            assertNotNull(sheet);
        }

        { /* ????????????(null) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xlsx"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, null);

            assertNull(sheet);
        }

        { /* ????????????(???) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xlsx"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, "");

            assertNull(sheet);
        }

        { /* ????????????(???????????????) */
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

        { /* ????????????(null) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xls"));
            final Sheet sheet = ExcelUtils.createSheet(workbook, null, "sheetName");

            assertNull(sheet);
        }

        { /* ????????????(null) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xls"));
            final Sheet sheet = ExcelUtils.createSheet(workbook, "templateSheetName", null);

            assertNull(sheet);
        }

        { /* ????????????(null) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xls"));
            final Sheet sheet = ExcelUtils.createSheet(workbook, "Sheet1", null);

            assertNull(sheet);
        }

        { /* ????????????(???) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xls"));
            final Sheet sheet = ExcelUtils.createSheet(workbook, "Sheet1", "");

            assertNull(sheet);
        }

        { /* ?????? */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xls"));
            final int sheetNum = Objects.requireNonNull(workbook).getNumberOfSheets();
            final Sheet sheet = ExcelUtils.createSheet(workbook, "Sheet1", "Sheet9");

            assertNotNull(sheet);
            assertEquals(sheet.getSheetName(), "Sheet9");
            assertNotNull(workbook.getSheet("Sheet9"));
            // ????????????????????????????????????????????????
            assertNull(workbook.getSheet("Sheet1"));
            assertEquals(workbook.getNumberOfSheets(), sheetNum);
        }

        { /* ????????????(null) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xlsx"));
            final Sheet sheet = ExcelUtils.createSheet(workbook, null, "sheetName");

            assertNull(sheet);
        }

        { /* ????????????(null) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xlsx"));
            final Sheet sheet = ExcelUtils.createSheet(workbook, "templateSheetName", null);

            assertNull(sheet);
        }

        { /* ????????????(null) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xlsx"));
            final Sheet sheet = ExcelUtils.createSheet(workbook, "Sheet1", null);

            assertNull(sheet);
        }

        { /* ????????????(???) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xlsx"));
            final Sheet sheet = ExcelUtils.createSheet(workbook, "Sheet1", "");

            assertNull(sheet);
        }

        { /* ?????? */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xlsx"));
            final int sheetNum = Objects.requireNonNull(workbook).getNumberOfSheets();
            final Sheet sheet = ExcelUtils.createSheet(workbook, "Sheet1", "Sheet9");

            assertNotNull(sheet);
            assertEquals(sheet.getSheetName(), "Sheet9");
            assertNotNull(workbook.getSheet("Sheet9"));
            // ????????????????????????????????????????????????
            assertNull(workbook.getSheet("Sheet1"));
            assertEquals(workbook.getNumberOfSheets(), sheetNum);
        }
    }

    @Test
    public void createSheetsTest() {
        { /* ?????? */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xlsx"));
            final String[] sheetNameArray = new String[]{ "Sheet10", "Sheet11", "Sheet12" };
            final List<Sheet> sheetList = ExcelUtils.createSheets(workbook, "Sheet1", sheetNameArray);

            assertNotNull(sheetList);
            assertEquals(sheetList.get(0).getSheetName(), "Sheet10");
            assertEquals(sheetList.get(1).getSheetName(), "Sheet11");
            assertEquals(sheetList.get(2).getSheetName(), "Sheet12");
            // ????????????????????????????????????????????????
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

        { /* ????????????(null) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xls"));
            final Sheet sheet = ExcelUtils.copySheet(workbook, null, "sheetName");

            assertNull(sheet);
        }

        { /* ????????????(null) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xls"));
            final Sheet sheet = ExcelUtils.copySheet(workbook, "templateSheetName", null);

            assertNull(sheet);
        }

        { /* ????????????(null) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xls"));
            final Sheet sheet = ExcelUtils.copySheet(workbook, "Sheet1", null);

            assertNull(sheet);
        }

        { /* ????????????(???) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xls"));
            final Sheet sheet = ExcelUtils.copySheet(workbook, "Sheet1", "");

            assertNull(sheet);
        }

        { /* ?????? */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xls"));
            final int sheetNum = Objects.requireNonNull(workbook).getNumberOfSheets();
            final Sheet sheet = ExcelUtils.copySheet(workbook, "Sheet1", "Sheet9");

            assertNotNull(sheet);
            assertEquals(sheet.getSheetName(), "Sheet9");
            assertNotNull(workbook.getSheet("Sheet9"));
            // ??????????????????????????????
            assertNotNull(workbook.getSheet("Sheet1"));
            assertEquals(workbook.getNumberOfSheets(), sheetNum + 1);
        }

        { /* ????????????(null) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xlsx"));
            final Sheet sheet = ExcelUtils.copySheet(workbook, null, "sheetName");

            assertNull(sheet);
        }

        { /* ????????????(null) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xlsx"));
            final Sheet sheet = ExcelUtils.copySheet(workbook, "templateSheetName", null);

            assertNull(sheet);
        }

        { /* ????????????(null) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xlsx"));
            final Sheet sheet = ExcelUtils.copySheet(workbook, "Sheet1", null);

            assertNull(sheet);
        }

        { /* ????????????(???) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xlsx"));
            final Sheet sheet = ExcelUtils.copySheet(workbook, "Sheet1", "");

            assertNull(sheet);
        }

        { /* ?????? */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xlsx"));
            final int sheetNum = Objects.requireNonNull(workbook).getNumberOfSheets();
            final Sheet sheet = ExcelUtils.copySheet(workbook, "Sheet1", "Sheet9");

            assertNotNull(sheet);
            assertEquals(sheet.getSheetName(), "Sheet9");
            assertNotNull(workbook.getSheet("Sheet9"));
            // ??????????????????????????????
            assertNotNull(workbook.getSheet("Sheet1"));
            assertEquals(workbook.getNumberOfSheets(), sheetNum + 1);
        }
    }

    @Test
    public void removeSheetTest_???????????????????????????() {
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

    @Test
    public void removeSheetTest_????????????() {
        { /* xls */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xls"));
            final int sheetNum = Objects.requireNonNull(workbook).getNumberOfSheets();
            ExcelUtils.removeSheet(workbook, "Sheet1");

            assertNotNull(workbook);
            assertEquals(workbook.getNumberOfSheets(), sheetNum - 1);
            assertNull(ExcelUtils.getSheet(workbook, "Sheet1"));
        }

        { /* xls */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test-multi.xls"));
            final int sheetNum = Objects.requireNonNull(workbook).getNumberOfSheets();
            ExcelUtils.removeSheet(workbook, "Sheet1");

            assertNotNull(workbook);
            assertEquals(workbook.getNumberOfSheets(), sheetNum - 1);
            assertNull(ExcelUtils.getSheet(workbook, "Sheet1"));
            assertNotNull(ExcelUtils.getSheet(workbook, "Sheet2"));
        }

        { /* xlsx */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xlsx"));
            final int sheetNum = Objects.requireNonNull(workbook).getNumberOfSheets();
            ExcelUtils.removeSheet(workbook, "Sheet1");

            assertNotNull(workbook);
            assertEquals(workbook.getNumberOfSheets(), sheetNum - 1);
            assertNull(ExcelUtils.getSheet(workbook, "Sheet1"));
        }

        { /* xlsx */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test-multi.xlsx"));
            final int sheetNum = Objects.requireNonNull(workbook).getNumberOfSheets();
            ExcelUtils.removeSheet(workbook, "Sheet1");

            assertNotNull(workbook);
            assertEquals(workbook.getNumberOfSheets(), sheetNum - 1);
            assertNull(ExcelUtils.getSheet(workbook, "Sheet1"));
            assertNotNull(ExcelUtils.getSheet(workbook, "Sheet2"));
        }
    }

    @Test
    public void removeSheetTest_??????() {
        { /* null */
            assertThrows(Exception.class, () -> ExcelUtils.removeSheet(null, null));
        }

        { /* null */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xls"));
            assertThrows(Exception.class, () -> ExcelUtils.removeSheet(Objects.requireNonNull(workbook), null));
        }

        { /* ??????????????????(????????????) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xls"));
            assertThrows(Exception.class, () -> ExcelUtils.removeSheet(Objects.requireNonNull(workbook), -1));
        }

        { /* ????????????(???) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xls"));
            assertThrows(Exception.class, () -> ExcelUtils.removeSheet(Objects.requireNonNull(workbook), ""));
        }

        { /* null */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xlsx"));
            assertThrows(Exception.class, () -> ExcelUtils.removeSheet(Objects.requireNonNull(workbook), null));
        }

        { /* ??????????????????(????????????) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xlsx"));
            assertThrows(Exception.class, () -> ExcelUtils.removeSheet(Objects.requireNonNull(workbook), -1));
        }

        { /* ????????????(???) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xlsx"));
            assertThrows(Exception.class, () -> ExcelUtils.removeSheet(Objects.requireNonNull(workbook), ""));
        }
    }

    @Test
    public void isProtectedSheetTest() {
        { /* null */
            assertFalse(ExcelUtils.isProtectedSheet(null));
        }

        { /* false */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xls"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);

            assertFalse(ExcelUtils.isProtectedSheet(sheet));
        }

        { /* true */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test-protected.xls"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);

            assertTrue(ExcelUtils.isProtectedSheet(sheet));
        }

        { /* false */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xlsx"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);

            assertFalse(ExcelUtils.isProtectedSheet(sheet));
        }

        { /* true */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test-protected.xlsx"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);

            assertTrue(ExcelUtils.isProtectedSheet(sheet));
        }
    }

    @Test
    public void isProtectedSheetTest_?????????????????????() {
        { /* null */
            assertFalse(ExcelUtils.isProtectedSheet(null, null));
        }

        { /* false */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xls"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);

            assertFalse(ExcelUtils.isProtectedSheet(sheet, null));
        }

        { /* false */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test-protected.xls"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);

            assertFalse(ExcelUtils.isProtectedSheet(sheet, "1234"));
        }

        { /* true */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test-protected.xls"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);

            assertTrue(ExcelUtils.isProtectedSheet(sheet, "pass"));
        }

        { /* false */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xlsx"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);

            assertFalse(ExcelUtils.isProtectedSheet(sheet, null));
        }

        { /* false */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test-protected.xlsx"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);

            assertFalse(ExcelUtils.isProtectedSheet(sheet, "1234"));
        }

        { /* true */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test-protected.xlsx"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);

            assertTrue(ExcelUtils.isProtectedSheet(sheet, "pass"));
        }
    }

    @Test
    public void getRowTest_???0() {
        { /* null */
            final Row row = ExcelUtils.getRow(null, 0, 0, 0);

            assertNull(row);
        }

        { /* ?????? */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xls"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Row row = ExcelUtils.getRow(sheet, 0, 0, 0);

            assertNotNull(row);
            assertEquals(row.getRowNum(), 0);
            assertEquals(row.getPhysicalNumberOfCells(), 1);
            assertEquals(row.getFirstCellNum(), 0);
            assertEquals(row.getLastCellNum(), 1);
        }

        { /* ?????? */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xls"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Row row = ExcelUtils.getRow(sheet, 0, 1, 1);

            assertNotNull(row);
            assertEquals(row.getRowNum(), 0);
            assertEquals(row.getPhysicalNumberOfCells(), 1);
            assertEquals(row.getFirstCellNum(), 1);
            assertEquals(row.getLastCellNum(), 2);
        }

        { /* ?????? */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xls"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Row row = ExcelUtils.getRow(sheet, 0, 0, 1);

            assertNotNull(row);
            assertEquals(row.getRowNum(), 0);
            assertEquals(row.getPhysicalNumberOfCells(), 2);
            assertEquals(row.getFirstCellNum(), 0);
            assertEquals(row.getLastCellNum(), 2);
        }

        { /* ?????? */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xlsx"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Row row = ExcelUtils.getRow(sheet, 0, 0, 0);

            assertNotNull(row);
            assertEquals(row.getRowNum(), 0);
            assertEquals(row.getPhysicalNumberOfCells(), 1);
            assertEquals(row.getFirstCellNum(), 0);
            assertEquals(row.getLastCellNum(), 1);
        }

        { /* ?????? */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xlsx"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Row row = ExcelUtils.getRow(sheet, 0, 1, 1);

            assertNotNull(row);
            assertEquals(row.getRowNum(), 0);
            assertEquals(row.getPhysicalNumberOfCells(), 1);
            assertEquals(row.getFirstCellNum(), 1);
            assertEquals(row.getLastCellNum(), 2);
        }

        { /* ?????? */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xlsx"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Row row = ExcelUtils.getRow(sheet, 0, 0, 1);

            assertNotNull(row);
            assertEquals(row.getRowNum(), 0);
            assertEquals(row.getPhysicalNumberOfCells(), 2);
            assertEquals(row.getFirstCellNum(), 0);
            assertEquals(row.getLastCellNum(), 2);
        }
    }

    @Test
    public void getRowTest_????????????() {
        { /* ??????????????????(????????????) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xls"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Row row = ExcelUtils.getRow(sheet, 0, -1, 0);

            assertNull(row);
        }

        { /* ??????????????????(????????????) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xls"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Row row = ExcelUtils.getRow(sheet, 0, -1, 0);

            assertNull(row);
        }

        { /* ??????????????????(????????????) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xls"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Row row = ExcelUtils.getRow(sheet, 0, 0, -1);

            assertNull(row);
        }

        { /* ??????????????????(????????????) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xls"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Row row = ExcelUtils.getRow(sheet, -1, -1, 0);

            assertNull(row);
        }

        { /* ??????????????????(????????????) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xls"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Row row = ExcelUtils.getRow(sheet, 0, -1, -1);

            assertNull(row);
        }

        { /* ??????????????????(????????????) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xls"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Row row = ExcelUtils.getRow(sheet, -1, 0, -1);

            assertNull(row);
        }

        { /* ??????????????????(????????????) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xls"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Row row = ExcelUtils.getRow(sheet, -1, -1, -1);

            assertNull(row);
        }

        { /* ??????????????????(????????????) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xlsx"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Row row = ExcelUtils.getRow(sheet, -1, 0, 0);

            assertNull(row);
        }

        { /* ??????????????????(????????????) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xlsx"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Row row = ExcelUtils.getRow(sheet, 0, -1, 0);

            assertNull(row);
        }

        { /* ??????????????????(????????????) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xlsx"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Row row = ExcelUtils.getRow(sheet, 0, 0, -1);

            assertNull(row);
        }

        { /* ??????????????????(????????????) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xlsx"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Row row = ExcelUtils.getRow(sheet, -1, -1, 0);

            assertNull(row);
        }

        { /* ??????????????????(????????????) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xlsx"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Row row = ExcelUtils.getRow(sheet, 0, -1, -1);

            assertNull(row);
        }

        { /* ??????????????????(????????????) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xlsx"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Row row = ExcelUtils.getRow(sheet, -1, 0, -1);

            assertNull(row);
        }

        { /* ??????????????????(????????????) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xlsx"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Row row = ExcelUtils.getRow(sheet, -1, -1, -1);

            assertNull(row);
        }
    }

    @Test
    public void getRowTest_??????() {
        { /* ?????????????????? */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xls"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Row row = ExcelUtils.getRow(sheet, 9999, 0, 1);

            assertNull(row);
        }

        { /* ??????????????? */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xls"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Row row = ExcelUtils.getRow(sheet, 0, 1, 0);

            assertNull(row);
        }

        { /* ????????? */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xls"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Row row = ExcelUtils.getRow(sheet, 0, 0, 99);

            assertNotNull(row);
            assertEquals(row.getRowNum(), 0);
            assertEquals(row.getPhysicalNumberOfCells(), 2);
            assertEquals(row.getFirstCellNum(), 0);
            assertEquals(row.getLastCellNum(), 2);
        }

        { /* ????????? */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xls"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Row row = ExcelUtils.getRow(sheet, 0, 99, 999);

            assertNotNull(row);
            assertEquals(row.getRowNum(), 0);
            assertEquals(row.getPhysicalNumberOfCells(), 0);
            assertEquals(row.getFirstCellNum(), -1);
            assertEquals(row.getLastCellNum(), -1);
        }

        { /* ?????????????????? */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xlsx"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Row row = ExcelUtils.getRow(sheet, 9999, 0, 1);

            assertNull(row);
        }

        { /* ??????????????? */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xlsx"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Row row = ExcelUtils.getRow(sheet, 0, 1, 0);

            assertNull(row);
        }

        { /* ????????? */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xlsx"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Row row = ExcelUtils.getRow(sheet, 0, 0, 99);

            assertNotNull(row);
            assertEquals(row.getRowNum(), 0);
            assertEquals(row.getPhysicalNumberOfCells(), 2);
            assertEquals(row.getFirstCellNum(), 0);
            assertEquals(row.getLastCellNum(), 2);
        }

        { /* ????????? */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test.xlsx"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Row row = ExcelUtils.getRow(sheet, 0, 99, 999);

            assertNotNull(row);
            assertEquals(row.getRowNum(), 0);
            assertEquals(row.getPhysicalNumberOfCells(), 0);
            assertEquals(row.getFirstCellNum(), -1);
            assertEquals(row.getLastCellNum(), -1);
        }
    }

    @Test
    public void getCellTest() {
        { /* null */
            final Cell cell = ExcelUtils.getCell(null, 0, 0);

            assertNull(cell);
        }

        { /* ??????????????????(????????????) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test-cell.xls"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Cell cell = ExcelUtils.getCell(sheet, -1, 0);

            assertNull(cell);
        }

        { /* ??????????????????(????????????) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test-cell.xls"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Cell cell = ExcelUtils.getCell(sheet, 0, -1);

            assertNull(cell);
        }

        { /* ??????????????????(????????????) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test-cell.xls"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Cell cell = ExcelUtils.getCell(sheet, -1, -1);

            assertNull(cell);
        }

        { /* ?????? */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test-cell.xls"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Cell cell = ExcelUtils.getCell(sheet, 0, 0);

            assertNotNull(cell);
        }

        { /* ??????????????????(????????????) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test-cell.xlsx"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Cell cell = ExcelUtils.getCell(sheet, -1, 0);

            assertNull(cell);
        }

        { /* ??????????????????(????????????) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test-cell.xlsx"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Cell cell = ExcelUtils.getCell(sheet, 0, -1);

            assertNull(cell);
        }

        { /* ??????????????????(????????????) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test-cell.xlsx"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Cell cell = ExcelUtils.getCell(sheet, -1, -1);

            assertNull(cell);
        }

        { /* ?????? */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test-cell.xlsx"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Cell cell = ExcelUtils.getCell(sheet, 0, 0);

            assertNotNull(cell);
        }
    }

    @Test
    public void getCellValueTest_xls() {
        { /* null */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test-cell.xls"));
            final Object value = ExcelUtils.getCellValue(workbook, null);

            assertNull(value);
        }

        { /* null */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test-cell.xls"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Cell cell = ExcelUtils.getCell(sheet, 0, 0);
            final Object value = ExcelUtils.getCellValue(null, cell);

            assertNull(value);
        }

        { /* ??????(???????????? - ?????????) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test-cell.xls"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Cell cell = ExcelUtils.getCell(sheet, 0, 0);
            final Object value = ExcelUtils.getCellValue(workbook, cell);

            assertNotNull(value);
            assertTrue(value instanceof HSSFRichTextString);
            assertEquals(value.toString(), "testA1");
        }

        { /* ??????(???????????? - ??????) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test-cell.xls"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Cell cell = ExcelUtils.getCell(sheet, 1, 0);
            final Object value = ExcelUtils.getCellValue(workbook, cell);

            assertNotNull(value);
            assertTrue(value instanceof Double);
            assertEquals(value, 1.0);
        }

        { /* ??????(???????????? - ??????) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test-cell.xls"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Cell cell = ExcelUtils.getCell(sheet, 2, 0);
            final Object value = ExcelUtils.getCellValue(workbook, cell);

            assertNotNull(value);
            assertTrue(value instanceof Double);
            assertEquals(value, 3.0);
        }

        { /* ??????(????????????) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test-cell.xls"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Cell cell = ExcelUtils.getCell(sheet, 3, 0);
            final Object value = ExcelUtils.getCellValue(workbook, cell);

            assertNull(value);
        }

        { /* ??????(???????????? - ??????3??????) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test-cell.xls"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Cell cell = ExcelUtils.getCell(sheet, 4, 0);
            final Object value = ExcelUtils.getCellValue(workbook, cell);

            assertNotNull(value);
            assertTrue(value instanceof HSSFRichTextString);
            assertEquals(value.toString(), "   ");
        }

        { /* ??????(????????????) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test-cell.xls"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Cell cell = ExcelUtils.getCell(sheet, 5, 0);
            final Object value = ExcelUtils.getCellValue(workbook, cell);

            assertNotNull(value);
            assertTrue(value instanceof Date);
            assertEquals(value, DateTimeUtils.parseToDate("2019" + DateTimeUtils.DATE_SPLIT + "01" + DateTimeUtils.DATE_SPLIT + "01", DateTimeUtils.DATE_FORMAT));
        }

        { /* ??????(??????) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test-cell.xls"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Cell cell = ExcelUtils.getCell(sheet, 6, 0);
            final Object value = ExcelUtils.getCellValue(workbook, cell);

            assertNotNull(value);
            assertTrue(value instanceof Boolean);
            assertEquals(value, Boolean.TRUE);
        }

        { /* ??????(?????????) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test-cell.xls"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Cell cell = ExcelUtils.getCell(sheet, 7, 0);
            final Object value = ExcelUtils.getCellValue(workbook, cell);

            assertNotNull(value);
            assertTrue(value instanceof String);
            assertEquals(value, "#DIV/0!");
        }

        { /* ??????(?????? - ??????) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test-cell.xls"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Cell cell = ExcelUtils.getCell(sheet, 8, 0);
            final Object value = ExcelUtils.getCellValue(workbook, cell);

            assertNotNull(value);
            assertTrue(value instanceof Double);
            assertEquals(value, 3.0);
        }
    }

    @Test
    public void getCellValueTest_xlsx() {
        { /* null */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test-cell.xlsx"));
            final Object value = ExcelUtils.getCellValue(workbook, null);

            assertNull(value);
        }

        { /* null */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test-cell.xlsx"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Cell cell = ExcelUtils.getCell(sheet, 0, 0);
            final Object value = ExcelUtils.getCellValue(null, cell);

            assertNull(value);
        }

        { /* ??????(???????????? - ?????????) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test-cell.xlsx"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Cell cell = ExcelUtils.getCell(sheet, 0, 0);
            final Object value = ExcelUtils.getCellValue(workbook, cell);

            assertNotNull(value);
            System.out.println("(" + cell.getRowIndex() + ", " + cell.getColumnIndex() + ") " + value.getClass().toString());
            assertTrue(value instanceof XSSFRichTextString);
            assertEquals(value.toString(), "testA1");
        }

        { /* ??????(???????????? - ??????) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test-cell.xlsx"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Cell cell = ExcelUtils.getCell(sheet, 1, 0);
            final Object value = ExcelUtils.getCellValue(workbook, cell);

            assertNotNull(value);
            System.out.println("(" + cell.getRowIndex() + ", " + cell.getColumnIndex() + ") " + value.getClass().toString());
            assertTrue(value instanceof Double);
            assertEquals(value, 1.0);
        }

        { /* ??????(???????????? - ??????) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test-cell.xlsx"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Cell cell = ExcelUtils.getCell(sheet, 2, 0);
            final Object value = ExcelUtils.getCellValue(workbook, cell);

            assertNotNull(value);
            System.out.println("(" + cell.getRowIndex() + ", " + cell.getColumnIndex() + ") " + value.getClass().toString());
            assertTrue(value instanceof Double);
            assertEquals(value, 3.0);
        }

        { /* ??????(????????????) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test-cell.xlsx"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Cell cell = ExcelUtils.getCell(sheet, 3, 0);
            final Object value = ExcelUtils.getCellValue(workbook, cell);

            assertNull(value);
        }

        { /* ??????(???????????? - ??????3??????) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test-cell.xlsx"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Cell cell = ExcelUtils.getCell(sheet, 4, 0);
            final Object value = ExcelUtils.getCellValue(workbook, cell);

            assertNotNull(value);
            System.out.println("(" + cell.getRowIndex() + ", " + cell.getColumnIndex() + ") " + value.getClass().toString());
            assertTrue(value instanceof XSSFRichTextString);
            assertEquals(value.toString(), "   ");
        }

        { /* ??????(????????????) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test-cell.xlsx"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Cell cell = ExcelUtils.getCell(sheet, 5, 0);
            final Object value = ExcelUtils.getCellValue(workbook, cell);

            assertNotNull(value);
            System.out.println("(" + cell.getRowIndex() + ", " + cell.getColumnIndex() + ") " + value.getClass().toString());
            assertTrue(value instanceof Date);
            assertEquals(value, DateTimeUtils.parseToDate("2019" + DateTimeUtils.DATE_SPLIT + "01" + DateTimeUtils.DATE_SPLIT + "01", DateTimeUtils.DATE_FORMAT));
        }

        { /* ??????(??????) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test-cell.xlsx"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Cell cell = ExcelUtils.getCell(sheet, 6, 0);
            final Object value = ExcelUtils.getCellValue(workbook, cell);

            assertNotNull(value);
            System.out.println("(" + cell.getRowIndex() + ", " + cell.getColumnIndex() + ") " + value.getClass().toString());
            assertTrue(value instanceof Boolean);
            assertEquals(value, Boolean.TRUE);
        }

        { /* ??????(?????????) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test-cell.xlsx"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Cell cell = ExcelUtils.getCell(sheet, 7, 0);
            final Object value = ExcelUtils.getCellValue(workbook, cell);

            assertNotNull(value);
            System.out.println("(" + cell.getRowIndex() + ", " + cell.getColumnIndex() + ") " + value.getClass().toString());
            assertTrue(value instanceof String);
            assertEquals(value, "#DIV/0!");
        }

        { /* ??????(?????? - ??????) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test-cell.xlsx"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Cell cell = ExcelUtils.getCell(sheet, 8, 0);
            final Object value = ExcelUtils.getCellValue(workbook, cell);

            assertNotNull(value);
            System.out.println("(" + cell.getRowIndex() + ", " + cell.getColumnIndex() + ") " + value.getClass().toString());
            assertTrue(value instanceof Double);
            assertEquals(value, 3.0);
        }

        { /* ??????(???????????? - ?????????) - ????????? */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test-cell.xlsx"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Object value = ExcelUtils.getCellValue(sheet, "A1");

            assertNotNull(value);
            assertTrue(value instanceof XSSFRichTextString);
            assertEquals(value.toString(), "testA1");
        }
    }

    @Test
    public void getCellValueTest_xls_????????????() {
        { /* ??????(????????? - ??????) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test-cell_format.xls"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Cell cell = ExcelUtils.getCell(sheet, 0, 0);
            final Object value = ExcelUtils.getCellValue(workbook, cell);

            assertNotNull(value);
            assertTrue(value instanceof HSSFRichTextString);
            assertEquals(value.toString(), "testA1");
        }

        { /* ??????(????????? - ?????????) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test-cell_format.xls"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Cell cell = ExcelUtils.getCell(sheet, 1, 0);
            final Object value = ExcelUtils.getCellValue(workbook, cell);

            assertNotNull(value);
            assertTrue(value instanceof HSSFRichTextString);
            assertEquals(value.toString(), "testA2");
        }

        { /* ??????(????????? - ????????????) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test-cell_format.xls"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Cell cell = ExcelUtils.getCell(sheet, 2, 0);
            final Object value = ExcelUtils.getCellValue(workbook, cell);

            assertNotNull(value);
            assertTrue(value instanceof HSSFRichTextString);
            assertEquals(value.toString(), "testA3");
        }

        { /* ??????(????????? - ?????????) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test-cell_format.xls"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Cell cell = ExcelUtils.getCell(sheet, 3, 0);
            final Object value = ExcelUtils.getCellValue(workbook, cell);

            assertNotNull(value);
            assertTrue(value instanceof HSSFRichTextString);
            assertEquals(value.toString(), "testA4");
        }
    }

    @Test
    public void getCellValueTest_xlsx_????????????() {
        { /* ??????(????????? - ??????) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test-cell_format.xlsx"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Cell cell = ExcelUtils.getCell(sheet, 0, 0);
            final Object value = ExcelUtils.getCellValue(workbook, cell);

            assertNotNull(value);
            assertTrue(value instanceof XSSFRichTextString);
            assertEquals(value.toString(), "testA1");
        }

        { /* ??????(????????? - ?????????) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test-cell_format.xlsx"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Cell cell = ExcelUtils.getCell(sheet, 1, 0);
            final Object value = ExcelUtils.getCellValue(workbook, cell);

            assertNotNull(value);
            assertTrue(value instanceof XSSFRichTextString);
            assertEquals(value.toString(), "testA2");
        }

        { /* ??????(????????? - ????????????) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test-cell_format.xlsx"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Cell cell = ExcelUtils.getCell(sheet, 2, 0);
            final Object value = ExcelUtils.getCellValue(workbook, cell);

            assertNotNull(value);
            assertTrue(value instanceof XSSFRichTextString);
            assertEquals("testA3", value.toString());
        }

        { /* ??????(????????? - ?????????) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test-cell_format.xlsx"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Cell cell = ExcelUtils.getCell(sheet, 3, 0);
            final Object value = ExcelUtils.getCellValue(workbook, cell);

            assertNotNull(value);
            assertTrue(value instanceof XSSFRichTextString);
            assertEquals(value.toString(), "testA4");
        }

        { /* ??????(???????????? - ?????????????????????) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test-cell_format.xlsx"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Cell cell = ExcelUtils.getCell(sheet, 5, 0);
            final Object value = ExcelUtils.getCellValue(workbook, cell);

            assertNotNull(value);
            assertTrue(value instanceof Double);
            assertEquals(1.0, value);
        }

        { /* ??????(??????????????? - ?????????????????????) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test-cell_format.xlsx"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Cell cell = ExcelUtils.getCell(sheet, 6, 0);
            final Object value = ExcelUtils.getCellValue(workbook, cell);

            assertNotNull(value);
            assertTrue(value instanceof Double);
            assertEquals(1.0, value);
        }

        { /* ??????(???????????? - ?????????????????????) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test-cell_format.xlsx"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Cell cell = ExcelUtils.getCell(sheet, 7, 0);
            final Object value = ExcelUtils.getCellValue(workbook, cell);

            assertNotNull(value);
            assertTrue(value instanceof Double);
            assertEquals(1.0, value);
        }

        { /* ??????(???????????? - ????????????????????????) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test-cell_format.xlsx"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Cell cell = ExcelUtils.getCell(sheet, 8, 0);
            final Object value = ExcelUtils.getCellValue(workbook, cell);

            assertNotNull(value);
            assertTrue(value instanceof XSSFRichTextString);
            assertEquals("OK", value.toString());
        }

        { /* ??????(??????????????? - ????????????????????????) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test-cell_format.xlsx"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Cell cell = ExcelUtils.getCell(sheet, 9, 0);
            final Object value = ExcelUtils.getCellValue(workbook, cell);

            assertNotNull(value);
            assertTrue(value instanceof XSSFRichTextString);
            assertEquals("OK", value.toString());
        }

        { /* ??????(???????????? - ????????????????????????) */
            final Workbook workbook = ExcelUtils.getWorkbook(getResourceFile("testExcel/test-cell_format.xlsx"));
            final Sheet sheet = ExcelUtils.getSheet(workbook, 0);
            final Cell cell = ExcelUtils.getCell(sheet, 10, 0);
            final Object value = ExcelUtils.getCellValue(workbook, cell);

            assertNotNull(value);
            assertTrue(value instanceof XSSFRichTextString);
            assertEquals("OK", value.toString());
        }
    }
}
