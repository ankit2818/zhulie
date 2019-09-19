package com.example.zhulie;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class task_list_adapter extends RecyclerView.Adapter<task_list_adapter.ViewHolder> {

    private Context mContext;
    private ArrayList<task_list_model> mList;

    public task_list_adapter(Context mContext, ArrayList<task_list_model> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mContext);

        View view = inflater.inflate(R.layout.task_list, viewGroup, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        TextView taskName = viewHolder.taskName ;

        taskName.setText(mList.get(i).getTaskName());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView taskName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            taskName = itemView.findViewById(R.id.add_task);
        }
    }
}
