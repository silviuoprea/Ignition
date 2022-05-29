package com.example.ignition;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DataRecycleView extends RecyclerView.Adapter<DataRecycleView.ViewHolder> {

    // variable for our array list and context
    private ArrayList<DataModel> dataModelArrayList;
    private Context context;

    // constructor
    public DataRecycleView(ArrayList<DataModel> dataModelArrayList, Context context) {
        this.dataModelArrayList = dataModelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // on below line we are inflating our layout
        // file for our recycler view items.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_rv, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // on below line we are setting data
        // to our views of recycler view item.
        DataModel modal = dataModelArrayList.get(position);
        holder.fuelConsumptionTV.setText(modal.getFuelConsumption());
    }

    @Override
    public int getItemCount() {
        // returning the size of our array list
        return dataModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // creating variables for our text views.
        private TextView fuelConsumptionTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our text views
            fuelConsumptionTV = itemView.findViewById(R.id.idFuelConsumed);
        }
    }
}