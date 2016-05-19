package edu.hneu.studentsportal.listener;


import edu.hneu.studentsportal.model.User;
import edu.hneu.studentsportal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class InitializationAdminUserOnStartupListener implements ApplicationListener<ContextRefreshedEvent> {

    private static final String ADMIN_USER_ID = "admin";
    private static final String PASSWORD = "adminnimda";
    public static final int ADMIN_ROLE = 1;

    @Autowired
    private UserService userService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if(Objects.isNull(userService.getUserForId(ADMIN_USER_ID))) {
            User admin = new User();
            admin.setId(ADMIN_USER_ID);
            admin.setPassword(PASSWORD);
            admin.setRole(ADMIN_ROLE);
            userService.save(admin);
        }
    }

}
