package com.example.ignition;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.ignition.databinding.FragmentSecondBinding;

public class SecondFragment extends Fragment {
    private FragmentSecondBinding binding;
    TestDrive testDrive = new TestDrive();
    int accelerateTrigger = 1;
    double distanceTravelled = 0;
    double fuel = 0;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.progressBar.setMax(testDrive.finalDistance);

        binding.startButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Handler handler =new Handler();
                final Runnable r = new Runnable() {
                    public void run() {
                        String distanceMessage = "Distance travelled is " + testDrive.currentDistance;
                        String currentSpeed = "Current speed is " + testDrive.speed;
                        String tripProgress = "Trip progress: " + testDrive.tripProgress + " %";
                        String fuelConsumption = "Fuel consumed: " + testDrive.fuelConsumption + " l";
                        TextView currentDistance = (TextView) requireView().findViewById(R.id.textView);
                        TextView speedometer = (TextView) requireView().findViewById(R.id.textView2);
                        TextView tripPercentage = (TextView) requireView().findViewById(R.id.textView6);
                        TextView fuelConsume = (TextView) requireView().findViewById(R.id.textView7);
                        handler.postDelayed(this, 1000);
                        binding.progressBar.setProgress(testDrive.currentDistance);
                        currentDistance.setText(distanceMessage);
                        speedometer.setText(currentSpeed);
                        tripPercentage.setText(tripProgress);
                        fuelConsume.setText(fuelConsumption);
                        if (testDrive.currentDistance >= testDrive.finalDistance)
                        {
                            distanceTravelled = testDrive.currentDistance;
                            fuel = testDrive.fuelConsumption;
                        }
                    }
                };
                handler.postDelayed(r, 0);
            }

        })
        ;
        @SuppressLint("UseSwitchCompatOrMaterialCode")
        Switch airConditioning = (Switch) view.findViewById(R.id.acSwitch);
        airConditioning.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // The toggle is enabled
                testDrive.fuelConsumptionRate += 0.1;
            } else {
                // The toggle is disabled
                testDrive.fuelConsumptionRate -= 0.1;
            }
        });
        @SuppressLint("UseSwitchCompatOrMaterialCode")
        Switch wornOutTiresSwitch = (Switch) view.findViewById(R.id.wotSwitch);
        wornOutTiresSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // The toggle is enabled
                testDrive.fuelConsumptionRate += 0.1;
            } else {
                // The toggle is disabled
                testDrive.fuelConsumptionRate -= 0.1;
            }
        });
        @SuppressLint("UseSwitchCompatOrMaterialCode")
        Switch wetRoadSwitch = (Switch) view.findViewById(R.id.wrSwitch);
        wetRoadSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // The toggle is enabled
                testDrive.fuelConsumptionRate += 0.1;
            } else {
                // The toggle is disabled
                testDrive.fuelConsumptionRate -= 0.1;
            }
        });
        @SuppressLint("UseSwitchCompatOrMaterialCode")
        Switch frontWindSwitch = (Switch) view.findViewById(R.id.fwSwitch);
        frontWindSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // The toggle is enabled
                testDrive.fuelConsumptionRate += 0.1;
            } else {
                // The toggle is disabled
                testDrive.fuelConsumptionRate -= 0.1;
            }
        });
        binding.acceleratebutton.setOnClickListener(view1 -> {

            Handler handler =new Handler();
            final Runnable r = () -> {
                if (accelerateTrigger == 1)
                testDrive.accelerate();
            };
            handler.postDelayed(r, 0);
        })
        ;
        ToggleButton acceleratorBlocker = (ToggleButton) view.findViewById(R.id.toggleButton);
        acceleratorBlocker.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // The toggle is enabled
                accelerateTrigger = 0;
            } else {
                // The toggle is disabled
                accelerateTrigger = 1;
            }
        });
    }
}