package edu.hneu.studentsportal.repository.impl;

import edu.hneu.studentsportal.entity.Student;
import edu.hneu.studentsportal.repository.StudentDao;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DefaultStudentDao implements StudentDao {

    private static final int ITEMS_ON_PAGE_COUNT = 10;

    @Override
    public List<Student> find(final String fullName, final Integer page) {
        return null;
    }

    @Override
    public Student find(final String subKey, final String groupCode) {
        return null;
    }

    @Override
    public long getPagesCountForSearchCriteria(final String searchCriteria) {
        return 0;
    }

    /*@Autowired
    private MongoOperations mongoOperations;

    @Override
    public Student findById(String id) {
        return mongoOperations.findOne(Query.query(Criteria.where("id").is(id)), StudentProfile.class);
    }

    @Override
    public void save(Student studentProfile) {
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
        Student profile = mongoOperations.findById(id, StudentProfile.class);
        mongoOperations.remove(profile);
    }

    @Override
    public List<StudentProfile> findAll() {
        return mongoOperations.findAll(StudentProfile.class);
    }

    @Override
    public Student find(String subKey, String groupCode) {
        Pattern subKeyPattern = Pattern.compile(subKey, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
        Query query = Query.query(new Criteria().andOperator(Criteria.where("id").regex(subKeyPattern), Criteria.where("group").is(groupCode)));
        return mongoOperations.findOne(query, StudentProfile.class);
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
    }*/
}
