package com.example.rucafe_app;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

/**
 * This class controls Donut Activity.
 * Adds Donuts and selects its flavors.
 * @author Amogh Sarangdhar, Minseok Park
 */
public class DonutActivity extends AppCompatActivity {

    private Donut donut;
    private Order currentOrder; //ptr

    private Spinner spn_Type;
    private ArrayAdapter<String> adapter_Type;
    private String[] types = {"Yeast", "Cake", "Donut hole"};

    private Spinner spn_Qty;
    private ArrayAdapter<Integer> adapter_Qty;
    private Integer[] qtys = new Integer[]{1,2,3,4,5};

    private TextView tv_donut_selected_flavor, tv_donut_subTotal;

    private Button bt_donut_choose_flavor;

    /**
     * Used to Initialize the Donut Activity
     * @param savedInstanceState Bundle object containing the activity's previously saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donut_main);

        findView();

        donut = new Donut();
        Donut_flavor flavor = setFlavor(tv_donut_selected_flavor.getText().toString());
        donut.setFlavor(flavor);
        currentOrder = MainActivity.currentOrder;

        setSpinnerData();
        setEventListner_Spinner();
        setListner_BackBtn();
    }

    /**
     * This method sets flavors for Donuts
     * @param tvFlavorStr takes in the flavor selected by the user.
     * @return Donut_flavor that the user has selected.
     */
    private Donut_flavor setFlavor(String tvFlavorStr)
    {
        switch (tvFlavorStr)
        {
            case "Plain": return Donut_flavor.PLAIN;
            case "Glaze": return Donut_flavor.GLAZE;
            case "Chocolate": return Donut_flavor.CHOCOLATE;
            case "Strawberry": return Donut_flavor.STRAWBERRY;
            case "Boston": return Donut_flavor.BOSTON;
            case "Apple Fritter": return Donut_flavor.APPLE_FRITTER;
            case "Banana": return Donut_flavor.BANANA;
            case "Powder sugar": return Donut_flavor.POWDER_SUGAR;
            case "Maple": return Donut_flavor.MAPLE;
            case "Eclair": return Donut_flavor.ECLAIR;
            case "Scarlet Special": return Donut_flavor.SCARLET_SPECIAL;
            case "Busch Goose": return Donut_flavor.BUSCH_GOOSE;
            default: return null;
        }
    }

    /**
     * Links the layout components with their specific id.
     */
    private void findView()
    {
        spn_Type = findViewById(R.id.spn_donut_type);
        spn_Qty = findViewById(R.id.spn_donut_Qty);
        tv_donut_subTotal = findViewById(R.id.tv_donut_subTotal);
        tv_donut_selected_flavor = findViewById(R.id.tv_donut_selected_flavor);
        tv_donut_selected_flavor.setText(getIntent().getStringExtra("FLAVOR"));
        bt_donut_choose_flavor = findViewById(R.id.bt_donut_choose_flavor);
    }

    /**
     * This methods sets the spinner data for Donut Type and Donut quantity.
     */
    private void setSpinnerData()
    {
        adapter_Type = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, types);
        adapter_Qty = new ArrayAdapter<Integer>(this, R.layout.support_simple_spinner_dropdown_item, qtys);
        spn_Qty.setAdapter(adapter_Qty);
        spn_Type.setAdapter(adapter_Type);
    }

    /**
     * Even Listener for Donut Spinner
     * Checks which Donut type and Quantity of donut is selected.
     */
    private void setEventListner_Spinner()
    {
        spn_Type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            /**
             * Inner method of setEventListner_Spinner(). Define functions/actions depending on selected TYPE in spinner by user.
             * @param adapterView
             * @param view
             * @param i
             * @param l
             */
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                donut.setTypeOfDonut(spn_Type.getSelectedItem().toString());
                subtotalUpdate();
            }

            /**
             * Inner method of setEventListner_Spinner(). Define function/action when nothing is selected in TYPE spinner (Otherwise)
             * @param adapterView
             */
            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {
                //No Action
            }
        });
        spn_Qty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            /**
             * Inner method of setEventListner_Spinner(). Define functions/actions depending on selected QTY in spinner by user.
             * @param adapterView
             * @param view
             * @param i
             * @param l
             */
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                donut.setNumOfItems(Integer.parseInt(spn_Qty.getSelectedItem().toString()));
                subtotalUpdate();
            }

            /**
             * Inner method of setEventListner_Spinner(). Define function/action when nothing is selected in QTY spinner (Otherwise)
             * @param adapterView
             */
            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {
                //no action
            }
        });
    }

    /**
     * Event Listener for donut_choose_flavor button for Donuts.
     * Behave same as clicking "back button" of android OS.
     */
    public void setListner_BackBtn()
    {
        bt_donut_choose_flavor.setOnClickListener(new View.OnClickListener()
        {
            /**
             * Inner method of setListner_BackBtn(). Define function/action of donut_choose_flavor button as "back button"
             * @param view
             */
            @Override
            public void onClick(View view)
            {
                DonutActivity.super.onBackPressed();
            }
        });
    }

    /**
     * Live update of subtotal while Ordering Donuts
     */
    public void subtotalUpdate()
    {
        DecimalFormat df = new DecimalFormat("0.00");
        Double subTotal = donut.itemPrice();
        tv_donut_subTotal.setText(df.format(subTotal));
    }


    /**
     * This method adds donut to Your Order
     * Pops AlertDialog for confirming the order
     * @param view
     */
    public void addDonut(View view)
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage("Do you want to add\n" + donut.toString() + "\n into your current Order?").setTitle("Add Coffee");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener()
        {
            /**
             * Inner method of alert.setNegativeButton, Define function/action when positive button is clicked.
             * @param dialog
             * @param which
             */
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                currentOrder.add(donut);
                showToastAdded();
                //onDestroy();
            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener()
        {
            /**
             * Inner method of alert.setNegativeButton Define function/action when negative button is clicked.
             * @param dialog
             * @param which
             */
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showToastNOTAdded();
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }

    /**
     * Toast to show that the donut was successfully added to Your Order
     */
    private void showToastAdded()
    {
        Toast.makeText(this, "Donut Added!", Toast.LENGTH_SHORT).show();
    }

    /**
     * Toast to show that the donut was not added to Your Order
     */
    private void showToastNOTAdded()
    {
        Toast.makeText(this, "Donut is not added", Toast.LENGTH_SHORT).show();
    }
}