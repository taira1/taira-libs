package jp.taira.libs.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.OldExcelFormatException;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

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
}
