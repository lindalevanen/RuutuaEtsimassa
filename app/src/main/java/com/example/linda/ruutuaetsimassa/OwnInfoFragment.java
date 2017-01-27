package com.example.linda.ruutuaetsimassa;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

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
        public void onNextPressed(String fromFragment);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_own_info, container, false);

        RelativeLayout regB = (RelativeLayout) view.findViewById(R.id.regButton);
        final EditText emailInp = (EditText) view.findViewById(R.id.emailInput);
        final EditText passwordInp = (EditText) view.findViewById(R.id.passwordInput);
        final EditText passwordAgainInp = (EditText) view.findViewById(R.id.passwordInputAgain);

        regB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailInp.getText().toString();
                String password = passwordInp.getText().toString();
                String passwordAgain = passwordAgainInp.getText().toString();
                if(email.isEmpty() || password.isEmpty() || passwordAgain.isEmpty()) {
                    Toast.makeText(getContext(), "Täytä kaikki kohdat.", Toast.LENGTH_SHORT).show();
                } else if(!password.equals(passwordAgain)) {
                    Toast.makeText(getContext(), "Salasanat eivät täsmää.", Toast.LENGTH_SHORT).show();
                } else {
                    onNextPressed();
                }
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
            mListener.onNextPressed("ownInfoFrag");
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
