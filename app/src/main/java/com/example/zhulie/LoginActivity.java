package com.example.zhulie;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.zhulie.Constants.*;

public class LoginActivity extends AppCompatActivity {

    private String url = userLogin;

    private EditText username, password;
    private Button btnLogin, btnRegister;
    private ProgressBar loginLoading;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sessionManager = new SessionManager(getApplicationContext());

        if (sessionManager.isLoggin()){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }


        username = findViewById(R.id.EdtUsername);
        password = findViewById(R.id.EdtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        loginLoading = findViewById(R.id.login_loading);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mUsername = username.getText().toString().trim();
                String mPassword = password.getText().toString().trim();

                if (mUsername.isEmpty()){
                    username.setError("Please Enter your email");
                }
                else if (mPassword.isEmpty()){
                    password.setError("Please insert your password");
                }
                else {
                    Login(mUsername, mPassword);
                }
            }
        });

    }

    private void Login(final String username, final String password) {

        loginLoading.setVisibility(View.VISIBLE);
        btnLogin.setVisibility(View.GONE);
        btnRegister.setVisibility(View.GONE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if (success.equals("true")){
                                btnLogin.setVisibility(View.VISIBLE);
                                btnRegister.setVisibility(View.VISIBLE);
                                loginLoading.setVisibility(View.GONE);

                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                sessionManager.createSession(username);
                                startActivity(intent);
                            }
                            else{
                                btnLogin.setVisibility(View.VISIBLE);
                                btnRegister.setVisibility(View.VISIBLE);
                                loginLoading.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(),"You are not authentic user", Toast.LENGTH_SHORT).show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            btnLogin.setVisibility(View.VISIBLE);
                            btnRegister.setVisibility(View.VISIBLE);
                            loginLoading.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(), "can not Login : "+e, Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        btnLogin.setVisibility(View.VISIBLE);
                        btnRegister.setVisibility(View.VISIBLE);
                        loginLoading.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "Login Error : "+error, Toast.LENGTH_LONG).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email_id", username);
                params.put("password", password);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }
}
