package com.example.zhulie;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;

public class ProfileFragment extends Fragment {

    TextView txtemail;
    Button btnLogout;
    SessionManager sessionManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        txtemail = view.findViewById(R.id.txtemail);
        btnLogout = view.findViewById(R.id.btnLogout);

        sessionManager = new SessionManager(getContext());

        HashMap<String, String> user = sessionManager.getUserDetails();
        String mEmail = user.get(SessionManager.EMAIL);

        txtemail.setText(mEmail);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.logout();
            }
        });


        return view;
    }

}
