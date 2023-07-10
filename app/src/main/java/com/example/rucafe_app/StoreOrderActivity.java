package com.example.rucafe_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * This class controls the Store Order Activity
 * Cancels the Order that is selected.
 * @author Amogh Sarangdhar, Minseok Park
 */
public class StoreOrderActivity extends AppCompatActivity {

    private Spinner spn_storeOrder_number;
    private ListView lv_storeOrder_items;
    private TextView tv_storeOrder_total;
    private Button bt_storeOrder_cancelOrder;

    private ArrayList<Order> orderLists = new ArrayList<Order>();
    private ArrayList<String> order_str = new ArrayList<String>();

    private ArrayAdapter<Integer> adapter_OrderNo;
    private ArrayAdapter<String> adapter_Order_Str;
    private ArrayList<Integer> arrayofOrders = new ArrayList<Integer>();  //for spinner

    /**
     * Used to Initialize the Store Order Activity
     * @param savedInstanceState Bundle object containing the activity's previously saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_order_main);
        findView();

        initializeStoreOrders();
        setSpinnerData();
        setListViewData();
        setEventListner_Spinner();
    }

    /**
     * Links the layout components with their specific id.
     */
    private void findView(){
        spn_storeOrder_number = findViewById(R.id.spn_storeOrder_number);
        lv_storeOrder_items = findViewById(R.id.lv_storeOrder_items);
        tv_storeOrder_total = findViewById(R.id.tv_storeOrder_total);
        bt_storeOrder_cancelOrder = findViewById(R.id.bt_storeOrder_cancelOrder);
    }

    /**
     * Method to initialize Store Orders.
     */
    public void initializeStoreOrders(){
        orderLists = MainActivity.storeOrder.getOrderLists();
        for(int i = 0; i < orderLists.size(); i++)
        {
            arrayofOrders.add(orderLists.get(i).getOrderNumber());
        }
    }

    /**
     * Method to set data in Spinner for Order Numbers.
     */
    public void setSpinnerData(){
        adapter_OrderNo = new ArrayAdapter<Integer>(this, R.layout.support_simple_spinner_dropdown_item, arrayofOrders);
        adapter_OrderNo.notifyDataSetChanged();
        spn_storeOrder_number.setAdapter(adapter_OrderNo);
    }

    /**
     * Set ListView for the corresponding Order number.
     */
    private void setListViewData(){
        orderLists = MainActivity.storeOrder.getOrderLists();
        adapter_Order_Str = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, order_str);
        lv_storeOrder_items.setAdapter(adapter_Order_Str);
    }

    /**
     * Event Listener for Spinner that checks Order Numbers.
     */
    private void setEventListner_Spinner(){

        spn_storeOrder_number.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            /**
             * Inner method of setOnItemSelectedListener; Define functions/action when an item is selected in OrderNo spn
             * @param adapterView
             * @param view
             * @param i
             * @param l
             */
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                String orderNum = spn_storeOrder_number.getSelectedItem().toString();  //order number on UI
                Order thisOrder = findOrderbyOrderNo_Str(orderNum);
                order_str = thisOrder.getStringArrayOfItems();
                setListViewData();
                getTotal();
            }
            /**
             * Inner method of setOnItemSelectedListener; Define functions/action when non of item is selected in OrderNo spn
             * @param adapterView
             */
            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {
                //No action
            }
        });
    }

    /**
     * Method to find the Order from the Order Number selected from the Spinner.
     * @param selectedOrderNo order number selected from the spinner.
     * @return Order that is selected from the spinner.
     */
    private Order findOrderbyOrderNo_Str(String selectedOrderNo)
    {
        Order result = null;
        for(int i = 0; i < orderLists.size(); i++)
        {
            if(orderLists.get(i).getOrderNumber() == Integer.parseInt(selectedOrderNo))
            {
                result = orderLists.get(i);
            }
        }
        return result;
    }

    /**
     * Method to find the Order from the Order Number selected from the Spinner.
     * @param selectedOrderNo order number selected from the spinner.
     * @return Order that is selected from the spinner.
     */
    private Order findOrderbyOrderNo_int(int selectedOrderNo)
    {
        Order result = null;
        for(int i = 0; i < orderLists.size(); i++)
        {
            if(orderLists.get(i).getOrderNumber() == selectedOrderNo)
            {
                result = orderLists.get(i);
            }
        }
        return result;
    }


    /**
     * Method to Cancel a particular Order
     * @param view base class for Store Order widgets
     */
    public void cancelOrder(View view){
        if(MainActivity.storeOrder.getNumOfOrders() > 0){
            String selectedOrderNo_str = spn_storeOrder_number.getSelectedItem().toString();
            MainActivity.storeOrder.getOrderLists().remove(findOrderbyOrderNo_Str(selectedOrderNo_str));
            adapter_OrderNo.remove(Integer.parseInt(selectedOrderNo_str));
            spn_storeOrder_number.setSelection(0);
            getTotal();
            if (orderLists.size() == 0)
            {
                adapter_Order_Str.clear();
            }

        }else{
            Toast.makeText(this, "There are No Placed Orders to Cancel!", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * Method to Get Total cost (Subtotal + Taxes) for the particular order.
     */
    private void getTotal(){
        DecimalFormat df = new DecimalFormat("0.00");
        if (MainActivity.storeOrder.getNumOfOrders() > 0)
        {
            int orderNo = (Integer) (spn_storeOrder_number.getSelectedItem());
            Order selectedOrder = findOrderbyOrderNo_int(orderNo);
            tv_storeOrder_total.setText(df.format(selectedOrder.getTotalPrice()));
        }
        else
        {
            tv_storeOrder_total.setText(df.format(0.00));
        }

    }

}