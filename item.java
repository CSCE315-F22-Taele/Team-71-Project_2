import java.util.ArrayList;

import javax.swing.JButton;


public class item extends JButton{
    
    private String name;
    private double cost;

    ArrayList<String> ingredientHolder = new ArrayList<String>();

    public item(String name, double cost, String ingredient){
        this.name = name;   
        this.cost = cost;
        setText("<html>" + name + "<br/>" + cost + "</html>");

        addActionListener(e ->
        {
            System.out.println(name);
        });

    }



  
    

}
