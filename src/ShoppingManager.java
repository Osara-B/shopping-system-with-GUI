public interface ShoppingManager {
    void addProductToSystem();                                                              //Method signature of method for adding products to system product list
    boolean hasProductWithID(String productID);                                             //Method signature of method for finding a Product object using its productId by iterating list
    void deleteProductFromSystem();                                                         //Method signature of method for deleting a Product from system product list
    void printListOfProducts();                                                             //Method signature of method for printing a structured list of products
    void saveProductsToFile();                                                              //Method signature of method for writing product list of system to a file
    String writeProduct(Product product);                                                   //Method signature of method for writing products to file with correct structure
    void loadProductsFromFile();                                                            //Method signature of method for reading saved product list from file

}
