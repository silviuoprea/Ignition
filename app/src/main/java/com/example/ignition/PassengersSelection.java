package com.example.ignition;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.ignition.databinding.FragmentPassagersSelectionBinding;

public class PassengersSelection extends Fragment {
    private FragmentPassagersSelectionBinding binding;
    Button submit, clear;
    RadioGroup radioGroup;
    double parameterFuel = 0.0;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch wornOutTires, ecoMode, lowTirePressure, slipperyRoad, bootFull, secondaryStorage;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPassagersSelectionBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        submit = (Button)view.findViewById(R.id.submit);
        clear = (Button)view.findViewById(R.id.clear);
        radioGroup = (RadioGroup) view.findViewById(R.id.groupradio);

        wornOutTires = (Switch)view.findViewById(R.id.WornOutTires);
        ecoMode = (Switch)view.findViewById(R.id.EcoMode);
        lowTirePressure = (Switch)view.findViewById(R.id.LowTirePressure);
        slipperyRoad = (Switch)view.findViewById(R.id.SlipperyRoad);
        bootFull = (Switch)view.findViewById(R.id.BootFull);
        secondaryStorage = (Switch)view.findViewById(R.id.SecondaryStorage);


        wornOutTires.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked)
                // The toggle is enabled
                parameterFuel += 0.1;
        });

        ecoMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked)
                // The toggle is enabled
                parameterFuel += 0.1;
        });

        lowTirePressure.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked)
                // The toggle is enabled
                parameterFuel += 0.1;
        });

        slipperyRoad.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked)
                // The toggle is enabled
                parameterFuel += 0.1;
        });

        bootFull.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked)
                // The toggle is enabled
                parameterFuel += 0.1;
        });

        secondaryStorage.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked)
                // The toggle is enabled
                parameterFuel += 0.1;
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @SuppressLint("NonConstantResourceId")
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected

                switch(checkedId) {
                    case R.id.Passanger1:
                        parameterFuel += 0.1;
                        break;
                    case R.id.Passanger2:
                        parameterFuel += 0.2;
                        break;
                    case R.id.Passanger3:
                        parameterFuel += 0.3;
                        break;
                    case R.id.Passanger4:
                        parameterFuel += 0.4;
                        break;
                    case R.id.Passanger5:
                        parameterFuel += 0.5;
                        break;
                }
            }
        });
        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle result = new Bundle();
                result.putDouble("data", parameterFuel);
                getParentFragmentManager().setFragmentResult("key", result);
                NavHostFragment.findNavController(PassengersSelection.this)
                        .navigate(R.id.action_passagersSelection_to_mapsActivity);
            }
        });
        binding.clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radioGroup.clearCheck();
                wornOutTires.setChecked(false);
                lowTirePressure.setChecked(false);
                slipperyRoad.setChecked(false);
                ecoMode.setChecked(false);
                bootFull.setChecked(false);
                secondaryStorage.setChecked(false);
            }
        });
    }
}