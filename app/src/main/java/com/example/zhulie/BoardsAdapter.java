package com.example.zhulie;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class BoardsAdapter extends RecyclerView.Adapter<BoardsAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<BoardsModel> mList;
//    RecyclerView recyclerView;
    ArrayList<task_list_model> lstTask;

    public BoardsAdapter(Context mContext, ArrayList<BoardsModel> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);

        View view = layoutInflater.inflate(R.layout.boards_layout, viewGroup, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        TextView boardName = viewHolder.boardName;
        RecyclerView recyclerView = viewHolder.recyclerView;

        boardName.setText(mList.get(i).getBoardName());

        lstTask = new ArrayList<>();

        lstTask.add(new task_list_model("Task 1"));
        lstTask.add(new task_list_model("Task 2"));
        lstTask.add(new task_list_model("Task 3"));
        lstTask.add(new task_list_model("Task 4"));
        lstTask.add(new task_list_model("Task 5"));
        lstTask.add(new task_list_model("Task 6"));
        lstTask.add(new task_list_model("Task 7"));
        lstTask.add(new task_list_model("Task 8"));
        lstTask.add(new task_list_model("Task 9"));
        lstTask.add(new task_list_model("Task 10"));
        lstTask.add(new task_list_model("Task 11"));
        lstTask.add(new task_list_model("Task 12"));
        lstTask.add(new task_list_model("Task 13"));
        lstTask.add(new task_list_model("Task 14"));
        lstTask.add(new task_list_model("Task 15"));
        lstTask.add(new task_list_model("Task 16"));
        lstTask.add(new task_list_model("Task 17"));


        task_list_adapter myAdapter = new task_list_adapter(mContext, lstTask);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL,false);

//        GridLayoutManager layoutManager = new GridLayoutManager(mContext,4);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(myAdapter);

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView boardName;
        RecyclerView recyclerView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            boardName = itemView.findViewById(R.id.board_header);
            recyclerView = itemView.findViewById(R.id.task_recycler_view);
        }
    }

}
