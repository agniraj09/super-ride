package com.booking.superride.config;

import org.hibernate.internal.TransactionManagement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

@Configuration
public class DatabaseConfig {

    @Bean
    TransactionTemplate readTransactionTemplate(PlatformTransactionManager platformTransactionManager){
        var readTransactionTemplate = new TransactionTemplate(platformTransactionManager);
        readTransactionTemplate.setReadOnly(true);
        return readTransactionTemplate;
    }

    @Bean
    TransactionTemplate writeTransactionTemplate(PlatformTransactionManager platformTransactionManager){
        var writeTransactionTemplate = new TransactionTemplate(platformTransactionManager);
        return writeTransactionTemplate;
    }
}
