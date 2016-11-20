package com.example.linda.ruutuaetsimassa;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CreditCardFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CreditCardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreditCardFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    public CreditCardFragment() {
        // Required empty public constructor
    }

    public interface OnFragmentInteractionListener {
        void onRegisterPressed();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CreditCardFragment.
     */
    public static CreditCardFragment newInstance() {
        CreditCardFragment fragment = new CreditCardFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_credit_card, container, false);

        ImageView questionB = (ImageView) view.findViewById(R.id.questionIcon);
        RelativeLayout regB = (RelativeLayout) view.findViewById(R.id.regButton);

        questionB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCCInfo();
            }
        });

        regB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRegisterPressed();
            }
        });

        initSpinners(view);

        return view;
    }

    public void initSpinners(View view) {
        Spinner monthSpinner = (Spinner) view.findViewById(R.id.month);
        Spinner yearSpinner = (Spinner) view.findViewById(R.id.year);

        ArrayAdapter<CharSequence> adapterM = ArrayAdapter.createFromResource(getContext(),
                R.array.months, android.R.layout.simple_spinner_item);

        ArrayList<String> years = new ArrayList<String>();
        for (int i = 2016; i <= 2030; i++) {
            years.add(Integer.toString(i));
        }

        ArrayAdapter<String> adapterY = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, years);

        adapterM.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterY.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        monthSpinner.setAdapter(adapterM);
        yearSpinner.setAdapter(adapterY);
    }

    public void showCCInfo() {
        LayoutInflater li = LayoutInflater.from(getContext());
        final View dialogLO = li.inflate(R.layout.prompt_cc_info, null);

        // Creates the dialog
        final AlertDialog d = new AlertDialog.Builder(getContext())
                .setView(dialogLO)
                .setPositiveButton("OK", null)
                .create();

        d.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button p = d.getButton(AlertDialog.BUTTON_POSITIVE);

                p.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        d.dismiss();
                    }
                });

            }
        });
        d.show();
    }

    public void onRegisterPressed() {
        if (mListener != null) {
            mListener.onRegisterPressed();
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
