import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Contains the main code and logic that is surfaced in the server GUI
 * 
 * @author Team_71
 */
public class serverGUI {
    public JFrame frame;

    private JPanel cartPanel;
    public JLabel totalCost;
    public JTextArea itemsOrderedText;
    private JScrollPane itemScroll;
    public String display = "";
    private JButton purchase;
    private JButton clear;
    JButton managerButton;

    public JPanel itemPanel;
    public JPanel itemContainer;
    public JScrollPane scrollPane;

    Map<String, Double> costMap = new LinkedHashMap<String, Double>();
    HashMap<String, String> ingredientMap = new HashMap<String, String>();

    public ArrayList<Double> cartPrices = new ArrayList<Double>();
    public ArrayList<String> cartNames = new ArrayList<String>();
    public ArrayList<String> ingredientList = new ArrayList<String>();

    public ArrayList<item> buttonList = new ArrayList<item>();

    jdbcpostgreSQL2 jd = new jdbcpostgreSQL2();
    public managerGUI mg;
    public String date;

    /**
     * Constructor for server GUI object
     * 
     * @param
     * @return
     * @throws
     */
    public serverGUI() {
        frame = new JFrame();
        frame.setTitle("Server");
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(new Dimension(800, 900));

        cartPanel = new JPanel(new GridLayout(7, 1, 0, 5));
        cartPanel.setBounds(0, 0, 150, 750);
        cartGUI();
        frame.getContentPane().add(cartPanel);
        frame.getContentPane().add(cartPanel);

        makeHashMap();

        itemPanel = new JPanel(new GridLayout(9, 5));
        itemContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        itemsGUI();
        frame.getContentPane().add(scrollPane);

        frame.setVisible(true);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");
        LocalDateTime now = LocalDateTime.now();

        date = dtf.format(now);

    }

    /**
     * The cartGUI function creates a GUI for the cart.
     * 
     * 
     *
     * @param
     * @return
     * @throws
     *
     */
    public void cartGUI() {

        JLabel categories = new JLabel("CART");
        categories.setHorizontalAlignment(JLabel.CENTER);
        categories.setFont(new Font("Serif", Font.BOLD, 20));
        cartPanel.add(categories);

        purchase = new JButton("PURCHASE");
        purchase.setPreferredSize(new Dimension(50, 50));
        cartPanel.add(purchase);
        purchaseItems();

        clear = new JButton("CLEAR");
        clear.setPreferredSize(new Dimension(50, 50));
        cartPanel.add(clear);
        clear();

        managerButton = new JButton("Open Manager");
        managerButton.setPreferredSize(new Dimension(50, 50));
        cartPanel.add(managerButton);
        managerScreen();

        totalCost = new JLabel("0.00");
        totalCost.setHorizontalAlignment(JLabel.CENTER);
        totalCost.setFont(new Font("Serif", Font.BOLD, 20));
        cartPanel.add(totalCost);

        itemsOrderedText = new JTextArea();
        itemsOrderedText.setText(display);
        itemScroll = new JScrollPane(itemsOrderedText);
        cartPanel.add(itemScroll);

    }

    /**
     * The itemsGUI function creates a GUI for the items in the menu.
     * It creates buttons for each item and adds them to an array list.
     * The function also sets up a scroll pane that allows users to see all of the
     * items on one screen.
     *
     *
     * @param
     * @return
     * @throws
     *
     */
    public void itemsGUI() {
        item button;

        for (Map.Entry<String, Double> costMap : costMap.entrySet()) {
            String key = costMap.getKey();
            Double cost = costMap.getValue();

            button = new item(key, cost, ingredientMap.get(key));
            buttonList.add(button);

            button.addActionListener(e -> {
                System.out.println(key + " " + cost + " " + ingredientMap.get(key));

                // cartPrices.add(cost);
                addToAry(key);
                cartNames.add(key);

                totalCost.setText(totalCartCost() + "$");

                display += " " + key + "\n";
                itemsOrderedText.setText(display);

                ingredientList.addAll(Arrays.asList(ingredientMap.get(key).split(",")));

            });

            button.setPreferredSize(new Dimension(100, 100));
            itemPanel.add(button);
        }

        itemContainer.add(itemPanel);
        scrollPane = new JScrollPane(itemContainer);
        scrollPane.setBounds(200, 110, 500, 625);

    }

    /**
     * The addToAry function adds the cost of each item in the buttonList to an
     * ArrayList.
     * 
     *
     * @param String
     *
     * @return
     *
     * @throws
     */
    public void addToAry(String name) {
        for (int i = 0; i < buttonList.size(); i++) {
            if (buttonList.get(i).getKey().equals(name)) {
                cartPrices.add(buttonList.get(i).cost);

            }
        }
    }

    /**
     * The makeHashMap function creates a HashMap of the cost and ingredients for
     * each item.
     * 
     *
     * @param
     * @return
     * @throws
     *
     * 
     */
    public void makeHashMap() {
        /* Make hashmaps */
        costMap.put("Chick-fil-A E", 4.19);
        ingredientMap.put("Chick-fil-A E", "BUNS,CHICKEN_B,SAUCE,PICKLES");

        costMap.put("Chick-fil-A M", 7.55);
        ingredientMap.put("Chick-fil-A M", "BUNS,CHICKEN_B,SAUCE,PICKLES,POTATOES,CHEESE");

        costMap.put("Chick-fil-A Deluxe E", 4.89);
        ingredientMap.put("Chick-fil-A Deluxe E", "BUNS,CHICKEN_B,SAUCE,TOMATOES,LETTUCE,PICKLES,BACON");
        costMap.put("Chick-fil-A Deluxe M", 8.25);
        ingredientMap.put("Chick-fil-A Deluxe E", "BUNS,CHICKEN_B,TOMATOES,LETTUCE,PICKLES,POTATOES");

        costMap.put("Spicy Chicken E", 4.49);
        ingredientMap.put("Spicy Chicken E", "BUNS,CHICKEN_B,SAUCE,PICKLES");
        costMap.put("Spicy Chicken M", 7.79);
        ingredientMap.put("Spicy Chicken M", "BUNS,CHICKEN_B,SAUCE,PICKLES,POTATOES");

        costMap.put("Chick-fil-A nuggets 8ct E", 4.29);
        ingredientMap.put("Chick-fil-A nuggets 8ct E", "CHICKEN_N");
        costMap.put("Chick-fil-A nuggets 8ct M", 7.65);
        ingredientMap.put("Chick-fil-A nuggets 8ct M", "CHICKEN_N,POTATOES");

        costMap.put("Chick-fil-A nuggets 12ct E", 6.15);
        ingredientMap.put("Chick-fil-A nuggets 12ct E", "CHICKEN_N");
        costMap.put("Chick-fil-A nuggets 12ct M", 9.45);
        ingredientMap.put("Chick-fil-A nuggets 12ct M", "CHICKEN_N,POTATOES");

        costMap.put("Chick-n-Strips 3ct E", 4.69);
        ingredientMap.put("Chick-n-Strips 3ct E", "CHICKEN_T");
        costMap.put("Chick-n-Strips 3ct M", 7.99);
        ingredientMap.put("Chick-n-Strips 3ct M", "CHICKEN_T,POTATOES");

        costMap.put("Chick-n-Strips 4ct E", 6.09);
        ingredientMap.put("Chick-n-Strips 4ct E", "CHICKEN_T");
        costMap.put("Chick-n-Strips 4ct M", 9.35);
        ingredientMap.put("Chick-n-Strips 4ct M", "CHICKEN_T,POTATOES");

        // grilled meals
        costMap.put("Grilled Chicken E", 5.79);
        ingredientMap.put("Grilled Chicken E", "BUNS,CHICKEN_B,SAUCE,PICKLES,TOMATOES,LETTUCE");
        costMap.put("Grilled Chicken M", 9.15);
        ingredientMap.put("Grilled Chicken M", "BUNS,CHICKEN_B,SAUCE,PICKLES,TOMATOES,LETTUCE,POTATOES");

        costMap.put("Grilled Nuggets 8ct E", 5.15);
        ingredientMap.put("Grilled Nuggets 8ct E", "CHICKEN_N");
        costMap.put("Grilled Nuggets 8ct M", 8.45);
        ingredientMap.put("Grilled Nuggets 8ct M", "CHICKEN_N,POTATOES");

        costMap.put("Grilled Nuggets 12ct E", 7.45);
        ingredientMap.put("Grilled Nuggets 12ct E", "CHICKEN_N");
        costMap.put("Grilled Nuggets 12ct M", 10.75);
        ingredientMap.put("Grilled Nuggets 12ct M", "CHICKEN_N,POTATOES");

        costMap.put("Grilled Chicken Club E", 7.29);
        ingredientMap.put("Grilled Chicken Club E", "BUNS,CHICKEN_B,SAUCE,PICKLES,TOMATOES,LETTUCE,BACON");
        costMap.put("Grilled Chicken Club M", 10.65);
        ingredientMap.put("Grilled Chicken Club M", "BUNS,CHICKEN_B,SAUCE,PICKLES,TOMATOES,LETTUCE,BACON,POTATOES");

        costMap.put("Grilled Chicken Cool Wrap E", 7.25);
        ingredientMap.put("Grilled Chicken Cool Wrap E", "CHICKEN_B,SAUCE,TORTILLA");
        costMap.put("Grilled Chicken Cool Wrap M", 10.59);
        ingredientMap.put("Grilled Chicken Cool Wrap M", "CHICKEN_B,SAUCE,TORTILLA,POTATOES");

        // kids meals
        costMap.put("Chick-fil-A nuggets 5ct Kids Meal", 5.15);
        ingredientMap.put("Chick-fil-A nuggets 5ct Kids Meal", "CHICKEN_N");

        costMap.put("Grilled nuggets 5ct Kids Meal", 5.69);
        ingredientMap.put("Grilled nuggets 5ct Kids Meal", "CHICKEN_N");

        // sides
        costMap.put("Chick-fil-A Small Waffle Potato Fries", 1.89);
        ingredientMap.put("Chick-fil-A Small Waffle Potato Fries", "POTATOES");
        costMap.put("Chick-fil-A Medium Waffle Potato Fries", 2.19);
        ingredientMap.put("Chick-fil-A Medium Waffle Potato Fries", "POTATOES, POTATOES");
        costMap.put("Chick-fil-A Large Waffle Potato Fries", 2.59);
        ingredientMap.put("Chick-fil-A Large Waffle Potato Fries", "POTATOES, POTATOES,, POTATOES");

        costMap.put("Small Fruit Cup", 2.79);
        ingredientMap.put("Small Fruit Cup", "FRUIT_CUP");
        costMap.put("Medium Fruit Cup", 3.65);
        ingredientMap.put("Medium Fruit Cup", "FRUIT_CUP");

        costMap.put("Small Mac & Cheese", 2.85);
        ingredientMap.put("Small Mac & Cheese", "MAC_&_CHEESE,CHEESE");
        costMap.put("Medium Mac & Cheese", 3.65);
        ingredientMap.put("Medium Mac & Cheese", "MAC_&_CHEESE,CHEESE");

    }

    /**
     * The totalCartCost function returns the total cost of all items in the cart.
     * 
     *
     * @param
     * @return String
     * @throws
     *
     */
    public String totalCartCost() {
        double total = 0;
        for (int i = 0; i < cartPrices.size(); i++) {
            total += cartPrices.get(i);
        }
        DecimalFormat round = new DecimalFormat("##.00");
        return round.format(total);
    }

    /**
     * The purchaseItems function is used to add items to the cart.
     * It takes no parameters and returns nothing.
     * The function adds items from the menu into a list of strings called
     * cartNames,
     * which are then displayed in a text area on the GUI.
     *
     * 
     * @param
     * @return
     * @throws
     */
    public void purchaseItems() {
        purchase.addActionListener(e -> {
            // need to call sql command here to insert into sales history
            jd.addOrder();
            // reset
            cartNames.clear();
            cartPrices.clear();
            ingredientList.clear();

            totalCost.setText("0.00" + "$");
            display = "";
            itemsOrderedText.setText(display);
        });

    }

    /**
     * The clear function clears the cartNames, cartPrices, ingredientList and
     * totalCost.
     * It also sets display to an empty string and sets itemsOrderedText to display.
     *
     *
     * @param
     * @return
     * @throws
     *
     */
    public void clear() {
        clear.addActionListener(e -> {
            cartNames.clear();
            cartPrices.clear();
            ingredientList.clear();

            totalCost.setText("0.00" + "$");
            display = "";
            itemsOrderedText.setText(display);
        });
    }

    /**
     * The managerScreen function creates a GUI for the manager.
     * 
     *
     * @param
     * @return
     * @throws
     */
    public void managerScreen() {
        managerButton.addActionListener(e -> {
            mg = new managerGUI();
            frame.setVisible(false);

        });
    }
}
