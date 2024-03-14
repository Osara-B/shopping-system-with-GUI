import java.io.*;
import java.util.*;
public class WestminsterShoppingManager implements ShoppingManager{
    private ArrayList<Product> productList;                                                            //Private attribute of an ArrayList of products objects named product List
    public WestminsterShoppingManager(){                                                               //Constructor for WestminsterShoppingManager class
        this.productList = new ArrayList<>();
    }

    public ArrayList<Product> getProductList() {
        return productList;                                                                            //Public getter for the arrayList of product attribute
    }

    public void setProductList(ArrayList<Product> productList) {
        this.productList = productList;                                                                //Public setter for the arrayList of product attribute
    }

    @Override
    public void addProductToSystem() {                                                                 //Public method to add product objects to product list of system
        Scanner input = new Scanner(System.in);                                                        //Instantiate scanner class
        System.out.println(productList.size());
        while (true) {
            try {                                                                                       //Try block catch any exception that occurs
                if (productList.size()>= 50) {
                    System.out.println("--- ERROR : Product Limit exceeded!---");
                    break;                                                                              //No more products can be added if product list exceeds 50 products
                }

                String productID = getStringInput("\nEnter product ID ");                   //Get product ID

                if (hasProductWithID(productID)) {                                                      //Process if product already exists in the product list
                    int choice ;
                    while(true) {
                        choice = getIntInput("\n--- ERROR : Product already exists ---\n ENTER 1 TO RE-ENTER PRODUCT 0 TO RETURN TO CONSOLE MENU");
                        if (choice==0 || choice==1 )                                                    //Get valid input of 1 or 0
                            break;
                        else {
                            System.out.println("--- ERROR : Invalid Input (Enter A Number Shown In The Menu)---"); //Invalid input
                        }
                    }
                    if (choice == 0) break;                                                             //Re enter console menu if user inputs 0

                } else {
                                                                                                        //Get validated and error handled input for productName, availableItems and price
                    String productName = getStringInput("\nEnter product name : ");
                    int availableItems = getIntInput("\nEnter number of available items : ");
                    double price = getDoubleInput("\nEnter price : ");

                    while (true) {
                        int option = getIntInput("\n---Enter 1 for ELECTRONIC PRODUCT Enter 2 for CLOTHING PRODUCT ---");
                                                                                                        //Get choice for electrical or clothing product
                        if (option == 1) {
                                                                                                        //Get validated error handled inputs for brand and warrantyPeriod
                            String brand = getStringInput("\nEnter brand of product : ");
                            int warrantyPeriod = getIntInput("\nEnter Warranty Period of Product (in Weeks) : ");

                            Product electronic = new Electronics(productID,productName,availableItems,price,brand,warrantyPeriod); //Create new Electronic product instance
                            productList.add(electronic);                                                                            //Add to product list
                            Collections.sort(productList);                                                                          //Sort product list by productID
                            break;
                        } else if (option == 2) {
                                                                                                        //Get validated error handled inputs for size and color
                            String size = getStringInput("\nEnter size of clothing : ");
                            String color = getStringInput("\nEnter color of clothing : ");
                            Product clothing = new Clothing(productID,productName,availableItems,price,size,color);                 //Create new Electronic product instance
                            productList.add(clothing);                                                                              //Add to product list
                            Collections.sort(productList);                                                                          //Sort product list by productID
                            break;
                        } else {
                            System.out.println("--- ERROR : Invalid Input (Enter A Number Shown In The Menu)---");                  //Loop for valid input if input is incorrect
                        }
                    }
                    System.out.println("\n----Product successfully created----");
                    break;
                }

            } catch (InputMismatchException e) {
                System.out.println("--- ERROR : Invalid Non Integer Input (Enter A Number Shown In The Menu)---");                 //Loop while loop if exception occurs in try block
            }
        }
    }
    @Override
    public boolean hasProductWithID(String productID) {                                                 //Method to check if product list has product of certain product ID
        for (Product product : productList) {
            if (product.getProductID().equals(productID)) {
                return true;                                                                            //Return true if list has product with the given productID
            }
        }
        return false;                                                                                   //Return false if list doesn't have product with given product ID
    }
    @Override
    public void deleteProductFromSystem() {                                                             //Method to delete product from product list
        while (true) {
            try {
                String productID = getStringInput("\nEnter product ID to remove product"); //Get product ID

                if (hasProductWithID(productID)) {                                                      //Process if product list has product
                    for (int i = 0; i < productList.size(); i++) {
                        Product product = productList.get(i);
                        if (product.getProductID().equals(productID)) {
                            System.out.println(product.toString());                                     //Print details of product to remove
                            productList.remove(i);                                                      //Remove product
                            Collections.sort(productList);                                              //Sort the product list again
                        }
                    }
                    System.out.println("\n---Product Successfully Removed!---");                        //Print success and remaining products number in product list
                    System.out.println("---"+productList.size()+" Products remaining in system!---");
                    break;
                } else {                                                                                //Process if product isn't in product list
                    int choice = getIntInput("\n---ERROR : Product is not in the product list--- \nENTER 1 REENTER PRODUCT TO REMOVE 0 TO RETURN TO CONSOLE MENU");
                                                                                                        //Get productID again or return to menu
                    if (choice == 1){
                        continue;
                    }
                    if (choice == 0)
                        break;
                    else {
                        System.out.println("--- ERROR : Invalid Input (Enter A Number Shown In The Menu)---");
                    }
                }

            } catch (InputMismatchException e) {                                                        //Handle input error that occurs during the delete product process
                System.out.println("--- ERROR : Invalid Non Integer Input (Enter A Number Shown In The Menu)---");
            }
        }
    }
    @Override
    public void printListOfProducts() {                                                                 //Method to print list of products
        System.out.println("\n----------LIST OF PRODUCTS-----------\n-------------------------------------");
        Collections.sort(productList);                                                                  //Sort list before printing
        int i=1;
        for (Product product:productList){                                                              //Loop through product list and print each to screen using toString method
            System.out.println("\t\tITEM "+ i);
            System.out.println(product.toString());
            System.out.println("-------------------------------------");
            i++;
        }
    }
    @Override
    public void saveProductsToFile() {
        FileWriter writing = null;                                                                      //Declare FileWriter outside the try block

        try {
            File myFile = new File("productList.txt");                                         // Create new file instance for productList.txt
            if (!myFile.exists()) {
                myFile.createNewFile();                                                                 //Create new file if file doesn't exist
            }

            writing = new FileWriter(myFile);                                                           //Create file writer instance

            for (Product product : productList) {                                                       //Loop through product list and write to file (using writeProduct method)
                                                                                                        // Write each product's information to the file
                writing.write(writeProduct(product));
            }

        } catch (IOException e) {
            System.out.println("---ERROR : COULD NOT SAVE TO FILE---");                                 //Catch and handle input output exceptions
        } finally {
            try {                                                                                       //Close file if no more lines to be read
                if (writing != null) {
                    writing.close();
                }
            } catch (IOException e) {                                                                   //Handle any error when closing the file
                System.out.println("---ERROR COULD NOT SAVE TO FILE---");
            }
        }
    }
    @Override
    public String writeProduct(Product product){                                                        //Method to structure the product information to write to file
                                                                                                        //Separate information by commas and separate products by new line
            if (product instanceof Electronics){
                Electronics electronic = (Electronics) product;
                return "Electronics,"+ electronic.getProductID() + "," + electronic.getProductName() + "," + electronic.getAvailableItems() + "," + electronic.getPrice()
                        + "," + electronic.getBrand()+ "," + electronic.getWarrantyPeriod()+"\n" ;
            }
            else if (product instanceof Clothing){
                Clothing clothing = (Clothing) product;
                return "Clothing,"+ clothing.getProductID() + "," + clothing.getProductName() + "," + clothing.getAvailableItems() + "," + clothing.getPrice()
                        + "," + clothing.getSize() + "," + clothing.getColor()+"\n" ;
            }

        return null;                                                                                    //Return null else
    }
    @Override
    public void loadProductsFromFile() {                                                                //Method to read the products from file
        try {
            BufferedReader reader = new BufferedReader(new FileReader("productList.txt"));      //BufferedReader instance to read lines from file
            String line;
            productList.clear();                                                                        //Clear existing product list
            while ((line = reader.readLine()) != null) {                                                //While lines exist, loop to read
                String[] infoList = line.trim().split(",");                                       //Split lines by comma and add to infoList

                if (infoList.length > 0) {
                                                                                                        // Determine the type of product (Electronics or Clothing)
                    String productType = infoList[0];
                    if ("Electronics".equals(productType)) {                                            //If electronic create new electronic product and add to product list
                        Product electronic = new Electronics(infoList[1],infoList[2],Integer.parseInt(infoList[3]),Double.parseDouble(infoList[4]),infoList[5],Integer.parseInt(infoList[6]));
                        productList.add(electronic);

                    } else if ("Clothing".equals(productType)) {                                        //If clothing create new clothing product and add to product list
                        Product clothing = new Clothing(infoList[1],infoList[2],Integer.parseInt(infoList[3]),Double.parseDouble(infoList[4]),infoList[5],infoList[6]);
                        productList.add(clothing);

                    } else {
                        System.out.println("ERROR READING FROM FILE");                                  //Display error
                    }
                }
            }

            System.out.println("\t\tProducts loaded from file.");

        } catch (IOException e) {                                                                      //Catch and handle exception
            System.out.println("\n---ERROR : COULD NOT READ FROM FILE---");
        }
    }


    public static String getStringInput(String outputMessage) {                                       //Method to get validated and error handled string input

        String StringInput;                                                                           //Declare StringInput outside while block
        while(true){
            try {
                Scanner input = new Scanner(System.in);
                System.out.println(outputMessage);
                StringInput = input.next();
                break;
            } catch (InputMismatchException e) {
                System.out.println("--- ERROR : Input Should Be A String Value ---");
            }
        }
        return StringInput;
    }
    public static int getIntInput(String outputMessage) {                               //Method to get validated and error handled integer input

        int IntInput = 0;
        while (true){
            try {
                Scanner input = new Scanner(System.in);
                System.out.println(outputMessage);
                IntInput = input.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.println("--- ERROR : Input Should Be An Integer Value ---");
            }
        }
        return IntInput;
    }
    public static double getDoubleInput(String outputMessage) {                         //Method to get validated and error handled double input
        double DoubleInput = 0;
        while (true){
            try {
                Scanner input = new Scanner(System.in);
                System.out.println(outputMessage);
                DoubleInput = input.nextDouble();
                break;
            } catch (InputMismatchException e) {
                System.out.println("--- ERROR : Input Should Be A Float Value ---");

            }
        }
        return DoubleInput;
    }

}

