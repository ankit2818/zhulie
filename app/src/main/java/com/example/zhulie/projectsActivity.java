package com.example.zhulie;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.zhulie.Constants.*;

public class projectsActivity extends AppCompatActivity {

    private String url = teamProjects;
    private String addProjectURL = newTeamProjects;

    RecyclerView projectRecycler;
    ArrayList<projectModel> projectList;
    String teamId,email,projectName;
    FloatingActionButton btnaddProjects;

    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects);

        sessionManager = new SessionManager(getApplicationContext());

        HashMap<String, String> user = sessionManager.getUserDetails();
        email = user.get(SessionManager.EMAIL);

        projectRecycler = findViewById(R.id.project_recycler_view);
        btnaddProjects = findViewById(R.id.btnaddProjects);

        btnaddProjects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alert = new AlertDialog.Builder(projectsActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.add_projects, null);

                final EditText EdtProject = mView.findViewById(R.id.EdtAddProjects);
                Button btnCancel = mView.findViewById(R.id.proj_btnCancel);
                Button btnAdd = mView.findViewById(R.id.proj_btnAdd);

                alert.setView(mView);

                final AlertDialog alertDialog1 = alert.create();
//                alertDialog.setCanceledOnTouchOutside(false);

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog1.dismiss();
                    }
                });

                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        projectName = EdtProject.getText().toString();
                        AddNewProject(email, projectName);
                        alertDialog1.dismiss();
                    }
                });

                alertDialog1.show();
            }
        });

        Intent intent = getIntent();
        teamId = intent.getExtras().getString("teamId");

        projectList = new ArrayList<>();
//        projectList.add(new projectModel("Project Name Here"));
//        projectList.add(new projectModel("Project Name Here"));
//        projectList.add(new projectModel("Project Name Here"));
//        projectList.add(new projectModel("Project Name Here"));
//        projectList.add(new projectModel("Project Name Here"));
//        projectList.add(new projectModel("Project Name Here"));
//        projectList.add(new projectModel("Project Name Here"));
//        projectList.add(new projectModel("Project Name Here"));
//        projectList.add(new projectModel("Project Name Here"));
//        projectList.add(new projectModel("Project Name Here"));
//        projectList.add(new projectModel("Project Name Here"));

        ProjectList();


        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        RecyclerView.LayoutManager layoutManager1 = layoutManager;

        projectAdapter adapter = new projectAdapter(getApplicationContext(), projectList);

        projectRecycler.setLayoutManager(layoutManager);
        projectRecycler.setAdapter(adapter);

    }

    private void ProjectList() {

        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("true")){
                                Toast.makeText(getApplicationContext(), "Project loaded successfully", Toast.LENGTH_SHORT).show();

                                JSONArray jsonArray = jsonObject.getJSONArray("projects");

                                for (int i = 0; i<jsonArray.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    String projectName = jsonObject1.getString("title");
                                    String dataOfCreation = jsonObject1.getString("date_of_creation");

                                    projectList.add(new projectModel(projectName, dataOfCreation));

                                    LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                                    RecyclerView.LayoutManager layoutManager1 = layoutManager;

                                    projectAdapter adapter = new projectAdapter(getApplicationContext(), projectList);

                                    projectRecycler.setLayoutManager(layoutManager);
                                    projectRecycler.setAdapter(adapter);
                                }

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Project loading Error "+e, Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Error in Project : "+error, Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("team_id", teamId);
                params.put("email_id", email);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }


    public void AddNewProject(final String email, final String projectName){
        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, addProjectURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("true")){
                                Intent intent = new Intent(getApplicationContext(), projectsActivity.class);
                                intent.putExtra("teamId",teamId);
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "Error while loading New Project", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Add Project Error : "+e, Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Error Add Project : "+error, Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String>params = new HashMap<>();
                params.put("project_name", projectName);
                params.put("team_id", teamId);
                params.put("email_id", email);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

}
