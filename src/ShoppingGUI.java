import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ShoppingGUI extends JFrame{                                                                                //Superclass of ShoppingGUI is JFrame

    private final JComboBox<String> comboBox;                                                                           //Components as attributes of the JFrame to have scope within the class
    private final DefaultTableModel model;                                                                              //Private final table model declaration
    private final JLabel id;                                                                                            //Private final JLabels showcasing information of products
    private final JLabel category;
    private final JLabel name;
    private final JLabel info1;
    private final JLabel info2;
    private final JLabel items;
    private final JTable table;                                                                                         //Private final JTable declaration
    public ArrayList<Product> products;                                                                                 //Product list the GUI will showcase
    public User user;                                                                                                   //Private User object (user utilizing the GUI)
    private ShoppingManager WestminsterShoppingCenter;
    public ShoppingGUI(ArrayList<Product> products,User user, ShoppingManager WestminsterShoppingCenter){               //Constructor for the shopping GUI
        this.user = user;
        this.products = products;
        this.WestminsterShoppingCenter = WestminsterShoppingCenter;

        setTitle("Westminster Shopping Center");                                                                        //Set JFrame attributes
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800,800);
        setLayout(new BorderLayout(10,10));
        Font defaultFont = new Font("Arial", Font.PLAIN, 12);

        JPanel topPanel = new JPanel();                                                                                 //Top panel containing select panel and cart panel with border layout
        topPanel.setLayout(new BorderLayout());

        JPanel selectPanel = new JPanel();                                                                              //Select panel containing select label and combo box
        selectPanel.setLayout(new FlowLayout(FlowLayout.LEFT,20,20));

        JLabel selectLabel = new JLabel("Select Product Category");                                                 //Select label advising to select product category

        comboBox = new JComboBox<>(new String[] {"All","Electronics","Clothing"});                                      //Combo box to select category of products
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTable();                                                                                          //Update table when a category is selected
            }
        });

        selectPanel.add(selectLabel);                                                                                   //Add select label and combo box to slelect panel
        selectPanel.add(comboBox);
        topPanel.add(selectPanel,BorderLayout.WEST);                                                                    //Add select panel to top panel on the west

        JButton cartButton = new JButton("Shopping Cart");                                                          //Button to redirect to shopping cart
        cartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                                                                                                                        // Opens the cartGUI when the "Shopping Cart" button is clicked
                CartGUI cartGUI = new CartGUI(user);
                cartGUI.setVisible(true);
            }
        });

        JPanel cartPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT,20,20));                               //Create cart panel with flow layout
        cartPanel.add(cartButton);
        topPanel.add(cartPanel,BorderLayout.EAST);                                                                      //Add cart button to cart panel and cart panel to top panel

        JPanel midPanel = new JPanel();                                                                                 //Create mid panel
        model = new DefaultTableModel(new String[]{"Product ID", "Name", "Category", "Price","Info"},0);        //Create table model instance
        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override                                                                                                   //Override the getTableCellRenderer component
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                Object productId = table.getValueAt(row, 0);                                                     // Get product ID from the first column (index 0)
                Product product = findProductById((String) productId);                                                  //Find product object using its ID

                int availableItems = product.getAvailableItems();

                if (availableItems < 3) {                                                                               //Turn products with less than 3 items red in color
                    component.setBackground(Color.RED);
                } else {
                    component.setBackground(table.getBackground());
                }
                return component;
            }
        });

        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {                                //Add listener to table
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        Object productId = table.getValueAt(selectedRow, 0);                                     //Get the value from the model

                        if (productId != null) {                                                                        // Find the product in the original list
                            Product selectedProduct = findProductById(productId.toString());
                            showDetails(selectedProduct);                                                               //Run show details method for the specific product
                        }
                    }
                }
            }
        });

        table.setRowHeight(30);                                                                                         //Formatting for the table
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        table.getColumnModel().getColumn(4).setPreferredWidth(100);

        JScrollPane tablePane = new JScrollPane(table);                                                                 //New scroll panel containing table
        midPanel.add(tablePane);                                                                                        //Add scroll pane to mid panel

        JPanel productPanel = new JPanel();                                                                             //Create product panel with flow layout
        productPanel.setLayout(new FlowLayout(FlowLayout.LEFT,20,20));

        JPanel bottomLeftPanel = new JPanel(new GridLayout(7,1,10,10));                             //Bottom left panel with grid layout
        JLabel topic = new JLabel("Product Details :");

        id = new JLabel();                                                                                              //Labels containing details
        id.setFont(defaultFont);
        category = new JLabel();
        category.setFont(defaultFont);
        name = new JLabel();
        name.setFont(defaultFont);
        info1 = new JLabel();
        info1.setFont(defaultFont);
        info2 = new JLabel();
        info2.setFont(defaultFont);
        items = new JLabel();
        items.setFont(defaultFont);
        bottomLeftPanel.add(topic);                                                                                     //Add labels to bottom left panel
        bottomLeftPanel.add(id);
        bottomLeftPanel.add(category);
        bottomLeftPanel.add(name);
        bottomLeftPanel.add(info1);
        bottomLeftPanel.add(info2);
        bottomLeftPanel.add(items);

        JPanel addToCartPanel = new JPanel();                                                                           //New add to cart panel

        JButton addToCart = new JButton("Add to Cart");
        addToCart.addActionListener(new ActionListener() {                                                              //Add listener to "Add to Cart" button
            @Override
            public void actionPerformed(ActionEvent e) {                                                                //Override action performed to add product to list and reduce availability
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    // Get the product ID from the selected row
                    Object productId = table.getValueAt(selectedRow, 0);
                    if (productId != null) {
                        // Find the corresponding product in the original list
                        Product selectedProduct = findProductById(productId.toString());                                //Acquire selected product in table

                        // Add the selected product to the user's shopping cart
                        if (selectedProduct.getAvailableItems() > 0) {                                                  //If available add to cart and reduce availability
                            user.getCart().addProduct(selectedProduct);
                            selectedProduct.setAvailableItems(selectedProduct.getAvailableItems()-1);
                            updateTable();
                            JOptionPane.showMessageDialog(ShoppingGUI.this, "Product added to cart!");
                            WestminsterShoppingCenter.saveProductsToFile();
                        } else {
                            JOptionPane.showMessageDialog(ShoppingGUI.this, "Product out of Stock!");       //Cant add product when availability is less tahn 1
                        }

                    }
                }
            }
        });


        addToCartPanel.add(addToCart);
        productPanel.add(bottomLeftPanel);

        JPanel bottomPanel = new JPanel(new BorderLayout());                                                            //Create bottom panel
        bottomPanel.add(productPanel,BorderLayout.WEST);                                                                //Add product panel with details and addtoCart panel with button to bottom panel
        bottomPanel.add(addToCartPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);                                                                              //Add panels to frame
        add(midPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);                                                                                               //Set visibility

    }

    public void updateTable(){                                                                                          //Method to update the table

        model.setRowCount(0);

        String selectedCategory = comboBox.getSelectedItem().toString();                                                //Get selected in combo box

        for (Product product : products) {                                                                              //Loop product list, get data according to selection
            if (selectedCategory.equals("All") || (product instanceof Electronics && selectedCategory.equals("Electronics"))
                    || (product instanceof Clothing && selectedCategory.equals("Clothing"))) {
                Object[] rowData = new Object[0];
                if (product instanceof Electronics){
                    rowData = new Object[]{product.getProductID(), product.getProductName(), "Electronics" , product.getPrice(), ((Electronics) product).getBrand() +" , "+ ((Electronics) product).getWarrantyPeriod()+ " Months"};
                } else if (product instanceof Clothing) {
                    rowData = new Object[]{product.getProductID(), product.getProductName(), "Clothing" , product.getPrice(), ((Clothing) product).getSize() +" , "+ ((Clothing) product).getColor()};

                }

                model.addRow(rowData);                                                                                  //Add row to table if correct category

            }
        }

    }
    public void showDetails(Product product){                                                                           //Method to set the values of JLabels for product details
        String[] detailList = product.toString().split("\n");

        id.setText(detailList[2]);
        category.setText(detailList[1]);
        name.setText(detailList[3]);
        info1.setText(detailList[6]);
        info2.setText(detailList[7]);
        items.setText(detailList[4]);

    }

    public Product findProductById(String productId) {                                                                  //Method to iterate through product list and find product object by ID
        for (Product product : products) {
            if (product.getProductID().equals(productId)) {
                return product;
            }
        }
        return null;                                                                                                    // Return null if not found
    }


}




//Reference TableCellRenderer https://docs.oracle.com/javase/8/docs/api/javax/swing/table/DefaultTableCellRenderer.html
//Reference TableCellRenderer http://www.java2s.com/ref/java/java-defaulttablecellrenderer-create-custom-cell-renderer.html
//Reference https://stackoverflow.com/questions/17388866/getting-selected-item-from-a-javafx-tableview