package com.shop.adminnumber;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.shop.adminnumber.AdminNumberParameter.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(
    classes = {
        AdminNumberService.class,
        AdminNumberServiceContextConfigurationTest.TestContextConfig.class
    }
)
class AdminNumberServiceContextConfigurationTest {
    @Autowired
    private AdminNumberRepository adminNumberRepository;
    @Autowired
    private AdminNumberValidator adminNumberValidator;
    @Autowired
    private AdminNumberService adminNumberService;

    @Test
    @DisplayName("Get all numbers of admins")
    void get_all_numbers_of_admins() {
        adminNumberService.findAll();

        verify(adminNumberRepository, atLeast(1)).findAll();
    }

    @Test
    @DisplayName("Get number of admin by id")
    void get_number_of_admin_by_id() {
        adminNumberService.findById(getAdminNumberId());

        verify(adminNumberRepository, atLeast(1)).findAll();
        verify(adminNumberValidator, atLeast(1)).validate(getAdminNumberId(), getAdminNumbers());
        verify(adminNumberRepository).findById(getAdminNumberId());
    }

    @Test
    @DisplayName("Save number of admin")
    void save_number_of_admin() {
        adminNumberService.save(getAdminNumberWithoutId());

        verify(adminNumberValidator, atLeast(1)).validateAdminNumber(getNumber());
        verify(adminNumberRepository).save(getAdminNumberWithId());
        verify(adminNumberRepository, atLeast(1)).findAll();
    }

    @Test
    @DisplayName("Update number of admin")
    void update_number_of_admin() {
        adminNumberService.update(getAdminNumberId(), getAdminNumberWithoutId2());

        verify(adminNumberRepository, atLeast(1)).findAll();
        verify(adminNumberValidator, atLeast(1)).validate(getAdminNumberId(), getAdminNumbers());
        verify(adminNumberValidator, atLeast(1)).validateAdminNumber(getNumber2());
        verify(adminNumberRepository).update(getAdminNumberId(), getAdminNumberWithId2(getAdminNumberId()));
    }

    @Test
    @DisplayName("Delete number of admin by number")
    void delete_number_of_admin_by_number() {
        adminNumberService.delete((getAdminNumberWithoutId()));

        verify(adminNumberRepository, atLeast(1)).findAll();
        verify(adminNumberValidator, atLeast(1)).validate(getNumber(), getAdminNumbers());
        verify(adminNumberRepository).delete((getAdminNumberWithoutId()));
    }

    @Test
    @DisplayName("Get number of admin by number")
    void get_number_of_admin_by_number() {
        adminNumberService.findByNumber(getNumber());

        verify(adminNumberRepository, atLeast(1)).findAll();
        verify(adminNumberValidator, atLeast(1)).validate(getNumber(), getAdminNumbers());
        verify(adminNumberRepository).findByNumber(getNumber());
    }

    @TestConfiguration
    static class TestContextConfig {
        @Bean
        public AdminNumber adminNumber() {
            return mock(AdminNumber.class);
        }

        @Bean
        public AdminNumberRepository adminNumberRepository() {
            return mock(AdminNumberRepository.class);
        }

        @Bean
        public AdminNumberValidator adminNumberValidator() {
            return mock(AdminNumberValidator.class);
        }
    }
}
