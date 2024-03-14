abstract class Product implements Comparable<Product>{                                                  //Product class
    private String productID;                                                                           //Private string product ID attribute
    private String productName;                                                                         //Private string product name attribute
    private int availableItems;                                                                         //private integer attribute for available items
    private double price;                                                                               //private double attribute

    public Product(String productID, String productName, int availableItems, double price) {            //Constructor
        this.productID = productID;
        this.productName = productName;
        this.availableItems = availableItems;
        this.price = price;
    }

    public String getProductID() {
        return productID;                                                                               //Getter for the private product ID
    }

    public void setProductID(String productID) {
        this.productID = productID;                                                                     //Setter for the private product ID
    }

    public String getProductName() {
        return productName;                                                                             //Getter for the private name attribute
    }

    public void setProductName(String productName) {
        this.productName = productName;                                                                 //Setter for the private name attribute
    }

    public int getAvailableItems() {
        return availableItems;                                                                          //Getter for the available items attribute
    }

    public void setAvailableItems(int availableItems) {
        this.availableItems = availableItems;                                                           //Setter for the available items attribute
    }

    public double getPrice() {
        return price;                                                                                   //Getter for the private price attribute
    }

    public void setPrice(double price) {
        this.price = price;                                                                            //Setter for the private price attribute
    }

    @Override
    public String toString() {                                                                         //Overridden toString method with structure needed
        return "Product ID: " + productID +
                "\nProduct Name: " + productName +
                "\nAvailable Items: " + availableItems +
                "\nPrice: £" + price;
    }

    @Override
    public int compareTo(Product product) {                                                            //compareTo method to be used in sort function
        return this.productID.compareTo(product.productID);                                            //compareTo method compares and returns number
    }

}

class Electronics extends Product {                                                                    //Subclass of product class
    private String brand;                                                                              //Private string brand attribute
    private int warrantyPeriod;                                                                        //Private integer warranty period attribute
                                                                                                       //Constructor
    public Electronics(String productID, String productName, int availableItems, double price, String brand,int warrantyPeriod){
        super(productID,productName,availableItems,price);
        this.brand=brand;
        this.warrantyPeriod=warrantyPeriod;

    }

    public String getBrand() {                                                                      //Getter for the private brand attribute
        return brand;
    }

    public void setBrand(String brand) {                                                            //Setter for the private brand attribute
        this.brand = brand;
    }

    public int getWarrantyPeriod() {
        return warrantyPeriod;                                                                      //Getter for the private price attribute
    }

    public void setWarrantyPeriod(int warrantyPeriod) {                                             //Getter for the private price attribute
        this.warrantyPeriod = warrantyPeriod;
    }

    @Override
    public String toString() {                                                                      //To string method for electronic type
        return "\nProduct type:  Electronics\n"+ super.toString() +
                "\nBrand: " + brand +
                "\nWarranty Period: " + warrantyPeriod + " weeks";
    }
}

class Clothing extends Product {                                                                    //Subclass of product class
    private String size;                                                                            //Private string size attribute
    private String color;                                                                           //Private string color attribute
                                                                                                    //Constructor
    public Clothing(String productID, String productName, int availableItems, double price, String size, String color) {
        super(productID, productName, availableItems, price);
        this.size=size;
        this.color=color;
    }

    public String getSize() {
        return size;                                                                                //Getter for the private size attribute
    }

    public void setSize(String size) {
        this.size = size;                                                                           //Setter for the private size attribute
    }

    public String getColor() {
        return color;                                                                               //Getter for the private color attribute
    }

    public void setColor(String color) {
        this.color = color;                                                                         //Setter for the private color attribute
    }
    @Override
    public String toString() {                                                                      //toString method for clothing type products
        return "\nProduct type:  Clothing\n"+ super.toString() +
                "\nSize: " + size +
                "\nColor: " + color;
    }
}