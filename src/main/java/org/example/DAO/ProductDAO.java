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
            PreparedStatement ps2 = conn.prepareStatement("insert into product (productID, productName, productPrice, sellerID) " +
                    "values (?, ?, ?, ?)");

            ps2.setLong(1, product.getProductID());
            ps2.setString(2, product.getProductName());
            ps2.setDouble(3, product.getProductPrice());
            ps2.setLong(4, product.getSellerID());
            ps2.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement("select p.*, s.sellerName from product p join seller s on p.sellerID = s.sellerID");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                long productID = rs.getLong("productID");
                String productName = rs.getString("productName");
                double productPrice = rs.getDouble("productPrice");
                long sellerID = rs.getLong("sellerID");
                String sellerName = rs.getString("sellerName");
                Product p = new Product(productID, productName, productPrice, sellerID, sellerName);
                productList.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }

    public List<Product> getProductNamePriceSeller() {
        List<Product> productList = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement("select p.productName, p.productPrice, s.sellerName from product p join seller s on p.sellerID = s.sellerID");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String productName = rs.getString("productName");
                double productPrice = rs.getDouble("productPrice");
                String sellerName = rs.getString("sellerName");
                Product p = new Product(productName, productPrice, sellerName);
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
            PreparedStatement ps = conn.prepareStatement("select p.*, s.sellerName from product p join seller s on p.sellerID = s.sellerID where productID = " + productID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String productName = rs.getString("productName");
                double productPrice = rs.getDouble("productPrice");
                long sellerID = rs.getLong("sellerID");
                String sellerName = rs.getString("sellerName");
                product = new Product(productID, productName, productPrice, sellerID, sellerName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    };

    public List<Product> getProductByName(String productName) {
        List<Product> productList = new ArrayList<>();
        try{
            PreparedStatement ps = conn.prepareStatement(
                    "select p.*, s.sellerName " +
                        "from product p " +
                        "join seller s " +
                            "on p.sellerID = s.sellerID " +
                        "where lower(productName) = '" + productName.toLowerCase() + "'");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                long productID = rs.getLong("productID");
                double productPrice = rs.getDouble("productPrice");
                long sellerID = rs.getLong("sellerID");
                String sellerName = rs.getString("sellerName");
                productList.add(new Product(productID, productName, productPrice, sellerID, sellerName));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }

    public List<Product> getProductBySeller(String sellerName){
        List<Product> productList = new ArrayList<>();
        try{
            PreparedStatement ps = conn.prepareStatement(
                    "select p.*, s.sellerName " +
                         "from product p " +
                         "join seller s " +
                            "on p.sellerID = s.sellerID " +
                         "where lower(sellerName) = '" + sellerName.toLowerCase() + "'");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                long productID = rs.getLong("productID");
                String productName = rs.getString("productName");
                double productPrice = rs.getDouble("productPrice");
                long sellerID = rs.getLong("sellerID");
                productList.add(new Product(productID, productName, productPrice, sellerID, sellerName));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }

    public List<Product> getProductByNameAndSeller(String productName, String sellerName) {
        List<Product> productList = new ArrayList<>();
        try{
            PreparedStatement ps = conn.prepareStatement(
                    "select p.*, s.sellerName " +
                        "from product p " +
                        "join seller s " +
                            "on p.sellerID = s.sellerID " +
                        "where lower(productName) = '" + productName.toLowerCase() + "' " +
                            "and lower(sellerName) = '" + sellerName.toLowerCase() + "'");

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                long productID = rs.getLong("productID");
                double productPrice = rs.getDouble("productPrice");
                long sellerID = rs.getLong("sellerID");
                productList.add(new Product(productID, productName, productPrice, sellerID, sellerName));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }

    public Product updateProduct(Product oldProduct, Product newProduct){
        try{
            PreparedStatement ps = conn.prepareStatement("update product " +
                    "set productName = ?, productPrice = ?, sellerID = ?" +
                    "where productID = " + oldProduct.getProductID());
            ps.setString(1, newProduct.getProductName());
            ps.setDouble(2, newProduct.getProductPrice());
            ps.setLong(3, newProduct.getSellerID());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newProduct;
    }

    public void deleteProduct(long productID){
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
