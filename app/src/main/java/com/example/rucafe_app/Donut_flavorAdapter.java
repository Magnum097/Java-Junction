package com.example.rucafe_app;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Adapter class for Donut flavor.
 * @author Amogh Sarangdhar, Minesok Park.
 */
public class Donut_flavorAdapter extends RecyclerView.Adapter<Donut_flavorAdapter.FlavorHolder>
{
    private Context context;
    private ArrayList<Donut_flavor> flavorList;

    /**
     * Constructor for Donut Flavor Adapter class
     * @param context context for flavor
     * @param donutlist Arraylist of Donuts
     */
    public Donut_flavorAdapter(Context context, ArrayList<Donut_flavor> donutlist) //Donut_flavor selectedFlavor
    {
        this.context = context;
        this.flavorList = donutlist;
    }

    /**
     * Creates a view holder for Donut flavors
     * @param parent parent ViewGroup of flavors
     * @param viewType type of flavor
     * @return FlavorHolder, newly created View(Flavor)Holder object.
     */
    @NonNull
    @Override
    public FlavorHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_donut, parent, false);
        return new FlavorHolder(view);
    }


    /**
     * BindView Holder for Donut Flavors.
     * @param holder holder for Donut Flavors.
     * @param position position of the flavor.
     */
    @Override
    public void onBindViewHolder(@NonNull FlavorHolder holder, int position)
    {
        holder.tv_item_flavor_name.setText(flavorList.get(position).toPlainString());
    }


    /**
     * Getter for Item Count of Donuts
     * @return int - size of flavorList
     */
    @Override
    public int getItemCount() {
        return flavorList.size();
    }

    /**
     * Innerclass; define "FlavorHolder" which is ViewHolder for this RecyclerView.
     */
    public static class FlavorHolder extends RecyclerView.ViewHolder
    {
        private TextView tv_item_flavor_name;
        private Button bt_item_flavor_set;
        private ConstraintLayout parentLayout;

        /**
         * Constructor for FlavorHolder class
         * @param itemView single row for Recycler View of Flavors
         */
        public FlavorHolder(@NonNull View itemView)
        {
            super(itemView);
            tv_item_flavor_name = itemView.findViewById(R.id.tv_item_flavor_name);
            bt_item_flavor_set = itemView.findViewById(R.id.bt_item_flavor_set);
            parentLayout = itemView.findViewById(R.id.rowLayout_item_Donut);
            setAddButtonOnClick(itemView);

        }

        /**
         * Adding Button to add Flavor to the Donut
         * @param itemView single row for Recycler View of Flavors
         */
        private void setAddButtonOnClick(@NonNull View itemView)
        {
            bt_item_flavor_set.setOnClickListener(new View.OnClickListener()
            {
                /**
                 * Inner method of setAddButtonOnClick; Define function when user click one of the View(Flavor)Holder.
                 * @param view
                 */
                @Override
                public void onClick(View view)
                {
                    AlertDialog.Builder alert = new AlertDialog.Builder(itemView.getContext());
                    alert.setTitle("Choose flavor");
                    alert.setMessage("Do you want to choose \n" + tv_item_flavor_name.getText().toString() + " as the flavor?");

                    alert.setPositiveButton("yes", new DialogInterface.OnClickListener()
                    {
                        /**
                         * Inner method of OnClick of setAddButtonOnClick; Define action when user click positive button
                         * @param dialog
                         * @param which
                         */
                        public void onClick(DialogInterface dialog, int which)
                        {
                            Toast.makeText(itemView.getContext(),
                                    tv_item_flavor_name.getText().toString() + " is chosen", Toast.LENGTH_SHORT
                            ).show();
                            Intent intent = new Intent(itemView.getContext(), DonutActivity.class);
                            intent.putExtra("FLAVOR", tv_item_flavor_name.getText().toString());
                            itemView.getContext().startActivity(intent);
                        }

                    }).setNegativeButton("no", new DialogInterface.OnClickListener()
                    {
                        /**
                         * Inner method of OnClick of setAddButtonOnClick; Define action when user click negative button
                         * @param dialog
                         * @param which
                         */
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(itemView.getContext(),
                                    tv_item_flavor_name.getText().toString() + " is not chosen.", Toast.LENGTH_SHORT).show();
                        }
                    });
                    AlertDialog dialog = alert.create();
                    dialog.show();
                }
            });
        }
    }


}
