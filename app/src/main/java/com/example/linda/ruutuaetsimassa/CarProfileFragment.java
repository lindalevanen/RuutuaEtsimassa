package com.example.linda.ruutuaetsimassa;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.linda.ruutuaetsimassa.Entities.Car;
import com.example.linda.ruutuaetsimassa.Entities.PoleType;
import com.google.android.gms.vision.text.Line;

import static com.example.linda.ruutuaetsimassa.HelperMethods.dpToPx;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CarProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CarProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CarProfileFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private static Car car = null;

    public CarProfileFragment() {
        // Required empty public constructor
    }

    public interface OnFragmentInteractionListener {
        void onClosePressed(String fromFragment);
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

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CarProfileFragment.
     */
    public static CarProfileFragment newInstance(Car userCar) {
        CarProfileFragment fragment = new CarProfileFragment();
        Bundle args = new Bundle();

        car = userCar;

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_car_profile, container, false);

        initInfo(view);

        ImageView closeButton = (ImageView) view.findViewById(R.id.closeButton);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClosePressed("carProfileFrag");
            }
        });

        return view;
    }

    public void initInfo(View view) {
        TextView idView = (TextView) view.findViewById(R.id.id);
        TextView registerView = (TextView) view.findViewById(R.id.registerNo);
        LinearLayout typesView = (LinearLayout) view.findViewById(R.id.pTypes);

        idView.setText(car.getCarId());
        registerView.setText(car.getRegisterNo());

        for(int i = 0; i < car.getPluginTypes().length; i++) {
            PoleType PT = car.getPluginTypes()[i];
            TextView newPT = new TextView(getContext());
            int padmarPx = dpToPx(getContext(),10);

            LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            p.setMargins(padmarPx,padmarPx,padmarPx,padmarPx);

            newPT.setLayoutParams(p);

            newPT.setText(PT.getName());
            newPT.setTextColor(Color.parseColor("#ffffff"));
            newPT.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            newPT.setPadding(padmarPx,padmarPx,padmarPx,padmarPx);
            newPT.setBackgroundResource(R.drawable.color_border);

            typesView.addView(newPT);
        }

        //tyyppiView.setText(car.getPluginType().getName());
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}
