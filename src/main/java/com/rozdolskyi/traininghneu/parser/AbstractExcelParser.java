package com.rozdolskyi.traininghneu.parser;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.io.IOException;

import static java.util.Objects.nonNull;

public abstract class AbstractExcelParser<E> {

    protected Sheet sheet;
    protected Integer currentIndex = 0;
    protected Workbook workbook;

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

    protected Row getRow(int rowNumber) {
        return sheet.getRow(rowNumber);
    }

    protected String getStringCellValue(int rowNumber, int cellNumber) {
        Row row = getRow(rowNumber);
        if (nonNull(row) && nonNull(row.getCell(cellNumber)))
            return row.getCell(cellNumber).toString();
        return StringUtils.EMPTY;
    }

    protected Integer getIntegerCellValue(int row, int cell) {
        return (int) sheet.getRow(row).getCell(cell).getNumericCellValue();
    }

}
