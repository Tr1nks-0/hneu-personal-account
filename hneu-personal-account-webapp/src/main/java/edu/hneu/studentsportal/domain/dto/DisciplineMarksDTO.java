package edu.hneu.studentsportal.domain.dto;

import edu.hneu.studentsportal.domain.DisciplineMark;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DisciplineMarksDTO {
    List<DisciplineMark> marks = new ArrayList<>();
}
