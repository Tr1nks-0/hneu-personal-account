package edu.hneu.studentsportal.parser;

import edu.hneu.studentsportal.domain.EducationProgram;
import edu.hneu.studentsportal.domain.Speciality;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class DisciplineDTO {
    private final Speciality speciality;
    private final EducationProgram educationProgram;
    private int course;
    private int semester;
    private String label;

    public DisciplineDTO(Speciality speciality, EducationProgram educationProgram, int course, int semester) {
        this.speciality = speciality;
        this.educationProgram = educationProgram;
        this.course = course;
        this.semester = semester;
    }
}
