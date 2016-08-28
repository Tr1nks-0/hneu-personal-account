package edu.hneu.studentsportal.dao.impl;

import edu.hneu.studentsportal.dao.StudentDao;
import edu.hneu.studentsportal.model.StudentProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Repository
public class DefaultStudentDao implements StudentDao {

    private static final int ITEMS_ON_PAGE_COUNT = 10;

    @Autowired
    private MongoOperations mongoOperations;

    @Override
    public StudentProfile findById(String id) {
        return mongoOperations.findById(id, StudentProfile.class);
    }

    @Override
    public void save(StudentProfile studentProfile) {
        studentProfile.setModificationTime(System.nanoTime());
        mongoOperations.save(studentProfile);
    }

    @Override
    public Optional<StudentProfile> findByEmail(String email) {
        List<StudentProfile> profilesForEmail = mongoOperations.find(Query.query(Criteria.where("email").is(email)), StudentProfile.class);
        return profilesForEmail.stream().findFirst();
    }

    @Override
    public void remove(String id) {
        StudentProfile profile = mongoOperations.findById(id, StudentProfile.class);
        mongoOperations.remove(profile);
    }

    @Override
    public List<StudentProfile> findAll() {
        return mongoOperations.findAll(StudentProfile.class);
    }

    @Override
    public List<StudentProfile> find(String searchCriteria, Integer page) {
        Query query = getStudentSearchQuery(searchCriteria);
        query.limit(ITEMS_ON_PAGE_COUNT);
        query.skip(ITEMS_ON_PAGE_COUNT * (page - 1));
        query.with(new Sort(Sort.Direction.DESC, "modificationTime"));
        return mongoOperations.find(query, StudentProfile.class);
    }

    @Override
    public long getPagesCountForSearchCriteria(String searchCriteria) {
        long studentsCount = mongoOperations.count(getStudentSearchQuery(searchCriteria), StudentProfile.class);
        return new BigDecimal(studentsCount).divide(new BigDecimal(ITEMS_ON_PAGE_COUNT), RoundingMode.UP).longValue();
    }

    private Query getStudentSearchQuery(String searchCriteria) {
        if(nonNull(searchCriteria) && !searchCriteria.isEmpty()) {
            searchCriteria = searchCriteria.trim();
            Pattern pattern = Pattern.compile(searchCriteria.replace(" ", "|"), Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
            int wordsCount = searchCriteria.split(" ").length;
            if (wordsCount == 1) {
                return Query.query(new Criteria().orOperator(
                        Criteria.where("name").regex(pattern),
                        Criteria.where("surname").regex(pattern),
                        Criteria.where("group").regex(pattern))
                );
            } else if(wordsCount == 2) {
                return Query.query(new Criteria().andOperator(
                        Criteria.where("name").regex(pattern),
                        Criteria.where("surname").regex(pattern))
                );
            } else {
                return Query.query(new Criteria().andOperator(
                        Criteria.where("name").regex(pattern),
                        Criteria.where("surname").regex(pattern),
                        Criteria.where("group").regex(pattern))
                );
            }
        } else {
            return new Query();
        }
    }
}
