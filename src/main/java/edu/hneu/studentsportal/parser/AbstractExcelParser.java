package edu.hneu.studentsportal.parser;

import edu.hneu.studentsportal.service.MessageService;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static java.util.Objects.nonNull;

public abstract class AbstractExcelParser<E> {

    protected Sheet sheet;
    protected Workbook workbook;

    @Resource
    protected MessageService messageService;

    public E parse(File file) {
        try {
            workbook = new XSSFWorkbook(new FileInputStream(file));
            sheet = workbook.getSheetAt(0);
            return extractModel();
        } catch (IOException e) {
            throw new IllegalArgumentException(messageService.fileNotFoundError(file.getPath()));
        }
    }

    public abstract E extractModel();

    protected String getStringCellValue(int row, int col) {
        Row rowValue = getRow(row);
        if (nonNull(rowValue) && nonNull(rowValue.getCell(col)))
            return rowValue.getCell(col).toString();
        return StringUtils.EMPTY;
    }

    protected String getStringCellValue(Indexer indexer, int col) {
        return getStringCellValue(indexer.getValue(), col);
    }

    private Row getRow(int row) {
        return sheet.getRow(row);
    }

    protected String getStringCellValue(int row) {
        return getStringCellValue(row, 0);
    }

    protected String getStringCellValue(Indexer indexer) {
        return getStringCellValue(indexer, 0);
    }

    protected String getString1CellValue(int row) {
        return getStringCellValue(row, 1);
    }

    protected String getString1CellValue(Indexer indexer) {
        return getStringCellValue(indexer, 1);
    }

    protected String getString2CellValue(int row) {
        return getStringCellValue(row, 2);
    }

    protected String getString2CellValue(Indexer indexer) {
        return getStringCellValue(indexer, 2);
    }

    protected Integer getIntegerCellValue(int row, int col) {
        return (int) sheet.getRow(row).getCell(col).getNumericCellValue();
    }

    protected Integer getIntegerCellValue(Indexer indexer, int col) {
        return getIntegerCellValue(indexer.getValue(), col);
    }

}
