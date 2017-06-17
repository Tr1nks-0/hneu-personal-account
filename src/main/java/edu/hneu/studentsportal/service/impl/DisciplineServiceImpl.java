package edu.hneu.studentsportal.service.impl;

import edu.hneu.studentsportal.domain.Discipline;
import edu.hneu.studentsportal.enums.DisciplineType;
import edu.hneu.studentsportal.repository.DisciplineRepository;
import edu.hneu.studentsportal.service.DisciplineService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class DisciplineServiceImpl implements DisciplineService {

    @Override
    public boolean isMagmaynor(Discipline discipline) {
        return DisciplineType.MAGMAYNOR.equals(discipline.getType());
    }

    @Override
    public boolean isMagmaynorLabel(String label) {
        return label.toLowerCase().contains("маголего");
    }
}
