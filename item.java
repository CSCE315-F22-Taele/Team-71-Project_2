import javax.swing.JButton;

/**
 * This class holds the main functionality behind the item object.
 * 
 * @author Team_71
 */
public class item extends JButton {

    private String name;
    public double cost;
    private String ingredient;

    /**
     * The getIngredient function returns the ingredient that is currently selected
     * in the JComboBox.
     * 
     *
     * @param
     * @return The value of the ingredient instance variable
     * @throws
     */
    public String getIngredient() {
        return ingredient;
    }

    /**
     * The setIngredient function sets the ingredient of this object to the given
     * String.
     *
     * 
     * @param ingredient
     *
     * @return The value of the ingredient variable
     *
     * @throws
     */
    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    /**
     * The item constructor sets the item name, cost, and associated ingredients to
     * the item object
     *
     * 
     * @param name       The item name (string)
     * @param cost       The item cost (double)
     * @param ingredient The item ingredient list (String)
     *
     * @return
     *
     * @throws
     */
    public item(String name, double cost, String ingredient) {
        this.name = name;
        this.cost = cost;
        this.ingredient = ingredient;
        setText("<html>" + name + "<br/>" + cost + "</html>");

    }

    /**
     * The changeName function changes the name of a JButton to newName and its cost
     * to newCost.
     * 
     *
     * @param newName Set the text of the label
     * @param double  Specify the cost of the item
     *
     * @return Nothing
     * @throws
     */
    public void changeName(String newName, double newCost) {
        setText("<html>" + newName + "<br/>" + newCost + "</html>");
    }

    /**
     * The getKey function returns the name of a given item.
     * 
     *
     * @param
     * @return The name of the item
     * @throws
     */
    public String getKey() {
        return name;
    }

    /**
     * The setCost function sets the cost of an item.
     * 
     *
     * @param newCost Set the value of cost
     *
     * @return
     * @throws
     */
    public void setCost(double newCost) {
        cost = newCost;
    }
}
