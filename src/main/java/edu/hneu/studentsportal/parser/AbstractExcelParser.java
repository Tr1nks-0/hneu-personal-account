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

import edu.hneu.studentsportal.parser.exception.ParseErrorCodes;
import edu.hneu.studentsportal.parser.exception.ParseException;

public abstract class AbstractExcelParser<E> {

    protected Sheet sheet;
    protected Workbook workbook;

    public E parse(File file) {
        try {
            workbook = WorkbookFactory.create(file);
            sheet = workbook.getSheetAt(0);
            return extractModel();
        } catch (InvalidFormatException | IOException e) {
            throw new ParseException(ParseErrorCodes.NOT_SUPPORTED_FORMAT);
        }
    }

    public abstract E extractModel();

    protected String getStringCellValue(int row, int col) {
        Row rowValue = getRow(row);
        if (nonNull(rowValue) && nonNull(rowValue.getCell(col)))
            return rowValue.getCell(col).toString();
        return StringUtils.EMPTY;
    }

    private Row getRow(int row) {
        return sheet.getRow(row);
    }

    protected String getStringCellValue(int row) {
        return getStringCellValue(row, 0);
    }

    protected String getString1CellValue(int row) {
        return getStringCellValue(row, 1);
    }

    protected String getString2CellValue(int row) {
        return getStringCellValue(row, 2);
    }

    protected Integer getIntegerCellValue(int row, int col) {
        return (int) sheet.getRow(row).getCell(col).getNumericCellValue();
    }

}
