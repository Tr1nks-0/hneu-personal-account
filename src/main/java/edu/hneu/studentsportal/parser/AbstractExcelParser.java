package edu.hneu.studentsportal.parser;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.MessageSource;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Locale;

import static java.util.Objects.nonNull;

public abstract class AbstractExcelParser<E> {

    protected Sheet sheet;
    protected Workbook workbook;

    @Resource
    protected MessageSource messageSource;

    public E parse(File file) {
        try {
            workbook = new XSSFWorkbook(new FileInputStream(file));
            sheet = workbook.getSheetAt(0);
            return extractModel();
        } catch (IOException e) {
            throw new IllegalArgumentException(messageSource.getMessage("invalid.student.profile.file.not.found", new Object[] {file.toPath()}, Locale.getDefault()));
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
