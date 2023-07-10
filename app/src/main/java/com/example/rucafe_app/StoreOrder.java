package com.example.rucafe_app;

import java.util.ArrayList;

/**
 * Store Order which contain all DB of a program and methods to handle Order objects.
 * @author Minseok Park, Amogh Sarangdhar
 */
public class StoreOrder implements Customizable{

    private ArrayList<Order> orderLists = new ArrayList<Order>();

    /**
     * Get Orderlists of the StoreOrder
     * @return ArrayList (Order), All orders in this program.
     */
    public ArrayList<Order> getOrderLists()
    {
        return orderLists;
    }

    /**
     *  Get actual number of Orders in DB
     * @return int actual number of orders in DB
     */
    public int getNumOfOrders()
    {
        return orderLists.size();
    }

    /**
     * Add an order to Orderlist of the StoreOrder
     * @param obj Object, newly placed order.
     * @return
     */
    @Override
    public boolean add(Object obj)
    {
        orderLists.add((Order) obj);
        return true;
    }

    /**
     * Remove target Order object from Orderlist of the StoreOrder
     * @param obj Object selected Order Object.
     * @return
     */
    @Override
    public boolean remove(Object obj)
    {
        if (orderLists.remove((Order) obj) == true)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Readability enhancer method which is equal to orderList.toString.
     * @return orderLists.toString();
     */
    @Override
    public String toString()
    {
        return orderLists.toString();
    }
}
