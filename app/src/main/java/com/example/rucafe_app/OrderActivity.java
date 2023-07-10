package com.example.rucafe_app;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * This class controls the Order Activity
 * Places the Order and removes the Items from Your Order
 * @author Amogh Sarangdhar, Minseok Park
 */
public class OrderActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView lv_order_orderlist;
    private ArrayAdapter<String> adapter_Items_Str;

    private TextView tv_order_subTotal;
    private TextView tv_order_tax;
    private TextView tv_order_total;
    private TextView tv_order_selectedItem;

    private ArrayList<MenuItem> currentOrderItems;
    private ArrayList<String> items_str = new ArrayList<String>();
    private MenuItem targetItem;

    /**
     * Used to Initialize the Order Activity
     * @param savedInstanceState Bundle object containing the activity's previously saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_main);
        findView();
        setListViewData();
        updateAllPrice();
        lv_order_orderlist.setOnItemClickListener(this);
    }

    /**
     * Links the layout components with their specific id.
     */
    private void findView()
    {
        lv_order_orderlist = findViewById(R.id.lv_order_orderlist);
        tv_order_subTotal = findViewById(R.id.tv_order_subtotal);
        tv_order_tax = findViewById(R.id.tv_order_tax);
        tv_order_total = findViewById(R.id.tv_order_total);
        tv_order_selectedItem = findViewById(R.id.tv_order_selectedItem);
    }

    /**
     * Sets the List View of Your specific Order
     * Displays the order details in the List View
     */
    private void setListViewData()
    {
        currentOrderItems = MainActivity.currentOrder.getItemList();

        for (int i = 0; i < currentOrderItems.size(); i++)
        {
            items_str.add(currentOrderItems.get(i).toString());
        }
        adapter_Items_Str = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items_str);
        lv_order_orderlist.setAdapter(adapter_Items_Str);

    }


    /**
     * Helper method to find and get MenuItem object converting from string of selected item in GUI.
     * @param selectedItem_str
     * @return corresponded MenuItem object of the selected item in GUI
     */
    private MenuItem getSelectedItemFromStr(String selectedItem_str)
    {
        MenuItem result = null;
        for (int i = 0; i < currentOrderItems.size(); i++)
        {
            if (currentOrderItems.get(i).toString().equals(selectedItem_str))
            {
                result = currentOrderItems.get(i);
            }
        }
        return result;
    }


    /**
     * Method to place the Order.
     * @param view the base class for placing order widgets.
     */
    public void placeOrder(View view)
    {
        if(MainActivity.currentOrder.getNumOfOrders() > 0){

            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setMessage("Do you want to place\n" + MainActivity.currentOrder.toString() + "\n as your Order").setTitle("Place Order");
            alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                /**
                 * Inner method of alert.setPositiveButton; Define action/function when positive button is clicked
                 * @param dialog
                 * @param which
                 */
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //if click "yes", then
                    //increment ordernumber before add.
                    MainActivity.num_Orders ++;
                    MainActivity.currentOrder.setOrderNumber(MainActivity.num_Orders);
                    MainActivity.storeOrder.add(MainActivity.currentOrder);
                    MainActivity.currentOrder = null; //depoint
                    MainActivity.currentOrder = new Order(); //repoint to new one.
                    adapter_Items_Str.clear();
                    targetItem = null; //depoint
                    lv_order_orderlist.setAdapter(adapter_Items_Str);
                    updateAllPrice();

                    showToastPlaceOrder();
                    tv_order_selectedItem.setText("");
                }
            });
            alert.setNegativeButton("No", new DialogInterface.OnClickListener()
            {
                /**
                 * Inner method of alert.setNegativeButton; Define action/function when negative button is clicked
                 * @param dialog
                 * @param which
                 */
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    showToastOrderNotPlaced();
                }
            });
            AlertDialog dialog = alert.create();
            dialog.show();
        }else{
            Toast.makeText(this, "No Items in the Order!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Method to remove items that are added to Your Order.
     * @param view the base class for removing items from order widgets.
     */
    public void removeItem(View view)
    {
        if(targetItem != null){
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setMessage("Do you want to remove \n" + targetItem.toString() + "\n from your Order?").setTitle("Remove Items");
            alert.setPositiveButton("Yes", new DialogInterface.OnClickListener()
            {
                /**
                 * Inner method of alert.setPositiveButton; Define action/function when positive button is clicked
                 * @param dialog
                 * @param which
                 */
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    MainActivity.currentOrder.remove(targetItem);
                    updateAllPrice();
                    tv_order_selectedItem.setText("");
                    adapter_Items_Str.remove(targetItem.toString());

                    showToastRemoveSelectedItem();
                    targetItem = null;
                }
            });
            alert.setNegativeButton("No", new DialogInterface.OnClickListener()
            {
                /**
                 * Inner method of alert.setNegativeButton; Define action/function when negative button is clicked
                 * @param dialog
                 * @param which
                 */
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    showToastNotRemoveSelectedItem();
                }
            });
            AlertDialog dialog = alert.create();
            dialog.show();
        }
        else{
            showToastNoItemsToRemove();
        }

    }


    /**
     * Method to update subtotal, tax, and total for the specific order.
     */
    private void updateAllPrice()
    {
        updateSubtotal();
        updateTax();
        updateTotal();        
    }

    /**
     * Method to update Your Order Subtotal
     */
    private void updateSubtotal()
    {
        DecimalFormat df = new DecimalFormat("0.00");
        tv_order_subTotal.setText(df.format(MainActivity.currentOrder.getsubTotal()));
    }

    /**
     * Method to update Your Order tax
     */
    private void updateTax(){
        DecimalFormat df = new DecimalFormat("0.00");
        tv_order_tax.setText(df.format(MainActivity.currentOrder.getTax()));
    }

    /**
     * Method to update Your Order total
     */
    private void updateTotal(){
        DecimalFormat df = new DecimalFormat("0.00");
        tv_order_total.setText(df.format(MainActivity.currentOrder.getTotalPrice()));
    }

    /**
     * Toast to show that the selected Item is successfully removed.
     */
    private void showToastRemoveSelectedItem(){
        Toast.makeText(this, "Selected Item Removed!", Toast.LENGTH_SHORT).show();
    }

    /**
     * Toast to show that the selected Item is NOT removed.
     */
    private void showToastNotRemoveSelectedItem(){
        Toast.makeText(this, "Selected Item Not Removed!", Toast.LENGTH_SHORT).show();
    }

    /**
     * Toast to show that there are no selected items to remove.
     */
    private void showToastNoItemsToRemove(){
        Toast.makeText(this, "No Items to remove!", Toast.LENGTH_SHORT).show();
    }

    /**
     * Toast to show that the Order has been successfully placed.
     */
    private void showToastPlaceOrder(){
        Toast.makeText(this, "Order has been placed!", Toast.LENGTH_SHORT).show();
    }

    /**
     * Toast to show that the Order has not yet been placed.
     */
    private void showToastOrderNotPlaced(){
        Toast.makeText(this, "Order has not been placed!", Toast.LENGTH_SHORT).show();
    }

    /**
     * OnItemClick method to select the item to be removed from the list
     * @param adapterView
     * @param view base class with order widgets
     * @param position position for the Item to be removed from.
     * @param l
     */
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
    {
        targetItem = getSelectedItemFromStr(lv_order_orderlist.getItemAtPosition(position).toString());
        tv_order_selectedItem.setText(lv_order_orderlist.getItemAtPosition(position).toString());
    }
}