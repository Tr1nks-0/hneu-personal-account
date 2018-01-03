package edu.hneu.studentsportal.repository;

import edu.hneu.studentsportal.domain.Discipline;
import edu.hneu.studentsportal.domain.EducationProgram;
import edu.hneu.studentsportal.domain.Speciality;
import edu.hneu.studentsportal.enums.DisciplineType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DisciplineRepository extends JpaRepository<Discipline, Long>, JpaSpecificationExecutor<Discipline> {

    Optional<Discipline> findById(long disciplineId);

    @Query(value = "SELECT MAX(course) FROM discipline WHERE speciality_id=:specialityId AND education_program_id=:educationProgramId", nativeQuery = true)
    Integer getLastCourse(@Param("specialityId") long specialityId, @Param("educationProgramId") long educationProgramId);

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    class DisciplineSpecifications {

        public static Specifications<Discipline> hasCode(String code) {
            return Specifications.where((root, query, cb) -> cb.and(cb.equal(root.get("code"), code)));
        }

        public static Specifications<Discipline> hasCourseAndSemester(Integer course, Integer semester) {
            return Specifications.where((root, query, cb) -> cb.and(cb.equal(root.get("course"), course), cb.equal(root.get("semester"), semester)));
        }

        public static Specifications<Discipline> hasSpeciality(Speciality speciality) {
            return Specifications.where((root, query, cb) -> cb.and(cb.equal(root.get("speciality"), speciality)));
        }

        public static Specifications<Discipline> hasEducationProgram(EducationProgram educationProgram) {
            return Specifications.where((root, query, cb) -> cb.and(cb.equal(root.get("educationProgram"), educationProgram)));
        }

        public static Specifications<Discipline> isNotTemporal() {
            return Specifications.where((root, query, cb) -> cb.and(
                    cb.or(cb.equal(root.get("type"), DisciplineType.REGULAR), cb.isTrue(root.get("secondary"))))
            );
        }
    }
}
