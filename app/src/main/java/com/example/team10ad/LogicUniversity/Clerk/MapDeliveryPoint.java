package com.example.team10ad.LogicUniversity.Clerk;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.team10ad.LogicUniversity.Model.CollectionPoint;
import com.example.team10ad.LogicUniversity.Service.CollectionPointService;
import com.example.team10ad.LogicUniversity.Service.ServiceGenerator.ServiceGenerator;
import com.example.team10ad.LogicUniversity.Util.Constants;
import com.example.team10ad.LogicUniversity.Util.MyApp;
import com.example.team10ad.LogicUniversity.Util.NestedScrollableViewHelper;
import com.example.team10ad.team10ad.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapDeliveryPoint extends Fragment implements OnMapReadyCallback {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    //map
    private GoogleMap mMap;
    private boolean mLocationPermissionGranted = false;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private Marker marker;
    private MapView mapFragment;

    //Collection Point List
    List<CollectionPoint> result;

    public MapDeliveryPoint() {
    }

    public static MapDeliveryPoint newInstance(String param1, String param2) {
        MapDeliveryPoint fragment = new MapDeliveryPoint();
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
        String token = Constants.BEARER + MyApp.getInstance().getPreferenceManager().getString(Constants.KEY_ACCESS_TOKEN);

        mapFragment = (MapView) view.findViewById(R.id.map);
        mapFragment.onCreate(savedInstanceState);
        mapFragment.onResume();
        getLocationPermission();
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
            mapFragment.getMapAsync(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        slidingViewCreate(view, token);

        return view;
    }

    private void getLocationPermission() {
        String[] permission = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
        if (ContextCompat.checkSelfPermission(this.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionGranted = true;
            } else {
                ActivityCompat.requestPermissions(getActivity(), permission, LOCATION_PERMISSION_REQUEST_CODE);
            }
        }
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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng pos = new LatLng(1.296879, 103.776332);
        moveCameraTo(pos, 15);

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker mark) {
                CollectionPoint obj = (CollectionPoint) mark.getTag();
                mark.setTitle(obj.getCpName());
                mark.setSnippet(obj.getCpLocation());
                mark.showInfoWindow();
                return false;
            }
        });

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker mark) {
                CollectionPoint cp = (CollectionPoint) mark.getTag();
                collectionPointDelivery(cp);
            }
        });
    }

    private void markerAdd(CollectionPoint cp) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
        markerOptions.position(new LatLng(Double.parseDouble(cp.getLatitude()),
                Double.parseDouble(cp.getLongitude())));
        Marker m = mMap.addMarker(markerOptions);
        m.setTag(cp);
    }

    private void moveCameraTo(LatLng location, int zoom) {
        CameraPosition cp = new CameraPosition.Builder().target(location).zoom(zoom).build();
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cp));
    }

    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }

    private void slidingViewCreate(View viewParam, String tokenParam) {
        final SlidingUpPanelLayout slidingUpPanelLayout =
                (SlidingUpPanelLayout) viewParam.findViewById(R.id.sliding_layout);
        final ListView slideView = viewParam.findViewById(R.id.sliding_cplistView);
        CollectionPointService cpServie = ServiceGenerator.createService(CollectionPointService.class, tokenParam);
        Call<List<CollectionPoint>> call = cpServie.getCollectionPoints();
        call.enqueue(new Callback<List<CollectionPoint>>() {
            @Override
            public void onResponse(Call<List<CollectionPoint>> call, Response<List<CollectionPoint>> response) {
                if (response.isSuccessful()) {
                    result = response.body();
                    final ArrayList<String> arrayList = new ArrayList<>();
                    for (CollectionPoint cp : result) {
                        arrayList.add(cp.getCpName());
                        markerAdd(cp);
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, arrayList);
                    slideView.setAdapter(adapter);
                    slideView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            CollectionPoint cp = result.get(i);
                            slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                            collectionPointDelivery(cp);
                        }
                    });
                } else {
                    Toast.makeText(MyApp.getInstance(), Constants.REQ_NO_SUCCESS, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<CollectionPoint>> call, Throwable t) {
                Toast.makeText(getContext(), "CONNECTION ERROR !", Toast.LENGTH_SHORT).show();
            }
        });

        NestedScrollableViewHelper helper = new NestedScrollableViewHelper();
        slidingUpPanelLayout.setScrollableViewHelper(helper);
    }

    private void collectionPointDelivery(CollectionPoint cp) {
        RequisitionList disbListFrag = new RequisitionList();
        Bundle b = new Bundle();
        b.putInt("CpId", cp.getCpId());
        b.putString("Cp", cp.getCpName());
        disbListFrag.setArguments(b);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, disbListFrag);
        ft.addToBackStack(null);
        ft.commit();
    }
}
