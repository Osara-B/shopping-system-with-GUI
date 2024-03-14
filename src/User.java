public class User {
    private String username;                                                                               //private string Username attribute of user object
    private String password;                                                                               //private string password attribute of user object
    private boolean newUser;                                                                               //private newUser boolean attribute to track whether user is new or signing in
    private ShoppingCart cart;                                                                             //private ShoppingCart instance relevant to the user object

    public User(String username, String password){                                                         //Constructor for the User class
        this.username=username;
        this.password=password;
        this.newUser=true;                                                                                 //Constructor automatically gives true value to newUser attribute
        this.cart = new ShoppingCart();                                                                    //Create new instance of shopping cart
    }


    public ShoppingCart getCart() {
        return cart;                                                                                      //Public getter for cart attribute
    }

    public void setCart(ShoppingCart cart) {
        this.cart = cart;                                                                                //Public setter for cart attribute
    }

    public String getUsername() {
        return username;                                                                                //Public getter for the username attribute
    }

    public void setUsername(String username) {
        this.username = username;                                                                       //Public setter for the username attribute
    }

    public String getPassword() {
        return password;                                                                                //Public getter for the password attribute
    }

    public void setPassword(String password) {
        this.password = password;                                                                       //Public setter for the password attribute
    }

    public boolean getNewUser() {
        return newUser;                                                                                 //Public getter for the NewUser attribute
    }

    public void setNewUser(boolean newUser) {
        this.newUser = newUser;                                                                         //Public setter for the NewUser attribute
    }
}
