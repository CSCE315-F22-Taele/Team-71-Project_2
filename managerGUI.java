import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
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

    public String iName = "";
    public int iNumber = 0;
    
    jdbcpostgreSQL2 jd = new jdbcpostgreSQL2();

    public managerGUI(){
        frame = new JFrame();
        frame.setTitle("Server");
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(new Dimension(800 ,800));

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
}
