package com.example.linda.ruutuaetsimassa;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OwnInfoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class OwnInfoFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    public OwnInfoFragment() {
        // Required empty public constructor
    }

    public interface OnFragmentInteractionListener {
        public void onNextPressed();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_own_info, container, false);

        RelativeLayout regB = (RelativeLayout) view.findViewById(R.id.regButton);

        regB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: check that information given is correct...
                onNextPressed();
            }
        });

        return view;
    }

    public static OwnInfoFragment newInstance() {
        OwnInfoFragment fragment = new OwnInfoFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    public void onNextPressed() {
        if (mListener != null) {
            mListener.onNextPressed();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


}
