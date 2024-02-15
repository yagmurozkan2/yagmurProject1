package org.example.Service;

import org.example.DAO.ProductDAO;
import org.example.Exception.ProductException;
import org.example.Model.*;
import org.example.Main;

import java.util.*;
//LOGGING
//SIDE EFFECT, ACTION THAT IS UNRELATED.
public class ProductService {
    SellerService sellerService;
    ProductDAO productDAO;

    public ProductService(SellerService sellerService, ProductDAO productDAO){
        this.sellerService = sellerService;
        this.productDAO = productDAO;
    }

    public List<Product> getProductList(){
        Main.log.info("Attempting to get all of Product List.");
        return productDAO.getAllProducts();
    }

    public Product getProductByID(long productID) throws ProductException {
        Main.log.info("Attempting to get a Product by its ID.");
        if (productDAO.getProductByID(productID) == null){
            throw new ProductException("ID not found.");
        }
        return productDAO.getProductByID(productID);
    }

    public Product getProductByName(String productName){
        Main.log.info("Attempting to get a Product by its Name.");
        return productDAO.getProductByName(productName);
    }

    public Product addProduct(Product p) throws ProductException{
        Main.log.info("Attempting to add a Product");
        if(p.getProductName() == null || p.getSellerName() == null){
            Main.log.warn("ProductException: Throwing null entry exception for Product Name or Seller Name.");
            throw new ProductException("Product Name and Seller Name cannot be null!");
        }
        else if (p.getProductPrice() < 0){
            Main.log.warn("ProductException: Throwing negative value exception for Product Price.");
            throw new ProductException("Product Price cannot be lower than 0!");
        }
        else if (sellerService.sellerDAO.searchSeller(p.getSellerName()).isEmpty()){
            Main.log.warn("ProductException: Throwing Seller Not Found exception.");
            throw new ProductException("Seller Name does not exits!");
        }
        long productID = (long) (Math.random() * Long.MAX_VALUE);
        p.setProductID(productID);
        productDAO.insertProduct(p);
        return p;
    }

    public Product updateProduct(Product oldProduct, Product newProduct) throws ProductException{
        Main.log.info("Attempting to update a Product");
        if(newProduct.getProductName() == null || newProduct.getSellerName() == null){
            Main.log.warn("ProductException: Throwing null entry exception for Product Name or Seller Name.");
            throw new ProductException("Product Name and Seller Name cannot be null!");
        }
        else if (newProduct.getProductPrice() < 0){
            Main.log.warn("ProductException: Throwing negative value exception for Product Price.");
            throw new ProductException("Product Price cannot be lower than 0!");
        }
        else if (sellerService.sellerDAO.searchSeller(newProduct.getSellerName()).isEmpty()){
            Main.log.warn("ProductException: Throwing Seller Not Found exception.");
            throw new ProductException("Seller Name does not exits!");
        }
        return productDAO.updateProduct(oldProduct, newProduct);
    }

    public void deleteProduct(Long productID){
        Main.log.info("Attempting to delete a Product");
        productDAO.deleteProduct(productID);
    }

    public void deleteAllRecords(){
        Main.log.info("Attempting to delete all records from Seller and Product");
        productDAO.deleteAllRecords();;
    }
}