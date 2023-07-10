package com.example.rucafe_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * This class controls Main Activity.
 * Directs the user to the respective ordering screen for Donuts and Coffee.
 * Also, takes the user to Your Order and the manager to Store Order View.
 * @author Amogh Sarangdhar, Minseok Park
 */
public class MainActivity extends AppCompatActivity {

    public static Order currentOrder = new Order();
    public static StoreOrder storeOrder = new StoreOrder();
    public static int num_Orders = 0;

    /**
     * Used to Initialize the Main Activity
     * @param savedInstanceState Bundle object containing the activity's previously saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_main);
    }

    /**
     * Takes the user to Ordering Coffee View.
     * @param view the base class for coffee widgets
     */
    public void toCoffee(View view) {
        Intent intent = new Intent(this, CoffeeActivity.class);
        startActivity(intent);
    }

    /**
     * Takes the user to Ordering Donut View.
     * @param view the base class for donut widgets
     */
    public void toDonut(View view)
    {
        Intent intent = new Intent(this, DonutActivity.class);
        startActivity(intent);
    }

    /**
     * Takes the user to Donut Flavors View.
     * @param view the base class for donut flavor widgets
     */
    public void toDonut_flavor(View view)
    {
        Intent intent = new Intent(this, Donut_flavorOption_Activity.class);
        startActivity(intent);
    }

    /**
     * Takes the user to Your Order View.
     * @param view the base class for your order widgets
     */
    public void toOrder(View view)
    {
        Intent intent = new Intent(this, OrderActivity.class);
        startActivity(intent);
    }

    /**
     * Takes the user to Store Order View.
     * @param view the base class for store order widgets
     */
    public void toStoreOrder(View view)
    {
        Intent intent = new Intent(this, StoreOrderActivity.class);
        startActivity(intent);


    }

}