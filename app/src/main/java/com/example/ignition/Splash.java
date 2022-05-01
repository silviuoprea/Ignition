package com.example.ignition;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.VideoView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Splash} factory method to
 * create an instance of this fragment.
 */
public class Splash extends Fragment {

    public Splash() {
        // Required empty public constructor
    }

    /****** Create Thread that will sleep for 5 seconds****/
    Thread background = new Thread() {
        public void run() {
            try {
                // Thread will sleep for 5 seconds
                sleep(5*500);
                NavHostFragment.findNavController(Splash.this).navigate(R.id.action_Splash_to_mapsActivity);

                // After 5 seconds redirect to another intent

            } catch (Exception e) {
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // start thread
        background.start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_splash, container, false);

        VideoView view = rootView.findViewById(R.id.videoView);
        String path = "android.resource://" + requireActivity().getPackageName() + "/" + R.raw.ignition;
        view.setVideoURI(Uri.parse(path));
        view.start();

        return rootView;
    }
}