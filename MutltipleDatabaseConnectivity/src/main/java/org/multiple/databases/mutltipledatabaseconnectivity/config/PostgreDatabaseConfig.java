package org.multiple.databases.mutltipledatabaseconnectivity.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "postgresEntityManager",
        basePackages = "org.multiple.databases.mutltipledatabaseconnectivity.repository.postgre",
        transactionManagerRef = "postgresTransactionManager"
)
public class PostgreDatabaseConfig {

    @Bean("postgredbproperties")
    @ConfigurationProperties(prefix = "spring.datasource.postgres")
    public DataSourceProperties getDataSource(){

//        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
//        dataSourceBuilder.driverClassName("org.postgresql.Driver");
//        dataSourceBuilder.url("jdbc:postgresql://localhost:5432/db2?createDatabaseIfNotExist=true");
//        dataSourceBuilder.username("sahil");
//        dataSourceBuilder.password("112296root");
//
//        return dataSourceBuilder.build();
        return new DataSourceProperties();
    }

    @Bean(name="postgresDatasource")
//    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource datasource(@Qualifier("postgredbproperties") DataSourceProperties properties){

        return properties.initializeDataSourceBuilder().build();
    }

    @Bean("postgresEntityManager")
    public LocalContainerEntityManagerFactoryBean postgreEntityManager(
            EntityManagerFactoryBuilder entityManagerFactoryBuilder,@Qualifier("postgresDatasource") DataSource dataSource
    ){
        Map<String,String> props = new HashMap<>();
        props.put("hibernate.dialect","org.hibernate.dialect.PostgreSQLDialect");
        props.put("hibernate.hbm2ddl.auto","update");
        props.put("hibernate.show_sql","true");
        
        return entityManagerFactoryBuilder
                .dataSource(dataSource)
                .packages("org.multiple.databases.mutltipledatabaseconnectivity.entity.postgre")
                .persistenceUnit("card")
                .properties(props)
                .build();
    }

    @Bean("postgresTransactionManager")
    public PlatformTransactionManager postgresTransactionManager(
            @Qualifier("postgresEntityManager") LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean
    ){
        return new JpaTransactionManager(localContainerEntityManagerFactoryBean.getObject());
    }
}
