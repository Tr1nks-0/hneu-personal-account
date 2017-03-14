package edu.hneu.studentsportal.parser;

import static java.util.Objects.nonNull;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import lombok.Data;

public abstract class AbstractExcelParser<E> {

    Sheet sheet;
    Workbook workbook;

    public E parse(File file) {
        try {
            workbook = WorkbookFactory.create(file);
            sheet = workbook.getSheetAt(0);
            return extractModel();
        } catch (InvalidFormatException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public abstract E extractModel();

    Row getRow(int rowNumber) {
        return sheet.getRow(rowNumber);
    }

    String getStringCellValue(int rowNumber, int cellNumber) {
        Row row = getRow(rowNumber);
        if (nonNull(row) && nonNull(row.getCell(cellNumber)))
            return row.getCell(cellNumber).toString();
        return StringUtils.EMPTY;
    }

    String getStringCellValue(int rowNumber) {
        return getStringCellValue(rowNumber, 0);
    }

    String getString1CellValue(int rowNumber) {
        return getStringCellValue(rowNumber, 1);
    }

    String getString2CellValue(int rowNumber) {
        return getStringCellValue(rowNumber, 2);
    }

    Integer getIntegerCellValue(int row, int cell) {
        return (int) sheet.getRow(row).getCell(cell).getNumericCellValue();
    }

    @Data
    static class Indexer {

        int value = 0;

        private Indexer(int start) {
            value = start;
        }

        public static Indexer of(int start) {
            return new Indexer(start);
        }

        int next() {
            return ++value;
        }
    }

}
