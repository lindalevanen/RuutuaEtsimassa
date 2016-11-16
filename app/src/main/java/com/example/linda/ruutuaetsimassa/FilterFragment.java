package com.example.linda.ruutuaetsimassa;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FilterFragment.OnFilterActionListener} interface
 * to handle interaction events.
 * Use the {@link FilterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FilterFragment extends Fragment {

    private OnFilterActionListener mListener;

    public FilterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFilterActionListener) {
            mListener = (OnFilterActionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFilterActionListener");
        }
    }

    public interface OnFilterActionListener {
        public void onClosePressed(String fragment);
        // TODO: Update argument type and name
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FilterFragment.
     */
    public static FilterFragment newInstance() {
        FilterFragment fragment = new FilterFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filter, container, false);

        initListeners(view);
        // Inflate the layout for this fragment
        return view;
    }

    public void initListeners(View view) {
        ImageView closeButton = (ImageView) view.findViewById(R.id.close_button);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClosePressed("filterFragment");
            }
        });
    }

    public void onClosePressed(String fragment) {
        if (mListener != null) {
            mListener.onClosePressed(fragment);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


}
