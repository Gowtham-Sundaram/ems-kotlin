package com.ems.ems_koltin.db_config.user_db

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
    entityManagerFactoryRef = "tertiaryEntityManagerFactory",
    transactionManagerRef = "tertiaryTransactionManager",
    basePackages = ["com.ems.ems_koltin.repository.user_repo"]
)
@EntityScan(basePackages =["com.ems.ems_koltin.entity_dao"])
class UserDbConfig {
    @Bean(name = ["tertiaryDataSource"])
    @ConfigurationProperties(prefix = "spring.thirddatasource")
    fun tertiaryDataSource(): DataSource = DataSourceBuilder.create().build()

    @Bean(name=["tertiaryEntityManagerFactory"])
    fun tertiaryEntityManagerFactory(builder: EntityManagerFactoryBuilder, @Qualifier("tertiaryDataSource") tertiaryDataSource: DataSource): LocalContainerEntityManagerFactoryBean =builder.dataSource(tertiaryDataSource).packages( "com.ems.ems_koltin.entity_dao.user_entity").build()

    @Bean(name=["tertiaryTransactionManager"])
    fun tertiaryTransactionManager(@Qualifier("tertiaryEntityManagerFactory") tertiaryEntityManagerFactory: EntityManagerFactory): PlatformTransactionManager =
        JpaTransactionManager(tertiaryEntityManagerFactory)
}