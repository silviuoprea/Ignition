package com.example.ignition;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ignition.databinding.FragmentResultsBinding;

public class Results extends Fragment {

    public Results() {}
    private FragmentResultsBinding binding;
    SecondFragment Previous = new SecondFragment();

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

            binding = FragmentResultsBinding.inflate(inflater, container, false);
            return binding.getRoot();

        }

        public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            String messageToShow = "Fuel consumed is:" + Previous.distanceTravelled + " Distance travelled is:" + Previous.fuel;
            TextView display = (TextView) requireView().findViewById(R.id.textview_first);
            display.setText(messageToShow);

            binding.returnToStart.setOnClickListener(view1 -> NavHostFragment.findNavController(Results.this)
                    .navigate(R.id.action_Results_to_FirstFragment));
        }
}