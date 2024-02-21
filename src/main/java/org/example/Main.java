package org.example;

import org.example.Controller.Controller;
import org.example.DAO.ProductDAO;
import org.example.DAO.SellerDAO;
import org.example.Exception.ProductException;
import org.example.Exception.SellerException;
import org.example.Model.Product;
import org.example.Model.Seller;
import org.example.Service.ProductService;
import org.example.Service.SellerService;
import org.example.Util.ConnectionSingleton;

import io.javalin.Javalin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;

public class Main {

    public static Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        System.out.println("Welcome to our program!\n");

        Connection conn = ConnectionSingleton.getConnection();

        ProductDAO productDAO = new ProductDAO(conn);
        SellerDAO sellerDAO = new SellerDAO(conn);

        SellerService sellerService = new SellerService(sellerDAO);
        ProductService productService = new ProductService(sellerService, productDAO);

        Controller controller = new Controller(sellerService, productService);

        try{
            sellerService.addSeller(new Seller("seller1"));
            sellerService.addSeller(new Seller("seller2"));
            sellerService.addSeller(new Seller("seller3"));

            productService.addProduct(new Product("product1", 10.99, "seller1"));
            productService.addProduct(new Product("product2", 13.99, "seller1"));
            productService.addProduct(new Product("product2", 14.99, "seller1"));
            productService.addProduct(new Product("product1", 14.99, "seller2"));
        } catch (SellerException | ProductException e) {
            throw new RuntimeException(e);
        }

        Javalin api = controller.getAPI();
        api.start(9008);



    }
}