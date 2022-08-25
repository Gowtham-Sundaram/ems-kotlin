package com.ems.ems_koltin.db_config.dept_db

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.persistence.EntityManagerFactory
import javax.sql.DataSource

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    entityManagerFactoryRef = "secondaryEntityManagerFactory",
    transactionManagerRef = "secondaryTransactionManager",
    basePackages = ["com.ems.ems_koltin.repository.dept_repo"]
)
@EntityScan(basePackages =["com.ems.ems_koltin.entity_dao"])
class DeptDbConfig {
    @Bean(name = ["secondaryDataSource"])
    @ConfigurationProperties(prefix = "spring.seconddatasource")
    fun secondaryDataSource(): DataSource = DataSourceBuilder.create().build()

    @Bean(name=["secondaryEntityManagerFactory"])
    fun secondaryEntityManagerFactory(builder: EntityManagerFactoryBuilder, @Qualifier("secondaryDataSource") secondaryDataSource: DataSource): LocalContainerEntityManagerFactoryBean =builder.dataSource(secondaryDataSource).packages( "com.ems.ems_koltin.entity_dao.dept_entity").build()

    @Bean(name=["secondaryTransactionManager"])
    fun secondaryTransactionManager(@Qualifier("secondaryEntityManagerFactory") secondaryEntityManagerFactory: EntityManagerFactory): PlatformTransactionManager =
        JpaTransactionManager(secondaryEntityManagerFactory)
}