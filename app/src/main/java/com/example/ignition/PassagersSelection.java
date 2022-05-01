package com.example.ignition;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;

import com.example.ignition.databinding.FragmentPassagersSelectionBinding;

public class PassagersSelection extends Fragment {
    private FragmentPassagersSelectionBinding binding;
    Button submit, clear;
    int passagerNumber = 1;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPassagersSelectionBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        submit = (Button)view.findViewById(R.id.submit);
        clear = (Button)view.findViewById(R.id.clear);
        RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.groupradio);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @SuppressLint("NonConstantResourceId")
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected

                switch(checkedId) {
                    case R.id.Passanger1:
                        passagerNumber = 1;
                        break;
                    case R.id.Passanger2:
                        passagerNumber = 2;
                        break;
                    case R.id.Passanger3:
                        passagerNumber = 3;
                        break;
                    case R.id.Passanger4:
                        passagerNumber = 4;
                        break;
                    case R.id.Passanger5:
                        passagerNumber = 5;
                        break;
                }
            }
        });
        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(PassagersSelection.this)
                        .navigate(R.id.action_passagersSelection_to_SecondFragment);
            }
        });
    }
}