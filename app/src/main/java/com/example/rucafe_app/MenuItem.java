package com.example.rucafe_app;

/**
 * MenuItem class which is Parent class of Donut and Coffee
 * @author Minseok Park, Amogh Sarangdhar
 */

public class MenuItem
{
    public double unitPrice = 0;
    public int numOfItems = 0;

    /**
     * Default Constructor for MenuItem instance
     */
    public MenuItem()
    {

    }

    /**
     * Getter to get the number of the item.
     * @return int, number of items in the order
     */
    public int getNumOfItems()
    {
        return numOfItems;
    }

    /**
     * Setter to set the number of the item.
     * @param numOfItems
     */
    public void setNumOfItems(int numOfItems)
    {
        this.numOfItems = numOfItems;
    }

    /**
     * Adding num of MenuItems
     * @param numOfItems
     */
    public void addNumOfItems(int numOfItems)
    {
        this.numOfItems = this.numOfItems + numOfItems;
    }

    /**
     * equals() method to use ArrayList's functions properly.
     * Check if two MenuItems are equal
     * @param obj, check the MenuItem is same to target (to use ArrayList's functions properly)
     * @return true, if same, false otherwise
     */
    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof Coffee)
        {
            return this.equals(obj);
        }
        else
        {
            return this.equals(obj);
        }
    }

    /**
     * Getter to get "Total" price of the item
     * @return double, Total price of the item.
     */
    public double itemPrice()
    {
        return unitPrice * numOfItems;  //need to change later
    }

}
