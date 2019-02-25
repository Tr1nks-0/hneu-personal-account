package edu.hneu.studentsportal.domain;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Entity
@Table(name = "sheet")
public class Sheet {

    @Id
    @Min(value = 0)
    @Max(value = Integer.MAX_VALUE)
    private long id;

    @NotNull
    private Date date;

    @NotNull
    @Min(value = 0)
    private Integer number;

    @NotNull
    @Length(min = 3, max = 100)
    private String title;

    @NotNull
    @Length(min = 3, max = 256)
    private String content;
}
