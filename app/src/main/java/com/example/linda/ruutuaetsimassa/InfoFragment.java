package com.example.linda.ruutuaetsimassa;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.linda.ruutuaetsimassa.Entities.Charger;
import com.google.android.gms.maps.model.Marker;

public class InfoFragment extends Fragment {

    private onInfoItemPressed mListener;

    private static final String ARGUMENT_NAME = "name";
    private static final String ARGUMENT_ADDRESS = "address";
    private static final String ARGUMENT_DESCRIPTION = "description";
    private static final String ARGUMENT_STATE = "state";
    private static final String ARGUMENT_PLUGTYPE = "plugType";
    private static final String ARGUMENT_POWER = "power";

    private static Marker marker;

    public interface onInfoItemPressed {
        void onBookPressed(Marker marker);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof onInfoItemPressed) {
            mListener = (onInfoItemPressed) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement onInfoItemPressed");
        }
    }

    public InfoFragment() {
        // Required empty public constructor
    }

    public static InfoFragment newInstance(Charger charger, Marker mark) {
        InfoFragment fragment = new InfoFragment();
        Bundle args = new Bundle();

        marker = mark;

        args.putString(ARGUMENT_NAME, charger.getName());
        args.putString(ARGUMENT_ADDRESS, charger.getAddress());
        args.putString(ARGUMENT_DESCRIPTION, charger.getDescription());
        args.putBoolean(ARGUMENT_STATE, charger.isFree());
        args.putString(ARGUMENT_PLUGTYPE, charger.getName());
        args.putString(ARGUMENT_POWER, charger.getName());

        fragment.setArguments(args);
        return fragment;
    }

    /*@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);
        final Bundle args = getArguments();

        TextView nameView = (TextView) view.findViewById(R.id.name);
        TextView addressView = (TextView) view.findViewById(R.id.address);
        TextView descriptionView = (TextView) view.findViewById(R.id.description);
        Button bookButton = (Button) view.findViewById(R.id.bookButton);

        nameView.setText(args.getString(ARGUMENT_NAME));
        addressView.setText(args.getString(ARGUMENT_ADDRESS));
        descriptionView.setText(args.getString(ARGUMENT_DESCRIPTION));

        bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(args.getBoolean(ARGUMENT_STATE)) {
                    onBookPressed(marker);
                    args.putBoolean(ARGUMENT_STATE, false);
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Varattu juuri nyt!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }


    public void onBookPressed(Marker marker) {
        if (mListener != null) {
            mListener.onBookPressed(marker);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
