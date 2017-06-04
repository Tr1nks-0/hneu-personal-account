package edu.hneu.studentsportal.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.jdbc.datasource.DriverManagerDataSource
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.annotation.EnableTransactionManagement

import javax.annotation.Resource
import javax.sql.DataSource

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories('edu.hneu.studentsportal.repository')
class StorageConfig {

    @Resource
    Environment env

    @Bean
    DataSource dataSource() {
        new DriverManagerDataSource(
                driverClassName: env.getRequiredProperty('db.driver'),
                url: env.getRequiredProperty('db.url'),
                username: env.getRequiredProperty('db.username'),
                password: env.getRequiredProperty('db.password')
        )
    }

    @Bean
    LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        new LocalContainerEntityManagerFactoryBean(
                packagesToScan: ['edu.hneu.studentsportal.domain'],
                dataSource: dataSource(),
                jpaVendorAdapter: new HibernateJpaVendorAdapter(),
                jpaProperties: [
                        'hibernate.dialect'     : env.getRequiredProperty('hibernate.dialect'),
                        'hibernate.hbm2ddl.auto': env.getRequiredProperty('hibernate.hbm2ddl.auto'),
                        'hibernate.show_sql'    : env.getRequiredProperty('hibernate.show_sql'),
                        'hibernate.format_sql'  : env.getRequiredProperty('hibernate.format_sql')
                ]
        )
    }

    @Bean
    JpaTransactionManager transactionManager() {
        new JpaTransactionManager(
                entityManagerFactory: entityManagerFactory().getObject()
        )
    }

}
