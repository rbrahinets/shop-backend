package com.shop.product;

import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

public class Product implements Serializable {
    @Serial
    private static final long serialVersionUID = 5L;
    private long id;
    private String name;
    private String describe;
    private double price;
    private String barcode;
    private boolean inStock;
    private byte[] image;

    public Product() {
    }

    public Product(
        long id,
        String name,
        String describe,
        double price,
        String barcode,
        boolean inStock,
        byte[] image
    ) {
        this.id = id;
        this.name = name;
        this.describe = describe;
        this.price = price;
        this.barcode = barcode;
        this.inStock = inStock;
        this.image = image;
    }

    public static Product of(
        String name,
        String describe,
        double price,
        String barcode,
        boolean inStock,
        byte[] image
    ) {
        return new Product(
            0,
            name,
            describe,
            price,
            barcode,
            inStock,
            image
        );
    }

    public static Product of(String barcode) {
        return new Product(
            0,
            null,
            null,
            0,
            barcode,
            false,
            null
        );
    }

    public Product withId(long id) {
        return new Product(
            id,
            this.name,
            this.describe,
            this.price,
            this.barcode,
            this.inStock,
            this.image
        );
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public boolean isInStock() {
        return inStock;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product that = (Product) o;
        return Objects.equals(id, that.id)
            && price == that.price
            && inStock == that.inStock
            && Objects.equals(name, that.name)
            && Objects.equals(describe, that.describe)
            && Objects.equals(barcode, that.barcode)
            && Arrays.equals(image, that.image);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, name, describe, price, barcode, inStock);
        result = 31 * result + Arrays.hashCode(image);
        return result;
    }

    @Override
    public String toString() {
        return "Product{"
            + "id=" + id
            + ", name='" + name + '\''
            + ", describe='" + describe + '\''
            + ", barcode='" + barcode + '\''
            + ", price=" + price
            + ", inStock=" + inStock
            + '}';
    }
}
