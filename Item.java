
/**
 * Write a description of class Item here.
 * 
 * @author (Edward Pisco) 
 * @version (04/04/16)
 */
public class Item
{
    // instance variables - replace the example below with your own
    private String itemDescription;
    private int itemWeight;
    
    public Item(String itemDescription, int itemWeight)
    {
        this.itemDescription = itemDescription;
        this.itemWeight = itemWeight;
    }

    /**
     * Constructor for objects of class Item
     */
    public String getItemDescription()
    {
        String itemString = "This room conatian: ";
        itemString = itemString + this.itemDescription + "\n Item Weight: " + this.itemWeight;
        return itemString;
    }
}

   
