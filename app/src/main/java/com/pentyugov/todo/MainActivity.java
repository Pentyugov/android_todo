package com.pentyugov.todo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pentyugov.todo.model.folder.Folder;
import com.pentyugov.todo.model.folder.FolderManager;
import com.pentyugov.todo.util.listener.RecyclerItemClickListener;
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
    private AlertDialog dialogBuilder;
    private List<Folder> systemFolders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println(Arrays.toString(getResources().getTextArray(R.array.folders)));
        init();
    }

    public void init() {
        systemFolders = FolderManager.getSystemFolders(getResources());
        title = findViewById(R.id.main_tv_title);
        newFolder = findViewById(R.id.main_iv_newFolder);
        folders = findViewById(R.id.main_rv_foldersList);
        foldersNames = new ArrayList<>(Arrays
                                            .asList(getResources()
                                            .getStringArray(R.array.folders)));
        adapter = new FolderAdapter(systemFolders);

        folders.setHasFixedSize(true);
        folders.setLayoutManager(new LinearLayoutManager(this));
        folders.setAdapter(adapter);
        folders.addOnItemTouchListener(
                new RecyclerItemClickListener(this, folders ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        showPopupMenu(view, position);
                    }
                })
        );

        newFolder.setOnClickListener(v -> {
            createNewFolderAlertDialog();
            dialogBuilder.show();
        });
    }

    public void createNewFolderAlertDialog() {
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.textedit_alert, null);
        EditText newFolderName = dialogView.findViewById(R.id.alert_newFolderName);

        dialogBuilder = new AlertDialog.Builder(this)
                .setTitle("New folder")
                .setMessage("Enter folder name")
                .setPositiveButton("Submit", (dialog, which) -> {
                    boolean isFolderExist = FolderManager.findFolderByName(systemFolders, newFolderName.getText().toString()) != null;
                    if(isFolderExist) {
                        showErrorAlert("Folder with name " + newFolderName.getText().toString() + "\nalready exists");
                    } else {
                        systemFolders.add(
                                FolderManager.createNewFolder(newFolderName.getText().toString(), systemFolders.size() + 1));
                        adapter.notifyDataSetChanged();
                    }

                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.cancel())
                .setView(dialogView)
                .create();
    }

    private void showErrorAlert(String message) {
        AlertDialog alert = new AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .create();
        alert.show();
    }


    private void showPopupMenu(View v, int position) {
        PopupMenu popupMenu = new PopupMenu(this, v);
        popupMenu.inflate(R.menu.folder_popupmenu);

        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_folder_rename: {

                } return true;

                case R.id.menu_folder_delete: {
                    FolderManager.deleteFolder(systemFolders, position);
                    adapter.notifyDataSetChanged();
                } return true;
                default:
                    return false;
            }
        });

        popupMenu.setOnDismissListener(menu -> {

        });
        popupMenu.show();
    }


}