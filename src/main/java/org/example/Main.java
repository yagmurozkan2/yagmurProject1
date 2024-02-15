package org.example;

import org.example.Controller.Controller;
import org.example.DAO.ProductDAO;
import org.example.DAO.SellerDAO;
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

        Javalin api = controller.getAPI();
        api.start(9008);

    }
}