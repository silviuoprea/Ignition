package com.example.ignition;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.ignition.databinding.FragmentFirstBinding;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    TestDrive testDrive = new TestDrive();
    Button buttonFirst;
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    /****** Create Thread that will sleep for 1 second****/
    Thread background = new Thread() {
        public void run() {
            try {
                // Thread will sleep for 1 second
                sleep(2*500);
                NavHostFragment.findNavController(FirstFragment.this).navigate(R.id.action_FirstFragment_to_passagersSelection);

                // After 1 second redirect to another intent

            } catch (Exception e) {
            }
        }
    };
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        testDrive.resetData();
        // start thread
        background.start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}