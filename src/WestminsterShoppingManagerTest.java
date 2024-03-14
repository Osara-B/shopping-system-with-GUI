//import org.junit.jupiter.api.Test;
//
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.InputStream;
//import java.io.PrintStream;
//import java.util.ArrayList;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class WestminsterShoppingManagerTest {
//
//    @Test
//    void addProductToSystem() {
//        // Test the addProductToSystem method
//        WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager();
//
//        // Mock user input to simulate adding a product
//        String mockInput = "123\nProduct1\n5\n10.0\n1\n";
//        InputStream inputStream = new ByteArrayInputStream(mockInput.getBytes());
//        System.setIn(inputStream);
//
//        shoppingManager.addProductToSystem();
//
//        // Retrieve the product list and verify that the product has been added
//        ArrayList<Product> productList = shoppingManager.getProductList();
//        assertEquals(1, productList.size());
//        assertEquals("123", productList.get(0).getProductID());
//    }
//
//    @Test
//    void deleteProductFromSystem() {
//        // Test the deleteProductFromSystem method
//        WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager();
//
//        // Add a product to the system
//        Product product = new Electronics("123", "Product1", 5, 10.0, "Brand1", 12);
//        shoppingManager.getProductList().add(product);
//
//        // Mock user input to simulate deleting the product
//        String mockInput = "123\n";
//        InputStream inputStream = new ByteArrayInputStream(mockInput.getBytes());
//        System.setIn(inputStream);
//
//        shoppingManager.deleteProductFromSystem();
//
//        // Verify that the product has been removed
//        assertEquals(0, shoppingManager.getProductList().size());
//    }
//
//    @Test
//    void printListOfProducts() {
//        // Test the printListOfProducts method
//        WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager();
//
//        // Add a product to the system
//        Product product = new Electronics("123", "Product1", 5, 10.0, "Brand1", 12);
//        shoppingManager.getProductList().add(product);
//
//        // Redirect the standard output to capture printed content
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        System.setOut(new PrintStream(outputStream));
//
//        // Call the printListOfProducts method
//        shoppingManager.printListOfProducts();
//
//        // Verify that the correct product information is printed
////        String expectedOutput = "---------LIST OF PRODUCTS----------\n" +
////                "-------------------------------------\n" +
////                product.toString() +
////                "-------------------------------------\n";
//        String expectedOutput = "\n----------LIST OF PRODUCTS-----------\n-------------------------------------" +
//                "\n\t\tITEM 1\n" +
//                product.toString() +
//                "\n-------------------------------------\n";
//        assertEquals(expectedOutput, outputStream.toString());
//    }
//
//    @Test
//    void saveAndLoadProductsToFile() {
//        // Test the saveProductsToFile and loadProductsFromFile methods
//        WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager();
//
//        // Add a product to the system
//        Product product = new Electronics("123", "Product1", 5, 10.0, "Brand1", 12);
//        shoppingManager.getProductList().add(product);
//
//        // Save products to a file
//        shoppingManager.saveProductsToFile();
//
//        // Clear the existing product list
//        shoppingManager.getProductList().clear();
//
//        // Load products from the file
//        shoppingManager.loadProductsFromFile();
//
//        // Verify that the correct product information is loaded
//        ArrayList<Product> loadedProductList = shoppingManager.getProductList();
//        assertEquals(1, loadedProductList.size());
//        assertEquals(product.getProductID(), loadedProductList.get(0).getProductID());
//    }
//}
