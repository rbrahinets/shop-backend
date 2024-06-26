package com.shop.category;

import com.shop.exceptions.NotFoundException;
import com.shop.exceptions.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.stream.Stream;

import static com.shop.category.CategoryParameter.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class CategoryValidatorTest {
    private CategoryValidator categoryValidator;

    @Mock
    private CategoryRepository categoryRepository;

    private static List<Category> categories;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        categoryValidator = new CategoryValidator();
        categories = List.of(getCategoryWithId());
    }

    @Test
    @DisplayName("Category validated without exceptions")
    void category_validated_without_exceptions() {
        assertDoesNotThrow(
            () -> categoryValidator.validateCategory(getName())
        );
    }

    @Test
    @DisplayName("Throw ValidationException because name of category is null")
    void throw_validation_exception_because_name_of_category_is_null() {
        assertThrows(
            ValidationException.class,
            () -> categoryValidator.validateCategory(null)
        );
    }

    @Test
    @DisplayName("Throw ValidationException because name of category is empty")
    void throw_validation_exception_because_name_of_category_is_empty() {
        assertThrows(
            ValidationException.class,
            () -> categoryValidator.validateCategory("")
        );
    }

    @Test
    @DisplayName("Id of category validated without exceptions")
    void id_of_category_validated_without_exceptions() {
        when(categoryRepository.findAll())
            .thenReturn(
                List.of(getCategoryWithId())
            );

        assertDoesNotThrow(
            () -> categoryValidator.validate(getCategoryId(), categories)
        );
    }

    @Test
    @DisplayName("Throw NotFoundException because id of category not found")
    void throw_not_found_exception_because_id_of_category_not_found() {
        assertThrows(
            NotFoundException.class,
            () -> categoryValidator.validate(getCategoryId(), getCategories())
        );
    }

    @Test
    @DisplayName("Name of category validated without exceptions")
    void name_of_category_validated_without_exceptions() {
        when(categoryRepository.findAll())
            .thenReturn(
                List.of(getCategoryWithId())
            );

        assertDoesNotThrow(
            () -> categoryValidator.validate(getName(), categories)
        );
    }

    @Test
    @DisplayName("Throw NotFoundException because name of category not found")
    void throw_not_found_exception_because_name_of_category_not_found() {
        assertThrows(
            NotFoundException.class,
            () -> categoryValidator.validate(getName(), getCategories())
        );
    }

    @Test
    @DisplayName("Throw ValidationException because name is null")
    void throw_validation_exception_because_name_is_null() {
        assertThrows(
            ValidationException.class,
            () -> categoryValidator.validate(null, categories)
        );
    }

    @Test
    @DisplayName("Throw ValidationException because name is empty")
    void throw_validation_exception_because_name_is_empty() {
        assertThrows(
            ValidationException.class,
            () -> categoryValidator.validate("", categories)
        );
    }

    @ParameterizedTest
    @MethodSource("validationTestCases")
    @DisplayName("Throw ValidationException for invalid name")
    void throw_validation_exception_for_invalid_name(String name, List<Category> categories) {
        assertThrows(ValidationException.class, () -> categoryValidator.validate(name, categories));
    }

    private static Stream<Arguments> validationTestCases() {
        return Stream.of(
            Arguments.of(null, categories),
            Arguments.of("", categories)
        );
    }
}
