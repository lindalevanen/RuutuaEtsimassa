package com.example.linda.ruutuaetsimassa;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;

import com.example.linda.ruutuaetsimassa.Entities.PoleType;

import java.util.HashMap;

import static android.R.attr.type;
import static com.example.linda.ruutuaetsimassa.HelperMethods.dpToPx;


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

    private static HashMap<PoleType, Boolean> poleTypeFilters;
    private static HashMap<String, Boolean> powerFilters;


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
        public void onFinishPressed(HashMap<PoleType, Boolean> poleTypeF, HashMap<String, Boolean> powerF);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FilterFragment.
     */
    public static FilterFragment newInstance(HashMap<PoleType, Boolean> poleTypeFilt,
                                             HashMap<String, Boolean> powerFilt) {
        FilterFragment fragment = new FilterFragment();

        poleTypeFilters = new HashMap<PoleType,Boolean>(poleTypeFilt);
        powerFilters = new HashMap<String,Boolean>(powerFilt);

        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filter, container, false);

        initListeners(view);
        addSwitches(view);
        // Inflate the layout for this fragment
        return view;
    }

    public void initListeners(View view) {
        ImageView closeButton = (ImageView) view.findViewById(R.id.closeButton);
        ImageView finishButton = (ImageView) view.findViewById(R.id.finishButton);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClosePressed("filterFragment");
            }
        });

        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onFinishPressed(poleTypeFilters, powerFilters);
            }
        });
    }

    public void addSwitches(View view) {
        LinearLayout typeSwitchLO = (LinearLayout) view.findViewById(R.id.typeSwithces);
        LinearLayout powerSwitchLO = (LinearLayout) view.findViewById(R.id.powerSwithces);

        for (final PoleType type : PoleType.values()) {
            Switch newSwitch = new Switch(getContext());
            LinearLayout.LayoutParams loParams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
            loParams.setMargins(0, dpToPx(getContext(), 5),0,0);

            newSwitch.setText(type.getName());
            newSwitch.setTag(type);

            if(poleTypeFilters.get(type)) newSwitch.setChecked(true);
            else newSwitch.setChecked(false);

            newSwitch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Switch s = (Switch) view;
                    poleTypeFilters.put(type, s.isChecked());
                }
            });

            typeSwitchLO.addView(newSwitch, loParams);
        }

        String[] powerArray = getResources().getStringArray(R.array.powers);
        for (final String power : powerArray) {
            Switch newSwitch = new Switch(getContext());
            LinearLayout.LayoutParams loParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            loParams.setMargins(0, dpToPx(getContext(), 5),0,0);
            newSwitch.setText(power + "kW");
            newSwitch.setTag(power);

            if(powerFilters.get(power)) newSwitch.setChecked(true);
            else newSwitch.setChecked(false);

            newSwitch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Switch s = (Switch) view;
                    powerFilters.put(power, s.isChecked());
                }
            });

            powerSwitchLO.addView(newSwitch, loParams);
        }
    }



    public void onClosePressed(String fragment) {
        if (mListener != null) {
            mListener.onClosePressed(fragment);
        }
    }

    public void onFinishPressed(HashMap<PoleType, Boolean> poleTypeF, HashMap<String, Boolean> powerF) {
        if (mListener != null) {
            mListener.onFinishPressed(poleTypeF, powerF);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


}
