package com.example.linda.ruutuaetsimassa;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.linda.ruutuaetsimassa.Entities.Charger;
import com.example.linda.ruutuaetsimassa.Entities.PoleType;
import com.google.android.gms.drive.query.Filter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.HashMap;

import static android.R.attr.width;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.fromResource;

public class MapActivity extends FragmentActivity
        implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener,
        InfoFragment.onInfoItemPressed, FilterFragment.OnFilterActionListener {

    private GoogleMap mMap;
    SlidingUpPanelLayout slideLO;
    private HashMap<Marker, Charger> markerChargerMap;

    //Navigation drawer variables
    public DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;
    private Marker ownCharger = null;

    private int height = 100;
    private double width = height * 0.6172;

    BitmapDescriptor blueMarker, redMarker;

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
        initMarkerBitmapDescriptors();
    }

    public void sliderInit() {
        slideLO = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        slideLO.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
    }

    public void initMarkerBitmapDescriptors() {
        Bitmap blueMarkerBM = BitmapFactory.decodeResource(getResources(), R.drawable.blue_map_marker);
        Bitmap rzBlueMarkerBM = Bitmap.createScaledBitmap(blueMarkerBM, (int) width, height, true);
        blueMarker = BitmapDescriptorFactory.fromBitmap(rzBlueMarkerBM);

        Bitmap redMarkerBM = BitmapFactory.decodeResource(getResources(), R.drawable.red_map_marker);
        Bitmap rzRedMarkerBM = Bitmap.createScaledBitmap(redMarkerBM, (int) width, height, true);
        redMarker = BitmapDescriptorFactory.fromBitmap(rzRedMarkerBM);
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

    public boolean hasOwnCharger() {
        return ownCharger != null;
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

        LatLng otaniemi = new LatLng(60.184310, 24.829612);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(otaniemi, 13));

        setDummyChargers();

        // Zoom in, animating the camera.
        mMap.animateCamera(CameraUpdateFactory.zoomIn());
        // Zoom out to zoom level 10, animating with a duration of 2 seconds.
        mMap.animateCamera(CameraUpdateFactory.zoomTo(13), 2000, null);

        mMap.setOnMarkerClickListener(this);
        mMap.setOnMapClickListener(this);
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {
        showMarkerInfo(marker);
        return false;
    }

    @Override
    public void onMapClick(LatLng latLng) {
        hideMarkerInfo();
    }

    public void setNewCharger(Charger charger) {
        Bitmap markerBtmp;
        if(charger.isFree()) {
            markerBtmp = BitmapFactory.decodeResource(getResources(), R.drawable.blue_map_marker);
        } else {
            markerBtmp = BitmapFactory.decodeResource(getResources(), R.drawable.red_map_marker);
        }
        Bitmap resized = Bitmap.createScaledBitmap(markerBtmp, (int) width, height, true);
        Marker newMarker = mMap.addMarker(new MarkerOptions()
                .position(charger.getCoords())
                .icon(BitmapDescriptorFactory.fromBitmap(resized)));

        markerChargerMap.put(newMarker, charger);
    }

    public void showMarkerInfo(Marker marker) {
        //Remove previous Infofragment if there is one
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction trans = manager.beginTransaction();

        Fragment possibleFrag = manager.findFragmentByTag("chargerInfo");
        if(possibleFrag != null) {
            trans.remove(possibleFrag);
            manager.popBackStack();
        }

        slideLO.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);

        boolean thisIsOwnCharger = hasOwnCharger() && ownCharger.equals(marker);

        InfoFragment infoFrag =
                InfoFragment.newInstance(markerChargerMap.get(marker), marker, thisIsOwnCharger);
        trans.add(R.id.info_frag, infoFrag, "chargerInfo");
        trans.addToBackStack(null);
        trans.commit();
    }

    public void hideMarkerInfo() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction trans = manager.beginTransaction();

        Fragment possibleFrag = manager.findFragmentByTag("chargerInfo");
        if(possibleFrag != null) {
            trans.remove(possibleFrag);
            manager.popBackStack();
            slideLO.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
        }

    }

    public void onClosePressed(String fragment) {
        FragmentManager manager = getSupportFragmentManager();
        /* These is switch-case in case there are multiple frament that close differently
        switch (fragment) {
            case "filterFragment":
        }*/
        manager.popBackStack();
    }

    public void onBookPressed(Marker marker, boolean unBook) {
        if(unBook) {
            marker.setIcon(blueMarker);
            ownCharger = null;
            showReceipt();
            markerChargerMap.get(marker).setAsFree(true);
            slideLO.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
        } else {
            if(hasOwnCharger()) {
                Toast.makeText(this, "Voit varata vain yhden pisteen kerrallaan!", Toast.LENGTH_SHORT).show();
            } else {
                marker.setIcon(redMarker);
                ownCharger = marker;
                Toast.makeText(this, "Varaus onnistui!", Toast.LENGTH_SHORT).show();
                markerChargerMap.get(marker).setAsFree(false);
                slideLO.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
            }
        }
    }

    public void showReceipt() {
        LayoutInflater li = LayoutInflater.from(this);
        final View dialogLO = li.inflate(R.layout.receipt_prompt, null);

        // Creates the dialog
        final AlertDialog d = new AlertDialog.Builder(this)
                .setView(dialogLO)
                .setPositiveButton("OK", null)
                .setNegativeButton("NÄYTÄ KUITTI", null)
                .create();

        d.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button p = d.getButton(AlertDialog.BUTTON_POSITIVE);
                Button n = d.getButton(AlertDialog.BUTTON_NEGATIVE);

                p.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        d.dismiss();
                    }
                });

                n.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /*d.dismiss();*/
                        Toast.makeText(getApplicationContext(), "Kuitin näyttö ei vielä implementoitu", Toast.LENGTH_SHORT).show();
                        //TODO: näytä kuitti
                    }
                });
            }
        });
        d.show();
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            slideLO.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
            super.onBackPressed();
        }
    }

    public void setDummyChargers() {
        LatLng otaniemi = new LatLng(60.184310, 24.829612);
        LatLng tapUimahalli = new LatLng(60.178551, 24.807798);
        LatLng tapMetroasema = new LatLng(60.175302, 24.805717);
        LatLng espooArenaParkkis = new LatLng(60.176399, 24.780923);
        LatLng sellonParkkis = new LatLng(60.218000, 24.812916);
        LatLng didricheninTaidemuseo = new LatLng(60.185346, 24.856132);
        LatLng cafeBella = new LatLng(60.156278, 24.786920);
        LatLng kallenTolppa = new LatLng(60.172455, 24.730604);
        LatLng matinTolppa = new LatLng(60.192930, 24.793582);

        Charger otaCharger = new Charger("Otaniemi charger",
                            "A charger in the middle of Otaniemi, free to use. It's located by Otaniemi mall " +
                            "behind Apteekki.", "Otaniementie 18",
                            true, 3.0, 18.0, PoleType.TESLA, otaniemi);

        Charger uimahalliCharger = new Charger("Uimahallin parkkis", "Juuri uimahallin sisäänkäynnin vieressä "+
                                                "oleva sähkötolpallinen parkkipaikka.", "Kirkkopolku 3",
                                                false, 4.0, 5.0, PoleType.J1772, tapUimahalli);

        Charger espooArenaParkkisCharger = new Charger("Espoon Arena", "Tolppa pääovilta n. 100m länteen, soita numeroon "+
                                            "+3591234567 onglelmien ilmetessä.", "Koivu-Mankkaantie 5",
                                            true, 5.5, 8.0, PoleType.NEMA15, espooArenaParkkis);

        Charger sellonParkkisCharger = new Charger("Sellon tolppa", "Lataustolppa P2 kerroksessa J puolella.",
                                        "Hevosenkenkä 5", false, 4.4, 14.0, PoleType.NEMA15, sellonParkkis);

        Charger didrichCharger = new Charger("Didrichenin museon tolppa", "Tolppa museon edessä, soita numeroon "+
                                    "+35850493757 ongelmien ilmetessä.","Kuusilahdenkuja 6", true,
                                            3.0, 10.0, PoleType.SAECOMBO, didricheninTaidemuseo);

        Charger cafeCharger = new Charger("Café Bella Charger", "Feel free to use our charger, it's located in "+
                                        "front of our lovely cafe.","Hietaniemenkuja 5", true, 2.5, 8.0, PoleType.NEMA15, cafeBella);

        Charger kallenCharger = new Charger("Kallen tolppa","Tolppa tien vieressä, soita +35854943843, "+
                                "jos tulee ongelmia","Olarinkatu 16", true, 2.5, 8.0, PoleType.NEMA50, kallenTolppa);

        Charger matinChareegr = new Charger("Matin latauspiste", "Latauspiste osoitteen kohdalta sisäpihalle päin, "+
                                "löytyy katoksen alta.","Uudenkirkontie 6", true, 3.0, 6.0, PoleType.TESLA, matinTolppa);

        setNewCharger(otaCharger);
        setNewCharger(uimahalliCharger);
        setNewCharger(espooArenaParkkisCharger);
        setNewCharger(sellonParkkisCharger);
        setNewCharger(didrichCharger);
        setNewCharger(cafeCharger);
        setNewCharger(kallenCharger);
        setNewCharger(matinChareegr);

    }

}
