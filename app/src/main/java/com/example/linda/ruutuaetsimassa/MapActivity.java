package com.example.linda.ruutuaetsimassa;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import com.example.linda.ruutuaetsimassa.Entities.Charger;
import com.example.linda.ruutuaetsimassa.Entities.PoleType;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.HashMap;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, InfoFragment.onInfoItemPressed{

    private GoogleMap mMap;
    SlidingUpPanelLayout slideLO;
    private HashMap<Marker, Charger> markerChargerMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        markerChargerMap = new HashMap<Marker, Charger>();

        sliderInit();
    }

    public void sliderInit() {
        slideLO = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        slideLO.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng otaniemi = new LatLng(60.184310, 24.829612);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(otaniemi, 13));

        Marker ota = mMap.addMarker(new MarkerOptions()
                .position(otaniemi));
        Charger otaCharger = new Charger("Otaniemi charger",
                "A charger in the middle of Otaniemi, free to use. It's located by Otaniemi mall " +
                        "behind Apteekki.", "Otaniementie 18",
                true, 18.0, PoleType.TESLA, otaniemi);

        markerChargerMap.put(ota, otaCharger);

        // Zoom in, animating the camera.
        mMap.animateCamera(CameraUpdateFactory.zoomIn());
        // Zoom out to zoom level 10, animating with a duration of 2 seconds.
        mMap.animateCamera(CameraUpdateFactory.zoomTo(13), 2000, null);

        mMap.setOnMarkerClickListener(this);
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {
        showMarkerInfo(marker);
        return false;
    }

    public void showMarkerInfo(Marker marker) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction trans = manager.beginTransaction();

        slideLO.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);

        InfoFragment infoFrag =
                InfoFragment.newInstance(markerChargerMap.get(marker));
        trans.add(R.id.info_frag, infoFrag, "landmarkInfo");
        trans.addToBackStack(null);
        trans.commit();
    }

    @Override
    public void onBackPressed() {
        slideLO.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
        super.onBackPressed();
    }
}
