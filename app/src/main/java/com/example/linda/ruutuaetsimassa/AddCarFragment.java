package com.example.linda.ruutuaetsimassa;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.linda.ruutuaetsimassa.Entities.Car;
import com.example.linda.ruutuaetsimassa.Entities.PoleType;
import com.google.android.gms.vision.text.Line;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static android.R.attr.fragment;
import static android.R.attr.type;
import static com.example.linda.ruutuaetsimassa.HelperMethods.dpToPx;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddCarFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddCarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddCarFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private View mainView;
    private int PTSpinnerAmount = 1;
    private int currentSpinnerId;
    private int currentAddButton;

    public AddCarFragment() {
        // Required empty public constructor
    }

    public interface OnFragmentInteractionListener {
        void onNextPressed(String fromFragment);
        void setUserCar(Car userCar);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AddCarFragment.
     */
    public static AddCarFragment newInstance() {
        AddCarFragment fragment = new AddCarFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mainView = inflater.inflate(R.layout.fragment_add_car, container, false);
        RelativeLayout regB = (RelativeLayout) mainView.findViewById(R.id.regButton);
        TextView skipB = (TextView) mainView.findViewById(R.id.skipB);
        ImageView addPTypeB = (ImageView) mainView.findViewById(R.id.addIcon);
        Spinner pluginType = (Spinner) mainView.findViewById(R.id.pluginType);

        currentAddButton = addPTypeB.getId();

        initPlugSpinner(pluginType, mainView);

        regB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onNextPressed(true);
            }
        });

        skipB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onNextPressed(false);
            }
        });

        addPTypeB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPType(mainView);
            }
        });

        return mainView;
    }

    public void addPType(final View mainView) {
        RelativeLayout typeContainer = (RelativeLayout) mainView.findViewById(R.id.pluginTypes);

        // Remove old add button from the view
        ImageView oldAddButton = (ImageView) mainView.findViewById(currentAddButton);
        oldAddButton.setVisibility(View.GONE);

        PTSpinnerAmount++;

        int padInPx = dpToPx(getContext(), 11);
        int largerPadInPx = dpToPx(getContext(), 20);

        // Create a new spinner with
        Spinner newType = new Spinner(getContext());
        RelativeLayout.LayoutParams loParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        loParams.setMargins(0, 0, 0, padInPx);
        loParams.addRule(RelativeLayout.BELOW, currentSpinnerId);

        newType.setLayoutParams(loParams);
        newType.setBackgroundResource(R.drawable.spinner_bg);
        newType.setTag(PTSpinnerAmount);
        newType.setId(View.generateViewId());
        newType.setPadding(padInPx, padInPx, padInPx, padInPx);

        initPlugSpinner(newType, mainView);

        typeContainer.addView(newType, loParams);

        if(PTSpinnerAmount != PoleType.values().length) {
            ImageView newAddButton = new ImageView(getContext());

            newAddButton.setImageResource(R.drawable.ic_add);

            RelativeLayout.LayoutParams loParams2 = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
            loParams2.setMargins(0, padInPx,0, largerPadInPx);
            loParams2.addRule(RelativeLayout.BELOW, newType.getId());
            loParams2.addRule(RelativeLayout.CENTER_HORIZONTAL);

            newAddButton.setLayoutParams(loParams2);
            newAddButton.setId(View.generateViewId());

            newAddButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addPType(mainView);
                }
            });

            typeContainer.addView(newAddButton);
            currentAddButton = newAddButton.getId();
        }
        currentSpinnerId = newType.getId();
    }

    public void initPlugSpinner(final Spinner spinner, View view) {
        String[] ptAsStrings = new String[PoleType.values().length];

        currentSpinnerId = spinner.getId();

        for(int i = 0; i < PoleType.values().length; i++) {
            ptAsStrings[i] = PoleType.values()[i].getName();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter(getContext(),
                android.R.layout.simple_spinner_item, ptAsStrings);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        /*
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                for (PoleType pt : PoleType.values()) {
                    if (pt.getName().equals(selectedItem)) {
                        poleType = pt;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });*/


    }

    public void onNextPressed(boolean withCar) {
        if (mListener != null) {
            if(withCar) {
                Car possibleCar = getPossibleCar();
                if (possibleCar != null) {
                    mListener.setUserCar(possibleCar);
                    mListener.onNextPressed("addCarFrag");
                } else {
                    Toast.makeText(getContext(), "Täytä kaikki kohdat.", Toast.LENGTH_SHORT).show();
                }
            } else {
                mListener.onNextPressed("addCarFrag");
            }
        }
    }

    public Car getPossibleCar() {
        EditText idField = (EditText) mainView.findViewById(R.id.carId);
        EditText rekkariNoField = (EditText) mainView.findViewById(R.id.numField);
        EditText rekkariCharField = (EditText) mainView.findViewById(R.id.charField);

        if(!idField.getText().toString().isEmpty() &&
                !rekkariNoField.getText().toString().isEmpty() &&
                !rekkariCharField.getText().toString().isEmpty()) {

            PoleType[] poleTypes = getPoleTypes();

            String wholeRekkari = rekkariNoField.getText().toString() + " - " + rekkariCharField.getText().toString();
            Car userCar = new Car(idField.getText().toString(), wholeRekkari, poleTypes);
            return userCar;
        } else {
            return null;
        }
    }

    public PoleType[] getPoleTypes() {
        RelativeLayout pluginContainer = (RelativeLayout) mainView.findViewById(R.id.pluginTypes);
        Set<PoleType> types = new HashSet<>();

        for(int index=0; index<pluginContainer.getChildCount(); ++index) {
            View nextChild = pluginContainer.getChildAt(index);
            if(nextChild instanceof Spinner) {
                String type = ((Spinner) nextChild).getSelectedItem().toString();

                for (PoleType pt : PoleType.values()) {
                    if (pt.getName().equals(type)) {
                        types.add(pt);
                    }
                }
            }
        }
        return types.toArray(new PoleType[types.size()]);
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
