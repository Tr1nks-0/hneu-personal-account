package com.rozdolskyi.traininghneu.service.impl;


import com.rozdolskyi.traininghneu.dao.StudentDao;
import com.rozdolskyi.traininghneu.model.StudentProfile;
import com.rozdolskyi.traininghneu.parser.PointsExcelParser;
import com.rozdolskyi.traininghneu.parser.StudentProfileExcelExcelParser;
import com.rozdolskyi.traininghneu.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class DefaultStudentService implements StudentService {

    @Autowired
    private StudentDao studentDao;

    @Override
    public StudentProfile readStudentProfilesFromFile(File file) {
        return new StudentProfileExcelExcelParser().parse(file);
    }

    @Override
    public void save(StudentProfile studentProfile) {
        studentDao.save(studentProfile);
    }

    @Override
    public StudentProfile findStudentProfileById(String id) {
        return studentDao.findById(id);
    }

    @Override
    public Optional<StudentProfile> findStudentProfileByEmail(String email) {
        return studentDao.findByEmail(email);
    }

    @Override
    public void updateStudentsScoresFromFiles(List<File> files) {
        for (File file : files) {
            Map<String, Map<String, String>> studentsScores = new PointsExcelParser().parse(file);

        }
    }

    @Override
    public List<StudentProfile> findAll() {
        return studentDao.findAll();
    }

    public void setStudentDao(StudentDao studentDao) {
        this.studentDao = studentDao;
    }
}
