package edu.hneu.studentsportal.domain.dto.studentcart;

import lombok.Data;

@Data
public class DisciplineMarkData {


    private DisciplineData discipline;
    private String mark;
    private SheetData markSheet;

    public DisciplineMarkData(DisciplineData discipline, String mark, SheetData markSheet) {
        this.discipline = discipline;
        this.mark = mark;
        this.markSheet = markSheet;
    }


}
