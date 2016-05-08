package edu.hneu.studentsportal.dao.impl;

import edu.hneu.studentsportal.dao.StudentDao;
import edu.hneu.studentsportal.model.StudentProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class DefaultStudentDao implements StudentDao {

    @Autowired
    private MongoOperations mongoOperations;

    @Override
    public StudentProfile findById(String id) {
        return mongoOperations.findById(id, StudentProfile.class);
    }

    @Override
    public void save(StudentProfile studentProfile) {
        mongoOperations.save(studentProfile);
    }

    @Override
    public Optional<StudentProfile> findByEmail(String email) {
        List<StudentProfile> profilesForEmail = mongoOperations.find(Query.query(Criteria.where("email").is(email)), StudentProfile.class);
        return profilesForEmail.stream().findFirst();
    }

    @Override
    public List<StudentProfile> findAll() {
        return mongoOperations.findAll(StudentProfile.class);
    }
}
