package com.example.team10ad.LogicUniversity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.team10ad.team10ad.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Map;

public class ClerkMapDeliveryPoint extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    //map
    private GoogleMap mMap;
    private boolean mLocationPermissionGranted = false;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private Marker marker, marker1, marker2;
    private MapView mapFragment;

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ClerkMapDeliveryPoint() {

    }

    public static ClerkMapDeliveryPoint newInstance(String param1, String param2) {
        ClerkMapDeliveryPoint fragment = new ClerkMapDeliveryPoint();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_clerk_map_delivery_point, container, false);
        mapFragment = (MapView) view.findViewById(R.id.map);
        mapFragment.onCreate(savedInstanceState);
        mapFragment.onResume();
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        getLocationPermission();
        return view;
    }

    private void getLocationPermission() {
        String[] permission = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
        if (ContextCompat.checkSelfPermission(this.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionGranted = true;
                initMap();
            } else {
                ActivityCompat.requestPermissions(getActivity(), permission, LOCATION_PERMISSION_REQUEST_CODE);
            }
        }
    }

    private void initMap() {

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
                final MarkerOptions myMarker = new MarkerOptions();

                BitmapDrawable bitmapdraw = (BitmapDrawable)getResources()
                        .getDrawable(R.drawable.marker);
                Bitmap b = bitmapdraw.getBitmap();
                Bitmap smallMarker = Bitmap.createScaledBitmap
                                (b, (int) Math.ceil(b.getWidth()*0.11),
                                        (int) Math.ceil(b.getHeight()*0.11),
                                        false);

                myMarker.icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
                LatLng pos = new LatLng(1.3, 103.81);
                LatLng pos1 = new LatLng(1.3, 103.84);
                LatLng pos2 = new LatLng(1.3, 103.87);
                LatLng[] poss = new LatLng[] {pos, pos1, pos2};
                String[] s = new String[] {"s1", "s2", "s3"};
                for(int i=0; i<3; i++){
                    myMarker.position(poss[i]);
                    Marker m = mMap.addMarker(myMarker);
                    m.setTag(s[i]);
                }
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(pos1, 11.0f));

                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker mark) {
                        mark.setTitle(mark.getTag().toString());
                        mark.showInfoWindow();
                        return false;
                    }
                });

                mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker mark) {
                /*Intent intent = new Intent(ClerkMapDeliveryPoint.this, Map2Activity.class);
                intent.putExtra("param", mark.getTag().toString());
                startActivity(intent);*/
                    }
                });
            }
        });
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }

}
