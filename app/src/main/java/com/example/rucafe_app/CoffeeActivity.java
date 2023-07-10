package com.example.rucafe_app;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

/**
 * This class controls Coffee Activity.
 * Adds coffee and AddIns to the order.
 * @author Amogh Sarangdhar, Minseok Park
 */
public class CoffeeActivity extends AppCompatActivity {

    private Coffee coffee;
    private Order currentOrder;

    private Spinner spn_Size;
    private ArrayAdapter<String> adapter_size;
    private String[] sizes = {"Short", "Tall", "Grande", "Venti"};

    private Spinner spn_Qty;
    private ArrayAdapter<Integer> adapter_Qty;
    private Integer[] qtys = new Integer[]{1,2,3,4,5};

    private CheckBox ckBx_cream, ckBx_milk, ckBx_syrup, ckBx_caramel, ckBx_whippedCream;
    private TextView tv_subTotal;

    /**
     * Used to Initialize the Coffee Activity
     * @param savedInstanceState Bundle object containing the activity's previously saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coffee);
        coffee = new Coffee();

        findView();

        adapter_size = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, sizes);
        adapter_Qty = new ArrayAdapter<Integer>(this, R.layout.support_simple_spinner_dropdown_item, qtys);
        spn_Qty.setAdapter(adapter_Qty);
        spn_Size.setAdapter(adapter_size);

        currentOrder = MainActivity.currentOrder;

        setEventListener_Spinner();
        setEventListener_CheckBox();
    }

    /**
     * Links the layout components with their specific id.
     */
    private void findView()
    {
        tv_subTotal = findViewById(R.id.tv_coffee_subTotal);
        ckBx_milk = findViewById(R.id.ckBx_milk);
        ckBx_caramel = findViewById(R.id.ckBx_caramel);
        ckBx_cream = findViewById(R.id.ckBx_cream);
        ckBx_syrup = findViewById(R.id.ckBx_syrup);
        ckBx_whippedCream = findViewById(R.id.ckBx_whippedCream);
        spn_Size = findViewById(R.id.spn_coffee_size);
        spn_Qty = findViewById(R.id.spn_coffee_Qty);
    }

    /**
     * Event Listener for Coffee AddIn checkbox.
     * Checks which Coffee AddIn is selected.
     */
    private void setEventListener_CheckBox()
    {
        ckBx_milk.setOnCheckedChangeListener((buttonView, isChecked) ->
            {
                adjustCoffeeProperty(isChecked, Coffee_AddIn.MILK);
            });
        ckBx_caramel.setOnCheckedChangeListener((buttonView, isChecked) ->
            {
                adjustCoffeeProperty(isChecked, Coffee_AddIn.CARAMEL);
            });
        ckBx_whippedCream.setOnCheckedChangeListener((buttonView, isChecked) ->
            {
                adjustCoffeeProperty(isChecked, Coffee_AddIn.WHIPPED_CREAM);
            });
        ckBx_syrup.setOnCheckedChangeListener((buttonView, isChecked) ->
            {
                adjustCoffeeProperty(isChecked, Coffee_AddIn.SYRUP);
            });
        ckBx_cream.setOnCheckedChangeListener((buttonView, isChecked) ->
            {
                adjustCoffeeProperty(isChecked, Coffee_AddIn.CREAM);
            });
    }

    /**
     * Adds AddIns to Coffee
     * @param isChecked checks if any AddIns are selected
     * @param addIn AddIn for Coffee
     */
    private void adjustCoffeeProperty(boolean isChecked, Coffee_AddIn addIn)
    {
        if (isChecked)
        {
            coffee.add(addIn);
        }
        else
        {
            coffee.remove(addIn);
        }
        subtotalUpdate();
    }

    /**
     * Even Listener for Spinner (to select Coffee size).
     * Checks the size of Coffee selected.
     */
    private void setEventListener_Spinner()
    {
        spn_Size.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            /**
             * Inner method of setOnItemSelectedListener. Define function/action when an item of SIZE spn is selected.
             * @param adapterView
             * @param view
             * @param i
             * @param l
             */
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                coffee.setSize_String(spn_Size.getSelectedItem().toString());
                subtotalUpdate();

            }

            /**
             * Inner method of setOnItemSelectedListener. Define function/action when non of item is selected in SIZE spn
             * @param adapterView
             */
            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {
                //no actions...
            }
        });

        spn_Qty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            /**
             * Inner method of setOnItemSelectedListener. Define function/action when an item of QTY spn is selected.
             * @param adapterView
             * @param view
             * @param i
             * @param l
             */
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                coffee.setNumOfItems(Integer.parseInt(spn_Qty.getSelectedItem().toString()));
                subtotalUpdate();
                //Do something here when an size is selected
            }

            /**
             * Inner method of setOnItemSelectedListener. Define function/action  when non of item is selected in QTY spn
             * @param adapterView
             */
            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });
    }

    /**
     * Live updates the Coffee subtotal.
     */
    public void subtotalUpdate()
    {
        DecimalFormat df = new DecimalFormat("0.00");
        Double subTotal = coffee.itemPrice();
        tv_subTotal.setText(df.format(subTotal));
    }

    /**
     * Adds the Coffee to the Order
     * @param view
     */
    public void addCoffee(View view)
    {
        currentOrder.add(coffee);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage("Do you want to add\n" + coffee.toString() + "\n into your current Order?").setTitle("Add Coffee");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener()
        {
            /**
             * Inner method of Alert.setPositiveButton; Define action/function when positive button is clicked
             * @param dialog
             * @param which
             */
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showToastAdded();
                resetProperties();
            }
        });
        /**
         * Inner method of Alert.setPositiveButton; Define action/function when negative button is clicked
         */
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showToastNOTAdded();
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }

    /**
     * Toast for successfully adding Coffee to Order
     */
    private void showToastAdded()
    {
        Toast.makeText(this, "Coffee Added!", Toast.LENGTH_SHORT).show();
    }

    /**
     * Toast when Coffee is not added to Order
     */
    private void showToastNOTAdded()
    {
        Toast.makeText(this, "Coffee is not added", Toast.LENGTH_SHORT).show();
    }


    /**
     * Reset all the Coffee Properties when a type of Coffee is ordered
     */
    private void resetProperties()
    {
        coffee = new Coffee(); //depoint.
        spn_Size.setSelection(0);
        spn_Qty.setSelection(0);
        ckBx_cream.setChecked(false);
        ckBx_syrup.setChecked(false);
        ckBx_caramel.setChecked(false);
        ckBx_milk.setChecked(false);
        ckBx_whippedCream.setChecked(false);
    }


}