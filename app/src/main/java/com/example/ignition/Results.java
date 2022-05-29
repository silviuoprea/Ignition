package com.example.ignition;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.fragment.NavHostFragment;

import com.example.ignition.databinding.FragmentResultsBinding;

public class Results extends Fragment {
    private static final String TAG = MapsActivity.class.getSimpleName();
    private FragmentResultsBinding binding;
    private double fromMapsActivity = 0.1;
    private String duration;
    private String distance;
    private DBHandler dbHandler;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentResultsBinding.inflate(inflater, container, false);
        getParentFragmentManager().setFragmentResultListener("key2", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                Log.v(TAG, "Checker before2 =" + fromMapsActivity);
                fromMapsActivity = result.getDouble("final");
                Log.v(TAG, "Checker after2 =" + fromMapsActivity);
            }

        });
        getParentFragmentManager().setFragmentResultListener("key3", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                Log.v(TAG, "Checker before2 =" + duration);
                duration = result.getString("final");
                Log.v(TAG, "Checker after2 =" + duration);
            }

        });
        getParentFragmentManager().setFragmentResultListener("key4", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                Log.v(TAG, "Checker before2 =" + distance);
                distance = result.getString("final");
                Log.v(TAG, "Checker after2 =" + distance);
            }

        });
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Handler handler = new Handler();
        final Runnable r = new Runnable() {
            public void run() {
                String messageToShow = "Fuel consumed is: " + fromMapsActivity + "\n" + "Duration is: " + duration + "\n" + "Distance is: " + distance;
                TextView display = (TextView) requireView().findViewById(R.id.textview_first);
                display.setText(messageToShow);
            }
        };
        handler.postDelayed(r, 0);
        dbHandler = new DBHandler(getActivity());

        // below line is to add on click listener for our add course button.
        binding.saveToDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // below line is to get data from all edit text fields.
                String consumption = fromMapsActivity + "";

                // on below line we are calling a method to add new
                // course to sqlite data and pass all our values to it.
                dbHandler.addFC(consumption, duration, distance);

                // after adding the data we are displaying a toast message.
                Toast.makeText(getActivity(), "Consumption has been added.", Toast.LENGTH_SHORT).show();
            }
        });

        binding.viewDatabase.setOnClickListener(view1 -> NavHostFragment.findNavController(Results.this)
                .navigate(R.id.action_results_to_databaseView));
        binding.returnToStart.setOnClickListener(view1 -> NavHostFragment.findNavController(Results.this)
                .navigate(R.id.action_Results_to_FirstFragment));
    }
}