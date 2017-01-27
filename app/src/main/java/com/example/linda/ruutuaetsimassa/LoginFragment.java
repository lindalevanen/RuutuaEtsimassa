package com.example.linda.ruutuaetsimassa;

import android.content.Context;
import android.content.DialogInterface;
import android.content.ReceiverCallNotAllowedException;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.Arrays;

import static android.R.attr.key;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LoginFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    public LoginFragment() {
        // Required empty public constructor
    }

    public interface OnFragmentInteractionListener {
        void onLoginPressed();
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
     * @return A new instance of fragment LoginFragment.
     */
    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View mainView = inflater.inflate(R.layout.fragment_login, container, false);

        RelativeLayout loginLO = (RelativeLayout) mainView.findViewById(R.id.signInButton);
        TextView forgotPassword = (TextView) mainView.findViewById(R.id.forgotPassword);

        initForgotPassWord(forgotPassword);

        loginLO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onFinishPressed(mainView);
            }
        });

        return mainView;
    }

    public void initForgotPassWord(TextView fp) {
        fp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPassword();
            }
        });
    }

    public void showPassword() {
        LayoutInflater li = LayoutInflater.from(getContext());
        final View dialogLO = li.inflate(R.layout.prompt_password, null);

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

    public void onFinishPressed(View view) {
        if (mListener != null) {
            EditText emailInp = (EditText) view.findViewById(R.id.emailInput);
            EditText passwordInp = (EditText) view.findViewById(R.id.passwordInput);
            String onlyWorkindPassword = "passu123";
            String[] emailArray = getResources().getStringArray(R.array.emails);

            String email = emailInp.getText().toString();
            String password = passwordInp.getText().toString();
            boolean bothFieldsEmpty = email.isEmpty() && password.isEmpty();
            boolean wrongEmailOrPassword = !Arrays.asList(emailArray).contains(email) ||
                    !password.equals(onlyWorkindPassword);

            if (bothFieldsEmpty) {
                Toast.makeText(getContext(), "Anna sähköposti ja salasana.", Toast.LENGTH_SHORT).show();
            } else if (email.isEmpty()) {
                Toast.makeText(getContext(), "Anna sähköposti.", Toast.LENGTH_SHORT).show();
            } else if (password.isEmpty()) {
                Toast.makeText(getContext(), "Anna salasana.", Toast.LENGTH_SHORT).show();
            } else if (wrongEmailOrPassword) {
                Toast.makeText(getContext(), "Virheellinen sähköposti tai salasana.", Toast.LENGTH_SHORT).show();
            } else {
                mListener.onLoginPressed();
            }
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


}
