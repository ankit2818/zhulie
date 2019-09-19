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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.zhulie.Constants.*;

public class RegisterActivity extends AppCompatActivity {

    private final String url = userRegister;

    private EditText username, password, cpassword;
    private Button btnRegister, btnLogin;
    private ProgressBar registerLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = findViewById(R.id.rEdtUsername);
        password = findViewById(R.id.rEdtPassword);
        cpassword = findViewById(R.id.rEdtcPassword);
        btnRegister = findViewById(R.id.btnRegister);
        btnLogin = findViewById(R.id.btnLogin);
        registerLoading = findViewById(R.id.register_loading);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rusername = username.getText().toString().trim();
                String rpassword = password.getText().toString().trim();
                String rcpassword = cpassword.getText().toString().trim();

                if (rusername.isEmpty()){
                    username.setError("Email can not be empty");
                }
                else if (rpassword.isEmpty()){
                    password.setError("Password can not be empty");
                }
                else if (rcpassword.isEmpty()){
                    cpassword.setError("Confirm password can not be empty");
                }
                else{
                    if (!rpassword.equals(rcpassword)){
                        Toast.makeText(getApplicationContext(), "Password is not same", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Register(rusername, rpassword);
                    }

                }

            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    private void Register(final String username, final String password) {
        registerLoading.setVisibility(View.VISIBLE);
        btnRegister.setVisibility(View.GONE);
        btnLogin.setVisibility(View.GONE);

        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if (success.equals("true")){
                                btnRegister.setVisibility(View.VISIBLE);
                                btnLogin.setVisibility(View.VISIBLE);
                                registerLoading.setVisibility(View.GONE);

                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(intent);
                            }
                            else{
                                btnRegister.setVisibility(View.VISIBLE);
                                btnLogin.setVisibility(View.VISIBLE);
                                registerLoading.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(),"Error while registration", Toast.LENGTH_SHORT).show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Registration Error : "+e, Toast.LENGTH_SHORT).show();
                            btnRegister.setVisibility(View.VISIBLE);
                            btnLogin.setVisibility(View.VISIBLE);
                            registerLoading.setVisibility(View.GONE);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        btnRegister.setVisibility(View.VISIBLE);
                        btnLogin.setVisibility(View.VISIBLE);
                        registerLoading.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(),"Register Error : "+error, Toast.LENGTH_SHORT).show();

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
