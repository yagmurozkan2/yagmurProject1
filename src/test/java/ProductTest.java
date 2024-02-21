import org.example.DAO.*;
import org.example.Exception.*;
import org.example.Model.*;
import org.example.Service.*;
import org.example.Util.ConnectionSingleton;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.util.*;

public class ProductTest {

    Connection conn;
    SellerDAO sellerDAO;
    ProductDAO productDAO;
    SellerService sellerService;
    ProductService productService;

    @Before
    public void setUp(){
        conn = ConnectionSingleton.getConnection();
        sellerDAO = new SellerDAO(conn);
        productDAO = new ProductDAO(conn);
        sellerService = new SellerService(sellerDAO);
        productService = new ProductService(sellerService, productDAO);
        productService.deleteAllRecords();
    }

    @Test
    public void ProductTableEmptyAtStart(){
        Assert.assertTrue(productService.getProductList().isEmpty());
    }

    @Test
    public void SellerTableEmptyAtStart(){
        Assert.assertTrue(sellerService.getSellerList().isEmpty());
    }

    //happy path to add seller
    @Test
    public void SellerServiceAddSeller(){
        Seller seller = new Seller("Michael G. Scott");

        try {
            sellerService.addSeller(seller);
        } catch (SellerException e) {
            e.printStackTrace();
            Assert.fail("Seller Exception incorrectly thrown.");
        }

        List<Seller> sellerSet = sellerService.getSellerList();
        Assert.assertTrue(sellerSet.contains(seller));
    }

    //Adding a seller with NULL as Seller Name
    @Test
    public void SellerServiceAddNullSeller(){
        Seller seller = new Seller(null);
        try {
            sellerService.addSeller(seller);
            Assert.fail();
        } catch (SellerException e) {
            e.printStackTrace();
        }
    }

    //Adding an existing Seller
    @Test
    public void SellerServiceAddExistingSeller(){
        SellerServiceAddSeller();
        Seller seller = new Seller("Michael G. Scott");
        try {
            sellerService.addSeller(seller);
            Assert.fail();
        } catch (SellerException e) {
            e.printStackTrace();
        }
    }

    //happy path to delete a seller
    @Test
    public void SellerServiceDeleteSeller(){
        SellerServiceAddSeller();
        Seller selerDelete = sellerService.getSellerList().get(0);
        sellerService.deleteSeller(selerDelete.getSellerID());

        List<Seller> sellerList = sellerService.getSellerList();
        Assert.assertFalse(sellerList.contains(selerDelete));
    }

    //happy path to add product
    @Test
    public void ProductServiceAddProduct(){
        String productName = "Dunder Mifflin Paper";
        double productPrice = 25.99;
        String sellerName = "Michael G. Scott";
        SellerServiceAddSeller();

        try {
            productService.addProduct(new Product(productName, productPrice, sellerName));
        } catch (ProductException e) {
            e.printStackTrace();
            Assert.fail("Product or Seller Exception incorrectly thrown.");
        }

        List<Product> newPaperProduct = productService.getProductByName(productName);
        Assert.assertEquals(productName, newPaperProduct.get(0).getProductName());
        Assert.assertEquals(productPrice, newPaperProduct.get(0).getProductPrice(), 0.001);
        Assert.assertEquals(sellerName, newPaperProduct.get(0).getSellerName());
        Assert.assertFalse(productService.getProductList().isEmpty());
    }

    //Adding a product with NULL as Product Name
    @Test
    public void ProductServiceAddNullProduct(){
        String productName = null;
        double productPrice = 25.99;
        String sellerName = "Michael G. Scott";
        SellerServiceAddSeller();

        try {
            productService.addProduct(new Product(productName, productPrice, sellerName));
            Assert.fail();
        } catch (ProductException e) {
            e.printStackTrace();
        }
    }

    //Adding a product with negative Product Price
    @Test
    public void ProductServiceAddProductwithNegativePrice(){
        String productName = "Dunder Mifflin Paper";
        double productPrice = -25.99;
        String sellerName = "Michael G. Scott";
        SellerServiceAddSeller();

        try {
            productService.addProduct(new Product(productName, productPrice, sellerName));
            Assert.fail();
        } catch (ProductException e) {
            e.printStackTrace();
        }
    }

    //Adding a product with non-existing Seller
    @Test
    public void ProductServiceAddProductwithNonExistingSeller(){
        String productName = "Dunder Mifflin Paper";
        double productPrice = 25.99;
        String sellerName = "Jim Halpert";

        try {
            productService.addProduct(new Product(productName, productPrice, sellerName));
            Assert.fail();
        } catch (NullPointerException | ProductException e) {
            e.printStackTrace();
        }
    }

    //Adding an existing Product
    @Test
    public void ProductServiceAddExistingProduct(){
        ProductServiceAddProduct();
        try {
            productService.addProduct(new Product("Dunder Mifflin Paper",25.99,"Michael G. Scott"));
            Assert.fail();
        } catch (ProductException e) {
            e.printStackTrace();
        }
    }

    //happy path search product by id
    @Test
    public void ProductServiceProductFound(){
        ProductServiceAddProduct();

        long productID;
        Product product = null;

        try {
            productID = productService.getProductList().get(0).getProductID();
            product = productService.getProductByID(productID);
        } catch (ProductException e) {
            e.printStackTrace();
            Assert.fail("Product or Seller Exception incorrectly thrown.");
        }

        Assert.assertNotNull(product);
    }

    //happy path find product by seller
    @Test
    public void ProductServiceGetProductbySeller(){
        ProductServiceAddProduct();
        String productName = "Dunder Mifflin Paper";
        String sellerName = "Michael G. Scott";

        List<Product> productList = productService.getProductByNameAndSeller(productName,sellerName);
        Assert.assertEquals(productService.getProductList(), productList);
    }

    //happy path find product by name and seller
    @Test
    public void ProductServiceGetProductbyNameandSeller(){
        ProductServiceAddProduct();
        String sellerName = "Michael G. Scott";

        List<Product> productList = productService.getProductBySeller(sellerName);
        Assert.assertEquals(productService.getProductList(), productList);
    }

    //Happy Path update Product
    @Test
    public void ProductServiceUpdateProduct(){
        ProductServiceAddProduct();
        String newProductName = "Dunder Mifflin Premium Paper";
        double newProductPrice = 30.99;
        String newSellerName = "Dwight Schrute";

        try {
            sellerService.addSeller(new Seller(newSellerName));

            List<Product> oldPaperProduct = productService.getProductByName("Dunder Mifflin Paper");
            Product newPaperProduct = new Product(newProductName, newProductPrice, newSellerName);
            productService.updateProduct(oldPaperProduct.get(0),newPaperProduct);
        } catch (ProductException e) {
            e.printStackTrace();
            Assert.fail("Product Exception incorrectly thrown.");
        } catch (SellerException e) {
            e.printStackTrace();
            Assert.fail("Seller Exception incorrectly thrown.");
        }

        List<Product> newPaperProduct = productService.getProductByName(newProductName);

        Assert.assertEquals(newProductName, newPaperProduct.get(0).getProductName());
        Assert.assertEquals(newProductPrice, newPaperProduct.get(0).getProductPrice(), 0.001);
        Assert.assertEquals(newSellerName, newPaperProduct.get(0).getSellerName());
        Assert.assertFalse(productService.getProductList().isEmpty());
    }

    //Updating a product with null product name
    @Test
    public void ProductServiceUpdateNullProduct(){
        ProductServiceAddProduct();
        String newProductName = null;
        double newProductPrice = 30.99;
        String newSellerName = "Dwight Schrute";

        try {
            Product newPaperProduct = new Product(newProductName, newProductPrice, newSellerName);
            productService.updateProduct(productService.getProductByName("Dunder Mifflin Paper").get(0),newPaperProduct);
            Assert.fail();
        } catch (ProductException e) {
            e.printStackTrace();
        }
    }

    //Updating a product with negative product price
    @Test
    public void ProductServiceUpdateProductwithNegativePrice(){
        ProductServiceAddProduct();
        String newProductName = "Dunder Mifflin Premium Paper";
        double newProductPrice = -30.99;
        String newSellerName = "Dwight Schrute";

        try {
            Product newPaperProduct = new Product(newProductName, newProductPrice, newSellerName);
            productService.updateProduct(productService.getProductByName("Dunder Mifflin Paper").get(0),newPaperProduct);
            Assert.fail();
        } catch (ProductException e) {
            e.printStackTrace();
        }
    }

    //Updating a product with non-existing Seller
    @Test
    public void ProductServiceUpdateProductwithNonExistingSeller(){
        ProductServiceAddProduct();
        String newProductName = "Dunder Mifflin Premium Paper";
        double newProductPrice = 30.99;
        String newSellerName = "Jim Halpert";

        try {
            Product newPaperProduct = new Product(newProductName, newProductPrice, newSellerName);
            productService.updateProduct(productService.getProductByName("Dunder Mifflin Paper").get(0),newPaperProduct);
            Assert.fail();
        } catch (ProductException e) {
            e.printStackTrace();
        }
    }

    //happy path to delete a product
    @Test
    public void ProductServiceDeleteProduct(){
        String productName = "Dunder Mifflin Premium Recycled Paper";
        double productPrice = 35.99;
        String sellerName = "Michael G. Scott";
        SellerServiceAddSeller();

        try {
            productService.addProduct(new Product(productName, productPrice, sellerName));
        } catch (ProductException e) {
            e.printStackTrace();
            Assert.fail("Product or Seller Exception incorrectly thrown.");
        }

        List<Product> paperProduct = productService.getProductByName(productName);
        productService.deleteProduct(paperProduct.get(0).getProductID());

        Assert.assertFalse(productService.getProductList().contains(paperProduct));
    }

}
