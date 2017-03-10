import org.springframework.jdbc.datasource.DriverManagerDataSource
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean

beans {

    dataSource(DriverManagerDataSource) {
        driverClassName = "com.mysql.jdbc.Driver"
        url = "jdbc:mysql://localhost:3306/stud_portal"
        username = "root"
        password = "root"
    }

    entityManagerFactory(LocalContainerEntityManagerFactoryBean) {
        dataSource = dataSource
        packagesToScan = "edu.hneu.studentsportal.entity"
        jpaProperties = [
                "hibernate.dialect"     : "org.hibernate.dialect.MySQL5InnoDBDialect",
                "hibernate.hbm2ddl.auto": "create-update",
                "hibernate.show_sql"    : true,
                "hibernate.format_sql"  : true
        ]
    }

    transactionManager(JpaTransactionManager) {
        entityManagerFactory = entityManagerFactory
    }

}