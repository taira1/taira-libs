package jp.taira.libs.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.OldExcelFormatException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.poifs.crypt.CryptoFunctions;
import org.apache.poi.ss.formula.FormulaParseException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.io.*;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * Excelユーティリティクラス
 */
@Slf4j
public class ExcelUtils {

    private ExcelUtils() {
        throw new IllegalAccessError("Constants class.");
    }

    /**
     * ワークブックを取得する。
     *
     * @param inputStream 入力ストリーム
     * @return Workbookオブジェクト
     */
    public static Workbook getWorkbook(final InputStream inputStream) {
        try {
            return WorkbookFactory.create(inputStream);
        } catch (OldExcelFormatException e) {
            throw new OldExcelFormatException(e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    /**
     * ワークブックを取得する。
     *
     * @param path ファイルのパス
     * @return Workbookオブジェクト
     */
    public static Workbook getWorkbook(final Path path) {
        try {
            return getWorkbook(new FileInputStream(path.toAbsolutePath().toString()));
        } catch (OldExcelFormatException e) {
            throw new OldExcelFormatException(e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    /**
     * ワークブックを取得する。
     *
     * @param workbook ワークブック
     * @return 入力ストリーム
     */
    public static InputStream getWorkbook(final Workbook workbook) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            workbook.write(baos);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
        byte[] barray = baos.toByteArray();

        return new ByteArrayInputStream(barray);
    }

    /**
     * シートを取得する。<br>
     * 0番目のシートを取得する。
     *
     * @param workbook ワークブック
     * @return Sheetオブジェクト
     */
    public static Sheet getSheet(final Workbook workbook) {
        return getSheet(workbook, 0);
    }

    /**
     * シートを取得する。<br>
     * 指定したインデックスのシートを取得する。
     *
     * @param workbook ワークブック
     * @param sheetIndex シートのインデックス
     * @return シート
     */
    public static Sheet getSheet(final Workbook workbook, final int sheetIndex) {
        try {
            return workbook.getSheetAt(sheetIndex);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    /**
     * シートを取得する。<br>
     * 指定したシート名のシートを取得する。
     *
     * @param workbook ワークブック
     * @param sheetName シート名
     * @return Sheetオブジェクト
     */
    public static Sheet getSheet(final Workbook workbook, final String sheetName) {
        try {
            return workbook.getSheet(sheetName);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    /**
     * シートを生成する。<br>
     * 指定したテンプレート名のシートをコピーして、指定した名称のシートを生成する。<br>
     * テンプレートシートは削除する。
     *
     * @param workbook ワークブック
     * @param templateSheetName テンプレートシート名
     * @param sheetName シート名
     * @return 生成したSheetオブジェクト
     */
    public static Sheet createSheet(final Workbook workbook, final String templateSheetName, final String sheetName) {
        final List<Sheet> sheetList = createSheets(workbook, templateSheetName, true, sheetName);

        return CollectionUtils.isEmpty(sheetList) ? null : sheetList.get(0);
    }

    /**
     * シートを生成する。<br>
     * 指定したテンプレート名のシートをコピーして、指定した名称分のシートを生成する。<br>
     * テンプレートシートは削除する。
     *
     * @param workbook ワークブック
     * @param templateSheetName テンプレートシート名
     * @param sheetNames シート名配列
     * @return 生成したSheetオブジェクトのリスト
     */
    public static List<Sheet> createSheets(final Workbook workbook, final String templateSheetName, final String... sheetNames) {
        return createSheets(workbook, templateSheetName, true, sheetNames);
    }

    /**
     * シートを生成する。<br>
     * 指定したテンプレート名のシートをコピーして、指定した名称分のシートを生成する。
     *
     * @param workbook ワークブック
     * @param templateSheetName テンプレートシート名
     * @param templateDelete テンプレートシートを削除する場合はtrue、そうでない場合はfalse。
     * @param sheetNames シート名配列
     * @return 生成したSheetオブジェクトのリスト
     */
    private static List<Sheet> createSheets(final Workbook workbook, final String templateSheetName, final boolean templateDelete, final String... sheetNames) {
        if (workbook == null) {
            log.error("workbook is null.");
            return null;
        }

        if (StringUtils.isEmpty(templateSheetName)) {
            log.error("templateSheetName is null.");
            return null;
        }

        final Sheet templateSheet = getSheet(workbook, templateSheetName);
        if (templateSheet == null) {
            log.error("templateSheet is null.");
            return null;
        }

        if (sheetNames == null || sheetNames.length < 1) {
            log.error("sheetNames is empty.");
            return null;
        }

        List<Sheet> sheetList = new LinkedList<>();
        final PrintSetup templatePs = templateSheet.getPrintSetup();
        for (final String sheetName : sheetNames) {
            if (StringUtils.isBlank(sheetName)) {
                continue;
            }

            final Sheet sheet = workbook.cloneSheet(workbook.getSheetIndex(templateSheetName));
            workbook.setSheetName(workbook.getSheetIndex(sheet), sheetName);

            // 印刷設定
            final PrintSetup ps = sheet.getPrintSetup();
            ps.setCopies(templatePs.getCopies());
            ps.setDraft(templatePs.getDraft());
            ps.setFitHeight(templatePs.getFitHeight());
            ps.setFitWidth(templatePs.getFitWidth());
            ps.setFooterMargin(templatePs.getFooterMargin());
            ps.setHeaderMargin(templatePs.getHeaderMargin());
            ps.setHResolution(templatePs.getHResolution());
            ps.setLandscape(templatePs.getLandscape());
            ps.setLeftToRight(templatePs.getLeftToRight());
            ps.setNoColor(templatePs.getNoColor());
            ps.setNoOrientation(templatePs.getNoOrientation());
            ps.setNotes(templatePs.getNotes());
            ps.setPaperSize(templatePs.getPaperSize());
            ps.setScale(templatePs.getScale());
            ps.setUsePage(templatePs.getUsePage());
            ps.setValidSettings(templatePs.getValidSettings());
            ps.setVResolution(templatePs.getVResolution());

            sheetList.add(sheet);
        }

        if (templateDelete) {
            removeSheet(workbook, templateSheetName);
        }

        return sheetList;
    }

    /**
     * シートをコピーする。
     *
     * @param workbook ワークブック
     * @param fromSheetName コピー元シート名
     * @param toSheetName コピー先シート名
     * @return 生成したSheetオブジェクト
     */
    public static Sheet copySheet(final Workbook workbook, final String fromSheetName, final String toSheetName) {
        final List<Sheet> sheetList = createSheets(workbook, fromSheetName, false, toSheetName);

        return CollectionUtils.isEmpty(sheetList) ? null : sheetList.get(0);
    }

    /**
     * シートをコピーする。
     *
     * @param workbook ワークブック
     * @param fromSheetName コピー元シート名
     * @param toSheetNames コピー先シート名配列
     * @return 生成したSheetオブジェクトのリスト
     */
    public static List<Sheet> copySheets(final Workbook workbook, final String fromSheetName, final String... toSheetNames) {
        return createSheets(workbook, fromSheetName, false, toSheetNames);
    }

    /**
     * シートを削除する。<br>
     * 指定したインデックスのシートを削除する。
     *
     * @param workbook ワークブック
     * @param sheetIndex シートインデックス
     */
    public static void removeSheet(final Workbook workbook, final int sheetIndex) {
        workbook.removeSheetAt(sheetIndex);
    }

    /**
     * シートを削除する。<br>
     * 指定したシート名のシートを削除する。
     *
     * @param workbook ワークブック
     * @param sheetName シート名
     */
    public static void removeSheet(final Workbook workbook, final String sheetName) {
        workbook.removeSheetAt(workbook.getSheetIndex(sheetName));
    }

    /**
     * シートが、指定したパスワードで保護されているか判断する。
     *
     * @param sheet シート
     * @param password パスワード
     * @return 指定したシートが保護されている場合にtrue、指定したシートが保護されていない場合にfalseを返す。
     */
    public static boolean isProtectedSheet(final Sheet sheet, final String password) {
        if (password == null) {
            return false;
        }

        if (sheet instanceof HSSFSheet) {
            return ((HSSFSheet)sheet).getPassword() == (short) CryptoFunctions.createXorVerifier1(password);
        } else if (sheet instanceof XSSFSheet) {
            return ((XSSFSheet)sheet).validateSheetPassword(password);
        }

        return false;
    }

    /**
     * シートが保護されているか判断する。
     *
     * @param sheet シート
     * @return 指定したシートが保護されている場合にtrue、指定したシートが保護されていない場合にfalseを返す。
     */
    public static boolean isProtectedSheet(final Sheet sheet) {
        if (sheet == null) {
            return false;
        }

        return sheet.getProtect();
    }

    /**
     * 行を取得する。<br>
     * 行が存在しない場合は、前の行を指定列まで書式をコピーして生成する。
     *
     * @param sheet シート
     * @param rowIndex 行インデックス
     * @param firstCol 開始列インデックス
     * @param lastCol 終了列インデックス
     * @return Rowオブジェクト
     */
    public static Row getRow(final Sheet sheet, final int rowIndex, final int firstCol, final int lastCol) {
        if (sheet == null) {
            log.error("sheet is null.");
            return null;
        }

        if (rowIndex < 0) {
            log.error("rowIndex({}) is invalid.", rowIndex);
            return null;
        }

        if (firstCol < 0 || lastCol < 0) {
            log.error("firstCol({}) / lastCol({}) is invalid.", firstCol, lastCol);
            return null;
        }

        if (firstCol > lastCol) {
            log.error("firstCol({}) / lastCol({}) is invalid.", firstCol, lastCol);
            return null;
        }

        Row row = sheet.getRow(rowIndex);
        if (row == null) {
            final Row srcRow = sheet.getRow(rowIndex - 1);
            if (srcRow == null) {
                log.error("srcRow(rowIndex={}) is invalid.", rowIndex - 1);
                return null;
            }
            row = sheet.createRow(rowIndex);

            row.setHeight(srcRow.getHeight());

            for (int i = firstCol; i <= lastCol; i++) {
                final Cell srcCell = srcRow.getCell(i);
                final Cell cell = row.createCell(i);

                cell.setCellStyle(srcCell.getCellStyle());
            }
        } else {
            //logger.debug("CellNum: " + row.getFirstCellNum() + " -> " + row.getLastCellNum());
            for (int i = row.getLastCellNum(); i >= row.getFirstCellNum(); i--) {
                if (i < 0) {
                    continue;
                }

                final Cell cell = row.getCell(i);
                if (cell == null) {
                    continue;
                }

                if (i < firstCol || i > lastCol) {
                    row.removeCell(cell);
                }
            }
        }

        return row;
    }

    /**
     * セルを取得する。
     *
     * @param sheet シート
     * @param row Rowオブジェクト
     * @param colIndex 列インデックス
     * @return Cellオブジェクト
     */
    public static Cell getCell(final Sheet sheet, final Row row, final int colIndex) {
        if (sheet == null) {
            log.error("sheet is null.");
            return null;
        }

        if (row == null) {
            log.error("row is null.");
            return null;
        }

        if (colIndex < 0) {
            log.error("colIndex({}) is invalid.", colIndex);
            return null;
        }

        return row.getCell(colIndex);
    }

    /**
     * セルを取得する。
     *
     * @param sheet シート
     * @param rowIndex 行インデックス
     * @param colIndex 列インデックス
     * @return Cellオブジェクト
     */
    public static Cell getCell(final Sheet sheet, final int rowIndex, final int colIndex) {
        if (sheet == null) {
            log.error("sheet is null.");
            return null;
        }

        return getCell(sheet, sheet.getRow(rowIndex), colIndex);
    }

    /**
     * セルを取得する。<br>
     * セルが存在しない場合は、生成する。
     *
     * @param sheet シート
     * @param row Rowオブジェクト
     * @param colIndex 列インデックス
     * @return Cellオブジェクト
     */
    public static Cell getOrCreateCell(final Sheet sheet, final Row row, final int colIndex) {
        final Cell cell = getCell(sheet, row, colIndex);
        return cell != null ? cell : row.createCell(colIndex);
    }

    /**
     * セルの書式を指定して、値を取得する。
     *
     * @param workbook ワークブック
     * @param cell セル
     * @param cellType セルの書式
     * @return セルの値
     */
    public static Object getCellValue(final Workbook workbook, final Cell cell, final CellType cellType) {
        if (workbook == null || cell == null) {
            return null;
        }

        final String cellCoordinate = "(" + cell.getRowIndex() + "," + cell.getColumnIndex() + ")";
        Object result;

        if (cellType == CellType.BLANK) {
            result = null;
        } else if (cellType == CellType.BOOLEAN) {
            result = cell.getBooleanCellValue();
        } else if (cellType == CellType.ERROR) {
            String errorResult;
            try {
                byte errorCode = cell.getErrorCellValue();
                FormulaError formulaError = FormulaError.forInt(errorCode);
                errorResult = formulaError.getString();
            } catch (RuntimeException e) {
                log.debug("Getting error code for {} failed!: {}", cellCoordinate, e.getMessage());
                if (cell instanceof XSSFCell) {
                    errorResult = ((XSSFCell)cell).getErrorCellString();
                } else {
                    log.error("Couldn't handle unexpected error scenario in cell: " + cellCoordinate, e);
                    throw e;
                }
            }
            result = errorResult;
        } else if (cellType == CellType.FORMULA) {
            result = getFormulaCellValue(workbook, cell);
        } else if (cellType == CellType.NUMERIC) {
            if (DateUtil.isCellDateFormatted(cell)) {
                result = cell.getDateCellValue();
            } else {
                result = cell.getNumericCellValue();
            }
        } else if (cellType == CellType.STRING) {
            /* xls -> HSSFRichTextString, xlsx -> XSSFRichTextString */
            result = cell.getRichStringCellValue();
        } else {
            throw new IllegalStateException("Unknown cell type: " + cell.getCellType());
        }

        log.debug("cell{} resolved to value: {}", cellCoordinate, result);

        return result;
    }

    /**
     * セルの値を取得する。
     *
     * @param workbook ワークブック
     * @param cell セル
     * @return セルの値
     */
    public static Object getCellValue(final Workbook workbook, final Cell cell) {
        if (cell == null) {
            return null;
        }

        return getCellValue(workbook, cell, cell.getCellType());
    }

    /**
     * セルの値を取得する。
     *
     * @param sheet シート
     * @param cellName セル名
     * @return セルの値
     */
    public static Object getCellValue(Sheet sheet, String cellName) {
        CellReference cellReference = new CellReference(cellName);
        Row row = sheet.getRow(cellReference.getRow());
        if (row == null) {
            return null;
        }

        return getCellValue(sheet.getWorkbook(), row.getCell(cellReference.getCol()));
    }

    /**
     * セル(数式)の値を取得する。
     *
     * @param workbook ワークブック
     * @param cell セル
     * @return セルの値
     */
    protected static Object getFormulaCellValue(final Workbook workbook, final Cell cell) {
        final CellType cachedFormulaResultType = cell.getCachedFormulaResultType();
        try {
            if (cachedFormulaResultType == CellType.NUMERIC) {
                return cell.getNumericCellValue();
            }
            if (cachedFormulaResultType == CellType.STRING) {
                return cell.getRichStringCellValue();
            }
        } catch (Exception e) {
            log.error("Failed to fetch cached/precalculated formula value of cell: " + cell, e);
        }

        try {
            log.info("cell({},{}) is a formula. Attempting to evaluate: {}", cell.getRowIndex(), cell.getColumnIndex(), cell.getCellFormula());
            final FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
            final Cell evaluatedCell = evaluator.evaluateInCell(cell);
            return getCellValue(workbook, evaluatedCell);
        } catch (Exception e) {
            log.warn("Exception occurred while evaluating formula at position ({},{}): {}", cell.getRowIndex(), cell.getColumnIndex(), e.getMessage());

            if (e instanceof FormulaParseException) {
                log.error("Parse exception occurred while evaluating cell formula: " + cell, e);
            } else if (e instanceof IllegalArgumentException) {
                log.error("Illegal formula argument occurred while evaluating cell formula: " + cell, e);
            } else {
                log.error("Unexpected exception occurred while evaluating cell formula: " + cell, e);
            }
        }

        return cell.getCellFormula();
    }

    /**
     * セル種別をセットする。
     *
     * @param cell セル
     * @param cellType セル種別
     */
    public static void setCellType(final Cell cell, final CellType cellType) {
        if (cell instanceof XSSFCell) {
            ((XSSFCell)cell).setCellType(cellType);
        } else if (cell instanceof HSSFCell) {
            ((HSSFCell)cell).setCellType(cellType);
        }
    }

    /**
     * シートの条件付き書式をセットする。
     *
     * @param sheet シート
     */
    public static void conditionalFormat(final Sheet sheet) {
        conditionalFormat(sheet, 0);
    }

    /**
     * シートの条件付き書式をセットする。
     *
     * @param sheet シート
     * @param rowIndexFrom 開始行インデックス
     */
    public static void conditionalFormat(final Sheet sheet, final int rowIndexFrom) {
        final SheetConditionalFormatting formatting = sheet.getSheetConditionalFormatting();
        for (int i = 0; i < formatting.getNumConditionalFormattings(); i++) {
            final ConditionalFormatting format = formatting.getConditionalFormattingAt(i);
            final CellRangeAddress[] ranges = format.getFormattingRanges();
            if (rowIndexFrom <= ranges[0].getFirstRow()) {
                for (CellRangeAddress range : ranges) {
                    range.setLastRow(sheet.getLastRowNum());
                }
                format.setFormattingRanges(ranges);
            }
        }
    }

    /**
     * ワークブックからバイト配列を取得する。
     *
     * @param workbook ワークブック
     * @return バイト配列
     */
    public static byte[] getBytes(final Workbook workbook) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            workbook.write(baos);
            return baos.toByteArray();
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    /**
     * Excelファイルを指定パスに出力する。
     *
     * @param workbook ワークブック
     * @param path ファイルのパス
     */
    public static void output(final Workbook workbook, final Path path) {
        try (BufferedInputStream bis = new BufferedInputStream(new ByteArrayInputStream(Objects.requireNonNull(getBytes(workbook))));
             BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(path.toAbsolutePath().toString()))) {
            byte[] buff = new byte[4096];
            int len = 0;
            while ((len = bis.read(buff)) != -1) {
                bos.write(buff, 0, len);
            }
            bos.flush();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
