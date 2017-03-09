package edu.hneu.studentsportal.listener;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import edu.hneu.studentsportal.model.User;
import edu.hneu.studentsportal.model.type.UserRole;
import edu.hneu.studentsportal.service.StudentService;
import edu.hneu.studentsportal.service.UserService;

@Component
public class InitializationAdminUserOnStartupListener implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger LOG = Logger.getLogger(InitializationAdminUserOnStartupListener.class);

    private static final String ADMIN_USER_ID = "oleksandr.rozdolskyi2012@gmail.com";

    @Autowired
    private UserService userService;
    @Autowired
    private StudentService studentService;

    @Override
    public void onApplicationEvent(final ContextRefreshedEvent contextRefreshedEvent) {
        final User admin = new User();
        admin.setId(ADMIN_USER_ID);
        admin.setRole(UserRole.ADMIN);
        userService.save(admin);
        LOG.info("Default admin was created!");

        // studentService.refreshMarks();
        // LOG.info("All marks refreshed");
    }

}
