package org.example.DAO;

import org.example.Model.Seller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class SellerDAO {
    Connection conn;

    public SellerDAO(Connection conn) {
        this.conn = conn;
    }
    public void insertSeller(Seller seller){
        try{
            PreparedStatement ps = conn.prepareStatement("insert into seller (sellerName) values (?)");
            ps.setString(1, seller.getSellerName());
            ps.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public HashSet<Seller> getAllSellers() {
        HashSet<Seller> sellerHashSet = new HashSet<>();
        try {
            PreparedStatement ps = conn.prepareStatement("select * from seller");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String sellerName = rs.getString("sellerName");
                Seller s = new Seller(sellerName);
                sellerHashSet.add(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sellerHashSet;
    }

    public String searchSeller(String sellerName){
        try {
            PreparedStatement ps = conn.prepareStatement("select * from seller where sellerName = '" + sellerName + "'");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getString("sellerName");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }
}
