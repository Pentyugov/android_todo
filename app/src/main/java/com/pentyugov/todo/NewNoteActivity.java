package com.pentyugov.todo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.pentyugov.todo.model.folder.Folder;
import com.pentyugov.todo.model.folder.FolderManager;
import com.pentyugov.todo.model.note.Note;

import java.util.Date;
import java.util.List;

public class NewNoteActivity extends AppCompatActivity {
    private EditText descriptionInput;
    private ImageView done;
    private EditText title;
    private int parentFolderPosition;
    private Folder parentFolder;
    private List<Folder> folders;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newnote);
        init();
    }

    public void init() {
        Intent intent = getIntent();
        parentFolderPosition = intent.getExtras().getInt("folderPosition");
        folders = FolderManager.getFoldersFromFile(this);
        parentFolder = folders.get(parentFolderPosition);
        done = findViewById(R.id.newNote_iv_doneBtn);
        title = findViewById(R.id.newNote_ed_title);
        descriptionInput = findViewById(R.id.newNote_ed_description);
        descriptionInput.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(descriptionInput, InputMethodManager.SHOW_IMPLICIT);

        done.setOnClickListener(v -> {
            Note note = new Note();
            note.setTitle(title.getText().toString());
            note.setDescription(descriptionInput.getText().toString());
            note.setDone(false);
            note.setCreateTs(new Date());
            parentFolder.addNote(note);
            folders.set(parentFolderPosition, parentFolder);
            FolderManager.saveFoldersToFile(folders, this);
            finish();
        });
    }
}
