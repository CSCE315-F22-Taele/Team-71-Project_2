import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;


public class item extends JButton{
    
    private String name;
    private double cost;
    private String ingredient;


    public item(String name, double cost, String ingredient){
        this.name = name;   
        this.cost = cost;
        this.ingredient = ingredient;
        setText("<html>" + name + "<br/>" + cost + "</html>");

    }
}
