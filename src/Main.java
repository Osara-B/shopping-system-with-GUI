import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager();                  //New instance of Westminster Shopping Manager
        shoppingManager.loadProductsFromFile();

        while(true)
            try {                                                                                       //Try block to catch any exceptions that may occur
                {
                    Scanner input = new Scanner(System.in);                                             //Initialize Scanner class
                    System.out.print("""
                                            
                        -------------------------------------------------
                        \t\t\t\t\tMain Menu:
                         1. Manager System
                         2. User System
                         0. Quit
                         
                        -------------------------------------------------
                         ENTER OPTION : \s""");
                    int choice = input.nextInt();                                                       //Get integer input for the manager of user system
                    if (choice ==1){
//MANAGER SYSTEM

                        while(true) {                                                                   //Enter while loop for manager system
                            try {
                                System.out.print("""
                                            
                        -------------------------------------------------
                        \t\t\t\tConsole Menu:
                         1. Add a new product
                         2. Delete a product
                         3. Print list of products
                         4. Save products to file
                         5. Load products from file (WARNING - Existing products will be overwritten)
                         6. Exit
                        -------------------------------------------------
                         ENTER OPTION : \s""");                                                         //Print menu for managerial system

                                int value = input.nextInt();

                                switch (value) {                                                        //Input taken from the user runs the respective code using switch
                                    case 1:                                                             //input 1 to add new product
                                        shoppingManager.addProductToSystem();
                                        break;
                                    case 2:                                                             //input 2 to delete a product
                                        shoppingManager.deleteProductFromSystem();
                                        break;
                                    case 3:                                                             //input 3 to print a list of products
                                        shoppingManager.printListOfProducts();
                                        break;
                                    case 4:                                                             //input 4 to save products to file
                                        shoppingManager.saveProductsToFile();
                                        break;
                                    case 5:                                                             //input 5 to load products from file
                                        shoppingManager.loadProductsFromFile();
                                        break;
                                    case 6:                                                             //input 6 to quit manager program
                                        System.out.println("\n---YOU QUIT THE MANAGER PROGRAM---\n");
                                        break;
                                    default:                                                            //other inputs
                                        System.out.println(("\n--- ERROR : Invalid Input (Enter A Number Shown In The Menu)---\n"));
                                }
                                if (value == 6) {                                                       //To quit from the infinite while loop
                                    break;
                                }
                            }catch(InputMismatchException e){                                           //Code block if a non integer value is entered for console menu input
                                System.out.println("\n--- ERROR : Invalid Non-Integer Input (Enter A Number Shown In The Menu)---\n");
                                input.nextLine();
                            }
                        }
//USER SYSTEM
                    }
                    else if (choice==2) {

                        ShoppingManager WestminsterShoppingCenter = new WestminsterShoppingManager();   //Create instance of WestminsterShoppingManager and load products from file
                        WestminsterShoppingCenter.loadProductsFromFile();
                        ArrayList<Product>stock = ((WestminsterShoppingManager) WestminsterShoppingCenter).getProductList();        //Give the same reference to "stock" arraylist

                        ArrayList<User> userList = new ArrayList<User>();                               //Create an arraylist of users
                        loadUsersFromFile(userList);                                                    //Load users previously saved to file
                        String username;
                        while (true){                                                                   //Enter while loop to sign in or register

                                System.out.print("""
                                                        
                                    -------------------------------------------------
                                    \t\t\t\t\tUser Menu:
                                     0. SIGN IN
                                     1. REGISTER
                                                                       
                                    -------------------------------------------------
                                     ENTER OPTION : \s""");
                                int value = input.nextInt();                                        //Get integer input
                                if (value==0){                                                      //Sign in process
                                    boolean flag = true;                                            //Flag to check whether password is correct or incorrect
                                    while(true) {
                                        System.out.println("Enter username ");
                                        username = input.next();
                                        System.out.println("Enter password ");
                                        String password = input.next();
                                        try{
                                            if (getUserByUsername(userList,username).getPassword().equals(password)){
                                                getUserByUsername(userList,username).setNewUser(false);             //Set newUser variable in User to false if user signs in
                                                System.out.println("\n--- Correct Password ---\n");
                                                System.out.println("\n--- Signing in ---\n");
                                                break;
                                            } else {
                                                System.out.println("\n--- ERROR : Incorrect Password ---\n");
                                                flag = false;
                                                break;
                                            }}
                                        catch (NullPointerException e){                                            //If the user doesn't exist, exceptions is caught and handled
                                            System.out.println("\n--- ERROR : USER DOESN'T EXIST---\n");
                                            flag = false;
                                            break;
                                        }
                                    }
                                    if (flag){ break;}                                                            //Break loop if password is correct (flag is true)

                                } else if (value ==1) {                                                           //Asks user for username and password and creates new user
                                    System.out.println("Enter username ");
                                    username = input.next();
                                    System.out.println("Enter password ");
                                    String password = input.next();
                                    User user1 = new User(username, password);                                    //Instantiate user
                                    System.out.println("---Account Created!---");
                                    userList.add(user1);                                                          //Add users to user list
                                    saveUsersToFile(userList);                                                    //Save users to file and break loop
                                    break;
                                } else {
                                    System.out.println("\n--- ERROR : Invalid Input (Enter A Number Shown In The Menu)---\n"); //Loops for valid input if input is invalid
                                }
                        }
                        User user = getUserByUsername(userList, username);                                      //Get the User class user object using its username
                        ShoppingGUI GUI = new ShoppingGUI(stock, user, WestminsterShoppingCenter);              //Instance of Shopping GUI

                    }
                    else if (choice==0){
                        System.out.println("---Quitting Program!---");                                          //Quit program if choice is 0 in main menu
                        break;
                    }
                    else {
                        System.out.println("\n--- ERROR : Invalid Input (Enter A Number Shown In The Menu)---\n");      //Continue loop if user input is invalif
                    }
                }
            } catch (InputMismatchException e){
                System.out.println("\n--- ERROR : Invalid Non-Integer Input (Enter A Number Shown In The Menu)---\n");  //Handle exception if input for main menu isn't integer
            }

    }

    public static void saveUsersToFile(ArrayList<User> userList){                                               //Method to save users to file
        FileWriter writing = null;                                                                              //Create FileWrite instance outside while loop

        try {                                                                                                   //Try block for code that may have exception
            File myFile = new File("userList.txt");
            if (!myFile.exists()) {
                myFile.createNewFile();
            }

            writing = new FileWriter(myFile);                                                                  //new FileWriter instance

            for (User user : userList) {

                writing.write(user.getUsername()+","+user.getPassword()+"\n");                             //Loop through user list and write username and password with a comma between in a new line
            }

        } catch (IOException e) {
            System.out.println("---ERROR COULD NOT SAVE TO FILE---");                                         //Handle Input Output exception
        } finally {
            try {
                if (writing != null) {
                    writing.close();                                                                          //Close the file if there are no more lines
                }
            } catch (IOException e) {
                System.out.println("---ERROR COULD NOT SAVE TO FILE!---");                                    //Handle any error that occurs when closing the file
            }
        }
    }
    public static void loadUsersFromFile(ArrayList<User> userList){                                         //Method to load saved users from file
        try {
            BufferedReader reader = new BufferedReader(new FileReader("userList.txt"));             //BufferedReader to read a line from the file
            String line;

            while ((line = reader.readLine()) != null) {                                                    //Loop until no newlines exist in the file

                String[] infoList = line.trim().split(",");                                           //Turn the line containing username and password separated by a comma to a list

                if (infoList.length > 0) {
                    User user = new User(infoList[0],infoList[1]);                                          //If the list isn't empty create new usr instance with the obtained username nad password
                    userList.add(user);                                                                     //Add users to the userList parameter
                }
            }

            System.out.println("\t\tUsers loaded from file.");

        } catch (IOException e) {
            System.out.println("--- ERROR COULD NOT READ FROM FILE---");                                    //Handle input output exception that may occur
        }
    }
    public static User getUserByUsername(ArrayList<User> userList,String username) {                        //Method to acquire user objects using its username attribute
        for (User user : userList){
            if (user.getUsername().equals(username)){                                                       //Iterate through list of users and find the relevant user object
                return user;                                                                                //Return acquired user if found
            }
        }
        return null;                                                                                        //Return null if no user is found
    }

}

