package com.shop.controllers;

import com.shop.PdfUtility;
import com.shop.dto.ReportDto;
import com.shop.models.Basket;
import com.shop.models.Person;
import com.shop.models.Product;
import com.shop.models.Wallet;
import com.shop.services.BasketService;
import com.shop.services.PersonService;
import com.shop.services.ProductsBasketsService;
import com.shop.services.WalletService;
import com.stripe.exception.StripeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = ProductsBasketsController.PRODUCTS_BASKETS_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductsBasketsController {
    public static final String PRODUCTS_BASKETS_URL = "/web-api/products-baskets";
    private static final Logger logger = LoggerFactory
        .getLogger(ProductsBasketsController.class);
    private final ProductsBasketsService productsBasketsService;
    private final BasketService basketService;
    private final PersonService personService;
    private final WalletService walletService;

    public ProductsBasketsController(
        ProductsBasketsService productsBasketsService,
        BasketService basketService,
        PersonService personService,
        WalletService walletService
    ) {
        this.productsBasketsService = productsBasketsService;
        this.basketService = basketService;
        this.personService = personService;
        this.walletService = walletService;
    }

    @GetMapping
    public List<Product> findAllProductsInBasket(@AuthenticationPrincipal UserDetails userDetail) {
        Person person = personService.getPerson(userDetail.getUsername());
        Basket basket = basketService.getBasketByPerson(person.getId());
        return productsBasketsService.getProductsFromBasket(basket.getId());
    }

    @PostMapping("/{id}")
    public long addProductToBasket(
        @AuthenticationPrincipal UserDetails userDetail,
        @PathVariable long id,
        HttpServletResponse response
    ) throws IOException {
        Person person = personService.getPerson(userDetail.getUsername());
        Basket basket = basketService.getBasketByPerson(person.getId());
        response.sendRedirect("/basket");
        return productsBasketsService.addProductToBasket(id, basket.getId());
    }

    @PostMapping("/{id}/delete")
    public void deleteProductFromBasket(
        @AuthenticationPrincipal UserDetails userDetail,
        @PathVariable long id,
        HttpServletResponse response
    ) throws IOException {
        Person person = personService.getPerson(userDetail.getUsername());
        Basket basket = basketService.getBasketByPerson(person.getId());
        response.sendRedirect("/basket");
        productsBasketsService.deleteProductFromBasket(id, basket.getId());
    }

    @PostMapping("/buy")
    public void buy(
        @AuthenticationPrincipal UserDetails userDetail,
        HttpServletResponse response
    ) throws IOException {
        try {
            response.setContentType("application/pdf");
            DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
            String currentDateTime = dateFormatter.format(new Date());
            String headerKey = "Content-Disposition";
            String headerValue = "attachment; filename=report_" + currentDateTime + ".pdf";
            response.setHeader(headerKey, headerValue);

            Person person = personService.getPerson(userDetail.getUsername());
            Basket basket = basketService.getBasketByPerson(person.getId());

            List<Product> productsInBasket = productsBasketsService
                .getProductsFromBasket(basket.getId());

            ReportDto reportDto = new ReportDto();
            reportDto.setProducts(productsInBasket);
            reportDto.setTotalCost(basket.getTotalCost());

            productsBasketsService.buy(person.getId());

            Wallet wallet = walletService.getWalletByPerson(person.getId());
            reportDto.setAmountOfMoney(wallet.getAmountOfMoney());

            PdfUtility pdfUtility = new PdfUtility(reportDto);
            pdfUtility.export(response);
        } catch (StripeException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
