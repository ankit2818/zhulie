package com.example.zhulie;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class ProjectBoardsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<BoardsModel> lstBoards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_boards);

        lstBoards = new ArrayList<>();
        lstBoards.add(new BoardsModel("Card 1"));
        lstBoards.add(new BoardsModel("Card 2"));
        lstBoards.add(new BoardsModel("Card 3"));
        lstBoards.add(new BoardsModel("Card 4"));
        lstBoards.add(new BoardsModel("Card 5"));
        lstBoards.add(new BoardsModel("Card 6"));
        lstBoards.add(new BoardsModel("Card 7"));

        recyclerView = findViewById(R.id.boards_recycler_view);

        BoardsAdapter myAdapter = new BoardsAdapter(getApplicationContext(), lstBoards);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(myAdapter);

    }
}
