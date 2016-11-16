package com.example.linda.ruutuaetsimassa;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.linda.ruutuaetsimassa.Entities.Charger;
import com.example.linda.ruutuaetsimassa.Entities.PoleType;
import com.google.android.gms.drive.query.Filter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.HashMap;

public class MapActivity extends FragmentActivity
        implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, InfoFragment.onInfoItemPressed,
        FilterFragment.OnFilterActionListener {

    private GoogleMap mMap;
    SlidingUpPanelLayout slideLO;
    private HashMap<Marker, Charger> markerChargerMap;

    //Navigation drawer variables
    public DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;

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
        initDrawer();

    }

    public void sliderInit() {
        slideLO = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        slideLO.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
    }

    /**
     * Set up the navigation drawer
     */

    public void initDrawer() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_lo);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        this.addDrawerItems();

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItem(position);
            }
        });
    }

    /**
     * Determine what happens when the items in the navi drawer are pressed.
     * @param position the position of the item in the list
     */

    public void selectItem(int position) {
        switch (position) {
            case 0:     //Filtering
                FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
                FilterFragment filterFrag =
                        FilterFragment.newInstance();
                trans.add(R.id.filter_fragment, filterFrag, "filterFragment");
                trans.addToBackStack(null);
                trans.commit();

                mDrawerLayout.closeDrawer(GravityCompat.START);
                break;
            case 1:     //Settings
                Toast.makeText(this, "Settings not yet implemented!", Toast.LENGTH_SHORT).show();
                break;
            case 2:     //Own charger
                Toast.makeText(this, "Adding own charger not yet implemented!", Toast.LENGTH_SHORT).show();
                break;
            case 3:     //Sign out
                Toast.makeText(this, "Signing out not yet implemented!", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void addDrawerItems() {
        String[] items = getResources().getStringArray(R.array.drawer_items);
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        mDrawerList.setAdapter(mAdapter);
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

        // This all we should be later able to do in the user setting a new marker
        LatLng otaniemi = new LatLng(60.184310, 24.829612);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(otaniemi, 13));

        Charger otaCharger = new Charger("Otaniemi charger",
                "A charger in the middle of Otaniemi, free to use. It's located by Otaniemi mall " +
                        "behind Apteekki.", "Otaniementie 18",
                true, 18.0, PoleType.TESLA, otaniemi);

        setNewCharger(otaCharger);

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

    public void setNewCharger(Charger charger) {
        Marker newMarker = mMap.addMarker(new MarkerOptions()
                .position(charger.getCoords()));
        markerChargerMap.put(newMarker, charger);
    }

    public void showMarkerInfo(Marker marker) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction trans = manager.beginTransaction();

        slideLO.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);

        InfoFragment infoFrag =
                InfoFragment.newInstance(markerChargerMap.get(marker));
        trans.add(R.id.info_frag, infoFrag, "chargerInfo");
        trans.addToBackStack(null);
        trans.commit();
    }

    public void onClosePressed(String fragment) {
        FragmentManager manager = getSupportFragmentManager();
        /* These is switch-case in case there are multiple frament that close differently
        switch (fragment) {
            case "filterFragment":
        }*/
        manager.popBackStack();
    }

    @Override
    public void onBackPressed() {
        slideLO.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
        super.onBackPressed();
    }
}
