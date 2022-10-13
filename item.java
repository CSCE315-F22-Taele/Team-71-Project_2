import java.util.ArrayList;

import javax.swing.JButton;

public class item extends JButton{
    
    private String name;
    private float cost;
    private ArrayList<String> ingredients;

    public item(String name, float cost){
        this.name = name;   
        this.cost = cost;
        setText("<html>" + name + "<br/>" + cost + "</html>");

    }

}
