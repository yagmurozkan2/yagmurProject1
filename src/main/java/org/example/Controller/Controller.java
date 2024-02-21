package org.example.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import org.example.Main;
import org.example.Model.*;
import org.example.Service.*;
import java.util.*;

public class Controller {
    SellerService sellerService;
    ProductService productService;

    public Controller(SellerService sellerService, ProductService productService){
        this.sellerService = sellerService;
        this.productService = productService;
    }

    public Javalin getAPI(){
        Javalin api = Javalin.create();

        api.get("health", context -> {context.result("Server is UP!");});

        api.get("seller", context -> {
            List<Seller> sellerList = sellerService.getSellerList();
            context.json(sellerList);
        });

        api.post("seller", context -> {
            try{
                ObjectMapper om = new ObjectMapper();
                Seller s = om.readValue(context.body(), Seller.class);
                sellerService.addSeller(s);
                context.status(201);
                context.json(s);
            } catch(JsonProcessingException e){
                context.status(400);
            }
        });

        api.delete("seller/{sellerID}", context -> {
            long sellerID = Long.parseLong(context.pathParam("sellerID"));
            sellerService.deleteSeller(sellerID);
            context.result("Seller Deleted!");
            context.status(200);
        });

        api.get("product", context -> {
            String nameFilter = context.queryParam("nameFilter");
            String sellerFilter = context.queryParam("sellerFilter");
            List<Product> productList = new ArrayList<>();
            if (nameFilter != null && sellerFilter == null) {
                productList = productService.getProductByName(nameFilter);
            }
            else if (sellerFilter != null && nameFilter == null) {
                productList = productService.getProductBySeller(sellerFilter);
            }
            else if (nameFilter != null && sellerFilter != null) {
                productList = productService.getProductByNameAndSeller(nameFilter, sellerFilter);
            }
            else {
                productList = productService.getProductList();

            }
            context.json(productList);
        });

        api.get("product/{productID}", context -> {
            long productID = Long.parseLong(context.pathParam("productID"));
            Product p = productService.getProductByID(productID);
            if (p == null){
                context.status(404);
                context.result("ID not found!");
            }
            else{
                context.json(p);
                context.status(200);
            }

        });

        api.post("product", context -> {
            try{
                ObjectMapper om = new ObjectMapper();
                Product p = om.readValue(context.body(), Product.class);
                productService.addProduct(p);
                context.status(200);
                context.json(p);
            } catch(JsonProcessingException e){
                context.status(400);
            }
        });

        api.put("product/{productID}", context -> {
            long productID = Long.parseLong(context.pathParam("productID"));
            Product old_p = productService.getProductByID(productID);
            if (old_p == null){
                context.status(404);
                context.result("ID not found!");
            }
            else {
                try{
                    ObjectMapper om = new ObjectMapper();
                    Product new_p = om.readValue(context.body(), Product.class);
                    Product newProduct = productService.updateProduct(old_p, new_p);
                    newProduct.setProductID(productID);
                    context.status(200);
                    context.json(newProduct);
                } catch(JsonProcessingException e){
                    context.status(400);
                }
            }
        });

        api.delete("product/{productID}", context -> {
            long productID = Long.parseLong(context.pathParam("productID"));
            productService.deleteProduct(productID);
            context.result("Product Deleted!");
            context.status(200);
        });

        return api;
    }
}

