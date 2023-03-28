package com.shop.signup;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(SignUpController.SIGN_UP_URL)
public class SignUpController {
    public static final String SIGN_UP_URL = "/api/sign-up";
    private final SignUpService signUpService;

    public SignUpController(SignUpService signUpService) {
        this.signUpService = signUpService;
    }

    @PostMapping
    public SignUpDto signUp(
        @RequestBody SignUpDto signUpDto
    ) {
        signUpService.signUp(
            signUpDto.firstName(),
            signUpDto.lastName(),
            signUpDto.email(),
            signUpDto.phone(),
            signUpDto.password().toCharArray(),
            signUpDto.adminNumber()
        );

        return signUpDto;
    }
}

