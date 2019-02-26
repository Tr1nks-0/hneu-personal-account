package edu.hneu.studentsportal.service;

import edu.hneu.studentsportal.domain.Sheet;
import edu.hneu.studentsportal.domain.dto.studentcart.SheetData;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class SheetService {

    public SheetData mapSheet(Sheet sheet) {
        if (Objects.isNull(sheet)) {
            return null;
        }
        return new SheetData(sheet.getDate(), sheet.getNumber(), sheet.getContent());
    }
}
