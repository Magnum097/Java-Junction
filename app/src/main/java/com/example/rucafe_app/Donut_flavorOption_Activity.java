package com.example.rucafe_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * This class controls Donut Flavor option Activity
 * @author Amogh Sarangdhar, Minseok Park
 */
public class Donut_flavorOption_Activity extends AppCompatActivity
{
    private ArrayList<Donut_flavor> flavors_AL = new ArrayList<Donut_flavor>();

    Intent intent;

    /**
     * Used to Initialize the Donut Flavor option Activity
     * @param savedInstanceState Bundle object containing the activity's previously saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donut_flavor_choose);
        RecyclerView rc_flavors = findViewById(R.id.rcv_donut_flavor_options);
        setupMenuItems();

        Donut_flavorAdapter adapter = new Donut_flavorAdapter(this, flavors_AL);
        rc_flavors.setAdapter(adapter);
        rc_flavors.setLayoutManager(new LinearLayoutManager(this));
        Toast.makeText(this, "Choose Flavor!", Toast.LENGTH_LONG).show();
    }

    /**
     * Setting up Donut Flavors into the Recycler View
     */
    private void setupMenuItems()
    {
        flavors_AL.add(Donut_flavor.PLAIN);
        flavors_AL.add(Donut_flavor.GLAZE);
        flavors_AL.add(Donut_flavor.CHOCOLATE);
        flavors_AL.add(Donut_flavor.STRAWBERRY);
        flavors_AL.add(Donut_flavor.BOSTON);
        flavors_AL.add(Donut_flavor.APPLE_FRITTER);
        flavors_AL.add(Donut_flavor.BANANA);
        flavors_AL.add(Donut_flavor.POWDER_SUGAR);
        flavors_AL.add(Donut_flavor.MAPLE);
        flavors_AL.add(Donut_flavor.ECLAIR);
        flavors_AL.add(Donut_flavor.SCARLET_SPECIAL);
        flavors_AL.add(Donut_flavor.BUSCH_GOOSE);
    }
}