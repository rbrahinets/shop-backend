package com.shop.configs;

import com.shop.repositories.ContactRepository;
import com.shop.services.ContactService;
import com.shop.validators.ContactValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({RepositoryConfig.class, ValidatorConfig.class})
public class ServiceConfig {
    @Bean
    public ContactService contactService(
        ContactRepository contactRepository,
        ContactValidator contactValidator
    ) {
        return new ContactService(contactRepository, contactValidator);
    }
}