package com.pentyugov.todo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pentyugov.todo.util.adapter.FolderAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView title;
    private ImageView newFolder;
    private RecyclerView folders;
    private List<String> foldersNames;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    public void init() {
        title = findViewById(R.id.main_tv_title);
        newFolder = findViewById(R.id.main_iv_newFolder);
        folders = findViewById(R.id.main_rv_foldersList);
        foldersNames = new ArrayList<>(Arrays
                                            .asList(getResources()
                                            .getStringArray(R.array.folders)));
        adapter = new FolderAdapter(foldersNames);

        folders.setHasFixedSize(true);
        folders.setLayoutManager(new LinearLayoutManager(this));
        folders.setAdapter(adapter);

        newFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}