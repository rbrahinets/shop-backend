package com.shop.signup;

import com.shop.adminnumber.AdminNumberValidator;
import com.shop.exceptions.ValidationException;
import com.shop.adminnumber.AdminNumber;
import com.shop.user.User;
import com.shop.user.UserValidator;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SignUpValidator {
    private final UserValidator userValidator;
    private final AdminNumberValidator adminNumberValidator;

    public SignUpValidator(
        UserValidator userValidator,
        AdminNumberValidator adminNumberValidator
    ) {
        this.userValidator = userValidator;
        this.adminNumberValidator = adminNumberValidator;
    }

    public boolean isValidData(
        String firstName,
        String lastName,
        String email,
        String phone,
        char[] password,
        String adminNumber,
        List<User> users,
        List<AdminNumber> adminNumbers
    ) throws ValidationException {
        userValidator.validate(
            firstName,
            lastName,
            email,
            phone,
            password,
            users
        );

        adminNumberValidator.validateAdminNumberForSignUp(
            adminNumber,
            adminNumbers
        );

        return true;
    }
}
