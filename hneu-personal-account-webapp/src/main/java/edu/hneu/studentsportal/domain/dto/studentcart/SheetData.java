package edu.hneu.studentsportal.domain.dto.studentcart;

import lombok.Data;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class SheetData {
    private static final DateFormat DATE_FORMAT=new SimpleDateFormat("yyyy-MM-dd");

    private Date date;
    private Integer number;
    private String title;
    private String content;
    public SheetData(Date date, Integer number, String title, String content) {
        this.date = date;
        this.number = number;
        this.title = title;
        this.content = content;
    }

    public String getFormattedDateString() {
        return DATE_FORMAT.format(date);
    }
}
