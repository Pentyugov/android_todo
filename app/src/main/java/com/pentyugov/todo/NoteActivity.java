package com.pentyugov.todo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.pentyugov.todo.model.folder.Folder;
import com.pentyugov.todo.model.folder.FolderManager;

import java.util.List;

public class NoteActivity extends AppCompatActivity {
    private TextView title;
    private List<Folder> folders;
    private Folder parentFolder;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        init();
    }

    public void init() {
        Intent intent = getIntent();
        title = findViewById(R.id.note_tv_title);
        folders = FolderManager.getFoldersFromFile(this);
        if(folders != null) {
            parentFolder = folders.get(intent.getExtras().getInt("folderPosition"));
        }
        title.setText(title.getText().toString() + parentFolder.getName());


    }
}
