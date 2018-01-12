package edu.hneu.studentsportal.domain.dto;

import edu.hneu.studentsportal.domain.EducationProgram;
import edu.hneu.studentsportal.domain.Faculty;
import edu.hneu.studentsportal.domain.Group;
import edu.hneu.studentsportal.domain.Speciality;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO {
    @NotNull
    private Faculty faculty;
    @NotNull
    private Speciality speciality;
    @NotNull
    private EducationProgram educationProgram;
    @NotNull
    private Group group;
    @NotBlank
    @Length(max = 200)
    private String name;
    @NotBlank
    @Length(max = 200)
    private String surname;
    @Pattern(regexp = "\\d+")
    private String passportNumber;
    @NotNull
    private Long scheduleStudentId;
    @NotNull
    private Integer incomeYear;
    @NotEmpty
    private List<String> contactInfo;
    @NotNull
    private MultipartFile photo;
}
