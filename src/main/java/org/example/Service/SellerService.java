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

    public List<Seller> getSellerList(){
        Main.log.info("Attempting to get all of Seller List.");
        return sellerDAO.getAllSellers();
    }

    public List<String> getSellerNames(){
        Main.log.info("Attempting to get all of Seller Names.");
        return sellerDAO.getSellerNames();
    }

    public void addSeller(Seller s) throws SellerException {
        Main.log.info("Attempting to add a Seller.");
        if(s.getSellerName() == null){
            Main.log.warn("SellerException: Throwing null entry exception for Seller Name.");
            throw new SellerException("Seller Name cannot be null!");
        }
        else if (getSellerNames().contains(s.getSellerName())){
            Main.log.warn("SellerException: Seller already exists");
            throw new SellerException("Seller Name already exists!");
        }
        else {
            long sellerID = (long) (Math.random() * Long.MAX_VALUE);
            s.setSellerID(sellerID);
            sellerDAO.insertSeller(s);
        }
    }

    public void deleteSeller(long sellerID){
        Main.log.info("Attempting to delete a Seller.");
        sellerDAO.deleteSeller(sellerID);
    }
}
