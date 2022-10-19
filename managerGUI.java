import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import java.awt.*;
import java.util.Map;

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

    public managerGUI(){
        frame = new JFrame();
        frame.setTitle("Manager");
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(new Dimension(1000 ,800));
        
        salesAndServerButton();


        inventoryUpdater = new JPanel(new GridLayout(10,1,0,5));
        inventoryUpdater.setBounds(0,0,150,750);
        inventoryUpdaterGUI();

        frame.getContentPane().add(inventoryUpdater);

        inventoryView = new JPanel(new GridLayout(1,1));
        inventoryViewContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        inventoryViewerGUI();        
        frame.getContentPane().add(scrollPane);
        frame.setVisible(true);

    }
    public void inventoryViewerGUI(){
        inventoryStats = new JTextArea();
        inventoryStats.setEditable(false);
        inventoryView.add(inventoryStats);
        inventoryViewContainer.add(inventoryView);
        scrollPane = new JScrollPane(inventoryViewContainer);
        inventoryView.setBackground(Color.BLACK);
        scrollPane.setBounds(200,110,500,625);

    }
    public void inventoryUpdaterGUI(){
        JLabel Updater = new JLabel("Updater");
        Updater.setHorizontalAlignment(JLabel.CENTER);
        Updater.setFont(new Font("Serif", Font.BOLD, 20));
        inventoryUpdater.add(Updater);

        update = new JButton("Update");
        update.setPreferredSize(new Dimension(50,50));
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
        addNewItem.setPreferredSize(new Dimension(50,50));
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
    public void updateInventoryView(){
        update.addActionListener(e ->
        {   System.out.println("test2");
            System.out.println(jd.viewInventory());
            //inventoryStats.setText("WORKING");

            if(ingredientNameArea.getText().length() != 0){
                iName = ingredientNameArea.getText();
                iNumber = Integer.parseInt(ingredientNumberArea.getText());
                jd.changeInventory();
            }
            inventoryStats.setText(jd.viewInventory());

        });
    }

    public void salesAndServerButton(){
        salesReportGUI sr = new salesReportGUI();

        JButton server = new JButton("Server");
        server.setBounds(850, 20, 100, 30);
        frame.add(server);

        server.addActionListener( e -> {
            jd.sg.frame.setVisible(true);
            frame.setVisible(false);
            sr.frame.setVisible(false);

        });

        JButton salesData = new JButton("<html>" + "sales" + "<br/>" + "data" + "</html>");
        salesData.setBounds(750, 20, 100, 30);
        frame.add(salesData);

        salesData.addActionListener( e -> {
            //jd.sg.frame.setVisible(true);
            //frame.setVisible(false);
            sr.frame.setVisible(true);
        });
    }

    public void addNewItemPressed(){
        addNewItem.addActionListener(e ->
        {   //System.out.println("test2");
          
            
            for(int i = 0; i < jd.sg.buttonList.size(); i++){
                if(jd.sg.buttonList.get(i).getKey().compareTo(newItemNameArea.getText()) == 0){
                    double cost = Double.parseDouble(newItemCostArea.getText());
                    jd.sg.costMap.put(newItemNameArea.getText(), cost);
                    jd.sg.buttonList.get(i).setCost(cost);
                    jd.sg.buttonList.get(i).changeName(newItemNameArea.getText(), cost);
                   
                }
            }
            
            Boolean notIn = false;
            for (Map.Entry<String, Double> costMap : jd.sg.costMap.entrySet()) {
                String key = costMap.getKey();
                Double cost = costMap.getValue();
                if(key.equals(newItemNameArea.getText())){
                    notIn = true;
                }
            }
            
            if(notIn == false){
                jd.sg.costMap.put(newItemNameArea.getText(), Double.parseDouble(newItemCostArea.getText()));
                jd.sg.ingredientMap.put(newItemNameArea.getText(), newItemIngredientArea.getText());
                
                item button = new item(newItemNameArea.getText() ,Double.parseDouble(newItemCostArea.getText()), newItemIngredientArea.getText());
                button.addActionListener(x -> {
                    System.out.println("nice");
                    jd.sg.cartNames.add(button.getKey());
                    jd.sg.display += " " + button.getKey() + "\n";
                    jd.sg.itemsOrderedText.setText( jd.sg.display);
                    jd.sg.cartPrices.add(button.cost);
                    jd.sg.totalCost.setText(jd.sg.totalCartCost() + "$");

                });
                jd.sg.buttonList.add(button);
                jd.sg.itemPanel.add(button);
                

            }
            
        });

    }
}
