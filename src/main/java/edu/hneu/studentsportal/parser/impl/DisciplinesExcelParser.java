package edu.hneu.studentsportal.parser.impl;


import com.google.common.collect.Lists;
import edu.hneu.studentsportal.domain.Discipline;
import edu.hneu.studentsportal.domain.EducationProgram;
import edu.hneu.studentsportal.domain.Speciality;
import edu.hneu.studentsportal.enums.DisciplineDegree;
import edu.hneu.studentsportal.parser.AbstractExcelParser;
import edu.hneu.studentsportal.parser.Indexer;
import edu.hneu.studentsportal.repository.EducationProgramRepository;
import edu.hneu.studentsportal.repository.SpecialityRepository;
import javaslang.control.Try;
import lombok.extern.log4j.Log4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

import static java.util.Objects.nonNull;
import static org.apache.commons.lang.BooleanUtils.isFalse;
import static org.apache.commons.lang.StringUtils.isEmpty;

@Log4j
@Component
@Scope("prototype")
public class DisciplinesExcelParser extends AbstractExcelParser<List<Discipline>> {

    @Resource
    private SpecialityRepository specialityRepository;
    @Resource
    private EducationProgramRepository educationProgramRepository;

    @Override
    public List<Discipline> extractModel() {
        List<Discipline> disciplines = Lists.newArrayList();
        Indexer indexer = Indexer.of(0);

        while (isNotFileEnd(indexer.next())) {
            Try.run(() -> {
                Discipline discipline = Discipline.builder()
                        .speciality(parseSpeciality(indexer))
                        .educationProgram(parseEducationProgram(indexer))
                        .course(getIntegerCellValue(indexer, 3))
                        .degree(parseDegree(indexer))
                        .label(getStringCellValue(indexer, 6))
                        .credits(getIntegerCellValue(indexer, 7))
                        .semester(parseSemester(indexer))
                        .build();
                disciplines.add(discipline);
            }).onFailure(e -> log.warn(e.getMessage(), e));
        }
        return disciplines;
    }

    private boolean isNotFileEnd(int row) {
        return isFalse(isEmpty(getString1CellValue(row)));
    }

    private Speciality parseSpeciality(Indexer indexer) {
        String code = parseCode(getString1CellValue(indexer));
        return specialityRepository.findByCode(String.valueOf(code))
                .orElseThrow(() -> new IllegalStateException("Cannot find speciality for code: " + code));
    }

    private EducationProgram parseEducationProgram(Indexer indexer) {
        String code = parseCode(getString2CellValue(indexer));
        return educationProgramRepository.findByCode(code)
                .orElseThrow(() -> new IllegalStateException("Cannot find education program for code: " + code));
    }

    private String parseCode(String line) {
        return line.split("\\.")[0];
    }

    private DisciplineDegree parseDegree(Indexer indexer) {
        return DisciplineDegree.parse(getStringCellValue(indexer, 4).toLowerCase());
    }

    private int parseSemester(Indexer indexer) {
        return nonNull(getStringCellValue(indexer, 8)) ? 1 : 2;
    }
}
