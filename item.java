import javax.swing.JButton;


public class item extends JButton{
    
    private String name;
    public double cost;
    private String ingredient;

    public String getIngredient() {
        return ingredient;
    }
    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }
    public item(String name, double cost, String ingredient){
        this.name = name;   
        this.cost = cost;
        this.ingredient = ingredient;
        setText("<html>" + name + "<br/>" + cost + "</html>");

    }
    public void changeName(String newName, double newCost){
        setText("<html>" + newName + "<br/>" + newCost + "</html>");
    }
    public String getKey(){
        return name;
    }
    public void setCost(double newCost){
        cost = newCost;
    }
}
