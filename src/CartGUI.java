import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CartGUI extends JFrame {                                                                                   //Shopping cart frame is subclass of JFrame

    private final DefaultTableModel cartModel;                                                                          //Declare final private components
    private ArrayList<Product> selectedProducts;
    private java.util.List<Object[]> tableRows;                                                                         //Private object list containing rows of the table
    private final User user;                                                                                            //Private user object using the GUI
    private final JLabel total;                                                                                         //Labels containing total and discount
    private final JLabel userDiscount;
    private final JLabel itemDiscount;
    private final JLabel finalTotal;

    public CartGUI(User user) {                                                                                         //Constructor for the GUI
        this.user = user;
        if (user.getCart()!=null) {
            this.selectedProducts = user.getCart().getCartProducts();                                                   //Get product list in cart of user
        }

        setTitle("Shopping Cart");                                                                                      //Fromatting for the Shopping Cart page
        setSize(600, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(5,10));
        Font defaultFont = new Font("Arial", Font.PLAIN, 12);

        JPanel topPanel = new JPanel();

        cartModel = new DefaultTableModel(new String[]{"Product ID", "Quantity","Price"}, 0);                   //Table model for the shopping cart
        JTable cartTable = new JTable(cartModel);

        cartTable.setRowHeight(30);
        cartTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));                                 //Formatting the table

        JScrollPane cartTablePane = new JScrollPane(cartTable);
        topPanel.add(cartTablePane);                                                                                    //Add scroll pane with table to top panel
        add(topPanel,BorderLayout.NORTH);

        JPanel midPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT,40,10));                                //New middle panel for price details
        midPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel info = new JPanel();                                                                                     //Info panel containing info details
        info.setLayout(new GridLayout(4,1,10,10));

        total = new JLabel();                                                                                           //Create and format JLabels to conatin details
        total.setFont(defaultFont);
        userDiscount = new JLabel();
        userDiscount.setFont(defaultFont);
        itemDiscount = new JLabel();
        itemDiscount.setFont(defaultFont);
        finalTotal = new JLabel();

        info.add(total);
        info.add(userDiscount);
        info.add(itemDiscount);
        info.add(finalTotal);

        midPanel.add(info);                                                                                             //Add info panel to mid panel
        add(midPanel,BorderLayout.CENTER);                                                                              //Add mid panel to frame

        JPanel bottomPanel = new JPanel();                                                                              //Create bottom panel
        JButton checkoutButton = new JButton("Checkout");
        checkoutButton.addActionListener(new ActionListener() {                                                         //Clear shopping cart when user presses purchase
            @Override
            public void actionPerformed(ActionEvent e) {
                user.getCart().getCartProducts().clear();
                populateShoppingCart();
            }
        });

        add(bottomPanel,BorderLayout.SOUTH);                                                                            //Add bottom panel to frame

        bottomPanel.add(checkoutButton,BorderLayout.SOUTH);

        populateShoppingCart();                                                                                         //Call method to populate the shopping cart table

        setVisible(true);
    }

    private void populateShoppingCart() {
        cartModel.setRowCount(0);
        if (selectedProducts != null) {
            tableRows = new ArrayList<>();                                                                              //List containing row of the shopping cart table

            for (Product product : selectedProducts) {
                if (product != null) {

                    boolean productExists = false;                                                                      //Set productExists variable to false
                    for (Object[] row : tableRows) {
                        if (row[0] instanceof Product) {                                                                //Iterate through product list to populate create rows list for table
                            Product existingProduct = (Product) row[0];
                            if (existingProduct.equals(product)) {                                                      //If product exists in the list, update quantity and total price
                                int quantity = (int) row[1] + 1;
                                double totalPrice = product.getPrice() * quantity;
                                row[1] = quantity;
                                row[2] = totalPrice;
                                productExists = true;
                                break;
                            }
                        }
                    }


                    if (!productExists) {                                                                               // If the product is not in the row list, add a new row
                        int quantity = 1;
                        double totalPrice = product.getPrice() * quantity;
                        Object[] rowData = {product, quantity, totalPrice};
                        tableRows.add(rowData);
                    }
                }
            }


            for (Object[] row : tableRows) {                                                                            // Iterate through the list and add rows to the table model
                if (row[0] instanceof Product) {
                    Product product = (Product) row[0];
                    Object[] rowData = {product.getProductID(), row[1], row[2]};
                    cartModel.addRow(rowData);
                }
            }
            double totalPrice = getTotal();                                                                             //Get total price using method and add to relevant label
            total.setText("Total       "+totalPrice+ " £");
            total.setHorizontalAlignment(SwingConstants.RIGHT);

            double initialDiscount = Math.round(getInitialDiscount(totalPrice)*100.0)/100.0;                            //Get initial discount using method and add to relevant label
            userDiscount.setText("First Purchase Discount (10%)       "+initialDiscount+ " £");
            userDiscount.setHorizontalAlignment(SwingConstants.RIGHT);

            double itemsDiscount = Math.round(getItemDiscount(totalPrice)*100.0)/100.0;                                 //Get category discount using method and add to relevant label
            itemDiscount.setText("Three Items In Same Category Discount (20%)       "+ itemsDiscount + " £");

            finalTotal.setText("Final Total       "+ (totalPrice-initialDiscount-itemsDiscount)+ " £");                 //Calculate final price and add to relevant label
            finalTotal.setHorizontalAlignment(SwingConstants.RIGHT);
        }
    }

    public double getInitialDiscount(double total){                                                                     //Check if user is first time and give 10% discount
        if (user.getNewUser()){                                                                                         //Check using NewUser variable of user
            double InitialDiscount = total * 0.1;
            return InitialDiscount;
        } else
        {return 0;}
    }

    public double getItemDiscount(double total) {                                                                       //Check if there are more than 3 product in category and give 20% discount
        int ECount = 0;
        int CCount = 0;

        for (Product product : selectedProducts){                                                                       //Iterate throgh product list and find if more than 3 items from same category
            if (product instanceof Electronics){
                ECount +=1;
            }
            if (product instanceof Clothing){
                CCount +=1;
            }
        }

        if (ECount >2 || CCount >2 ){                                                                                   //Return discount if condition is satisfied
            return total*0.2;
        } else {
            return 0.0; // No discount if no item has quantity more than 2
        }

    }

    public double getTotal(){                                                                                           //Iterate thorough table and find the total price
        double total =0 ;
        for (Object[] row : tableRows) {
            double price = (double) row[2];
            total += price;
        }
        return total;
    }

}
