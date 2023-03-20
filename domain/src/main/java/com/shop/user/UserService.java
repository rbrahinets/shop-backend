package com.shop.user;

import com.shop.adminnumber.AdminNumber;
import com.shop.adminnumber.AdminNumberService;
import com.shop.adminnumber.AdminNumberValidator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserValidator userValidator;
    private final AdminNumberValidator adminNumberValidator;
    private final AdminNumberService adminNumberService;
    private final PasswordEncoder passwordEncoder;

    public UserService(
        UserRepository userRepository,
        UserValidator userValidator,
        AdminNumberValidator adminNumberValidator,
        AdminNumberService adminNumberService,
        PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.userValidator = userValidator;
        this.adminNumberValidator = adminNumberValidator;
        this.adminNumberService = adminNumberService;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(long id) {
        userValidator.validate(id, userRepository.findAll());
        Optional<User> user = userRepository.findById(id);
        return user.orElseGet(User::new);
    }

    public User findByEmail(String email) {
        userValidator.validateEmail(email);
        Optional<User> user = userRepository.findByEmail(email);
        return user.orElseGet(User::new);
    }

    public User save(User user) {
        userValidator.validate(
            user.getFirstName(),
            user.getLastName(),
            user.getEmail(),
            user.getPhone(),
            passwordEncoder.encode(user.getPassword()),
            userRepository.findAll()
        );

        List<AdminNumber> adminNumbers = adminNumberService.findAll();
        adminNumberValidator.validate(user.getAdminNumberId(), adminNumbers);

        userRepository.save(
            user.getFirstName(),
            user.getLastName(),
            user.getEmail(),
            user.getPhone(),
            passwordEncoder.encode(user.getPassword()),
            user.getAdminNumberId()
        );

        return user;
    }

    public User update(User user) {
        userValidator.validate(user.getId(), userRepository.findAll());
        userValidator.validateFullName(user.getFirstName(), user.getLastName());
        userRepository.update(user.getId(), user.getFirstName(), user.getLastName());
        return user;
    }

    public void delete(User user) {
        userValidator.validate(user.getId(), userRepository.findAll());
        userRepository.delete(user.getId());
    }
}
