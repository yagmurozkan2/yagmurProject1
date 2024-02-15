package org.example.DAO;

import org.example.Model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    Connection conn;

    public ProductDAO(Connection conn) {
        this.conn = conn;
    }

    public void insertProduct(Product product){
        try{
            PreparedStatement ps = conn.prepareStatement("insert into product (productID, productName, productPrice, sellerName) " +
                    "values (?, ?, ?, ?)");
            ps.setLong(1, product.getProductID());
            ps.setString(2, product.getProductName());
            ps.setDouble(3, product.getProductPrice());
            ps.setString(4, product.getSellerName());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement("select * from product");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                long productID = rs.getLong("productID");
                String productName = rs.getString("productName");
                Double productPrice = rs.getDouble("productPrice");
                String sellerName = rs.getString("sellerName");
                Product p = new Product(productID, productName, productPrice, sellerName);
                productList.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }

    public Product getProductByID(long productID){
        Product product = null;
        try {
            PreparedStatement ps = conn.prepareStatement("select * from product where productID = " + productID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String productName = rs.getString("productName");
                Double productPrice = rs.getDouble("productPrice");
                String sellerName = rs.getString("sellerName");
                product = new Product(productID, productName, productPrice, sellerName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    };

    public Product getProductByName(String productName) {
        Product product = null;
        try{
            PreparedStatement ps = conn.prepareStatement("select * from product where productName = '" + productName + "'");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Long productID = rs.getLong("productID");
                Double productPrice = rs.getDouble("productPrice");
                String sellerName = rs.getString("sellerName");
                product = new Product(productID, productName, productPrice, sellerName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    public Product updateProduct(Product oldProduct, Product newProduct){
        try{
            PreparedStatement ps = conn.prepareStatement("update product " +
                    "set productName = ?, productPrice = ?, sellerName = ? " +
                    "where productID = " + oldProduct.getProductID());
            ps.setString(1, newProduct.getProductName());
            ps.setDouble(2, newProduct.getProductPrice());
            ps.setString(3, newProduct.getSellerName());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newProduct;
    }

    public void deleteProduct(Long productID){
        try{
            PreparedStatement ps = conn.prepareStatement("delete from product " +
                    "where productID = " + productID);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAllRecords(){
        try{
            PreparedStatement ps = conn.prepareStatement("delete from product; delete from seller;");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
