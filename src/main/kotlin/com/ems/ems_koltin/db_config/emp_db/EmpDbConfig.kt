package com.ems.ems_koltin.db_config.emp_db


import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
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
    entityManagerFactoryRef = "primaryEntityManagerFactory",
    transactionManagerRef = "primaryTransactionManager",
    basePackages = ["com.ems.ems_koltin.repository.emp_repo"]
)
@EntityScan(basePackages =["com.ems.ems_koltin"])
class EmpDbConfig {
    @Bean(name = ["primaryDataSource"])
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    fun primaryDataSource():DataSource=DataSourceBuilder.create().build()

    @Primary
    @Bean(name=["primaryEntityManagerFactory"])
    fun primaryEntityManagerFactory(builder: EntityManagerFactoryBuilder, @Qualifier("primaryDataSource") primaryDataSource: DataSource):LocalContainerEntityManagerFactoryBean=builder.dataSource(primaryDataSource).packages("com.ems.ems_koltin.entity_dao.emp_entity").build()

    @Primary
    @Bean(name=["primaryTransactionManager"])
    fun primaryTransactionManager(@Qualifier("primaryEntityManagerFactory") primaryEntityManagerFactory: EntityManagerFactory):PlatformTransactionManager=JpaTransactionManager(primaryEntityManagerFactory)

}

