package com.example.zhulie;

import android.app.AlertDialog;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
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

public class HomeFragment extends Fragment {

    private String url = getTeam;
    private String addTeamURL = newTeam;
    private String email, teamName;
    private FloatingActionButton btnaddTeam;
    private LinearLayout no_team_msg;

    RecyclerView recyclerView_show_teams;
    ArrayList<teamModel> teamList;

    SessionManager sessionManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        sessionManager = new SessionManager(getContext());

        HashMap<String, String> user = sessionManager.getUserDetails();
        email = user.get(SessionManager.EMAIL);

        recyclerView_show_teams = view.findViewById(R.id.show_teams);
        no_team_msg = view.findViewById(R.id.no_team_msg);
        btnaddTeam = view.findViewById(R.id.btnaddTeam);

        btnaddTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getContext(),"Clicked", Toast.LENGTH_SHORT).show();

                final AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                View mView = getLayoutInflater().inflate(R.layout.add_team, null);

                final EditText EdtaddTeam = mView.findViewById(R.id.EdtteamName);
                Button btnCancel = mView.findViewById(R.id.btnCancel);
                Button btnAdd = mView.findViewById(R.id.btnAdd);

                alert.setView(mView);

                final AlertDialog alertDialog = alert.create();
//                alertDialog.setCanceledOnTouchOutside(false);

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });

                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        teamName = EdtaddTeam.getText().toString();
                        if(!teamName.equals("")){
                            AddNewTeam(email, teamName);
                        } else {
                            Toast.makeText(getContext(), "Team name cannot be empty.", Toast.LENGTH_SHORT).show();
                        }
                        alertDialog.dismiss();
                    }
                });

                alertDialog.show();
            }
        });


        teamList = new ArrayList<>();

        TeamList();

//        teamList.add(new teamModel(R.drawable.one_piece, "Team Straw Hat"));
//        teamList.add(new teamModel(R.drawable.one_piece, "Team Straw Hat"));
//        teamList.add(new teamModel(R.drawable.one_piece, "Team Straw Hat"));
//        teamList.add(new teamModel(R.drawable.one_piece, "Team Straw Hat"));
//        teamList.add(new teamModel(R.drawable.one_piece, "Team Straw Hat"));
//        teamList.add(new teamModel(R.drawable.one_piece, "Team Straw Hat"));
//        teamList.add(new teamModel(R.drawable.one_piece, "Team Straw Hat"));
//        teamList.add(new teamModel(R.drawable.one_piece, "Team Straw Hat"));
//        teamList.add(new teamModel(R.drawable.one_piece, "Team Straw Hat"));
//        teamList.add(new teamModel(R.drawable.one_piece, "Team Straw Hat"));

        return view;
    }

    private void TeamList() {

        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("true")){
                                Toast.makeText(getContext(), "Teams Loaded successfully", Toast.LENGTH_SHORT).show();

                                JSONArray teams = jsonObject.getJSONArray("teams");

                                for (int i=0; i<teams.length(); i++) {

                                    JSONObject jsonObject1 = teams.getJSONObject(i);
                                    String teamName = jsonObject1.getString("team_name");
                                    String teamId = jsonObject1.getString("team_id");

                                    teamList.add(new teamModel(R.drawable.one_piece, teamName,teamId));

                                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                                    RecyclerView.LayoutManager layoutManager1 = layoutManager;

                                    show_team_adapter adapter = new show_team_adapter(getContext(), teamList);

                                    recyclerView_show_teams.setLayoutManager(layoutManager);
                                    recyclerView_show_teams.setAdapter(adapter);
                                    no_team_msg.setVisibility(View.GONE);

                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(),"Error loading teams : "+e, Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(),"Teams Error : "+error, Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String>params = new HashMap<>();
                params.put("email_id", email);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }

    public void AddNewTeam(final String email, final String teamName){
        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, addTeamURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("true")){
                                Intent intent = new Intent(getContext(), MainActivity.class);
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(getContext(), "Error while loading New Team", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "Add Team Error : "+e, Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Error Add Team : "+error, Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String>params = new HashMap<>();
                params.put("team_name", teamName);
                params.put("email_id", email);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }


}
