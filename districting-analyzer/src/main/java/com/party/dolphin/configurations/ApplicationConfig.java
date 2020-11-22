// package com.party.dolphin.configurations;

// import javax.sql.DataSource;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.jdbc.DataSourceBuilder;
// import org.springframework.context.annotation.*;
// import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
// import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
// import org.springframework.orm.jpa.vendor.Database;
// import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

// @Configuration
// @EnableJpaRepositories
// public class ApplicationConfig {
//     // This is needed to configure the emf data source
//     @Bean
//     public DataSource dataSource() {
//         return DataSourceBuilder.create().build();
//     }

//     // This is needed to set the package to scan
//     @Bean
//     public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
//         HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//         vendorAdapter.setGenerateDdl(true);
//         vendorAdapter.setDatabase(Database.MYSQL);

//         LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
//         factory.setJpaVendorAdapter(vendorAdapter);
//         factory.setPackagesToScan("com.party.dolphin.repository");
//         factory.setDataSource(dataSource());
//         return factory;
//     }
// }
