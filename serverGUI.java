import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.*;
public class serverGUI {
    private JFrame frame;
    private JPanel categoryPanel;

    serverGUI(){
        frame = new JFrame();
        frame.setVisible(true);
        frame.setTitle("Server");
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(new Dimension(800 ,800));

        categoryPanel = new JPanel(new GridLayout(7,1,0,5));
        categoryPanel.setBounds(0,0,150,750);
        catagoriesGUI();
        frame.getContentPane().add(categoryPanel);

    }

    public void catagoriesGUI(){
        JLabel categories = new JLabel("Category");
        categories.setHorizontalAlignment(JLabel.CENTER);
        categories.setFont(new Font("Serif", Font.BOLD, 20));
        categoryPanel.add(categories);

        JButton breakfast = new JButton("Breakfast");
        breakfast.setPreferredSize(new Dimension(50,50));
        categoryPanel.add(breakfast);
        
        JButton Entrees = new JButton("Entrees");
        Entrees.setPreferredSize(new Dimension(50,50));
        categoryPanel.add(Entrees);

        JButton Meals = new JButton("Meals");
        Meals.setPreferredSize(new Dimension(50,50));
        categoryPanel.add(Meals);

        JButton Drinks = new JButton("Drinks");
        Drinks.setPreferredSize(new Dimension(50,50));
        categoryPanel.add(Drinks);

        JButton Treats = new JButton("Treats");
        Treats.setPreferredSize(new Dimension(50,50));
        categoryPanel.add(Treats);

        JButton Sides = new JButton("Sides");
        Sides.setPreferredSize(new Dimension(50,50));
        categoryPanel.add(Sides);
    }

    public void itemsGUI(){
        
    }

}
