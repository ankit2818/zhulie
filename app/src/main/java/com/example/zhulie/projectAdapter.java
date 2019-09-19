package com.example.zhulie;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class projectAdapter extends RecyclerView.Adapter<projectAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<projectModel> mList;

    public projectAdapter(Context mContext, ArrayList<projectModel> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);

        View view = layoutInflater.inflate(R.layout.project_list, viewGroup, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        TextView projectName = viewHolder.projectName;
        TextView dateOfCreation = viewHolder.dateOfCreation;
        CardView projectListCardView = viewHolder.projectListCardView;

        projectName.setText(mList.get(i).getProjectName());
        dateOfCreation.setText("Date of Creation - "+mList.get(i).getDate_creation());
        projectListCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(mContext, "This is clicked", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(mContext, ProjectBoardsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                mContext.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView projectName;
        TextView dateOfCreation;
        CardView projectListCardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            projectName = itemView.findViewById(R.id.projectName);
            dateOfCreation = itemView.findViewById(R.id.date_creation);
            projectListCardView = itemView.findViewById(R.id.project_list_card_view);
        }
    }

}
