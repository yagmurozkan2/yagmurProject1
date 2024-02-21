package org.example.Model;

import java.util.Objects;

public class Product {
    public long productID;
    public String productName;
    public double productPrice;
    public long sellerID;
    public String sellerName;

    public Product() {}

    public Product(String productName, double productPrice, String sellerName) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.sellerName = sellerName;
    }

    public Product(long productID, String productName, double productPrice, String sellerName) {
        this.productID = productID;
        this.productName = productName;
        this.productPrice = productPrice;
        this.sellerName = sellerName;
    }

    public Product(long productID, String productName, double productPrice, long sellerID, String sellerName) {
        this.productID = productID;
        this.productName = productName;
        this.productPrice = productPrice;
        this.sellerID = sellerID;
        this.sellerName = sellerName;
    }


    public long getProductID() {
        return productID;
    }

    public void setProductID(long productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public long getSellerID() {
        return sellerID;
    }

    public void setSellerID(long sellerID) {
        this.sellerID = sellerID;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productID=" + productID +
                ", productName='" + productName + '\'' +
                ", productPrice=" + productPrice +
                ", sellerName='" + sellerName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return productID == product.productID && Double.compare(productPrice, product.productPrice) == 0 && Objects.equals(productName, product.productName) && Objects.equals(sellerName, product.sellerName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productID, productName, productPrice, sellerName);
    }
}
