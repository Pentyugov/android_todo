package com.pentyugov.todo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pentyugov.todo.model.folder.Folder;
import com.pentyugov.todo.model.folder.FolderManager;
import com.pentyugov.todo.model.note.Note;
import com.pentyugov.todo.util.adapter.NoteAdapter;
import com.pentyugov.todo.util.listener.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class NoteActivity extends AppCompatActivity {
    private TextView title;
    private List<Folder> folders;
    private Folder parentFolder;
    private RecyclerView.Adapter adapter;
    private RecyclerView notesRV;
    private List<Note> notes;
    private ImageView addNote;
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.2F);
    private int parentFolderPosition;
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
        addNote = findViewById(R.id.note_im_addNote);
        if(folders != null) {
            parentFolderPosition = intent.getExtras().getInt("folderPosition");
            parentFolder = folders.get(parentFolderPosition);
            notes = parentFolder.getNotes();
            title.setText(title.getText().toString() + " " + parentFolder.getName());
        }


        adapter = new NoteAdapter(notes, this);
        notesRV = findViewById(R.id.note_rv_notesList);
        notesRV.setHasFixedSize(true);
        notesRV.setLayoutManager(new LinearLayoutManager(this));
        notesRV.setAdapter(adapter);

        addNote.setOnClickListener(v -> {
            v.startAnimation(buttonClick);
            Intent newNoteActivity = new Intent(this, NewNoteActivity.class);
            newNoteActivity.putExtra("folderPosition", parentFolderPosition);
            startActivity(newNoteActivity);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }
}
