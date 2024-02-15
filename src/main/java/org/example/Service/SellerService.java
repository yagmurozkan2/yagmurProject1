package org.example.Service;

import org.example.DAO.SellerDAO;
import org.example.Exception.SellerException;
import org.example.Main;
import org.example.Model.Seller;

import java.util.*;

public class SellerService {
    SellerDAO sellerDAO;

    public SellerService(SellerDAO sellerDAO) {
        this.sellerDAO = sellerDAO;
    }

    public HashSet<Seller> getSellerSet(){
        Main.log.info("Attempting to get all of Seller List.");
        return sellerDAO.getAllSellers();
    }

    public void addSeller(Seller s) throws SellerException {
        Main.log.info("Attempting to add a Seller.");
        if(s.getSellerName() == null){
            Main.log.warn("ProductException: Throwing null entry exception for Seller Name.");
            throw new SellerException("Seller Name cannot be null!");
        }
        sellerDAO.insertSeller(s);
    }
}
