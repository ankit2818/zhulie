package com.example.zhulie;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class show_team_adapter extends RecyclerView.Adapter<show_team_adapter.ViewHolder> {

    private Context mContext;
    private ArrayList<teamModel> mList;

    show_team_adapter(Context context, ArrayList<teamModel> list){
        mContext = context;
        mList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(mContext);

        View view = layoutInflater.inflate(R.layout.show_team_list, viewGroup, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

        ImageView image = viewHolder.teamImage;
        TextView team_name = viewHolder.teamName;
        CardView team_card_view = viewHolder.team_card_view;

        image.setImageResource(mList.get(i).getTeamImage());
        team_name.setText(mList.get(i).getTeamName());

        team_card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, projectsActivity.class);
                intent.putExtra("teamId", mList.get(i).getTeamId());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView teamImage;
        TextView teamName;
        CardView team_card_view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            teamImage = itemView.findViewById(R.id.teamImage);
            teamName = itemView.findViewById(R.id.teamName);
            team_card_view = itemView.findViewById(R.id.team_card_view);

        }
    }

}
