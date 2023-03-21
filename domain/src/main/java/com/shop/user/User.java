package com.shop.user;

import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 2L;
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private char[] password;
    private long adminNumberId;

    public User() {
    }

    public User(
        long id,
        String firstName,
        String lastName
    ) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User(
        long id,
        String firstName,
        String lastName,
        String email,
        String phone,
        char[] password,
        long adminNumberId
    ) {
        this(id, firstName, lastName);
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.adminNumberId = adminNumberId;
    }

    public static User of(
        String firstName,
        String lastName,
        String email,
        String phone,
        char[] password,
        long adminNumberId
    ) {
        return new User(
            0,
            firstName,
            lastName,
            email,
            phone,
            password,
            adminNumberId
        );
    }

    public static User of(
        String firstName,
        String lastName
    ) {
        return new User(
            0,
            firstName,
            lastName
        );
    }

    public User withId(long id) {
        return new User(
            id,
            this.firstName,
            this.lastName,
            this.email,
            this.phone,
            this.password,
            this.adminNumberId
        );
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public char[] getPassword() {
        return password;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    public long getAdminNumberId() {
        return adminNumberId;
    }

    public void setAdminNumberId(long adminNumberId) {
        this.adminNumberId = adminNumberId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User that = (User) o;
        return Objects.equals(id, that.id)
            && Objects.equals(firstName, that.firstName)
            && Objects.equals(lastName, that.lastName)
            && Objects.equals(email, that.email)
            && Objects.equals(phone, that.phone)
            && Arrays.equals(password, that.password)
            && adminNumberId == that.adminNumberId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            firstName,
            lastName,
            email,
            phone,
            Arrays.hashCode(password),
            adminNumberId
        );
    }

    @Override
    public String toString() {
        return "User{"
            + "id=" + id
            + ", firstName='" + firstName + '\''
            + ", lastName='" + lastName + '\''
            + ", email='" + email + '\''
            + ", phone='" + phone + '\''
            + '}';
    }
}
