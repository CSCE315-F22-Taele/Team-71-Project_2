import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * This class holds the main functionality behind the manager logic.
 * 
 * @author Team_71
 */

public class managerGUI {
    private JFrame frame;

    private JPanel inventoryView;
    private JPanel inventoryViewContainer;
    private JScrollPane scrollPane;
    private JTextArea inventoryStats;

    private JPanel inventoryUpdater;
    private JButton update;
    private JTextArea ingredientNameArea;
    private JTextArea ingredientNumberArea;

    private JButton addNewItem;
    private JTextArea newItemNameArea;
    private JTextArea newItemCostArea;
    private JTextArea newItemIngredientArea;

    public String iName = "";
    public int iNumber = 0;

    jdbcpostgreSQL2 jd = new jdbcpostgreSQL2();

    /**
     * Constructor for the managerGUI object
     * 
     * @param
     * @return
     * @throws
     */

    public managerGUI() {
        frame = new JFrame();
        frame.setTitle("Manager");
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(new Dimension(1000, 800));

        salesAndServerButton();

        inventoryUpdater = new JPanel(new GridLayout(10, 1, 0, 5));
        inventoryUpdater.setBounds(0, 0, 150, 750);
        inventoryUpdaterGUI();

        frame.getContentPane().add(inventoryUpdater);

        inventoryView = new JPanel(new GridLayout(1, 1));
        inventoryViewContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        inventoryViewerGUI();
        frame.getContentPane().add(scrollPane);
        frame.setVisible(true);

    }

    /**
     * The inventoryViewerGUI function creates a GUI that displays the inventory.
     * 
     *
     * @param
     * @return A jscrollpane object
     * @throws
     */
    public void inventoryViewerGUI() {
        inventoryStats = new JTextArea();
        inventoryStats.setEditable(false);
        inventoryView.add(inventoryStats);
        inventoryViewContainer.add(inventoryView);
        scrollPane = new JScrollPane(inventoryViewContainer);
        inventoryView.setBackground(Color.BLACK);
        scrollPane.setBounds(200, 110, 500, 625);

    }

    /**
     * The inventoryUpdaterGUI function creates a GUI for the inventoryUpdater
     * class.
     * It has three buttons: update, addNewItem, and exit. The update button updates
     * the inventory file with new information from the user inputted text areas.
     * The addNewItem button adds a new item to the inventory file using user
     * inputted data from text areas.
     * The exit button exits out of this GUI window and returns to main menu when
     * pressed by closing all windows in this program (including itself).
     *
     * 
     * @param
     * @return
     * @return
     *
     */
    public void inventoryUpdaterGUI() {
        JLabel Updater = new JLabel("Updater");
        Updater.setHorizontalAlignment(JLabel.CENTER);
        Updater.setFont(new Font("Serif", Font.BOLD, 20));
        inventoryUpdater.add(Updater);

        update = new JButton("Update");
        update.setPreferredSize(new Dimension(50, 50));
        inventoryUpdater.add(update);
        updateInventoryView();

        JLabel EnterIngredient = new JLabel("Enter Ingredient Below");
        EnterIngredient.setHorizontalAlignment(JLabel.CENTER);
        EnterIngredient.setFont(new Font("Serif", Font.BOLD, 10));
        inventoryUpdater.add(EnterIngredient);

        ingredientNameArea = new JTextArea();
        inventoryUpdater.add(ingredientNameArea);

        JLabel EnterIngredientNum = new JLabel("Enter Ingredient Amount");
        EnterIngredientNum.setHorizontalAlignment(JLabel.CENTER);
        EnterIngredientNum.setFont(new Font("Serif", Font.BOLD, 10));
        inventoryUpdater.add(EnterIngredientNum);

        ingredientNumberArea = new JTextArea();
        inventoryUpdater.add(ingredientNumberArea);

        addNewItem = new JButton("Enter name,cost,ingredient");
        addNewItem.setPreferredSize(new Dimension(50, 50));
        addNewItem.setFont(new Font("Serif", Font.BOLD, 10));
        inventoryUpdater.add(addNewItem);
        addNewItemPressed();

        newItemNameArea = new JTextArea();
        inventoryUpdater.add(newItemNameArea);

        newItemCostArea = new JTextArea();
        inventoryUpdater.add(newItemCostArea);

        newItemIngredientArea = new JTextArea();
        inventoryUpdater.add(newItemIngredientArea);

    }

    /**
     * The updateInventoryView function updates the inventory view.
     * 
     *
     * @param
     * @return
     * @throws
     *
     */
    public void updateInventoryView() {
        update.addActionListener(e -> {
            System.out.println("test2");
            System.out.println(jd.viewInventory());
            // inventoryStats.setText("WORKING");

            if (ingredientNameArea.getText().length() != 0) {
                iName = ingredientNameArea.getText();
                iNumber = Integer.parseInt(ingredientNumberArea.getText());
                jd.changeInventory();
            }
            inventoryStats.setText(jd.viewInventory());

        });
    }

    /**
     * The salesAndServerButton function creates a button that will open the
     * salesReportGUI frame.
     * The function also creates a button that will open the serverGUI frame.
     *
     * 
     * @param
     * @return
     * @throws
     *
     */
    public void salesAndServerButton() {
        salesReportGUI sr = new salesReportGUI();

        JButton server = new JButton("Server");
        server.setBounds(850, 20, 100, 30);
        frame.add(server);

        server.addActionListener(e -> {
            jdbcpostgreSQL2.sg.frame.setVisible(true);
            frame.setVisible(false);
            sr.frame.setVisible(false);

        });

        JButton salesData = new JButton("<html>" + "sales" + "<br/>" + "data" + "</html>");
        salesData.setBounds(750, 20, 100, 30);
        frame.add(salesData);

        salesData.addActionListener(e -> {
            // jdbcpostgreSQL2.sg.frame.setVisible(true);
            // frame.setVisible(false);
            sr.frame.setVisible(true);
        });
    }

    /**
     * The addNewItemPressed function is called when the addNewItem button is
     * pressed.
     * It adds a new item to the menu, and also adds it to the list of buttons that
     * are displayed on screen.
     *
     *
     * @param
     * @return
     * @throws
     */
    public void addNewItemPressed() {
        addNewItem.addActionListener(e -> { // System.out.println("test2");

            for (int i = 0; i < jdbcpostgreSQL2.sg.buttonList.size(); i++) {
                if (jdbcpostgreSQL2.sg.buttonList.get(i).getKey().compareTo(newItemNameArea.getText()) == 0) {
                    double cost = Double.parseDouble(newItemCostArea.getText());
                    jdbcpostgreSQL2.sg.costMap.put(newItemNameArea.getText(), cost);
                    jdbcpostgreSQL2.sg.buttonList.get(i).setCost(cost);
                    jdbcpostgreSQL2.sg.buttonList.get(i).changeName(newItemNameArea.getText(), cost);

                }
            }

            Boolean notIn = false;
            for (Map.Entry<String, Double> costMap : jdbcpostgreSQL2.sg.costMap.entrySet()) {
                String key = costMap.getKey();
                if (key.equals(newItemNameArea.getText())) {
                    notIn = true;
                }
            }

            if (notIn == false) {
                jdbcpostgreSQL2.sg.costMap.put(newItemNameArea.getText(),
                        Double.parseDouble(newItemCostArea.getText()));
                jdbcpostgreSQL2.sg.ingredientMap.put(newItemNameArea.getText(), newItemIngredientArea.getText());

                item button = new item(newItemNameArea.getText(), Double.parseDouble(newItemCostArea.getText()),
                        newItemIngredientArea.getText());
                button.addActionListener(x -> {
                    System.out.println("nice");
                    jdbcpostgreSQL2.sg.cartNames.add(button.getKey());
                    jdbcpostgreSQL2.sg.display += " " + button.getKey() + "\n";
                    jdbcpostgreSQL2.sg.itemsOrderedText.setText(jdbcpostgreSQL2.sg.display);
                    jdbcpostgreSQL2.sg.cartPrices.add(button.cost);
                    jdbcpostgreSQL2.sg.totalCost.setText(jdbcpostgreSQL2.sg.totalCartCost() + "$");

                });
                jdbcpostgreSQL2.sg.buttonList.add(button);
                jdbcpostgreSQL2.sg.itemPanel.add(button);

            }

        });

    }
}
