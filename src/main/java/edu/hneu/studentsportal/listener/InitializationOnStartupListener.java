package edu.hneu.studentsportal.listener;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

import edu.hneu.studentsportal.repository.DisciplineRepository;
import edu.hneu.studentsportal.repository.GroupRepository;
import edu.hneu.studentsportal.service.StudentService;
import edu.hneu.studentsportal.service.UserService;

@Component
public class InitializationOnStartupListener implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger LOG = Logger.getLogger(InitializationOnStartupListener.class);

    private static final String ADMIN_USER_ID = "oleksandr.rozdolskyi2012@gmail.com";

    @Autowired
    private UserService userService;
    @Autowired
    private StudentService studentService;

    @Resource
    private GroupRepository groupRepository;
    @Resource
    private DisciplineRepository disciplineRepository;
    @Resource
    private DataSource dataSource;

    @Override
    public void onApplicationEvent(final ContextRefreshedEvent contextRefreshedEvent) {
        ClassPathResource users = new ClassPathResource("scripts/users.sql");
        ClassPathResource faculties = new ClassPathResource("scripts/faculties.sql");
        ClassPathResource specialities = new ClassPathResource("scripts/specialities.sql");
        ClassPathResource educationPrograms = new ClassPathResource("scripts/educationPrograms.sql");
        ClassPathResource groups = new ClassPathResource("scripts/groups.sql");
        ClassPathResource disciplines = new ClassPathResource("scripts/disciplines.sql");

        DatabasePopulator databasePopulator = new ResourceDatabasePopulator(users, faculties, specialities, educationPrograms, groups, disciplines);
        DatabasePopulatorUtils.execute(databasePopulator, dataSource);
    }
}
