package com.upc.movilmarket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Maps extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private DatabaseReference mDatabase;
    private ArrayList<Marker> tmpRealTimeMarker = new ArrayList<>();
    private ArrayList<Marker> RealTimeMarker = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapa);
        mapFragment.getMapAsync(this);
        mDatabase= FirebaseDatabase.getInstance().getReference();

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);

       LatLng tienda1 = new LatLng(-12.184321, -77.005778);
        mMap.addMarker(new MarkerOptions().position(tienda1).title("TIENDA CHORRILLOS"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(tienda1,17));
        mDatabase.child("Direcciones").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (Marker marker:RealTimeMarker){

                    marker.remove();
                }
                for (DataSnapshot snapshot1: snapshot.getChildren()){
                    MapFirebase mp = snapshot1.getValue(MapFirebase.class);
                    Double latitud = mp.getLatitud();
                    Double longitud = mp.getLongitud();
                    //mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
                    MarkerOptions markerOptions =new MarkerOptions();
                    markerOptions.position(new LatLng(latitud,longitud));
                    tmpRealTimeMarker.add(mMap.addMarker(markerOptions));

                }
                RealTimeMarker.clear();
                RealTimeMarker.addAll(tmpRealTimeMarker);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}