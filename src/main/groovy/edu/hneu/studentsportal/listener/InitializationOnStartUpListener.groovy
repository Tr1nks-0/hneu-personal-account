package edu.hneu.studentsportal.listener

import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.core.io.ClassPathResource
import org.springframework.jdbc.datasource.init.DatabasePopulator
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator
import org.springframework.stereotype.Component

import javax.annotation.Resource
import javax.sql.DataSource

@Component
//@Profile("init")
class InitializationOnStartUpListener implements ApplicationListener<ContextRefreshedEvent> {

    @Resource
    DataSource dataSource

    @Override
    void onApplicationEvent(ContextRefreshedEvent event) {
        def users = new ClassPathResource("scripts/users.sql")
        def faculties = new ClassPathResource("scripts/faculties.sql")
        def specialities = new ClassPathResource("scripts/specialities.sql")
        def educationPrograms = new ClassPathResource("scripts/educationPrograms.sql")
        def groups = new ClassPathResource("scripts/groups.sql")
        def disciplines = new ClassPathResource("scripts/disciplines.sql")

        def databasePopulator = new ResourceDatabasePopulator(users, faculties, specialities, educationPrograms, groups, disciplines)
        DatabasePopulatorUtils.execute(databasePopulator, dataSource)
    }

}
