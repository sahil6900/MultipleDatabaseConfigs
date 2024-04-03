package org.multiple.databases.mutltipledatabaseconnectivity.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
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
        entityManagerFactoryRef = "mysqlEntityManager",
        basePackages ="org.multiple.databases.mutltipledatabaseconnectivity.repository.mysql",
        transactionManagerRef = "mysqlTransactionManager"

)
public class MySQLDatabaseConfig {

    private Environment environment;

    final Logger logger = LoggerFactory.getLogger(MySQLDatabaseConfig.class);

    @Primary
    @Bean("mysqldbproperties")
    @ConfigurationProperties(prefix="spring.datasource.mysql")
    public DataSourceProperties getDataSource(){

//        DataSourceBuilder dataSource = DataSourceBuilder.create();
//        dataSource.driverClassName("com.mysql.cj.jdbc.Driver");
//        dataSource.url("jdbc:mysql://localhost:3306/db1?createDatabaseIfNotExist=true");
//        dataSource.username("root");
//        dataSource.password("112296root");
//        logger.info("Datasource :{}",dataSource);
//
//        return dataSource.build();

        return new DataSourceProperties();
    }

    @Primary
    @Bean(name="mysqlDbDatasource")
//    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource datasource(@Qualifier("mysqldbproperties") DataSourceProperties properties){

        return properties.initializeDataSourceBuilder().build();
    }


    @Primary
    @Bean("mysqlEntityManager")
    public LocalContainerEntityManagerFactoryBean mysqlEntityManager(
            EntityManagerFactoryBuilder entityManagerFactoryBuilder,@Qualifier("mysqlDbDatasource") DataSource dataSource
    ){
        Map<String,String> props = new HashMap<>();
        props.put("hibernate.dialect","org.hibernate.dialect.MySQL8Dialect");
        props.put("hibernate.hbm2ddl.auto","update");
        props.put("hibernate.show_sql","true");

        return entityManagerFactoryBuilder
                .dataSource(dataSource)
                .packages("org.multiple.databases.mutltipledatabaseconnectivity.entity.mysql")
                .persistenceUnit("customer")
                .properties(props)
                .build();

    }

    @Primary
    @Bean("mysqlTransactionManager")
    public PlatformTransactionManager mysqlTransactionManager(
            @Qualifier("mysqlEntityManager") LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean){
        return new JpaTransactionManager(localContainerEntityManagerFactoryBean.getObject());
    }
}
