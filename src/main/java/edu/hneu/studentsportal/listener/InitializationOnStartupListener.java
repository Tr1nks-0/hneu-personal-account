package edu.hneu.studentsportal.listener;

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
//@Profile("init")
public class InitializationOnStartupListener implements ApplicationListener<ContextRefreshedEvent> {

    @Resource
    private DataSource dataSource;

    @Override
    public void onApplicationEvent(final ContextRefreshedEvent contextRefreshedEvent) {
        final ClassPathResource users = new ClassPathResource("scripts/users.sql");
        final ClassPathResource faculties = new ClassPathResource("scripts/faculties.sql");
        final ClassPathResource specialities = new ClassPathResource("scripts/specialities.sql");
        final ClassPathResource educationPrograms = new ClassPathResource("scripts/educationPrograms.sql");
        final ClassPathResource groups = new ClassPathResource("scripts/groups.sql");
        final ClassPathResource disciplines = new ClassPathResource("scripts/disciplines.sql");

        final DatabasePopulator databasePopulator = new ResourceDatabasePopulator(users, faculties, specialities, educationPrograms, groups, disciplines);
        DatabasePopulatorUtils.execute(databasePopulator, dataSource);
    }
}
