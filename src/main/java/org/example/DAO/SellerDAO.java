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
            PreparedStatement ps = conn.prepareStatement("insert into seller (sellerID, sellerName) values (?, ?)");
            ps.setLong(1, seller.getSellerID());
            ps.setString(2, seller.getSellerName());
            ps.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Seller> getAllSellers() {
        List<Seller> sellerList = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement("select * from seller");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                long sellerID = rs.getLong("sellerID");
                String sellerName = rs.getString("sellerName");
                Seller s = new Seller(sellerID, sellerName);
                sellerList.add(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sellerList;
    }

    public List<String> getSellerNames() {
        List<String> sellerList = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement("select sellerName from seller");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String sellerName = rs.getString("sellerName");
                sellerList.add(sellerName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sellerList;
    }

    public Seller searchSeller(String sellerName){
        Seller sellerfound = new Seller();
        try {
            PreparedStatement ps = conn.prepareStatement("select * from seller where sellerName = '" + sellerName + "'");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                sellerfound.setSellerID(rs.getLong("sellerID"));
                sellerfound.setSellerName(rs.getString("sellerName"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sellerfound;
    }

    public void deleteSeller(long sellerID) {
        try{
            PreparedStatement ps = conn.prepareStatement("delete from seller " +
                    "where sellerID = " + sellerID);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
