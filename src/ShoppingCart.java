import java.util.ArrayList;

public class ShoppingCart {
    private ArrayList<Product> cartProducts;                                                               //Private ArrayList of Products attribute named cartProducts

    public ShoppingCart(){
        this.cartProducts=new ArrayList<>();                                                               //Constructor for ShoppingCart Class
    }

    public ArrayList<Product> getCartProducts() {
        return cartProducts;
    }

    public void setCartProducts(ArrayList<Product> cartProducts) {                                      //Public setter for ArrayList of cart products
        this.cartProducts = cartProducts;
    }

    public void addProduct(Product product){
        cartProducts.add(product);                                                                     //Public method to add Product objects to the ShoppingCart instance
    }
    public void removeProduct(Product product){
        cartProducts.remove(product);                                                                  //Public method to remove Product objects from ShoppingCart instance
    }

    public void calculateTotal(double total){
        total=0;
        for (Product product :cartProducts){
            total+=product.getPrice();
        }
    }


}
