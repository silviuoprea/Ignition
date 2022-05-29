package com.example.ignition;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ignition.databinding.FragmentDatabaseViewBinding;

import java.util.ArrayList;
public class DatabaseView extends Fragment {

        private FragmentDatabaseViewBinding binding;
        private ArrayList<DataModel> dataModelArrayList;
        private DBHandler dbHandler;
        private DataRecycleView dataRVAdapter;
        private RecyclerView dataRV;


    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
        ) {

        binding = FragmentDatabaseViewBinding.inflate(inflater, container, false);
        return binding.getRoot();

        }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // initializing our all variables.
        dataModelArrayList = new ArrayList<>();
        dbHandler = new DBHandler(getContext());

        // getting our course array
        // list from db handler class.
        dataModelArrayList = dbHandler.readFC();

        // on below line passing our array lost to our adapter class.
            dataRVAdapter = new DataRecycleView(dataModelArrayList, getContext());
            dataRV = view.findViewById(R.id.idFuelConsumed);

        // setting layout manager for our recycler view.
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
            dataRV.setLayoutManager(linearLayoutManager);

        // setting our adapter to recycler view.
            dataRV.setAdapter(dataRVAdapter);

        binding.startOver.setOnClickListener(view1 -> NavHostFragment.findNavController(DatabaseView.this)
                .navigate(R.id.action_databaseView_to_FirstFragment));
    }
}