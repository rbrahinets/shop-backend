package com.shop.user;

import com.shop.adminnumber.AdminNumberRepository;
import com.shop.adminnumber.AdminNumberValidator;
import com.shop.cart.Cart;
import com.shop.cart.CartRepository;
import com.shop.interfaces.ServiceInterface;
import com.shop.userrole.UserRoleRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements ServiceInterface<User> {
    private final UserRepository userRepository;
    private final UserValidator userValidator;
    private final AdminNumberValidator adminNumberValidator;
    private final AdminNumberRepository adminNumberRepository;
    private final CartRepository cartRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(
        UserRepository userRepository,
        UserValidator userValidator,
        AdminNumberValidator adminNumberValidator,
        AdminNumberRepository adminNumberRepository,
        CartRepository cartRepository,
        UserRoleRepository userRoleRepository,
        PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.userValidator = userValidator;
        this.adminNumberValidator = adminNumberValidator;
        this.adminNumberRepository = adminNumberRepository;
        this.cartRepository = cartRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(long id) {
        userValidator.validate(id, userRepository.findAll());
        Optional<User> user = userRepository.findById(id);
        return user.orElseGet(User::new);
    }

    @Override
    @Transactional
    public User save(User user) {
        userValidator.validate(
            user.getFirstName(),
            user.getLastName(),
            user.getEmail(),
            user.getPhone(),
            user.getPassword(),
            userRepository.findAll()
        );

        user.setPassword(passwordEncoder.encode(String.copyValueOf(user.getPassword())).toCharArray());

        adminNumberValidator.validateAdminNumberForSignUp(
            user.getAdminNumber(),
            adminNumberRepository.findAll()
        );

        userRepository.save(user);
        user.setId(userRepository.findAll().size() + 1L);
        return user;
    }

    @Override
    @Transactional
    public User update(long id, User user) {
        userValidator.validate(id, userRepository.findAll());
        userValidator.validateFullName(user.getFirstName(), user.getLastName());
        userRepository.update(id, user);
        user.setId(id);
        return user;
    }

    @Override
    @Transactional
    public void delete(User user) {
        userValidator.validate(user.getId(), userRepository.findAll());
        Optional<Cart> cartOptional = cartRepository.findByUser(user);
        cartOptional.ifPresent(cartRepository::delete);
        userRoleRepository.deleteRoleForUser(user.getId());
        userRepository.delete(user);
    }

    public User findByEmail(String email) {
        userValidator.validateEmail(email);
        Optional<User> user = userRepository.findByEmail(email);
        return user.orElseGet(User::new);
    }
}
