package com.shop.adminnumber;

import com.shop.interfaces.ServiceInterface;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AdminNumberService implements ServiceInterface<AdminNumber> {
    private final AdminNumberRepository adminNumberRepository;
    private final AdminNumberValidator adminNumberValidator;

    public AdminNumberService(
        AdminNumberRepository adminNumberRepository,
        AdminNumberValidator adminNumberValidator
    ) {
        this.adminNumberRepository = adminNumberRepository;
        this.adminNumberValidator = adminNumberValidator;
    }

    @Override
    public List<AdminNumber> findAll() {
        return adminNumberRepository.findAll();
    }

    @Override
    public AdminNumber findById(long id) {
        adminNumberValidator.validate(id, adminNumberRepository.findAll());
        Optional<AdminNumber> adminNumber = adminNumberRepository.findById(id);
        return adminNumber.orElseGet(AdminNumber::new);
    }

    @Override
    @Transactional
    public AdminNumber save(AdminNumber adminNumber) {
        adminNumberValidator.validateAdminNumber(adminNumber.getNumber());
        adminNumberRepository.save(adminNumber);
        adminNumber.setId(adminNumberRepository.findAll().size() + 1L);
        return adminNumber;
    }

    @Override
    @Transactional
    public AdminNumber update(long id, AdminNumber adminNumber) {
        adminNumberValidator.validate(id, adminNumberRepository.findAll());
        adminNumberValidator.validateAdminNumber(adminNumber.getNumber());
        adminNumberRepository.update(id, adminNumber);
        adminNumber.setId(id);
        return adminNumber;
    }

    @Override
    @Transactional
    public void delete(AdminNumber adminNumber) {
        adminNumberValidator.validate(adminNumber.getNumber(), adminNumberRepository.findAll());
        adminNumberRepository.delete(adminNumber);
    }

    public AdminNumber findByNumber(String number) {
        adminNumberValidator.validate(number, adminNumberRepository.findAll());
        Optional<AdminNumber> adminNumberOptional = adminNumberRepository.findByNumber(number);
        return adminNumberOptional.orElseGet(AdminNumber::new);
    }
}
