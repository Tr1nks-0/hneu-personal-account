package edu.hneu.studentsportal.listener;

import edu.hneu.studentsportal.repository.DisciplineRepository;
import edu.hneu.studentsportal.repository.GroupRepository;
import edu.hneu.studentsportal.service.StudentService;
import edu.hneu.studentsportal.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;

@Component
public class InitializationAdminUserOnStartupListener implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger LOG = Logger.getLogger(InitializationAdminUserOnStartupListener.class);

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
        ClassPathResource initData = new ClassPathResource("scripts/init.sql");
        DatabasePopulator databasePopulator = new ResourceDatabasePopulator(initData);
        DatabasePopulatorUtils.execute(databasePopulator, dataSource);
    }
}
