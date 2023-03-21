package com.shop.productscarts;

import com.shop.cart.Cart;
import com.shop.cart.CartService;
import com.shop.product.Product;
import com.shop.report.ReportDto;
import com.shop.security.SignInPasswordAuthenticationProvider;
import com.shop.user.User;
import com.shop.user.UserService;
import com.shop.wallet.Wallet;
import com.shop.wallet.WalletService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.shop.productscarts.ProductsCartsController.PRODUCTS_CARTS_URL;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@MockBeans({
    @MockBean(PasswordEncoder.class),
    @MockBean(SignInPasswordAuthenticationProvider.class),
    @MockBean(ReportDto.class)
})
@WebMvcTest(ProductsCartsController.class)
class ProductsCartsControllerTest {
    @Autowired
    @MockBean
    private ProductsCartsService productsCartsService;
    @Autowired
    @MockBean
    private CartService cartService;
    @Autowired
    @MockBean
    private UserService userService;
    @Autowired
    @MockBean
    private WalletService walletService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("All products from cart request")
    void all_products_from_cart_request() throws Exception {
        when(userService.findByEmail("test@email.com"))
            .thenReturn(
                new User(
                    2,
                    "John",
                    "Smith",
                    "test@email.com",
                    "+380000000000",
                    new char[]{'p', 'a', 's', 's', 'w', 'o', 'r', 'd'},
                    2
                )
            );

        when(cartService.findByUser(User.of(null, null).withId(2)))
            .thenReturn(new Cart(1, 0, 2));

        when(productsCartsService.findAllProductsInCart(1))
            .thenReturn(
                List.of(
                    new Product(
                        1,
                        "name",
                        "describe",
                        100,
                        "123",
                        true,
                        new byte[]{1, 1, 1}
                    )
                )
            );

        mockMvc.perform(get(PRODUCTS_CARTS_URL)
                .with(user("test@email.com").password("password").roles("USER"))
                .with(csrf()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON))
            .andExpect(jsonPath("$").isArray());
    }

    @Test
    @DisplayName("Save product to cart")
    void save_product_to_cart() throws Exception {
        when(userService.findByEmail("test@email.com"))
            .thenReturn(
                new User(
                    2,
                    "John",
                    "Smith",
                    "test@email.com",
                    "+380000000000",
                    new char[]{'p', 'a', 's', 's', 'w', 'o', 'r', 'd'},
                    2
                )
            );

        when(cartService.findByUser(User.of(null, null).withId(2)))
            .thenReturn(new Cart(1, 0, 2));

        when(productsCartsService.saveProductToCart(1, 1))
            .thenReturn(1L);

        mockMvc.perform(post(PRODUCTS_CARTS_URL + "/1")
                .with(user("test@email.com").password("password").roles("USER"))
                .with(csrf()))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/cart"));

        verify(productsCartsService).saveProductToCart(1, 1);
    }

    @Test
    @DisplayName("Product from cart not deleted because of incorrect id")
    void product_from_cart_not_deleted_because_of_incorrect_id() throws Exception {
        mockMvc.perform(post(PRODUCTS_CARTS_URL + "/id/delete")
                .with(user("test@email.com").password("password").roles("USER"))
                .with(csrf()))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Product from cart deleted")
    void product_from_cart_deleted() throws Exception {
        when(userService.findByEmail("test@email.com"))
            .thenReturn(
                new User(
                    2,
                    "John",
                    "Smith",
                    "test@email.com",
                    "+380000000000",
                    new char[]{'p', 'a', 's', 's', 'w', 'o', 'r', 'd'},
                    2
                )
            );

        when(cartService.findByUser(User.of(null, null).withId(2)))
            .thenReturn(new Cart(1, 0, 2));

        mockMvc.perform(post(PRODUCTS_CARTS_URL + "/1/delete")
                .with(user("test@email.com").password("password").roles("USER"))
                .with(csrf()))
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Buy products in cart")
    void buy_products_in_cart() throws Exception {
        when(userService.findByEmail("test@email.com"))
            .thenReturn(
                new User(
                    2,
                    "John",
                    "Smith",
                    "test@email.com",
                    "+380000000000",
                    new char[]{'p', 'a', 's', 's', 'w', 'o', 'r', 'd'},
                    2
                )
            );

        when(cartService.findByUser(User.of(null, null).withId(2)))
            .thenReturn(new Cart(1, 0, 2));

        when(productsCartsService.findAllProductsInCart(1))
            .thenReturn(
                List.of(
                    new Product(
                        1,
                        "name",
                        "describe",
                        100,
                        "123",
                        true,
                        new byte[]{1, 1, 1}
                    )
                )
            );

        mockMvc.perform(post(PRODUCTS_CARTS_URL + "/buy")
                .with(user("test@email.com").password("password").roles("USER"))
                .with(csrf()))
            .andExpect(status().isOk());

        verify(productsCartsService).buy(2);
    }

    @Test
    @DisplayName("Download report")
    void download_report() throws Exception {
        when(userService.findByEmail("test@email.com"))
            .thenReturn(
                new User(
                    2,
                    "John",
                    "Smith",
                    "test@email.com",
                    "+380000000000",
                    new char[]{'p', 'a', 's', 's', 'w', 'o', 'r', 'd'},
                    2
                )
            );

        when(walletService.findByUser(User.of(null, null).withId(2)))
            .thenReturn(new Wallet(1, "123", 0, 2));

        mockMvc.perform(post(PRODUCTS_CARTS_URL + "/download-report")
                .with(user("test@email.com").password("password").roles("USER"))
                .with(csrf()))
            .andExpect(status().isOk());
    }
}
