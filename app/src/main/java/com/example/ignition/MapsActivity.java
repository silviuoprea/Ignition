package com.example.ignition;

import static com.google.android.gms.maps.model.JointType.ROUND;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.fragment.NavHostFragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ignition.databinding.ActivityMapsBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.SquareCap;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends Fragment implements OnMapReadyCallback {

    private static final String TAG = MapsActivity.class.getSimpleName();
    SupportMapFragment mapFragment;
    private GoogleMap mMap;
    private List<LatLng> polyLineList;
    private Marker marker;
    private float v;
    private double lat, lng;
    private double latitude, longitude;
    private Handler handler;
    private LatLng startPosition, endPosition;
    private int index, next;
    private LatLng home;
    private EditText destinationEditText;
    private String destination;
    private PolylineOptions polylineOptions, blackPolylineOptions;
    private Polyline blackPolyline, greyPolyLine;
    private double speed = 0;
    private long speedLong = 0;
    private int multiplier = 190;
    private TextView speedometerText;
    private TextView fuelConsumed;
    private ProgressBar speedometer;
    private double fuelConsumption = 0.0;
    private double fromFrag1;
    private double toSend;
    private String duration;
    private String distance;
    FusedLocationProviderClient client;

    ImageView powerButton;ImageView accelPedal;ImageView breakPedal;
    private ActivityMapsBinding binding;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = ActivityMapsBinding.inflate(inflater, container, false);
        getParentFragmentManager().setFragmentResultListener("key", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                fromFrag1 = result.getDouble("data");
            }
        });
        client = LocationServices.getFusedLocationProviderClient(getActivity());

        getCurrentLocation();
        return binding.getRoot();
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onViewCreated(@NonNull View v, Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);

        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        polyLineList = new ArrayList<>();
        powerButton = v.findViewById(R.id.destination_button);
        destinationEditText = v.findViewById(R.id.edittext_place);
        accelPedal = v.findViewById(R.id.accelpedal);
        breakPedal = v.findViewById(R.id.breakpedal);
        speedometer = v.findViewById(R.id.speedometer);
        speedometerText = v.findViewById(R.id.speedometerText);
        fuelConsumed = v.findViewById(R.id.fuelConsumed);


        powerButton.setOnClickListener(view -> {
            destination = destinationEditText.getText().toString();
            destination = destination.replace(" ", "+");
            Log.d(TAG, destination);
            mapFragment.getMapAsync(this);
        });

        accelPedal.setOnClickListener(view -> {
            updateSpeed(true);
        });
        accelPedal.setOnLongClickListener(view -> {
            while(true){
                updateSpeed(true);}
        });
        breakPedal.setOnClickListener(view -> {
            updateSpeed(false);
        });

        speedometer.setProgress(200-multiplier);

        speedometerText.setText(200-multiplier + "km/h");

        fuelConsumed.setText(fuelConsumption + "l");
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    /**
     * Updates the speed
     * If accelOrBreak = true then accelerate
     * else break
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void updateSpeed(boolean accelOrBreak) {
        if (accelOrBreak)
            if(multiplier > 0 && multiplier <201)
            {multiplier -=10;
                speedometerText.setText(200-multiplier + "km/h");
                speedometer.setProgress((200-multiplier)/2);
            }
            else multiplier = 0;
        else multiplier = 30_000_000;
    }


    private void setFuelConsumption()
    {
        Log.v(TAG, "str state =" + fromFrag1);
        // display the string into textView
        fuelConsumption += fromFrag1;
    }
    @SuppressLint("MissingPermission")
    private void getCurrentLocation()
    {
        // Initialize Location manager
        LocationManager locationManager
                = (LocationManager)getActivity()
                .getSystemService(
                        Context.LOCATION_SERVICE);

        Log.d(TAG, " GPS ACTIVE : " + locationManager.isProviderEnabled(
                LocationManager.GPS_PROVIDER) + "");
        Log.d(TAG, " NETWORK ACTIVE : " + locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER) + "");
        // Check condition
        if (locationManager.isProviderEnabled(
                LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER)) {
            // When location service is enabled
            // Get last location
            client.getLastLocation().addOnCompleteListener(
                    task -> {
                        // Initialize location
                        Location location
                                = task.getResult();
                        Log.d(TAG, " location is  : " + location + "");
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                        Log.d(TAG, " Latitudinea e : " + latitude + "");
                        Log.d(TAG, " longitudea e : " + longitude + "");
                    });
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Iasi,Romania.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG, " Latitudinea e : " + latitude + "");
        Log.d(TAG, " longitudea e : " + longitude + "");

        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.setTrafficEnabled(true);
        mMap.setIndoorEnabled(false);
        mMap.setBuildingsEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(false);
        // Add a marker in Home and move the camera
        home = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(home).title("Marker in Home"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(home));
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder()
                .target(googleMap.getCameraPosition().target)
                .zoom(18)
                .bearing(30)
                .tilt(45)
                .build()));
        String requestUrl;
        try {
            requestUrl = "https://maps.googleapis.com/maps/api/directions/json?" +
                    "mode=driving&"
                    + "transit_routing_preference=less_driving&"
                    + "origin=" + home.latitude + "," + home.longitude + "&"
                    + "destination=" + destination + "&"
                    + "key=" + getResources().getString(R.string.map_key);
            Log.d(TAG, requestUrl);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                    requestUrl, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d(TAG, response + "");
                            try {
                                JSONArray jsonArray = response.getJSONArray("routes");
                                distance = jsonArray.getJSONObject(0).getJSONArray("legs").getJSONObject(0).getJSONObject("distance").getString("text");
                                duration = jsonArray.getJSONObject(0).getJSONArray("legs").getJSONObject(0).getJSONObject("duration").getString("text");


                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject route = jsonArray.getJSONObject(i);
                                    JSONObject poly = route.getJSONObject("overview_polyline");
                                    String polyline = poly.getString("points");
                                    polyLineList = decodePoly(polyline);
                                    Log.d(TAG, polyLineList + "");
                                }
                                //Adjusting bounds
                                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                                for (LatLng latLng : polyLineList) {
                                    builder.include(latLng);
                                }
                                LatLngBounds bounds = builder.build();
                                CameraUpdate mCameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 20);
                                mMap.animateCamera(mCameraUpdate);

                                polylineOptions = new PolylineOptions();
                                polylineOptions.color(Color.GRAY);
                                polylineOptions.width(5);
                                polylineOptions.startCap(new SquareCap());
                                polylineOptions.endCap(new SquareCap());
                                polylineOptions.jointType(ROUND);
                                polylineOptions.addAll(polyLineList);
                                greyPolyLine = mMap.addPolyline(polylineOptions);

                                blackPolylineOptions = new PolylineOptions();
                                blackPolylineOptions.width(10);
                                blackPolylineOptions.color(Color.CYAN);
                                blackPolylineOptions.startCap(new SquareCap());
                                blackPolylineOptions.endCap(new SquareCap());
                                blackPolylineOptions.jointType(ROUND);
                                blackPolyline = mMap.addPolyline(blackPolylineOptions);

                                mMap.addMarker(new MarkerOptions()
                                        .position(polyLineList.get(polyLineList.size() - 1)));

                                ValueAnimator polylineAnimator = ValueAnimator.ofInt(0, 100);
                                polylineAnimator.setDuration(3000);
                                polylineAnimator.setInterpolator(new LinearInterpolator());
                                polylineAnimator.addUpdateListener(valueAnimator -> {
                                    List<LatLng> points = greyPolyLine.getPoints();
                                    int percentValue = (int) valueAnimator.getAnimatedValue();
                                    int size = points.size();
                                    int newPoints = (int) (size * (percentValue / 100.0f));
                                    List<LatLng> p = points.subList(0, newPoints);
                                    blackPolyline.setPoints(p);
                                });
                                polylineAnimator.start();
                                marker = mMap.addMarker(new MarkerOptions().position(home)
                                        .flat(true)
                                        .icon(BitmapDescriptorFactory.fromResource(R.raw.car)));
                                handler = new Handler();
                                index = -1;
                                next = 1;
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Log.v(TAG, "Checker before =" + toSend);
                                        toSend = fuelConsumption;
                                        Log.v(TAG, "Checker after =" + toSend);
                                        if (index < polyLineList.size() - 1) {
                                            index++;
                                            next = index + 1;
                                        }
                                        else
                                        {
                                            moveOn();
                                            return;
                                        }
                                        if (index < polyLineList.size() - 1) {
                                            startPosition = polyLineList.get(index);
                                            endPosition = polyLineList.get(next);
                                        }
                                        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
                                        speed = (Math.abs(endPosition.longitude - startPosition.longitude)) + (Math.abs(endPosition.latitude - startPosition.latitude));
                                        fuelConsumption += (speed + multiplier) / 10_000;
                                        fuelConsumption = Math.round(fuelConsumption * 100.00) /100.00;
                                        Log.v(TAG, "Before =" + fuelConsumption);
                                        setFuelConsumption();
                                        Log.v(TAG, "After =" + fuelConsumption);
                                        fuelConsumed.setText(fuelConsumption + "l");
                                        speedLong = (int) (speed * 30_000)*multiplier;
                                        valueAnimator.setDuration(speedLong);
                                        Log.v(TAG, "Result =" + speedLong + "Multiplier = " + multiplier);
                                        valueAnimator.setInterpolator(new LinearInterpolator());
                                        valueAnimator.addUpdateListener(valueAnimator1 -> {
                                            v = valueAnimator1.getAnimatedFraction();
                                            lng = v * endPosition.longitude + (1 - v)
                                                    * startPosition.longitude;
                                            lat = v * endPosition.latitude + (1 - v)
                                                    * startPosition.latitude;
                                            LatLng newPos = new LatLng(lat, lng);
                                            marker.setPosition(newPos);
                                            marker.setAnchor(0.5f, 0.5f);
                                            marker.setRotation(getBearing(startPosition, newPos));
                                            mMap.moveCamera(CameraUpdateFactory
                                                    .newCameraPosition
                                                            (new CameraPosition.Builder()
                                                                    .target(newPos)
                                                                    .zoom(18f)
                                                                    .build()));
                                        });
                                        valueAnimator.start();
                                        handler.postDelayed(this, speedLong); // corner delay
                                    }
                                }, 3000); // start delay


                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }, error -> Log.d(TAG, error + ""));
            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            requestQueue.add(jsonObjectRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * https://mathematica.stackexchange.com/questions/11521/using-j-link-to-decode-google-maps-polyline-strings
     * Decodes polylines
     */
    private List<LatLng> decodePoly(String encoded) {
        List<LatLng> poly = new ArrayList<>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }

        return poly;
    }

    /**
     * Calculates degrees for orientation
     */
    private float getBearing(LatLng begin, LatLng end) {
        double lat = Math.abs(begin.latitude - end.latitude);
        double lng = Math.abs(begin.longitude - end.longitude);

        final double angle = Math.toDegrees(Math.atan(lng / lat));
        if (begin.latitude < end.latitude && begin.longitude < end.longitude)
            return (float) angle;
        else if (begin.latitude >= end.latitude && begin.longitude < end.longitude)
            return (float) ((90 - angle) + 90);
        else if (begin.latitude >= end.latitude && begin.longitude >= end.longitude)
            return (float) (angle + 180);
        else if (begin.latitude < end.latitude && begin.longitude >= end.longitude)
            return (float) ((90 - angle) + 270);
        return -1;
    }

    private void moveOn()
    {
        Bundle result2 = new Bundle();
        Bundle result3 = new Bundle();
        Bundle result4 = new Bundle();
        result2.putDouble("final", toSend);
        result3.putString("final", duration);
        result4.putString("final", distance);
        getParentFragmentManager().setFragmentResult("key2", result2);
        getParentFragmentManager().setFragmentResult("key3", result3);
        getParentFragmentManager().setFragmentResult("key4", result4);
        NavHostFragment.findNavController(MapsActivity.this)
                .navigate(R.id.action_mapsActivity_to_results);
    }
}
